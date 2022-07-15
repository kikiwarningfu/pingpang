package heyong.intellectPinPang.bean.competition;

public class AddRefereeBean {
    private String tableNum;
    private boolean isSelect;
    private boolean isCanClickable;

    public boolean isCanClickable() {
        return isCanClickable;
    }

    public void setCanClickable(boolean canClickable) {
        isCanClickable = canClickable;
    }

    public String getTableNum() {
        return tableNum == null ? "" : tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
