package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class XlClubContestInfoBean {


        private List<ResultListDataBean> resultListData;
        private List<String> contestArrangeList;

        public List<ResultListDataBean> getResultListData() {
            return resultListData;
        }

        public void setResultListData(List<ResultListDataBean> resultListData) {
            this.resultListData = resultListData;
        }

        public List<String> getContestArrangeList() {
            return contestArrangeList;
        }

        public void setContestArrangeList(List<String> contestArrangeList) {
            this.contestArrangeList = contestArrangeList;
        }

        public static class ResultListDataBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * userName : 茅十八
             * arrangeList1 : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":0,"sessions":0,"leftWinCount":0,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"rightSuspend":0,"leftSuspend":0,"left1Name":null,"right1Name":null,"status":null,"waiver":0,"winTeamId":0,"id":null,"contestId":null,"left1Id":null,"right1Id":null,"left2Id":null,"rigth2Id":null,"teamLeftId":null,"teamRightId":null,"contestType":null,"leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":null,"tableNum":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":3,"sessions":3,"leftWinCount":3,"rightWinCount":0,"leftWavier":0,"rightWavier":0,"rightSuspend":0,"leftSuspend":0,"left1Name":"茅十八","right1Name":"张无忌","status":"1","waiver":0,"winTeamId":0,"id":514749497608802320,"contestId":514749497294229520,"left1Id":511215681036390416,"right1Id":511486160083128336,"left2Id":null,"rigth2Id":null,"teamLeftId":514749497449418768,"teamRightId":514749497449418768,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-21 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-21 08:00:00","isDelete":"0","tableNum":3},{"pageNo":1,"pageSize":10,"orderBy":"id desc","rounds":2,"sessions":2,"leftWinCount":0,"rightWinCount":3,"leftWavier":0,"rightWavier":0,"rightSuspend":0,"leftSuspend":0,"left1Name":"茅十八","right1Name":"柯南","status":"1","waiver":0,"winTeamId":0,"id":514749497579442192,"contestId":514749497294229520,"left1Id":511215681036390416,"right1Id":512936350908452880,"left2Id":null,"rigth2Id":null,"teamLeftId":514749497449418768,"teamRightId":514749497449418768,"contestType":"1","leftIntegral":null,"rughtIntegral":null,"winId1":null,"winId2":null,"createUser":511486160083128336,"createTime":"2020-11-21 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-21 08:00:00","isDelete":"0","tableNum":2}]
             * arrangeList2 : null
             * id : 514749497503944720
             * joinId : 511215681036390416
             * integral : 3
             * result1 : 1.0
             * result2 : 1.07
             * ranking : 2
             * matchId : 514749497294229520
             * teamId : 514749497449418768
             * createUser : 511486160083128336
             * createTime : 2020-11-21 08:00:00
             * updateUser : 511486160083128336
             * updateTime : 2020-11-21 08:00:00
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String userName;
            private Object arrangeList2;
            private long id;
            private long joinId;
            private int integral;
            private double result1;
            private double result2;
            private int ranking;
            private long matchId;
            private long teamId;
            private long createUser;
            private String createTime;
            private long updateUser;
            private String updateTime;
            private String isDelete;
            private List<ArrangeList1Bean> arrangeList1;

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
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public Object getArrangeList2() {
                return arrangeList2;
            }

            public void setArrangeList2(Object arrangeList2) {
                this.arrangeList2 = arrangeList2;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getJoinId() {
                return joinId;
            }

            public void setJoinId(long joinId) {
                this.joinId = joinId;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public double getResult1() {
                return result1;
            }

            public void setResult1(double result1) {
                this.result1 = result1;
            }

            public double getResult2() {
                return result2;
            }

            public void setResult2(double result2) {
                this.result2 = result2;
            }

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public long getTeamId() {
                return teamId;
            }

            public void setTeamId(long teamId) {
                this.teamId = teamId;
            }

            public long getCreateUser() {
                return createUser;
            }

            public void setCreateUser(long createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
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
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public List<ArrangeList1Bean> getArrangeList1() {
                return arrangeList1;
            }

            public void setArrangeList1(List<ArrangeList1Bean> arrangeList1) {
                this.arrangeList1 = arrangeList1;
            }

            public static class ArrangeList1Bean {
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
                 * rightSuspend : 0
                 * leftSuspend : 0
                 * left1Name : null
                 * right1Name : null
                 * status : null
                 * waiver : 0
                 * winTeamId : 0
                 * id : null
                 * contestId : null
                 * left1Id : null
                 * right1Id : null
                 * left2Id : null
                 * rigth2Id : null
                 * teamLeftId : null
                 * teamRightId : null
                 * contestType : null
                 * leftIntegral : null
                 * rughtIntegral : null
                 * winId1 : null
                 * winId2 : null
                 * createUser : null
                 * createTime : null
                 * updateUser : null
                 * updateTime : null
                 * isDelete : null
                 * tableNum : null
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
                private int rightSuspend;
                private int leftSuspend;
                private Object left1Name;
                private Object right1Name;
                private Object status;
                private int waiver;
                private int winTeamId;
                private Object id;
                private Object contestId;
                private Object left1Id;
                private Object right1Id;
                private Object left2Id;
                private Object rigth2Id;
                private Object teamLeftId;
                private Object teamRightId;
                private Object contestType;
                private Object leftIntegral;
                private Object rughtIntegral;
                private Object winId1;
                private Object winId2;
                private Object createUser;
                private Object createTime;
                private Object updateUser;
                private Object updateTime;
                private Object isDelete;
                private Object tableNum;

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
                    return orderBy;
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

                public int getRightSuspend() {
                    return rightSuspend;
                }

                public void setRightSuspend(int rightSuspend) {
                    this.rightSuspend = rightSuspend;
                }

                public int getLeftSuspend() {
                    return leftSuspend;
                }

                public void setLeftSuspend(int leftSuspend) {
                    this.leftSuspend = leftSuspend;
                }

                public Object getLeft1Name() {
                    return left1Name;
                }

                public void setLeft1Name(Object left1Name) {
                    this.left1Name = left1Name;
                }

                public Object getRight1Name() {
                    return right1Name;
                }

                public void setRight1Name(Object right1Name) {
                    this.right1Name = right1Name;
                }

                public Object getStatus() {
                    return status;
                }

                public void setStatus(Object status) {
                    this.status = status;
                }

                public int getWaiver() {
                    return waiver;
                }

                public void setWaiver(int waiver) {
                    this.waiver = waiver;
                }

                public int getWinTeamId() {
                    return winTeamId;
                }

                public void setWinTeamId(int winTeamId) {
                    this.winTeamId = winTeamId;
                }

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public Object getContestId() {
                    return contestId;
                }

                public void setContestId(Object contestId) {
                    this.contestId = contestId;
                }

                public Object getLeft1Id() {
                    return left1Id;
                }

                public void setLeft1Id(Object left1Id) {
                    this.left1Id = left1Id;
                }

                public Object getRight1Id() {
                    return right1Id;
                }

                public void setRight1Id(Object right1Id) {
                    this.right1Id = right1Id;
                }

                public Object getLeft2Id() {
                    return left2Id;
                }

                public void setLeft2Id(Object left2Id) {
                    this.left2Id = left2Id;
                }

                public Object getRigth2Id() {
                    return rigth2Id;
                }

                public void setRigth2Id(Object rigth2Id) {
                    this.rigth2Id = rigth2Id;
                }

                public Object getTeamLeftId() {
                    return teamLeftId;
                }

                public void setTeamLeftId(Object teamLeftId) {
                    this.teamLeftId = teamLeftId;
                }

                public Object getTeamRightId() {
                    return teamRightId;
                }

                public void setTeamRightId(Object teamRightId) {
                    this.teamRightId = teamRightId;
                }

                public Object getContestType() {
                    return contestType;
                }

                public void setContestType(Object contestType) {
                    this.contestType = contestType;
                }

                public Object getLeftIntegral() {
                    return leftIntegral;
                }

                public void setLeftIntegral(Object leftIntegral) {
                    this.leftIntegral = leftIntegral;
                }

                public Object getRughtIntegral() {
                    return rughtIntegral;
                }

                public void setRughtIntegral(Object rughtIntegral) {
                    this.rughtIntegral = rughtIntegral;
                }

                public Object getWinId1() {
                    return winId1;
                }

                public void setWinId1(Object winId1) {
                    this.winId1 = winId1;
                }

                public Object getWinId2() {
                    return winId2;
                }

                public void setWinId2(Object winId2) {
                    this.winId2 = winId2;
                }

                public Object getCreateUser() {
                    return createUser;
                }

                public void setCreateUser(Object createUser) {
                    this.createUser = createUser;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }

                public Object getUpdateUser() {
                    return updateUser;
                }

                public void setUpdateUser(Object updateUser) {
                    this.updateUser = updateUser;
                }

                public Object getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(Object updateTime) {
                    this.updateTime = updateTime;
                }

                public Object getIsDelete() {
                    return isDelete;
                }

                public void setIsDelete(Object isDelete) {
                    this.isDelete = isDelete;
                }

                public Object getTableNum() {
                    return tableNum;
                }

                public void setTableNum(Object tableNum) {
                    this.tableNum = tableNum;
                }
            }
        }

}
