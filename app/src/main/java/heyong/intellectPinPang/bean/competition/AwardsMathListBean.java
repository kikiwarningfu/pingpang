package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class AwardsMathListBean {

    /**
     * matchId : 512317538144129040
     * searchTitle : 团体比赛成绩
     * mathTitle : 北京二级运动员晋级赛
     * city : 北京 大兴区
     * local : 林肯公园
     * beginTime : 2021-12-15
     * rank : 0
     * rankName :
     * matchImgUrl : null
     * allCount : 0
     * socre :
     * name : null
     */
    private long arrangeId;
    private long userId;
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
    private String socre;
    private String name;

    public long getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(long arrangeId) {
        this.arrangeId = arrangeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public String getSocre() {
        return socre == null ? "" : socre;
    }

    public void setSocre(String socre) {
        this.socre = socre;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
