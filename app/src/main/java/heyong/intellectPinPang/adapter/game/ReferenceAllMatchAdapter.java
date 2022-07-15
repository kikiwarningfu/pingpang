package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.databinding.ItemReferenceAllMatchOneBinding;
import heyong.intellectPinPang.databinding.ItemReferenceAllMatchTwoBinding;

/**
 * 裁判长
 */
public class ReferenceAllMatchAdapter extends BaseQuickAdapter<TableNumArrangeBean.ArrangesBean, BaseViewHolder> {
    //    public ReferenceAllMatchAdapter() {
//        super(R.layout.item_reference_all_match_one);
//    }
    public ReferenceAllMatchAdapter() {
        super(0);
        MultiTypeDelegate<TableNumArrangeBean.ArrangesBean> multiTypeDelegate = new MultiTypeDelegate<TableNumArrangeBean.ArrangesBean>() {
            @Override
            protected int getItemType(TableNumArrangeBean.ArrangesBean o) {
                return Integer.parseInt(o.getItemType());
            }
        };
        multiTypeDelegate.registerItemType(1, R.layout.item_reference_all_match_one);
        multiTypeDelegate.registerItemType(2, R.layout.item_reference_all_match_one);
        multiTypeDelegate.registerItemType(3, R.layout.item_reference_all_match_two);
        multiTypeDelegate.registerItemType(4, R.layout.item_reference_all_match_two);

        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, TableNumArrangeBean.ArrangesBean item) {
        int status = Integer.parseInt(item.getStatus());//1待赛  2已结束
        switch (item.getItemType()) {
            case "1":
            case "2":
                ItemReferenceAllMatchOneBinding binding = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    //                    binding.cardView.setBackground(null);
                    binding.llContent.setBackground(null);
                } else {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding.cardView.setBackgroundResource(0);
                    binding.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding.tvTitle.setText("" + item.getTitle());
//                ImageLoader.loadImage(binding.ivLeftLogo, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                ImageLoader.loadImage(binding.ivRightLogo, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding.tvLeftName.setText("" + item.getPlayer1Name());
                binding.tvRightName.setText("" + item.getPlayer3Name());
                helper.addOnClickListener(R.id.tv_game_end_status);
                binding.tvBeginTime.setText(""+item.getBeginTime());
//                null未开始,1已结束，2进行中，3左边已确认成绩 4右边已确认成绩 6双方都确认成绩 7团队左方填完对阵表 8团队右边填完对阵表 9，双方都填完 10，虚位待赛
                if (status ==0) {//未开始
                    binding.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding.llScore.setVisibility(View.GONE);
                    binding.tvGameStatus.setText("待赛");
                    binding.tvGameEndStatus.setText("调台");
                    helper.addOnClickListener(R.id.tv_game_end_status);

                    binding.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                } else if(status==1){
                    binding.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding.llScore.setVisibility(View.VISIBLE);
                    binding.tvGameStatus.setText("已结束");
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    helper.addOnClickListener(R.id.ll_score);
                    binding.tvGameEndStatus.setText("详情");
                    binding.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();

//                    #EB4430
                    binding.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if(leftWavier==1&&rightWavier==1)
                    {
                        binding.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        binding.tvScoreRight.setText("W-"+item.getRightWinCount());
                    }else
                    {
                        binding.tvScoreLeft.setText(""+item.getLeftWinCount());
                        binding.tvScoreRight.setText(""+item.getRightWinCount());
                        if(leftWavier==1)
                        {
                            binding.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        }
                        if(rightWavier==1)
                        {
                            binding.tvScoreRight.setText("W-"+item.getRightWinCount());
                        }
                        if(item.getLeftWinCount()>item.getRightWinCount())
                        {
                            binding.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                        }else if(item.getLeftWinCount()<item.getRightWinCount())
                        {
                            binding.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                        }
                    }
                }else
                {
                    binding.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding.llScore.setVisibility(View.VISIBLE);
                    binding.tvGameStatus.setText("进行中");
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    helper.addOnClickListener(R.id.ll_score);
                    binding.tvGameEndStatus.setText("详情");
                    binding.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    if (leftWavier == 1 || rightWavier == 1) {
                        binding.tvGameEndStatus.setVisibility(View.GONE);

                    }else
                    {
                        binding.tvGameEndStatus.setVisibility(View.VISIBLE);
                    }
//                    #EB4430
                    binding.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if(leftWavier==1&&rightWavier==1)
                    {
                        binding.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        binding.tvScoreRight.setText("W-"+item.getRightWinCount());
                    }else
                    {
                        binding.tvScoreLeft.setText(""+item.getLeftWinCount());
                        binding.tvScoreRight.setText(""+item.getRightWinCount());
                        if(leftWavier==1)
                        {
                            binding.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        }
                        if(rightWavier==1)
                        {
                            binding.tvScoreRight.setText("W-"+item.getRightWinCount());
                        }
                        if(item.getLeftWinCount()>item.getRightWinCount())
                        {
                            binding.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                        }else if(item.getLeftWinCount()<item.getRightWinCount())
                        {
                            binding.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                        }
                    }
                }
                break;

            case "3":
            case "4":
                ItemReferenceAllMatchTwoBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding2.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding2.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    //                    binding.cardView.setBackground(null);
                    binding2.llContent.setBackground(null);
                } else {
                    binding2.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding2.cardView.setBackgroundResource(0);
                    binding2.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                helper.addOnClickListener(R.id.tv_game_end_status);
                binding2.tvBeginTime.setText(""+item.getBeginTime());

                binding2.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding2.tvTitle.setText("" + item.getTitle());
//                ImageLoader.loadImage(binding2.ivLeft1Logo, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                ImageLoader.loadImage(binding2.ivLeft2Logo, item.getHeadImg2(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                ImageLoader.loadImage(binding2.ivRight1Logo, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                ImageLoader.loadImage(binding2.ivRight2Logo, item.getHeadImg4(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding2.tvLeft1Name.setText("" + item.getPlayer1Name());
                binding2.tvLeft2Name.setText("" + item.getPlayer2Name());
                binding2.tvRight1Name.setText("" + item.getPlayer3Name());
                binding2.tvRight2Name.setText("" + item.getPlayer4Name());

                int status2 = Integer.parseInt(item.getStatus());
                if (status2 ==0) {//待赛
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding2.llScore.setVisibility(View.INVISIBLE);
                    binding2.tvGameStatus.setText("待赛");
                    binding2.tvGameEndStatus.setText("调台");
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                } else if(status2==1){
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding2.llScore.setVisibility(View.VISIBLE);
                    binding2.tvGameStatus.setText("已结束");
                    binding2.tvGameEndStatus.setText("详情");
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    helper.addOnClickListener(R.id.ll_score);
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    if (leftWavier == 1 || rightWavier == 1) {
                        binding2.tvGameEndStatus.setVisibility(View.GONE);

                    }else
                    {
                        binding2.tvGameEndStatus.setVisibility(View.VISIBLE);
                    }
                    //                    #EB4430
                    binding2.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding2.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if(leftWavier==1&&rightWavier==1)
                    {
                        binding2.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        binding2.tvScoreRight.setText("W-"+item.getRightWinCount());
                    }else
                    {
                        binding2.tvScoreLeft.setText(""+item.getLeftWinCount());
                        binding2.tvScoreRight.setText(""+item.getRightWinCount());
                        if(leftWavier==1)
                        {
                            binding2.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        }
                        if(rightWavier==1)
                        {
                            binding2.tvScoreRight.setText("W-"+item.getRightWinCount());
                        }
                        if(item.getLeftWinCount()>item.getRightWinCount())
                        {
                            binding2.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                        }else if(item.getLeftWinCount()<item.getRightWinCount())
                        {
                            binding2.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                        }
                    }

                }else
                {
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding2.llScore.setVisibility(View.VISIBLE);
                    binding2.tvGameStatus.setText("进行中");
                    binding2.tvGameEndStatus.setText("详情");
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    helper.addOnClickListener(R.id.ll_score);
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    //                    #EB4430
                    binding2.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding2.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if(leftWavier==1&&rightWavier==1)
                    {
                        binding2.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        binding2.tvScoreRight.setText("W-"+item.getRightWinCount());
                    }else
                    {
                        binding2.tvScoreLeft.setText(""+item.getLeftWinCount());
                        binding2.tvScoreRight.setText(""+item.getRightWinCount());
                        if(leftWavier==1)
                        {
                            binding2.tvScoreLeft.setText("W-"+item.getLeftWinCount());
                        }
                        if(rightWavier==1)
                        {
                            binding2.tvScoreRight.setText("W-"+item.getRightWinCount());
                        }
                        if(item.getLeftWinCount()>item.getRightWinCount())
                        {
                            binding2.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                        }else if(item.getLeftWinCount()<item.getRightWinCount())
                        {
                            binding2.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                        }
                    }
                }




                break;
        }
    }
}
