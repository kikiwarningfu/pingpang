package heyong.intellectPinPang.bean.live;

import java.util.ArrayList;
import java.util.List;

public class LiveMatchDetailBean {


    /**
     * requestId : int //直播唯一ID
     * matchId : long //比赛唯一ID
     * tableNum : int //球台数
     * matchName : string //赛事名称
     * title : string //赛事名称（阶段、组）
     * gameTime : date //比赛开始时间
     * address : string //详细地址
     * money : float //悬赏支付金额
     * userMoney : float //接单用户预计可分成金额
     * left : [{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}]
     * right : [{"id":"long //球员ID","name":"string //球员名称","logo":"string //球员头像"}]
     * orderId : int //唯一订单号
     * payStatus : int //悬赏支付状态 -1=待支付，0=支付成功，1=已经接单，2=退款中  3=退款成功,4=正常结束，5=未支付超时关闭
     * isAppraise : int //是否已经评价  1=是 0=未
     * payType : int //支付方式 1=支付宝 2=微信 3=苹果内付
     * receiveStatus : int //接单状态 -1=还未接单 0=禁用，1=接单成功 2=放弃，3=直播中，4=结束
     * isWithDraw : int //是否可以提现 1=可以 0=不可以
     * publicMoney : float //公共悬赏发布时应该支付金额，单位元
     * selfMoney : float //发布自己接单悬赏时需要支付给平台的金额 单位元
     */

    private int requestId;
    private long matchId;
    private int tableNum;
    private String matchName;
    private String title;
    private String gameTime;
    private String address;
    private float money;
    private float userMoney;
    private int orderId;
    private int payStatus;
    private int isAppraise;
    private int payType;
    private int receiveStatus;
    private int isWithDraw;
    private float publicMoney;
    private float selfMoney;
    private List<LeftBean> left;
    private List<RightBean> right;
    private int matchMethod;
    private int liveType;
    private int closeSec;

    public int getLiveType() {
        return liveType;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public int getCloseSec() {
        return closeSec;
    }

    public void setCloseSec(int closeSec) {
        this.closeSec = closeSec;
    }

    public int getMatchMethod() {
        return matchMethod;
    }

    public void setMatchMethod(int matchMethod) {
        this.matchMethod = matchMethod;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getMatchName() {
        return matchName == null ? "" : matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGameTime() {
        return gameTime == null ? "" : gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(float userMoney) {
        this.userMoney = userMoney;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getIsAppraise() {
        return isAppraise;
    }

    public void setIsAppraise(int isAppraise) {
        this.isAppraise = isAppraise;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public int getIsWithDraw() {
        return isWithDraw;
    }

    public void setIsWithDraw(int isWithDraw) {
        this.isWithDraw = isWithDraw;
    }

    public float getPublicMoney() {
        return publicMoney;
    }

    public void setPublicMoney(float publicMoney) {
        this.publicMoney = publicMoney;
    }

    public float getSelfMoney() {
        return selfMoney;
    }

    public void setSelfMoney(float selfMoney) {
        this.selfMoney = selfMoney;
    }

    public List<LeftBean> getLeft() {
        if (left == null) {
            return new ArrayList<>();
        }
        return left;
    }

    public void setLeft(List<LeftBean> left) {
        this.left = left;
    }

    public List<RightBean> getRight() {
        if (right == null) {
            return new ArrayList<>();
        }
        return right;
    }

    public void setRight(List<RightBean> right) {
        this.right = right;
    }

    public static class LeftBean {
        /**
         * id : long //球员ID
         * name : string //球员名称
         * logo : string //球员头像
         */

        private long id;
        private String name;
        private String logo;

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

        public String getLogo() {
            return logo == null ? "" : logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class RightBean {
        /**
         * id : long //球员ID
         * name : string //球员名称
         * logo : string //球员头像
         */

        private long id;
        private String name;
        private String logo;

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

        public String getLogo() {
            return logo == null ? "" : logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

}
