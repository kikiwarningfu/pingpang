package heyong.intellectPinPang.bean.live;

import java.io.Serializable;

public class XlZhiboJiedanOrderBean implements Serializable {


        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * phoneNum : 13301366784
         * force : null
         * hasBegin : false
         * cancel : true
         * complete : false
         * code : null
         * xlZhiboSetOrder : {"pageNo":1,"pageSize":10,"orderBy":"id desc","acconut":"19520481334","name":"王宁","leftClubName":null,"rightClubName":null,"dayTime":null,"hourTime":null,"mainDirection":null,"orderNum":"2021092783361644","code":null,"orderStatusB":null,"yongjin":0,"matchAddress":null,"matchId":625993725198293632,"id":627156077901141168,"arrangeId":626441274055983135,"matchTitle":"0925","itemType":"2","itemTitle":"测试标题","player1":"西安乒跃","player2":null,"player3":"宏亮乒乓","player4":null,"matchTime":"2021-10-31 10:00:00","matchLocal":"安徽 巢湖 居巢 123","orderType":"2","freeType":"1","money":"50.00","isDelete":"0","freeStatus":"1","duJuWay":"1","orderTime":"2021-09-27 14:54","hasJiedan":"1","reason":null,"tuikuanshuoming":null,"pingjia":null,"pingjialevel":null,"orderStatus":"4","tableNum":4,"orderUser":511486160083128336,"createUser":511486160083128336,"createTime":"2021-09-27 14:54:27","updateUser":511486160083128336,"updateTime":"2021-09-27 15:21:11","choucheng":"10.00"}
         * id : 627162832836121776
         * setOrderId : 627156077901141168
         * userId : 536930097770355328
         * money : 40.00
         * tixianStatus : 1
         * status : 1
         * zhiboAddress : null
         * huifangAddress : null
         * createUser : 536930097770355328
         * createTime : 2021-09-27 15:21:11
         * updateUser : 536930097770355328
         * updateTime : 2021-09-27 15:21:11
         * isDelete : 0
         * startTime : null
         * duration : 0
         * completeTime : null
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private String phoneNum;
        private String force;
        private boolean hasBegin;
        private boolean cancel;
        private boolean complete;
        private String code;
        private XlZhiboSetOrderBean xlZhiboSetOrder;
        private long id;
        private long setOrderId;
        private long userId;
        private String money;
        private String tixianStatus;
        private String status;
        private String zhiboAddress;
        private String huifangAddress;
        private long createUser;
        private String createTime;
        private long updateUser;
        private String updateTime;
        private String isDelete;
        private String startTime;
        private int duration;
        private String completeTime;

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

