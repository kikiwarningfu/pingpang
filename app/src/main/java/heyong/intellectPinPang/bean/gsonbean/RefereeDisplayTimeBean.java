package heyong.intellectPinPang.bean.gsonbean;

public class RefereeDisplayTimeBean {
    private String time;
    private String ids;
    private boolean isSelect;
    private String upTime;

    public String getUpTime() {
        return upTime == null ? "" : upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIds() {
        return ids == null ? "" : ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
