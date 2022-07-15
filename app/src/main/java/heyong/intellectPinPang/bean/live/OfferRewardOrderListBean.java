package heyong.intellectPinPang.bean.live;

import java.util.List;

public class OfferRewardOrderListBean {

        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private List<ListBean> list;
        private int firstPage;
        private int prePage;
        private int nextPage;
        private int lastPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
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

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String dayTime;
            private String hourTime;
            private String mainDirection;
            private String orderNum;
            private String code;
            private String orderStatusB;
            private int yongjin;
            private String matchAddress;
            private long matchId;
            private long id;
            private long arrangeId;
            private String matchTitle;
            private int itemType;
            private String itemTitle;
            private String player1;
            private String player2;
            private String player3;
            private String player4;
            private String matchTime;
            private String matchLocal;
            private String orderType;
            private String freeType;
            private String money;
            private String isDelete;
            private String freeStatus;
            private String duJuWay;
            private String orderTime;
            private String hasJiedan;
            private String reason;
            private String tuikuanshuoming;
            private String pingjia;
            private String pingjialevel;
            private String orderStatus;
            private int tableNum;
            private long orderUser;
            private long createUser;
            private String createTime;
            private long updateUser;
            private String updateTime;
            private String choucheng;
            private String leftClubName;
            private String rightClubName;

            public String getLeftClubName() {
                return leftClubName == null ? "" : leftClubName;
            }

            public void setLeftClubName(String leftClubName) {
                this.leftClubName = leftClubName;
            }

            public String getRightClubName() {
                return rightClubName == null ? "" : rightClubName;
            }

            public void setRightClubName(String rightClubName) {
                this.rightClubName = rightClubName;
            }

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

            public String getDayTime() {
                return dayTime == null ? "" : dayTime;
            }

            public void setDayTime(String dayTime) {
                this.dayTime = dayTime;
            }

            public String getHourTime() {
                return hourTime == null ? "" : hourTime;
            }

            public void setHourTime(String hourTime) {
                this.hourTime = hourTime;
            }

            public String getMainDirection() {
                return mainDirection == null ? "" : mainDirection;
            }

            public void setMainDirection(String mainDirection) {
                this.mainDirection = mainDirection;
            }

            public String getOrderNum() {
                return orderNum == null ? "" : orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getCode() {
                return code == null ? "" : code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getOrderStatusB() {
                return orderStatusB == null ? "" : orderStatusB;
            }

            public void setOrderStatusB(String orderStatusB) {
                this.orderStatusB = orderStatusB;
            }

            public int getYongjin() {
                return yongjin;
            }

            public void setYongjin(int yongjin) {
                this.yongjin = yongjin;
            }

            public String getMatchAddress() {
                return matchAddress == null ? "" : matchAddress;
            }

            public void setMatchAddress(String matchAddress) {
                this.matchAddress = matchAddress;
            }

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getArrangeId() {
                return arrangeId;
            }

            public void setArrangeId(long arrangeId) {
                this.arrangeId = arrangeId;
            }

            public String getMatchTitle() {
                return matchTitle == null ? "" : matchTitle;
            }

            public void setMatchTitle(String matchTitle) {
                this.matchTitle = matchTitle;
            }

            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }

            public String getItemTitle() {
                return itemTitle == null ? "" : itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getPlayer1() {
                return player1 == null ? "" : player1;
            }

            public void setPlayer1(String player1) {
                this.player1 = player1;
            }

            public String getPlayer2() {
                return player2 == null ? "" : player2;
            }

            public void setPlayer2(String player2) {
                this.player2 = player2;
            }

            public String getPlayer3() {
                return player3 == null ? "" : player3;
            }

            public void setPlayer3(String player3) {
                this.player3 = player3;
            }

            public String getPlayer4() {
                return player4 == null ? "" : player4;
            }

            public void setPlayer4(String player4) {
                this.player4 = player4;
            }

            public String getMatchTime() {
                return matchTime == null ? "" : matchTime;
            }

            public void setMatchTime(String matchTime) {
                this.matchTime = matchTime;
            }

            public String getMatchLocal() {
                return matchLocal == null ? "" : matchLocal;
            }

            public void setMatchLocal(String matchLocal) {
                this.matchLocal = matchLocal;
            }

            public String getOrderType() {
                return orderType == null ? "" : orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getFreeType() {
                return freeType == null ? "" : freeType;
            }

            public void setFreeType(String freeType) {
                this.freeType = freeType;
            }

            public String getMoney() {
                return money == null ? "" : money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getIsDelete() {
                return isDelete == null ? "" : isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getFreeStatus() {
                return freeStatus == null ? "" : freeStatus;
            }

            public void setFreeStatus(String freeStatus) {
                this.freeStatus = freeStatus;
            }

            public String getDuJuWay() {
                return duJuWay == null ? "" : duJuWay;
            }

            public void setDuJuWay(String duJuWay) {
                this.duJuWay = duJuWay;
            }

            public String getOrderTime() {
                return orderTime == null ? "" : orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getHasJiedan() {
                return hasJiedan == null ? "" : hasJiedan;
            }

            public void setHasJiedan(String hasJiedan) {
                this.hasJiedan = hasJiedan;
            }

            public String getReason() {
                return reason == null ? "" : reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getTuikuanshuoming() {
                return tuikuanshuoming == null ? "" : tuikuanshuoming;
            }

            public void setTuikuanshuoming(String tuikuanshuoming) {
                this.tuikuanshuoming = tuikuanshuoming;
            }

            public String getPingjia() {
                return pingjia == null ? "" : pingjia;
            }

            public void setPingjia(String pingjia) {
                this.pingjia = pingjia;
            }

            public String getPingjialevel() {
                return pingjialevel == null ? "" : pingjialevel;
            }

            public void setPingjialevel(String pingjialevel) {
                this.pingjialevel = pingjialevel;
            }

            public String getOrderStatus() {
                return orderStatus == null ? "" : orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getTableNum() {
                return tableNum;
            }

            public void setTableNum(int tableNum) {
                this.tableNum = tableNum;
            }

            public long getOrderUser() {
                return orderUser;
            }

            public void setOrderUser(long orderUser) {
                this.orderUser = orderUser;
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

            public String getChoucheng() {
                return choucheng == null ? "" : choucheng;
            }

            public void setChoucheng(String choucheng) {
                this.choucheng = choucheng;
            }
        }

}
