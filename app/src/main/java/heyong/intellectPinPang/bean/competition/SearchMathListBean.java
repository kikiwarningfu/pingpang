package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class SearchMathListBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 1
         * startRow : 1
         * endRow : 1
         * total : 1
         * pages : 1
         * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","matchStatus":null,"replaceChargeEat":1,"matchRules":null,"projectList":null,"id":521833189891642976,"matchTitle":"北京三级运动员晋级赛","applyClubId":512196767447027728,"sponsor":"北京体育局SSS","organizer":"狂鸟俱乐部","registrationDeadline":"2021-12-15 00:00","beginTime":"2020-12-11","endTime":"2020-12-19","matchImgId":null,"matchImgUrl":"www.baidu.com","stampUpload":"1","stampImgId":null,"stampImgUrl":"www.baidu.com","holdCity":"北京 大兴区","venue":"林肯公园","linkPerson":"小浣熊","linkNum":"13315678931","ownRegistration":"1","authentication":"1","replaceCharge":"1","registrationFeeType":"1","registrationCount":100,"ownFree":120,"eatFree":30,"daysCount":null,"eatAllFree":0,"matchGrade":"全国级","matchLevel":"专业级","status":"2","createUser":511486160083128336,"createTime":"2020-12-10 23:38:47","updateUser":511486160083128336,"updateTime":"2020-12-10 23:38:47","isDelete":"0"}]
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
             * matchStatus : null
             * replaceChargeEat : 1
             * matchRules : null
             * projectList : null
             * id : 521833189891642976
             * matchTitle : 北京三级运动员晋级赛
             * applyClubId : 512196767447027728
             * sponsor : 北京体育局SSS
             * organizer : 狂鸟俱乐部
             * registrationDeadline : 2021-12-15 00:00
             * beginTime : 2020-12-11
             * endTime : 2020-12-19
             * matchImgId : null
             * matchImgUrl : www.baidu.com
             * stampUpload : 1
             * stampImgId : null
             * stampImgUrl : www.baidu.com
             * holdCity : 北京 大兴区
             * venue : 林肯公园
             * linkPerson : 小浣熊
             * linkNum : 13315678931
             * ownRegistration : 1
             * authentication : 1
             * replaceCharge : 1
             * registrationFeeType : 1
             * registrationCount : 100
             * ownFree : 120
             * eatFree : 30
             * daysCount : null
             * eatAllFree : 0
             * matchGrade : 全国级
             * matchLevel : 专业级
             * status : 2
             * createUser : 511486160083128336
             * createTime : 2020-12-10 23:38:47
             * updateUser : 511486160083128336
             * updateTime : 2020-12-10 23:38:47
             * isDelete : 0
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String matchStatus;
            private int replaceChargeEat;
            private String matchRules;
            private String projectList;
            private long id;
            private String matchTitle;
            private long applyClubId;
            private String sponsor;
            private String organizer;
            private String registrationDeadline;
            private String beginTime;
            private String endTime;
            private String matchImgId;
            private String matchImgUrl;
            private String stampUpload;
            private String stampImgId;
            private String stampImgUrl;
            private String holdCity;
            private String venue;
            private String linkPerson;
            private String linkNum;
            private String ownRegistration;
            private String authentication;
            private String replaceCharge;
            private String registrationFeeType;
            private int registrationCount;
            private int ownFree;
            private int eatFree;
            private String daysCount;
            private int eatAllFree;
            private String matchGrade;
            private String matchLevel;
            private String status;
            private long createUser;
            private String createTime;
            private long updateUser;
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

            public String getMatchStatus() {
                return matchStatus == null ? "1" : matchStatus;
            }

            public void setMatchStatus(String matchStatus) {
                this.matchStatus = matchStatus;
            }

            public int getReplaceChargeEat() {
                return replaceChargeEat;
            }

            public void setReplaceChargeEat(int replaceChargeEat) {
                this.replaceChargeEat = replaceChargeEat;
            }

            public String getMatchRules() {
                return matchRules == null ? "" : matchRules;
            }

            public void setMatchRules(String matchRules) {
                this.matchRules = matchRules;
            }

            public String getProjectList() {
                return projectList == null ? "" : projectList;
            }

            public void setProjectList(String projectList) {
                this.projectList = projectList;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getMatchTitle() {
                return matchTitle == null ? "" : matchTitle;
            }

            public void setMatchTitle(String matchTitle) {
                this.matchTitle = matchTitle;
            }

            public long getApplyClubId() {
                return applyClubId;
            }

            public void setApplyClubId(long applyClubId) {
                this.applyClubId = applyClubId;
            }

            public String getSponsor() {
                return sponsor == null ? "" : sponsor;
            }

            public void setSponsor(String sponsor) {
                this.sponsor = sponsor;
            }

            public String getOrganizer() {
                return organizer == null ? "" : organizer;
            }

            public void setOrganizer(String organizer) {
                this.organizer = organizer;
            }

            public String getRegistrationDeadline() {
                return registrationDeadline == null ? "" : registrationDeadline;
            }

            public void setRegistrationDeadline(String registrationDeadline) {
                this.registrationDeadline = registrationDeadline;
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

            public String getMatchImgId() {
                return matchImgId == null ? "" : matchImgId;
            }

            public void setMatchImgId(String matchImgId) {
                this.matchImgId = matchImgId;
            }

            public String getMatchImgUrl() {
                return matchImgUrl == null ? "" : matchImgUrl;
            }

            public void setMatchImgUrl(String matchImgUrl) {
                this.matchImgUrl = matchImgUrl;
            }

            public String getStampUpload() {
                return stampUpload == null ? "" : stampUpload;
            }

            public void setStampUpload(String stampUpload) {
                this.stampUpload = stampUpload;
            }

            public String getStampImgId() {
                return stampImgId == null ? "" : stampImgId;
            }

            public void setStampImgId(String stampImgId) {
                this.stampImgId = stampImgId;
            }

            public String getStampImgUrl() {
                return stampImgUrl == null ? "" : stampImgUrl;
            }

            public void setStampImgUrl(String stampImgUrl) {
                this.stampImgUrl = stampImgUrl;
            }

            public String getHoldCity() {
                return holdCity == null ? "" : holdCity;
            }

            public void setHoldCity(String holdCity) {
                this.holdCity = holdCity;
            }

            public String getVenue() {
                return venue == null ? "" : venue;
            }

            public void setVenue(String venue) {
                this.venue = venue;
            }

            public String getLinkPerson() {
                return linkPerson == null ? "" : linkPerson;
            }

            public void setLinkPerson(String linkPerson) {
                this.linkPerson = linkPerson;
            }

            public String getLinkNum() {
                return linkNum == null ? "" : linkNum;
            }

            public void setLinkNum(String linkNum) {
                this.linkNum = linkNum;
            }

            public String getOwnRegistration() {
                return ownRegistration == null ? "" : ownRegistration;
            }

            public void setOwnRegistration(String ownRegistration) {
                this.ownRegistration = ownRegistration;
            }

            public String getAuthentication() {
                return authentication == null ? "" : authentication;
            }

            public void setAuthentication(String authentication) {
                this.authentication = authentication;
            }

            public String getReplaceCharge() {
                return replaceCharge == null ? "" : replaceCharge;
            }

            public void setReplaceCharge(String replaceCharge) {
                this.replaceCharge = replaceCharge;
            }

            public String getRegistrationFeeType() {
                return registrationFeeType == null ? "" : registrationFeeType;
            }

            public void setRegistrationFeeType(String registrationFeeType) {
                this.registrationFeeType = registrationFeeType;
            }

            public int getRegistrationCount() {
                return registrationCount;
            }

            public void setRegistrationCount(int registrationCount) {
                this.registrationCount = registrationCount;
            }

            public int getOwnFree() {
                return ownFree;
            }

            public void setOwnFree(int ownFree) {
                this.ownFree = ownFree;
            }

            public int getEatFree() {
                return eatFree;
            }

            public void setEatFree(int eatFree) {
                this.eatFree = eatFree;
            }

            public String getDaysCount() {
                return daysCount == null ? "" : daysCount;
            }

            public void setDaysCount(String daysCount) {
                this.daysCount = daysCount;
            }

            public int getEatAllFree() {
                return eatAllFree;
            }

            public void setEatAllFree(int eatAllFree) {
                this.eatAllFree = eatAllFree;
            }

            public String getMatchGrade() {
                return matchGrade == null ? "" : matchGrade;
            }

            public void setMatchGrade(String matchGrade) {
                this.matchGrade = matchGrade;
            }

            public String getMatchLevel() {
                return matchLevel == null ? "" : matchLevel;
            }

            public void setMatchLevel(String matchLevel) {
                this.matchLevel = matchLevel;
            }

            public String getStatus() {
                return status == null ? "" : status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getIsDelete() {
                return isDelete == null ? "" : isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }
        }

}
