package heyong.intellectPinPang.event;

public class CityEvent {
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityEvent(String name) {
        this.name = name;
    }
}
