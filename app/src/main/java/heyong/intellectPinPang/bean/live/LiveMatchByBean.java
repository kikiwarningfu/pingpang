package heyong.intellectPinPang.bean.live;

public class LiveMatchByBean {


        /**
         * payStr : {"package":"Sign=WXPay","appid":"wx41a81a9c33549fd1","sign":"1ABF3EC5CD436AE5DD8F0053287FC559","partnerid":"1578302901","prepayid":"wx21173717979793e15b46db8fad90390000","noncestr":"a57a6a6a135540889def99e51ef4ac71","timestamp":"1611221838"}
         * orderId : wx1611221837399JK5J
         */

        private String payStr;
        private String orderId;

    public String getPayStr() {
        return payStr == null ? "" : payStr;
    }

    public void setPayStr(String payStr) {
        this.payStr = payStr;
    }

    public String getOrderId() {
        return orderId == null ? "" : orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
