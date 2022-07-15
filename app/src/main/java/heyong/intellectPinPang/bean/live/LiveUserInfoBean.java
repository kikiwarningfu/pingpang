package heyong.intellectPinPang.bean.live;

public class LiveUserInfoBean {


    /**
     * id : int
     * userId : long //用户ID
     * deviceInfo : string //设备信息
     * testVideo : string //测试视频url
     * status : int //状态信息     0=申请中，1=审核通过，2=审核拒绝，3=禁用
     * createTime : date //申请时间
     * approveUserId : long //审批人ID
     * approveTime : date //审批时间
     * remark : string //备注或拒绝原因
     * accountType : int //收款方式 1=支付宝，2=微信，3=银行卡
     * realName : string //真实姓名
     * accountCode : string //银行卡，微信OPENID或支付宝支付宝登录号，目前只支持支付宝方式
     * bankCode : string //如account_type=3(银行支付)，填写银行编号
     */

    private long id;
    private long userId;
    private String deviceInfo;
    private String testVideo;
    private int status;
    private String createTime;
    private long approveUserId;
    private String approveTime;
    private String remark;
    private int accountType;
    private String realName;
    private String accountCode;
    private String bankCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDeviceInfo() {
        return deviceInfo == null ? "" : deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTestVideo() {
        return testVideo == null ? "" : testVideo;
    }

    public void setTestVideo(String testVideo) {
        this.testVideo = testVideo;
    }

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

    public long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveTime() {
        return approveTime == null ? "" : approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getRealName() {
        return realName == null ? "" : realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccountCode() {
        return accountCode == null ? "" : accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getBankCode() {
        return bankCode == null ? "" : bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
