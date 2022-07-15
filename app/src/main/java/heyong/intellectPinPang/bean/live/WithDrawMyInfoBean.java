package heyong.intellectPinPang.bean.live;

public class WithDrawMyInfoBean {

    /**
     * id : 615598367317578928
     * mayMoney : 120.00
     * weChat : true
     * upperLimit : 200.00
     * apayName : null
     * apayAccount : null
     * wxName : 一念·初见
     * wxOpenId : 133
     * apay : false
     */

    private long id;
    private String mayMoney;
    private boolean weChat;
    private String upperLimit;
    private String apayName;
    private String apayAccount;
    private String wxName;
    private String wxOpenId;
    private boolean apay;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMayMoney() {
        return mayMoney == null ? "0" : mayMoney;
    }

    public void setMayMoney(String mayMoney) {
        this.mayMoney = mayMoney;
    }

    public boolean isWeChat() {
        return weChat;
    }

    public void setWeChat(boolean weChat) {
        this.weChat = weChat;
    }

    public String getUpperLimit() {
        return upperLimit == null ? "" : upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getApayName() {
        return apayName == null ? "" : apayName;
    }

    public void setApayName(String apayName) {
        this.apayName = apayName;
    }

    public String getApayAccount() {
        return apayAccount == null ? "" : apayAccount;
    }

    public void setApayAccount(String apayAccount) {
        this.apayAccount = apayAccount;
    }

    public String getWxName() {
        return wxName == null ? "" : wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxOpenId() {
        return wxOpenId == null ? "" : wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public boolean isApay() {
        return apay;
    }

    public void setApay(boolean apay) {
        this.apay = apay;
    }
}
