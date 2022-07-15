package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class XlUserInfoListBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * size : 2
         * startRow : 1
         * endRow : 2
         * total : 2
         * pages : 1
         * list : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","age":0,"timeFilter":null,"totalCount":0,"winCount":0,"loseCount":0,"winningProbability":null,"loginType":null,"id":511215427033534480,"name":null,"account":"13389765273","oppenId":"12345678","qqId":null,"iosId":null,"password":null,"accountStatus":null,"identificationStatus":"2","identificationFront":null,"identificationBack":null,"headImg":null,"nickName":null,"bornDate":"2019-07-05 08:00:00","sex":null,"identificationNum":null,"ownSign":null,"clapHand":"1","gripMethon":null,"rightPlayMethon":null,"leftPlayMethon":null,"floorBrandId":null,"floorModelId":null,"rightBrandId":null,"rightModelId":null,"rightLandId":null,"rightHardId":null,"rightColorId":null,"leftBrandId":null,"leftModelId":null,"leftLandId":null,"leftHardId":null,"leftColorId":null,"dressSize":null,"shoesSize":null,"createUser":511215427033534480,"createTime":"2020-11-11 08:00:00","updateUser":511215427033534480,"updateTime":"2020-11-11 08:00:00","isDelete":"0","address":null,"matchIntegral":null,"autographImg":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","age":0,"timeFilter":null,"totalCount":0,"winCount":0,"loseCount":0,"winningProbability":null,"loginType":null,"id":511486160083128336,"name":"张三","account":"13292803269","oppenId":null,"qqId":null,"iosId":null,"password":null,"accountStatus":null,"identificationStatus":"2","identificationFront":null,"identificationBack":null,"headImg":null,"nickName":"张怀紫","bornDate":null,"sex":null,"identificationNum":null,"ownSign":null,"clapHand":"1","gripMethon":null,"rightPlayMethon":null,"leftPlayMethon":null,"floorBrandId":null,"floorModelId":null,"rightBrandId":null,"rightModelId":null,"rightLandId":null,"rightHardId":null,"rightColorId":null,"leftBrandId":null,"leftModelId":null,"leftLandId":null,"leftHardId":null,"leftColorId":null,"dressSize":null,"shoesSize":null,"createUser":511486160083128336,"createTime":"2020-11-12 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-12 08:00:00","isDelete":"0","address":null,"matchIntegral":null,"autographImg":null}]
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
             * age : 0
             * timeFilter : null
             * totalCount : 0
             * winCount : 0
             * loseCount : 0
             * winningProbability : null
             * loginType : null
             * id : 511215427033534480
             * name : null
             * account : 13389765273
             * oppenId : 12345678
             * qqId : null
             * iosId : null
             * password : null
             * accountStatus : null
             * identificationStatus : 2
             * identificationFront : null
             * identificationBack : null
             * headImg : null
             * nickName : null
             * bornDate : 2019-07-05 08:00:00
             * sex : null
             * identificationNum : null
             * ownSign : null
             * clapHand : 1
             * gripMethon : null
             * rightPlayMethon : null
             * leftPlayMethon : null
             * floorBrandId : null
             * floorModelId : null
             * rightBrandId : null
             * rightModelId : null
             * rightLandId : null
             * rightHardId : null
             * rightColorId : null
             * leftBrandId : null
             * leftModelId : null
             * leftLandId : null
             * leftHardId : null
             * leftColorId : null
             * dressSize : null
             * shoesSize : null
             * createUser : 511215427033534480
             * createTime : 2020-11-11 08:00:00
             * updateUser : 511215427033534480
             * updateTime : 2020-11-11 08:00:00
             * isDelete : 0
             * address : null
             * matchIntegral : null
             * autographImg : null
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private int age;
            private String timeFilter;
            private int totalCount;
            private int winCount;
            private int loseCount;
            private String winningProbability;
            private String loginType;
            private long id;
            private String name;
            private String account;
            private String oppenId;
            private String qqId;
            private String iosId;
            private String password;
            private String accountStatus;
            private String identificationStatus;
            private String identificationFront;
            private String identificationBack;
            private String headImg;
            private String nickName;
            private String bornDate;
            private String sex;//性别
            private String identificationNum;
            private String ownSign;
            private String clapHand;
            private String gripMethon;
            private String rightPlayMethon;
            private String leftPlayMethon;
            private String floorBrandId;
            private String floorModelId;
            private String rightBrandId;
            private String rightModelId;
            private String rightLandId;
            private String rightHardId;
            private String rightColorId;
            private String leftBrandId;
            private String leftModelId;
            private String leftLandId;
            private String leftHardId;
            private String leftColorId;
            private String dressSize;
            private String shoesSize;
            private long createUser;
            private String createTime;
            private long updateUser;
            private String updateTime;
            private String isDelete;
            private String address;
            private String matchIntegral;
            private String autographImg;

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

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getTimeFilter() {
                return timeFilter == null ? "" : timeFilter;
            }

            public void setTimeFilter(String timeFilter) {
                this.timeFilter = timeFilter;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getWinCount() {
                return winCount;
            }

            public void setWinCount(int winCount) {
                this.winCount = winCount;
            }

            public int getLoseCount() {
                return loseCount;
            }

            public void setLoseCount(int loseCount) {
                this.loseCount = loseCount;
            }

            public String getWinningProbability() {
                return winningProbability == null ? "0%" : winningProbability;
            }

            public void setWinningProbability(String winningProbability) {
                this.winningProbability = winningProbability;
            }

            public String getLoginType() {
                return loginType == null ? "" : loginType;
            }

            public void setLoginType(String loginType) {
                this.loginType = loginType;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccount() {
                return account == null ? "" : account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getOppenId() {
                return oppenId == null ? "" : oppenId;
            }

            public void setOppenId(String oppenId) {
                this.oppenId = oppenId;
            }

            public String getQqId() {
                return qqId == null ? "" : qqId;
            }

            public void setQqId(String qqId) {
                this.qqId = qqId;
            }

            public String getIosId() {
                return iosId == null ? "" : iosId;
            }

            public void setIosId(String iosId) {
                this.iosId = iosId;
            }

            public String getPassword() {
                return password == null ? "" : password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getAccountStatus() {
                return accountStatus == null ? "" : accountStatus;
            }

            public void setAccountStatus(String accountStatus) {
                this.accountStatus = accountStatus;
            }

            public String getIdentificationStatus() {
                return identificationStatus == null ? "" : identificationStatus;
            }

            public void setIdentificationStatus(String identificationStatus) {
                this.identificationStatus = identificationStatus;
            }

            public String getIdentificationFront() {
                return identificationFront == null ? "" : identificationFront;
            }

            public void setIdentificationFront(String identificationFront) {
                this.identificationFront = identificationFront;
            }

            public String getIdentificationBack() {
                return identificationBack == null ? "" : identificationBack;
            }

            public void setIdentificationBack(String identificationBack) {
                this.identificationBack = identificationBack;
            }

            public String getHeadImg() {
                return headImg == null ? "" : headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getNickName() {
                return nickName == null ? "" : nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getBornDate() {
                return bornDate == null ? "" : bornDate;
            }

            public void setBornDate(String bornDate) {
                this.bornDate = bornDate;
            }

            public String getSex() {
                return sex == null ? "" : sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getIdentificationNum() {
                return identificationNum == null ? "" : identificationNum;
            }

            public void setIdentificationNum(String identificationNum) {
                this.identificationNum = identificationNum;
            }

            public String getOwnSign() {
                return ownSign == null ? "" : ownSign;
            }

            public void setOwnSign(String ownSign) {
                this.ownSign = ownSign;
            }

            public String getClapHand() {
                return clapHand == null ? "" : clapHand;
            }

            public void setClapHand(String clapHand) {
                this.clapHand = clapHand;
            }

            public String getGripMethon() {
                return gripMethon == null ? "" : gripMethon;
            }

            public void setGripMethon(String gripMethon) {
                this.gripMethon = gripMethon;
            }

            public String getRightPlayMethon() {
                return rightPlayMethon == null ? "" : rightPlayMethon;
            }

            public void setRightPlayMethon(String rightPlayMethon) {
                this.rightPlayMethon = rightPlayMethon;
            }

            public String getLeftPlayMethon() {
                return leftPlayMethon == null ? "" : leftPlayMethon;
            }

            public void setLeftPlayMethon(String leftPlayMethon) {
                this.leftPlayMethon = leftPlayMethon;
            }

            public String getFloorBrandId() {
                return floorBrandId == null ? "" : floorBrandId;
            }

            public void setFloorBrandId(String floorBrandId) {
                this.floorBrandId = floorBrandId;
            }

            public String getFloorModelId() {
                return floorModelId == null ? "" : floorModelId;
            }

            public void setFloorModelId(String floorModelId) {
                this.floorModelId = floorModelId;
            }

            public String getRightBrandId() {
                return rightBrandId == null ? "" : rightBrandId;
            }

            public void setRightBrandId(String rightBrandId) {
                this.rightBrandId = rightBrandId;
            }

            public String getRightModelId() {
                return rightModelId == null ? "" : rightModelId;
            }

            public void setRightModelId(String rightModelId) {
                this.rightModelId = rightModelId;
            }

            public String getRightLandId() {
                return rightLandId == null ? "" : rightLandId;
            }

            public void setRightLandId(String rightLandId) {
                this.rightLandId = rightLandId;
            }

            public String getRightHardId() {
                return rightHardId == null ? "" : rightHardId;
            }

            public void setRightHardId(String rightHardId) {
                this.rightHardId = rightHardId;
            }

            public String getRightColorId() {
                return rightColorId == null ? "" : rightColorId;
            }

            public void setRightColorId(String rightColorId) {
                this.rightColorId = rightColorId;
            }

            public String getLeftBrandId() {
                return leftBrandId == null ? "" : leftBrandId;
            }

            public void setLeftBrandId(String leftBrandId) {
                this.leftBrandId = leftBrandId;
            }

            public String getLeftModelId() {
                return leftModelId == null ? "" : leftModelId;
            }

            public void setLeftModelId(String leftModelId) {
                this.leftModelId = leftModelId;
            }

            public String getLeftLandId() {
                return leftLandId == null ? "" : leftLandId;
            }

            public void setLeftLandId(String leftLandId) {
                this.leftLandId = leftLandId;
            }

            public String getLeftHardId() {
                return leftHardId == null ? "" : leftHardId;
            }

            public void setLeftHardId(String leftHardId) {
                this.leftHardId = leftHardId;
            }

            public String getLeftColorId() {
                return leftColorId == null ? "" : leftColorId;
            }

            public void setLeftColorId(String leftColorId) {
                this.leftColorId = leftColorId;
            }

            public String getDressSize() {
                return dressSize == null ? "" : dressSize;
            }

            public void setDressSize(String dressSize) {
                this.dressSize = dressSize;
            }

            public String getShoesSize() {
                return shoesSize == null ? "" : shoesSize;
            }

            public void setShoesSize(String shoesSize) {
                this.shoesSize = shoesSize;
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

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMatchIntegral() {
                return matchIntegral == null ? "" : matchIntegral;
            }

            public void setMatchIntegral(String matchIntegral) {
                this.matchIntegral = matchIntegral;
            }

            public String getAutographImg() {
                return autographImg == null ? "" : autographImg;
            }

            public void setAutographImg(String autographImg) {
                this.autographImg = autographImg;
            }
        }

}
