package heyong.intellectPinPang.bean.live;

public class SuspendLiveBroadCastBean {
    private int mostPopular;//最高人气
    private String liveBroadcastDuration;//直播时长
    private String headImg;
    private String name;
    private String sex;
    private int times;//直播时长时间戳

    public int getMostPopular() {
        return mostPopular;
    }

    public void setMostPopular(int mostPopular) {
        this.mostPopular = mostPopular;
    }

    public String getLiveBroadcastDuration() {
        return liveBroadcastDuration == null ? "" : liveBroadcastDuration;
    }

    public void setLiveBroadcastDuration(String liveBroadcastDuration) {
        this.liveBroadcastDuration = liveBroadcastDuration;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
