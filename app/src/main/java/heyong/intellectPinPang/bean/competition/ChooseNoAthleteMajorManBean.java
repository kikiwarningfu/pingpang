package heyong.intellectPinPang.bean.competition;

public class ChooseNoAthleteMajorManBean {
    private String userName;
    private long usetId;
    private String userSex;

    public ChooseNoAthleteMajorManBean(String userName, long usetId, String userSex) {
        this.userName = userName;
        this.usetId = usetId;
        this.userSex = userSex;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUsetId() {
        return usetId;
    }

    public void setUsetId(long usetId) {
        this.usetId = usetId;
    }

    public String getUserSex() {
        return userSex == null ? "" : userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}
