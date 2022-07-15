package heyong.intellectPinPang.bean.competition;

public class ClubContestStatisticsBean {
    /**
     * 总场次
     */
    private int allCount;
    /**
     * 待赛
     */
    private int notStartedCount;
    /**
     * 已结束
     */
    private int endCount;
    /**
     * 是否展示结束比赛按钮
     */
    private boolean endButton;

    /**
     * 是否显示录入比分按钮
     */
    private boolean enterButton;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getNotStartedCount() {
        return notStartedCount;
    }

    public void setNotStartedCount(int notStartedCount) {
        this.notStartedCount = notStartedCount;
    }

    public int getEndCount() {
        return endCount;
    }

    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }

    public boolean isEndButton() {
        return endButton;
    }

    public void setEndButton(boolean endButton) {
        this.endButton = endButton;
    }

    public boolean isEnterButton() {
        return enterButton;
    }

    public void setEnterButton(boolean enterButton) {
        this.enterButton = enterButton;
    }
}
