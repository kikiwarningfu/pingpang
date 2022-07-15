package heyong.intellectPinPang.bean.competition;

public class OrderBookBean {


        /**
         * id : 1
         * matchId : 606775612493879936
         * bookName : ZDMJ.PDF
         * bookUrl : http://images.xlttsports.com/ZDMJ.pdf
         * bookSuffix : pdf
         * createUser : 1
         * createTime : 2021-08-03
         * updateUser : 1
         * updateTime : 2021-08-03
         * isDelete : 0
         */

        private int id;
        private long matchId;
        private String bookName;
        private String bookUrl;
        private String bookSuffix;
        private int createUser;
        private String createTime;
        private int updateUser;
        private String updateTime;
        private String isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getBookName() {
        return bookName == null ? "" : bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl == null ? "" : bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookSuffix() {
        return bookSuffix == null ? "" : bookSuffix;
    }

    public void setBookSuffix(String bookSuffix) {
        this.bookSuffix = bookSuffix;
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
