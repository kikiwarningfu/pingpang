package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.databinding.ItemAllGameBinding;

/**
 * 所有场次比赛
 */
public class AllGameAdapter extends BaseQuickAdapter<XlClubInfoListBean.ListBean, BaseViewHolder> {

    public AllGameAdapter() {
        super(R.layout.item_all_game);
    }

    @Override
    protected void convert(BaseViewHolder helper, XlClubInfoListBean.ListBean item) {
        ItemAllGameBinding binding= DataBindingUtil.bind(helper.getConvertView());




    }
}
