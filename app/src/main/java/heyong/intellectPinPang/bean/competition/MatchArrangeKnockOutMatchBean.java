package heyong.intellectPinPang.bean.competition;

import java.util.List;

/**
 * 淘汰赛
 */
public class MatchArrangeKnockOutMatchBean {

    /**
     * itemType : 2
     * format : 2
     * formTitle : 男子单打A
     * outList : [{"id":1,"idLeft":511486160083128336,"idRight":511215681036390416,"left1Name":"王洪福","left2Name":null,"right1Name":"茅十八","right2Name":null,"leftCount":2,"rightCount":0,"score":"2:0","winId":511215427033534480,"win1Name":"孟星魂","win2Name":null,"tableNum":null,"day":"2021-01-05 11:38:54","hour":"2021-01-05 11:38:54","club1Name":"12312","club2Name":"自由","leftPosition":null,"rightPosition":null,"status":null},{"id":2,"idLeft":511486160083128336,"idRight":511215681036390416,"left1Name":"王洪福","left2Name":null,"right1Name":"茅十八","right2Name":null,"leftCount":2,"rightCount":0,"score":"2:0","winId":510501774432908576,"win1Name":null,"win2Name":null,"tableNum":null,"day":"2021-01-05 11:38:57","hour":"2021-01-05 11:38:57","club1Name":"12312","club2Name":"自由","leftPosition":null,"rightPosition":null,"status":null}]
     */

    private String itemType;
    private String format;
    private String formTitle;
    private List<OutListBean> outList;

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getFormat() {
        return format == null ? "" : format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormTitle() {
        return formTitle == null ? "" : formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public List<OutListBean> getOutList() {
        return outList;
    }

    public void setOutList(List<OutListBean> outList) {
        this.outList = outList;
    }

    public static class OutListBean {
        /**
         * id : 1
         * idLeft : 511486160083128336
         * idRight : 511215681036390416
         * left1Name : 王洪福
         * left2Name : null
         * right1Name : 茅十八
         * right2Name : null
         * leftCount : 2
         * rightCount : 0
         * score : 2:0
         * winId : 511215427033534480
         * win1Name : 孟星魂
         * win2Name : null
         * tableNum : null
         * day : 2021-01-05 11:38:54
         * hour : 2021-01-05 11:38:54
         * club1Name : 12312
         * club2Name : 自由
         * leftPosition : null
         * rightPosition : null
         * status : null
         */

        private long id;
        private long idLeft;
        private long idRight;
        private String left1Name;
        private String matchId;
        private String left2Name;
        private String right1Name;
        private String right2Name;
        private int leftCount;
        private int rightCount;
        private String score;
        private long winId;
        private String win1Name;
        private String win2Name;
        private String tableNum;
        private String day;
        private String hour;
        private String club1Name;
        private String club1Id;
        private String club2Name;
        private String club2Id;
        private String winClubName;
        private String winClubId;
        private String leftPosition;
        private String rightPosition;
        private String status;

        public String getMatchId() {
            return matchId == null ? "" : matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getClub1Id() {
            return club1Id == null ? "" : club1Id;
        }

        public void setClub1Id(String club1Id) {
            this.club1Id = club1Id;
        }

        public String getClub2Id() {
            return club2Id == null ? "" : club2Id;
        }

        public void setClub2Id(String club2Id) {
            this.club2Id = club2Id;
        }

        public String getWinClubName() {
            return winClubName == null ? "" : winClubName;
        }

        public void setWinClubName(String winClubName) {
            this.winClubName = winClubName;
        }

        public String getWinClubId() {
            return winClubId == null ? "" : winClubId;
        }

        public void setWinClubId(String winClubId) {
            this.winClubId = winClubId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getIdLeft() {
            return idLeft;
        }

        public void setIdLeft(long idLeft) {
            this.idLeft = idLeft;
        }

        public long getIdRight() {
            return idRight;
        }

        public void setIdRight(long idRight) {
            this.idRight = idRight;
        }

        public String getLeft1Name() {
            return left1Name == null ? "" : left1Name;
        }

        public void setLeft1Name(String left1Name) {
            this.left1Name = left1Name;
        }

        public String getLeft2Name() {
            return left2Name == null ? "" : left2Name;
        }

        public void setLeft2Name(String left2Name) {
            this.left2Name = left2Name;
        }

        public String getRight1Name() {
            return right1Name == null ? "" : right1Name;
        }

        public void setRight1Name(String right1Name) {
            this.right1Name = right1Name;
        }

        public String getRight2Name() {
            return right2Name == null ? "" : right2Name;
        }

        public void setRight2Name(String right2Name) {
            this.right2Name = right2Name;
        }

        public int getLeftCount() {
            return leftCount;
        }

        public void setLeftCount(int leftCount) {
            this.leftCount = leftCount;
        }

        public int getRightCount() {
            return rightCount;
        }

        public void setRightCount(int rightCount) {
            this.rightCount = rightCount;
        }

        public String getScore() {
            return score == null ? "" : score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public long getWinId() {
            return winId;
        }

        public void setWinId(long winId) {
            this.winId = winId;
        }

        public String getWin1Name() {
            return win1Name == null ? "" : win1Name;
        }

        public void setWin1Name(String win1Name) {
            this.win1Name = win1Name;
        }

        public String getWin2Name() {
            return win2Name == null ? "" : win2Name;
        }

        public void setWin2Name(String win2Name) {
            this.win2Name = win2Name;
        }

        public String getTableNum() {
            return tableNum == null ? "" : tableNum;
        }

        public void setTableNum(String tableNum) {
            this.tableNum = tableNum;
        }

        public String getDay() {
            return day == null ? "" : day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getHour() {
            return hour == null ? "" : hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getClub1Name() {
            return club1Name == null ? "" : club1Name;
        }

        public void setClub1Name(String club1Name) {
            this.club1Name = club1Name;
        }

        public String getClub2Name() {
            return club2Name == null ? "" : club2Name;
        }

        public void setClub2Name(String club2Name) {
            this.club2Name = club2Name;
        }

        public String getLeftPosition() {
            return leftPosition == null ? "1" : leftPosition;
        }

        public void setLeftPosition(String leftPosition) {
            this.leftPosition = leftPosition;
        }

        public String getRightPosition() {
            return rightPosition == null ? "" : rightPosition;
        }

        public void setRightPosition(String rightPosition) {
            this.rightPosition = rightPosition;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
