package heyong.intellectPinPang.bean.competition;

public class CompetitionItemBean {


    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * id : 512317538177683472
     * projectId : 512317538160906256
     * sexType : 1
     * manMinAge : 2021-12-15
     * manMaxAge : 2021-12-15
     * projectItem : 1
     * projectRule : null
     * minLimit : 1
     * maxLimit : 1
     * createUser : 511486160083128336
     * createTime : 2020-11-14 00:00:00
     * updateUser : 511486160083128336
     * updateTime : 2020-11-14 00:00:00
     * isDelete : 0
     * itemTitle : 成人甲组男子团体
     * itemType : 1
     * womanMinAge : null
     * womanMaxAge : null
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private long id;
    private long projectId;
    private String sexType;
    private String manMinAge;
    private String manMaxAge;
    private String projectItem;
    private Object projectRule;
    private int minLimit;
    private int maxLimit;
    private long createUser;
    private String createTime;
    private long updateUser;
    private String updateTime;
    private String isDelete;
    private String itemTitle;
    private String itemType;
    private String womanMinAge;
    private String womanMaxAge;
    private boolean isSelect;
    private String projectName;
    private long groupId;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getSexType() {
        return sexType == null ? "1" : sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public String getManMinAge() {
        return manMinAge == null ? "" : manMinAge;
    }

    public void setManMinAge(String manMinAge) {
        this.manMinAge = manMinAge;
    }

    public String getManMaxAge() {
        return manMaxAge == null ? "" : manMaxAge;
    }

    public void setManMaxAge(String manMaxAge) {
        this.manMaxAge = manMaxAge;
    }

    public String getProjectItem() {
        return projectItem == null ? "1" : projectItem;
    }

    public void setProjectItem(String projectItem) {
        this.projectItem = projectItem;
    }

    public Object getProjectRule() {
        return projectRule;
    }

    public void setProjectRule(Object projectRule) {
        this.projectRule = projectRule;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
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

    public String getItemTitle() {
        return itemTitle == null ? "" : itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getWomanMinAge() {
        return womanMinAge == null ? "" : womanMinAge;
    }

    public void setWomanMinAge(String womanMinAge) {
        this.womanMinAge = womanMinAge;
    }

    public String getWomanMaxAge() {
        return womanMaxAge == null ? "" : womanMaxAge;
    }

    public void setWomanMaxAge(String womanMaxAge) {
        this.womanMaxAge = womanMaxAge;
    }
}
