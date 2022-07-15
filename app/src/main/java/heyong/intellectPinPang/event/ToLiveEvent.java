package heyong.intellectPinPang.event;

public class ToLiveEvent {
    String title;

    public ToLiveEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
