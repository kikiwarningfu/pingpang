package heyong.intellectPinPang.event;

public class PlayBackEvent {
    String title;

    public PlayBackEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
