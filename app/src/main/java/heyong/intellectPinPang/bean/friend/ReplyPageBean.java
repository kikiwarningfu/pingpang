package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class ReplyPageBean {


        /**
         * total : 11
         * pageNum : 1
         * pageSize : 10
         * size : 10
         * startRow : 1
         * endRow : 10
         * pages : 2
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2]
         * navigateFirstPage : 1
         * navigateLastPage : 2
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
             * id : 11
             * dynaId : 50
             * userId : 536930097770355328
             * content : 来一张图片谭木匠
             * addtime : 1628319230938
             * delTime : null
             * delFlag : null
             * comId : 23
             * likeFlag : 0
             * likeCount : 0
             * replyCount : 0
             * dynamicLikeList : null
             * replyList : null
             * replyPage : null
             * currentUserLike : null
             * replyPid : null
             * picture : http://images.xlttsports.com/android_img_20210807145348278
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
            private int comId;
            private int likeFlag;
            private int likeCount;
            private int replyCount;
            private String dynamicLikeList;
            private List<ReplyListBean> replyList;
            private String replyPage;
            private String replyPid;
            private String picture;
            private boolean hasParent;

            private ReplyParentBean replyParent;
            public ReplyParentBean getReplyParent() {
                return replyParent;
            }

            public void setReplyParent(ReplyParentBean replyParent) {
                this.replyParent = replyParent;
            }

            public static class ReplyParentBean {
                /**
                 * id : 6
                 * dynaId : 50
                 * userId : 598806417188585488
                 * content : 再来点啥
                 * addtime : 1628300109592
                 * delTime : null
                 * delFlag : null
                 * comId : 23
                 * likeFlag : 0
                 * likeCount : 0
                 * replyCount : 0
                 * dynamicLikeList : null
                 * replyList : null
                 * currentUserLike : null
                 * replyPid : null
                 * picture : null
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
                private int comId;
                private int likeFlag;
                private int likeCount;
                private int replyCount;
                private String dynamicLikeList;
                private String replyList;
                private String currentUserLike;
                private String replyPid;
                private String picture;
                private boolean hasParent;
                private String replyParent;

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
                    return content == null ? "" : content;
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

                public int getComId() {
                    return comId;
                }

                public void setComId(int comId) {
                    this.comId = comId;
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

                public String getDynamicLikeList() {
                    return dynamicLikeList == null ? "" : dynamicLikeList;
                }

                public void setDynamicLikeList(String dynamicLikeList) {
                    this.dynamicLikeList = dynamicLikeList;
                }

                public String getReplyList() {
                    return replyList == null ? "" : replyList;
                }

                public void setReplyList(String replyList) {
                    this.replyList = replyList;
                }

                public String getCurrentUserLike() {
                    return currentUserLike == null ? "" : currentUserLike;
                }

                public void setCurrentUserLike(String currentUserLike) {
                    this.currentUserLike = currentUserLike;
                }

                public String getReplyPid() {
                    return replyPid == null ? "" : replyPid;
                }

                public void setReplyPid(String replyPid) {
                    this.replyPid = replyPid;
                }

                public String getPicture() {
                    return picture == null ? "" : picture;
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

                public String getReplyParent() {
                    return replyParent == null ? "" : replyParent;
                }

                public void setReplyParent(String replyParent) {
                    this.replyParent = replyParent;
                }
            }
            private CurrentUserLikeBean currentUserLike;

            public CurrentUserLikeBean getCurrentUserLike() {
                return currentUserLike;
            }

            public void setCurrentUserLike(CurrentUserLikeBean currentUserLike) {
                this.currentUserLike = currentUserLike;
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
            public List<ReplyListBean> getReplyList() {
                if (replyList == null) {
                    return new ArrayList<>();
                }
                return replyList;
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
                return content == null ? "" : content;
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

            public int getComId() {
                return comId;
            }

            public void setComId(int comId) {
                this.comId = comId;
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

            public String getDynamicLikeList() {
                return dynamicLikeList == null ? "" : dynamicLikeList;
            }

            public void setDynamicLikeList(String dynamicLikeList) {
                this.dynamicLikeList = dynamicLikeList;
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

            public String getPicture() {
                return picture == null ? "" : picture;
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



            public void setReplyList(List<ReplyListBean> replyList) {
                this.replyList = replyList;
            }


            public static class ReplyListBean {
                /**
                 * id : 7
                 * dynaId : 50
                 * userId : 536930097770355328
                 * content : 回复的内容
                 * addtime : 1628307906378
                 * delTime : null
                 * delFlag : null
                 * comId : 23
                 * likeFlag : 0
                 * likeCount : 0
                 * replyCount : 0
                 * dynamicLikeList : null
                 * replyList : null
                 * currentUserLike : null
                 * replyPid : 6
                 * picture : null
                 * hasParent : true
                 * replyParent : {"id":6,"dynaId":50,"userId":598806417188585488,"content":"再来点啥","addtime":1628300109592,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null}
                 */

                private String id;
                private int dynaId;
                private long userId;
                private String content;
                private long addtime;
                private String delTime;
                private String delFlag;
                private int comId;
                private int likeFlag;
                private int likeCount;
                private int replyCount;
                private String dynamicLikeList;
                private String replyList;
                private CurrentUserLikeBeanX currentUserLike;

                public CurrentUserLikeBeanX getCurrentUserLike() {
                    return currentUserLike;
                }

                public void setCurrentUserLike(CurrentUserLikeBeanX currentUserLike) {
                    this.currentUserLike = currentUserLike;
                }

                private int replyPid;
                private String picture;
                private boolean hasParent;
                private ReplyParentBeanX replyParent;

                public static class CurrentUserLikeBeanX {
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

                public String getId() {
                    return id == null ? "" : id;
                }

                public void setId(String id) {
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
                    return content == null ? "" : content;
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

                public int getComId() {
                    return comId;
                }

                public void setComId(int comId) {
                    this.comId = comId;
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

                public String getDynamicLikeList() {
                    return dynamicLikeList == null ? "" : dynamicLikeList;
                }

                public void setDynamicLikeList(String dynamicLikeList) {
                    this.dynamicLikeList = dynamicLikeList;
                }

                public String getReplyList() {
                    return replyList == null ? "" : replyList;
                }

                public void setReplyList(String replyList) {
                    this.replyList = replyList;
                }


                public int getReplyPid() {
                    return replyPid;
                }

                public void setReplyPid(int replyPid) {
                    this.replyPid = replyPid;
                }

                public String getPicture() {
                    return picture == null ? "" : picture;
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

                public ReplyParentBeanX getReplyParent() {
                    return replyParent;
                }

                public void setReplyParent(ReplyParentBeanX replyParent) {
                    this.replyParent = replyParent;
                }

                public static class ReplyParentBeanX {
                    /**
                     * id : 6
                     * dynaId : 50
                     * userId : 598806417188585488
                     * content : 再来点啥
                     * addtime : 1628300109592
                     * delTime : null
                     * delFlag : null
                     * comId : 23
                     * likeFlag : 0
                     * likeCount : 0
                     * replyCount : 0
                     * dynamicLikeList : null
                     * replyList : null
                     * currentUserLike : null
                     * replyPid : null
                     * picture : null
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
                    private int comId;
                    private int likeFlag;
                    private int likeCount;
                    private int replyCount;
                    private String dynamicLikeList;
                    private String replyList;
                    private String currentUserLike;
                    private String replyPid;
                    private String picture;
                    private boolean hasParent;
                    private String replyParent;

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
                        return content == null ? "" : content;
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

                    public int getComId() {
                        return comId;
                    }

                    public void setComId(int comId) {
                        this.comId = comId;
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

                    public String getDynamicLikeList() {
                        return dynamicLikeList == null ? "" : dynamicLikeList;
                    }

                    public void setDynamicLikeList(String dynamicLikeList) {
                        this.dynamicLikeList = dynamicLikeList;
                    }

                    public String getReplyList() {
                        return replyList == null ? "" : replyList;
                    }

                    public void setReplyList(String replyList) {
                        this.replyList = replyList;
                    }

                    public String getCurrentUserLike() {
                        return currentUserLike == null ? "" : currentUserLike;
                    }

                    public void setCurrentUserLike(String currentUserLike) {
                        this.currentUserLike = currentUserLike;
                    }

                    public String getReplyPid() {
                        return replyPid == null ? "" : replyPid;
                    }

                    public void setReplyPid(String replyPid) {
                        this.replyPid = replyPid;
                    }

                    public String getPicture() {
                        return picture == null ? "" : picture;
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

                    public String getReplyParent() {
                        return replyParent == null ? "" : replyParent;
                    }

                    public void setReplyParent(String replyParent) {
                        this.replyParent = replyParent;
                    }
                }
            }
        }

}
