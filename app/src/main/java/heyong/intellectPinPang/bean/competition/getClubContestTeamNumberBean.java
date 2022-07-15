package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class getClubContestTeamNumberBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * name : 张无忌
     * id : 513707520821858321
     * contestTeamId : 513707520821858320
     * userId : 511486160083128336
     * createUser : 511486160083128336
     * createTime : 2020-11-18 08:00:00
     * updateUser : 511486160083128336
     * updateTime : 2020-11-18 08:00:00
     * isDelete : 0
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private String name;
    private long id;
    private long contestTeamId;
    private long userId;
    private long createUser;
    private String createTime;
    private long updateUser;
    private String updateTime;
    private String isDelete;

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

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContestTeamId() {
        return contestTeamId;
    }

    public void setContestTeamId(long contestTeamId) {
        this.contestTeamId = contestTeamId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
