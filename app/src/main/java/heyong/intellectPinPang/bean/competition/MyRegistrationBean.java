package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyRegistrationBean implements MultiItemEntity {
    private int cardType;
    public static final int NEWS_DETAIL_EIGHT = 0;

    public static final int NEWS_DETAIL_FOUR = 1;

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

    public MyRegistrationBean(int cardType) {
        this.cardType = cardType;
    }
}
