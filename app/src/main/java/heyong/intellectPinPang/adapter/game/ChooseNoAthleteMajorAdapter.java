package heyong.intellectPinPang.adapter.game;

import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.databinding.ItemChooseNoAthletemajorBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 业余双打的适配器
 */
public class ChooseNoAthleteMajorAdapter extends BaseQuickAdapter<ChooseJoinMatchUserBean.PlayersBean, BaseViewHolder> {
    List<ChooseJoinMatchUserBean.PlayersBean> manPlayers=new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> womanPlayers=new ArrayList<>();

    public ChooseNoAthleteMajorAdapter() {
        super(R.layout.item_choose_no_athletemajor);
    }

    public void setList(List<ChooseJoinMatchUserBean.PlayersBean> manPlayers, List<ChooseJoinMatchUserBean.PlayersBean> womanPlayers) {
        this.manPlayers = manPlayers;
        this.womanPlayers = womanPlayers;
        notifyDataSetChanged();
    }

    public List<ChooseJoinMatchUserBean.PlayersBean> getManPlayers() {
        return manPlayers;
    }

    public List<ChooseJoinMatchUserBean.PlayersBean> getWomanPlayers() {
        return womanPlayers;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseJoinMatchUserBean.PlayersBean item) {
        ItemChooseNoAthletemajorBinding binding = DataBindingUtil.bind(helper.getConvertView());
        helper.addOnClickListener(R.id.ll_left_one);
        helper.addOnClickListener(R.id.ll_right_one);
        String round = CommonUtilis.numberToChinese(helper.getAdapterPosition() + 1);
        binding.tvShuangda.setText("第" + round + "双打");

        if (TextUtils.isEmpty(item.getUserLeftName())) {
            binding.tvLeftOne.setText("请选择");
        } else {
            binding.tvLeftOne.setText("" + item.getUserLeftName());
        }
        if (TextUtils.isEmpty(item.getUserRightName())) {
            binding.tvRightOne.setText("请选择");
        } else {
            binding.tvRightOne.setText("" + item.getUserRightName());
        }


    }
}
