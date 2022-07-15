package heyong.intellectPinPang.ui.coachdisplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RefereeDisPlayBean implements Serializable {
    private String title;
    private String leftName;
    private String leftLogo;
    private String rightName;
    private String rightLogo;
    private String time;
    private int showStatus;
    private String score;
    private int itemType;//1团体 2单打
    private int clickStatus;//1 是跳转抽签（区分团体还是单打）  2 跳转到团体


    private int leftWavier;
    private int rightWavier;
    private int leftWinCount;
    private int rightWinCount;
    private String clubName1;
    private String clubName2;
    private List<ChessBean> chess;
    private boolean isShowGiveUp;

    public boolean isShowGiveUp() {
        return isShowGiveUp;
    }

    public void setShowGiveUp(boolean showGiveUp) {
        isShowGiveUp = showGiveUp;
    }

    public List<ChessBean> getChess() {
        if (chess == null) {
            return new ArrayList<>();
        }
        return chess;
    }

    public void setChess(List<ChessBean> chess) {
        this.chess = chess;
    }

    public String getClubName1() {
        return clubName1 == null ? "" : clubName1;
    }

    public void setClubName1(String clubName1) {
        this.clubName1 = clubName1;
    }

    public String getClubName2() {
        return clubName2 == null ? "" : clubName2;
    }

    public void setClubName2(String clubName2) {
        this.clubName2 = clubName2;
    }

    public static class ChessBean implements Serializable{
        /**
         * id : 1
         * hitType : null
         * leftCount : null
         * rightCount : null
         * player1 : 王洪福
         * player2 : 王洪福
         * player3 : 王洪福
         * player4 : 王洪福
         * player1Set : A
         * player2Set : null
         * player3Set : A
         * player4Set : null
         * leftWavier : null
         * rightWavier : null
         * status : null
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
        private int leftWavier;
        private int rightWavier;
        private String status;
        private boolean canChange;
        private boolean isSubmit = false;
        private boolean isFirst;
        private boolean isTextSubmit;

        public boolean isCanChange() {
            return canChange;
        }

        public void setCanChange(boolean canChange) {
            this.canChange = canChange;
        }

        public boolean isSubmit() {
            return isSubmit;
        }

        public void setSubmit(boolean submit) {
            isSubmit = submit;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        public boolean isTextSubmit() {
            return isTextSubmit;
        }

        public void setTextSubmit(boolean textSubmit) {
            isTextSubmit = textSubmit;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHitType() {
            return hitType == null ? "" : hitType;
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

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
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

    public int getClickStatus() {
        return clickStatus;
    }

    public void setClickStatus(int clickStatus) {
        this.clickStatus = clickStatus;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeftName() {
        return leftName == null ? "" : leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getLeftLogo() {
        return leftLogo == null ? "" : leftLogo;
    }

    public void setLeftLogo(String leftLogo) {
        this.leftLogo = leftLogo;
    }

    public String getRightName() {
        return rightName == null ? "" : rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightLogo() {
        return rightLogo == null ? "" : rightLogo;
    }

    public void setRightLogo(String rightLogo) {
        this.rightLogo = rightLogo;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public String getScore() {
        return score == null ? "" : score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
