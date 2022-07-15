package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MyClubListBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 7
         * startRow : 1
         * endRow : 7
         * total : 7
         * pages : 1
         * list : [{"id":515472080981495824,"coverLogo":"http://images.xlttsports.com/android_img_20201123102123491","teamTitle":"测试团队俱乐部","city":"安徽 巢湖 居巢","detailAddress":"奥德赛大所多撒","status":"2","charge":true},{"id":514029346949206032,"coverLogo":"http://images.xlttsports.com/android_img_20201119104823462","teamTitle":"福的俱乐部","city":"天津 天津 和平","detailAddress":"不怕时间段","status":"2","charge":true},{"id":512234257633546256,"coverLogo":"","teamTitle":"狂鸟电竞俱乐部11","city":"山西 运城","detailAddress":"盐湖区","status":"2","charge":true},{"id":512234132873973776,"coverLogo":"","teamTitle":"狂鸟电竞俱乐部1","city":"山西 运城","detailAddress":"盐湖区","status":"2","charge":true},{"id":512196767447027728,"coverLogo":"http://images.xlttsports.com/android_img_20201114092629056","teamTitle":"俱乐部","city":"上海 上海 卢湾","detailAddress":"11","status":"2","charge":true},{"id":512196432695431184,"coverLogo":"http://images.xlttsports.com/android_img_20201114092508841","teamTitle":"自由团体","city":"上海 上海 黄浦","detailAddress":"上海老街","status":"2","charge":true},{"id":512196047566049296,"coverLogo":"http://images.xlttsports.com/android_img_20201114092349902","teamTitle":"1231","city":"北京 北京 东城","detailAddress":"123123","status":"2","charge":true}]
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
             * id : 515472080981495824
             * coverLogo : http://images.xlttsports.com/android_img_20201123102123491
             * teamTitle : 测试团队俱乐部
             * city : 安徽 巢湖 居巢
             * detailAddress : 奥德赛大所多撒
             * status : 2
             * charge : true
             */

            private long id;
            private String coverLogo;
            private String teamTitle;
            private String city;
            private String detailAddress;
            private String status;
            private boolean charge;
            private String clubStatus;

            public String getClubStatus() {
                return clubStatus == null ? "1" : clubStatus;
            }

            public void setClubStatus(String clubStatus) {
                this.clubStatus = clubStatus;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getCoverLogo() {
                return coverLogo;
            }

            public void setCoverLogo(String coverLogo) {
                this.coverLogo = coverLogo;
            }

            public String getTeamTitle() {
                return teamTitle;
            }

            public void setTeamTitle(String teamTitle) {
                this.teamTitle = teamTitle;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDetailAddress() {
                return detailAddress;
            }

            public void setDetailAddress(String detailAddress) {
                this.detailAddress = detailAddress;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public boolean isCharge() {
                return charge;
            }

            public void setCharge(boolean charge) {
                this.charge = charge;
            }
        }

}
