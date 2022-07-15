package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class GameMatchBean implements Serializable {

    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy : id desc
     * matchStatus : null
     * replaceChargeEat : 0
     * matchRules : null
     * projectList : null
     * id : null
     * matchTitle : null
     * applyClubId : null
     * sponsor : 不
     * organizer : 不
     * registrationDeadline : 2020-11-19 00:00
     * beginTime : 2020-11-18
     * endTime : 2020-11-18
     * matchImgId : null
     * matchImgUrl : null
     * stampUpload : null
     * stampImgId : null
     * stampImgUrl : null
     * holdCity : null
     * venue : 你也
     * linkPerson : 不会
     * linkNum : null
     * ownRegistration : null
     * authentication : null
     * replaceCharge : null
     * registrationFeeType : null
     * registrationCount : null
     * ownFree : 0
     * eatFree : null
     * daysCount : null
     * eatAllFree : null
     * matchGrade : null
     * matchLevel : null
     * status : null
     * createUser : null
     * createTime : null
     * updateUser : null
     * updateTime : null
     * isDelete : null
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private String matchStatus;
    private int replaceChargeEat;
    private String matchRules;
    private String projectList;
    private String id;
    private String matchTitle;
    private String applyClubId;
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
    private String registrationCount;
    private int ownFree;
    private String eatFree;
    private String daysCount;
    private String eatAllFree;
    private String matchGrade;
    private String matchLevel;
    private String status;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String isDelete;
    private String phoneNumber;//手机号
    private int joinCount;

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    private String lngLat;

    public String getLngLat() {
        return lngLat == null ? "" : lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    public String getPhoneNumber() {
        return phoneNumber == null ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getApplyClubId() {
        return applyClubId == null ? "" : applyClubId;
    }

    public void setApplyClubId(String applyClubId) {
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
        return ownRegistration == null ? "0" : ownRegistration;
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
        return replaceCharge == null ? "0" : replaceCharge;
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

    public String getRegistrationCount() {
        return registrationCount == null ? "" : registrationCount;
    }

    public void setRegistrationCount(String registrationCount) {
        this.registrationCount = registrationCount;
    }

    public int getOwnFree() {
        return ownFree;
    }

    public void setOwnFree(int ownFree) {
        this.ownFree = ownFree;
    }

    public String getEatFree() {
        return eatFree == null ? "" : eatFree;
    }

    public void setEatFree(String eatFree) {
        this.eatFree = eatFree;
    }

    public String getDaysCount() {
        return daysCount == null ? "" : daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
    }

    public String getEatAllFree() {
        return eatAllFree == null ? "" : eatAllFree;
    }

    public void setEatAllFree(String eatAllFree) {
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

    public String getCreateUser() {
        return createUser == null ? "" : createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser == null ? "" : updateUser;
    }

    public void setUpdateUser(String updateUser) {
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
