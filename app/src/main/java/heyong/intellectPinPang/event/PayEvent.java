package heyong.intellectPinPang.event;

/**
 * Create On 2019/7/5 0005 11:42
 * Copyrights 2019/7/5 0005 handongkeji All rights reserved
 * ClassName:PayEvent
 * PackageName:com.drinkingcat.app.event
 * Site:http://www.handongkeji.com
 * Author: chenzhiguang
 * Date: 2019/7/5 0005 11:42
 * Description:
 */
public class PayEvent {

    public static final int PAY_SUCCESS_CODE = 0;

    public static final int PAY_ERROR_CODE = 1;

    public static final int PAY_CANCEL_CODE = 2;

    public static final int PAY_ERROR_INTERNAL_CODE = 500;

    private int payCode;

    private String errorMsg;

    public PayEvent(int payCode) {
        this(payCode, "Pay Message ");
    }

    public PayEvent(int payCode, String errorMsg) {
        this.payCode = payCode;
        this.errorMsg = errorMsg;
    }

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? "" : errorMsg;
    }

    @Override
    public String toString() {
        return "PayEvent{" +
                "payCode=" + payCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
