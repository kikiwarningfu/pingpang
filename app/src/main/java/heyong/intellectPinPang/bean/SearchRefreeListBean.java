package heyong.intellectPinPang.bean;

import java.util.List;

public class SearchRefreeListBean {


    private List<ArrangesBean> arranges;
    private String matchTitle;
    private String status;
    private int notBeginCount;
    private int endCount;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private String list;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private String navigatepageNums;

    public List<ArrangesBean> getArranges() {
        return arranges;
    }

    public void setArranges(List<ArrangesBean> arranges) {
        this.arranges = arranges;
    }

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNotBeginCount() {
        return notBeginCount;
    }

    public void setNotBeginCount(int notBeginCount) {
        this.notBeginCount = notBeginCount;
    }

    public int getEndCount() {
        return endCount;
    }

    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }

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

    public String getList() {
        return list == null ? "" : list;
    }

    public void setList(String list) {
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

    public String getNavigatepageNums() {
        return navigatepageNums == null ? "" : navigatepageNums;
    }

    public void setNavigatepageNums(String navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ArrangesBean {
        private String projectTitle;
        private long id;
        private String itemType;
        private String title;
        private String titleType;
        private String headImg1;
        private String player1Name;
        private long player1Id;
        private String headImg2;
        private String player2Name;
        private String headImg3;
        private String player3Name;
        private long player3Id;
        private String headImg4;
        private String player4Name;
        private String beginTime;
        private String status;
        private String location;
        private int leftWinCount;
        private int rightWinCount;
        private int leftWaiver;
        private int rightWaiver;
        private int site;
        private boolean refereeInArrange;
        private String club1Name;
        private String club2Name;
        private String format;
        private String itemTitle;
        private String stage;
        private String titleWay;
        private long projectItemId;
        private String arrangeNum;
        private String formatTitle;
        private int tableNum;
        private String refereeId;

        public String getProjectTitle() {
            return projectTitle == null ? "" : projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getItemType() {
            return itemType == null ? "0" : itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleType() {
            return titleType == null ? "" : titleType;
        }

        public void setTitleType(String titleType) {
            this.titleType = titleType;
        }

        public String getHeadImg1() {
            return headImg1 == null ? "" : headImg1;
        }

        public void setHeadImg1(String headImg1) {
            this.headImg1 = headImg1;
        }

        public String getPlayer1Name() {
            return player1Name == null ? "" : player1Name;
        }

        public void setPlayer1Name(String player1Name) {
            this.player1Name = player1Name;
        }

        public long getPlayer1Id() {
            return player1Id;
        }

        public void setPlayer1Id(long player1Id) {
            this.player1Id = player1Id;
        }

        public String getHeadImg2() {
            return headImg2 == null ? "" : headImg2;
        }

        public void setHeadImg2(String headImg2) {
            this.headImg2 = headImg2;
        }

        public String getPlayer2Name() {
            return player2Name == null ? "" : player2Name;
        }

        public void setPlayer2Name(String player2Name) {
            this.player2Name = player2Name;
        }

        public String getHeadImg3() {
            return headImg3 == null ? "" : headImg3;
        }

        public void setHeadImg3(String headImg3) {
            this.headImg3 = headImg3;
        }

        public String getPlayer3Name() {
            return player3Name == null ? "" : player3Name;
        }

        public void setPlayer3Name(String player3Name) {
            this.player3Name = player3Name;
        }

        public long getPlayer3Id() {
            return player3Id;
        }

        public void setPlayer3Id(long player3Id) {
            this.player3Id = player3Id;
        }

        public String getHeadImg4() {
            return headImg4 == null ? "" : headImg4;
        }

        public void setHeadImg4(String headImg4) {
            this.headImg4 = headImg4;
        }

        public String getPlayer4Name() {
            return player4Name == null ? "" : player4Name;
        }

        public void setPlayer4Name(String player4Name) {
            this.player4Name = player4Name;
        }

        public String getBeginTime() {
            return beginTime == null ? "" : beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocation() {
            return location == null ? "" : location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getLeftWinCount() {
            return leftWinCount;
        }

        public void setLeftWinCount(int leftWinCount) {
            this.leftWinCount = leftWinCount;
        }

        public int getRightWinCount() {
            return rightWinCount;
        }

        public void setRightWinCount(int rightWinCount) {
            this.rightWinCount = rightWinCount;
        }

        public int getLeftWaiver() {
            return leftWaiver;
        }

        public void setLeftWaiver(int leftWaiver) {
            this.leftWaiver = leftWaiver;
        }

        public int getRightWaiver() {
            return rightWaiver;
        }

        public void setRightWaiver(int rightWaiver) {
            this.rightWaiver = rightWaiver;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public boolean isRefereeInArrange() {
            return refereeInArrange;
        }

        public void setRefereeInArrange(boolean refereeInArrange) {
            this.refereeInArrange = refereeInArrange;
        }

        public String getClub1Name() {
            return club1Name == null ? "" : club1Name;
        }

        public void setClub1Name(String club1Name) {
            this.club1Name = club1Name;
        }

        public String getClub2Name() {
            return club2Name == null ? "" : club2Name;
        }

        public void setClub2Name(String club2Name) {
            this.club2Name = club2Name;
        }

        public String getFormat() {
            return format == null ? "" : format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getItemTitle() {
            return itemTitle == null ? "" : itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getStage() {
            return stage == null ? "" : stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getTitleWay() {
            return titleWay == null ? "" : titleWay;
        }

        public void setTitleWay(String titleWay) {
            this.titleWay = titleWay;
        }

        public long getProjectItemId() {
            return projectItemId;
        }

        public void setProjectItemId(long projectItemId) {
            this.projectItemId = projectItemId;
        }

        public String getArrangeNum() {
            return arrangeNum == null ? "" : arrangeNum;
        }

        public void setArrangeNum(String arrangeNum) {
            this.arrangeNum = arrangeNum;
        }

        public String getFormatTitle() {
            return formatTitle == null ? "" : formatTitle;
        }

        public void setFormatTitle(String formatTitle) {
            this.formatTitle = formatTitle;
        }

        public int getTableNum() {
            return tableNum;
        }

        public void setTableNum(int tableNum) {
            this.tableNum = tableNum;
        }

        public String getRefereeId() {
            return refereeId == null ? "" : refereeId;
        }

        public void setRefereeId(String refereeId) {
            this.refereeId = refereeId;
        }
    }

}
