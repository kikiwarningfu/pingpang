package com.dnwalter.formlayoutmanager.entity;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

public class Monster extends BaseFormModel {
    private int type;//0 是惊喜  1 是未开始  2 已结束赢了  3 已结束 输了
    private String pageNo;
    private String pageSize;
    private String orderBy;
    private String rounds;
    private String sessions;
    private String leftWinCount;
    private String rightWinCount;
    private String leftWavier;
    private String rightWavier;
    private String left1Name;
    private String right1Name;
    private String status;
    private String waiver;
    private String winTeamId;
    private String id;
    private String contestId;
    private String left1Id;
    private String right1Id;
    private String left2Id;
    private String rigth2Id;
    private String teamLeftId;
    private String teamRightId;
    private String contestType;
    private String leftIntegral;
    private String rughtIntegral;
    private String winId1;
    private String winId2;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String isDelete;
    private String tableNum;
    private String score = "";
    private String intergal;
    private String result;
    private String ranking;
    private String teamId;
    private String showPlayerName;
    private String clubName;
    private String matchId;
    private String clubId;
    private String day;
    private String minute;
    private int showScore;
    private int index;
    private int showType;
    private String itemType;
    private int qianNum;

    public int getQianNum() {
        return qianNum;
    }

    public void setQianNum(int qianNum) {
        this.qianNum = qianNum;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getShowScore() {
        return showScore;
    }

    public void setShowScore(int showScore) {
        this.showScore = showScore;
    }

    public String getDay() {
        return day == null ? "" : day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMinute() {
        return minute == null ? "" : minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMatchId() {
        return matchId == null ? "" : matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getClubId() {
        return clubId == null ? "" : clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getShowPlayerName() {
        return showPlayerName == null ? "" : showPlayerName;
    }

    public void setShowPlayerName(String showPlayerName) {
        this.showPlayerName = showPlayerName;
    }

    public String getClubName() {
        return clubName == null ? "" : clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getTeamId() {
        return teamId == null ? "" : teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getRanking() {
        return ranking == null ? "" : ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIntergal() {
        return intergal == null ? "" : intergal;
    }

    public void setIntergal(String intergal) {
        this.intergal = intergal;
    }

    public String getScore() {
        return score == null ? "" : score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPageNo() {
        return pageNo == null ? "" : pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize == null ? "" : pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy == null ? "" : orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getRounds() {
        return rounds == null ? "" : rounds;
    }

    public void setRounds(String rounds) {
        this.rounds = rounds;
    }

    public String getSessions() {
        return sessions == null ? "" : sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public String getLeftWinCount() {
        return leftWinCount == null ? "" : leftWinCount;
    }

    public void setLeftWinCount(String leftWinCount) {
        this.leftWinCount = leftWinCount;
    }

    public String getRightWinCount() {
        return rightWinCount == null ? "" : rightWinCount;
    }

    public void setRightWinCount(String rightWinCount) {
        this.rightWinCount = rightWinCount;
    }

    public String getLeftWavier() {
        return leftWavier == null ? "" : leftWavier;
    }

    public void setLeftWavier(String leftWavier) {
        this.leftWavier = leftWavier;
    }

    public String getRightWavier() {
        return rightWavier == null ? "" : rightWavier;
    }

    public void setRightWavier(String rightWavier) {
        this.rightWavier = rightWavier;
    }

    public String getLeft1Name() {
        return left1Name == null ? "" : left1Name;
    }

    public void setLeft1Name(String left1Name) {
        this.left1Name = left1Name;
    }

    public String getRight1Name() {
        return right1Name == null ? "" : right1Name;
    }

    public void setRight1Name(String right1Name) {
        this.right1Name = right1Name;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaiver() {
        return waiver == null ? "" : waiver;
    }

    public void setWaiver(String waiver) {
        this.waiver = waiver;
    }

    public String getWinTeamId() {
        return winTeamId == null ? "" : winTeamId;
    }

    public void setWinTeamId(String winTeamId) {
        this.winTeamId = winTeamId;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContestId() {
        return contestId == null ? "" : contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getLeft1Id() {
        return left1Id == null ? "" : left1Id;
    }

    public void setLeft1Id(String left1Id) {
        this.left1Id = left1Id;
    }

    public String getRight1Id() {
        return right1Id == null ? "" : right1Id;
    }

    public void setRight1Id(String right1Id) {
        this.right1Id = right1Id;
    }

    public String getLeft2Id() {
        return left2Id == null ? "" : left2Id;
    }

    public void setLeft2Id(String left2Id) {
        this.left2Id = left2Id;
    }

    public String getRigth2Id() {
        return rigth2Id == null ? "" : rigth2Id;
    }

    public void setRigth2Id(String rigth2Id) {
        this.rigth2Id = rigth2Id;
    }

    public String getTeamLeftId() {
        return teamLeftId == null ? "" : teamLeftId;
    }

    public void setTeamLeftId(String teamLeftId) {
        this.teamLeftId = teamLeftId;
    }

    public String getTeamRightId() {
        return teamRightId == null ? "" : teamRightId;
    }

    public void setTeamRightId(String teamRightId) {
        this.teamRightId = teamRightId;
    }

    public String getContestType() {
        return contestType == null ? "" : contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getLeftIntegral() {
        return leftIntegral == null ? "" : leftIntegral;
    }

    public void setLeftIntegral(String leftIntegral) {
        this.leftIntegral = leftIntegral;
    }

    public String getRughtIntegral() {
        return rughtIntegral == null ? "" : rughtIntegral;
    }

    public void setRughtIntegral(String rughtIntegral) {
        this.rughtIntegral = rughtIntegral;
    }

    public String getWinId1() {
        return winId1 == null ? "" : winId1;
    }

    public void setWinId1(String winId1) {
        this.winId1 = winId1;
    }

    public String getWinId2() {
        return winId2 == null ? "" : winId2;
    }

    public void setWinId2(String winId2) {
        this.winId2 = winId2;
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

    public String getTableNum() {
        return tableNum == null ? "" : tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }
}
