package heyong.intellectPinPang.bean.competition;

public class XlMessageInfoPostBean {
    private String msgType;
    private String pageNo;
    private String pageSize;

    public String getMsgType() {
        return msgType == null ? "" : msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getPageNo() {
        return pageNo == null ? "" : pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize == null ? "" : pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
