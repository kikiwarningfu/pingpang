package heyong.intellectPinPang.ui.coachdisplay;

import android.graphics.Color;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemRefereeDisplayListBinding;

/**
 * 赛事列表的适配器
 */
public class RefereeDisplayListAdapter extends BaseItemDraggableAdapter<RefereeDisPlayBean, BaseViewHolder> {
    boolean isDraggable = false;

    public RefereeDisplayListAdapter(List<RefereeDisPlayBean> refereeDisPlayBeanList) {
        super(R.layout.item_referee_display_list, refereeDisPlayBeanList);
    }

    public void isShowDragable(boolean isDragable) {
        this.isDraggable = isDragable;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefereeDisPlayBean item) {
        ItemRefereeDisplayListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        helper.addOnClickListener(R.id.tv_game_end_status);

        binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
        binding.tvTitle.setText("" + item.getTitle());
        //        if (item.getLeftLogo().equals("1")) {
        //            ImageLoader.loadImages(binding.ivLeftLogoOne, R.drawable.img_chaoshen);
        //        } else {
        //            ImageLoader.loadImage(binding.ivLeftLogoOne, item.getLeftLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        //        }
        //        if (item.getRightLogo().equals("2")) {
        //            ImageLoader.loadImages(binding.ivRightLogoOne, R.drawable.img_xiaoliang);
        //        } else {
        //            ImageLoader.loadImage(binding.ivRightLogoOne, item.getRightLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        //        }
        binding.tvLeftNameOne.setText("" + item.getLeftName());
        binding.tvRightNameOne.setText("" + item.getRightName());
        if (isDraggable) {
            binding.llNoMoveUp.setVisibility(View.GONE);
            binding.ivMoveUp.setVisibility(View.VISIBLE);
        } else {
            binding.llNoMoveUp.setVisibility(View.VISIBLE);

            binding.ivMoveUp.setVisibility(View.GONE);

        }
        switch (item.getShowStatus()) {
            case 1://显示开始
                binding.llScore.setVisibility(View.GONE);
                binding.tvGameStatus.setText("待赛");
                binding.tvGameEndStatus.setText("开始");
                binding.tvGiveUp.setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.tv_give_up);
                binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_competition_blulu_8);
                binding.tvGameEndStatus.setTextColor(Color.parseColor("#FF0D3078"));
                binding.tvBeginTime.setText("" + item.getTime());
                break;
            case 2://显示详情
                binding.llScore.setVisibility(View.VISIBLE);
                binding.tvBeginTime.setText("" + item.getTime());
                binding.tvGiveUp.setVisibility(View.GONE);
                binding.tvGameStatus.setText("已结束");
                /*判断弃权*/
                int leftWavier = item.getLeftWavier();
                int rightWavier = item.getRightWavier();
                binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_competition_blulu_8);
                binding.tvGameEndStatus.setTextColor(Color.parseColor("#FF0D3078"));
                binding.tvGameEndStatus.setTextColor(Color.parseColor("#FF0D3078"));
                //设置为false  隐藏掉详情 不让点进去
                if (item.isShowGiveUp()) {
                    binding.tvGameEndStatus.setVisibility(View.VISIBLE);
                } else {
                    binding.tvGameEndStatus.setVisibility(View.GONE);
                }
                binding.tvGameEndStatus.setText("详情");
                binding.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                binding.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                if (leftWavier == 1 && rightWavier == 1) {
                    binding.tvScoreLeft.setText("W-" + item.getLeftWinCount());
                    binding.tvScoreRight.setText("W-" + item.getRightWinCount());
                } else {
                    binding.tvScoreLeft.setText("" + item.getLeftWinCount());
                    binding.tvScoreRight.setText("" + item.getRightWinCount());
                    if (leftWavier == 1) {
                        binding.tvScoreLeft.setText("W-" + item.getLeftWinCount());
                    }
                    if (rightWavier == 1) {
                        binding.tvScoreRight.setText("W-" + item.getRightWinCount());
                    }
                    if (item.getLeftWinCount() > item.getRightWinCount()) {
                        binding.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                    } else if (item.getLeftWinCount() < item.getRightWinCount()) {
                        binding.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                    }
                }


                break;
        }

    }


}
