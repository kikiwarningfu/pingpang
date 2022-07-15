package heyong.intellectPinPang.bean.competition;

public class SelectAthletesAndCoachesBean {
    private String name;
    boolean isSelect;

    public SelectAthletesAndCoachesBean() {
    }

    public SelectAthletesAndCoachesBean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
