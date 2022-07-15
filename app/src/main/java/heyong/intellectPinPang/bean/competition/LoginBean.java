package heyong.intellectPinPang.bean.competition;

public class LoginBean {

    /**
     * sign : eyJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOiI1MTE0ODYxNjAwODMxMjgzMzYiLCJzYXAiOiJudWxsIiwiZXhwIjoxNjA1MjQ3OTI3fQ.RnXOEPFofdVlfhj8CDsMHxSWIHE5P147Z6Dkhel_V4GdPHj9nKLrR7sZH94jv-BD8Wq8gMaXTVcXzQpKDmODBdk9ozn1BrkhLc75TbMk5CkRncLDE6bv3vaqTWP3BSnK7cDeyFGPs4RMFfSQ2uZVLolDTs9R3CUpxyK-a0B1mqg
     * userId : 511486679262466064
     * userName : null
     * phoneNum : 13292803269
     */

    private String sign;
    private long userId;
    private Object userName;
    private String phoneNum;

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum == null ? "" : phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
