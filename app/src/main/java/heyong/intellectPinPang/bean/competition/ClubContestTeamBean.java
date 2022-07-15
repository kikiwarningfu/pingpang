package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ClubContestTeamBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * angle : 0
     * contestTeamMembers : null
     * id : 514857732211300992
     * teamNum : 0
     * clubContestInfoId : 514857731963837056
     * createUser : 511486160083128336
     * createTime : 2020-11-21 08:00:00
     * updateUser : 511486160083128336
     * updateTime : 2020-11-21 08:00:00
     * isDelete : 0
     * teamType : null
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private int angle;
    private String contestTeamMembers;
    private long id;
    private String teamNum;
    private long clubContestInfoId;
    private long createUser;
    private String createTime;
    private long updateUser;
    private String updateTime;
    private String isDelete;
    private String teamType;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public long getClubContestInfoId() {
        return clubContestInfoId;
    }

    public void setClubContestInfoId(long clubContestInfoId) {
        this.clubContestInfoId = clubContestInfoId;
    }

    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(long updateUser) {
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
