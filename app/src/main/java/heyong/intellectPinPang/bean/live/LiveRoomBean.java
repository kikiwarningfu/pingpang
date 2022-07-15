package heyong.intellectPinPang.bean.live;

public class LiveRoomBean {


    private int userId;
    private int optionalUid;
    private String token;
    private String free;//购买所需费用
    private boolean purchased;//是否已购买
    private boolean canTry;//是否能试看
    private int surplusSecond;//剩余试看秒数
    private long id;
    private String itemTitle;
    private String itemType;
    private String matchTitle;
    private String matchTime;
    private int onLineCount;
    private String channelName;
    private String player1;
    private String player1Img;
    private String player2;
    private String player2Img;
    private String player3;
    private String player3Img;
    private String player4;
    private String player4Img;
    private String status;
    private String videoAddress;

    public String getVideoAddress() {
        return videoAddress == null ? "" : videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress;
    }

    public int getOptionalUid() {
        return optionalUid;
    }

    public void setOptionalUid(int optionalUid) {
        this.optionalUid = optionalUid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFree() {
        return free == null ? "" : free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean isCanTry() {
        return canTry;
    }

    public void setCanTry(boolean canTry) {
        this.canTry = canTry;
    }

    public int getSurplusSecond() {
        return surplusSecond;
    }

    public void setSurplusSecond(int surplusSecond) {
        this.surplusSecond = surplusSecond;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle == null ? "" : itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchTime() {
        return matchTime == null ? "" : matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public int getOnLineCount() {
        return onLineCount;
    }

    public void setOnLineCount(int onLineCount) {
        this.onLineCount = onLineCount;
    }

    public String getChannelName() {
        return channelName == null ? "" : channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPlayer1() {
        return player1 == null ? "" : player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer1Img() {
        return player1Img == null ? "" : player1Img;
    }

    public void setPlayer1Img(String player1Img) {
        this.player1Img = player1Img;
    }

    public String getPlayer2() {
        return player2 == null ? "" : player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer2Img() {
        return player2Img == null ? "" : player2Img;
    }

    public void setPlayer2Img(String player2Img) {
        this.player2Img = player2Img;
    }

    public String getPlayer3() {
        return player3 == null ? "" : player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer3Img() {
        return player3Img == null ? "" : player3Img;
    }

    public void setPlayer3Img(String player3Img) {
        this.player3Img = player3Img;
    }

    public String getPlayer4() {
        return player4 == null ? "" : player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    public String getPlayer4Img() {
        return player4Img == null ? "" : player4Img;
    }

    public void setPlayer4Img(String player4Img) {
        this.player4Img = player4Img;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
