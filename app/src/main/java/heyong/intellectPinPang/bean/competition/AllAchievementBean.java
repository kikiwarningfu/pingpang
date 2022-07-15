package heyong.intellectPinPang.bean.competition;

public class AllAchievementBean {

    /**
     * allCount : 2
     * teamCount : 1
     * singleCount : 1
     * doubleCount : 0
     * hunHeCount : 0
     * winCount : 2
     * loseCount : 0
     * win : 100.00%
     */

    private int allCount;
    private int teamCount;
    private int singleCount;
    private int doubleCount;
    private int hunHeCount;
    private int winCount;
    private int loseCount;
    private String win;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(int singleCount) {
        this.singleCount = singleCount;
    }

    public int getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(int doubleCount) {
        this.doubleCount = doubleCount;
    }

    public int getHunHeCount() {
        return hunHeCount;
    }

    public void setHunHeCount(int hunHeCount) {
        this.hunHeCount = hunHeCount;
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

    public String getWin() {
        return win == null ? "" : win;
    }

    public void setWin(String win) {
        this.win = win;
    }
}
