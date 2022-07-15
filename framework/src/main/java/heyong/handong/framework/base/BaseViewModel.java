package heyong.handong.framework.base;

import android.text.TextUtils;

import androidx.lifecycle.*;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.stream.MalformedJsonException;

import heyong.handong.framework.api.ApiException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class BaseViewModel extends ViewModel implements GenericLifecycleObserver {


    public final MutableLiveData<String> tokenExpir = new MutableLiveData<>();
    public final MutableLiveData<String> multipleDevice = new MutableLiveData<>();
    public final MutableLiveData<String> accountFrozen = new MutableLiveData<>();
    public final MutableLiveData<Boolean> error = new MutableLiveData<>();
    public final MutableLiveData<Boolean> success = new MutableLiveData<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        switch (event) {
            case ON_DESTROY:
                compositeDisposable.dispose();
                break;
        }
    }

    protected abstract class SimpleObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(T t) {
            if (t instanceof ResponseBean) {

                ResponseBean responseBean = (ResponseBean) t;
                String msg = responseBean.getMessage();
                String code = responseBean.getErrorCode();
                try {
                    if (responseBean.getErrorCode().equals("200")||responseBean.getErrorCode().equals("0")||responseBean.getErrorCode().equals("10000")) {
                        onSuccess(t);
                        success.postValue(true);
                    } else {
//                        TOKEN_EXPIRED("200000-100001", "token.expired", "用户令牌超时"),
//                                NOT_HAVE_TOKEN("200000-100002", "token.not.exist", "缺失用户令牌"),
                        if (code.equals("200000-100001")||code.equals("200000-100002")) {
//                            if (msg.toLowerCase().contains("token")) {
                            tokenExpir.postValue("token已过期，请重新登录");
//                            } else {
//                                onError(ApiException.Code_Default, "" + msg);
//                            }
                        } else {
                            dealError(t, msg, code);
                        }

                    }
                } catch (Exception e) {
                    if (code.equals("200000-100001")) {
//                        if (msg.toLowerCase().contains("token")) {
                        tokenExpir.postValue("token已过期，请重新登录");
//                        } else {
//                            onError(ApiException.Code_Default, "" + msg);
//                        }
                    } else {
                        dealError(t, msg, code);
                    }
                }

            } else {
                onSuccess(t);
                success.postValue(true);
            }
        }

        private void dealError(T t, String msg, String code) {
            if (code.equals("100000-100012") || code.equals("100000-100011")
                    || code.equals("100000-100026") || code.equals("100000-100018") || code.equals("100000-100030")
                    || code.equals("100000-100029") || code.equals("100000-100032") || code.equals("100000-100033") || code.equals("100000-100035")
                    || code.equals("100000-100034") || code.equals("100000-100002") || code.equals("100000-100005") || code.equals("100000-100001")
                    || code.equals("100000-100003") || code.equals("100000-100004") || code.equals("100000-100006")
                    || code.equals("100000-100044")||code.equals("100000-100048")||code.equals("100000-100022")||code.equals("100000-100010")
            ||code.equals("100000-100047")
            ||code.equals("300000-100001")||code.equals("300000-100001")
            ||code.equals("300000-100012")||code.equals("100000-100040")
            ||code.equals("100000-100007")||code.equals("100000-100008")||code.equals("100000-100009")||code.equals("100000-100013")||code.equals("100000-100014")
            ||code.equals("100000-100015")||code.equals("100000-100016")||code.equals("100000-100017")||code.equals("100000-100018")||code.equals("100000-100019")
                    ||code.equals("100000-100020")
                    ||code.equals("100000-100021")||code.equals("100000-100022")||code.equals("100000-100023")||code.equals("100000-100024")||code.equals("100000-100025")
                    ||code.equals("100000-100026")||code.equals("100000-100027")||code.equals("100000-100028")||code.equals("100000-100029")||code.equals("100000-100030")
                    ||code.equals("100000-100031")||code.equals("100000-100032")||code.equals("100000-100033")||code.equals("100000-100034")||code.equals("100000-100035")
                    ||code.equals("100000-100036")||code.equals("100000-100037")||code.equals("100000-100038")||code.equals("100000-100039")||code.equals("100000-100040")
                    ||code.equals("100000-100041")||code.equals("100000-100042")||code.equals("100000-100043")||code.equals("100000-100044")||code.equals("100000-100045")
                    ||code.equals("100000-100046")||code.equals("100000-100047")||code.equals("100000-100048")||code.equals("100000-100049")||code.equals("100000-100050")
                    ||code.equals("100000-100051")||code.equals("100000-100052")
            ||code.equals("100000-100065")||code.equals("100000-100064")
            ||code.equals("100000-100054")||code.equals("100000-100072")
        ||code.equals("100000-100067")||code.equals("100000-100068")||code.equals("100000-100061")
            ||code.equals("100000-100070")
            ||code.equals("100000-100074")||code.equals("100000-100075")||code.equals("100000-100076")) {


//                LIVE_NOT_RECEIVER("300000-100001","live.not.receiver", "您目前还不是接单人哦，马上申请成为接单人，接单手不停！"),
//                        LIVE_NOT_REVIEW("300000-100003","live.not.review", "您的审核还未通过，我们马上审核！"),
//                        LIVE_RECEIVE_FROZEN("300000-100002","live.receive.frozen", "您的接单人身份被冻结，暂时不能接单"),
//                        LIVE_ILLEGAL_REQUEST("300000-100004","live.illegal.request", "非法的请求！"),
//                        LIVE_RECEIVE_LATE("300000-100005","live.receiver.late", "您来晚了，别人已经抢先接单了！"),
//                        LIVE_SLEF_ORDER("300000-100006","live.self.order", "这是私有订单，只能发布人本人能接单！"),
//                        LIVE_PUSH_LATE("300000-100007","live.push.late", "您来晚了，已经有人发布了直播悬赏!"),
//                        LIVE_PUSH_DOING("300000-100008","live.push.doing", "他人正在发布直播悬赏!"),
//                        LIVE_PUSH_MONEY_MIN("300000-100009","live.push.money.min", "悬赏金额低于系统设定最少值!"),
//                        LIVE_PAY_ERROR("300000-100010","live.pay.error", "支付出错啦，请重试!"),
//                        LIVE_ILLEGAL_ASSESS("300000-100011","live.illegal.assess", "你没有权限评价他人订单！"),
//                        LIVE_IMPROVE_ACCOUNT("300000-100012","live.improve.account", "请完善用户的收款信息！"),
//                        LIVE_PUSH_FORBIDDEN("300000-100013","live.push.forbidden", "离比赛还不到60分钟不能颁布悬赏了！"),
//                        LIVE_RECEIVE_REJECT("300000-100014","live.receive.reject", "您的申请被拒绝！"),
//                        LIVE_APPRAISE_FORBIDDEN("300000-100015","live.appraise.forbidden", "您没权限评价！"),
//                        LIVE_APPLY_REPEAT("300000-100016","live.apply.repeat", "您已经申请过，请不要重复申请！"),
//                        LIVE_REFUND_LATE("300000-100017","live.refund.late", "订单已经结算完成，不能申请退款！"),
//                        LIVE_ORDER_NOEXIST("300000-100017","live.order.noexist", "订单不存在！"),
//                        LIVE_ORDER_CANNOTCANCLE("300000-100017","live.order.cannotcancle", "此订单已经有人接单，不能取消！"),
//                        LIVE_ORDER_CANCLELATE("300000-100017","live.order.canclelate", "比赛开始前1小时内不能取消订单！"),
                //100000-100044  有未完成的界面
                onSuccess(t);
                success.postValue(true);
            } else {
                onError(0, msg);
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof CompositeException) {
                CompositeException compositeE = (CompositeException) e;
                for (Throwable throwable : compositeE.getExceptions()) {
                    if (throwable instanceof SocketTimeoutException) {
                        onError(ApiException.Code_TimeOut, ApiException.SOCKET_TIMEOUT_EXCEPTION);
                    } else if (throwable instanceof ConnectException) {
                        onError(ApiException.Code_UnConnected, ApiException.CONNECT_EXCEPTION);
                    } else if (throwable instanceof UnknownHostException) {
                        onError(ApiException.Code_UnConnected, ApiException.CONNECT_EXCEPTION);
                    } else if (throwable instanceof MalformedJsonException) {
                        onError(ApiException.Code_MalformedJson, ApiException.MALFORMED_JSON_EXCEPTION);
                    } else {
                        onError(ApiException.Code_Default, "解析异常");
                    }
                }
            } else if (e instanceof SocketTimeoutException) {
                onError(ApiException.Code_TimeOut, ApiException.SOCKET_TIMEOUT_EXCEPTION);
            } else if (e instanceof ConnectException) {
                onError(ApiException.Code_UnConnected, ApiException.CONNECT_EXCEPTION);
            } else if (e instanceof UnknownHostException) {
                onError(ApiException.Code_UnConnected, ApiException.CONNECT_EXCEPTION);
            } else if (e instanceof MalformedJsonException) {
                onError(ApiException.Code_MalformedJson, ApiException.MALFORMED_JSON_EXCEPTION);
            } else {
                String msg = e.getMessage();
                if (msg == null) {
                    onError(ApiException.Code_Default, e.toString());
                    return;
                }
                if (msg.toLowerCase().contains("token")) {
                    tokenExpir.postValue("token已过期，请重新登录");
                } else {
                    onError(ApiException.Code_Default, "" + msg);
                }
            }


        }

        @Override
        public void onComplete() {

        }

        /**
         * 加载成功
         *
         * @param t 获得的数据实体
         */
        public abstract void onSuccess(@NonNull T t);


        /**
         * 连接失败或返回数据错误
         *
         * @param code errorMsg
         */
        public void onError(int code, String errorMsg) {
            error.postValue(true);
            if (!TextUtils.isEmpty(errorMsg)) {
                ToastUtils.showShort(errorMsg);
            }
        }
    }
}
