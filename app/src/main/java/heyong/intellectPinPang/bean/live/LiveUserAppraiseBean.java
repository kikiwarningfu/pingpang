package heyong.intellectPinPang.bean.live;

public class LiveUserAppraiseBean {

    /**
     * requestId : int //唯一一ID
     * start : int //评星分数，在1-5这间
     * content : string //评价内容
     */

    private int requestId;
    private int start;
    private String content;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
