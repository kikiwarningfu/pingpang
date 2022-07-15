package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.homepage.NewsDetailBean;
import heyong.intellectPinPang.databinding.ItemNewsDetailEightBinding;
import heyong.intellectPinPang.databinding.ItemNewsDetailFourBinding;
import heyong.intellectPinPang.databinding.ItemNewsDetailTwoBinding;

/**
 * 和新闻详情的适配器公用一个
 */
public class MatchScoreAdapter extends BaseQuickAdapter<NewsDetailBean, BaseViewHolder> {
    public MatchScoreAdapter() {
//        super(list);
//        addItemType(CardBeanBoss.CARD_OK, R.layout.item_my_card_boss);
//        addItemType(CardBeanBoss.CARD_NO, R.layout.item_my_card_boss_addcard);
        super(0);
        MultiTypeDelegate<NewsDetailBean> multiTypeDelegate = new MultiTypeDelegate<NewsDetailBean>() {
            @Override
            protected int getItemType(NewsDetailBean o) {
//                Log.e(TAG, "getItemType: type==="+o.getType());
                return o.getCardType();
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.item_news_detail_eight);//显示8个
        multiTypeDelegate.registerItemType(1, R.layout.item_news_detail_four);//显示4个
        multiTypeDelegate.registerItemType(2, R.layout.item_news_detail_two);//显示2个
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsDetailBean item) {
        switch (helper.getItemViewType()) {
            case NewsDetailBean.NEWS_DETAIL_FOUR://1
                ItemNewsDetailFourBinding  binding= DataBindingUtil.bind(helper.getConvertView());

                break;
            case NewsDetailBean.NEWS_DETAIL_EIGHT://0
                ItemNewsDetailEightBinding binding1=DataBindingUtil.bind(helper.getConvertView());

                break;
            case NewsDetailBean.NEWS_DETAIL_TWO://2
                ItemNewsDetailTwoBinding binding2=DataBindingUtil.bind(helper.getConvertView());


                break;
        }
    }
}
