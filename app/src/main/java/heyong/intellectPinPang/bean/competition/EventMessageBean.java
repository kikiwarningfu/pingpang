package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class EventMessageBean implements MultiItemEntity {
    private int cardType;
    public static final int MESSAGE_NO_BUTTON = 0;

    public static final int MESSAGE_HAVE_BUTTON = 2;
    public static final int MESSAGE_SEE_RESULT = 1;
    public static final int MESSAGE_APPLY_CLUB = 3;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int getItemType() {
        return cardType;
    }

    public EventMessageBean(int cardType) {
        this.cardType = cardType;
    }
}
