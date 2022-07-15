package heyong.intellectPinPang.bean.live;

import java.util.ArrayList;
import java.util.List;

//直播初始化，设置付款方式及价位
public class LiveMatchInitBean {


    /**
     * watchMoney : 0.01
     * payeeList : [{"type":1,"name":"支付宝"}]
     */

    private double watchMoney;
    private List<PayeeListBean> payeeList;

    public double getWatchMoney() {
        return watchMoney;
    }

    public void setWatchMoney(double watchMoney) {
        this.watchMoney = watchMoney;
    }

    public List<PayeeListBean> getPayeeList() {
        if (payeeList == null) {
            return new ArrayList<>();
        }
        return payeeList;
    }

    public void setPayeeList(List<PayeeListBean> payeeList) {
        this.payeeList = payeeList;
    }

    public static class PayeeListBean {
        /**
         * type : 1
         * name : 支付宝
         */

        private int type;
        private String name;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