    public String getPhoneNum() {
        return phoneNum == null ? "" : phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getForce() {
        return force == null ? "" : force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public boolean isHasBegin() {
        return hasBegin;
    }

    public void setHasBegin(boolean hasBegin) {
        this.hasBegin = hasBegin;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public XlZhiboSetOrderBean getXlZhiboSetOrder() {
        return xlZhiboSetOrder;
    }

    public void setXlZhiboSetOrder(XlZhiboSetOrderBean xlZhiboSetOrder) {
        this.xlZhiboSetOrder = xlZhiboSetOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSetOrderId() {
        return setOrderId;
    }

    public void setSetOrderId(long setOrderId) {
        this.setOrderId = setOrderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money == null ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTixianStatus() {
        return tixianStatus == null ? "" : tixianStatus;
    }

    public void setTixianStatus(String tixianStatus) {
        this.tixianStatus = tixianStatus;
    }

    public String getStatus() {
        return status == null ? "1" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZhiboAddress() {
        return zhiboAddress == null ? "" : zhiboAddress;
    }

    public void setZhiboAddress(String zhiboAddress) {
        this.zhiboAddress = zhiboAddress;
    }

    public String getHuifangAddress() {
        return huifangAddress == null ? "" : huifangAddress;
    }

    public void setHuifangAddress(String huifangAddress) {
        this.huifangAddress = huifangAddress;
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

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCompleteTime() {
        return completeTime == null ? "" : completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public static class XlZhiboSetOrderBean implements Serializable{
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * acconut : 19520481334
             * name : 王宁
             * leftClubName : null
             * rightClubName : null
             * dayTime : null
             * hourTime : null
             * mainDirection : null
             * orderNum : 2021092783361644
             * code : null
             * orderStatusB : null
             * yongjin : 0.0
             * matchAddress : null
             * matchId : 625993725198293632
             * id : 627156077901141168
             * arrangeId : 626441274055983135
             * matchTitle : 0925
             * itemType : 2
             * itemTitle : 测试标题
             * player1 : 西安乒跃
             * player2 : null
             * player3 : 宏亮乒乓
             * player4 : null
             * matchTime : 2021-10-31 10:00:00
             * matchLocal : 安徽 巢湖 居巢 123
             * orderType : 2
             * freeType : 1
             * money : 50.00
             * isDelete : 0
             * freeStatus : 1
             * duJuWay : 1
             * orderTime : 2021-09-27 14:54
             * hasJiedan : 1
             * reason : null
             * tuikuanshuoming : null
             * pingjia : null
             * pingjialevel : null
             * orderStatus : 4
             * tableNum : 4
             * orderUser : 511486160083128336
             * createUser : 511486160083128336
             * createTime : 2021-09-27 14:54:27
             * updateUser : 511486160083128336
             * updateTime : 2021-09-27 15:21:11
             * choucheng : 10.00
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private String acconut;
            private String name;
            private String leftClubName;
            private String rightClubName;
            private String dayTime;
            private String hourTime;
            private String mainDirection;
            private String orderNum;
            private String code;
            private String orderStatusB;
            private double yongjin;
            private String matchAddress;
            private long matchId;
            private long id;
            private long arrangeId;
            private String matchTitle;
            private String itemType;
            private String itemTitle;
            private String player1;
            private String player2;
            private String player3;
            private String player4;
            private String matchTime;
            private String matchLocal;
            private String orderType;
            private String freeType;
            private String money;
            private String isDelete;
            private String freeStatus;
            private String duJuWay;
            private String orderTime;
            private String hasJiedan;
            private String reason;
            private String tuikuanshuoming;
            private String pingjia;
            private String pingjialevel;
            private String orderStatus;
            private int tableNum;
            private long orderUser;
            private long createUser;
            private String createTime;
            private long updateUser;
            private String updateTime;
            private String choucheng;

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

        public String getAcconut() {
            return acconut == null ? "" : acconut;
        }

        public void setAcconut(String acconut) {
            this.acconut = acconut;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLeftClubName() {
            return leftClubName == null ? "" : leftClubName;
        }

        public void setLeftClubName(String leftClubName) {
            this.leftClubName = leftClubName;
        }

        public String getRightClubName() {
            return rightClubName == null ? "" : rightClubName;
        }

        public void setRightClubName(String rightClubName) {
            this.rightClubName = rightClubName;
        }

        public String getDayTime() {
            return dayTime == null ? "" : dayTime;
        }

        public void setDayTime(String dayTime) {
            this.dayTime = dayTime;
        }

        public String getHourTime() {
            return hourTime == null ? "" : hourTime;
        }

        public void setHourTime(String hourTime) {
            this.hourTime = hourTime;
        }

        public String getMainDirection() {
            return mainDirection == null ? "" : mainDirection;
        }

        public void setMainDirection(String mainDirection) {
            this.mainDirection = mainDirection;
        }

        public String getOrderNum() {
            return orderNum == null ? "" : orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrderStatusB() {
            return orderStatusB == null ? "" : orderStatusB;
        }

        public void setOrderStatusB(String orderStatusB) {
            this.orderStatusB = orderStatusB;
        }

        public double getYongjin() {
            return yongjin;
        }

        public void setYongjin(double yongjin) {
            this.yongjin = yongjin;
        }

        public String getMatchAddress() {
            return matchAddress == null ? "" : matchAddress;
        }

        public void setMatchAddress(String matchAddress) {
            this.matchAddress = matchAddress;
        }

        public long getMatchId() {
            return matchId;
        }

        public void setMatchId(long matchId) {
            this.matchId = matchId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getArrangeId() {
            return arrangeId;
        }

        public void setArrangeId(long arrangeId) {
            this.arrangeId = arrangeId;
        }

        public String getMatchTitle() {
            return matchTitle == null ? "" : matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
        }

        public String getItemType() {
            return itemType == null ? "" : itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemTitle() {
            return itemTitle == null ? "" : itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getPlayer1() {
            return player1 == null ? "" : player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer2() {
            return player2 == null ? "" : player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public String getPlayer3() {
            return player3 == null ? "" : player3;
        }

        public void setPlayer3(String player3) {
            this.player3 = player3;
        }

        public String getPlayer4() {
            return player4 == null ? "" : player4;
        }

        public void setPlayer4(String player4) {
            this.player4 = player4;
        }

        public String getMatchTime() {
            return matchTime == null ? "" : matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getMatchLocal() {
            return matchLocal == null ? "" : matchLocal;
        }

        public void setMatchLocal(String matchLocal) {
            this.matchLocal = matchLocal;
        }

        public String getOrderType() {
            return orderType == null ? "" : orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getFreeType() {
            return freeType == null ? "" : freeType;
        }

        public void setFreeType(String freeType) {
            this.freeType = freeType;
        }

        public String getMoney() {
            return money == null ? "" : money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIsDelete() {
            return isDelete == null ? "" : isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getFreeStatus() {
            return freeStatus == null ? "" : freeStatus;
        }

        public void setFreeStatus(String freeStatus) {
            this.freeStatus = freeStatus;
        }

        public String getDuJuWay() {
            return duJuWay == null ? "" : duJuWay;
        }

        public void setDuJuWay(String duJuWay) {
            this.duJuWay = duJuWay;
        }

        public String getOrderTime() {
            return orderTime == null ? "" : orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getHasJiedan() {
            return hasJiedan == null ? "" : hasJiedan;
        }

        public void setHasJiedan(String hasJiedan) {
            this.hasJiedan = hasJiedan;
        }

        public String getReason() {
            return reason == null ? "" : reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getTuikuanshuoming() {
            return tuikuanshuoming == null ? "" : tuikuanshuoming;
        }

        public void setTuikuanshuoming(String tuikuanshuoming) {
            this.tuikuanshuoming = tuikuanshuoming;
        }

        public String getPingjia() {
            return pingjia == null ? "" : pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getPingjialevel() {
            return pingjialevel == null ? "" : pingjialevel;
        }

        public void setPingjialevel(String pingjialevel) {
            this.pingjialevel = pingjialevel;
        }

        public String getOrderStatus() {
            return orderStatus == null ? "" : orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getTableNum() {
            return tableNum;
        }

        public void setTableNum(int tableNum) {
            this.tableNum = tableNum;
        }

        public long getOrderUser() {
            return orderUser;
        }

        public void setOrderUser(long orderUser) {
            this.orderUser = orderUser;
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

        public String getChoucheng() {
            return choucheng == null ? "" : choucheng;
        }

        public void setChoucheng(String choucheng) {
            this.choucheng = choucheng;
        }
    }

}
