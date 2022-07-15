package heyong.intellectPinPang.bean.competition;

public class JudgeJoinMatchStatusBean {
    /**
     * joinStatus : 1
     * role : 2
     */

    private String joinStatus;
    private String role;
    public String getJoinStatus() {
        return joinStatus == null ? "0" : joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getRole() {
        return role == null ? "1" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
