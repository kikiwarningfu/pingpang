package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.Collections;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.databinding.ItemRefereeCompetitionDaisaiBinding;
import heyong.intellectPinPang.databinding.ItemRefereeCompetitionStartOneBinding;
import heyong.intellectPinPang.databinding.ItemRefereeCompetitionStartTwoBinding;
import heyong.intellectPinPang.iterface.IOperationData;

/**
 * 裁判员比赛界面-裁判长未点开始
 */
public class RefereeCompetitionInterfaceAdapter extends
        BaseItemDraggableAdapter<TableNumArrangeBean.ArrangesBean, BaseViewHolder>
        implements IOperationData {
    boolean isDraggable = false;

    private String matchStaus = "";

    public void isShowDragable(boolean isDragable) {
        this.isDraggable = isDragable;
    }

    public void setmatchStatus(String status) {
        if (TextUtils.isEmpty(status)) {
            matchStaus = "0";
        } else {
            this.matchStaus = status;
        }
    }

    public String getMatchStatus() {
        return matchStaus;
    }


    public RefereeCompetitionInterfaceAdapter(List<TableNumArrangeBean.ArrangesBean> mItems) {
        super(mItems);
        MultiTypeDelegate<TableNumArrangeBean.ArrangesBean> multiTypeDelegate = new MultiTypeDelegate<TableNumArrangeBean.ArrangesBean>() {
            @Override
            protected int getItemType(TableNumArrangeBean.ArrangesBean o) {
                return o.getShowItemType();
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.item_referee_competition_start_one);
        multiTypeDelegate.registerItemType(1, R.layout.item_referee_competition_start_two);
        multiTypeDelegate.registerItemType(2, R.layout.item_referee_competition_daisai);

        setMultiTypeDelegate(multiTypeDelegate);
        mItems.addAll(mItems);

    }

    @Override
    protected void convert(BaseViewHolder helper, TableNumArrangeBean.ArrangesBean item) {
        int myStatus = Integer.parseInt(matchStaus);
        switch (helper.getItemViewType()) {//开始   已结束

            case 0://单打
                String itemType = item.getItemType();

                ItemRefereeCompetitionStartOneBinding binding = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    // binding.cardView.setBackground(null);
                    binding.llContent.setBackground(null);
                } else {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding.cardView.setBackgroundResource(0);
                    binding.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                if (Integer.parseInt(itemType) == 2) {
                    //单打
                    binding.tvLeftClubName.setVisibility(View.VISIBLE);
                    binding.tvRightClubName.setVisibility(View.VISIBLE);
                    binding.tvLeftClubName.setText("" + item.getClub1Name());
                    binding.tvRightClubName.setText("" + item.getClub2Name());
                } else {
                    //团体
                    binding.tvLeftClubName.setVisibility(View.GONE);
                    binding.tvRightClubName.setVisibility(View.GONE);
                }
                binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding.tvTitle.setText("" + item.getTitle());
                //                ImageLoader.loadImage(binding.ivLeftLogoOne, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                //                ImageLoader.loadImage(binding.ivRightLogoOne, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding.tvLeftNameOne.setText("" + item.getPlayer1Name());
                binding.tvRightNameOne.setText("" + item.getPlayer3Name());
                if (isDraggable) {
                    binding.rlHaveMoveUp.setVisibility(View.VISIBLE);
                    binding.llNoMoveUp.setVisibility(View.GONE);
                } else {
                    binding.rlHaveMoveUp.setVisibility(View.GONE);
                    binding.llNoMoveUp.setVisibility(View.VISIBLE);
                }
                int status = Integer.parseInt(item.getStatus());
                helper.addOnClickListener(R.id.tv_game_end_status);
                //status返回2 进行中
                if (status == 0 || status == 7 || status == 8 || status == 9) {//待赛
                    binding.llScore.setVisibility(View.GONE);
                    binding.tvGameStatus.setText("待赛");
                    binding.tvGameEndStatus.setText("开始");
                    binding.tvGameEndStatus.setVisibility(View.VISIBLE);

                    if (myStatus == 5) {
                        binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_blue);
                        binding.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));
                        binding.tvGiveUp.setVisibility(View.VISIBLE);
                        helper.addOnClickListener(R.id.tv_give_up);
                    } else {
                        binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_gray);
                        binding.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));
                        binding.tvGiveUp.setVisibility(View.GONE);
                    }

                    binding.tvBeginTime.setText("" + item.getBeginTime());
                } else {
                    helper.addOnClickListener(R.id.ll_score);
                    binding.llScore.setVisibility(View.VISIBLE);
                    binding.tvGiveUp.setVisibility(View.GONE);

                    binding.tvBeginTime.setText("" + item.getBeginTime());
                    binding.tvGiveUp.setVisibility(View.GONE);
                    if (status == 2) {
                        binding.tvGameStatus.setText("进行中");
                    } else {
                        binding.tvGameStatus.setText("已结束");
                    }
                    //                    binding.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_big_blue_8);
                    binding.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));

                    binding.tvGameEndStatus.setText("详情");
                    //                    #EB4430
                    binding.tvScoreLeft.setTextColor(Color.parseColor("#333333"));
                    binding.tvScoreRight.setTextColor(Color.parseColor("#333333"));
                    if (leftWavier == 1 || rightWavier == 1) {
                        binding.tvGameEndStatus.setVisibility(View.GONE);

                    } else {
                        binding.tvGameEndStatus.setVisibility(View.VISIBLE);
                    }
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
                    binding.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));


                }
                break;
            case 1://双打
                ItemRefereeCompetitionStartTwoBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding2.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding2.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    binding2.llContent.setBackgroundResource(0);
                } else {
                    binding2.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding2.cardView.setBackgroundResource(0);
                    binding2.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                binding2.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding2.tvTitle.setText("" + item.getTitle());
                //                ImageLoader.loadImage(binding2.ivLeftLogoOne, item.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                //                ImageLoader.loadImage(binding2.ivLeftLogoTwo, item.getHeadImg2(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                //                ImageLoader.loadImage(binding2.ivRightLogoOne, item.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                //                ImageLoader.loadImage(binding2.ivRightLogoTwo, item.getHeadImg4(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                binding2.tvLeftNameOne.setText("" + item.getPlayer1Name());
                binding2.tvLeftNameTwo.setText("" + item.getPlayer2Name());
                binding2.tvRightNameOne.setText("" + item.getPlayer3Name());
                binding2.tvRightNameTwo.setText("" + item.getPlayer4Name());
                binding2.tvBeginTime.setText("" + item.getBeginTime());
                binding2.tvLeftClubName.setText("" + item.getClub1Name());
                binding2.tvRightClubName.setText("" + item.getClub2Name());
                helper.addOnClickListener(R.id.tv_game_end_status);
                if (isDraggable) {
                    binding2.ivMoveUp.setVisibility(View.VISIBLE);
                } else {
                    binding2.ivMoveUp.setVisibility(View.GONE);

                }
                int status2 = Integer.parseInt(item.getStatus());
                if (status2 == 0 || status2 == 7 || status2 == 8 || status2 == 9) {//待赛F
                    //                    binding2.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                    binding2.llScore.setVisibility(View.GONE);
                    binding2.tvGameStatus.setText("待赛");
                    //                    binding2.tvGameStatus.setTextColor(Color.parseColor("#0D3078"));
                    binding2.tvGameEndStatus.setText("开始");
                    binding2.tvGameEndStatus.setVisibility(View.VISIBLE);

                    if (myStatus == 5) {
                        binding2.tvGiveUp.setVisibility(View.VISIBLE);
                        helper.addOnClickListener(R.id.tv_give_up);
                        binding2.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_blue);
                        binding2.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));
                    } else {
                        binding2.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_gray);
                        binding2.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));
                        binding2.tvGiveUp.setVisibility(View.GONE);

                    }


                } else {
                    helper.addOnClickListener(R.id.ll_score);
                    binding2.llScore.setVisibility(View.VISIBLE);
                    binding2.tvGameEndStatus.setBackgroundResource(R.drawable.shape_club_big_blue_8);
                    binding2.tvGameEndStatus.setText("详情");
                    binding2.tvGameEndStatus.setTextColor(Color.parseColor("#FEFEFE"));
                    if (myStatus == 2) {
                        binding2.tvGameStatus.setText("进行中");
                    } else {
                        binding2.tvGameStatus.setText("已结束");
                    }
                    binding2.tvGiveUp.setVisibility(View.GONE);

                    //                    binding2.tvGameStatus.setTextColor(Color.parseColor("#CCCCCC"));
                    /*判断弃权*/
                    int leftWavier = item.getLeftWaiver();
                    int rightWavier = item.getRightWaiver();
                    if (leftWavier == 1 || rightWavier == 1) {
                        binding2.tvGameEndStatus.setVisibility(View.GONE);

                    } else {
                        binding2.tvGameEndStatus.setVisibility(View.VISIBLE);
                    }
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


                }
                break;
            case 2://待赛
                ItemRefereeCompetitionDaisaiBinding binding3 = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding3.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding3.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    binding3.llContent.setBackgroundResource(0);
                } else {
                    binding3.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding3.cardView.setBackgroundResource(0);
                    binding3.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                binding3.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
                binding3.tvTitle.setText("" + item.getTitle());
                binding3.tvBeginTime.setTextColor(Color.parseColor("#333333"));
                binding3.tvBeginTime.setText("" + item.getBeginTime());

                if (isDraggable) {
                    binding3.ivMoveUp.setVisibility(View.VISIBLE);
                } else {
                    binding3.ivMoveUp.setVisibility(View.GONE);
                }

                break;

        }
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {

    }
}
