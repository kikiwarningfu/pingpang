package heyong.intellectPinPang.bean.live;

import android.app.Service;
import android.bluetooth.BluetoothProfile;

import java.io.Serializable;

public class LiveMatchUrlBean implements Serializable {

    /**
     * status : int //状态 0=未开始 1=直播 2=录播 3=禁播
     * num : int //播放次数
     * playUrls : string[] //直播或录播地址，直播只有一个值，录播可能会存在多个url
     * liveUrl : string //推流地址，离比赛开赛30分钟内才返回
     * isFree : int //是否免费 1=免费  0=收费
     */

    private int status;
    private int num;
    private String[] playUrls;
    private String liveUrl;
    private int isFree;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getPlayUrls() {
        return playUrls;
    }

    public void setPlayUrls(String[] playUrls) {
        this.playUrls = playUrls;
    }

    public String getLiveUrl() {
        return liveUrl == null ? "" : liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }
}
