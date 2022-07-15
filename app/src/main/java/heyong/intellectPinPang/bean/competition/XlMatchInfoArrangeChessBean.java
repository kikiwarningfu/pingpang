package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class XlMatchInfoArrangeChessBean implements Serializable {

    /**
     * itemType : null
     * leftTeamType : null
     * rightTeamType : null
     * chess : [{"id":1,"hitType":null,"leftCount":1,"rightCount":1,"player1":null,"player2":null,"player3":null,"player4":null,"player1Set":null,"player2Set":null,"player3Set":null,"player4Set":null},{"id":2,"hitType":null,"leftCount":1,"rightCount":1,"player1":null,"player2":null,"player3":null,"player4":null,"player1Set":null,"player2Set":null,"player3Set":null,"player4Set":null}]
     * leftCount : 2
     * rightCount : 1
     * headImg1 : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
     * player1 : 王洪福
     * player1Q : http://images.xlttsports.com/20201219153217009059.png
     * headImg2 : null
     * player2 : null
     * player2Q : null
     * headImg3 : null
     * player3 : 茅十八
     * player3Q : null
     * headImg4 : null
     * player4 : null
     * player4Q : null
     * club1Name : 12312
     * club2Name : 自由
     * status : 3
     * refeeQ : null
     */

    private String itemType;
    private String leftTeamType;
    private String rightTeamType;
    private String leftCount;
    private String rightCount;
    private String headImg1;
    private String player1;
    private String player1Q;
    private String headImg2;
    private String player2;
    private String player2Q;
    private String headImg3;
    private String player3;
    private String player3Q;
    private String headImg4;
    private String player4;
    private String player4Q;
    private String club1Name;
    private String club2Name;
    private String status;
    private String refeeQ;
    private String zhangImg;
    private List<ChessBean> chess;
    private String title;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZhangImg() {
        return zhangImg == null ? "" : zhangImg;
    }

    public void setZhangImg(String zhangImg) {
        this.zhangImg = zhangImg;
    }

    public String getItemType() {
        return itemType == null ? "1" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getLeftTeamType() {
        return leftTeamType == null ? "" : leftTeamType;
    }

    public void setLeftTeamType(String leftTeamType) {
        this.leftTeamType = leftTeamType;
    }

    public String getRightTeamType() {
        return rightTeamType == null ? "" : rightTeamType;
    }

    public void setRightTeamType(String rightTeamType) {
        this.rightTeamType = rightTeamType;
    }

    public String getLeftCount() {
        return leftCount == null ? "0" : leftCount;
    }

    public void setLeftCount(String leftCount) {
        this.leftCount = leftCount;
    }

    public String getRightCount() {
        return rightCount == null ? "0" : rightCount;
    }

    public void setRightCount(String rightCount) {
        this.rightCount = rightCount;
    }

    public String getHeadImg1() {
        return headImg1 == null ? "" : headImg1;
    }

    public void setHeadImg1(String headImg1) {
        this.headImg1 = headImg1;
    }

    public String getPlayer1() {
        return player1 == null ? "" : player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer1Q() {
        return player1Q == null ? "" : player1Q;
    }

    public void setPlayer1Q(String player1Q) {
        this.player1Q = player1Q;
    }

    public String getHeadImg2() {
        return headImg2 == null ? "" : headImg2;
    }

    public void setHeadImg2(String headImg2) {
        this.headImg2 = headImg2;
    }

    public String getPlayer2() {
        return player2 == null ? "" : player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer2Q() {
        return player2Q == null ? "" : player2Q;
    }

    public void setPlayer2Q(String player2Q) {
        this.player2Q = player2Q;
    }

    public String getHeadImg3() {
        return headImg3 == null ? "" : headImg3;
    }

    public void setHeadImg3(String headImg3) {
        this.headImg3 = headImg3;
    }

    public String getPlayer3() {
        return player3 == null ? "" : player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer3Q() {
        return player3Q == null ? "" : player3Q;
    }

    public void setPlayer3Q(String player3Q) {
        this.player3Q = player3Q;
    }

    public String getHeadImg4() {
        return headImg4 == null ? "" : headImg4;
    }

    public void setHeadImg4(String headImg4) {
        this.headImg4 = headImg4;
    }

    public String getPlayer4() {
        return player4 == null ? "" : player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    public String getPlayer4Q() {
        return player4Q == null ? "" : player4Q;
    }

    public void setPlayer4Q(String player4Q) {
        this.player4Q = player4Q;
    }

    public String getClub1Name() {
        return club1Name == null ? "" : club1Name;
    }

    public void setClub1Name(String club1Name) {
        this.club1Name = club1Name;
    }

    public String getClub2Name() {
        return club2Name == null ? "" : club2Name;
    }

    public void setClub2Name(String club2Name) {
        this.club2Name = club2Name;
    }

    public String getStatus() {
        return status == null ? "1" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefeeQ() {
        return refeeQ == null ? "" : refeeQ;
    }

    public void setRefeeQ(String refeeQ) {
        this.refeeQ = refeeQ;
    }

    public List<ChessBean> getChess() {
        return chess;
    }

    public void setChess(List<ChessBean> chess) {
        this.chess = chess;
    }

    public static class ChessBean implements Serializable{
        /**
         * id : 1
         * hitType : null
         * leftCount : 1
         * rightCount : 1
         * player1 : null
         * player2 : null
         * player3 : null
         * player4 : null
         * player1Set : null
         * player2Set : null
         * player3Set : null
         * player4Set : null
         */

        private long id;
        private String hitType;
        private String leftCount;
        private String rightCount;
        private String player1;
        private String player2;
        private String player3;
        private String player4;
        private String player1Set;
        private String player2Set;
        private String player3Set;
        private String player4Set;
        private String leftWavier;
        private String rightWavier;
        private String status;

        public String getLeftWavier() {
            return leftWavier == null ? "0" : leftWavier;
        }

        public void setLeftWavier(String leftWavier) {
            this.leftWavier = leftWavier;
        }

        public String getRightWavier() {
            return rightWavier == null ? "0" : rightWavier;
        }

        public void setRightWavier(String rightWavier) {
            this.rightWavier = rightWavier;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHitType() {
            return hitType == null ? "1" : hitType;
        }

        public void setHitType(String hitType) {
            this.hitType = hitType;
        }

        public String getLeftCount() {
            return leftCount == null ? "" : leftCount;
        }

        public void setLeftCount(String leftCount) {
            this.leftCount = leftCount;
        }

        public String getRightCount() {
            return rightCount == null ? "" : rightCount;
        }

        public void setRightCount(String rightCount) {
            this.rightCount = rightCount;
        }

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

        public String getPlayer3Set() {
            return player3Set == null ? "" : player3Set;
        }

        public void setPlayer3Set(String player3Set) {
            this.player3Set = player3Set;
        }

        public String getPlayer4Set() {
            return player4Set == null ? "" : player4Set;
        }

        public void setPlayer4Set(String player4Set) {
            this.player4Set = player4Set;
        }
    }

}
