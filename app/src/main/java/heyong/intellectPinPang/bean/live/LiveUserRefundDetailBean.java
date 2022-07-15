package heyong.intellectPinPang.bean.live;

public class LiveUserRefundDetailBean {

    /**
     * requestId : int //直播唯一ID
     * reFundTime : date //申请退款时间
     * checkIngTime : date //退款审核中时间
     * status : int //状态 1=申请退款中，2=申请同意，3=申请驳回
     * complainTitle : string //申请退款理由标题
     * complain : string //申请退款理由
     * remark : string //备注，审批内容
     * adminTime : date //审批时间
     * money : float //退款金额
     */

    private int requestId;
    private String reFundTime;
    private String checkIngTime;
    private int status;
    private String complainTitle;
    private String complain;
    private String remark;
    private String adminTime;
    private float money;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getReFundTime() {
        return reFundTime == null ? "" : reFundTime;
    }

    public void setReFundTime(String reFundTime) {
        this.reFundTime = reFundTime;
    }

    public String getCheckIngTime() {
        return checkIngTime == null ? "" : checkIngTime;
    }

    public void setCheckIngTime(String checkIngTime) {
        this.checkIngTime = checkIngTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComplainTitle() {
        return complainTitle == null ? "" : complainTitle;
    }

    public void setComplainTitle(String complainTitle) {
        this.complainTitle = complainTitle;
    }

    public String getComplain() {
        return complain == null ? "" : complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdminTime() {
        return adminTime == null ? "" : adminTime;
    }

    public void setAdminTime(String adminTime) {
        this.adminTime = adminTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
