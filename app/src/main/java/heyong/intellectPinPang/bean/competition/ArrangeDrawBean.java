package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;

public class ArrangeDrawBean implements Serializable {
    private String title;
    private String itemType;
    private String arrangeId;
    private String leftId;
    private String left1Name;
    private String left1Img;
    private String left2Name;
    private String left2Img;
    private String rightId;
    private String right1Name;
    private String right1Img;
    private String right2Name;
    private String right2Img;
    private String matchId;

    public ArrangeDrawBean(String title, String itemType, String arrangeId, String leftId, String left1Name,
                           String left1Img, String left2Name, String left2Img, String rightId, String right1Name, String right1Img, String right2Name, String right2Img, String matchId) {
        this.title = title;
        this.itemType = itemType;
        this.arrangeId = arrangeId;
        this.leftId = leftId;
        this.left1Name = left1Name;
        this.left1Img = left1Img;
        this.left2Name = left2Name;
        this.left2Img = left2Img;
        this.rightId = rightId;
        this.right1Name = right1Name;
        this.right1Img = right1Img;
        this.right2Name = right2Name;
        this.right2Img = right2Img;
        this.matchId = matchId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemType() {
        return itemType == null ? "1" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getArrangeId() {
        return arrangeId == null ? "" : arrangeId;
    }

    public void setArrangeId(String arrangeId) {
        this.arrangeId = arrangeId;
    }

    public String getLeftId() {
        return leftId == null ? "" : leftId;
    }

    public void setLeftId(String leftId) {
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

    public String getRightId() {
        return rightId == null ? "" : rightId;
    }

    public void setRightId(String rightId) {
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

    public String getMatchId() {
        return matchId == null ? "" : matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
