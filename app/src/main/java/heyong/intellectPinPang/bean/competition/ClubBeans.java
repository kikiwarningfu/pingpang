package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ClubBeans implements MultiItemEntity {

    private int cardType;
    public static final int CLUBS_DETAIL_ONE = 0;

    public static final int CLUBS_DATAIL_TWO = 1;
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

    public ClubBeans(int cardType) {
        this.cardType = cardType;
    }
}
