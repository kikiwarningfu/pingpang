package heyong.intellectPinPang.bean.homepage;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class NewsDetailBean implements MultiItemEntity {
    private int cardType;
    public static final int NEWS_DETAIL_EIGHT = 0;

    public static final int NEWS_DETAIL_FOUR = 1;
    public static final int NEWS_DETAIL_TWO = 2;
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

    public NewsDetailBean(int cardType) {
        this.cardType = cardType;
    }
}
