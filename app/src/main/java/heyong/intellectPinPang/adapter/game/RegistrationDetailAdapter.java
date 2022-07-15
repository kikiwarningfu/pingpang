package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.RegistrationBean;
import heyong.intellectPinPang.databinding.ItemRegistrationDetailBinding;
import heyong.intellectPinPang.live.adapter.MyTagRegisterDetailAdapter;
import heyong.intellectPinPang.live.adapter.MyTagRegisterDetailMemberAdapter;

public class RegistrationDetailAdapter extends BaseQuickAdapter<RegistrationBean, BaseViewHolder> {

    MyTagRegisterDetailAdapter mMyTagRegisterLeaderAdapter;
    MyTagRegisterDetailMemberAdapter mMyTagRegisterTeamNumberAdapter;
    private Context mContext;

    public RegistrationDetailAdapter(Context context) {
        super(R.layout.item_registration_detail);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RegistrationBean item) {
        ItemRegistrationDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        mMyTagRegisterLeaderAdapter = new MyTagRegisterDetailAdapter(mContext, item.getLeader());
        binding.flowLeader.setAdapter(mMyTagRegisterLeaderAdapter);
        if (item.getLeader().size() > 0) {
            binding.llLeader.setVisibility(View.VISIBLE);
        } else {
            binding.llLeader.setVisibility(View.GONE);
        }
        if (item.getPlayers().size() > 0) {
            binding.llTeamNumber.setVisibility(View.VISIBLE);
        } else {
            binding.llTeamNumber.setVisibility(View.GONE);
        }
        mMyTagRegisterTeamNumberAdapter = new MyTagRegisterDetailMemberAdapter(mContext, item.getPlayers());
        binding.flowTeamNumber.setAdapter(mMyTagRegisterTeamNumberAdapter);
        binding.tvClubName.setText("" + item.getClubName());
        binding.tvAllPeople.setText("合计：" + item.getPersonCount() + "人");


    }


}
