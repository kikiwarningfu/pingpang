package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MyMatchBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 1
         * startRow : 1
         * endRow : 1
         * total : 1
         * pages : 1
         * list : [{"status":"2","matchImgUrl":"www.baidu.com","matchTitle":"北京二级运动员晋级赛","sponsor":"北京体育局","beginTime":"2021-12-15","holdCity":"北京 大兴区","venue":"林肯公园","id":512317538144129040,"matchStatus":"1"}]
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
             * status : 2
             * matchImgUrl : www.baidu.com
             * matchTitle : 北京二级运动员晋级赛
             * sponsor : 北京体育局
             * beginTime : 2021-12-15
             * holdCity : 北京 大兴区
             * venue : 林肯公园
             * id : 512317538144129040
             * matchStatus : 1
             */

            private String status;//<!--2.报名中，1.进行中，3已结束-->
            private String matchImgUrl;
            private String matchTitle;
            private String sponsor;
            private String beginTime;
            private String holdCity;
            private String venue;
            private long id;
            private String matchStatus;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMatchImgUrl() {
                return matchImgUrl;
            }

            public void setMatchImgUrl(String matchImgUrl) {
                this.matchImgUrl = matchImgUrl;
            }

            public String getMatchTitle() {
                return matchTitle;
            }

            public void setMatchTitle(String matchTitle) {
                this.matchTitle = matchTitle;
            }

            public String getSponsor() {
                return sponsor;
            }

            public void setSponsor(String sponsor) {
                this.sponsor = sponsor;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getHoldCity() {
                return holdCity;
            }

            public void setHoldCity(String holdCity) {
                this.holdCity = holdCity;
            }

            public String getVenue() {
                return venue;
            }

            public void setVenue(String venue) {
                this.venue = venue;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getMatchStatus() {
                return matchStatus;
            }

            public void setMatchStatus(String matchStatus) {
                this.matchStatus = matchStatus;
            }
        }

}
