package heyong.intellectPinPang.bean.live;

import java.util.List;

public class CanonDemandBean {

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
        private long arrangeId;
        private long matchId;
        private String player1;
        private String player2;
        private String club1Name;
        private String club2Name;
        private String player3;
        private String player4;
        private String dayTime;
        private String hourTime;
        private String projectType;

        public String getProjectType() {
            return projectType == null ? "1" : projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public long getArrangeId() {
            return arrangeId;
        }

        public void setArrangeId(long arrangeId) {
            this.arrangeId = arrangeId;
        }

        public long getMatchId() {
            return matchId;
        }

        public void setMatchId(long matchId) {
            this.matchId = matchId;
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
    }

}
