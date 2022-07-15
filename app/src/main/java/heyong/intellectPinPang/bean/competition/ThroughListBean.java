package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ThroughListBean {

        /**
         * matchId : 512317538144129040
         * searchTitle : null
         * mathTitle : 北京二级运动员晋级赛
         * city : 北京 大兴区
         * local : 林肯公园
         * beginTime : 2021-12-15
         * rank : 0
         * rankName :
         * matchImgUrl : www.baidu.com
         * allCount : 0
         */

        private long matchId;
        private String searchTitle;
        private String mathTitle;
        private String city;
        private String local;
        private String beginTime;
        private int rank;
        private String rankName;
        private String matchImgUrl;
        private int allCount;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getSearchTitle() {
        return searchTitle == null ? "" : searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getMathTitle() {
        return mathTitle == null ? "" : mathTitle;
    }

    public void setMathTitle(String mathTitle) {
        this.mathTitle = mathTitle;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocal() {
        return local == null ? "" : local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getBeginTime() {
        return beginTime == null ? "" : beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRankName() {
        return rankName == null ? "" : rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getMatchImgUrl() {
        return matchImgUrl == null ? "" : matchImgUrl;
    }

    public void setMatchImgUrl(String matchImgUrl) {
        this.matchImgUrl = matchImgUrl;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
