package heyong.intellectPinPang.bean.competition;

public class FillInMatchListBean {


        /**
         * arrangeId : 1
         * matchId : 512317538144129040
         * title : 男子单打循环赛
         * angle : 136
         * leftClubId : null
         * leftTeamId : null
         * leftClubType : null
         * leftClubName : null
         * leftClubImg : null
         * rightClubId : 514049889534513168
         * rightTeamId : null
         * rightClubType : null
         * rightClubName : null
         * rightClubImg : http://images.xlttsports.com/20201119120745029662.png
         * leftOrRight : 2
         * appOperationLogId : 534761089414877616
         */

        private long arrangeId;
        private long matchId;
        private String title;
        private String angle;
        private String leftClubId;
        private String leftTeamId;
        private String leftClubType;
        private String leftClubName;
        private String leftClubImg;
        private long rightClubId;
        private String rightTeamId;
        private String rightClubType;
        private String rightClubName;
        private String rightClubImg;
        private String leftOrRight;
        private long appOperationLogId;

    public long getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(long arrangeId) {
        this.arrangeId = arrangeId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAngle() {
        return angle == null ? "1" : angle;
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
        return leftClubType == null ? "1" : leftClubType;
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

    public long getRightClubId() {
        return rightClubId;
    }

    public void setRightClubId(long rightClubId) {
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
        return leftOrRight == null ? "1" : leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public long getAppOperationLogId() {
        return appOperationLogId;
    }

    public void setAppOperationLogId(long appOperationLogId) {
        this.appOperationLogId = appOperationLogId;
    }
}
