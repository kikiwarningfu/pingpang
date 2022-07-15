package heyong.intellectPinPang.bean.gsonbean;

public class FriendPostBean {
    private String id;
    private String momnentId;
    private String userId;
    private String content;
    private String commentId;
    private String page;
    private String pageNumber;
    private String currentUserId;
    private String dynaId;
    private String replyId;
    private String comId;
    private String replyPid;
    private String picture;

    public String getPicture() {
        return picture == null ? "" : picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getReplyPid() {
        return replyPid == null ? "" : replyPid;
    }

    public void setReplyPid(String replyPid) {
        this.replyPid = replyPid;
    }

    public String getComId() {
        return comId == null ? "" : comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getReplyId() {
        return replyId == null ? "" : replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getDynaId() {
        return dynaId == null ? "" : dynaId;
    }

    public void setDynaId(String dynaId) {
        this.dynaId = dynaId;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMomnentId() {
        return momnentId == null ? "" : momnentId;
    }

    public void setMomnentId(String momnentId) {
        this.momnentId = momnentId;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentId() {
        return commentId == null ? "" : commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPage() {
        return page == null ? "" : page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageNumber() {
        return pageNumber == null ? "" : pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCurrentUserId() {
        return currentUserId == null ? "" : currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
