package heyong.intellectPinPang.event;

public class EventBusCityEvent {
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventBusCityEvent(String name) {
        this.name = name;
    }
}
