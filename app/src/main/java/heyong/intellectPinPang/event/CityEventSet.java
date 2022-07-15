package heyong.intellectPinPang.event;

public class CityEventSet {
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityEventSet(String name) {
        this.name = name;
    }
}
