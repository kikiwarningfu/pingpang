package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class EnListBean implements Serializable{


        /**
         * pageNum : 1
         * pageSize : 10
         * size : 1
         * startRow : 1
         * endRow : 1
         * total : 1
         * pages : 1
         * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","matchTitle":"北京二级运动员晋级赛","id":1,"matchId":512317538144129040,"enrollId":null,"enrollTypet":null,"enrollFree":null,"personCount":1,"needEat":"1","eatFree":1,"eatCount":1,"allFree":1,"freeType":"1","freeStatus":"1","createUser":511486160083128336,"createTime":"2020-12-02 00:00:00","updateUser":1,"updateTime":"2020-12-02 00:00:00","isDelete":"0"}]
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

        public static class ListBean implements Serializable {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * matchTitle : 北京二级运动员晋级赛
             * id : 1
             * matchId : 512317538144129040
             * enrollId : null
             * enrollTypet : null
             * enrollFree : null
             * personCount : 1
             * needEat : 1
             * eatFree : 1
             * eatCount : 1
             * allFree : 1
             * freeType : 1
             * freeStatus : 1
             * createUser : 511486160083128336
             * createTime : 2020-12-02 00:00:00
             * updateUser : 1
             * updateTime : 2020-12-02 00:00:00
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String matchTitle;
            private long id;
            private long matchId;
            private String enrollId;
            private int enrollTypet;
            private String enrollFree;
            private int personCount;
            private String needEat;
            private int eatFree;
            private int eatCount;
            private int allFree;
            private String freeType;
            private String freeStatus;
            private long createUser;
            private String createTime;
            private int updateUser;
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

            public String getMatchTitle() {
                return matchTitle == null ? "" : matchTitle;
            }

            public void setMatchTitle(String matchTitle) {
                this.matchTitle = matchTitle;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public String getEnrollId() {
                return enrollId == null ? "" : enrollId;
            }

            public void setEnrollId(String enrollId) {
                this.enrollId = enrollId;
            }

            public int getEnrollTypet() {
                return enrollTypet;
            }

            public void setEnrollTypet(int enrollTypet) {
                this.enrollTypet = enrollTypet;
            }

            public String getEnrollFree() {
                return enrollFree == null ? "" : enrollFree;
            }

            public void setEnrollFree(String enrollFree) {
                this.enrollFree = enrollFree;
            }

            public int getPersonCount() {
                return personCount;
            }

            public void setPersonCount(int personCount) {
                this.personCount = personCount;
            }

            public String getNeedEat() {
                return needEat == null ? "" : needEat;
            }

            public void setNeedEat(String needEat) {
                this.needEat = needEat;
            }

            public int getEatFree() {
                return eatFree;
            }

            public void setEatFree(int eatFree) {
                this.eatFree = eatFree;
            }

            public int getEatCount() {
                return eatCount;
            }

            public void setEatCount(int eatCount) {
                this.eatCount = eatCount;
            }

            public int getAllFree() {
                return allFree;
            }

            public void setAllFree(int allFree) {
                this.allFree = allFree;
            }

            public String getFreeType() {
                return freeType == null ? "" : freeType;
            }

            public void setFreeType(String freeType) {
                this.freeType = freeType;
            }

            public String getFreeStatus() {
                return freeStatus == null ? "" : freeStatus;
            }

            public void setFreeStatus(String freeStatus) {
                this.freeStatus = freeStatus;
            }

            public long getCreateUser() {
                return createUser;
            }

            public void setCreateUser(long createUser) {
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

}
