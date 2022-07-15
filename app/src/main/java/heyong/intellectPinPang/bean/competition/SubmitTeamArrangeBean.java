package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class SubmitTeamArrangeBean {

    public SubmitTeamArrangeBean() {
    }

    /**
     * appOperationLogId : 534704869186359728
     * leftOrRight : 2
     * arrangeId : 1
     * teamArranges : [{"teamArrangeId":"1","player1Id":"526770476186751408","player2Id":""}]
     */

    private String appOperationLogId;
    private String leftOrRight;
    private String leftTeamId;
    private String rightTeamId;
    private String arrangeId;
    private List<TeamArrangesBean> teamArranges;

    public String getLeftTeamId() {
        return leftTeamId;
    }

    public void setLeftTeamId(String leftTeamId) {
        this.leftTeamId = leftTeamId;
    }

    public String getRightTeamId() {
        return rightTeamId;
    }

    public void setRightTeamId(String rightTeamId) {
        this.rightTeamId = rightTeamId;
    }

    public String getAppOperationLogId() {
        return appOperationLogId;
    }

    public void setAppOperationLogId(String appOperationLogId) {
        this.appOperationLogId = appOperationLogId;
    }

    public String getLeftOrRight() {
        return leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public String getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(String arrangeId) {
        this.arrangeId = arrangeId;
    }

    public List<TeamArrangesBean> getTeamArranges() {
        return teamArranges;
    }

    public void setTeamArranges(List<TeamArrangesBean> teamArranges) {
        this.teamArranges = teamArranges;
    }

    public static class TeamArrangesBean {
        /**
         * teamArrangeId : 1
         * player1Id : 526770476186751408
         * player2Id :
         */

        private String teamArrangeId;
        private String player1Id;
        private String player2Id;
        private String player1Name;
        private String player2Name;
        private String player1Set;
        private String player2Set;
        private String hitType;
        private String ownTeamId;

        public String getTeamArrangeId() {
            return teamArrangeId == null ? "" : teamArrangeId;
        }

        public void setTeamArrangeId(String teamArrangeId) {
            this.teamArrangeId = teamArrangeId;
        }

        public String getPlayer1Id() {
            return player1Id == null ? "" : player1Id;
        }

        public void setPlayer1Id(String player1Id) {
            this.player1Id = player1Id;
        }

        public String getPlayer2Id() {
            return player2Id == null ? "" : player2Id;
        }

        public void setPlayer2Id(String player2Id) {
            this.player2Id = player2Id;
        }

        public String getPlayer1Name() {
            return player1Name == null ? "请选择" : player1Name;
        }

        public void setPlayer1Name(String player1Name) {
            this.player1Name = player1Name;
        }

        public String getPlayer2Name() {
            return player2Name == null ? "请选择" : player2Name;
        }

        public void setPlayer2Name(String player2Name) {
            this.player2Name = player2Name;
        }

        public String getPlayer1Set() {
            return player1Set == null ? "" : player1Set;
        }

        public void setPlayer1Set(String player1Set) {
            this.player1Set = player1Set;
        }

        public String getPlayer2Set() {
            return player2Set == null ? "" : player2Set;
        }

        public void setPlayer2Set(String player2Set) {
            this.player2Set = player2Set;
        }

        public String getHitType() {
            return hitType == null ? "" : hitType;
        }

        public void setHitType(String hitType) {
            this.hitType = hitType;
        }

        public String getOwnTeamId() {
            return ownTeamId == null ? "" : ownTeamId;
        }

        public void setOwnTeamId(String ownTeamId) {
            this.ownTeamId = ownTeamId;
        }
    }
}
