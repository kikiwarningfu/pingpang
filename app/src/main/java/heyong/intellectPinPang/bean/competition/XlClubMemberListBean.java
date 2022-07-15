package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class XlClubMemberListBean {


    private List<ChargeListBean> chargeList;
    private List<ChargeListBean> others;

    public List<ChargeListBean> getChargeList() {
        if (chargeList == null) {
            return new ArrayList<>();
        }
        return chargeList;
    }

    public void setChargeList(List<ChargeListBean> chargeList) {
        this.chargeList = chargeList;
    }

    public List<ChargeListBean> getOthers() {
        if (others == null) {
            return new ArrayList<>();
        }
        return others;
    }

    public void setOthers(List<ChargeListBean> others) {
        this.others = others;
    }

    public static class ChargeListBean {
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
        private String fistLetter;

        public String getFistLetter() {
            return fistLetter == null ? "" : fistLetter;
        }

        public void setFistLetter(String fistLetter) {
            this.fistLetter = fistLetter;
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
            return memberType == null ? "0" : memberType;
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
