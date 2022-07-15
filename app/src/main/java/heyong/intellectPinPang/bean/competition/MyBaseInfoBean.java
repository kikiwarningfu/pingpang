package heyong.intellectPinPang.bean.competition;

public class MyBaseInfoBean {
    /**
     * headImg : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
     * nickName : dasdasd
     * sex : 1
     * matchIntegral : 0
     * role : 国家一级裁判员
     * messageCount : 0
     * backImgUrl :
     * identificationStatus : 2
     */

    private String headImg;
    private String nickName;
    private String sex;
    private int matchIntegral;
    private String role;
    private int messageCount;
    private String backImgUrl;
    private String identificationStatus;
    private String account;

    public String getAccount() {
        return account == null ? "" : account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMatchIntegral() {
        return matchIntegral;
    }

    public void setMatchIntegral(int matchIntegral) {
        this.matchIntegral = matchIntegral;
    }

    public String getRole() {
        return role == null ? "" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getBackImgUrl() {
        return backImgUrl == null ? "" : backImgUrl;
    }

    public void setBackImgUrl(String backImgUrl) {
        this.backImgUrl = backImgUrl;
    }

    public String getIdentificationStatus() {
        return identificationStatus == null ? "" : identificationStatus;
    }

    public void setIdentificationStatus(String identificationStatus) {
        this.identificationStatus = identificationStatus;
    }
}
