package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class QueryPointDetailBean {


    /**
     * itemType : 2
     * pointsDetailsDtos : [{"player1":"王洪福","player2":null,"player3":"茅十八","player4":null,"result":null,"chess":["2:2","2:2"]}]
     */

    private String itemType;
    private List<PointsDetailsDtosBean> pointsDetailsDtos;

    public String getItemType() {
        return itemType == null ? "1" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<PointsDetailsDtosBean> getPointsDetailsDtos() {
        return pointsDetailsDtos;
    }

    public void setPointsDetailsDtos(List<PointsDetailsDtosBean> pointsDetailsDtos) {
        this.pointsDetailsDtos = pointsDetailsDtos;
    }

    public static class PointsDetailsDtosBean {
        /**
         * player1 : 王洪福
         * player2 : null
         * player3 : 茅十八
         * player4 : null
         * result : null
         * chess : ["2:2","2:2"]
         */

        private String player1;
        private String player2;
        private String player3;
        private String player4;
        private String result;
        private List<String> chess;

        public String getPlayer1() {
            return player1 == null ? "" : player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer2() {
            return player2 == null ? "" : player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public String getPlayer3() {
            return player3 == null ? "" : player3;
        }

        public void setPlayer3(String player3) {
            this.player3 = player3;
        }

        public String getPlayer4() {
            return player4 == null ? "" : player4;
        }

        public void setPlayer4(String player4) {
            this.player4 = player4;
        }

        public String getResult() {
            return result == null ? "" : result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public List<String> getChess() {
            return chess;
        }

        public void setChess(List<String> chess) {
            this.chess = chess;
        }
    }

}
