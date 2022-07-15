package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class CoashRoleListBean {


        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * id : 1
         * roleName : 国家一级裁判员
         * roleType : 1
         * createUser : 1
         * createTime : 2020-12-02 00:00:00
         * updateUser : 1
         * updateTime : 2020-12-02 00:00:00
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private long id;
        private String roleName;
        private String roleType;
        private int createUser;
        private String createTime;
        private int updateUser;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName == null ? "" : roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType == null ? "" : roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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
