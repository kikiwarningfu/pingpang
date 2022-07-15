package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class TitleListBean {


    /**
     * pageNum : 1
     * pageSize : 1
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 12
     * pages : 12
     * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","name":"王洪福","sex":"1","headImg":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg","category":"1","time":null,"counts":null,"id":512983227553058832,"title":"自由团体","message":"【张三】已退出自由团体","isTop":"1","msgType":"2","relationId":512933466846302224,"receiverId":511486160083128336,"sendId":511486160083128336,"status":"0","createUser":511486160083128336,"createTiem":"2020-11-16 00:00:00","updateUser":511486160083128336,"updateTime":"2020-12-02 00:00:00","isDelete":"0"}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 2
     * lastPage : 8
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4,5,6,7,8]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        if (navigatepageNums == null) {
            return new ArrayList<>();
        }
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * name : 王洪福
         * sex : 1
         * headImg : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
         * category : 1
         * time : null
         * counts : null
         * id : 512983227553058832
         * title : 自由团体
         * message : 【张三】已退出自由团体
         * isTop : 1
         * msgType : 2
         * relationId : 512933466846302224
         * receiverId : 511486160083128336
         * sendId : 511486160083128336
         * status : 0
         * createUser : 511486160083128336
         * createTiem : 2020-11-16 00:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-12-02 00:00:00
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private String name;
        private String nickName;
        private String sex;
        private String headImg;
        private String category;
        private String time;
        private String counts;
        private long id;
        private String title;
        private String message;
        private String isTop;
        private String msgType;
        private long relationId;
        private long receiverId;
        private long sendId;
        private String status;
        private long createUser;
        private String createTiem;
        private long updateUser;
        private String updateTime;
        private String isDelete;

        public int getPageNo() {
            return pageNo;
        }

        public String getNickName() {
            return nickName == null ? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public String getSex() {
            return sex == null ? "1" : sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadImg() {
            return headImg == null ? "" : headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getCategory() {
            return category == null ? "0" : category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCounts() {
            return counts == null ? "" : counts;
        }

        public void setCounts(String counts) {
            this.counts = counts;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message == null ? "" : message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIsTop() {
            return isTop == null ? "" : isTop;
        }

        public void setIsTop(String isTop) {
            this.isTop = isTop;
        }

        public String getMsgType() {
            return msgType == null ? "" : msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public long getRelationId() {
            return relationId;
        }

        public void setRelationId(long relationId) {
            this.relationId = relationId;
        }

        public long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(long receiverId) {
            this.receiverId = receiverId;
        }

        public long getSendId() {
            return sendId;
        }

        public void setSendId(long sendId) {
            this.sendId = sendId;
        }

        public String getStatus() {
            return status == null ? "0" : status;
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

        public String getCreateTiem() {
            return createTiem == null ? "" : createTiem;
        }

        public void setCreateTiem(String createTiem) {
            this.createTiem = createTiem;
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
}
