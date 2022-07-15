package heyong.intellectPinPang.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.handongkeji.framework.R;
import com.kongzue.dialog.util.DialogSettings;
import com.umeng.analytics.MobclickAgent;
//import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import cn.leo.click.SingleClickManager;
import heyong.handong.framework.ShowSuccessEvent;
import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.DataBindingProvider;
import heyong.handong.framework.base.ResultCallback;
import heyong.handong.framework.dialog.CoachDialog;
import heyong.handong.framework.dialog.TokenDialog;
import heyong.handong.framework.utils.KeyBoardUtils;
import heyong.handong.framework.utils.MyToastUtlis;
import heyong.handong.framework.utils.SystemUtil;
import heyong.handong.framework.widget.Loading_view;
import heyong.intellectPinPang.MyApp;
import heyong.intellectPinPang.soundnet.vlive.Config;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.RtcEventHandler;
import heyong.intellectPinPang.soundnet.vlive.protocol.ClientProxy;
import heyong.lzy.imagepicker.util.StatusBarUtil;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.RtmClient;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public abstract class BaseActivity<T extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity
        implements  DataBindingProvider {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public T mBinding;
    public VM mViewModel;
    public int wdith;
    private Dialog progressDialog;
    private GifImageView gifImageView;
    private Loading_view loading;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        SingleClickManager.setClickInterval(1000);
        try {
            mBinding = DataBindingUtil.setContentView(this, getLayoutRes());

        }catch (Exception e)
        {

        }
        int version = SystemUtil.getVersion();
        try {
            if (version <= 6) {
                StatusBarUtil.statusBarLightMode(this);
                mBinding.getRoot().setFitsSystemWindows(true);
            }
        } catch (Exception e) {
        }


        initViewModel();
        initView(savedInstanceState);

        initWindow();
        initDialog();

        if (mViewModel != null) {
            mViewModel.tokenExpir.observe(this, message -> {
                showOut(message);
            });

            mViewModel.multipleDevice.observe(this, message -> {
                AccountHelper.logout();
                TokenDialog tokenDialog = new TokenDialog();
                tokenDialog.setMessage(message);
                tokenDialog.setConfirmListener(v -> {
                    startLogin();
                });
                tokenDialog.show(getSupportFragmentManager(), "multipleDevice");
            });

            mViewModel.accountFrozen.observe(this, message -> {
                AccountHelper.logout();
                TokenDialog tokenDialog = new TokenDialog();
                tokenDialog.setMessage(message);
                tokenDialog.setConfirmListener(v -> {
                    startLogin();
                });
                tokenDialog.show(getSupportFragmentManager(), "accountFrozen");
            });

            mViewModel.error.observe(this, a -> {
                dismissLoading();
            });
            mViewModel.success.observe(this, a -> {
//                dismissLoading();
            });
        }


    }





    public void showOut(String message) {
        AccountHelper.logout();

        TokenDialog tokenDialog = new TokenDialog();
        tokenDialog.setMessage(message);
        tokenDialog.setConfirmListener(v -> {
            startLogin();
        });
        tokenDialog.show(getSupportFragmentManager(), "tokenExpir");
    }

    private void startLogin() {
        Intent intent = new Intent(getPackageName() + ".com.action.login");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            if (!isTaskRoot()) {
                finish();
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(ShowSuccessEvent event) {
        if (!isBackground(this)) {
            String token = AccountHelper.getToken();
            if (!TextUtils.isEmpty(token)) {
                showCoatchDemo(event);
            }
        }

    }

    //设置字体为默认大小，不随系统字体大小改而改变
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private static long lastClickTime = 0;
    private static final int CLICK_TIME = 80; //快速点击间隔时间

    // 判断按钮是否快速点击
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < CLICK_TIME) {//判断系统时间差是否小于点击间隔时间
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {

                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public boolean hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            return im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public static boolean isBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
//            StaticMethod.debugEMSG(topActivity.getPackageName() + " : " + context.getPackageName());
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private void initDialog() {
        DialogSettings.init();
        DialogSettings.DEBUGMODE = true;
        DialogSettings.isUseBlur = false;
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        DialogSettings.theme = DialogSettings.THEME.LIGHT;
    }

//    @Override
//    public Resources getResources() {
//        return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
//    }

    private void initWindow() {
        WindowManager wm = getWindowManager();
        wdith = wm.getDefaultDisplay().getWidth();
    }

    public void showCoatchDemo(ShowSuccessEvent showSuccessEvent) {
        String ids = showSuccessEvent.getIds();
        String tvTitle = showSuccessEvent.getTvTitle();


        CoachDialog tokenDialog = new CoachDialog();
        tokenDialog.setMessage("" + tvTitle);
        tokenDialog.setIds("" + ids);
        tokenDialog.setConfirmListener(v -> {
            startLogin();
        });
        tokenDialog.show(getSupportFragmentManager(), "accountFrozen");

    }

    @Override
    public void setContentView(int res) {
        super.setContentView(res);
//        AndroidBug5497Workaround.assistActivity(this);    //兼容Edittext弹不起来
    }

    protected void initViewModel() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Type argument = actualTypeArguments[1];
            mViewModel = ViewModelProviders.of(this).get((Class<VM>) argument);
        } else {
            mViewModel = (VM) ViewModelProviders.of(this).get(BaseViewModel.class);
//            throw new RuntimeException("error type : " + type.getClass().getCanonicalName());
        }
    }

    public void showLoading() {

        try {
            if (loading == null) {
                loading = new Loading_view(this, R.style.CustomDialog);
                loading.show();
            } else {
                loading.show();
            }

        } catch (Exception e) {

        }

    }

    public void showLoading(String text) {

        try {
            if (loading == null) {
                loading = new Loading_view(this, R.style.CustomDialog);
                loading.setText("" + text);
                loading.show();
            } else {
                loading.show();
            }
//            mLoadingDialog = LoadDialogUtils.showLoadingDialog(this, "加载中");
//            if (progressDialog == null) {
//                progressDialog = new Dialog(this, R.style.progress_dialog);
//                progressDialog.setContentView(R.layout.dialog_loading);
//                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                gifImageView = progressDialog.findViewById(R.id.gv_error);
//                gifImageView.setImageResource(R.drawable.load_gif);
//                progressDialog.setCanceledOnTouchOutside(false);
//            }
//
//            progressDialog.show();
        } catch (Exception e) {

        }

    }

    //
    public void dismissLoading() {
        if (loading != null) {
            loading.dismiss();//3秒后调用关闭加载的方法
        }

    }

    protected void goActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }


    public void toast(String message) {
        MyToastUtlis.showToastThread("" + message, getApplicationContext());
//        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private SparseArray<ResultCallback> callbackArray = new SparseArray<>();

    public void startActivityForResult(Intent intent, int requestCode, ResultCallback callback) {
        if (callback == null) {
            throw new RuntimeException("callback is null");
        }
        callbackArray.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options, ResultCallback callback) {
        if (callback == null) {
            throw new RuntimeException("callback is null");
        }
        callbackArray.put(requestCode, callback);
        startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        ResultCallback resultCallback = callbackArray.get(requestCode);
        callbackArray.remove(requestCode);
        if (resultCallback != null) {
            resultCallback.onResult(data);
        }
    }

    protected void onError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //region软键盘的处理

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }


    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 右滑返回上级


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev))
                    return super.dispatchTouchEvent(ev);
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());

            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }


}
