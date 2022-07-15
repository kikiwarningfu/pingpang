package heyong.intellectPinPang.ui.mine.event;

public class LiveHallChangeEvent {
    String timeUpdate;

    public String getTimeUpdate() {
        return timeUpdate == null ? "" : timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public LiveHallChangeEvent(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }
}
