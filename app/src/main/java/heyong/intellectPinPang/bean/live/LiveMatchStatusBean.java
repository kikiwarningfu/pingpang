package heyong.intellectPinPang.bean.live;

public class LiveMatchStatusBean {

    /**
     * status : int //状态  -1=暂不允许发布悬赏  0=可以发布悬赏 1=悬赏已发出，等待接单  2=已经有用户接单  3=直播中 4=直播已经结束
     */

    private int status;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
