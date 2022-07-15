package heyong.intellectPinPang.bean.competition;

import heyong.handong.framework.base.ResponseBean;

public class SelectionsOfEventBean extends ResponseBean {
    public boolean isSelect;
    private String name;
    private String id;

    public SelectionsOfEventBean() {
    }

    public SelectionsOfEventBean(boolean isSelect, String name, String id) {
        this.isSelect = isSelect;
        this.name = name;
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
