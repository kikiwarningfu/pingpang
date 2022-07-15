package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class DynamicListBean {


    /**
     * total : 32
     * list : [{"id":44,"userId":585843408703476912,"content":"那个不","areaCode":150908,"addtime":1627438089927,"delTime":null,"likeCount":1,"weekLikeCount":1,"commentCount":1,"enable":0,"type":1,"pictureList":null,"videoList":null,"dynamicCommentList":[{"id":13,"dynaId":44,"userId":585843408703476912,"content":"发布一个","addtime":1627702387838,"delTime":null,"delFlag":null,"comId":null,"likeFlag":1,"likeCount":1,"replyCount":0,"dynamicLikeList":null,"currentUserLike":{"id":24,"dynaId":44,"userId":585843408703476912,"commentId":13,"replyId":null,"addtime":1627702395650,"delFlag":null,"delTime":null}}],"dynamicLikeList":null,"userIds":null,"province":"北京市","city":"北京市","area":"大兴区","detailAddr":null,"address":"北京市北京市大兴区林肯公园A区停车场(出口)","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":43,"userId":585843408703476912,"content":"再发布一个","areaCode":0,"addtime":1627438062601,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":1,"pictureList":null,"videoList":null,"dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"null","city":"null","area":"null","detailAddr":null,"address":"nullnullnull","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":42,"userId":585843408703476912,"content":"发布行吧","areaCode":150904,"addtime":1627437955539,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":2,"pictureList":"[{\"filePixels\":{\"fileUrl\":\"http://images.xlttsports.com/android_img_20210728100542378\"}}]","videoList":null,"dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"北京市","city":"北京市","area":"大兴区","detailAddr":null,"address":"北京市北京市大兴区林肯公园A区停车场","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":41,"userId":598806417188585488,"content":"发布一个视频","areaCode":120302,"addtime":1627367025709,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":3,"pictureList":null,"videoList":"[{\"filePixels\":{\"fileUrl\":\"http://images.xlttsports.com/android_VIDEO_20210727_142308.mp4\"}}]","dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"北京市","city":"北京市","area":"大兴区","detailAddr":null,"address":"北京市北京市大兴区林肯公园A区","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":40,"userId":598806417188585488,"content":"发布一下","areaCode":0,"addtime":1627356907197,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":3,"pictureList":null,"videoList":"[{\"filePixels\":{\"fileUrl\":\"http://images.xlttsports.com/android_VIDEO_20210727_113430.mp4\"}}]","dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"null","city":"null","area":"null","detailAddr":null,"address":"nullnullnull","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":39,"userId":598806417188585488,"content":"再发布一个","areaCode":120302,"addtime":1627356341154,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":3,"pictureList":null,"videoList":"[{\"filePixels\":{\"fileUrl\":\"http://images.xlttsports.com/android_VIDEO_20210727_112517.mp4\"}}]","dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"北京市","city":"北京市","area":"大兴区","detailAddr":null,"address":"北京市北京市大兴区林肯公园A区","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":38,"userId":598806417188585488,"content":"再发布一个视频","areaCode":120302,"addtime":1627356122147,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":3,"pictureList":null,"videoList":"[]","dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"北京市","city":"北京市","area":"大兴区","detailAddr":null,"address":"北京市北京市大兴区林肯公园A区","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":37,"userId":598806417188585488,"content":"发布一个视频","areaCode":0,"addtime":1627355663073,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":3,"pictureList":null,"videoList":"[]","dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"null","city":"null","area":"null","detailAddr":null,"address":"nullnullnull","likeFlag":0,"shareCount":0,"currentUserLike":null},{"id":36,"userId":598806417188585488,"content":"你猜猜","areaCode":0,"addtime":1627355491461,"delTime":null,"likeCount":0,"weekLikeCount":0,"commentCount":0,"enable":0,"type":1,"pictureList":null,"videoList":null,"dynamicCommentList":null,"dynamicLikeList":null,"userIds":null,"province":"null","city":"null","area":"null","detailAddr":null,"address":"nullnullnull","likeFlag":0,"shareCount":0,"currentUserLike":null}]
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * pages : 4
     * prePage : 0
     * nextPage : 2
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4]
     * navigateFirstPage : 1
     * navigateLastPage : 4
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
         * id : 44
         * userId : 585843408703476912
         * content : 那个不
         * areaCode : 150908
         * addtime : 1627438089927
         * delTime : null
         * likeCount : 1
         * weekLikeCount : 1
         * commentCount : 1
         * enable : 0
         * type : 1
         * pictureList : null
         * videoList : null
         * dynamicCommentList : [{"id":13,"dynaId":44,"userId":585843408703476912,"content":"发布一个","addtime":1627702387838,"delTime":null,"delFlag":null,"comId":null,"likeFlag":1,"likeCount":1,"replyCount":0,"dynamicLikeList":null,"currentUserLike":{"id":24,"dynaId":44,"userId":585843408703476912,"commentId":13,"replyId":null,"addtime":1627702395650,"delFlag":null,"delTime":null}}]
         * dynamicLikeList : null
         * userIds : null
         * province : 北京市
         * city : 北京市
         * area : 大兴区
         * detailAddr : null
         * address : 北京市北京市大兴区林肯公园A区停车场(出口)
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
        private String dynamicLikeList;
        private String userIds;
        private String province;
        private String city;
        private String area;
        private String detailAddr;
        private String address;
        private int likeFlag;
        private int shareCount;
        private CurrentUserLikeBean currentUserLike;
        private List<DynamicCommentListBean> dynamicCommentList;
        private List<String>mypictureList;
        private List<String>myvideoList;

        public List<String> getMypictureList() {
            if (mypictureList == null) {
                return new ArrayList<>();
            }
            return mypictureList;
        }

        public void setMypictureList(List<String> mypictureList) {
            this.mypictureList = mypictureList;
        }

        public List<String> getMyvideoList() {
            if (myvideoList == null) {
                return new ArrayList<>();
            }
            return myvideoList;
        }

        public void setMyvideoList(List<String> myvideoList) {
            this.myvideoList = myvideoList;
        }

        public void setDelTime(String delTime) {
            this.delTime = delTime;
        }

        public void setPictureList(String pictureList) {
            this.pictureList = pictureList;
        }

        public void setVideoList(String videoList) {
            this.videoList = videoList;
        }

        public void setDynamicLikeList(String dynamicLikeList) {
            this.dynamicLikeList = dynamicLikeList;
        }

        public void setUserIds(String userIds) {
            this.userIds = userIds;
        }

        public void setDetailAddr(String detailAddr) {
            this.detailAddr = detailAddr;
        }

        public void setCurrentUserLike(CurrentUserLikeBean currentUserLike) {
            this.currentUserLike = currentUserLike;
        }

        public static class CurrentUserLikeBean {
            /**
             * id : 24
             * dynaId : 44
             * userId : 585843408703476912
             * commentId : 13
             * replyId : null
             * addtime : 1627702395650
             * delFlag : null
             * delTime : null
             */

            private int id;
            private int dynaId;
            private long userId;
            private int commentId;
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

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
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

        public String getVideoList() {
            return videoList == null ? "" : videoList;
        }

        public String getDynamicLikeList() {
            return dynamicLikeList == null ? "" : dynamicLikeList;
        }

        public String getUserIds() {
            return userIds == null ? "" : userIds;
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

        public CurrentUserLikeBean getCurrentUserLike() {
            return currentUserLike;
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

        public static class DynamicCommentListBean {
            /**
             * id : 13
             * dynaId : 44
             * userId : 585843408703476912
             * content : 发布一个
             * addtime : 1627702387838
             * delTime : null
             * delFlag : null
             * comId : null
             * likeFlag : 1
             * likeCount : 1
             * replyCount : 0
             * dynamicLikeList : null
             * currentUserLike : {"id":24,"dynaId":44,"userId":585843408703476912,"commentId":13,"replyId":null,"addtime":1627702395650,"delFlag":null,"delTime":null}
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

            public CurrentUserLikeBean getCurrentUserLike() {
                return currentUserLike;
            }

            public void setCurrentUserLike(CurrentUserLikeBean currentUserLike) {
                this.currentUserLike = currentUserLike;
            }

            public static class CurrentUserLikeBean {
                /**
                 * id : 24
                 * dynaId : 44
                 * userId : 585843408703476912
                 * commentId : 13
                 * replyId : null
                 * addtime : 1627702395650
                 * delFlag : null
                 * delTime : null
                 */

                private int id;
                private int dynaId;
                private long userId;
                private int commentId;
                private Object replyId;
                private long addtime;
                private Object delFlag;
                private Object delTime;

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

                public int getCommentId() {
                    return commentId;
                }

                public void setCommentId(int commentId) {
                    this.commentId = commentId;
                }

                public Object getReplyId() {
                    return replyId;
                }

                public void setReplyId(Object replyId) {
                    this.replyId = replyId;
                }

                public long getAddtime() {
                    return addtime;
                }

                public void setAddtime(long addtime) {
                    this.addtime = addtime;
                }

                public Object getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(Object delFlag) {
                    this.delFlag = delFlag;
                }

                public Object getDelTime() {
                    return delTime;
                }

                public void setDelTime(Object delTime) {
                    this.delTime = delTime;
                }
            }
        }
    }

}
