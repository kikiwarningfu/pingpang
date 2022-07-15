package heyong.intellectPinPang.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.event.PayEvent;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String WX_ID = "wx41a81a9c33549fd1";
    public static final String WX_KEY = "92c1bbfa48663660be692db6187ff706";
    private IWXAPI api;
    private static WXListener wxListener;

    public interface WXListener {
        void wxCallBack(int code);
    }
    public static void setWXListener(WXListener listener) {
        wxListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WX_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }
//        PayResp resp1 = (PayResp) resp;
//        String  payType = resp1.extData;//支付方式 用来区分订单支付 还是充值回调

//        switch (payType) {
//            case "OrderPay":        //订单支付的回调
//
//                break;
//            case "RechargePay":     //充值支付的回调
//
//                break;
//        }
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//
//        }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("aaa", "onPayFinish, errCode = " + resp.errCode);

        int code = resp.errCode;

        if (code == 0) {  //  成功
            if (wxListener != null) {
                wxListener.wxCallBack(0);
            }
            RxBus.getDefault().post(new PayEvent(PayEvent.PAY_SUCCESS_CODE));
        } else if (code == -1) {  //  错误
            if (wxListener != null) {
                wxListener.wxCallBack(-1);
            }
            RxBus.getDefault().post(new PayEvent(PayEvent.PAY_ERROR_CODE));
        } else if (code == -2) {  // 用户取消
            if (wxListener != null) {
                wxListener.wxCallBack(-2);
            }
            RxBus.getDefault().post(new PayEvent(PayEvent.PAY_CANCEL_CODE));
        }
        finish();
    }
}