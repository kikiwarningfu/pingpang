package heyong.intellectPinPang.bean.gsonbean;

import java.io.Serializable;
import java.util.List;

public class CreateEventsBean implements Serializable {


    /**
     * matchTitle : 北京二级运动员晋级赛
     * applyClubId : 512196767447027728
     * sponsor : 北京体育局
     * organizer : 狂鸟俱乐部
     * registrationDeadline : 2021-12-15 00:00:00
     * beginTime : 2021-12-16 00:00:00
     * endTime : 2021-12-19 00:00:00
     * matchImgUrl : www.baidu.com
     * stampUpload : 1
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
     * matchGrade : 全国级
     * matchLevel : 专业级
     * projectList : [{"projectTitle":"成人甲组","projectItemList":[{"sexType":"1","minAge":"2021-12-16 00:00:00","maxAge":"2021-12-16 00:00:00","projectItem":"团体","minLimit":"1","maxLimit":"1","itemTitle":"成人甲组男子团体"}]}]
     */

    private String matchTitle;
    private String applyClubId;
    private String sponsor;
    private String organizer;
    private String registrationDeadline;
    private String beginTime;
    private String endTime;
    private String matchImgUrl;
    private String stampUpload;
    private String stampImgUrl;
    private String holdCity;
    private String venue;
    private String linkPerson;
    private String linkNum;
    private String ownRegistration;
    private String authentication;
    private String replaceCharge;
    private String registrationFeeType;
    private String registrationCount;
    private String ownFree;
    private String eatFree;
    private String matchGrade;
    private String matchLevel;
    private String replaceChargeEat;
    private List<ProjectListBean> projectList;

    public String getReplaceChargeEat() {
        return replaceChargeEat == null ? "" : replaceChargeEat;
    }

    public void setReplaceChargeEat(String replaceChargeEat) {
        this.replaceChargeEat = replaceChargeEat;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getApplyClubId() {
        return applyClubId;
    }

    public void setApplyClubId(String applyClubId) {
        this.applyClubId = applyClubId;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMatchImgUrl() {
        return matchImgUrl;
    }

    public void setMatchImgUrl(String matchImgUrl) {
        this.matchImgUrl = matchImgUrl;
    }

    public String getStampUpload() {
        return stampUpload;
    }

    public void setStampUpload(String stampUpload) {
        this.stampUpload = stampUpload;
    }

    public String getStampImgUrl() {
        return stampImgUrl;
    }

    public void setStampImgUrl(String stampImgUrl) {
        this.stampImgUrl = stampImgUrl;
    }

    public String getHoldCity() {
        return holdCity;
    }

    public void setHoldCity(String holdCity) {
        this.holdCity = holdCity;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(String linkNum) {
        this.linkNum = linkNum;
    }

    public String getOwnRegistration() {
        return ownRegistration;
    }

    public void setOwnRegistration(String ownRegistration) {
        this.ownRegistration = ownRegistration;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getReplaceCharge() {
        return replaceCharge;
    }

    public void setReplaceCharge(String replaceCharge) {
        this.replaceCharge = replaceCharge;
    }

    public String getRegistrationFeeType() {
        return registrationFeeType;
    }

    public void setRegistrationFeeType(String registrationFeeType) {
        this.registrationFeeType = registrationFeeType;
    }

    public String getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(String registrationCount) {
        this.registrationCount = registrationCount;
    }

    public String getOwnFree() {
        return ownFree;
    }

    public void setOwnFree(String ownFree) {
        this.ownFree = ownFree;
    }

    public String getEatFree() {
        return eatFree;
    }

    public void setEatFree(String eatFree) {
        this.eatFree = eatFree;
    }

    public String getMatchGrade() {
        return matchGrade;
    }

    public void setMatchGrade(String matchGrade) {
        this.matchGrade = matchGrade;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public List<ProjectListBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListBean> projectList) {
        this.projectList = projectList;
    }

    public static class ProjectListBean implements Serializable{

        public ProjectListBean(String projectTitle, List<ProjectItemListBean> projectItemList) {
            this.projectTitle = projectTitle;
            this.projectItemList = projectItemList;
        }

        /**
         * projectTitle : 成人甲组
         * projectItemList : [{"sexType":"1","minAge":"2021-12-16 00:00:00","maxAge":"2021-12-16 00:00:00","projectItem":"团体","minLimit":"1","maxLimit":"1","itemTitle":"成人甲组男子团体"}]
         */

        private String projectTitle;
        private List<ProjectItemListBean> projectItemList;

        public String getProjectTitle() {
            return projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        public List<ProjectItemListBean> getProjectItemList() {
            return projectItemList;
        }

        public void setProjectItemList(List<ProjectItemListBean> projectItemList) {
            this.projectItemList = projectItemList;
        }

        public static class ProjectItemListBean implements Serializable {
            /**
             * sexType : 1
             * minAge : 2021-12-16 00:00:00
             * maxAge : 2021-12-16 00:00:00
             * projectItem : 团体
             * minLimit : 1
             * maxLimit : 1
             * itemTitle : 成人甲组男子团体
             */

            private String sexType;
            private String minAge;
            private String maxAge;
            private String projectItem;
            private String minLimit;
            private String maxLimit;
            private String itemTitle;
            private String manMinAge;
            private String manMaxAge;
            private String womanMinAge;
            private String womanMaxAge;

            public String getSexType() {
                return sexType == null ? "" : sexType;
            }

            public void setSexType(String sexType) {
                this.sexType = sexType;
            }

            public String getMinAge() {
                return minAge == null ? "" : minAge;
            }

            public void setMinAge(String minAge) {
                this.minAge = minAge;
            }

            public String getMaxAge() {
                return maxAge == null ? "" : maxAge;
            }

            public void setMaxAge(String maxAge) {
                this.maxAge = maxAge;
            }

            public String getProjectItem() {
                return projectItem == null ? "" : projectItem;
            }

            public void setProjectItem(String projectItem) {
                this.projectItem = projectItem;
            }

            public String getMinLimit() {
                return minLimit == null ? "" : minLimit;
            }

            public void setMinLimit(String minLimit) {
                this.minLimit = minLimit;
            }

            public String getMaxLimit() {
                return maxLimit == null ? "" : maxLimit;
            }

            public void setMaxLimit(String maxLimit) {
                this.maxLimit = maxLimit;
            }

            public String getItemTitle() {
                return itemTitle == null ? "" : itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getManMinAge() {
                return manMinAge == null ? "" : manMinAge;
            }

            public void setManMinAge(String manMinAge) {
                this.manMinAge = manMinAge;
            }

            public String getManMaxAge() {
                return manMaxAge == null ? "" : manMaxAge;
            }

            public void setManMaxAge(String manMaxAge) {
                this.manMaxAge = manMaxAge;
            }

            public String getWomanMinAge() {
                return womanMinAge == null ? "" : womanMinAge;
            }

            public void setWomanMinAge(String womanMinAge) {
                this.womanMinAge = womanMinAge;
            }

            public String getWomanMaxAge() {
                return womanMaxAge == null ? "" : womanMaxAge;
            }

            public void setWomanMaxAge(String womanMaxAge) {
                this.womanMaxAge = womanMaxAge;
            }
        }
    }
}
