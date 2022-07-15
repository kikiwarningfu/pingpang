package heyong.intellectPinPang.bean.live;

public class WalletInfoBean {


    /**
     * id : 616231249266496688
     * mayMoney : 0.00
     * allMoney : 0.00
     * dayMoney : 0.00
     * monthMoney : 0.00
     * totalMoney : 0.00
     */

    private long id;
    private String mayMoney;
    private String allMoney;
    private String dayMoney;
    private String monthMoney;
    private String totalMoney;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMayMoney() {
        return mayMoney == null ? "" : mayMoney;
    }

    public void setMayMoney(String mayMoney) {
        this.mayMoney = mayMoney;
    }

    public String getAllMoney() {
        return allMoney == null ? "" : allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    public String getDayMoney() {
        return dayMoney == null ? "" : dayMoney;
    }

    public void setDayMoney(String dayMoney) {
        this.dayMoney = dayMoney;
    }

    public String getMonthMoney() {
        return monthMoney == null ? "" : monthMoney;
    }

    public void setMonthMoney(String monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getTotalMoney() {
        return totalMoney == null ? "" : totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
