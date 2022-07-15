package heyong.intellectPinPang.event;

import heyong.intellectPinPang.bean.friend.MyPoiItems;

public class FriendCircleEvent {
    private MyPoiItems myPoiItems;

    public FriendCircleEvent(MyPoiItems myPoiItems) {
        this.myPoiItems = myPoiItems;
    }

    public MyPoiItems getMyPoiItems() {
        return myPoiItems;
    }

    public void setMyPoiItems(MyPoiItems myPoiItems) {
        this.myPoiItems = myPoiItems;
    }
}
