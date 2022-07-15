package heyong.intellectPinPang.bean.live;

public class LiveUserWithDrawalDetailBean {


    /**
     * status : int //提现状态  0=提现中 1=提现完成 2=提现驳回
     * createTime : date //提现申请时间
     * applyingTime : date //提现中
     * endTime : date //提现成功或驳回时间
     * money : float //提现金额，单位元
     */

    private int status;
    private String createTime;
    private String applyingTime;
    private String endTime;
    private double money;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getApplyingTime() {
        return applyingTime == null ? "" : applyingTime;
    }

    public void setApplyingTime(String applyingTime) {
        this.applyingTime = applyingTime;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
