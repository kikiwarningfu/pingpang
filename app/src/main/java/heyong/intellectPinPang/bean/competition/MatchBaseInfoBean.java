package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;

public class MatchBaseInfoBean implements Serializable {
    private String arrangeId;
    private String matchId;
    private String title;
    private String angle;
    private String leftClubId;
    private String leftTeamId;
    private String leftClubType;
    private String leftClubName;
    private String leftClubImg;
    private String rightClubId;
    private String rightTeamId;
    private String rightClubType;
    private String rightClubName;
    private String rightClubImg;
    private String leftOrRight;
    private String appOperationLogId;

    public MatchBaseInfoBean(String arrangeId, String matchId, String title, String angle, String leftClubId, String leftTeamId, String leftClubType, String leftClubName, String leftClubImg, String rightClubId, String rightTeamId, String rightClubType, String rightClubName, String rightClubImg, String leftOrRight, String appOperationLogId) {
        this.arrangeId = arrangeId;
        this.matchId = matchId;
        this.title = title;
        this.angle = angle;
        this.leftClubId = leftClubId;
        this.leftTeamId = leftTeamId;
        this.leftClubType = leftClubType;
        this.leftClubName = leftClubName;
        this.leftClubImg = leftClubImg;
        this.rightClubId = rightClubId;
        this.rightTeamId = rightTeamId;
        this.rightClubType = rightClubType;
        this.rightClubName = rightClubName;
        this.rightClubImg = rightClubImg;
        this.leftOrRight = leftOrRight;
        this.appOperationLogId = appOperationLogId;
    }

    public MatchBaseInfoBean() {
    }

    public String getArrangeId() {
        return arrangeId == null ? "" : arrangeId;
    }

    public void setArrangeId(String arrangeId) {
        this.arrangeId = arrangeId;
    }

    public String getMatchId() {
        return matchId == null ? "" : matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAngle() {
        return angle == null ? "" : angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getLeftClubId() {
        return leftClubId == null ? "" : leftClubId;
    }

    public void setLeftClubId(String leftClubId) {
        this.leftClubId = leftClubId;
    }

    public String getLeftTeamId() {
        return leftTeamId == null ? "" : leftTeamId;
    }

    public void setLeftTeamId(String leftTeamId) {
        this.leftTeamId = leftTeamId;
    }

    public String getLeftClubType() {
        return leftClubType == null ? "" : leftClubType;
    }

    public void setLeftClubType(String leftClubType) {
        this.leftClubType = leftClubType;
    }

    public String getLeftClubName() {
        return leftClubName == null ? "" : leftClubName;
    }

    public void setLeftClubName(String leftClubName) {
        this.leftClubName = leftClubName;
    }

    public String getLeftClubImg() {
        return leftClubImg == null ? "" : leftClubImg;
    }

    public void setLeftClubImg(String leftClubImg) {
        this.leftClubImg = leftClubImg;
    }

    public String getRightClubId() {
        return rightClubId == null ? "" : rightClubId;
    }

    public void setRightClubId(String rightClubId) {
        this.rightClubId = rightClubId;
    }

    public String getRightTeamId() {
        return rightTeamId == null ? "" : rightTeamId;
    }

    public void setRightTeamId(String rightTeamId) {
        this.rightTeamId = rightTeamId;
    }

    public String getRightClubType() {
        return rightClubType == null ? "" : rightClubType;
    }

    public void setRightClubType(String rightClubType) {
        this.rightClubType = rightClubType;
    }

    public String getRightClubName() {
        return rightClubName == null ? "" : rightClubName;
    }

    public void setRightClubName(String rightClubName) {
        this.rightClubName = rightClubName;
    }

    public String getRightClubImg() {
        return rightClubImg == null ? "" : rightClubImg;
    }

    public void setRightClubImg(String rightClubImg) {
        this.rightClubImg = rightClubImg;
    }

    public String getLeftOrRight() {
        return leftOrRight == null ? "" : leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public String getAppOperationLogId() {
        return appOperationLogId == null ? "" : appOperationLogId;
    }

    public void setAppOperationLogId(String appOperationLogId) {
        this.appOperationLogId = appOperationLogId;
    }
}
