package heyong.intellectPinPang.bean.homepage;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RefereeCompetitionInterfaceBean implements MultiItemEntity {
    private int cardType;
    public static final int REFEREE_COMPETITION_ONE = 0;

    public static final int REFEREE_COMPETITION_TWO = 1;

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

    public RefereeCompetitionInterfaceBean(int cardType) {
        this.cardType = cardType;
    }
}
