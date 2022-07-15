package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MatchArrangeRoundBean {


    /**
     * name : 循环赛
     * itemType : 2
     * arrangeData : [{"clubName":"12312","playerName":"王洪福","pageNo":1,"pageSize":10,"orderBy":"id desc","arrangeList":[{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:54","sexType":null,"id":1,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":514038703090864144,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":511215427033534480,"ranking":1,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:54","endTime":"2021-01-05 11:39:01","status":null,"waiver":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:57","sexType":null,"id":2,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":514038703090864144,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":510501774432908576,"ranking":3,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:57","endTime":"2021-01-05 11:39:03","status":null,"waiver":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:59","sexType":null,"id":3,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":1,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":510501774432908576,"ranking":1,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:59","endTime":"2021-01-05 11:39:04","status":null,"waiver":null}],"id":1,"joinid":511486160083128336,"result1":null,"result2":null,"ranking":1,"matchid":512317538144129040,"itemid":514038703090864144,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","clubId":512196047566049296,"isLastSocre":"1","groupNum":"1"}]
     */

    private String name;
    private String itemType;
    private List<ArrangeDataBean> arrangeData;
    private String format;
    private String formTitle;


    public static class ArrangeDataBean {
        /**
         * clubName : 12312
         * playerName : 王洪福
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * arrangeList : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:54","sexType":null,"id":1,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":514038703090864144,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":511215427033534480,"ranking":1,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:54","endTime":"2021-01-05 11:39:01","status":null,"waiver":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:57","sexType":null,"id":2,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":514038703090864144,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":510501774432908576,"ranking":3,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:57","endTime":"2021-01-05 11:39:03","status":null,"waiver":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","player1Name":"王洪福","player2Name":"茅十八","day":"05","time":"11:38:59","sexType":null,"id":3,"idLeft":511486160083128336,"idRight":511215681036390416,"matchId":512317538144129040,"projectItemId":1,"projectType":"1","stage":null,"arrangeNum":"1","rightIntegral":null,"leftIntegral":null,"winId":510501774432908576,"ranking":1,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":"0","tableNum":null,"clubIdLeft":512196047566049296,"clubIdRight":512196432695431184,"winClubId":510501774432908576,"refereeId":null,"refereeMark":null,"stageMethod":null,"isLastStage":null,"beginTime":"2021-01-05 11:38:59","endTime":"2021-01-05 11:39:04","status":null,"waiver":null}]
         * id : 1
         * joinid : 511486160083128336
         * result1 : null
         * result2 : null
         * ranking : 1
         * matchid : 512317538144129040
         * itemid : 514038703090864144
         * createUser : null
         * createTime : null
         * updateUser : null
         * updateTime : null
         * isDelete : 0
         * clubId : 512196047566049296
         * isLastSocre : 1
         * groupNum : 1
         */

        private String clubName;
        private String player1Name;
        private String player2Name;
        private int pageNo;
        private int pageSize;
        private String orderBy;
        private long id;
        private long joinid;
        private String result1;
        private String result2;
        private String ranking;
        private long matchid;
        private long itemid;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String isDelete;
        private long clubId;
        private String isLastSocre;
        private String groupNum;
        private List<ArrangeListBean> arrangeList;
        private String integral;
        private int qianNum;

        public int getQianNum() {
            return qianNum;
        }

        public void setQianNum(int qianNum) {
            this.qianNum = qianNum;
        }

        public String getIntegral() {
            return integral == null ? "" : integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getClubName() {
            return clubName == null ? "" : clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

        public String getPlayer1Name() {
            return player1Name == null ? "" : player1Name;
        }

        public void setPlayer1Name(String player1Name) {
            this.player1Name = player1Name;
        }

        public String getPlayer2Name() {
            return player2Name == null ? "" : player2Name;
        }

        public void setPlayer2Name(String player2Name) {
            this.player2Name = player2Name;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getJoinid() {
            return joinid;
        }

        public void setJoinid(long joinid) {
            this.joinid = joinid;
        }

        public String getResult1() {
            return result1 == null ? "" : result1;
        }

        public void setResult1(String result1) {
            this.result1 = result1;
        }

        public String getResult2() {
            return result2 == null ? "" : result2;
        }

        public void setResult2(String result2) {
            this.result2 = result2;
        }

        public String getRanking() {
            return ranking == null ? "" : ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public long getMatchid() {
            return matchid;
        }

        public void setMatchid(long matchid) {
            this.matchid = matchid;
        }

        public long getItemid() {
            return itemid;
        }

        public void setItemid(long itemid) {
            this.itemid = itemid;
        }

        public String getCreateUser() {
            return createUser == null ? "" : createUser;
        }

        public void setCreateUser(String createUser) {
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

        public long getClubId() {
            return clubId;
        }

        public void setClubId(long clubId) {
            this.clubId = clubId;
        }

        public String getIsLastSocre() {
            return isLastSocre == null ? "" : isLastSocre;
        }

        public void setIsLastSocre(String isLastSocre) {
            this.isLastSocre = isLastSocre;
        }

        public String getGroupNum() {
            return groupNum == null ? "" : groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public List<ArrangeListBean> getArrangeList() {
            return arrangeList;
        }

        public void setArrangeList(List<ArrangeListBean> arrangeList) {
            this.arrangeList = arrangeList;
        }

        public static class ArrangeListBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * player1Name : 王洪福
             * player2Name : 茅十八
             * day : 05
             * time : 11:38:54
             * sexType : null
             * id : 1
             * idLeft : 511486160083128336
             * idRight : 511215681036390416
             * matchId : 512317538144129040
             * projectItemId : 514038703090864144
             * projectType : 1
             * stage : null
             * arrangeNum : 1
             * rightIntegral : null
             * leftIntegral : null
             * winId : 511215427033534480
             * ranking : 1
             * createUser : null
             * createTime : null
             * updateUser : null
             * updateTime : null
             * isDelete : 0
             * tableNum : null
             * clubIdLeft : 512196047566049296
             * clubIdRight : 512196432695431184
             * winClubId : 510501774432908576
             * refereeId : null
             * refereeMark : null
             * stageMethod : null
             * isLastStage : null
             * beginTime : 2021-01-05 11:38:54
             * endTime : 2021-01-05 11:39:01
             * status : null
             * waiver : null
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String leftWinCount;
            private String rightWinCount;
            private String leftWaiver;
            private String rightWaiver;
            private String player1Name;
            private String player2Name;
            private String leftPosition;
            private String rightPosition;
            private String day;
            private String time;
            private String sexType;
            private long id;
            private long idLeft;
            private long idRight;
            private long matchId;
            private long projectItemId;
            private String projectType;
            private String stage;
            private String arrangeNum;
            private String rightIntegral;
            private String leftIntegral;
            private long winId;
            private int ranking;
            private String createUser;
            private String createTime;
            private String updateUser;
            private String updateTime;
            private String isDelete;
            private String tableNum;
            private long clubIdLeft;
            private long clubIdRight;
            private long winClubId;
            private String refereeId;
            private String refereeMark;
            private String stageMethod;
            private String isLastStage;
            private String beginTime;
            private String endTime;
            private String status;
            private String waiver;

            public String getLeftPosition() {
                return leftPosition == null ? "" : leftPosition;
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

            public String getLeftWinCount() {
                return leftWinCount == null ? "0" : leftWinCount;
            }

            public void setLeftWinCount(String leftWinCount) {
                this.leftWinCount = leftWinCount;
            }

            public String getRightWinCount() {
                return rightWinCount == null ? "0" : rightWinCount;
            }

            public void setRightWinCount(String rightWinCount) {
                this.rightWinCount = rightWinCount;
            }

            public String getLeftWaiver() {
                return leftWaiver == null ? "0" : leftWaiver;
            }

            public void setLeftWaiver(String leftWaiver) {
                this.leftWaiver = leftWaiver;
            }

            public String getRightWaiver() {
                return rightWaiver == null ? "0" : rightWaiver;
            }

            public void setRightWaiver(String rightWaiver) {
                this.rightWaiver = rightWaiver;
            }

            public String getPlayer1Name() {
                return player1Name == null ? "" : player1Name;
            }

            public void setPlayer1Name(String player1Name) {
                this.player1Name = player1Name;
            }

            public String getPlayer2Name() {
                return player2Name == null ? "" : player2Name;
            }

            public void setPlayer2Name(String player2Name) {
                this.player2Name = player2Name;
            }

            public String getDay() {
                return day == null ? "" : day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getTime() {
                return time == null ? "" : time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSexType() {
                return sexType == null ? "" : sexType;
            }

            public void setSexType(String sexType) {
                this.sexType = sexType;
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

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public long getProjectItemId() {
                return projectItemId;
            }

            public void setProjectItemId(long projectItemId) {
                this.projectItemId = projectItemId;
            }

            public String getProjectType() {
                return projectType == null ? "" : projectType;
            }

            public void setProjectType(String projectType) {
                this.projectType = projectType;
            }

            public String getStage() {
                return stage == null ? "" : stage;
            }

            public void setStage(String stage) {
                this.stage = stage;
            }

            public String getArrangeNum() {
                return arrangeNum == null ? "" : arrangeNum;
            }

            public void setArrangeNum(String arrangeNum) {
                this.arrangeNum = arrangeNum;
            }

            public String getRightIntegral() {
                return rightIntegral == null ? "" : rightIntegral;
            }

            public void setRightIntegral(String rightIntegral) {
                this.rightIntegral = rightIntegral;
            }

            public String getLeftIntegral() {
                return leftIntegral == null ? "" : leftIntegral;
            }

            public void setLeftIntegral(String leftIntegral) {
                this.leftIntegral = leftIntegral;
            }

            public long getWinId() {
                return winId;
            }

            public void setWinId(long winId) {
                this.winId = winId;
            }

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public String getCreateUser() {
                return createUser == null ? "" : createUser;
            }

            public void setCreateUser(String createUser) {
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

            public String getTableNum() {
                return tableNum == null ? "" : tableNum;
            }

            public void setTableNum(String tableNum) {
                this.tableNum = tableNum;
            }

            public long getClubIdLeft() {
                return clubIdLeft;
            }

            public void setClubIdLeft(long clubIdLeft) {
                this.clubIdLeft = clubIdLeft;
            }

            public long getClubIdRight() {
                return clubIdRight;
            }

            public void setClubIdRight(long clubIdRight) {
                this.clubIdRight = clubIdRight;
            }

            public long getWinClubId() {
                return winClubId;
            }

            public void setWinClubId(long winClubId) {
                this.winClubId = winClubId;
            }

            public String getRefereeId() {
                return refereeId == null ? "" : refereeId;
            }

            public void setRefereeId(String refereeId) {
                this.refereeId = refereeId;
            }

            public String getRefereeMark() {
                return refereeMark == null ? "" : refereeMark;
            }

            public void setRefereeMark(String refereeMark) {
                this.refereeMark = refereeMark;
            }

            public String getStageMethod() {
                return stageMethod == null ? "" : stageMethod;
            }

            public void setStageMethod(String stageMethod) {
                this.stageMethod = stageMethod;
            }

            public String getIsLastStage() {
                return isLastStage == null ? "" : isLastStage;
            }

            public void setIsLastStage(String isLastStage) {
                this.isLastStage = isLastStage;
            }

            public String getBeginTime() {
                return beginTime == null ? "" : beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getEndTime() {
                return endTime == null ? "" : endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getStatus() {
                return status == null ? "" : status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getWaiver() {
                return waiver == null ? "" : waiver;
            }

            public void setWaiver(String waiver) {
                this.waiver = waiver;
            }
        }
    }

    public String getFormTitle() {
        return formTitle == null ? "" : formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormat() {
        return format == null ? "1" : format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType == null ? "0" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<ArrangeDataBean> getArrangeData() {
        return arrangeData;
    }

    public void setArrangeData(List<ArrangeDataBean> arrangeData) {
        this.arrangeData = arrangeData;
    }
}
