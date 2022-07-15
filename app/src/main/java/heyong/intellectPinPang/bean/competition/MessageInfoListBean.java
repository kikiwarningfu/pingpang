package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MessageInfoListBean {


        /**
         * counts : 0
         * messages : [{"title":{"pageNo":1,"pageSize":10,"orderBy":"id desc","logoType":"2","coverLogo":"http://images.xlttsports.com/android_img_20201114092508841","category":null,"time":"2020-12-01","counts":12,"id":512989672671580176,"title":"自由团体","message":"您申请加入自由团体的申请已通过","isTop":"1","msgType":"2","relationId":512933466846302224,"receiverId":511486160083128336,"sendId":511486160083128336,"status":"0","createUser":511486160083128336,"createTiem":"2020-12-01 00:00:01","updateUser":511486160083128336,"updateTime":"2020-12-02 00:00:00","isDelete":"0"}},{"title":{"pageNo":1,"pageSize":10,"orderBy":"id desc","logoType":"2","coverLogo":"","category":null,"time":"昨天","counts":4,"id":518793264271757328,"title":"狂鸟电竞俱乐部1","message":"您申请加入狂鸟电竞俱乐部1的申请已通过","isTop":"0","msgType":"2","relationId":518792256342757392,"receiverId":511486160083128336,"sendId":511486160083128336,"status":"0","createUser":511486160083128336,"createTiem":"2020-12-02 14:19:13","updateUser":511486160083128336,"updateTime":"2020-12-02 14:19:13","isDelete":"0"}}]
         */

        private int counts;
        private List<MessagesBean> messages;

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public static class MessagesBean {
            /**
             * title : {"pageNo":1,"pageSize":10,"orderBy":"id desc","logoType":"2","coverLogo":"http://images.xlttsports.com/android_img_20201114092508841","category":null,"time":"2020-12-01","counts":12,"id":512989672671580176,"title":"自由团体","message":"您申请加入自由团体的申请已通过","isTop":"1","msgType":"2","relationId":512933466846302224,"receiverId":511486160083128336,"sendId":511486160083128336,"status":"0","createUser":511486160083128336,"createTiem":"2020-12-01 00:00:01","updateUser":511486160083128336,"updateTime":"2020-12-02 00:00:00","isDelete":"0"}
             */

            private TitleBean title;

            public TitleBean getTitle() {
                return title;
            }

            public void setTitle(TitleBean title) {
                this.title = title;
            }

            public static class TitleBean {
                /**
                 * pageNo : 1
                 * pageSize : 10
                 * orderBy : id desc
                 * logoType : 2
                 * coverLogo : http://images.xlttsports.com/android_img_20201114092508841
                 * category : null
                 * time : 2020-12-01
                 * counts : 12
                 * id : 512989672671580176
                 * title : 自由团体
                 * message : 您申请加入自由团体的申请已通过
                 * isTop : 1
                 * msgType : 2
                 * relationId : 512933466846302224
                 * receiverId : 511486160083128336
                 * sendId : 511486160083128336
                 * status : 0
                 * createUser : 511486160083128336
                 * createTiem : 2020-12-01 00:00:01
                 * updateUser : 511486160083128336
                 * updateTime : 2020-12-02 00:00:00
                 * isDelete : 0
                 */

                private int pageNo;
                private int pageSize;
                private String orderBy;
                private String logoType;
                private String coverLogo;
                private String category;
                private String time;
                private int counts;
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

                public String getLogoType() {
                    return logoType == null ? "" : logoType;
                }

                public void setLogoType(String logoType) {
                    this.logoType = logoType;
                }

                public String getCoverLogo() {
                    return coverLogo == null ? "" : coverLogo;
                }

                public void setCoverLogo(String coverLogo) {
                    this.coverLogo = coverLogo;
                }

                public String getCategory() {
                    return category == null ? "" : category;
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

                public int getCounts() {
                    return counts;
                }

                public void setCounts(int counts) {
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
                    return isTop == null ? "0" : isTop;
                }

                public void setIsTop(String isTop) {
                    this.isTop = isTop;
                }

                public String getMsgType() {
                    return msgType == null ? "1" : msgType;
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
}
