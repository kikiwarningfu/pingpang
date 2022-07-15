package heyong.intellectPinPang.bean.live;

import java.util.ArrayList;
import java.util.List;

public class LiveUserOrderListBean {


    /**
     * pageNum : int //当前页
     * pageSize : int //每页的数量
     * size : int //当前页的数量
     * startRow : int //当前页面第一个元素在数据库中的行号
     * endRow : int //当前页面最后一个元素在数据库中的行号
     * total : long //记录总条数
     * pages : int //总页数
     * list : [{"requestId":"int //直播唯一ID","matchId":"long //比赛唯一ID","tableNum":"int //球台数","matchName":"string //赛事名称","title":"string //赛事名称（阶段、组）","gameTime":"date //比赛开始时间","address":"string //详细地址","money":"float //悬赏支付金额","userMoney":"float //接单用户预计可分成金额","left":[{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}],"right":[{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}],"orderId":"int //唯一订单号","payStatus":"int //悬赏支付状态 -1=待支付，0=支付成功，1=已经接单，2=退款中  3=退款成功,4=正常结束，5=未支付超时关闭","isAppraise":"int //是否已经评价  1=是 0=未","payType":"int //支付方式 1=支付宝 2=微信 3=苹果内付","receiveStatus":"int //接单状态 -1=还未接单 0=禁用，1=接单成功 2=放弃，3=直播中，4=结束","isWithDraw":"int //是否可以提现 1=可以 0=不可以","matchMethod":"int //比赛局数 1=三局两胜，2=五局三胜 3=七局五胜","publicMoney":"float //公共悬赏发布时应该支付金额，单位元","selfMoney":"float //发布自己接单悬赏时需要支付给平台的金额 单位元"}]
     * firstPage : int //第一页
     * prePage : int //上一页
     * nextPage : int //下一页
     * lastPage : int //最后一页
     * isFirstPage : boolean //是否为第一页
     * isLastPage : boolean //是否为最后一页
     * hasPreviousPage : boolean //是否有前一页
     * hasNextPage : boolean //是否有下一页
     * navigatePages : int //导航页码数
     * navigatepageNums : int[] //所有导航页号
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private long total;
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
    private int[] navigatepageNums;
    private List<ListBean> list;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
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

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
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

    public static class ListBean {
        /**
         * requestId : int //直播唯一ID
         * matchId : long //比赛唯一ID
         * tableNum : int //球台数
         * matchName : string //赛事名称
         * title : string //赛事名称（阶段、组）
         * gameTime : date //比赛开始时间
         * address : string //详细地址
         * money : float //悬赏支付金额
         * userMoney : float //接单用户预计可分成金额
         * left : [{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}]
         * right : [{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}]
         * orderId : int //唯一订单号
         * payStatus : int //悬赏支付状态 -1=待支付，0=支付成功，1=已经接单，2=退款中  3=退款成功,4=正常结束，5=未支付超时关闭
         * isAppraise : int //是否已经评价  1=是 0=未
         * payType : int //支付方式 1=支付宝 2=微信 3=苹果内付
         * receiveStatus : int //接单状态 -1=还未接单 0=禁用，1=接单成功 2=放弃，3=直播中，4=结束
         * isWithDraw : int //是否可以提现 1=可以 0=不可以
         * matchMethod : int //比赛局数 1=三局两胜，2=五局三胜 3=七局五胜
         * publicMoney : float //公共悬赏发布时应该支付金额，单位元
         * selfMoney : float //发布自己接单悬赏时需要支付给平台的金额 单位元
         */

        private int requestId;
        private long matchId;
        private int tableNum;
        private String matchName;
        private String title;
        private String gameTime;
        private String address;
        private float money;
        private float userMoney;
        private int orderId;
        private int payStatus;
        private int isAppraise;
        private int payType;
        private int receiveStatus;
        private int isWithDraw;
        private int matchMethod;
        private float publicMoney;
        private float selfMoney;
        private List<LeftBean> left;
        private List<RightBean> right;
        private int showOwnerStatus;
        private long useTime;
        private boolean timeFlag;
        public long getUseTime() {
            return useTime;
        }
        private int liveType;
        private long closeSec;

        public int getLiveType() {
            return liveType;
        }

        public void setLiveType(int liveType) {
            this.liveType = liveType;
        }

        public long getCloseSec() {
            return closeSec;
        }

        public void setCloseSec(long closeSec) {
            this.closeSec = closeSec;
        }

        public void setUseTime(long useTime) {
            this.useTime = useTime;
        }
        public int getShowOwnerStatus() {
            return showOwnerStatus;
        }

        public void setShowOwnerStatus(int showOwnerStatus) {
            this.showOwnerStatus = showOwnerStatus;
        }
        public boolean isTimeFlag() {
            return timeFlag;
        }

        public void setTimeFlag(boolean timeFlag) {
            this.timeFlag = timeFlag;
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

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public float getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(float userMoney) {
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

        public int getMatchMethod() {
            return matchMethod;
        }

        public void setMatchMethod(int matchMethod) {
            this.matchMethod = matchMethod;
        }

        public float getPublicMoney() {
            return publicMoney;
        }

        public void setPublicMoney(float publicMoney) {
            this.publicMoney = publicMoney;
        }

        public float getSelfMoney() {
            return selfMoney;
        }

        public void setSelfMoney(float selfMoney) {
            this.selfMoney = selfMoney;
        }

        public List<LeftBean> getLeft() {
            if (left == null) {
                return new ArrayList<>();
            }
            return left;
        }

        public void setLeft(List<LeftBean> left) {
            this.left = left;
        }

        public List<RightBean> getRight() {
            if (right == null) {
                return new ArrayList<>();
            }
            return right;
        }

        public void setRight(List<RightBean> right) {
            this.right = right;
        }

        public static class LeftBean {
            /**
             * id : long //球员ID
             * name : string //球员名称
             * logo : string //球员头像
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
             * id : long //球员ID
             * name : string //球员名称
             * logo : string //球员头像
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
