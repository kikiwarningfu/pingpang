package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ArrangeTeamContestBean {


        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * winTeamId : 0
         * id : 513707520901550096
         * contestId : 513707520385650704
         * left1Id : 513707520704417809
         * right1Id : 513707520821858321
         * left2Id : null
         * rigth2Id : null
         * teamLeftId : 513707520704417808
         * teamRightId : 513707520821858320
         * contestType : 1
         * leftIntegral : null
         * rughtIntegral : null
         * winId1 : null
         * winId2 : null
         * createUser : 511486160083128336
         * createTime : 2020-11-18 08:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-18 08:00:00
         * isDelete : 0
         * tableNum : 1
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private long winTeamId;
        private long id;
        private long contestId;
        private String left1Id;
        private String right1Id;
        private String left2Id;
        private String rigth2Id;
        private String teamLeftId;
        private String teamRightId;
        private String contestType;
        private String leftIntegral;
        private String rughtIntegral;
        private String winId1;
        private String winId2;
        private long createUser;
        private String createTime;
        private long updateUser;
        private String updateTime;
        private String isDelete;
        private int tableNum;

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

    public long getWinTeamId() {
        return winTeamId;
    }

    public void setWinTeamId(long winTeamId) {
        this.winTeamId = winTeamId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public String getLeft1Id() {
        return left1Id == null ? "" : left1Id;
    }

    public void setLeft1Id(String left1Id) {
        this.left1Id = left1Id;
    }

    public String getRight1Id() {
        return right1Id == null ? "" : right1Id;
    }

    public void setRight1Id(String right1Id) {
        this.right1Id = right1Id;
    }

    public String getLeft2Id() {
        return left2Id == null ? "" : left2Id;
    }

    public void setLeft2Id(String left2Id) {
        this.left2Id = left2Id;
    }

    public String getRigth2Id() {
        return rigth2Id == null ? "" : rigth2Id;
    }

    public void setRigth2Id(String rigth2Id) {
        this.rigth2Id = rigth2Id;
    }

    public String getTeamLeftId() {
        return teamLeftId == null ? "" : teamLeftId;
    }

    public void setTeamLeftId(String teamLeftId) {
        this.teamLeftId = teamLeftId;
    }

    public String getTeamRightId() {
        return teamRightId == null ? "" : teamRightId;
    }

    public void setTeamRightId(String teamRightId) {
        this.teamRightId = teamRightId;
    }

    public String getContestType() {
        return contestType == null ? "" : contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getLeftIntegral() {
        return leftIntegral == null ? "" : leftIntegral;
    }

    public void setLeftIntegral(String leftIntegral) {
        this.leftIntegral = leftIntegral;
    }

    public String getRughtIntegral() {
        return rughtIntegral == null ? "" : rughtIntegral;
    }

    public void setRughtIntegral(String rughtIntegral) {
        this.rughtIntegral = rughtIntegral;
    }

    public String getWinId1() {
        return winId1 == null ? "" : winId1;
    }

    public void setWinId1(String winId1) {
        this.winId1 = winId1;
    }

    public String getWinId2() {
        return winId2 == null ? "" : winId2;
    }

    public void setWinId2(String winId2) {
        this.winId2 = winId2;
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

    public long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(long updateUser) {
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

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}
