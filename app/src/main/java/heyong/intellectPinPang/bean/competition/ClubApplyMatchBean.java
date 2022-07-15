package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ClubApplyMatchBean {

    /**
     * clickType : 1
     * leader : 511215681036390416
     * matchId : 512317538144129040
     * enrollId : 519856978433445904
     * personCount : 1
     * needEat : 0
     * eatCount : 1
     * freeType : 1
     * referee : ["511486160083128336","511486160083128336"]
     * players : [{"projectId":"513767530847309840","itemId":"513767530885058576","type":"3","itemTitle":"混双","teams":[{"teamTitle":"旋风队","player":[{"id":"511486160083128336"}]}]}]
     */

    private String clickType;
    private String leader;
    private String matchId;
    private String enrollId;
    private String personCount;
    private String needEat;
    private String eatCount;
    private String freeType;
    private List<String> referee;
    private List<PlayersBean> players;
    private String id;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClickType() {
        return clickType == null ? "" : clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getLeader() {
        return leader == null ? "" : leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getMatchId() {
        return matchId == null ? "" : matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getEnrollId() {
        return enrollId == null ? "" : enrollId;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
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

    public String getEatCount() {
        return eatCount == null ? "" : eatCount;
    }

    public void setEatCount(String eatCount) {
        this.eatCount = eatCount;
    }

    public String getFreeType() {
        return freeType == null ? "" : freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public List<String> getReferee() {
        return referee;
    }

    public void setReferee(List<String> referee) {
        this.referee = referee;
    }

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class PlayersBean {
        /**
         * projectId : 513767530847309840
         * itemId : 513767530885058576
         * type : 3
         * itemTitle : 混双
         * teams : [{"teamTitle":"旋风队","player":[{"id":"511486160083128336"}]}]
         */

        private String projectId;
        private String itemId;
        private String type;
        private String itemTitle;
        private List<TeamsBean> teams;

        public String getProjectId() {
            return projectId == null ? "" : projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getItemId() {
            return itemId == null ? "" : itemId;
        }

        public void setItemId(String itemId) {
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

        public List<TeamsBean> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamsBean> teams) {
            this.teams = teams;
        }

        public static class TeamsBean {
            /**
             * teamTitle : 旋风队
             * player : [{"id":"511486160083128336"}]
             */

            private String teamTitle;
            private List<PlayerBean> player;

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
                 * id : 511486160083128336
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
