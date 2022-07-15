package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;

public class BsAttrListBean implements Serializable {

    /**
     * name : 度数
     */

    private String name;
    private String etContgent;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getEtContgent() {
        return etContgent == null ? "" : etContgent;
    }

    public void setEtContgent(String etContgent) {
        this.etContgent = etContgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
