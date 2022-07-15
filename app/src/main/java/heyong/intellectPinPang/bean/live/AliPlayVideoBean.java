package heyong.intellectPinPang.bean.live;

import java.util.List;

public class AliPlayVideoBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 1
         * startRow : 1
         * endRow : 1
         * total : 1
         * pages : 1
         * list : [{"requestId":1,"matchId":512317538144129040,"tableNum":3,"matchName":"北京二级运动员晋级赛","title":"2021-02-01 15:08 男双打 []","gameTime":"2021-02-01 15:08:00","address":"北京 大兴区林肯公园","money":0.01,"userMoney":0.008,"left":[{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"},{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"}],"right":[{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"},{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"}],"orderId":1,"payStatus":1,"reFundFailMsg":null,"isAppraise":1,"payType":2,"receiveStatus":4,"isWithDraw":0,"matchMethod":null,"publicMoney":null,"selfMoney":null,"liveType":1,"createTime":"2021-02-01 10:06:45"}]
         * firstPage : 0
         * prePage : 0
         * nextPage : 0
         * lastPage : 0
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
             * requestId : 1
             * matchId : 512317538144129040
             * tableNum : 3
             * matchName : 北京二级运动员晋级赛
             * title : 2021-02-01 15:08 男双打 []
             * gameTime : 2021-02-01 15:08:00
             * address : 北京 大兴区林肯公园
             * money : 0.01
             * userMoney : 0.008
             * left : [{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"},{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"}]
             * right : [{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"},{"id":511486160083128336,"name":"王洪福","logo":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg"}]
             * orderId : 1
             * payStatus : 1
             * reFundFailMsg : null
             * isAppraise : 1
             * payType : 2
             * receiveStatus : 4
             * isWithDraw : 0
             * matchMethod : null
             * publicMoney : null
             * selfMoney : null
             * liveType : 1
             * createTime : 2021-02-01 10:06:45
             */

            private int requestId;
            private long matchId;
            private int tableNum;
            private String matchName;
            private String title;
            private String gameTime;
            private String address;
            private double money;
            private double userMoney;
            private int orderId;
            private int payStatus;
            private String reFundFailMsg;
            private int isAppraise;
            private int payType;
            private int receiveStatus;
            private int isWithDraw;
            private String matchMethod;
            private String publicMoney;
            private String selfMoney;
            private int liveType;
            private String createTime;
            private List<LeftBean> left;
            private List<RightBean> right;
            private long closeSec;

            public long getCloseSec() {
                return closeSec;
            }

            public void setCloseSec(long closeSec) {
                this.closeSec = closeSec;
            }

            public int getRequestId() {
                return requestId;
            }

            public void setRequestId(int requestId) {
                this.requestId = requestId;
            }

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public int getTableNum() {
                return tableNum;
            }

            public void setTableNum(int tableNum) {
                this.tableNum = tableNum;
            }

            public String getMatchName() {
                return matchName == null ? "" : matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGameTime() {
                return gameTime == null ? "" : gameTime;
            }

            public void setGameTime(String gameTime) {
                this.gameTime = gameTime;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public double getUserMoney() {
                return userMoney;
            }

            public void setUserMoney(double userMoney) {
                this.userMoney = userMoney;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public String getReFundFailMsg() {
                return reFundFailMsg == null ? "" : reFundFailMsg;
            }

            public void setReFundFailMsg(String reFundFailMsg) {
                this.reFundFailMsg = reFundFailMsg;
            }

            public int getIsAppraise() {
                return isAppraise;
            }

            public void setIsAppraise(int isAppraise) {
                this.isAppraise = isAppraise;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public int getReceiveStatus() {
                return receiveStatus;
            }

            public void setReceiveStatus(int receiveStatus) {
                this.receiveStatus = receiveStatus;
            }

            public int getIsWithDraw() {
                return isWithDraw;
            }

            public void setIsWithDraw(int isWithDraw) {
                this.isWithDraw = isWithDraw;
            }

            public String getMatchMethod() {
                return matchMethod == null ? "" : matchMethod;
            }

            public void setMatchMethod(String matchMethod) {
                this.matchMethod = matchMethod;
            }

            public String getPublicMoney() {
                return publicMoney == null ? "" : publicMoney;
            }

            public void setPublicMoney(String publicMoney) {
                this.publicMoney = publicMoney;
            }

            public String getSelfMoney() {
                return selfMoney == null ? "" : selfMoney;
            }

            public void setSelfMoney(String selfMoney) {
                this.selfMoney = selfMoney;
            }

            public int getLiveType() {
                return liveType;
            }

            public void setLiveType(int liveType) {
                this.liveType = liveType;
            }

            public String getCreateTime() {
                return createTime == null ? "" : createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public List<LeftBean> getLeft() {
                return left;
            }

            public void setLeft(List<LeftBean> left) {
                this.left = left;
            }

            public List<RightBean> getRight() {
                return right;
            }

            public void setRight(List<RightBean> right) {
                this.right = right;
            }

            public static class LeftBean {
                /**
                 * id : 511486160083128336
                 * name : 王洪福
                 * logo : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
                 */

                private long id;
                private String name;
                private String logo;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }
            }

            public static class RightBean {
                /**
                 * id : 511486160083128336
                 * name : 王洪福
                 * logo : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
                 */

                private long id;
                private String name;
                private String logo;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }
            }
        }

}
