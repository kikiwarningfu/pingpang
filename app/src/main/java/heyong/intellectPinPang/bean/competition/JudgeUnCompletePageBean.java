package heyong.intellectPinPang.bean.competition;

public class JudgeUnCompletePageBean {

        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * id : 534758602951774640
         * userId : 511486160083128336
         * param : {"angle":"136","appOperationLogId":534692132859302320,"arrangeId":1,"leftClubId":514049889534513168,"leftClubName":"战狼","leftClubType":"2","leftOrRight":"1","matchId":512317538144129040,"rightClubId":514049889534513168,"rightClubImg":"http://images.xlttsports.com/20201119120745029662.png","rightClubName":"野狼","rightClubType":"1","title":"男子单打循环赛"}
         * url : matchOperation/fillInMatchBaseInfo
         * page : 2
         * status : 1
         * createUser : 511486160083128336
         * createTime : 2021-01-15 15:39:46
         * updateUser : 511486160083128336
         * updateTime : 2021-01-15 15:39:46
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private long id;
        private long userId;
        private String param;
        private String url;
        private String page;
        private String status;
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

    public String getParam() {
        return param == null ? "" : param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPage() {
        return page == null ? "1" : page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
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
