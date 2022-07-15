package heyong.intellectPinPang.bean.competition;

import android.text.TextUtils;

import java.io.Serializable;

public class ArrangeDrawDataBean implements Serializable {


    /**
     * title : 男双打循环赛
     * itemType : 1
     * arrangeId : 1
     * matchId : 1
     * leftId : 526801420729864624
     * left1Name : 王洪福
     * left1Img : 1
     * left2Name : 张三丰
     * left2Img : 1
     * leftType : 2
     * rightId : 526801420729864624
     * right1Name : 王洪福
     * right1Img : 1
     * right2Name : 张三丰
     * right2Img : 1
     * rightType : 1
     * angle : 298
     * appLogId : null
     */

    private String title;
    private String itemType;
    private long arrangeId;
    private long matchId;
    private long leftId;
    private String left1Name;
    private String left1Img;
    private String left2Name;
    private String left2Img;
    private String leftType;
    private long rightId;
    private String right1Name;
    private String right1Img;
    private String right2Name;
    private String right2Img;
    private String rightType;
    private int angle;
    private String appLogId;
    private String leftClubId;
    private String rightClubId;

    public ArrangeDrawDataBean(String title, String itemType, String arrangeId, String leftId, String left1Name, String left1Img, String left2Name, String left2Img, String rightId, String right1Name, String right1Img, String right2Name, String right2Img, String matchId) {
        this.title = title;
        this.itemType = itemType;
        if (TextUtils.isEmpty(arrangeId)) {
            this.arrangeId = 0;
        } else {
            this.arrangeId = Long.parseLong(arrangeId);
        }
        if (TextUtils.isEmpty(leftId)) {
            this.leftId = 0;
        } else {
            this.leftId = Long.parseLong(leftId);
        }
        this.left1Name = left1Name;
        this.left1Img = left1Img;
        this.left2Name = left2Name;
        this.left2Img = left2Img;
        if (TextUtils.isEmpty(rightId)) {
            this.rightId = 0;
        } else {
            this.rightId = Long.parseLong(rightId);
        }
        this.right1Name = right1Name;
        this.right1Img = right1Img;
        this.right2Name = right2Name;
        this.right2Img = right2Img;
        if (TextUtils.isEmpty(matchId)) {
            this.matchId = 0;
        } else {
            this.matchId = Long.parseLong(matchId);
        }
    }

    public String getLeftClubId() {
        return leftClubId == null ? "" : leftClubId;
    }

    public void setLeftClubId(String leftClubId) {
        this.leftClubId = leftClubId;
    }

    public String getRightClubId() {
        return rightClubId == null ? "" : rightClubId;
    }

    public void setRightClubId(String rightClubId) {
        this.rightClubId = rightClubId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public long getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(long arrangeId) {
        this.arrangeId = arrangeId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getLeftId() {
        return leftId;
    }

    public void setLeftId(long leftId) {
        this.leftId = leftId;
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

    public String getLeftType() {
        return leftType == null ? "0" : leftType;
    }

    public void setLeftType(String leftType) {
        this.leftType = leftType;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
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

    public String getRightType() {
        return rightType == null ? "0" : rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getAppLogId() {
        return appLogId == null ? "" : appLogId;
    }

    public void setAppLogId(String appLogId) {
        this.appLogId = appLogId;
    }
}
