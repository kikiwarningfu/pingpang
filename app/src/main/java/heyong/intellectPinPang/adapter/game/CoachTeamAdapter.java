package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CoachMatchDtaBean;
import heyong.intellectPinPang.databinding.ItemCoachTeamBinding;
import heyong.intellectPinPang.databinding.ItemCoachTeamDoubleBinding;
import heyong.intellectPinPang.utils.MyDateUtils;

/**
 * projectType   //1 团体 2单打  3双打
 * status null未开始（待赛） 1是已结束 2进行中
 */
public class CoachTeamAdapter extends BaseQuickAdapter<CoachMatchDtaBean, BaseViewHolder> {
    public CoachTeamAdapter() {
        super(0);
        MultiTypeDelegate<CoachMatchDtaBean> multiTypeDelegate = new MultiTypeDelegate<CoachMatchDtaBean>() {
            @Override
            protected int getItemType(CoachMatchDtaBean o) {
//                Log.e(TAG, "getItemType: type==="+o.getType());
                return Integer.parseInt(o.getProjectType());
            }
        };
        multiTypeDelegate.registerItemType(1, R.layout.item_coach_team);//
        multiTypeDelegate.registerItemType(2, R.layout.item_coach_team);//
        multiTypeDelegate.registerItemType(3, R.layout.item_coach_team_double);//
        setMultiTypeDelegate(multiTypeDelegate);
    }


    @Override
    protected void convert(BaseViewHolder helper, CoachMatchDtaBean item) {
        String matchTime = "";
        try {
            matchTime = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "HH:mm", item.getBeginTime());
        } catch (Exception e) {
            matchTime = item.getBeginTime();
        }

        if (TextUtils.isEmpty(matchTime)) {
            matchTime = item.getBeginTime();
        }
        switch (item.getProjectType()) {
            case "1"://团体
            case "2"://单打   1 2 都是单打判断
                ItemCoachTeamBinding binding = DataBindingUtil.bind(helper.getConvertView());

                binding.tvTableNumber.setText("" + item.getArrangeNum());
                binding.tvTitle.setText("" + item.getItemName());
                binding.tvTableNum.setText("" + item.getTableNum() + "台");
                ImageLoader.loadImage(binding.ivLeftLogo, item.getLeftClubImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(binding.ivRightLogo, item.getRightClubImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                if(item.getProjectType().equals("1"))
                {
                    binding.tvLeftName.setText("" + item.getLeftClubName());
                    binding.tvRightName.setText("" + item.getRightClubName());
                }else {
                    binding.tvLeftName.setText("" + item.getLeft1Name());
                    binding.tvRightName.setText("" + item.getRight1Name());
                }

                binding.tvTime.setText("" + matchTime);

                helper.addOnClickListener(R.id.ll_table_click);
                if (TextUtils.isEmpty(item.getStatus())) {
                    //待赛
                    binding.tvStatus.setText("待赛");
                    binding.tvStatus.setTextColor(Color.parseColor("#0D3078"));
                    binding.tvStatus.setTextSize(15);
                    binding.ivVs.setVisibility(View.VISIBLE);
                    binding.llEndScore.setVisibility(View.GONE);
                    binding.tvTime.setTextColor(Color.parseColor("#0D3078"));
                } else {
                    switch (item.getStatus()) {
                        case "1"://已结束
                            binding.tvStatus.setText("已结束");
                            binding.tvStatus.setTextColor(Color.parseColor("#666666"));
                            binding.tvStatus.setTextSize(15);
                            binding.ivVs.setVisibility(View.GONE);
                            binding.llEndScore.setVisibility(View.VISIBLE);
                            binding.tvSocre.setText("" + item.getLeftWinCount() + ":" + item.getRightWinCount());
                            binding.tvTime.setTextColor(Color.parseColor("#FF666666"));

                            //显示比分
                            break;
                        case "2"://进行中
                            binding.tvStatus.setText("进行中");
                            binding.ivVs.setVisibility(View.VISIBLE);
                            binding.llEndScore.setVisibility(View.GONE);
                            binding.tvStatus.setTextColor(Color.parseColor("#0D3078"));
                            binding.tvTime.setTextColor(Color.parseColor("#0D3078"));

                            binding.tvStatus.setTextSize(15);
                            break;
                    }
                }

                break;
            case "3"://当成双打判断
                ItemCoachTeamDoubleBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
                binding2.tvTableNumber.setText("" + item.getArrangeNum());
                binding2.tvTableNum.setText("" + item.getTableNum() + "台");
                binding2.tvTitle.setText("" + item.getItemName());
                ImageLoader.loadImage(binding2.ivLeft1Logo, item.getLeft1Img());
                ImageLoader.loadImage(binding2.ivLeft2Logo, item.getLeft2Img());
                binding2.tvLeft1Name.setText("" + item.getLeft1Name());
                binding2.tvLeft2Name.setText("" + item.getLeft2Name());
                binding2.tvRight1Name.setText("" + item.getRight1Name());
                binding2.tvRight2Name.setText("" + item.getRight2Name());
                binding2.tvTime.setText("" + matchTime);
                if (TextUtils.isEmpty(item.getStatus())) {
                    //待赛
                    binding2.tvStatus.setText("待赛");
                    binding2.tvStatus.setTextColor(Color.parseColor("#0D3078"));
                    binding2.tvStatus.setTextSize(15);
                    binding2.ivVs.setVisibility(View.VISIBLE);
                    binding2.llEndScore.setVisibility(View.GONE);
                    binding2.tvTime.setTextColor(Color.parseColor("#0D3078"));

                } else {
                    switch (item.getStatus()) {
                        case "1"://已结束
                            binding2.tvStatus.setText("已结束");
                            binding2.tvStatus.setTextColor(Color.parseColor("#666666"));
                            binding2.tvStatus.setTextSize(15);
                            binding2.ivVs.setVisibility(View.GONE);
                            binding2.llEndScore.setVisibility(View.VISIBLE);
                            binding2.tvSocre.setText("" + item.getLeftWinCount() + ":" + item.getRightWinCount());
                            binding2.tvTime.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "2"://进行中
                            binding2.tvStatus.setText("进行中");
                            binding2.tvStatus.setTextColor(Color.parseColor("#0D3078"));
                            binding2.tvStatus.setTextSize(15);
                            binding2.tvTime.setTextColor(Color.parseColor("#0D3078"));
                            binding2.ivVs.setVisibility(View.VISIBLE);
                            binding2.llEndScore.setVisibility(View.GONE);
                            break;
                    }
                }
                break;

        }
    }


}
