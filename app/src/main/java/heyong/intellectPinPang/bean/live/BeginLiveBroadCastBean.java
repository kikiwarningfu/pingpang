package heyong.intellectPinPang.bean.live;

import java.io.Serializable;

public class BeginLiveBroadCastBean implements Serializable {
    private String channelName;
    private String userRole;
    private int optionalUid;
    private String options;
    private String token;
    private String channelScene;
    private String leftName;
    private String rightName;

    public String getLeftName() {
        return leftName == null ? "" : leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName == null ? "" : rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getOptionalUid() {
        return optionalUid;
    }

    public void setOptionalUid(int optionalUid) {
        this.optionalUid = optionalUid;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannelScene() {
        return channelScene;
    }

    public void setChannelScene(String channelScene) {
        this.channelScene = channelScene;
    }

}
