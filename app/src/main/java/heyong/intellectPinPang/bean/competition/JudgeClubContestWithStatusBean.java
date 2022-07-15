package heyong.intellectPinPang.bean.competition;

public class JudgeClubContestWithStatusBean {


        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * contestStatus : null
         * xlClubContestArranges : null
         * contestType : null
         * xlClubContestTeams : null
         * id : 549592687839369856
         * clubId : 542051526933305984
         * contestTitle : 队内赛事
         * beginTime : 2021-02-25
         * endTiem : 2021-03-05
         * tableCount : 5
         * matchType : 1
         * matchMethod : 三局二胜
         * createUser : 541976244129979008
         * createTime : 2021-02-25 14:05:08
         * updateUser : null
         * updateTime : 2021-02-25 14:05:08
         * isDelete : 0
         * contest : null
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private String contestStatus;
        private String xlClubContestArranges;
        private String contestType;
        private String xlClubContestTeams;
        private long id;
        private long clubId;
        private String contestTitle;
        private String beginTime;
        private String endTiem;
        private int tableCount;
        private String matchType;
        private String matchMethod;
        private long createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String isDelete;
        private String contest;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy == null ? "" : orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getContestStatus() {
        return contestStatus == null ? "2" : contestStatus;
    }

    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    public String getXlClubContestArranges() {
        return xlClubContestArranges == null ? "" : xlClubContestArranges;
    }

    public void setXlClubContestArranges(String xlClubContestArranges) {
        this.xlClubContestArranges = xlClubContestArranges;
    }

    public String getContestType() {
        return contestType == null ? "" : contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getXlClubContestTeams() {
        return xlClubContestTeams == null ? "" : xlClubContestTeams;
    }

    public void setXlClubContestTeams(String xlClubContestTeams) {
        this.xlClubContestTeams = xlClubContestTeams;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
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

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getMatchType() {
        return matchType == null ? "1" : matchType;
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

    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser == null ? "" : updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime == null ? "" : updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete == null ? "" : isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getContest() {
        return contest == null ? "" : contest;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }
}
