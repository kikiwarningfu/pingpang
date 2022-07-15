package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyGameListBean;
import heyong.intellectPinPang.databinding.ItemReferenceVisitorMatchOneBinding;
import heyong.intellectPinPang.databinding.ItemReferenceVisitorMatchTwoBinding;

//游客
public class ReferenceMyMatchAdapter extends BaseQuickAdapter<MyGameListBean, BaseViewHolder> {

    public ReferenceMyMatchAdapter() {
        super(0);
        MultiTypeDelegate<MyGameListBean> multiTypeDelegate = new MultiTypeDelegate<MyGameListBean>() {
            @Override
            protected int getItemType(MyGameListBean o) {
                return Integer.parseInt(o.getItemType());
            }
        };
        multiTypeDelegate.registerItemType(1, R.layout.item_reference_visitor_match_one);
        multiTypeDelegate.registerItemType(2, R.layout.item_reference_visitor_match_one);
        multiTypeDelegate.registerItemType(3, R.layout.item_reference_visitor_match_two);
        multiTypeDelegate.registerItemType(4, R.layout.item_reference_visitor_match_two);

        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyGameListBean item) {

        switch (item.getItemType()) {
            case "1":
            case "2":
                ItemReferenceVisitorMatchOneBinding binding = DataBindingUtil.bind(helper.getConvertView());

                binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding.tvTitle.setText("" + item.getTitle());
                ImageLoader.loadImage(binding.ivLeftLogo, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(binding.ivRightLogo, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding.tvLeftName.setText("" + item.getPlayer1Name());
                binding.tvRightName.setText("" + item.getPlayer3Name());
//                if (item.isLocation()) {
//                    binding.rlContainer.setBackgroundResource(R.drawable.shape_little_blue_corners_8_no_solid);
//                } else {
                    binding.rlContainer.setBackgroundResource(R.drawable.shape_write_corners_10);
//                }
//                helper.addOnClickListener(R.id.ll_can_live);
                int status = Integer.parseInt(item.getStatus());
                binding.tvBeginTime.setText("" + item.getBeginTime());
                //1 3 4 6 已結束    null是待賽
                // 2 7 8 9 進行中
                if (status == 0) {//待赛
//                    binding.llCanLive.setVisibility(View.VISIBLE);
                    binding.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding.llScore.setVisibility(View.INVISIBLE);
                    binding.tvGameStatus.setText("待赛");
                    binding.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                } else if (status == 1 || status == 3 || status == 4 || status == 6) {
                    binding.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding.llScore.setVisibility(View.VISIBLE);
                    helper.addOnClickListener(R.id.ll_score);
                    binding.tvGameStatus.setText("已结束");
                    binding.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    //                    #EB4430
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


                } else {
//                    binding.llCanLive.setVisibility(View.GONE);
                    binding.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding.llScore.setVisibility(View.INVISIBLE);
                    binding.tvGameStatus.setText("进行中");
                    binding.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                    binding.tvGameStatus.setVisibility(View.VISIBLE);
                }

                break;
            case "3":
            case "4":
                ItemReferenceVisitorMatchTwoBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
//                helper.addOnClickListener(R.id.ll_can_live);
//                if (item.isLocation()) {
//                    binding2.rlContainer.setBackgroundResource(R.drawable.shape_little_blue_corners_8_no_solid);
//                } else {
                    binding2.rlContainer.setBackgroundResource(R.drawable.shape_write_corners_10);
//                }
                binding2.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding2.tvTitle.setText("" + item.getTitle());
                ImageLoader.loadImage(binding2.ivLeft1Logo, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(binding2.ivLeft2Logo, item.getHeadImg2(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(binding2.ivRight1Logo, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(binding2.ivRight2Logo, item.getHeadImg4(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding2.tvLeft1Name.setText("" + item.getPlayer1Name());
                binding2.tvLeft2Name.setText("" + item.getPlayer2Name());
                binding2.tvRight1Name.setText("" + item.getPlayer3Name());
                binding2.tvRight2Name.setText("" + item.getPlayer4Name());
                binding2.tvBeginTime.setText("" + item.getBeginTime());

                int status2 = Integer.parseInt(item.getStatus());
                if (status2 == 0) {//待赛
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding2.llScore.setVisibility(View.INVISIBLE);
                    binding2.tvGameStatus.setText("待赛");
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
//                    binding2.llCanLive.setVisibility(View.VISIBLE);

                } else if (status2 == 1 || status2 == 3 || status2 == 4 || status2 == 6) {
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#999999"));
                    binding2.llScore.setVisibility(View.VISIBLE);
//                    binding2.llCanLive.setVisibility(View.GONE);
                    helper.addOnClickListener(R.id.ll_score);

                    binding2.tvGameStatus.setText("已结束");
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();

                    //                    #EB4430
                    binding2.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding2.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if (leftWavier == 1 && rightWavier == 1) {
                        binding2.tvScoreLeft.setText("W-" + item.getLeftWinCount());
                        binding2.tvScoreRight.setText("W-" + item.getRightWinCount());
                    } else {
                        binding2.tvScoreLeft.setText("" + item.getLeftWinCount());
                        binding2.tvScoreRight.setText("" + item.getRightWinCount());
                        if (leftWavier == 1) {
                            binding2.tvScoreLeft.setText("W-" + item.getLeftWinCount());
                        }
                        if (rightWavier == 1) {
                            binding2.tvScoreRight.setText("W-" + item.getRightWinCount());
                        }
                        if (item.getLeftWinCount() > item.getRightWinCount()) {
                            binding2.tvScoreLeft.setTextColor(Color.parseColor("#EB4430"));
                        } else if (item.getLeftWinCount() < item.getRightWinCount()) {
                            binding2.tvScoreRight.setTextColor(Color.parseColor("#EB4430"));
                        }
                    }


                } else {
                    binding2.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding2.llScore.setVisibility(View.INVISIBLE);
                    binding2.tvGameStatus.setText("进行中");
                    binding2.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                    binding2.tvGameStatus.setVisibility(View.VISIBLE);
//                    binding2.llCanLive.setVisibility(View.GONE);
                }

                break;


        }


    }
}
