package heyong.intellectPinPang.bean.live;

public class ZhiboJiedanUserBaseInfoBean {

    private long id;
    private String name;
    private String sex;
    private String idCardNum;
    private int deposit;
    private boolean hasNeedDeposit;
    private boolean authentication;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCardNum() {
        return idCardNum == null ? "" : idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public boolean isHasNeedDeposit() {
        return hasNeedDeposit;
    }

    public void setHasNeedDeposit(boolean hasNeedDeposit) {
        this.hasNeedDeposit = hasNeedDeposit;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }
}
