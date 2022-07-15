package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class SimpleCalculationInfoBean {
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
    private List<ListInfoBean> listInfo;

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
         * winCount : 0
         * loseCount : 0
         */

        private String name;
        private int winCount;
        private int loseCount;
        private String result;

        public String getResult() {
            return result == null ? "" : result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public ListInfoBean(String name, int winCount, int loseCount, String result) {
            this.name = name;
            this.winCount = winCount;
            this.loseCount = loseCount;
            this.result = result;
        }

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
