package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoListBean;
import heyong.intellectPinPang.databinding.ItemInternalNameBinding;
import heyong.intellectPinPang.utils.MyDateUtils;

/**
 * 对内比赛列表的适配器
 */
public class InternalGameListAdapter extends BaseQuickAdapter<XlClubContestInfoListBean.ListBean, BaseViewHolder> {
    //    contestStatus    1进行中  2 已结束
    public InternalGameListAdapter() {
        super(R.layout.item_internal_name);
    }

    @Override
    protected void convert(BaseViewHolder helper, XlClubContestInfoListBean.ListBean item) {
        ItemInternalNameBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvTitle.setText("" + item.getContestTitle());
        if("1".equals(item.getContestStatus())) {
            binding.tvContestStatus.setText("进行中");
            binding.tvContestStatus.setTextColor(Color.parseColor("#FBC545"));

        }else if("2".equals(item.getContestStatus())) {
            binding.tvContestStatus.setText("已结束");
            binding.tvContestStatus.setTextColor(Color.parseColor("#333333"));
        }
        try{
            String matchTime111 = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getEndTiem());
            binding.tvTime.setText("" +matchTime111);
        }catch (Exception e)
        {
            binding.tvTime.setText("" +item.getEndTiem());
        }


    }
}
