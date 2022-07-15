package heyong.intellectPinPang.bean.live;

import java.util.List;

public class MoneyDetailListBean {


        /**
         * pageNum : 1
         * pageSize : 10
         * size : 1
         * startRow : 1
         * endRow : 1
         * total : 1
         * pages : 1
         * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":617759236445066416,"userId":511486160083128336,"walletId":615598367317578928,"moneyType":"1","detailType":"1","money":"120.00","beginTime":"2021-09-01 16:34:39","endTime":"2021-09-01 16:34:39","status":"1","title":"直播收益","remarks":null,"createTime":"2021-09-01 16:34:39","createUser":511486160083128336,"updateTime":"2021-09-01 16:34:39","updateUser":511486160083128336,"isDelete":"0"}]
         * firstPage : 1
         * prePage : 0
         * nextPage : 0
         * lastPage : 1
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
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
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * id : 617759236445066416
             * userId : 511486160083128336
             * walletId : 615598367317578928
             * moneyType : 1
             * detailType : 1
             * money : 120.00
             * beginTime : 2021-09-01 16:34:39
             * endTime : 2021-09-01 16:34:39
             * status : 1
             * title : 直播收益
             * remarks : null
             * createTime : 2021-09-01 16:34:39
             * createUser : 511486160083128336
             * updateTime : 2021-09-01 16:34:39
             * updateUser : 511486160083128336
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private long id;
            private long userId;
            private long walletId;
            private String moneyType;
            private String detailType;
            private String money;
            private String beginTime;
            private String endTime;
            private String status;
            private String title;
            private String remarks;
            private String createTime;
            private long createUser;
            private String updateTime;
            private long updateUser;
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

            public long getWalletId() {
                return walletId;
            }

            public void setWalletId(long walletId) {
                this.walletId = walletId;
            }

            public String getMoneyType() {
                return moneyType == null ? "" : moneyType;
            }

            public void setMoneyType(String moneyType) {
                this.moneyType = moneyType;
            }

            public String getDetailType() {
                return detailType == null ? "" : detailType;
            }

            public void setDetailType(String detailType) {
                this.detailType = detailType;
            }

            public String getMoney() {
                return money == null ? "" : money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getBeginTime() {
                return beginTime == null ? "" : beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getEndTime() {
                return endTime == null ? "" : endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getStatus() {
                return status == null ? "" : status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRemarks() {
                return remarks == null ? "" : remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateTime() {
                return createTime == null ? "" : createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public long getCreateUser() {
                return createUser;
            }

            public void setCreateUser(long createUser) {
                this.createUser = createUser;
            }

            public String getUpdateTime() {
                return updateTime == null ? "" : updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public long getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(long updateUser) {
                this.updateUser = updateUser;
            }

            public String getIsDelete() {
                return isDelete == null ? "" : isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }
        }

}
