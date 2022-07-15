package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class MyReplyListBean {


    /**
     * id : 23
     * dynaId : 50
     * userId : 585843408703476912
     * content : 和我去永宁
     * addtime : 1628042291935
     * delTime : null
     * delFlag : null
     * comId : null
     * likeFlag : 0
     * likeCount : 1
     * replyCount : 6
     * dynamicLikeList : null
     * replyList : [{"id":7,"dynaId":50,"userId":536930097770355328,"content":"回复的内容","addtime":1628307906378,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":6,"picture":null,"hasParent":true,"replyParent":{"id":6,"dynaId":50,"userId":598806417188585488,"content":"再来点啥","addtime":1628300109592,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null}},{"id":6,"dynaId":50,"userId":598806417188585488,"content":"再来点啥","addtime":1628300109592,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":5,"dynaId":50,"userId":598806417188585488,"content":"来点什么吧","addtime":1628300098961,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":4,"dynaId":50,"userId":585843408703476912,"content":"[打哈气]","addtime":1628230312376,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":3,"dynaId":50,"userId":585843408703476912,"content":"再来一条2","addtime":1628154430668,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":2,"dynaId":50,"userId":585843408703476912,"content":"发一条","addtime":1628154283592,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":0,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null},{"id":1,"dynaId":50,"userId":585843408703476912,"content":"永宁的评论1","addtime":1628046499615,"delTime":null,"delFlag":null,"comId":23,"likeFlag":0,"likeCount":1,"replyCount":0,"dynamicLikeList":null,"replyList":null,"currentUserLike":null,"replyPid":null,"picture":null,"hasParent":false,"replyParent":null}]
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
    private String comId;
    private int likeFlag;
    private int likeCount;
    private int replyCount;
    private String dynamicLikeList;
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
    private String replyPid;
    private String picture;
    private boolean hasParent;
    private String replyParent;
    private List<ReplyListBean> replyList;

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

    public String getComId() {
        return comId == null ? "" : comId;
    }

    public void setComId(String comId) {
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

    public List<ReplyListBean> getReplyList() {
        if (replyList == null) {
            return new ArrayList<>();
        }
        return replyList;
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
        private ReplyParentBean replyParent;

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
    }

}
