package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class QueryComputeInfoBean {


    /**
     * itemType : null
     * first : [{"player1":"吴彦祖","player2":null,"joinId":536930097770355328,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":1,"ranking":1,"listInfo":[{"joinId":536930097770355328,"player1":"彭于晏","player2":null,"winCount":0,"loseCount":2},{"joinId":536930097770355328,"player1":"杨洋","player2":null,"winCount":2,"loseCount":0},{"joinId":null,"player1":"合计","player2":null,"winCount":2,"loseCount":2}]},{"player1":"彭于晏","player2":null,"joinId":541933497838819968,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":1,"ranking":2,"listInfo":[{"joinId":541933497838819968,"player1":"吴彦祖","player2":null,"winCount":0,"loseCount":2},{"joinId":541933497838819968,"player1":"杨洋","player2":null,"winCount":2,"loseCount":0},{"joinId":null,"player1":"合计","player2":null,"winCount":2,"loseCount":2}]},{"player1":"杨洋","player2":null,"joinId":541938996831410816,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":1,"ranking":3,"listInfo":[{"joinId":541938996831410816,"player1":"彭于晏","player2":null,"winCount":2,"loseCount":0},{"joinId":541938996831410816,"player1":"吴彦祖","player2":null,"winCount":2,"loseCount":0},{"joinId":null,"player1":"合计","player2":null,"winCount":4,"loseCount":0}]}]
     * second : [{"player1":"吴彦祖","player2":null,"joinId":536930097770355328,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":1.17,"ranking":1,"listInfo":[{"joinId":536930097770355328,"player1":"彭于晏","player2":null,"winCount":13,"loseCount":22},{"joinId":536930097770355328,"player1":"杨洋","player2":null,"winCount":22,"loseCount":8},{"joinId":null,"player1":"合计","player2":null,"winCount":35,"loseCount":30}]},{"player1":"彭于晏","player2":null,"joinId":541933497838819968,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":1,"ranking":2,"listInfo":[{"joinId":541933497838819968,"player1":"吴彦祖","player2":null,"winCount":13,"loseCount":22},{"joinId":541933497838819968,"player1":"杨洋","player2":null,"winCount":22,"loseCount":13},{"joinId":null,"player1":"合计","player2":null,"winCount":35,"loseCount":35}]},{"player1":"杨洋","player2":null,"joinId":541938996831410816,"matchId":564457669474538672,"itemId":564457669893969072,"groupNum":"564461930031764656","result":0.86,"ranking":3,"listInfo":[{"joinId":541938996831410816,"player1":"彭于晏","player2":null,"winCount":22,"loseCount":13},{"joinId":541938996831410816,"player1":"吴彦祖","player2":null,"winCount":22,"loseCount":8},{"joinId":null,"player1":"合计","player2":null,"winCount":44,"loseCount":21}]}]
     */

    private String itemType;
    private List<FirstBean> first;
    private List<SecondBean> second;
    private String integral;

    public String getIntegral() {
        return integral == null ? "" : integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<FirstBean> getFirst() {
        return first;
    }

    public void setFirst(List<FirstBean> first) {
        this.first = first;
    }

    public List<SecondBean> getSecond() {
        return second;
    }

    public void setSecond(List<SecondBean> second) {
        this.second = second;
    }

    public static class FirstBean {
        /**
         * player1 : 吴彦祖
         * player2 : null
         * joinId : 536930097770355328
         * matchId : 564457669474538672
         * itemId : 564457669893969072
         * groupNum : 564461930031764656
         * result : 1.0
         * ranking : 1
         * listInfo : [{"joinId":536930097770355328,"player1":"彭于晏","player2":null,"winCount":0,"loseCount":2},{"joinId":536930097770355328,"player1":"杨洋","player2":null,"winCount":2,"loseCount":0},{"joinId":null,"player1":"合计","player2":null,"winCount":2,"loseCount":2}]
         */

        private String player1;
        private String player2;
        private long joinId;
        private long matchId;
        private long itemId;
        private String groupNum;
        private double result;
        private int ranking;
        private List<ListInfoBean> listInfo;
        private  boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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

        public long getJoinId() {
            return joinId;
        }

        public void setJoinId(long joinId) {
            this.joinId = joinId;
        }

        public long getMatchId() {
            return matchId;
        }

        public void setMatchId(long matchId) {
            this.matchId = matchId;
        }

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public String getGroupNum() {
            return groupNum == null ? "" : groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public List<ListInfoBean> getListInfo() {
            return listInfo;
        }

        public void setListInfo(List<ListInfoBean> listInfo) {
            this.listInfo = listInfo;
        }

        public static class ListInfoBean {
            /**
             * joinId : 536930097770355328
             * player1 : 彭于晏
             * player2 : null
             * winCount : 0
             * loseCount : 2
             */

            private long joinId;
            private String player1;
            private String player2;
            private int winCount;
            private int loseCount;

            public long getJoinId() {
                return joinId;
            }

            public void setJoinId(long joinId) {
                this.joinId = joinId;
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

            public int getWinCount() {
                return winCount;
            }

            public void setWinCount(int winCount) {
                this.winCount = winCount;
            }

            public int getLoseCount() {
                return loseCount;
            }

            public void setLoseCount(int loseCount) {
                this.loseCount = loseCount;
            }
        }
    }

    public static class SecondBean {
        /**
         * player1 : 吴彦祖
         * player2 : null
         * joinId : 536930097770355328
         * matchId : 564457669474538672
         * itemId : 564457669893969072
         * groupNum : 564461930031764656
         * result : 1.17
         * ranking : 1
         * listInfo : [{"joinId":536930097770355328,"player1":"彭于晏","player2":null,"winCount":13,"loseCount":22},{"joinId":536930097770355328,"player1":"杨洋","player2":null,"winCount":22,"loseCount":8},{"joinId":null,"player1":"合计","player2":null,"winCount":35,"loseCount":30}]
         */

        private String player1;
        private String player2;
        private long joinId;
        private long matchId;
        private long itemId;
        private String groupNum;
        private double result;
        private int ranking;
        private List<ListInfoBeanX> listInfo;

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

        public long getJoinId() {
            return joinId;
        }

        public void setJoinId(long joinId) {
            this.joinId = joinId;
        }

        public long getMatchId() {
            return matchId;
        }

        public void setMatchId(long matchId) {
            this.matchId = matchId;
        }

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public String getGroupNum() {
            return groupNum == null ? "" : groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public List<ListInfoBeanX> getListInfo() {
            return listInfo;
        }

        public void setListInfo(List<ListInfoBeanX> listInfo) {
            this.listInfo = listInfo;
        }

        public static class ListInfoBeanX {
            /**
             * joinId : 536930097770355328
             * player1 : 彭于晏
             * player2 : null
             * winCount : 13
             * loseCount : 22
             */

            private long joinId;
            private String player1;
            private String player2;
            private int winCount;
            private int loseCount;

            public long getJoinId() {
                return joinId;
            }

            public void setJoinId(long joinId) {
                this.joinId = joinId;
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

            public int getWinCount() {
                return winCount;
            }

            public void setWinCount(int winCount) {
                this.winCount = winCount;
            }

            public int getLoseCount() {
                return loseCount;
            }

            public void setLoseCount(int loseCount) {
                this.loseCount = loseCount;
            }
        }
    }

}
