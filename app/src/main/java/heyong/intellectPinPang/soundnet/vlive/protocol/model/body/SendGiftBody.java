package heyong.intellectPinPang.soundnet.vlive.protocol.model.body;

public class SendGiftBody {
    int giftId;
    int count;

    public SendGiftBody(int id, int count) {
        this.giftId = id;
        this.count = count;
    }
}
