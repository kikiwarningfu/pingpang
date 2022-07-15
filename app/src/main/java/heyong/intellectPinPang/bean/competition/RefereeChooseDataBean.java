package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class RefereeChooseDataBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * identity : null
     * socre : 0
     * matchId : 512317538144129040
     * tableNum : null
     * name : 王洪福
     * id : 1
     * userId : 511486160083128336
     * status : 1
     * createUser : 1
     * createTime : 2021-01-13 11:45:49
     * updateUser : 1
     * updateTime : 2021-01-13 11:45:52
     * isDelete : 0
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private String identity;
    private int socre;
    private long matchId;
    private String tableNum;
    private String name;
    private long id;
    private long userId;
    private String status;
    private int createUser;
    private String createTime;
    private int updateUser;
    private String updateTime;
    private String isDelete;
    private boolean isSelect;
    private boolean canClickable;

    public boolean isCanClickable() {
        return canClickable;
    }

    public void setCanClickable(boolean canClickable) {
        this.canClickable = canClickable;
    }

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

    public String getIdentity() {
        return identity == null ? "" : identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getTableNum() {
        return tableNum == null ? "" : tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
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
