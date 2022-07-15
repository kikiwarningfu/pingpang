package heyong.intellectPinPang.bean.competition;

public class ChooseTeamFirstBean {


        /**
         * id : 1
         * player1 : 张三
         * player2 : 李四
         * player3 : 王五
         * player4 : 赵六
         * leftOrRight : 2
         */

        private long id;
        private String player1;
        private String player2;
        private String player3;
        private String player4;
        private String leftOrRight;

        private int angle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLeftOrRight() {
        return leftOrRight == null ? "" : leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
