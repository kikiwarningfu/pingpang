package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class LiveMatchPublishListBean {

    /**
     * matchName : string //赛事名称
     * liveMatchs : [{"requestId":"int //直播唯一ID","title":"string //标题"}]
     */

    private String matchName;
    private List<LiveMatchsBean> liveMatchs;

    public String getMatchName() {
        return matchName == null ? "" : matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public List<LiveMatchsBean> getLiveMatchs() {
        if (liveMatchs == null) {
            return new ArrayList<>();
        }
        return liveMatchs;
    }

    public void setLiveMatchs(List<LiveMatchsBean> liveMatchs) {
        this.liveMatchs = liveMatchs;
    }

    public static class LiveMatchsBean {
        /**
         * requestId : int //直播唯一ID
         * title : string //标题
         */

        private int requestId;
        private String title;

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
