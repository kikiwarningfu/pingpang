package heyong.intellectPinPang.event;


public class BeginArrangeDisplayChouEvent {
    private String beginData;
    private int clickPosition;

    public BeginArrangeDisplayChouEvent(String beginData, int clickPosition) {
        this.beginData = beginData;
        this.clickPosition = clickPosition;
    }

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    public BeginArrangeDisplayChouEvent(String beginData) {
        this.beginData = beginData;
    }

    public String getBeginData() {
        return beginData == null ? "" : beginData;
    }

    public void setBeginData(String beginData) {
        this.beginData = beginData;
    }
}
