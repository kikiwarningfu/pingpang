package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*俱乐部详情*/
public class XlClubInfoBean implements Serializable {


    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * isCharge : null
     * userImage : null
     * inChargeId : 511486160083128336
     * joinStatus : 1
     * recordList : [{"winId":0,"ranking":1,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":2,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":3,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":4,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":5,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":6,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":7,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]},{"winId":0,"ranking":8,"totalCount":0,"recordTypeCount":[{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]}]
     * memberCount : 1
     * memberList : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","headImg":null,"name":"张三","nickName":"张怀紫","ownAchievementDto":null,"ownSign":null,"id":512234257641934864,"clubId":512234257633546256,"userId":511486160083128336,"memberType":"1","status":"2","createUser":511486160083128336,"createTime":"2020-11-14 08:00:00","updateUser":511486160083128336,"updateTime":"2020-11-14 08:00:00","isDelete":"0"}]
     * id : 512234257633546256
     * teamType : 1
     * teamTitle : 狂鸟电竞俱乐部11
     * abbreviation : xljlb
     * inCharge : 郭宏亮
     * phoneNum : 13312356789
     * city : 山西 运城
     * detailAddress : 盐湖区
     * clubImgId : null
     * clubImgUrl : www.baidu.com
     * qualificationImgId : null
     * qualificationImgUrl : www.baidu.com
     * tablesNum : 8
     * teamIntroduce : 大型俱乐部
     * status : 2
     * createUser : 511486160083128336
     * createTime : 2020-11-14 08:00:00
     * updateUser : 511486160083128336
     * updateTime : 2020-11-14 08:00:00
     * isDelete : 0
     * coverLogo :
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private String isCharge;
    private String userImage;
    private long inChargeId;
    private String joinStatus;
    private int memberCount;
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
    private List<RecordListBean> recordList;
    private List<MemberListBean> memberList;

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
        return isCharge == null ? "0" : isCharge;
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
        return joinStatus == null ? "0" : joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
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

    public List<RecordListBean> getRecordList() {
        if (recordList == null) {
            return new ArrayList<>();
        }
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public List<MemberListBean> getMemberList() {
        if (memberList == null) {
            return new ArrayList<>();
        }
        return memberList;
    }

    public void setMemberList(List<MemberListBean> memberList) {
        this.memberList = memberList;
    }

    public static class RecordListBean implements Serializable{
        /**
         * winId : 0
         * ranking : 1
         * totalCount : 0
         * recordTypeCount : [{"projectType":"1","counts":0},{"projectType":"2","counts":0},{"projectType":"3","counts":0},{"projectType":"4","counts":0}]
         */

        private int winId;
        private int ranking;
        private int totalCount;
        private List<RecordTypeCountBean> recordTypeCount;

        public int getWinId() {
            return winId;
        }

        public void setWinId(int winId) {
            this.winId = winId;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<RecordTypeCountBean> getRecordTypeCount() {
            return recordTypeCount;
        }

        public void setRecordTypeCount(List<RecordTypeCountBean> recordTypeCount) {
            this.recordTypeCount = recordTypeCount;
        }

        public static class RecordTypeCountBean implements Serializable{
            /**
             * projectType : 1
             * counts : 0
             */

            private String projectType;
            private int counts;

            public String getProjectType() {
                return projectType;
            }

            public void setProjectType(String projectType) {
                this.projectType = projectType;
            }

            public int getCounts() {
                return counts;
            }

            public void setCounts(int counts) {
                this.counts = counts;
            }
        }
    }

    public static class MemberListBean implements Serializable{
        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * headImg : null
         * name : 张三
         * nickName : 张怀紫
         * ownAchievementDto : null
         * ownSign : null
         * id : 512234257641934864
         * clubId : 512234257633546256
         * userId : 511486160083128336
         * memberType : 1
         * status : 2
         * createUser : 511486160083128336
         * createTime : 2020-11-14 08:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-14 08:00:00
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private String headImg;
        private String name;
        private String nickName;
        private String ownAchievementDto;
        private String ownSign;
        private long id;
        private long clubId;
        private long userId;
        private String memberType;
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

        public String getHeadImg() {
            return headImg == null ? "" : headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName == null ? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOwnAchievementDto() {
            return ownAchievementDto == null ? "" : ownAchievementDto;
        }

        public void setOwnAchievementDto(String ownAchievementDto) {
            this.ownAchievementDto = ownAchievementDto;
        }

        public String getOwnSign() {
            return ownSign == null ? "" : ownSign;
        }

        public void setOwnSign(String ownSign) {
            this.ownSign = ownSign;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getClubId() {
            return clubId;
        }

        public void setClubId(long clubId) {
            this.clubId = clubId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getMemberType() {
            return memberType == null ? "" : memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
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
