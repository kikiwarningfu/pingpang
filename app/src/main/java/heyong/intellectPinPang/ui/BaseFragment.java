package heyong.intellectPinPang.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.handongkeji.framework.R;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.DataBindingProvider;
import heyong.handong.framework.base.ResultCallback;
import heyong.handong.framework.utils.ToastUtils;
import heyong.handong.framework.widget.Loading_view;
import pl.droidsonroids.gif.GifImageView;


public abstract class BaseFragment<T extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements DataBindingProvider {

    protected T binding;
    protected VM viewModel;
    private QMUITipDialog tipDialog;
    private Dialog progressDialog;
    private GifImageView gifImageView;
    private Loading_view loading;
    //传递过来的参数Bundle，供子类使用
    protected Bundle args;

    /**
     * 创建fragment的静态方法，方便传递参数
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment>T newInstance(Class clazz,Bundle args) {
        T mFragment=null;
        try {
            mFragment= (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = initViewModel();
        args = getArguments();
        getLifecycle().addObserver(viewModel);

        initView(savedInstanceState);

        BaseViewModel parentViewModel = ((BaseActivity) getActivity()).mViewModel;
        if (parentViewModel != viewModel && viewModel != null) {

            viewModel.tokenExpir.observe(this, message -> {
                parentViewModel.tokenExpir.postValue(message);
            });
            viewModel.multipleDevice.observe(this, message -> {
                parentViewModel.multipleDevice.postValue(message);
            });
            viewModel.accountFrozen.observe(this, message -> {
                parentViewModel.accountFrozen.postValue(message);
            });

            viewModel.error.observe(this, a -> {
                dismissLoading();
            });
            viewModel.success.observe(this, a -> {
                dismissLoading();
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(viewModel);
    }

    public void showLoading() {

//        try {
//            if (progressDialog == null) {
//                progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
//                progressDialog.setContentView(R.layout.dialog_loading);
//                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                gifImageView = progressDialog.findViewById(R.id.gv_error);
//                gifImageView.setImageResource(R.drawable.load_gif);
//                progressDialog.setCanceledOnTouchOutside(false);
//            }
//
//            progressDialog.show();
//        } catch (Exception e) {
//
//        }
        try {
            if (loading == null) {
                loading = new Loading_view(getActivity(), R.style.CustomDialog);
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

    public void dismissLoading() {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
        if (loading != null) {
            loading.dismiss();//3秒后调用关闭加载的方法
        }

    }


    protected VM initViewModel() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Type argument = actualTypeArguments[1];
            if (initalizeViewModelFromActivity()) {
                viewModel = ViewModelProviders.of(getActivity()).get((Class<VM>) argument);
            } else {
                viewModel = ViewModelProviders.of(this).get((Class<VM>) argument);
            }
        } else {
            viewModel = (VM) new BaseViewModel();
        }
        return viewModel;
    }

    protected boolean initalizeViewModelFromActivity() {
        return false;
    }

    protected void goActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    public void toast(String message) {
        ToastUtils.showToast(getActivity(), ""+message);
//        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

}