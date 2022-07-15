package heyong.intellectPinPang.event;

public class AttentionFriendEvent {
    int loadType;

    public AttentionFriendEvent(int loadType) {
        this.loadType = loadType;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }
}
