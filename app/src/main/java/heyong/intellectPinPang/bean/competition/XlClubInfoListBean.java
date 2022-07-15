package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class XlClubInfoListBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 2
         * startRow : 1
         * endRow : 2
         * total : 2
         * pages : 1
         * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","isCharge":null,"userImage":[null,"","",""],"inChargeId":511486160083128336,"joinStatus":null,"recordList":null,"memberCount":1,"memberList":null,"id":514905140932729472,"teamType":"1","teamTitle":"cehituanti","abbreviation":"dasda","inCharge":"asdasdads","phoneNum":"1328545687","city":"宁夏 银川 兴庆","detailAddress":"123123123","clubImgId":null,"clubImgUrl":"http://images.xlttsports.com/android_img_20201121204901257","qualificationImgId":null,"qualificationImgUrl":"https://img.tupianzj.com/uploads/allimg/202010/9999/c98f2d32b4.jpg","tablesNum":11,"teamIntroduce":"123123123","status":"2","createUser":511486160083128336,"createTime":"2020-11-21 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-21 08:00:00","isDelete":"0","coverLogo":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","isCharge":null,"userImage":[null,null,null,null],"inChargeId":511486160083128336,"joinStatus":null,"recordList":null,"memberCount":6,"memberList":null,"id":514870870914718336,"teamType":"1","teamTitle":"测试团体","abbreviation":"哈哈哈华盛","inCharge":"往后","phoneNum":"13292803268","city":"新疆 乌鲁木齐 天山","detailAddress":"发大厦的阿萨德","clubImgId":null,"clubImgUrl":"http://images.xlttsports.com/android_img_20201121183251510","qualificationImgId":null,"qualificationImgUrl":"https://img.tupianzj.com/uploads/allimg/202010/9999/c98f2d32b4.jpg","tablesNum":11,"teamIntroduce":"啊实打实所大谁说的按时的","status":"2","createUser":511486160083128336,"createTime":"2020-11-21 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-21 08:00:00","isDelete":"0","coverLogo":null}]
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
             * isCharge : null
             * userImage : [null,"","",""]
             * inChargeId : 511486160083128336
             * joinStatus : null
             * recordList : null
             * memberCount : 1
             * memberList : null
             * id : 514905140932729472
             * teamType : 1
             * teamTitle : cehituanti
             * abbreviation : dasda
             * inCharge : asdasdads
             * phoneNum : 1328545687
             * city : 宁夏 银川 兴庆
             * detailAddress : 123123123
             * clubImgId : null
             * clubImgUrl : http://images.xlttsports.com/android_img_20201121204901257
             * qualificationImgId : null
             * qualificationImgUrl : https://img.tupianzj.com/uploads/allimg/202010/9999/c98f2d32b4.jpg
             * tablesNum : 11
             * teamIntroduce : 123123123
             * status : 2
             * createUser : 511486160083128336
             * createTime : 2020-11-21 08:00:00
             * updateUser : 511486160083128336
             * updateTime : 2020-11-21 08:00:00
             * isDelete : 0
             * coverLogo : null
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String isCharge;
            private long inChargeId;
            private String joinStatus;
            private String recordList;
            private int memberCount;
            private String memberList;
            private long id;
            private String teamType;
            private String teamTitle;
            private String abbreviation;
            private String inCharge;
            private String phoneNum;
            private String city;
            private String detailAddress;
            private String clubImgId;
            private String clubImgUrl;
            private String qualificationImgId;
            private String qualificationImgUrl;
            private int tablesNum;
            private String teamIntroduce;
            private String status;
            private long createUser;
            private String createTime;
            private long updateUser;
            private String updateTime;
            private String isDelete;
            private String coverLogo;
            private List<String> userImage;

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

            public String getIsCharge() {
                return isCharge == null ? "" : isCharge;
            }

            public void setIsCharge(String isCharge) {
                this.isCharge = isCharge;
            }

            public long getInChargeId() {
                return inChargeId;
            }

            public void setInChargeId(long inChargeId) {
                this.inChargeId = inChargeId;
            }

            public String getJoinStatus() {
                return joinStatus == null ? "" : joinStatus;
            }

            public void setJoinStatus(String joinStatus) {
                this.joinStatus = joinStatus;
            }

            public String getRecordList() {
                return recordList == null ? "" : recordList;
            }

            public void setRecordList(String recordList) {
                this.recordList = recordList;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public String getMemberList() {
                return memberList == null ? "" : memberList;
            }

            public void setMemberList(String memberList) {
                this.memberList = memberList;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTeamType() {
                return teamType == null ? "" : teamType;
            }

            public void setTeamType(String teamType) {
                this.teamType = teamType;
            }

            public String getTeamTitle() {
                return teamTitle == null ? "" : teamTitle;
            }

            public void setTeamTitle(String teamTitle) {
                this.teamTitle = teamTitle;
            }

            public String getAbbreviation() {
                return abbreviation == null ? "" : abbreviation;
            }

            public void setAbbreviation(String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public String getInCharge() {
                return inCharge == null ? "" : inCharge;
            }

            public void setInCharge(String inCharge) {
                this.inCharge = inCharge;
            }

            public String getPhoneNum() {
                return phoneNum == null ? "" : phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getCity() {
                return city == null ? "" : city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDetailAddress() {
                return detailAddress == null ? "" : detailAddress;
            }

            public void setDetailAddress(String detailAddress) {
                this.detailAddress = detailAddress;
            }

            public String getClubImgId() {
                return clubImgId == null ? "" : clubImgId;
            }

            public void setClubImgId(String clubImgId) {
                this.clubImgId = clubImgId;
            }

            public String getClubImgUrl() {
                return clubImgUrl == null ? "" : clubImgUrl;
            }

            public void setClubImgUrl(String clubImgUrl) {
                this.clubImgUrl = clubImgUrl;
            }

            public String getQualificationImgId() {
                return qualificationImgId == null ? "" : qualificationImgId;
            }

            public void setQualificationImgId(String qualificationImgId) {
                this.qualificationImgId = qualificationImgId;
            }

            public String getQualificationImgUrl() {
                return qualificationImgUrl == null ? "" : qualificationImgUrl;
            }

            public void setQualificationImgUrl(String qualificationImgUrl) {
                this.qualificationImgUrl = qualificationImgUrl;
            }

            public int getTablesNum() {
                return tablesNum;
            }

            public void setTablesNum(int tablesNum) {
                this.tablesNum = tablesNum;
            }

            public String getTeamIntroduce() {
                return teamIntroduce == null ? "" : teamIntroduce;
            }

            public void setTeamIntroduce(String teamIntroduce) {
                this.teamIntroduce = teamIntroduce;
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

            public String getCoverLogo() {
                return coverLogo == null ? "" : coverLogo;
            }

            public void setCoverLogo(String coverLogo) {
                this.coverLogo = coverLogo;
            }

            public List<String> getUserImage() {
                if (userImage == null) {
                    return new ArrayList<>();
                }
                return userImage;
            }

            public void setUserImage(List<String> userImage) {
                this.userImage = userImage;
            }

    }
}
