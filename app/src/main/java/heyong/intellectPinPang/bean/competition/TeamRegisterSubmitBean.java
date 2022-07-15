package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class TeamRegisterSubmitBean implements MultiItemEntity {

    private int cardType;
    public static final int TEAM_REGISTER_LEADER = 0;

    public static final int TEAM_REGISTER_REFEREE = 1;
    public static final int TEAM_REGISTER_TEAM = 2;
    public static final int TEAM_REGISTER_SINGLE = 3;
    public static final int TEAM_REGISTER_DOUBLE = 4;
    public static final int TEAM_REGISTER_HUNSHUANG = 5;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int getItemType() {
        return cardType;
    }

    public TeamRegisterSubmitBean(int cardType) {
        this.cardType = cardType;
    }

    /**
     * matchTitle : 北京二级运动员晋级赛
     * beginTime : 2021-12-16
     * endTime : 2021-12-25
     * referee : [{"userId":511486160083128336,"userName":"王洪福","sex":"1"}]
     * leader : {"userId":511486160083128336,"userName":"王洪福","sex":"1"}
     * players : [{"itemId":513767530885058576,"type":"3","itemTitle":"混双","teams":[{"player":[{"userId":511215681036390416,"userName":"茅十八","sex":"2"}],"id":1,"teamTitle":"战狼队"}]}]
     * teamFree : 0
     * itemId : 0
     * type : null
     * itemTitle : null
     * name : null
     * id : 525702871824880048
     * matchId : 512317538144129040
     * enrollId : 511486160083128336
     * enrollTypet : 2
     * enrollFree : 0
     * personCount : null
     * needEat : 0
     * eatFree : 0
     * eatCount : 0
     * allFree : 1
     * freeType : 3
     * freeStatus : 1
     * createUser : 511486160083128336
     * createTime : 2020-12-21 15:55:32
     * updateUser : 511486160083128336
     * updateTime : 2020-12-21 15:55:32
     * isDelete : 0
     */

    private String matchTitle;
    private String beginTime;
    private String endTime;
    private LeaderBean leader;
    private int teamFree;
    private int itemId;
    private String type;
    private String itemTitle;
    private String name;
    private long id;
    private long matchId;
    private long enrollId;
    private String enrollTypet;
    private int enrollFree;
    private String personCount;
    private String needEat;
    private int eatFree;
    private int eatCount;
    private int allFree;
    private String freeType;
    private String freeStatus;
    private long createUser;
    private String createTime;
    private long updateUser;
    private String updateTime;
    private String isDelete;
    private List<RefereeBean> referee;
    private List<PlayersBean> players;

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
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

    public LeaderBean getLeader() {
        return leader;
    }

    public void setLeader(LeaderBean leader) {
        this.leader = leader;
    }

    public int getTeamFree() {
        return teamFree;
    }

    public void setTeamFree(int teamFree) {
        this.teamFree = teamFree;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemTitle() {
        return itemTitle == null ? "" : itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(long enrollId) {
        this.enrollId = enrollId;
    }

    public String getEnrollTypet() {
        return enrollTypet == null ? "" : enrollTypet;
    }

    public void setEnrollTypet(String enrollTypet) {
        this.enrollTypet = enrollTypet;
    }

    public int getEnrollFree() {
        return enrollFree;
    }

    public void setEnrollFree(int enrollFree) {
        this.enrollFree = enrollFree;
    }

    public String getPersonCount() {
        return personCount == null ? "" : personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getNeedEat() {
        return needEat == null ? "" : needEat;
    }

    public void setNeedEat(String needEat) {
        this.needEat = needEat;
    }

    public int getEatFree() {
        return eatFree;
    }

    public void setEatFree(int eatFree) {
        this.eatFree = eatFree;
    }

    public int getEatCount() {
        return eatCount;
    }

    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }

    public int getAllFree() {
        return allFree;
    }

    public void setAllFree(int allFree) {
        this.allFree = allFree;
    }

    public String getFreeType() {
        return freeType == null ? "" : freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public String getFreeStatus() {
        return freeStatus == null ? "" : freeStatus;
    }

    public void setFreeStatus(String freeStatus) {
        this.freeStatus = freeStatus;
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

    public List<RefereeBean> getReferee() {
        return referee;
    }

    public void setReferee(List<RefereeBean> referee) {
        this.referee = referee;
    }

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class LeaderBean {
        /**
         * userId : 511486160083128336
         * userName : 王洪福
         * sex : 1
         */

        private long userId;
        private String userName;
        private String sex;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static class RefereeBean {
        /**
         * userId : 511486160083128336
         * userName : 王洪福
         * sex : 1
         */

        private long userId;
        private String userName;
        private String sex;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static class PlayersBean {
        /**
         * itemId : 513767530885058576
         * type : 3
         * itemTitle : 混双
         * teams : [{"player":[{"userId":511215681036390416,"userName":"茅十八","sex":"2"}],"id":1,"teamTitle":"战狼队"}]
         */

        private long itemId;
        private String type;
        private String itemTitle;
        private List<TeamsBean> teams;

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public List<TeamsBean> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamsBean> teams) {
            this.teams = teams;
        }

        public static class TeamsBean {
            /**
             * player : [{"userId":511215681036390416,"userName":"茅十八","sex":"2"}]
             * id : 1
             * teamTitle : 战狼队
             */

            private long id;
            private String teamTitle;
            private List<PlayerBean> player;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTeamTitle() {
                return teamTitle;
            }

            public void setTeamTitle(String teamTitle) {
                this.teamTitle = teamTitle;
            }

            public List<PlayerBean> getPlayer() {
                return player;
            }

            public void setPlayer(List<PlayerBean> player) {
                this.player = player;
            }

            public static class PlayerBean {
                /**
                 * userId : 511215681036390416
                 * userName : 茅十八
                 * sex : 2
                 */

                private long userId;
                private String userName;
                private String sex;

                public long getUserId() {
                    return userId;
                }

                public void setUserId(long userId) {
                    this.userId = userId;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }
            }
        }
    }

}
