package heyong.intellectPinPang.bean.live;

import com.google.gson.annotations.SerializedName;

public class LiveWxPayBean {

    /**
     * package : Sign=WXPay
     * appid : wx41a81a9c33549fd1
     * sign : 1ABF3EC5CD436AE5DD8F0053287FC559
     * partnerid : 1578302901
     * prepayid : wx21173717979793e15b46db8fad90390000
     * noncestr : a57a6a6a135540889def99e51ef4ac71
     * timestamp : 1611221838
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
