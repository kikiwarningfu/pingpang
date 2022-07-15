package heyong.intellectPinPang.event;

public class ToBuyEvent {
    String title;

    public ToBuyEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
