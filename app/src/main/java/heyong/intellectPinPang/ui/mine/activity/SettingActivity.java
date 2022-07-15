package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.bugly.beta.Beta;
//import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;
import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivitySettingBinding;
import heyong.intellectPinPang.event.HotFixAddEvent;
import heyong.intellectPinPang.ui.competition.activity.PersonalInformationActivity;
import heyong.intellectPinPang.ui.login.activity.LoginActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CleanMyCache;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity<ActivitySettingBinding, BaseViewModel> implements View.OnClickListener {

    private String numCache;



    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        getCache();


    }

//    // 需要点击几次 就设置几
//    long[] mHits = null;
//
//    public void onDisplaySettingButton() {
//        if (mHits == null) {
//            mHits = new long[3];
//        }
//        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);//把从第二位至最后一位之间的数字复制到第一位至倒数第一位
//        mHits[mHits.length - 1] = SystemClock.uptimeMillis();//记录一个时间
//        if (SystemClock.uptimeMillis() - mHits[0] <= 1000) {//一秒内连续点击。
//            mHits = null;    //这里说明一下，我们在进来以后需要还原状态，否则如果点击过快，第六次，第七次 都会不断进来触发该效果。重新开始计数即可
//            if (mShow) {
//                //这里是你具体的操作
//                mShow = false;
//                Log.e(TAG, "onDisplaySettingButton: 连点五次");
//            } else {
//                //这里也是你具体的操作
//                mShow = true;
//                Log.e(TAG, "onDisplaySettingButton: 没有连点五次");
//            }
//            //这里一般会把mShow存储到sp中。
//        }
//    }

    private void getCache() {
        try {
            numCache = CleanMyCache.getTotalCacheSize(SettingActivity.this);
            mBinding.tvCache.setText("" + numCache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(SettingActivity.this)
                        .setMessage("")
                        .setTvTitle("是否退出登录?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                exitLogin()
                        )
                        .show();
                break;
            case R.id.ll_personal_data://个人资料
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //跳转到个人资料界面
                goActivity(PersonalInformationActivity.class);
                break;
            case R.id.ll_account://账户和安全
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(AccountNumberSecurityKotlinActivity.class);
//                goActivity(AccountNumberSecurityActivity.class);
                break;
            case R.id.ll_clear_cache://清除缓存
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(SettingActivity.this)
                        .setMessage("")
                        .setTvTitle("是否清空缓存?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                showDialogs()
                        )
                        .show();

                break;
            case R.id.ll_user_agrement://用户协议
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentWeb2 = new Intent(this, WebViewActivity.class);
                intentWeb2.putExtra(WebViewActivity.TITLE, "用户协议");
                intentWeb2.putExtra(WebViewActivity.URLS, "http://backstage.xlttsports.com/appDownLoad/用户协议与隐私保护.html");
                startActivity(intentWeb2);
                break;
            case R.id.ll_policy://隐私政策
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentWeb = new Intent(this, WebViewActivity.class);
                intentWeb.putExtra(WebViewActivity.TITLE, "隐私政策");
                intentWeb.putExtra(WebViewActivity.URLS, "http://backstage.xlttsports.com/appDownLoad/隐私政策.html");
                startActivity(intentWeb);
                break;
            case R.id.ll_about_us://关于我们
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(AboutUsKotlinActivity.class);
//                goActivity(AboutUsActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(HotFixAddEvent event) {
        Beta.applyDownloadedPatch();
    }

    private void exitLogin() {
        try {
            AccountHelper.setToken("");
            JPushInterface.deleteAlias(SettingActivity.this, 1);
            goActivity(LoginActivity.class);
            finish();
        } catch (Exception e) {
            AccountHelper.setToken("");
            goActivity(LoginActivity.class);
            finish();
        }

//        PushAgent.getInstance(SettingActivity.this).deleteAlias("zhinengpingcai_" + AccountHelper.getUserId(),
//                "zhinengpingcai_type", (isSuccess, message) -> {
//                    Log.i("UmengAlias", "isSuccess=" + isSuccess + "/nmessage==" + message);
//                    Log.i("UmengAlias", "alias=" + "" + AccountHelper.getUserId());
//                });

    }

    private void showDialogs() {
        CleanMyCache.clearAllCache(SettingActivity.this);
        new MessageDialogBuilder(SettingActivity.this)
                .setMessage("")
                .setTvTitle("清理成功")
                .setTvCancel("取消")
                .setBtnCancleHint(true)
                .setTvSure("确定")

                .show();
        getCache();
    }
}