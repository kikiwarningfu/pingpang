package heyong.intellectPinPang.bean.competition;

public class QueryMatchApplyInfoBean {

    /**
     * id : 512317538144129040
     * matchTitle : 北京二级运动员晋级赛
     * replaceCharge : 1
     * replaceChargeEat : 0
     * registrationCount : 100
     * ownFree : 120
     * daysCount : 10
     * eatFree : 30
     */

    private long id;
    private String matchTitle;
    private String replaceCharge;
    private int replaceChargeEat;
    private int registrationCount;
    private int ownFree;
    private int daysCount;
    private int eatFree;
    private String matchLevel;
    private String teamFree;

    public String getTeamFree() {
        return teamFree == null ? "0" : teamFree;
    }

    public void setTeamFree(String teamFree) {
        this.teamFree = teamFree;
    }

    public String getMatchLevel() {
        return matchLevel == null ? "专业级" : matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getReplaceCharge() {
        return replaceCharge == null ? "" : replaceCharge;
    }

    public void setReplaceCharge(String replaceCharge) {
        this.replaceCharge = replaceCharge;
    }

    public int getReplaceChargeEat() {
        return replaceChargeEat;
    }

    public void setReplaceChargeEat(int replaceChargeEat) {
        this.replaceChargeEat = replaceChargeEat;
    }

    public int getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(int registrationCount) {
        this.registrationCount = registrationCount;
    }

    public int getOwnFree() {
        return ownFree;
    }

    public void setOwnFree(int ownFree) {
        this.ownFree = ownFree;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public int getEatFree() {
        return eatFree;
    }

    public void setEatFree(int eatFree) {
        this.eatFree = eatFree;
    }
}
