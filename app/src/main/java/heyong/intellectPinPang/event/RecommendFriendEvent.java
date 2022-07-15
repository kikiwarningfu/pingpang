package heyong.intellectPinPang.event;

public class RecommendFriendEvent {
    int loadType;

    public RecommendFriendEvent(int loadType) {
        this.loadType = loadType;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }
}
