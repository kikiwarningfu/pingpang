package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class CalculationInfoBean {

        private List<FirstBean> first;
        private List<SecondBean> second;

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
             * name : 柯南
             * userId : 512936350908452880
             * result : 1.5
             * ranking : 2
             * listInfo : [{"name":"律香川","winCount":1,"loseCount":2},{"name":"孟星魂","winCount":2,"loseCount":0},{"name":"律香川","winCount":2,"loseCount":0},{"name":"茅十八","winCount":2,"loseCount":0},{"name":"合计","winCount":7,"loseCount":2}]
             */

            private String name;
            private long userId;
            private long matchId;
            private double result;
            private int ranking;
            private List<ListInfoBean> listInfo;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
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
                if (listInfo == null) {
                    return new ArrayList<>();
                }
                return listInfo;
            }

            public void setListInfo(List<ListInfoBean> listInfo) {
                this.listInfo = listInfo;
            }

            public static class ListInfoBean {
                /**
                 * name : 律香川
                 * winCount : 1
                 * loseCount : 2
                 */

                private String name;
                private int winCount;
                private int loseCount;

                public String getName() {
                    return name == null ? "" : name;
                }

                public void setName(String name) {
                    this.name = name;
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
             * name : null
             * userId : 512936350908452880
             * result : 0.0
             * ranking : 2
             * listInfo : [{"name":"律香川","winCount":0,"loseCount":0},{"name":"孟星魂","winCount":0,"loseCount":0},{"name":"律香川","winCount":0,"loseCount":0},{"name":"茅十八","winCount":0,"loseCount":0},{"name":"合计","winCount":0,"loseCount":0}]
             */

            private String name;
            private long userId;
            private double result;
            private int ranking;
            private long matchId;

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            private List<ListInfoBeanX> listInfo;

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
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
                if (listInfo == null) {
                    return new ArrayList<>();
                }
                return listInfo;
            }

            public void setListInfo(List<ListInfoBeanX> listInfo) {
                this.listInfo = listInfo;
            }

            public static class ListInfoBeanX {
                /**
                 * name : 律香川
                 * winCount : 0
                 * loseCount : 0
                 */

                private String name;
                private int winCount;
                private int loseCount;

                public String getName() {
                    return name == null ? "" : name;
                }

                public void setName(String name) {
                    this.name = name;
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
