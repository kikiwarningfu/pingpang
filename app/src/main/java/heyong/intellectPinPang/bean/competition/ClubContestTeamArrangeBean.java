package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class ClubContestTeamArrangeBean {


    /**
     * contestArranges : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":515564111917518864,"contestId":515564111816855568,"left1Id":515564111879770129,"right1Id":0,"left2Id":null,"rigth2Id":null,"teamLeftId":0,"teamRightId":0,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","tableNum":0},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":515564111930101776,"contestId":515564111816855568,"left1Id":515564111888158736,"right1Id":0,"left2Id":null,"rigth2Id":null,"teamLeftId":0,"teamRightId":0,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","tableNum":0},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":515564111942684688,"contestId":515564111816855568,"left1Id":515564111879770129,"right1Id":0,"left2Id":null,"rigth2Id":null,"teamLeftId":0,"teamRightId":0,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","tableNum":0},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":515564111955267600,"contestId":515564111816855568,"left1Id":515564111888158736,"right1Id":0,"left2Id":null,"rigth2Id":null,"teamLeftId":0,"teamRightId":0,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","tableNum":0},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":515564111980433424,"contestId":515564111816855568,"left1Id":515564111900741648,"right1Id":0,"left2Id":null,"rigth2Id":null,"teamLeftId":0,"teamRightId":0,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","tableNum":0}]
     * contestStatus : 1
     * contestTitle : 短视的
     * clubContestTeams : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","winCount":0,"angle":0,"contestTeamMembers":null,"id":515564111837827088,"teamNum":"1","clubContestInfoId":515564111816855568,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","teamType":"1"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","winCount":0,"angle":0,"contestTeamMembers":null,"id":515564111879770128,"teamNum":"2","clubContestInfoId":515564111816855568,"createUser":511486160083128336,"createTime":"2020-11-23 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-23 08:00:00","isDelete":"0","teamType":"2"}]
     */

    private String contestStatus;
    private String contestTitle;
    private List<ContestArrangesBean> contestArranges;
    private List<ClubContestTeamsBean> clubContestTeams;

    public String getContestStatus() {
        return contestStatus == null ? "" : contestStatus;
    }

    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    public String getContestTitle() {
        return contestTitle == null ? "" : contestTitle;
    }

    public void setContestTitle(String contestTitle) {
        this.contestTitle = contestTitle;
    }

    public List<ContestArrangesBean> getContestArranges() {
        if (contestArranges == null) {
            return new ArrayList<>();
        }
        return contestArranges;
    }

    public void setContestArranges(List<ContestArrangesBean> contestArranges) {
        this.contestArranges = contestArranges;
    }

    public List<ClubContestTeamsBean> getClubContestTeams() {
        if (clubContestTeams == null) {
            return new ArrayList<>();
        }
        return clubContestTeams;
    }

    public void setClubContestTeams(List<ClubContestTeamsBean> clubContestTeams) {
        this.clubContestTeams = clubContestTeams;
    }

    public static class ContestArrangesBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * rounds : 0
         * sessions : 0
         * leftWinCount : 0
         * rightWinCount : 0
         * leftWavier : 0
         * rightWavier : 0
         * left1Name : null
         * right1Name : null
         * status : null
         * waiver : 0
         * winTeamId : 0
         * id : 515564111917518864
         * contestId : 515564111816855568
         * left1Id : 515564111879770129
         * right1Id : 0
         * left2Id : null
         * rigth2Id : null
         * teamLeftId : 0
         * teamRightId : 0
         * contestType : 1
         * leftIntegral : null
         * rughtIntegral : null
         * winId1 : null
         * winId2 : null
         * createUser : 511486160083128336
         * createTime : 2020-11-23 08:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-23 08:00:00
         * isDelete : 0
         * tableNum : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private int rounds;
        private int sessions;
        private int leftWinCount;
        private int rightWinCount;
        private int leftWavier;
        private int rightWavier;
        private String left1Name;
        private String left2Name;
        private String right1Name;
        private String right2Name;
        private String status;
        private int waiver;
        private long winTeamId;
        private long id;
        private long contestId;
        private String left1Id;
        private String right1Id;
        private String left2Id;
        private String rigth2Id;
        private int teamLeftId;
        private int teamRightId;
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

        public String getLeft2Name() {
            return left2Name == null ? "" : left2Name;
        }

        public void setLeft2Name(String left2Name) {
            this.left2Name = left2Name;
        }

        public String getRight2Name() {
            return right2Name == null ? "" : right2Name;
        }

        public void setRight2Name(String right2Name) {
            this.right2Name = right2Name;
        }

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

        public int getRounds() {
            return rounds;
        }

        public void setRounds(int rounds) {
            this.rounds = rounds;
        }

        public int getSessions() {
            return sessions;
        }

        public void setSessions(int sessions) {
            this.sessions = sessions;
        }

        public int getLeftWinCount() {
            return leftWinCount;
        }

        public void setLeftWinCount(int leftWinCount) {
            this.leftWinCount = leftWinCount;
        }

        public int getRightWinCount() {
            return rightWinCount;
        }

        public void setRightWinCount(int rightWinCount) {
            this.rightWinCount = rightWinCount;
        }

        public int getLeftWavier() {
            return leftWavier;
        }

        public void setLeftWavier(int leftWavier) {
            this.leftWavier = leftWavier;
        }

        public int getRightWavier() {
            return rightWavier;
        }

        public void setRightWavier(int rightWavier) {
            this.rightWavier = rightWavier;
        }

        public String getLeft1Name() {
            return left1Name == null ? "" : left1Name;
        }

        public void setLeft1Name(String left1Name) {
            this.left1Name = left1Name;
        }

        public String getRight1Name() {
            return right1Name == null ? "" : right1Name;
        }

        public void setRight1Name(String right1Name) {
            this.right1Name = right1Name;
        }

        public String getStatus() {
            return status == null ? "0" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getWaiver() {
            return waiver;
        }

        public void setWaiver(int waiver) {
            this.waiver = waiver;
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

        public int getTeamLeftId() {
            return teamLeftId;
        }

        public void setTeamLeftId(int teamLeftId) {
            this.teamLeftId = teamLeftId;
        }

        public int getTeamRightId() {
            return teamRightId;
        }

        public void setTeamRightId(int teamRightId) {
            this.teamRightId = teamRightId;
        }

        public String getContestType() {
            return contestType == null ? "1" : contestType;
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

    public static class ClubContestTeamsBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * winCount : 0
         * angle : 0
         * contestTeamMembers : null
         * id : 515564111837827088
         * teamNum : 1
         * clubContestInfoId : 515564111816855568
         * createUser : 511486160083128336
         * createTime : 2020-11-23 08:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-23 08:00:00
         * isDelete : 0
         * teamType : 1
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private int winCount;
        private int angle;
        private String contestTeamMembers;
        private long id;
        private String teamNum;
        private long clubContestInfoId;
        private long createUser;
        private String createTime;
        private long updateUser;
        private String updateTime;
        private String isDelete;
        private String teamType;

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

        public int getWinCount() {
            return winCount;
        }

        public void setWinCount(int winCount) {
            this.winCount = winCount;
        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }

        public String getContestTeamMembers() {
            return contestTeamMembers == null ? "" : contestTeamMembers;
        }

        public void setContestTeamMembers(String contestTeamMembers) {
            this.contestTeamMembers = contestTeamMembers;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTeamNum() {
            return teamNum == null ? "" : teamNum;
        }

        public void setTeamNum(String teamNum) {
            this.teamNum = teamNum;
        }

        public long getClubContestInfoId() {
            return clubContestInfoId;
        }

        public void setClubContestInfoId(long clubContestInfoId) {
            this.clubContestInfoId = clubContestInfoId;
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

        public String getTeamType() {
            return teamType == null ? "" : teamType;
        }

        public void setTeamType(String teamType) {
            this.teamType = teamType;
        }
    }

}
