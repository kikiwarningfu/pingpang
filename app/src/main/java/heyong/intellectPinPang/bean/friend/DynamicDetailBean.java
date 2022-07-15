package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class DynamicDetailBean {


    /**
     * id : 46
     * userId : 555730791365171840
     * content : 魔后请提交
     * areaCode : 0
     * addtime : 1627568139328
     * delTime : null
     * likeCount : 1
     * weekLikeCount : 1
     * commentCount : 3
     * enable : 0
     * type : 2
     * pictureList : [{"filePixels":{"fileUrl":"http://images.xlttsports.com/android_img_20210726111056249"}}]
     * videoList : null
     * dynamicCommentList : [{"id":11,"dynaId":46,"userId":555730791365171840,"content":"要来","addtime":1627634472957,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":null,"dynamicLikeList":[],"currentUserLike":null},{"id":10,"dynaId":46,"userId":555730791365171840,"content":"再来一条","addtime":1627634464293,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":null,"dynamicLikeList":[],"currentUserLike":null},{"id":9,"dynaId":46,"userId":555730791365171840,"content":"大家来评价啊","addtime":1627633700485,"delTime":null,"delFlag":null,"comId":null,"likeFlag":0,"likeCount":null,"dynamicLikeList":[],"currentUserLike":null}]
     * dynamicLikeList : [{"id":19,"dynaId":46,"userId":585843408703476912,"commentId":null,"replyId":null,"addtime":1627635401110,"delFlag":null,"delTime":null}]
     * userIds : null
     * province : 北京市
     * city : 北京市
     * area : 大兴区
     * detailAddr : null
     * address : 北京市北京市大兴区林肯公园A区
     * likeFlag : 0
     * shareCount : 0
     * currentUserLike : null
     */

    private int id;
    private long userId;
    private String content;
    private int areaCode;
    private long addtime;
    private String delTime;
    private int likeCount;
    private int weekLikeCount;
    private int commentCount;
    private int enable;
    private int type;
    private String pictureList;
    private String videoList;
    private String userIds;
    private String province;
    private String city;
    private String area;
    private String detailAddr;
    private String address;
    private int likeFlag;
    private int shareCount;
    private CurrentUserLikeBean currentUserLike;
    private boolean collectVisible;

    public CurrentUserLikeBean getCurrentUserLike() {
        return currentUserLike;
    }

    private List<DynamicCommentListBean> dynamicCommentList;
    private List<DynamicLikeListBean> dynamicLikeList;

    public void setCurrentUserLike(CurrentUserLikeBean currentUserLike) {
        this.currentUserLike = currentUserLike;
    }

    public boolean isCollectVisible() {
        return collectVisible;
    }

    public void setCollectVisible(boolean collectVisible) {
        this.collectVisible = collectVisible;
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

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getWeekLikeCount() {
        return weekLikeCount;
    }

    public void setWeekLikeCount(int weekLikeCount) {
        this.weekLikeCount = weekLikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPictureList() {
        return pictureList == null ? "" : pictureList;
    }

    public void setPictureList(String pictureList) {
        this.pictureList = pictureList;
    }

    public String getVideoList() {
        return videoList == null ? "" : videoList;
    }

    public void setVideoList(String videoList) {
        this.videoList = videoList;
    }

    public String getUserIds() {
        return userIds == null ? "" : userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area == null ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailAddr() {
        return detailAddr == null ? "" : detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }


    public List<DynamicCommentListBean> getDynamicCommentList() {
        if (dynamicCommentList == null) {
            return new ArrayList<>();
        }
        return dynamicCommentList;
    }

    public void setDynamicCommentList(List<DynamicCommentListBean> dynamicCommentList) {
        this.dynamicCommentList = dynamicCommentList;
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

    public static class DynamicCommentListBean {
        /**
         * id : 11
         * dynaId : 46
         * userId : 555730791365171840
         * content : 要来
         * addtime : 1627634472957
         * delTime : null
         * delFlag : null
         * comId : null
         * likeFlag : 0
         * likeCount : null
         * dynamicLikeList : []
         * currentUserLike : null
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
        private String likeCount;
        private int replyCount;
        private String replyList;
        private String replyPid;
        private String picture;
        private boolean hasParent;
        private String replyParent;

        private List<String> dynamicLikeList;
        private CurrentUserLikeBean currentUserLike;

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getReplyList() {
            return replyList == null ? "" : replyList;
        }

        public void setReplyList(String replyList) {
            this.replyList = replyList;
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

        public String getLikeCount() {
            return likeCount == null ? "0" : likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }


        public List<String> getDynamicLikeList() {
            if (dynamicLikeList == null) {
                return new ArrayList<>();
            }
            return dynamicLikeList;
        }

        public void setDynamicLikeList(List<String> dynamicLikeList) {
            this.dynamicLikeList = dynamicLikeList;
        }
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

}
