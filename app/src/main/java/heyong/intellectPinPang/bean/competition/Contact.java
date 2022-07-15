package heyong.intellectPinPang.bean.competition;


import heyong.intellectPinPang.cn.CN;

/**
 * Created by you on 2017/9/11.
 */

public class Contact implements CN {

    public String name;
    public String id;
    private int selectType = 0;//0是灰色  1 是未选中  2 是选中


    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contact() {
    }

    public Contact(String name, String id, int selectType) {
        this.name = name;
        this.id = id;
        this.selectType = selectType;
    }

    public String getName() {
        return name;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String chinese() {
        return name;
    }
}
