package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ClubNewsMessageBean implements MultiItemEntity {
    private int cardType;
    public static final int MESSAGE_NO_BUTTON = 0;

    public static final int MESSAGE_HAVE_BUTTON = 1;

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

    public ClubNewsMessageBean(int cardType) {
        this.cardType = cardType;
    }
}
