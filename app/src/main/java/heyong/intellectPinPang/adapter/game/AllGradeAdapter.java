package heyong.intellectPinPang.adapter.game;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.databinding.ItemAllGradeBinding;

public class AllGradeAdapter extends BaseQuickAdapter<AwardsMathListBean, BaseViewHolder> {
    private int type = -1;

    public AllGradeAdapter(int type) {
        super(R.layout.item_all_grade);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardsMathListBean item) {
        ItemAllGradeBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
        binding.tvMatchTitle.setText("" + item.getMathTitle());
        binding.tvCompetitionTime.setText("" + item.getBeginTime());
        binding.tvCompetitionAddress.setText("" + item.getCity().replace(" ", "") + item.getLocal());
        binding.tvDuishouNameDanda.setText("" + item.getName());
        binding.tvScore.setText("" + item.getSocre());
        helper.addOnClickListener(R.id.tv_duishou_name_danda);
        helper.addOnClickListener(R.id.tv_score);

//        if (type == 2) {
//            //单打
//            binding.tvDuishouNameDanda.setVisibility(View.GONE);
//            binding.viewDuishouNameDanda.setVisibility(View.GONE);
//        } else {
//            binding.tvDuishouNameDanda.setVisibility(View.VISIBLE);
//            binding.viewDuishouNameDanda.setVisibility(View.VISIBLE);
//        }


    }
}
