package heyong.intellectPinPang.event;

public class LiveOrderStatusEvent {
    private String orderStatus="";

    public LiveOrderStatusEvent(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus == null ? "" : orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
