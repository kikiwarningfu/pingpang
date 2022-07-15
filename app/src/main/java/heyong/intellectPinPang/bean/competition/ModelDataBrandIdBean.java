package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ModelDataBrandIdBean {

        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * xlModelThicks : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":517340571653148689,"attributeValue":"2","modelId":517340571653148688,"attributeType":"1","createUser":511486160083128336,"createTime":"2020-11-28 00:00:00","updateUser":511486160083128336,"updateTime":"2020-11-28 00:00:00","isDelete":"0"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":517340571669925904,"attributeValue":"3","modelId":517340571653148688,"attributeType":"1","createUser":511486160083128336,"createTime":"2020-11-28 00:00:00","updateUser":511486160083128336,"updateTime":"2020-11-28 00:00:00","isDelete":"0"}]
         * xlModelHardness : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":517340571686703120,"attributeValue":"1","modelId":517340571653148688,"attributeType":"2","createUser":511486160083128336,"createTime":"2020-11-28 00:00:00","updateUser":511486160083128336,"updateTime":"2020-11-28 00:00:00","isDelete":"0"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":517340571699286032,"attributeValue":"3","modelId":517340571653148688,"attributeType":"2","createUser":511486160083128336,"createTime":"2020-11-28 00:00:00","updateUser":511486160083128336,"updateTime":"2020-11-28 00:00:00","isDelete":"0"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":517340571711868944,"attributeValue":"5","modelId":517340571653148688,"attributeType":"2","createUser":511486160083128336,"createTime":"2020-11-28 00:00:00","updateUser":511486160083128336,"updateTime":"2020-11-28 00:00:00","isDelete":"0"}]
         * id : 517340571653148688
         * title : ak-911
         * colour : 3
         * brandId : 517295786057830416
         * modelType : null
         * createUser : 511486160083128336
         * createTime : 2020-11-28 00:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-28 00:00:00
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private long id;
        private String title;
        private String colour;
        private long brandId;
        private Object modelType;
        private long createUser;
        private String createTime;
        private long updateUser;
        private String updateTime;
        private String isDelete;
        private List<XlModelThicksBean> xlModelThicks;
        private List<XlModelHardnessBean> xlModelHardness;

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
            return orderBy;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public long getBrandId() {
            return brandId;
        }

        public void setBrandId(long brandId) {
            this.brandId = brandId;
        }

        public Object getModelType() {
            return modelType;
        }

        public void setModelType(Object modelType) {
            this.modelType = modelType;
        }

        public long getCreateUser() {
            return createUser;
        }

        public void setCreateUser(long createUser) {
            this.createUser = createUser;
        }

        public String getCreateTime() {
            return createTime;
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
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public List<XlModelThicksBean> getXlModelThicks() {
            return xlModelThicks;
        }

        public void setXlModelThicks(List<XlModelThicksBean> xlModelThicks) {
            this.xlModelThicks = xlModelThicks;
        }

        public List<XlModelHardnessBean> getXlModelHardness() {
            return xlModelHardness;
        }

        public void setXlModelHardness(List<XlModelHardnessBean> xlModelHardness) {
            this.xlModelHardness = xlModelHardness;
        }

        public static class XlModelThicksBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * id : 517340571653148689
             * attributeValue : 2
             * modelId : 517340571653148688
             * attributeType : 1
             * createUser : 511486160083128336
             * createTime : 2020-11-28 00:00:00
             * updateUser : 511486160083128336
             * updateTime : 2020-11-28 00:00:00
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private long id;
            private String attributeValue;
            private long modelId;
            private String attributeType;
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
                return orderBy;
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

            public String getAttributeValue() {
                return attributeValue;
            }

            public void setAttributeValue(String attributeValue) {
                this.attributeValue = attributeValue;
            }

            public long getModelId() {
                return modelId;
            }

            public void setModelId(long modelId) {
                this.modelId = modelId;
            }

            public String getAttributeType() {
                return attributeType;
            }

            public void setAttributeType(String attributeType) {
                this.attributeType = attributeType;
            }

            public long getCreateUser() {
                return createUser;
            }

            public void setCreateUser(long createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
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
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }
        }

        public static class XlModelHardnessBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * id : 517340571686703120
             * attributeValue : 1
             * modelId : 517340571653148688
             * attributeType : 2
             * createUser : 511486160083128336
             * createTime : 2020-11-28 00:00:00
             * updateUser : 511486160083128336
             * updateTime : 2020-11-28 00:00:00
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private long id;
            private String attributeValue;
            private long modelId;
            private String attributeType;
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
                return orderBy;
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

            public String getAttributeValue() {
                return attributeValue;
            }

            public void setAttributeValue(String attributeValue) {
                this.attributeValue = attributeValue;
            }

            public long getModelId() {
                return modelId;
            }

            public void setModelId(long modelId) {
                this.modelId = modelId;
            }

            public String getAttributeType() {
                return attributeType;
            }

            public void setAttributeType(String attributeType) {
                this.attributeType = attributeType;
            }

            public long getCreateUser() {
                return createUser;
            }

            public void setCreateUser(long createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
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
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }
        }

}
