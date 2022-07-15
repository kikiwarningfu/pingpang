package heyong.intellectPinPang.event;

public class ShowPopKeyEvent {
    private String contenet;
    private String replyId;

    public String getReplyId() {
        return replyId == null ? "" : replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public ShowPopKeyEvent() {

    }

    public ShowPopKeyEvent(String contenet, String replyId) {
        this.contenet = contenet;
        this.replyId = replyId;
    }

    public ShowPopKeyEvent(String contenet) {
        this.contenet = contenet;
    }

    public String getContenet() {
        return contenet == null ? "" : contenet;
    }

    public void setContenet(String contenet) {
        this.contenet = contenet;
    }
}
