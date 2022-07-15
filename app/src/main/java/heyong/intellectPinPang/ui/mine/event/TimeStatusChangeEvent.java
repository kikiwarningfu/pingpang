package heyong.intellectPinPang.ui.mine.event;

public class TimeStatusChangeEvent {
    String timeUpdate;

    public String getTimeUpdate() {
        return timeUpdate == null ? "" : timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public TimeStatusChangeEvent(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }
}
