package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateXlClubContestInfoBean implements Serializable{
    /**
     * clubId : 512196432695431184
     * contestTitle : 2020冬季训练赛
     * beginTime : 2021-12-15 00:00:00
     * endTiem : 2021-12-15 00:00:00
     * tableCount : 8
     * matchType : 2
     * matchMethod : 五局三胜
     * contest : 1
     * xlClubContestTeams : [{"teamNum":"1","contestTeamMembers":[{"userId":"511215427033534480"},{"userId":"511887748295594000"},{"userId":"511922527137206288"}]},{"teamNum":"2","contestTeamMembers":[{"userId":"511486160083128336"},{"userId":"511215681036390416"},{"userId":"512936350908452880"}]}]
     * xlClubContestArranges : [{"contestType":"1"},{"contestType":"1"},{"contestType":"1"},{"contestType":"1"},{"contestType":"1"}]
     */

    private String clubId;
    private String contestTitle;
    private String beginTime;
    private String endTiem;
    private String tableCount = "8";
    private String matchType;
    private String matchMethod;
    private String contest;
    private List<XlClubContestTeamsBean> xlClubContestTeams;
    private List<XlClubContestArrangesBean> xlClubContestArranges;

    public String getClubId() {
        return clubId == null ? "" : clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getContestTitle() {
        return contestTitle == null ? "" : contestTitle;
    }

    public void setContestTitle(String contestTitle) {
        this.contestTitle = contestTitle;
    }

    public String getBeginTime() {
        return beginTime == null ? "" : beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTiem() {
        return endTiem == null ? "" : endTiem;
    }

    public void setEndTiem(String endTiem) {
        this.endTiem = endTiem;
    }

    public String getTableCount() {
        return tableCount == null ? "" : tableCount;
    }

    public void setTableCount(String tableCount) {
        this.tableCount = tableCount;
    }

    public String getMatchType() {
        return matchType == null ? "" : matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchMethod() {
        return matchMethod == null ? "" : matchMethod;
    }

    public void setMatchMethod(String matchMethod) {
        this.matchMethod = matchMethod;
    }

    public String getContest() {
        return contest == null ? "" : contest;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }

    public List<XlClubContestTeamsBean> getXlClubContestTeams() {
        if (xlClubContestTeams == null) {
            return new ArrayList<>();
        }
        return xlClubContestTeams;
    }

    public void setXlClubContestTeams(List<XlClubContestTeamsBean> xlClubContestTeams) {
        this.xlClubContestTeams = xlClubContestTeams;
    }

    public List<XlClubContestArrangesBean> getXlClubContestArranges() {
        if (xlClubContestArranges == null) {
            return new ArrayList<>();
        }
        return xlClubContestArranges;
    }

    public void setXlClubContestArranges(List<XlClubContestArrangesBean> xlClubContestArranges) {
        this.xlClubContestArranges = xlClubContestArranges;
    }

    public static class XlClubContestTeamsBean implements Serializable{
        /**
         * teamNum : 1
         * contestTeamMembers : [{"userId":"511215427033534480"},{"userId":"511887748295594000"},{"userId":"511922527137206288"}]
         */

        private String teamNum;
        private List<ContestTeamMembersBean> contestTeamMembers;

        public String getTeamNum() {
            return teamNum;
        }

        public void setTeamNum(String teamNum) {
            this.teamNum = teamNum;
        }

        public List<ContestTeamMembersBean> getContestTeamMembers() {
            return contestTeamMembers;
        }

        public void setContestTeamMembers(List<ContestTeamMembersBean> contestTeamMembers) {
            this.contestTeamMembers = contestTeamMembers;
        }

        public static class ContestTeamMembersBean implements Serializable {
            /**
             * userId : 511215427033534480
             */

            private String userId;
            private String userName;
            private boolean isClose;

            public boolean isClose() {
                return isClose;
            }

            public void setClose(boolean close) {
                isClose = close;
            }

            public String getUserName() {
                return userName == null ? "" : userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }

    public static class XlClubContestArrangesBean {
        /**
         * contestType : 1
         */

        private String contestType;

        public String getContestType() {
            return contestType;
        }

        public void setContestType(String contestType) {
            this.contestType = contestType;
        }
    }
}
