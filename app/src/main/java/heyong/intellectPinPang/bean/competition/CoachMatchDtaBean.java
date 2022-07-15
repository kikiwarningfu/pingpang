package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CoachMatchDtaBean implements MultiItemEntity {

    /**
     * id : 1
     * left1Name : null
     * left1Img : null
     * left2Name : null
     * left2Img : null
     * right1Name : null
     * right1Img : null
     * right2Name : null
     * right2Img : null
     * leftClubName : 1231
     * leftClubImg : http://images.xlttsports.com/android_img_20201114092349902
     * rightClubName : 自由团体
     * rightClubImg : http://images.xlttsports.com/android_img_20201114092508841
     * arrangeNum : null
     * stage : null
     * itemName : null
     * beginTime : null
     * status : null
     * tableNum : 0
     * projectType : 1
     */

    private long id;
    private String left1Name;
    private String left1Img;
    private String left2Name;
    private String left2Img;
    private String right1Name;
    private String right1Img;
    private String right2Name;
    private String right2Img;
    private String leftClubName;
    private String leftClubImg;
    private String rightClubName;
    private String rightClubImg;
    private String arrangeNum;
    private String stage;
    private String itemName;
    private String beginTime;
    private String status;
    private int tableNum;
    private String projectType;
    private int cardType;
    private int leftWinCount=0;
    private int rightWinCount=0;

    public static final int NEWS_DETAIL_EIGHT = 0;

    public static final int NEWS_DETAIL_FOUR = 1;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int getItemType() {
        return cardType;
    }

    public CoachMatchDtaBean(int cardType) {
        this.cardType = cardType;
    }

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

    public String getLeft1Img() {
        return left1Img == null ? "" : left1Img;
    }

    public void setLeft1Img(String left1Img) {
        this.left1Img = left1Img;
    }

    public String getLeft2Name() {
        return left2Name == null ? "" : left2Name;
    }

    public void setLeft2Name(String left2Name) {
        this.left2Name = left2Name;
    }

    public String getLeft2Img() {
        return left2Img == null ? "" : left2Img;
    }

    public void setLeft2Img(String left2Img) {
        this.left2Img = left2Img;
    }

    public String getRight1Name() {
        return right1Name == null ? "" : right1Name;
    }

    public void setRight1Name(String right1Name) {
        this.right1Name = right1Name;
    }

    public String getRight1Img() {
        return right1Img == null ? "" : right1Img;
    }

    public void setRight1Img(String right1Img) {
        this.right1Img = right1Img;
    }

    public String getRight2Name() {
        return right2Name == null ? "" : right2Name;
    }

    public void setRight2Name(String right2Name) {
        this.right2Name = right2Name;
    }

    public String getRight2Img() {
        return right2Img == null ? "" : right2Img;
    }

    public void setRight2Img(String right2Img) {
        this.right2Img = right2Img;
    }

    public String getLeftClubName() {
        return leftClubName == null ? "" : leftClubName;
    }

    public void setLeftClubName(String leftClubName) {
        this.leftClubName = leftClubName;
    }

    public String getLeftClubImg() {
        return leftClubImg == null ? "" : leftClubImg;
    }

    public void setLeftClubImg(String leftClubImg) {
        this.leftClubImg = leftClubImg;
    }

    public String getRightClubName() {
        return rightClubName == null ? "" : rightClubName;
    }

    public void setRightClubName(String rightClubName) {
        this.rightClubName = rightClubName;
    }

    public String getRightClubImg() {
        return rightClubImg == null ? "" : rightClubImg;
    }

    public void setRightClubImg(String rightClubImg) {
        this.rightClubImg = rightClubImg;
    }

    public String getArrangeNum() {
        return arrangeNum == null ? "" : arrangeNum;
    }

    public void setArrangeNum(String arrangeNum) {
        this.arrangeNum = arrangeNum;
    }

    public String getStage() {
        return stage == null ? "" : stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getItemName() {
        return itemName == null ? "" : itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBeginTime() {
        return beginTime == null ? "" : beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getProjectType() {
        return projectType == null ? "1" : projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public static int getNewsDetailEight() {
        return NEWS_DETAIL_EIGHT;
    }

    public static int getNewsDetailFour() {
        return NEWS_DETAIL_FOUR;
    }
}
