package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class BsResponityListBean {
    List<BsAttrListBean> bsAttrListBeans;

    public BsResponityListBean() {
    }

    public BsResponityListBean(List<BsAttrListBean> bsAttrListBeans) {
        this.bsAttrListBeans = bsAttrListBeans;
    }

    public List<BsAttrListBean> getBsAttrListBeans() {
        return bsAttrListBeans;
    }

    public void setBsAttrListBeans(List<BsAttrListBean> bsAttrListBeans) {
        this.bsAttrListBeans = bsAttrListBeans;
    }
}
