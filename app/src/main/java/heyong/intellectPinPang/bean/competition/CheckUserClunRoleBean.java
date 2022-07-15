package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class CheckUserClunRoleBean {

        /**
         * matchTitle : 北京二级运动员晋级赛
         * clubInfo : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","isCharge":null,"userImage":null,"inChargeId":511486160083128336,"joinStatus":null,"recordList":null,"memberCount":0,"memberList":null,"id":512196047566049296,"teamType":"3","teamTitle":"1231","abbreviation":"12312","inCharge":"123123","phoneNum":"123123","city":"北京 北京 东城","detailAddress":"123123","clubImgId":null,"clubImgUrl":"http://images.xlttsports.com/android_img_20201114092405895","qualificationImgId":null,"qualificationImgUrl":"http://images.xlttsports.com/android_img_20201114092409432","tablesNum":123,"teamIntroduce":"123123","status":"2","createUser":511486160083128336,"createTime":"2020-11-14 00:00:00","updateUser":511486160083128336,"updateTime":"2020-12-16 16:42:27","isDelete":"0","coverLogo":"http://images.xlttsports.com/android_img_20201114092349902"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","isCharge":null,"userImage":null,"inChargeId":0,"joinStatus":null,"recordList":null,"memberCount":0,"memberList":null,"id":512196767447027728,"teamType":"2","teamTitle":"俱乐部","abbreviation":"哈哈哈哈哈","inCharge":"楚河","phoneNum":"13292893293","city":"北京 北京 东城","detailAddress":"11","clubImgId":null,"clubImgUrl":"http://images.xlttsports.com/android_img_20201114092653780","qualificationImgId":null,"qualificationImgUrl":"http://images.xlttsports.com/android_img_20201114092657792","tablesNum":12,"teamIntroduce":"11111111","status":"2","createUser":511486160083128336,"createTime":"2020-11-14 00:00:00","updateUser":511486160083128336,"updateTime":"2020-12-05 12:38:07","isDelete":"0","coverLogo":"http://images.xlttsports.com/android_img_20201114092629056"}]
         */

        private String matchTitle;
        private List<ClubInfoBean> clubInfo;

        public String getMatchTitle() {
            return matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
        }

        public List<ClubInfoBean> getClubInfo() {
            return clubInfo;
        }

        public void setClubInfo(List<ClubInfoBean> clubInfo) {
            this.clubInfo = clubInfo;
        }

        public static class ClubInfoBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * isCharge : null
             * userImage : null
             * inChargeId : 511486160083128336
             * joinStatus : null
             * recordList : null
             * memberCount : 0
             * memberList : null
             * id : 512196047566049296
             * teamType : 3
             * teamTitle : 1231
             * abbreviation : 12312
             * inCharge : 123123
             * phoneNum : 123123
             * city : 北京 北京 东城
             * detailAddress : 123123
             * clubImgId : null
             * clubImgUrl : http://images.xlttsports.com/android_img_20201114092405895
             * qualificationImgId : null
             * qualificationImgUrl : http://images.xlttsports.com/android_img_20201114092409432
             * tablesNum : 123
             * teamIntroduce : 123123
             * status : 2
             * createUser : 511486160083128336
             * createTime : 2020-11-14 00:00:00
             * updateUser : 511486160083128336
             * updateTime : 2020-12-16 16:42:27
             * isDelete : 0
             * coverLogo : http://images.xlttsports.com/android_img_20201114092349902
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String isCharge;
            private String userImage;
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

            public String getUserImage() {
                return userImage == null ? "" : userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
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
        }

}
