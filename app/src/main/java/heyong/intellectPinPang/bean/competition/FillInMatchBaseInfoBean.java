package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class FillInMatchBaseInfoBean {

    /**
     * title : 男子单打循环赛
     * ownClubName : 野狼
     * ownList : [{"arrangeId":1,"teamArrangeId":1,"hitType":"1","ownTeamId":528612125992964528,"player1Set":"A","player2Set":null}]
     * opponentClubName : 战狼
     * coach : [{"clapHand":"1","gripMethon":"1","rightPlayMethon":"2","leftPlayMethon":"1","floorBrandId":517295786057830416,"floorModelId":517340571653148688,"rightBrandId":518475288938959360,"rightModelId":517340571653148688,"rightLandId":517340571669925904,"rightHardId":517340571686703120,"rightColorId":1,"leftBrandId":518475162799460864,"leftModelId":517340571653148688,"leftLandId":517340571653148689,"leftHardId":517340571699286032,"leftColorId":1,"id":"511486160083128336","name":"王洪福","account":"13292803269","accountStatus":"3","identificationStatus":"2","headImg":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg","nickName":"dasdasd","bornDate":"1995-04-05","sex":"1","identificationNum":"131121199504050238","ownSign":"asdasdasd","clapHandName":null,"gripMethonName":null,"rightPlayMethonName":null,"leftPlayMethonName":null,"floorBrandName":null,"floorModelName":null,"rightBrandName":null,"rightModelName":null,"rightLandName":null,"rightHardName":null,"rightColorName":null,"leftBrandName":null,"leftModelName":null,"leftLandName":null,"leftHardName":null,"leftColorName":null,"dressSize":"XL","shoesSize":"43","createUser":"511486160083128336","createTime":"2020-11-12 00:00:00","updateUser":"511486160083128336","updateTime":"2020-11-12 00:00:00","isDelete":"0","address":"河北 石家庄 长安","autographImg":"http://images.xlttsports.com/20201219153217009059.png"},{"clapHand":"1","gripMethon":"1","rightPlayMethon":"2","leftPlayMethon":"1","floorBrandId":517295786057830416,"floorModelId":517340571653148688,"rightBrandId":518475288938959360,"rightModelId":517340571653148688,"rightLandId":517340571669925904,"rightHardId":517340571686703120,"rightColorId":1,"leftBrandId":518475162799460864,"leftModelId":517340571653148688,"leftLandId":517340571653148689,"leftHardId":517340571699286032,"leftColorId":1,"id":"511486160083128336","name":"王洪福","account":"13292803269","accountStatus":"3","identificationStatus":"2","headImg":"https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg","nickName":"dasdasd","bornDate":"1995-04-05","sex":"1","identificationNum":"131121199504050238","ownSign":"asdasdasd","clapHandName":null,"gripMethonName":null,"rightPlayMethonName":null,"leftPlayMethonName":null,"floorBrandName":null,"floorModelName":null,"rightBrandName":null,"rightModelName":null,"rightLandName":null,"rightHardName":null,"rightColorName":null,"leftBrandName":null,"leftModelName":null,"leftLandName":null,"leftHardName":null,"leftColorName":null,"dressSize":"XL","shoesSize":"43","createUser":"511486160083128336","createTime":"2020-11-12 00:00:00","updateUser":"511486160083128336","updateTime":"2020-11-12 00:00:00","isDelete":"0","address":"河北 石家庄 长安","autographImg":"http://images.xlttsports.com/20201219153217009059.png"}]
     * players : [{"clapHand":null,"gripMethon":null,"rightPlayMethon":null,"leftPlayMethon":null,"floorBrandId":null,"floorModelId":null,"rightBrandId":null,"rightModelId":null,"rightLandId":null,"rightHardId":null,"rightColorId":null,"leftBrandId":null,"leftModelId":null,"leftLandId":null,"leftHardId":null,"leftColorId":null,"id":"511922527137206288","name":"律香川","account":null,"accountStatus":null,"identificationStatus":"1","headImg":null,"nickName":null,"bornDate":"1995-04-05","sex":"1","identificationNum":null,"ownSign":null,"clapHandName":null,"gripMethonName":null,"rightPlayMethonName":null,"leftPlayMethonName":null,"floorBrandName":null,"floorModelName":null,"rightBrandName":null,"rightModelName":null,"rightLandName":null,"rightHardName":null,"rightColorName":null,"leftBrandName":null,"leftModelName":null,"leftLandName":null,"leftHardName":null,"leftColorName":null,"dressSize":null,"shoesSize":null,"createUser":"511922527137206288","createTime":"2020-11-13 00:00:00","updateUser":"511922527137206288","updateTime":"2020-11-13 00:00:00","isDelete":"0","address":null,"autographImg":null},{"clapHand":null,"gripMethon":null,"rightPlayMethon":null,"leftPlayMethon":null,"floorBrandId":null,"floorModelId":null,"rightBrandId":null,"rightModelId":null,"rightLandId":null,"rightHardId":null,"rightColorId":null,"leftBrandId":null,"leftModelId":null,"leftLandId":null,"leftHardId":null,"leftColorId":null,"id":"526770476186751408","name":"不三","account":"17331255271","accountStatus":null,"identificationStatus":"1","headImg":null,"nickName":"沈紫","bornDate":"1995-04-05","sex":"2","identificationNum":null,"ownSign":null,"clapHandName":null,"gripMethonName":null,"rightPlayMethonName":null,"leftPlayMethonName":null,"floorBrandName":null,"floorModelName":null,"rightBrandName":null,"rightModelName":null,"rightLandName":null,"rightHardName":null,"rightColorName":null,"leftBrandName":null,"leftModelName":null,"leftLandName":null,"leftHardName":null,"leftColorName":null,"dressSize":null,"shoesSize":null,"createUser":"526770476186751408","createTime":"2020-12-24 14:37:48","updateUser":"526770476186751408","updateTime":"2020-12-24 14:37:48","isDelete":"0","address":null,"autographImg":null}]
     * leftOrRight : 2
     * appOperationLogId : 535046530349289904
     */

    private String title;
    private String ownClubName;
    private String opponentClubName;
    private String leftOrRight;
    private long appOperationLogId;
    private List<OwnListBean> ownList;
    private List<CoachBean> coach;
    private List<PlayersBean> players;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnClubName() {
        return ownClubName == null ? "" : ownClubName;
    }

    public void setOwnClubName(String ownClubName) {
        this.ownClubName = ownClubName;
    }

    public String getOpponentClubName() {
        return opponentClubName == null ? "" : opponentClubName;
    }

    public void setOpponentClubName(String opponentClubName) {
        this.opponentClubName = opponentClubName;
    }

    public String getLeftOrRight() {
        return leftOrRight == null ? "" : leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public long getAppOperationLogId() {
        return appOperationLogId;
    }

    public void setAppOperationLogId(long appOperationLogId) {
        this.appOperationLogId = appOperationLogId;
    }

    public List<OwnListBean> getOwnList() {
        return ownList;
    }

    public void setOwnList(List<OwnListBean> ownList) {
        this.ownList = ownList;
    }

    public List<CoachBean> getCoach() {
        return coach;
    }

    public void setCoach(List<CoachBean> coach) {
        this.coach = coach;
    }

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class OwnListBean {
        /**
         * arrangeId : 1
         * teamArrangeId : 1
         * hitType : 1
         * ownTeamId : 528612125992964528
         * player1Set : A
         * player2Set : null
         */

        private long arrangeId;
        private long teamArrangeId;
        private String hitType;
        private long ownTeamId;
        private String player1Set;
        private String player2Set;
        private String player1Id;
        private String player2Id;

        public String getPlayer1Id() {
            return player1Id == null ? "" : player1Id;
        }

        public void setPlayer1Id(String player1Id) {
            this.player1Id = player1Id;
        }

        public String getPlayer2Id() {
            return player2Id == null ? "" : player2Id;
        }

        public void setPlayer2Id(String player2Id) {
            this.player2Id = player2Id;
        }

        public long getArrangeId() {
            return arrangeId;
        }

        public void setArrangeId(long arrangeId) {
            this.arrangeId = arrangeId;
        }

        public long getTeamArrangeId() {
            return teamArrangeId;
        }

        public void setTeamArrangeId(long teamArrangeId) {
            this.teamArrangeId = teamArrangeId;
        }

        public String getHitType() {
            return hitType == null ? "1" : hitType;
        }

        public void setHitType(String hitType) {
            this.hitType = hitType;
        }

        public long getOwnTeamId() {
            return ownTeamId;
        }

        public void setOwnTeamId(long ownTeamId) {
            this.ownTeamId = ownTeamId;
        }

        public String getPlayer1Set() {
            return player1Set == null ? "" : player1Set;
        }

        public void setPlayer1Set(String player1Set) {
            this.player1Set = player1Set;
        }

        public String getPlayer2Set() {
            return player2Set == null ? "" : player2Set;
        }

        public void setPlayer2Set(String player2Set) {
            this.player2Set = player2Set;
        }
    }

    public static class CoachBean {
        /**
         * clapHand : 1
         * gripMethon : 1
         * rightPlayMethon : 2
         * leftPlayMethon : 1
         * floorBrandId : 517295786057830416
         * floorModelId : 517340571653148688
         * rightBrandId : 518475288938959360
         * rightModelId : 517340571653148688
         * rightLandId : 517340571669925904
         * rightHardId : 517340571686703120
         * rightColorId : 1
         * leftBrandId : 518475162799460864
         * leftModelId : 517340571653148688
         * leftLandId : 517340571653148689
         * leftHardId : 517340571699286032
         * leftColorId : 1
         * id : 511486160083128336
         * name : 王洪福
         * account : 13292803269
         * accountStatus : 3
         * identificationStatus : 2
         * headImg : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
         * nickName : dasdasd
         * bornDate : 1995-04-05
         * sex : 1
         * identificationNum : 131121199504050238
         * ownSign : asdasdasd
         * clapHandName : null
         * gripMethonName : null
         * rightPlayMethonName : null
         * leftPlayMethonName : null
         * floorBrandName : null
         * floorModelName : null
         * rightBrandName : null
         * rightModelName : null
         * rightLandName : null
         * rightHardName : null
         * rightColorName : null
         * leftBrandName : null
         * leftModelName : null
         * leftLandName : null
         * leftHardName : null
         * leftColorName : null
         * dressSize : XL
         * shoesSize : 43
         * createUser : 511486160083128336
         * createTime : 2020-11-12 00:00:00
         * updateUser : 511486160083128336
         * updateTime : 2020-11-12 00:00:00
         * isDelete : 0
         * address : 河北 石家庄 长安
         * autographImg : http://images.xlttsports.com/20201219153217009059.png
         */

        private String clapHand;
        private String gripMethon;
        private String rightPlayMethon;
        private String leftPlayMethon;
        private long floorBrandId;
        private long floorModelId;
        private long rightBrandId;
        private long rightModelId;
        private long rightLandId;
        private long rightHardId;
        private int rightColorId;
        private long leftBrandId;
        private long leftModelId;
        private long leftLandId;
        private long leftHardId;
        private int leftColorId;
        private String id;
        private String name;
        private String account;
        private String accountStatus;
        private String identificationStatus;
        private String headImg;
        private String nickName;
        private String bornDate;
        private String sex;
        private String identificationNum;
        private String ownSign;
        private String clapHandName;
        private String gripMethonName;
        private String rightPlayMethonName;
        private String leftPlayMethonName;
        private String floorBrandName;
        private String floorModelName;
        private String rightBrandName;
        private String rightModelName;
        private String rightLandName;
        private String rightHardName;
        private String rightColorName;
        private String leftBrandName;
        private String leftModelName;
        private String leftLandName;
        private String leftHardName;
        private String leftColorName;
        private String dressSize;
        private String shoesSize;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String isDelete;
        private String address;
        private String autographImg;

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

        public long getFloorBrandId() {
            return floorBrandId;
        }

        public void setFloorBrandId(long floorBrandId) {
            this.floorBrandId = floorBrandId;
        }

        public long getFloorModelId() {
            return floorModelId;
        }

        public void setFloorModelId(long floorModelId) {
            this.floorModelId = floorModelId;
        }

        public long getRightBrandId() {
            return rightBrandId;
        }

        public void setRightBrandId(long rightBrandId) {
            this.rightBrandId = rightBrandId;
        }

        public long getRightModelId() {
            return rightModelId;
        }

        public void setRightModelId(long rightModelId) {
            this.rightModelId = rightModelId;
        }

        public long getRightLandId() {
            return rightLandId;
        }

        public void setRightLandId(long rightLandId) {
            this.rightLandId = rightLandId;
        }

        public long getRightHardId() {
            return rightHardId;
        }

        public void setRightHardId(long rightHardId) {
            this.rightHardId = rightHardId;
        }

        public int getRightColorId() {
            return rightColorId;
        }

        public void setRightColorId(int rightColorId) {
            this.rightColorId = rightColorId;
        }

        public long getLeftBrandId() {
            return leftBrandId;
        }

        public void setLeftBrandId(long leftBrandId) {
            this.leftBrandId = leftBrandId;
        }

        public long getLeftModelId() {
            return leftModelId;
        }

        public void setLeftModelId(long leftModelId) {
            this.leftModelId = leftModelId;
        }

        public long getLeftLandId() {
            return leftLandId;
        }

        public void setLeftLandId(long leftLandId) {
            this.leftLandId = leftLandId;
        }

        public long getLeftHardId() {
            return leftHardId;
        }

        public void setLeftHardId(long leftHardId) {
            this.leftHardId = leftHardId;
        }

        public int getLeftColorId() {
            return leftColorId;
        }

        public void setLeftColorId(int leftColorId) {
            this.leftColorId = leftColorId;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
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

        public String getClapHandName() {
            return clapHandName == null ? "" : clapHandName;
        }

        public void setClapHandName(String clapHandName) {
            this.clapHandName = clapHandName;
        }

        public String getGripMethonName() {
            return gripMethonName == null ? "" : gripMethonName;
        }

        public void setGripMethonName(String gripMethonName) {
            this.gripMethonName = gripMethonName;
        }

        public String getRightPlayMethonName() {
            return rightPlayMethonName == null ? "" : rightPlayMethonName;
        }

        public void setRightPlayMethonName(String rightPlayMethonName) {
            this.rightPlayMethonName = rightPlayMethonName;
        }

        public String getLeftPlayMethonName() {
            return leftPlayMethonName == null ? "" : leftPlayMethonName;
        }

        public void setLeftPlayMethonName(String leftPlayMethonName) {
            this.leftPlayMethonName = leftPlayMethonName;
        }

        public String getFloorBrandName() {
            return floorBrandName == null ? "" : floorBrandName;
        }

        public void setFloorBrandName(String floorBrandName) {
            this.floorBrandName = floorBrandName;
        }

        public String getFloorModelName() {
            return floorModelName == null ? "" : floorModelName;
        }

        public void setFloorModelName(String floorModelName) {
            this.floorModelName = floorModelName;
        }

        public String getRightBrandName() {
            return rightBrandName == null ? "" : rightBrandName;
        }

        public void setRightBrandName(String rightBrandName) {
            this.rightBrandName = rightBrandName;
        }

        public String getRightModelName() {
            return rightModelName == null ? "" : rightModelName;
        }

        public void setRightModelName(String rightModelName) {
            this.rightModelName = rightModelName;
        }

        public String getRightLandName() {
            return rightLandName == null ? "" : rightLandName;
        }

        public void setRightLandName(String rightLandName) {
            this.rightLandName = rightLandName;
        }

        public String getRightHardName() {
            return rightHardName == null ? "" : rightHardName;
        }

        public void setRightHardName(String rightHardName) {
            this.rightHardName = rightHardName;
        }

        public String getRightColorName() {
            return rightColorName == null ? "" : rightColorName;
        }

        public void setRightColorName(String rightColorName) {
            this.rightColorName = rightColorName;
        }

        public String getLeftBrandName() {
            return leftBrandName == null ? "" : leftBrandName;
        }

        public void setLeftBrandName(String leftBrandName) {
            this.leftBrandName = leftBrandName;
        }

        public String getLeftModelName() {
            return leftModelName == null ? "" : leftModelName;
        }

        public void setLeftModelName(String leftModelName) {
            this.leftModelName = leftModelName;
        }

        public String getLeftLandName() {
            return leftLandName == null ? "" : leftLandName;
        }

        public void setLeftLandName(String leftLandName) {
            this.leftLandName = leftLandName;
        }

        public String getLeftHardName() {
            return leftHardName == null ? "" : leftHardName;
        }

        public void setLeftHardName(String leftHardName) {
            this.leftHardName = leftHardName;
        }

        public String getLeftColorName() {
            return leftColorName == null ? "" : leftColorName;
        }

        public void setLeftColorName(String leftColorName) {
            this.leftColorName = leftColorName;
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

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAutographImg() {
            return autographImg == null ? "" : autographImg;
        }

        public void setAutographImg(String autographImg) {
            this.autographImg = autographImg;
        }
    }

    public static class PlayersBean {
        /**
         * clapHand : null
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
         * id : 511922527137206288
         * name : 律香川
         * account : null
         * accountStatus : null
         * identificationStatus : 1
         * headImg : null
         * nickName : null
         * bornDate : 1995-04-05
         * sex : 1
         * identificationNum : null
         * ownSign : null
         * clapHandName : null
         * gripMethonName : null
         * rightPlayMethonName : null
         * leftPlayMethonName : null
         * floorBrandName : null
         * floorModelName : null
         * rightBrandName : null
         * rightModelName : null
         * rightLandName : null
         * rightHardName : null
         * rightColorName : null
         * leftBrandName : null
         * leftModelName : null
         * leftLandName : null
         * leftHardName : null
         * leftColorName : null
         * dressSize : null
         * shoesSize : null
         * createUser : 511922527137206288
         * createTime : 2020-11-13 00:00:00
         * updateUser : 511922527137206288
         * updateTime : 2020-11-13 00:00:00
         * isDelete : 0
         * address : null
         * autographImg : null
         */

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
        private String id;
        private String name;
        private String account;
        private String accountStatus;
        private String identificationStatus;
        private String headImg;
        private String nickName;
        private String bornDate;
        private String sex;
        private String identificationNum;
        private String ownSign;
        private String clapHandName;
        private String gripMethonName;
        private String rightPlayMethonName;
        private String leftPlayMethonName;
        private String floorBrandName;
        private String floorModelName;
        private String rightBrandName;
        private String rightModelName;
        private String rightLandName;
        private String rightHardName;
        private String rightColorName;
        private String leftBrandName;
        private String leftModelName;
        private String leftLandName;
        private String leftHardName;
        private String leftColorName;
        private String dressSize;
        private String shoesSize;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String isDelete;
        private String address;
        private String autographImg;

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

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
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

        public String getClapHandName() {
            return clapHandName == null ? "" : clapHandName;
        }

        public void setClapHandName(String clapHandName) {
            this.clapHandName = clapHandName;
        }

        public String getGripMethonName() {
            return gripMethonName == null ? "" : gripMethonName;
        }

        public void setGripMethonName(String gripMethonName) {
            this.gripMethonName = gripMethonName;
        }

        public String getRightPlayMethonName() {
            return rightPlayMethonName == null ? "" : rightPlayMethonName;
        }

        public void setRightPlayMethonName(String rightPlayMethonName) {
            this.rightPlayMethonName = rightPlayMethonName;
        }

        public String getLeftPlayMethonName() {
            return leftPlayMethonName == null ? "" : leftPlayMethonName;
        }

        public void setLeftPlayMethonName(String leftPlayMethonName) {
            this.leftPlayMethonName = leftPlayMethonName;
        }

        public String getFloorBrandName() {
            return floorBrandName == null ? "" : floorBrandName;
        }

        public void setFloorBrandName(String floorBrandName) {
            this.floorBrandName = floorBrandName;
        }

        public String getFloorModelName() {
            return floorModelName == null ? "" : floorModelName;
        }

        public void setFloorModelName(String floorModelName) {
            this.floorModelName = floorModelName;
        }

        public String getRightBrandName() {
            return rightBrandName == null ? "" : rightBrandName;
        }

        public void setRightBrandName(String rightBrandName) {
            this.rightBrandName = rightBrandName;
        }

        public String getRightModelName() {
            return rightModelName == null ? "" : rightModelName;
        }

        public void setRightModelName(String rightModelName) {
            this.rightModelName = rightModelName;
        }

        public String getRightLandName() {
            return rightLandName == null ? "" : rightLandName;
        }

        public void setRightLandName(String rightLandName) {
            this.rightLandName = rightLandName;
        }

        public String getRightHardName() {
            return rightHardName == null ? "" : rightHardName;
        }

        public void setRightHardName(String rightHardName) {
            this.rightHardName = rightHardName;
        }

        public String getRightColorName() {
            return rightColorName == null ? "" : rightColorName;
        }

        public void setRightColorName(String rightColorName) {
            this.rightColorName = rightColorName;
        }

        public String getLeftBrandName() {
            return leftBrandName == null ? "" : leftBrandName;
        }

        public void setLeftBrandName(String leftBrandName) {
            this.leftBrandName = leftBrandName;
        }

        public String getLeftModelName() {
            return leftModelName == null ? "" : leftModelName;
        }

        public void setLeftModelName(String leftModelName) {
            this.leftModelName = leftModelName;
        }

        public String getLeftLandName() {
            return leftLandName == null ? "" : leftLandName;
        }

        public void setLeftLandName(String leftLandName) {
            this.leftLandName = leftLandName;
        }

        public String getLeftHardName() {
            return leftHardName == null ? "" : leftHardName;
        }

        public void setLeftHardName(String leftHardName) {
            this.leftHardName = leftHardName;
        }

        public String getLeftColorName() {
            return leftColorName == null ? "" : leftColorName;
        }

        public void setLeftColorName(String leftColorName) {
            this.leftColorName = leftColorName;
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

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAutographImg() {
            return autographImg == null ? "" : autographImg;
        }

        public void setAutographImg(String autographImg) {
            this.autographImg = autographImg;
        }
    }

}
