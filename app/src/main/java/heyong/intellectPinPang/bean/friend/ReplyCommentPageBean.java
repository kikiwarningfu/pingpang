package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class ReplyCommentPageBean {

    /**
     * total : 4
     * list : [{"id":24,"dynaId":50,"userId":536930097770355328,"content":"图片评论","addtime":1628319910103,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":1,"replyCount":3,"dynamicLikeList":null,"replyList":null,"replyPage":null,"currentUserLike":null,"replyPid":null,"picture":"http://images.xlttsports.com/android_img_20210807150507035","hasParent":false,"replyParent":null},{"id":23,"dynaId":50,"userId":585843408703476912,"content":"和我去永宁","addtime":1628042291935,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":1,"replyCount":10,"dynamicLikeList":null,"replyList":null,"replyPage":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":22,"dynaId":50,"userId":585843408703476912,"content":"哇哈哈哈哈","addtime":1628042272598,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":1,"replyCount":0,"dynamicLikeList":null,"replyList":null,"replyPage":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":18,"dynaId":50,"userId":585843408703476912,"content":"评价内容","addtime":1627953964859,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"replyPage":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null}]
     * pageNum : 1
     * pageSize : 10
     * size : 4
     * startRow : 1
     * endRow : 4
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     */

    private int total;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
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

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
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

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * id : 24
         * dynaId : 50
         * userId : 536930097770355328
         * content : 图片评论
         * addtime : 1628319910103
         * delTime : null
         * delFlag : null
         * comId : null
         * likeFlag : 0
         * likeCount : 1
         * replyCount : 3
         * dynamicLikeList : null
         * replyList : null
         * replyPage : null
         * currentUserLike : null
         * replyPid : null
         * picture : http://images.xlttsports.com/android_img_20210807150507035
         * hasParent : false
         * replyParent : null
         */

        private int id;
        private int dynaId;
        private long userId;
        private String content;
        private long addtime;
        private String delTime;
        private String delFlag;
        private String comId;
        private int likeFlag;
        private int likeCount;
        private int replyCount;
        private String replyList;
        private String replyPage;
        private CurrentUserLikeBean currentUserLike;

        private String replyPid;
        private String picture;
        private boolean hasParent;
        private String replyParent;

        public CurrentUserLikeBean getCurrentUserLike() {
            return currentUserLike;
        }

        private List<DynamicLikeListBean> dynamicLikeList;

        public void setCurrentUserLike(CurrentUserLikeBean currentUserLike) {
            this.currentUserLike = currentUserLike;
        }


        public List<DynamicLikeListBean> getDynamicLikeList() {
            if (dynamicLikeList == null) {
                return new ArrayList<>();
            }
            return dynamicLikeList;
        }

        public void setDynamicLikeList(List<DynamicLikeListBean> dynamicLikeList) {
            this.dynamicLikeList = dynamicLikeList;
        }

        public static class DynamicLikeListBean {
            /**
             * id : 19
             * dynaId : 46
             * userId : 585843408703476912
             * commentId : null
             * replyId : null
             * addtime : 1627635401110
             * delFlag : null
             * delTime : null
             */

            private int id;
            private int dynaId;
            private long userId;
            private String commentId;
            private String replyId;
            private long addtime;
            private String delFlag;
            private String delTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDynaId() {
                return dynaId;
            }

            public void setDynaId(int dynaId) {
                this.dynaId = dynaId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getCommentId() {
                return commentId == null ? "" : commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getReplyId() {
                return replyId == null ? "" : replyId;
            }

            public void setReplyId(String replyId) {
                this.replyId = replyId;
            }

            public long getAddtime() {
                return addtime;
            }

            public void setAddtime(long addtime) {
                this.addtime = addtime;
            }

            public String getDelFlag() {
                return delFlag == null ? "" : delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getDelTime() {
                return delTime == null ? "" : delTime;
            }

            public void setDelTime(String delTime) {
                this.delTime = delTime;
            }
        }

        public static class CurrentUserLikeBean {
            /**
             * id : 19
             * dynaId : 46
             * userId : 585843408703476912
             * commentId : null
             * replyId : null
             * addtime : 1627635401110
             * delFlag : null
             * delTime : null
             */

            private int id;
            private int dynaId;
            private long userId;
            private String commentId;
            private String replyId;
            private long addtime;
            private String delFlag;
            private String delTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDynaId() {
                return dynaId;
            }

            public void setDynaId(int dynaId) {
                this.dynaId = dynaId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getCommentId() {
                return commentId == null ? "" : commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getReplyId() {
                return replyId == null ? "" : replyId;
            }

            public void setReplyId(String replyId) {
                this.replyId = replyId;
            }

            public long getAddtime() {
                return addtime;
            }

            public void setAddtime(long addtime) {
                this.addtime = addtime;
            }

            public String getDelFlag() {
                return delFlag == null ? "" : delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getDelTime() {
                return delTime == null ? "" : delTime;
            }

            public void setDelTime(String delTime) {
                this.delTime = delTime;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDynaId() {
            return dynaId;
        }

        public void setDynaId(int dynaId) {
            this.dynaId = dynaId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }


        public int getLikeFlag() {
            return likeFlag;
        }

        public void setLikeFlag(int likeFlag) {
            this.likeFlag = likeFlag;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }


        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public boolean isHasParent() {
            return hasParent;
        }

        public void setHasParent(boolean hasParent) {
            this.hasParent = hasParent;
        }

        public String getDelTime() {
            return delTime == null ? "" : delTime;
        }

        public void setDelTime(String delTime) {
            this.delTime = delTime;
        }

        public String getDelFlag() {
            return delFlag == null ? "" : delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getComId() {
            return comId == null ? "" : comId;
        }

        public void setComId(String comId) {
            this.comId = comId;
        }

        public String getReplyList() {
            return replyList == null ? "" : replyList;
        }

        public void setReplyList(String replyList) {
            this.replyList = replyList;
        }

        public String getReplyPage() {
            return replyPage == null ? "" : replyPage;
        }

        public void setReplyPage(String replyPage) {
            this.replyPage = replyPage;
        }

        public String getReplyPid() {
            return replyPid == null ? "" : replyPid;
        }

        public void setReplyPid(String replyPid) {
            this.replyPid = replyPid;
        }

        public String getReplyParent() {
            return replyParent == null ? "" : replyParent;
        }

        public void setReplyParent(String replyParent) {
            this.replyParent = replyParent;
        }
    }

}
