package heyong.intellectPinPang.factory;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import heyong.intellectPinPang.bean.pay.WXPayBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by whf on 2017/11/28.
 * 支付工厂类
 */

public class PayFactory {
    private static final String TAG = PayFactory.class.getSimpleName();
    String s = "支付失败";
    public static String APPID="wx41a81a9c33549fd1";
    public static interface PayListener {
        void onPayListener(String s, boolean isOk);
    }

    public static interface PayLoginListener {
        void onPayLoginListener(String s, String authCode, boolean isOk);
    }

//    @Override
//    public void wxCallBack(int code) {
//        Log.e(TAG, "wxCallBack: code===="+code );
//        if (code == 0) {
//            ToastUtils.showSuccessToast("支付成功");
//            finish();
//        }else if (code == -2) {
//            ToastUtils.showErrorToast("取消支付");
//        } else {
//            ToastUtils.showErrorToast("支付出错");
//        }
//    }

//    WXPayEntryActivity.setWXListener(this);
//      PayFactory payFactory = new PayFactory();
//        payFactory.WxPay(PayActivity.this, data.data);
//    PayFactory payFactory = new PayFactory();
//        payFactory.AliPay(String.valueOf(data.data), PayActivity.this, new PayFactory.PayListener() {
//        @Override
//        public void onPayListener(String s, boolean isOk) {
//            if (isOk) {
//                RxBus.getDefault().post(new SwipHoleDetailEvent());
//                ToastUtils.showSuccessToast("报名成功");
//                finish();
//            } else {
//                ToastUtils.showErrorToast("" + s);
//            }
//        }
//    });

    /*支付宝授权登录*/
    public void AliLogin(final Context context,final String authInfo, final PayLoginListener mlistener) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, String>> e) throws Exception {
                AuthTask alilogin = new AuthTask((Activity) context);
                Map<String, String> result = alilogin.authV2(authInfo, true);
                e.onNext(result);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Map<String, String> data) {
                        AuthResult authResult = new AuthResult(data, true);
                        String resultStatus = authResult.getResultStatus();
                        // 判断resultStatus 为“9000”且result_code
                        // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                            // 获取alipay_open_id，调支付时作为参数extern_token 的value
                            mlistener.onPayLoginListener("授权成功", authResult.getAuthCode(), true);

                        } else {
                            // 其他状态值则为授权失败
                            mlistener.onPayLoginListener("授权失败", "", false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mlistener.onPayLoginListener(s, "", false);
//                        Log.e(TAG, "onNext   onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*支付宝支付*/
    public void AliPay(final String aliInfo, final Context context, final PayListener mlistener) {
        Log.e(TAG, "AliPay: ==="+new Gson().toJson(aliInfo) );
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, String>> e) throws Exception {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(aliInfo, true);

                e.onNext(result);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if (mCompositeDisposable != null)
//                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Map<String, String> data) {
                        int resultStatus = Integer.parseInt(data.get("resultStatus"));
//                        Log.e(TAG, "onNext: " + resultStatus);
                        switch (resultStatus) {
                            case 9000:
                                s = "订单支付成功";
                                mlistener.onPayListener(s, true);
                                break;
                            case 8000:
                                s = "正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态";
                                mlistener.onPayListener(s, false);
                                break;
                            case 4000:
                                s = "订单支付失败";
                                mlistener.onPayListener(s, false);
                                break;
                            case 5000:
                                s = "重复请求";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6001:
                                s = "用户中途取消";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6002:
                                s = "网络连接出错";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6004:
                                s = "支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态";
                                mlistener.onPayListener(s, false);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mlistener.onPayListener(s, false);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 微信部分
     */
    private IWXAPI api;

    /**
     * 微信部分
     */

    public void WxPay(Context context, WXPayBean mBean) {
        APPID = mBean.getAppid();
        // 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID
        api = WXAPIFactory.createWXAPI(context, mBean.getAppid());  // 参数二是申请的APPID

        // 将该app注册到微信
        api.registerApp(mBean.getAppid());  // 参数是申请的APPID

        PayReq request = new PayReq();
        request.appId = mBean.getAppid();
        request.partnerId = mBean.getPartnerid();
        request.prepayId = mBean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = mBean.getNoncestr();
        request.timeStamp = mBean.getTimestamp() + "";
        request.sign = mBean.getSign();
        api.sendReq(request);  // 发起支付
        Log.e(TAG, "okhttp WxPay: 发起支付" + api.sendReq(request));


    }
}
