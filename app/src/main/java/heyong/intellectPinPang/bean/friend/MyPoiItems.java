package heyong.intellectPinPang.bean.friend;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

import java.io.Serializable;

public class MyPoiItems implements Serializable {
    private String proviceName;
    private String cityName;
    private String areName;
    private String detailAddress;
    private String title;
    private boolean isSelect;
    private String cityCode;

    public String getCityCode() {
        return cityCode == null ? "0" : cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreName() {
        return areName;
    }

    public void setAreName(String areName) {
        this.areName = areName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
