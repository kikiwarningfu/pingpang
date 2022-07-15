package heyong.intellectPinPang.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BeginArrangeDisplayEvent {
    private String beginData;
    private int clickPosition;

    public BeginArrangeDisplayEvent(String beginData, int clickPosition) {
        this.beginData = beginData;
        this.clickPosition = clickPosition;
    }

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    public BeginArrangeDisplayEvent(String beginData) {
        this.beginData = beginData;
    }

    public String getBeginData() {
        return beginData == null ? "" : beginData;
    }

    public void setBeginData(String beginData) {
        this.beginData = beginData;
    }
}
