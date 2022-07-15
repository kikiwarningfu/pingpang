package heyong.intellectPinPang.event;

public class SwipMatchEvent {
    private String clickPosition;
    private int leftWavier;
    private int rightWavier;
    private int leftCount;
    private int rightCount;

    public int getLeftWavier() {
        return leftWavier;
    }

    public void setLeftWavier(int leftWavier) {
        this.leftWavier = leftWavier;
    }

    public int getRightWavier() {
        return rightWavier;
    }

    public void setRightWavier(int rightWavier) {
        this.rightWavier = rightWavier;
    }

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public SwipMatchEvent(String clickPosition, int leftWavier, int rightWavier, int leftCount, int rightCount) {
        this.clickPosition = clickPosition;
        this.leftWavier = leftWavier;
        this.rightWavier = rightWavier;
        this.leftCount = leftCount;
        this.rightCount = rightCount;
    }

    public String getClickPosition() {
        return clickPosition == null ? "" : clickPosition;
    }

    public void setClickPosition(String clickPosition) {
        this.clickPosition = clickPosition;
    }
}
