package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.RefereeConsoleBean;
import heyong.intellectPinPang.databinding.ItemChiefJudgeConsoleBinding;

/**
 * 获奖成绩
 */
public class ChiefJudgeConsoleAdapter extends BaseQuickAdapter<RefereeConsoleBean, BaseViewHolder> {

    public ChiefJudgeConsoleAdapter() {
        super(R.layout.item_chief_judge_console);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefereeConsoleBean item) {
        ItemChiefJudgeConsoleBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvAll.setText("" + item.getAllCount());
        binding.tvWancheng.setText("" + item.getDoneCount());
        binding.tvBisaiIng.setText("" + item.getInHandCount());
        binding.tvDaisai.setText("" + item.getNoStartCount());
        helper.addOnClickListener(R.id.tv_wancheng);
        helper.addOnClickListener(R.id.tv_daisai);
        if (helper.getAdapterPosition() == 0) {
            /*第一个*/
            binding.tvLeftOne.setText("小计");
            setTextBold(binding.tvLeftOne);
            binding.tvRightName.setVisibility(View.GONE);
            binding.ivAdd.setVisibility(View.GONE);
            binding.tvAll.setTextColor(Color.parseColor("#FF333333"));
            setTextBold(binding.tvAll);
            binding.tvWancheng.setTextColor(Color.parseColor("#FF333333"));
            setTextBold(binding.tvWancheng);
            binding.tvBisaiIng.setTextColor(Color.parseColor("#FF333333"));
            setTextBold(binding.tvBisaiIng);
            binding.tvDaisai.setTextColor(Color.parseColor("#FF333333"));
            setTextBold(binding.tvDaisai);
        } else {

//            helper.addOnClickListener(R.id.tv_left_one);
            binding.tvLeftOne.setText("" + item.getTableNum() + "台");
            setTextUnBold(binding.tvLeftOne);
            binding.tvAll.setTextColor(Color.parseColor("#FF333333"));
            setTextUnBold(binding.tvAll);
            binding.tvWancheng.setTextColor(Color.parseColor("#FF4795ED"));
            setTextUnBold(binding.tvWancheng);
            binding.tvDaisai.setTextColor(Color.parseColor("#FF4795ED"));
            setTextUnBold(binding.tvDaisai);
            //判断比赛中
            if(item.getInHandDto()!=null)
            {
                helper.addOnClickListener(R.id.tv_bisai_ing);
                String fraction = item.getInHandDto().getFraction();
                binding.tvBisaiIng.setText(""+fraction);
                binding.tvBisaiIng.setTextColor(Color.parseColor("#FF4795ED"));
                setTextUnBold(binding.tvBisaiIng);
            }else
            {
                binding.tvBisaiIng.setText("- -");
                binding.tvBisaiIng.setTextColor(Color.parseColor("#FF333333"));
                setTextUnBold(binding.tvBisaiIng);
            }


            String refereeId = item.getRefereeId();
            if (TextUtils.isEmpty(refereeId)) {
                binding.tvRightName.setVisibility(View.GONE);
                binding.ivAdd.setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.iv_add);
            } else {
                binding.tvRightName.setVisibility(View.VISIBLE);
                binding.ivAdd.setVisibility(View.GONE);
                binding.tvRightName.setText("" + item.getRefereeName());
                helper.addOnClickListener(R.id.tv_right_name);
            }
        }
        binding.tvLeftOne.setTextColor(Color.parseColor("#FF333333"));
        if (helper.getAdapterPosition() % 2 == 0) {
            binding.llContainer.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            binding.llContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }

    private void setTextBold(TextView textView) {
        //android中为textview动态设置字体为粗体
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void setTextUnBold(TextView textView) {
        //设置不为加粗
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }
}
