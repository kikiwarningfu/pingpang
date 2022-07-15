package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayBeginArrangeBean implements Serializable {

    /**
     * arrangeId : 1
     * itemType : 3
     * leftTeamType : 2
     * rightTeamType : 2
     * chess : [{"id":1,"hitType":null,"leftCount":null,"rightCount":null,"player1":"王洪福","player2":"王洪福","player3":"王洪福","player4":"王洪福","player1Set":"A","player2Set":null,"player3Set":"A","player4Set":null,"leftWavier":null,"rightWavier":null,"status":null}]
     * leftCount : 2
     * rightCount : 1
     * headImg1 : http://images.xlttsports.com/20201119120745029662.png
     * player1 : null
     * player1Q : http://images.xlttsports.com/20201219153217009059.png
     * headImg2 : null
     * player2 : null
     * player2Q : null
     * headImg3 : http://images.xlttsports.com/20201119120745029662.png
     * player3 : null
     * player3Q : http://images.xlttsports.com/20201219153217009059.png
     * headImg4 : null
     * player4 : null
     * player4Q : null
     * club1Name : null
     * club2Name : null
     * status : null
     * refeeQ : null
     * zhangImg : www.baidu.com
     * title : 女团体循环赛
     * refereeInArrange : false
     */

    private long arrangeId;
    private String itemType;
    private String leftTeamType;
    private String rightTeamType;
    private int leftCount;
    private int rightCount;
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
    private String title;
    private boolean refereeInArrange;
    private List<ChessBean> chess;
    private String methond;
    private int leftWaiver;
    private int rightWaiver;
    private int leftSupend;
    private int rightSupend;

    public int getLeftSupend() {
        return leftSupend;
    }

    public void setLeftSupend(int leftSupend) {
        this.leftSupend = leftSupend;
    }

    public int getRightSupend() {
        return rightSupend;
    }

    public void setRightSupend(int rightSupend) {
        this.rightSupend = rightSupend;
    }

    public int getLeftWaiver() {
        return leftWaiver;
    }

    public void setLeftWaiver(int leftWaiver) {
        this.leftWaiver = leftWaiver;
    }

    public int getRightWaiver() {
        return rightWaiver;
    }

    public void setRightWaiver(int rightWaiver) {
        this.rightWaiver = rightWaiver;
    }

    public long getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(long arrangeId) {
        this.arrangeId = arrangeId;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
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

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
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
        return status == null ? "0" : status;
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

    public String getZhangImg() {
        return zhangImg == null ? "" : zhangImg;
    }

    public void setZhangImg(String zhangImg) {
        this.zhangImg = zhangImg;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRefereeInArrange() {
        return refereeInArrange;
    }

    public String getMethond() {
        return methond == null ? "" : methond;
    }

    public void setMethond(String methond) {
        this.methond = methond;
    }

    public void setRefereeInArrange(boolean refereeInArrange) {
        this.refereeInArrange = refereeInArrange;
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

    public static class ChessBean implements Serializable {
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
        private String club1Name;
        private String club2Name;
        private String head1Img;
        private String head2Img;
        private List<LittleChessBean> littleChessBeans;

        public static class LittleChessBean implements Serializable {
            private boolean canChange;
            private long id;
            private boolean isFirst;
            private boolean isSubmit;
            private boolean isTextSubmit;
            private String leftCount;
            private int leftWavier;
            private String rightCount;
            private int rightWavier;
            private String status;

            public boolean isCanChange() {
                return canChange;
            }

            public void setCanChange(boolean canChange) {
                this.canChange = canChange;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public boolean isFirst() {
                return isFirst;
            }

            public void setFirst(boolean first) {
                isFirst = first;
            }

            public boolean isSubmit() {
                return isSubmit;
            }

            public void setSubmit(boolean submit) {
                isSubmit = submit;
            }

            public boolean isTextSubmit() {
                return isTextSubmit;
            }

            public void setTextSubmit(boolean textSubmit) {
                isTextSubmit = textSubmit;
            }

            public String getLeftCount() {
                return leftCount == null ? "" : leftCount;
            }

            public void setLeftCount(String leftCount) {
                this.leftCount = leftCount;
            }

            public int getLeftWavier() {
                return leftWavier;
            }

            public void setLeftWavier(int leftWavier) {
                this.leftWavier = leftWavier;
            }

            public String getRightCount() {
                return rightCount == null ? "" : rightCount;
            }

            public void setRightCount(String rightCount) {
                this.rightCount = rightCount;
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

        public List<LittleChessBean> getLittleChessBeans() {
            return littleChessBeans;
        }

        public void setLittleChessBeans(List<LittleChessBean> littleChessBeans) {
            this.littleChessBeans = littleChessBeans;
        }

        public String getHead1Img() {
            return head1Img == null ? "" : head1Img;
        }

        public void setHead1Img(String head1Img) {
            this.head1Img = head1Img;
        }

        public String getHead2Img() {
            return head2Img == null ? "" : head2Img;
        }

        public void setHead2Img(String head2Img) {
            this.head2Img = head2Img;
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

}
