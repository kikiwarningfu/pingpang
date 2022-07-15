package heyong.intellectPinPang.adapter.game;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ItemChooseAthlemeMajorListBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 弹出这个 作为 弹窗 选择人数
 */
public class ItemChooseAthleteMajorListAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean.Players, BaseViewHolder> {
    List<SelectPeopleProjectItemBean.Players> manPlayers;
    List<SelectPeopleProjectItemBean.Players> womanPlayers;
    private int ownerType;

    public void setList(List<SelectPeopleProjectItemBean.Players> manPlayers, List<SelectPeopleProjectItemBean.Players> womanPlayers, int ownerType) {
        this.manPlayers = manPlayers;
        this.womanPlayers = womanPlayers;
        this.ownerType = ownerType;
        notifyDataSetChanged();
    }

    public void setManList(List<SelectPeopleProjectItemBean.Players> manPlayers, int ownerType) {
        this.manPlayers = manPlayers;
        this.ownerType = ownerType;
        notifyDataSetChanged();
    }

    public void setWoManList(List<SelectPeopleProjectItemBean.Players> womanPlayers, int ownerType) {
        this.womanPlayers = womanPlayers;
        this.ownerType = ownerType;
        notifyDataSetChanged();
    }

    public List<SelectPeopleProjectItemBean.Players> getManPlayers() {
        return manPlayers;
    }

    public List<SelectPeopleProjectItemBean.Players> getWomanPlayers() {
        return womanPlayers;
    }


    public ItemChooseAthleteMajorListAdapter() {
        super(R.layout.item_choose_athleme_major_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean.Players item) {
        ItemChooseAthlemeMajorListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        helper.addOnClickListener(R.id.ll_man_name_left);
        helper.addOnClickListener(R.id.ll_man_name_right);
        String round = CommonUtilis.numberToChinese(helper.getAdapterPosition() + 1);
        binding.tvPosition.setText("第" + round + "双打");
//        if (TextUtils.isEmpty(item.getLeftOneName())) {
//            binding.tvLeftOne.setText("请选择");
//        } else {
//            binding.tvLeftOne.setText("" + item.getLeftOneName());
//        }
//        if (TextUtils.isEmpty(item.getRightOneName())) {
//            binding.tvRightOne.setText("请选择");
//        } else {
//            binding.tvRightOne.setText("" + item.getRightOneName());
//        }
        if(ownerType==1)
        {
            SelectPeopleProjectItemBean.Players players = manPlayers.get((helper.getAdapterPosition()));
            if (TextUtils.isEmpty(players.getLeftOneName())) {
                binding.tvLeftOne.setText("请选择");
            } else {
                binding.tvLeftOne.setText("" + players.getLeftOneName());
            }
            if (TextUtils.isEmpty(players.getRightOneName())) {
                binding.tvRightOne.setText("请选择");
            } else {
                binding.tvRightOne.setText("" + players.getRightOneName());
            }
        }else if(ownerType==2)
        {
            SelectPeopleProjectItemBean.Players players = womanPlayers.get((helper.getAdapterPosition()));
            if (TextUtils.isEmpty(players.getLeftOneName())) {
                binding.tvLeftOne.setText("请选择");
            } else {
                binding.tvLeftOne.setText("" + players.getLeftOneName());
            }
            if (TextUtils.isEmpty(players.getRightOneName())) {
                binding.tvRightOne.setText("请选择");
            } else {
                binding.tvRightOne.setText("" + players.getRightOneName());
            }
        }else
        {
            SelectPeopleProjectItemBean.Players players = womanPlayers.get(helper.getAdapterPosition());
            if (TextUtils.isEmpty(players.getLeftOneName())) {
                binding.tvLeftOne.setText("请选择");
            } else {
                binding.tvLeftOne.setText("" + players.getLeftOneName());
            }
            SelectPeopleProjectItemBean.Players players1 = womanPlayers.get(helper.getAdapterPosition());
            if (TextUtils.isEmpty(players1.getRightOneName())) {
                binding.tvRightOne.setText("请选择");
            } else {
                binding.tvRightOne.setText("" + players1.getRightOneName());
            }
        }
//        if ((manPlayers != null && manPlayers.size() > 0) && (womanPlayers != null && womanPlayers.size() > 0)) {
//            /*两个都有*/
//
//        } else if (manPlayers != null && manPlayers.size() > 0) {
//
//        }
    }
}
