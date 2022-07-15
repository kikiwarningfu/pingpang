package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyMatchBean;
import heyong.intellectPinPang.databinding.ItemMyEventBinding;

/**
 * 我的赛事    "status": "2",
 */
public class MyEventAdapter extends BaseQuickAdapter<MyMatchBean.ListBean, BaseViewHolder> {
    Context context;
    int roles;

    public MyEventAdapter(Context context) {
        super(R.layout.item_my_event);
        this.context = context;
    }

    public void setRole(int role) {
        this.roles = role;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMatchBean.ListBean item) {
        ItemMyEventBinding binding = DataBindingUtil.bind(helper.getConvertView());
        helper.addOnClickListener(R.id.tv_competition_detail);
        String status = item.getStatus();
        String matchStatus = item.getMatchStatus();
        ImageLoader.loadImage(binding.ivLogo, item.getMatchImgUrl(), R.drawable.ic_default_image_write, R.drawable.ic_default_image_write);
        binding.tvClubTitle.setText("" + item.getMatchTitle());
        binding.tvZhubanfang.setText("主办方：" + item.getSponsor());
        binding.tvCompetionTime.setText("" + item.getBeginTime());
        binding.tvAddress.setText("" + item.getHoldCity() + " " + item.getVenue());
        //status           1 报名未交费    2报名成功   3   弃权比赛
        //matchStatus       1   进行中      2报名中     3  已结束
        switch (matchStatus) {
            case "1"://进行中
                binding.tvStatusText.setText("进行中");
                binding.llStatusContainer.setBackgroundResource(R.drawable.shape_yellow_corners_13);
                ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way);
                if(roles==2) {
                    //判断是否是已弃权
                    switch (status) {
                        case "1"://报名未交费  不显示 弃权比赛按钮
                            binding.tvGiveUpCompetition.setVisibility(View.INVISIBLE);
                            binding.tvGiveUpCompetition.setBackgroundResource(R.drawable.shape_club_red);
                            binding.tvGiveUpCompetition.setText("弃权比赛");
                            binding.tvGiveUpCompetition.setTextColor(Color.WHITE);
                            break;
                        case "2"://显示弃权比赛  添加点击事件
                            helper.addOnClickListener(R.id.tv_give_up_competition);
                            binding.tvGiveUpCompetition.setVisibility(View.INVISIBLE);
                            binding.tvGiveUpCompetition.setBackgroundResource(R.drawable.shape_club_red);
                            binding.tvGiveUpCompetition.setText("弃权比赛");
                            binding.tvGiveUpCompetition.setTextColor(Color.WHITE);

                            break;
                        case "3"://已弃权
                            binding.tvGiveUpCompetition.setVisibility(View.INVISIBLE);
                            binding.tvGiveUpCompetition.setBackgroundResource(R.drawable.shape_club_gray);
                            binding.tvGiveUpCompetition.setText("已弃权");
                            binding.tvGiveUpCompetition.setTextColor(Color.WHITE);

                            break;
                    }
                }
                break;
            case "2"://报名中
                binding.tvStatusText.setText("报名中");
                binding.llStatusContainer.setBackgroundResource(R.drawable.shape_blue_corners_13);
                ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way_going);
                binding.tvGiveUpCompetition.setVisibility(View.GONE);


                break;
            case "3"://已结束
                binding.tvStatusText.setText("已结束");
                binding.llStatusContainer.setBackgroundResource(R.drawable.shape_gray_solid_13);
                ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way_close);
                binding.tvGiveUpCompetition.setVisibility(View.GONE);

                break;
        }

    }
}
