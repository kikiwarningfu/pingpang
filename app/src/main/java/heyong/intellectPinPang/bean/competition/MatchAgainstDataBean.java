package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MatchAgainstDataBean {


    /**
     * name : 周琳超
     * opponent : [{"name":"张飞扬","matchDate":"2021/08/07","matchTime":"07:20/1台","status":"2:0"},{"name":"李铖泽","matchDate":"2021/08/07","matchTime":"06:40/1台","status":"2:0"},{"name":"李铠旭","matchDate":"2021/08/07","matchTime":"06:00/1台","status":"2:0"}]
     */

    private String name;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private List<OpponentBean> opponent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OpponentBean> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<OpponentBean> opponent) {
        this.opponent = opponent;
    }

    public static class OpponentBean {
        /**
         * name : 张飞扬
         * matchDate : 2021/08/07
         * matchTime : 07:20/1台
         * status : 2:0
         */

        private String name;
        private String matchDate;
        private String matchTime;
        private String status;
        private String tableNum;

        public String getTableNum() {
            return tableNum == null ? "" : tableNum;
        }

        public void setTableNum(String tableNum) {
            this.tableNum = tableNum;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMatchDate() {
            return matchDate == null ? "" : matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }

        public String getMatchTime() {
            return matchTime == null ? "" : matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
