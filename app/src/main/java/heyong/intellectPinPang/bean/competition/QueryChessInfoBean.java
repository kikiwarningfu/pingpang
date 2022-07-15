package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class QueryChessInfoBean {


    /**
     * id : 515887942708269072
     * left1Name : 柯南
     * right1Name : 孟星魂
     * left1HeadImg : null
     * right1HeadImg : null
     * left2Name : null
     * right2Name : null
     * left2HeadImg : null
     * right2HeadImg : null
     * leftTeamNum : null
     * rightTeamNum : null
     * leftWinCount : 0
     * rightWinCount : 0
     * status : null
     * matchMethod : 三局二胜
     * leftWavier : 0
     * rightWavier : 0
     * rightSuspend : 0
     * leftSuspend : 0
     * left1Id : 512936350908452880
     * left2Id : 0
     * right1Id : 511215427033534480
     * right2Id : 0
     * teamLeftId : 515887942624382992
     * teamRightId : 515887942624382992
     * xlClubContestArrangeChesses : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","clickType":null,"status":"0","id":515953568025972752,"contestArrangeId":515887942708269072,"leftIntegral":null,"rightIntegral":null,"createUser":511486160083128336,"createTime":"2020-11-24 18:15:16","updateUser":511486160083128336,"updateTime":"2020-11-24 18:15:16","isDelete":"0"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","clickType":null,"status":"0","id":515953568055332880,"contestArrangeId":515887942708269072,"leftIntegral":null,"rightIntegral":null,"createUser":511486160083128336,"createTime":"2020-11-24 18:15:16","updateUser":511486160083128336,"updateTime":"2020-11-24 18:15:16","isDelete":"0"},{"pageNo":1,"pageSize":10,"orderBy":"id desc","clickType":null,"status":"0","id":515953568067915792,"contestArrangeId":515887942708269072,"leftIntegral":null,"rightIntegral":null,"createUser":511486160083128336,"createTime":"2020-11-24 18:15:16","updateUser":511486160083128336,"updateTime":"2020-11-24 18:15:16","isDelete":"0"}]
     */

    private long id;
    private String left1Name;
    private String right1Name;
    private String left1HeadImg;
    private String right1HeadImg;
    private String left2Name;
    private String right2Name;
    private String left2HeadImg;
    private String right2HeadImg;
    private String leftTeamNum;
    private String rightTeamNum;
    private int leftWinCount;
    private int rightWinCount;
    private String status;
    private String matchMethod;
    private int leftWavier;
    private int rightWavier;
    private int rightSuspend;
    private int leftSuspend;
    private long left1Id;
    private int left2Id;
    private long right1Id;
    private int right2Id;
    private long teamLeftId;
    private long teamRightId;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private List<XlClubContestArrangeChessesBean> xlClubContestArrangeChesses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLeft1HeadImg() {
        return left1HeadImg == null ? "" : left1HeadImg;
    }

    public void setLeft1HeadImg(String left1HeadImg) {
        this.left1HeadImg = left1HeadImg;
    }

    public String getRight1HeadImg() {
        return right1HeadImg == null ? "" : right1HeadImg;
    }

    public void setRight1HeadImg(String right1HeadImg) {
        this.right1HeadImg = right1HeadImg;
    }

    public String getLeft2Name() {
        return left2Name == null ? "" : left2Name;
    }

    public void setLeft2Name(String left2Name) {
        this.left2Name = left2Name;
    }

    public String getRight2Name() {
        return right2Name == null ? "" : right2Name;
    }

    public void setRight2Name(String right2Name) {
        this.right2Name = right2Name;
    }

    public String getLeft2HeadImg() {
        return left2HeadImg == null ? "" : left2HeadImg;
    }

    public void setLeft2HeadImg(String left2HeadImg) {
        this.left2HeadImg = left2HeadImg;
    }

    public String getRight2HeadImg() {
        return right2HeadImg == null ? "" : right2HeadImg;
    }

    public void setRight2HeadImg(String right2HeadImg) {
        this.right2HeadImg = right2HeadImg;
    }

    public String getLeftTeamNum() {
        return leftTeamNum == null ? "" : leftTeamNum;
    }

    public void setLeftTeamNum(String leftTeamNum) {
        this.leftTeamNum = leftTeamNum;
    }

    public String getRightTeamNum() {
        return rightTeamNum == null ? "" : rightTeamNum;
    }

    public void setRightTeamNum(String rightTeamNum) {
        this.rightTeamNum = rightTeamNum;
    }

    public int getLeftWinCount() {
        return leftWinCount;
    }

    public void setLeftWinCount(int leftWinCount) {
        this.leftWinCount = leftWinCount;
    }

    public int getRightWinCount() {
        return rightWinCount;
    }

    public void setRightWinCount(int rightWinCount) {
        this.rightWinCount = rightWinCount;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchMethod() {
        return matchMethod == null ? "" : matchMethod;
    }

    public void setMatchMethod(String matchMethod) {
        this.matchMethod = matchMethod;
    }

    public int getLeftWavier() {
        return leftWavier;
    }

    public void setLeftWavier(int leftWavier) {
        this.leftWavier = leftWavier;
    }

    public int getRightWavier() {
        return rightWavier;
    }

    public void setRightWavier(int rightWavier) {
        this.rightWavier = rightWavier;
    }

    public int getRightSuspend() {
        return rightSuspend;
    }

    public void setRightSuspend(int rightSuspend) {
        this.rightSuspend = rightSuspend;
    }

    public int getLeftSuspend() {
        return leftSuspend;
    }

    public void setLeftSuspend(int leftSuspend) {
        this.leftSuspend = leftSuspend;
    }

    public long getLeft1Id() {
        return left1Id;
    }

    public void setLeft1Id(long left1Id) {
        this.left1Id = left1Id;
    }

    public int getLeft2Id() {
        return left2Id;
    }

    public void setLeft2Id(int left2Id) {
        this.left2Id = left2Id;
    }

    public long getRight1Id() {
        return right1Id;
    }

    public void setRight1Id(long right1Id) {
        this.right1Id = right1Id;
    }

    public int getRight2Id() {
        return right2Id;
    }

    public void setRight2Id(int right2Id) {
        this.right2Id = right2Id;
    }

    public long getTeamLeftId() {
        return teamLeftId;
    }

    public void setTeamLeftId(long teamLeftId) {
        this.teamLeftId = teamLeftId;
    }

    public long getTeamRightId() {
        return teamRightId;
    }

    public void setTeamRightId(long teamRightId) {
        this.teamRightId = teamRightId;
    }

    public List<XlClubContestArrangeChessesBean> getXlClubContestArrangeChesses() {
        if (xlClubContestArrangeChesses == null) {
            return new ArrayList<>();
        }
        return xlClubContestArrangeChesses;
    }

    public void setXlClubContestArrangeChesses(List<XlClubContestArrangeChessesBean> xlClubContestArrangeChesses) {
        this.xlClubContestArrangeChesses = xlClubContestArrangeChesses;
    }

    public static class XlClubContestArrangeChessesBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * orderBy : id desc
         * clickType : null
         * status : 0
         * id : 515953568025972752
         * contestArrangeId : 515887942708269072
         * leftIntegral : null
         * rightIntegral : null
         * createUser : 511486160083128336
         * createTime : 2020-11-24 18:15:16
         * updateUser : 511486160083128336
         * updateTime : 2020-11-24 18:15:16
         * isDelete : 0
         */

        private int pageNo;
        private int pageSize;
        private String orderBy;
        private String clickType;
        private String status;
        private long id;
        private long contestArrangeId;
        private String leftIntegral;
        private String rightIntegral;
        private long createUser;
        private String createTime;
        private long updateUser;
        private String updateTime;
        private String isDelete;
        private boolean canChange;
        private boolean isSubmit = false;
        private boolean isFirst;
        private boolean isTextSubmit;

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        public boolean isTextSubmit() {
            return isTextSubmit;
        }

        public void setTextSubmit(boolean textSubmit) {
            isTextSubmit = textSubmit;
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

        public String getClickType() {
            return clickType == null ? "" : clickType;
        }

        public void setClickType(String clickType) {
            this.clickType = clickType;
        }

        public String getStatus() {
            return status == null ? "-1" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getContestArrangeId() {
            return contestArrangeId;
        }

        public void setContestArrangeId(long contestArrangeId) {
            this.contestArrangeId = contestArrangeId;
        }

        public String getLeftIntegral() {
            return leftIntegral == null ? "" : leftIntegral;
        }

        public void setLeftIntegral(String leftIntegral) {
            this.leftIntegral = leftIntegral;
        }

        public String getRightIntegral() {
            return rightIntegral == null ? "" : rightIntegral;
        }

        public void setRightIntegral(String rightIntegral) {
            this.rightIntegral = rightIntegral;
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

        public boolean isCanChange() {
            return canChange;
        }

        public void setCanChange(boolean canChange) {
            this.canChange = canChange;
        }
    }

}
