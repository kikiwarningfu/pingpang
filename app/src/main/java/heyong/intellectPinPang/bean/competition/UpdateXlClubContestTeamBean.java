package heyong.intellectPinPang.bean.competition;

public class UpdateXlClubContestTeamBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * angle : 145
     * contestTeamMembers : null
     * id : 513707520704417808
     * teamNum : null
     * clubContestInfoId : null
     * createUser : null
     * createTime : null
     * updateUser : null
     * updateTime : null
     * isDelete : null
     * teamType : 1
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private int angle;
    private String contestTeamMembers;
    private long id;
    private String teamNum;
    private String clubContestInfoId;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String isDelete;
    private String teamType;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy == null ? "" : orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getContestTeamMembers() {
        return contestTeamMembers == null ? "" : contestTeamMembers;
    }

    public void setContestTeamMembers(String contestTeamMembers) {
        this.contestTeamMembers = contestTeamMembers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamNum() {
        return teamNum == null ? "" : teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getClubContestInfoId() {
        return clubContestInfoId == null ? "" : clubContestInfoId;
    }

    public void setClubContestInfoId(String clubContestInfoId) {
        this.clubContestInfoId = clubContestInfoId;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser == null ? "" : updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime == null ? "" : updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete == null ? "" : isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getTeamType() {
        return teamType == null ? "" : teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }
}
