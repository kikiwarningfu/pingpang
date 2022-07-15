package heyong.intellectPinPang.event;

public class EndTeamEvent {
    private String strJson;
    private int leftAllWinCount;
    private int rightAllWinCount;

    public int getLeftAllWinCount() {
        return leftAllWinCount;
    }

    public void setLeftAllWinCount(int leftAllWinCount) {
        this.leftAllWinCount = leftAllWinCount;
    }

    public int getRightAllWinCount() {
        return rightAllWinCount;
    }

    public void setRightAllWinCount(int rightAllWinCount) {
        this.rightAllWinCount = rightAllWinCount;
    }

    public EndTeamEvent(String strJson, int leftAllWinCount, int rightAllWinCount) {
        this.strJson = strJson;
        this.leftAllWinCount = leftAllWinCount;
        this.rightAllWinCount = rightAllWinCount;
    }

    public String getStrJson() {
        return strJson == null ? "" : strJson;
    }

    public void setStrJson(String strJson) {
        this.strJson = strJson;
    }
}
