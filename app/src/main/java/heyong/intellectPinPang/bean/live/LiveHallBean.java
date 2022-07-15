package heyong.intellectPinPang.bean.live;

import java.util.ArrayList;
import java.util.List;

public class LiveHallBean {


    /**
     * pageNum : 1
     * pageSize : 10
     * size : 3
     * startRow : 1
     * endRow : 3
     * total : 3
     * pages : 1
     * list : [{"userId":null,"token":null,"free":"1.00","purchased":null,"canTry":null,"surplusSecond":0,"id":618846888955106480,"itemTitle":"测试标题","itemType":"1","matchTitle":"0811裁判长编排演示","matchTime":"2021-08-12","onLineCount":0,"channelName":"618846888955106480","player1":"宏亮乒乓一队","player1Img":null,"player2":null,"player2Img":null,"player3":"临汾二小一队","player3Img":null,"player4":null,"player4Img":null,"status":"2"},{"userId":null,"token":null,"free":"1.00","purchased":null,"canTry":null,"surplusSecond":0,"id":619556955526419632,"itemTitle":"测试标题","itemType":"1","matchTitle":"0811裁判长编排演示","matchTime":"2021-08-12","onLineCount":0,"channelName":"619556955526419632","player1":"华山乒乓队一队","player1Img":null,"player2":null,"player2Img":null,"player3":"博奥俱乐部一队","player3Img":null,"player4":null,"player4Img":null,"status":"1"},{"userId":null,"token":null,"free":"1.00","purchased":null,"canTry":null,"surplusSecond":0,"id":619557074191668400,"itemTitle":"测试标题","itemType":"1","matchTitle":"0811裁判长编排演示","matchTime":"2021-08-12","onLineCount":0,"channelName":"619557074191668400","player1":"宏亮乒乓一队","player1Img":null,"player2":null,"player2Img":null,"player3":"博奥俱乐部一队","player3Img":null,"player4":null,"player4Img":null,"status":"1"}]
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
         * userId : null
         * token : null
         * free : 1.00
         * purchased : null
         * canTry : null
         * surplusSecond : 0
         * id : 618846888955106480
         * itemTitle : 测试标题
         * itemType : 1
         * matchTitle : 0811裁判长编排演示
         * matchTime : 2021-08-12
         * onLineCount : 0
         * channelName : 618846888955106480
         * player1 : 宏亮乒乓一队
         * player1Img : null
         * player2 : null
         * player2Img : null
         * player3 : 临汾二小一队
         * player3Img : null
         * player4 : null
         * player4Img : null
         * status : 2
         */
        private String leftClubName;
        private String rightClubName;
        private String beginTime;
        private String tableNum;
        private String userId;
        private String optionalUid;

        private String token;
        private String free;
        private String purchased;
        private String canTry;
        private int surplusSecond;
        private long id;
        private String itemTitle;
        private String itemType;
        private String matchTitle;
        private String matchTime;
        private int onLineCount;
        private String channelName;
        private String player1;
        private String player1Img;
        private String player2;
        private String player2Img;
        private String player3;
        private String player3Img;
        private String player4;
        private String player4Img;
        private String status;
        private String videoAddress;
        private String fengmian;

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

        public String getBeginTime() {
            return beginTime == null ? "" : beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getTableNum() {
            return tableNum == null ? "" : tableNum;
        }

        public void setTableNum(String tableNum) {
            this.tableNum = tableNum;
        }

        public String getOptionalUid() {
            return optionalUid == null ? "" : optionalUid;
        }

        public void setOptionalUid(String optionalUid) {
            this.optionalUid = optionalUid;
        }

        public String getVideoAddress() {
            return videoAddress == null ? "" : videoAddress;
        }

        public void setVideoAddress(String videoAddress) {
            this.videoAddress = videoAddress;
        }

        public String getFengmian() {
            return fengmian == null ? "" : fengmian;
        }

        public void setFengmian(String fengmian) {
            this.fengmian = fengmian;
        }

        public String getUserId() {
            return userId == null ? "" : userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getFree() {
            return free == null ? "" : free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getPurchased() {
            return purchased == null ? "" : purchased;
        }

        public void setPurchased(String purchased) {
            this.purchased = purchased;
        }

        public String getCanTry() {
            return canTry == null ? "" : canTry;
        }

        public void setCanTry(String canTry) {
            this.canTry = canTry;
        }

        public int getSurplusSecond() {
            return surplusSecond;
        }

        public void setSurplusSecond(int surplusSecond) {
            this.surplusSecond = surplusSecond;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getItemTitle() {
            return itemTitle == null ? "" : itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getItemType() {
            return itemType == null ? "" : itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getMatchTitle() {
            return matchTitle == null ? "" : matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
        }

        public String getMatchTime() {
            return matchTime == null ? "" : matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public int getOnLineCount() {
            return onLineCount;
        }

        public void setOnLineCount(int onLineCount) {
            this.onLineCount = onLineCount;
        }

        public String getChannelName() {
            return channelName == null ? "" : channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getPlayer1() {
            return player1 == null ? "" : player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer1Img() {
            return player1Img == null ? "" : player1Img;
        }

        public void setPlayer1Img(String player1Img) {
            this.player1Img = player1Img;
        }

        public String getPlayer2() {
            return player2 == null ? "" : player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public String getPlayer2Img() {
            return player2Img == null ? "" : player2Img;
        }

        public void setPlayer2Img(String player2Img) {
            this.player2Img = player2Img;
        }

        public String getPlayer3() {
            return player3 == null ? "" : player3;
        }

        public void setPlayer3(String player3) {
            this.player3 = player3;
        }

        public String getPlayer3Img() {
            return player3Img == null ? "" : player3Img;
        }

        public void setPlayer3Img(String player3Img) {
            this.player3Img = player3Img;
        }

        public String getPlayer4() {
            return player4 == null ? "" : player4;
        }

        public void setPlayer4(String player4) {
            this.player4 = player4;
        }

        public String getPlayer4Img() {
            return player4Img == null ? "" : player4Img;
        }

        public void setPlayer4Img(String player4Img) {
            this.player4Img = player4Img;
        }

        public String getStatus() {
            return status == null ? "1" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
