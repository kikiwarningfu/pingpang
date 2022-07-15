package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchArrangeKnockOutMatchBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.databinding.ItemEleminateMatchBinding;
import heyong.intellectPinPang.utils.MyDateUtils;

/**
 * 淘汰赛制适配器  抢号 虚位待赛   其他情况 判断  第一轮有轮空  其他 的 是虚位待赛
 */
public class EleminateMatchAdapter extends BaseQuickAdapter<MatchArrangeKnockOutMatchBean.OutListBean, BaseViewHolder> {
    private int itemType;
    private String itemShows;
    List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info;
    private int clickPosition = -1;
    private int clickRankingPosition = 0;
    boolean isHaveQianghao;
    private int quNum = 0;
    private int clickShowNum = 0;

    public EleminateMatchAdapter() {
        super(R.layout.item_eleminate_match);

    }

    public void setTypes(int types) {
        this.itemType = types;
        notifyDataSetChanged();

    }

    public void setTyClickTypeItem(String item) {
        this.itemShows = item;
        notifyDataSetChanged();
    }

    public void setClickSHowNum(int clickSHowNum) {
        this.clickShowNum = clickSHowNum;
        notifyDataSetChanged();
    }

    public void setQuNum(int quNum) {
        this.quNum = quNum;
        notifyDataSetChanged();
    }

    public void setInfoList(List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info) {
        this.info = info;
        notifyDataSetChanged();
    }

    public void setclickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
        notifyDataSetChanged();

    }

    public void setClickRankingPosition(int clickRankingPosition) {
        this.clickRankingPosition = clickRankingPosition;
        Log.e(TAG, "convert: cliclRanking set===" + clickRankingPosition);
        notifyDataSetChanged();

    }

    public void setQianghao(boolean isHaveQianghao) {
        this.isHaveQianghao = isHaveQianghao;
        notifyDataSetChanged();
    }

    /**
     * 定义一个接口
     */
    public interface onOwnMyListener {
        void OnMyListener(int clickType, int itemType, MatchArrangeKnockOutMatchBean.OutListBean item, int leftAndRightType);
    }

    /**
     * 定义一个变量储存数据
     */
    private onOwnMyListener myListener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setMyListener(onOwnMyListener listener) {
        this.myListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchArrangeKnockOutMatchBean.OutListBean item) {
        //        Log.e(TAG, "convert: item===" + new Gson().toJson(item));
        ItemEleminateMatchBinding binding = DataBindingUtil.bind(helper.getConvertView());
        //1 分数   2 号台   3 人名/团队名  4 不跳(轮空 或者隐藏)  clickType  itemType  MatchArrangeKnockOutMatchBean.OutListBean item

        //1 团体 2 单打 3 双打 4混双
        //点分数 跳转到  对战成绩页面  分团体和单打
        //号台  跳转到那个台子的列表
        //人名/团队名  跳转到 个人详情/队伍列表
        // 虚位待赛和轮空 不跳
        //抢号为空 虚位待赛     如果不是抢号 判断position是否是第一个
        //        Log.e(TAG, "convert: itemShows===" + itemShows);
        //        Log.e(TAG, "convert: clickPosition===" + clickPosition);

        //决赛------------------------
        //取数目 小于等于8  显示所有的 1-2 3-4 5-6 7-8
        //取数目大于8   只显示position 为0的那个（1-8   9-16）
        Log.e(TAG, "convert: itemShows===" + itemShows);
        Log.e(TAG, "convert: quNum====" + quNum);
        Log.e(TAG, "convert: cliclRanking====" + clickRankingPosition);
        if (itemShows.equals("决赛")) {
            binding.llContent.setVisibility(View.VISIBLE);
            if (quNum != 0) {
                if (quNum <= 8) {
                    binding.llContent.setVisibility(View.VISIBLE);
                    binding.tvContent.setText("" + ((helper.getAdapterPosition() + 1) * 2 - 1) + " - " + (helper.getAdapterPosition() + 1) * 2);
                } else {

                    if (quNum >= 16) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + (((helper.getAdapterPosition() + 1) * 2 - 1) + (8 * clickRankingPosition)) + " - "
                                + ((helper.getAdapterPosition() + 1) * 2 + (8 * clickRankingPosition)));
                    }

                }
            } else {
                binding.llContent.setVisibility(View.GONE);
            }
        } else if (itemShows.equals("半决赛")) {
            binding.llContent.setVisibility(View.VISIBLE);
            if (quNum != 0) {
                if (quNum == 2) {
                    binding.llContent.setVisibility(View.GONE);
                } else if (quNum == 4) {
                    if (helper.getAdapterPosition() == 0) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + 1 + " - " + 4);
                    } else if (helper.getAdapterPosition() == 2) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + 5 + " - " + 8);
                    } else {
                        binding.llContent.setVisibility(View.GONE);
                    }
                } else if (quNum == 8) {
                    if (helper.getAdapterPosition() == 0) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + 1 + " - " + 4);
                    } else if (helper.getAdapterPosition() == 2) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + 5 + " - " + 8);
                    } else {
                        binding.llContent.setVisibility(View.GONE);
                    }


                } else if (quNum == 16) {

                    if (helper.getAdapterPosition() == 0) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + (1 + (8 * clickRankingPosition)) + " - " + (4 + (8 * clickRankingPosition)));
                    } else if (helper.getAdapterPosition() == 2) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + (5 + (8 * clickRankingPosition)) + " - " + (8 + (8 * clickRankingPosition)));
                    } else {
                        binding.llContent.setVisibility(View.GONE);
                    }
                } else {
                    if (helper.getAdapterPosition() == 0) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + (1 + (8 * clickRankingPosition)) + " - " + (4 + (8 * clickRankingPosition)));
                    } else if (helper.getAdapterPosition() == 2) {
                        binding.llContent.setVisibility(View.VISIBLE);
                        binding.tvContent.setText("" + (5 + (8 * clickRankingPosition)) + " - " + (8 + (8 * clickRankingPosition)));
                    } else {
                        binding.llContent.setVisibility(View.GONE);
                    }
                }
            } else {
                binding.llContent.setVisibility(View.GONE);
            }
        } else {
            binding.llContent.setVisibility(View.GONE);

        }

        //半决赛-----------------------
        //取前二 显示0  1-4
        //取前四  显示0和2  1-4 5-8
        //取前八  显示0   1-8  9-16
        //取前16   显示0  1-8 9-16 17-25 26-32


        //其他 不显示
        if (clickPosition == 0) {
            if (itemShows.equals("抢号")) {
                binding.tvLeftPosition.setVisibility(View.VISIBLE);
                binding.tvLeftPosition.setText("" + Math.abs(Integer.parseInt(item.getLeftPosition())) + "A");
            } else {
                binding.tvLeftPosition.setVisibility(View.VISIBLE);
                binding.tvLeftPosition.setText("" + ((helper.getAdapterPosition() + 1) * 2 - 1));
            }

            if (itemShows.equals("抢号")) {
                binding.tvRightPosition.setVisibility(View.VISIBLE);
                binding.tvRightPosition.setText("" + Math.abs(Integer.parseInt(item.getRightPosition())) + "B");
            } else {
                binding.tvRightPosition.setVisibility(View.VISIBLE);
                binding.tvRightPosition.setText("" + ((helper.getAdapterPosition() + 1) * 2));
            }
        }
        String left1Name = item.getLeft1Name();
        String left2Name = item.getLeft2Name();
        String right1Name = item.getRight1Name();
        String right2Name = item.getRight2Name();
        String club1Name = item.getClub1Name();
        String club2Name = item.getClub2Name();
        String win1Name = item.getWin1Name();
        String win2Name = item.getWin2Name();
        String winClubName = item.getWinClubName();
        String tableNum = item.getTableNum();
        String day = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "dd日", item.getDay());
        String hour = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "HH:mm", item.getHour());

        if (TextUtils.isEmpty(tableNum)) {
            binding.llNoStart.setVisibility(View.GONE);
        } else {
            binding.llNoStart.setVisibility(View.VISIBLE);
            binding.tvTableNum.setText("" + tableNum + "台");
        }
        //团体
        if (itemType == 1) {
            binding.tvLeftClubName.setVisibility(View.GONE);
            binding.tvRightClubName.setVisibility(View.GONE);
            binding.tvWinClubName.setVisibility(View.GONE);

            //  null未开始,1已结束，2进行中，3左边已确认成绩 4右边已确认成绩 6双方都确认成绩 7团队左方填完对阵表 8团队右边填完对阵表 9，双方都填完 10，虚位待赛
            if (TextUtils.isEmpty(item.getStatus())
                    || item.getStatus().equals("2") ||
                    item.getStatus().equals("7") || item.getStatus().equals("8") || item.getStatus().equals("9") || item.getStatus().equals("10")
            ) {
                binding.tvWinTeamName.setText("虚位待赛");
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                binding.tvDay.setText("" + day);
                binding.tvHour.setText("" + hour);
                binding.tvScore.setVisibility(View.GONE);
                binding.tvWinClubName.setVisibility(View.GONE);

                if (TextUtils.isEmpty(club1Name) && TextUtils.isEmpty(club2Name)) {//两个都为空 展示虚伪待赛

                    binding.tvLeftName.setText("虚位待赛");
                    binding.tvRightName.setText("虚位待赛");
                } else if (!TextUtils.isEmpty(club1Name) && !TextUtils.isEmpty(club2Name)) {//两个都有人
                    binding.tvLeftName.setText("" + club1Name);
                    binding.tvRightName.setText("" + club2Name);

                } else {//其中一个有人
                    if (TextUtils.isEmpty(item.getTableNum())) {//如果球台为空  那么如果哪个为空  哪个就显示轮空
                        if (TextUtils.isEmpty(club1Name)) {
                            binding.tvLeftName.setText("轮空");
                            binding.tvLeftName.setVisibility(View.VISIBLE);
                            binding.tvLeftClubName.setVisibility(View.GONE);
                            binding.tvWinClubName.setVisibility(View.GONE);
                        } else {
                            binding.tvLeftName.setText("" + club1Name);
                            binding.tvWinTeamName.setText("" + item.getWinClubName());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                        }
                        if (TextUtils.isEmpty(club2Name)) {
                            binding.tvRightName.setText("轮空");
                            binding.tvRightName.setVisibility(View.VISIBLE);
                            binding.tvRightClubName.setVisibility(View.GONE);
                            binding.tvWinClubName.setVisibility(View.GONE);
                        } else {
                            binding.tvRightName.setText("" + club2Name);
                            binding.tvWinTeamName.setText("" + item.getWinClubName());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.tvTableNum.setText("" + tableNum + "台");
                        if (!TextUtils.isEmpty(club1Name)) {
                            binding.tvLeftName.setText("" + club1Name);
                        } else {
                            binding.tvLeftName.setText("虚位待赛");
                        }
                        if (!TextUtils.isEmpty(club2Name)) {
                            binding.tvRightName.setText("" + club2Name);
                        } else {
                            binding.tvRightName.setText("虚位待赛");
                        }
                    }

                }
                //进行中
                if (item.getStatus().equals("2")) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.VISIBLE);
                    /*判断分数  设置*/
                    if (item.getLeftCount() >= item.getRightCount()) {
                        binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                    } else {
                        binding.tvScore.setTextColor(Color.parseColor("#333333"));
                    }
                    binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());

                }

            } else {
                /*已结束*/
                binding.llNoStart.setVisibility(View.GONE);
                binding.tvScore.setVisibility(View.VISIBLE);
                binding.tvWinTeamName.setText("" + item.getWin1Name());
                /*判断分数  设置*/
                if (item.getLeftCount() >= item.getRightCount()) {
                    binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                } else {
                    binding.tvScore.setTextColor(Color.parseColor("#333333"));
                }
                binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                binding.tvWinClubName.setVisibility(View.GONE);
                binding.tvWinClubName.setText("" + item.getWinClubName());
                if (!TextUtils.isEmpty(club1Name)) {
                    binding.tvLeftName.setText("" + club1Name);
                    binding.tvLeftName.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(club2Name)) {
                    binding.tvRightName.setText("" + club2Name);
                    binding.tvRightName.setVisibility(View.VISIBLE);
                }
                binding.rlScore.setEnabled(false);

                if (TextUtils.isEmpty(item.getTableNum())) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(club1Name)) {

                        binding.tvLeftName.setText("轮空");
                        binding.tvLeftName.setVisibility(View.VISIBLE);
                        binding.tvLeftClubName.setVisibility(View.GONE);
                        binding.tvWinClubName.setVisibility(View.GONE);
                    }
                    if (TextUtils.isEmpty(club2Name)) {

                        binding.tvRightName.setText("轮空");
                        binding.tvRightName.setVisibility(View.VISIBLE);
                        binding.tvRightClubName.setVisibility(View.GONE);
                        binding.tvWinClubName.setVisibility(View.GONE);
                    }
                }
            }

        } else if (itemType == 2) {
            /*单打*/
            binding.tvWinClubName.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(item.getStatus())
                    || item.getStatus().equals("2") ||
                    item.getStatus().equals("7") || item.getStatus().equals("8") || item.getStatus().equals("9") || item.getStatus().equals("10")
            ) {
                binding.tvLeftClubName.setVisibility(View.VISIBLE);
                binding.tvRightClubName.setVisibility(View.VISIBLE);
                binding.tvScore.setVisibility(View.GONE);
                /*未进行*/
                binding.tvWinTeamName.setText("虚位待赛");
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                binding.tvDay.setText("" + day);
                binding.tvHour.setText("" + hour);
                binding.tvWinClubName.setVisibility(View.GONE);
                if (TextUtils.isEmpty(left1Name) && TextUtils.isEmpty(right1Name)) {//两个都为空 展示虚伪待赛

                    binding.tvLeftName.setText("虚位待赛");
                    binding.tvRightName.setText("虚位待赛");
                    binding.tvLeftClubName.setVisibility(View.GONE);
                    binding.tvRightClubName.setVisibility(View.GONE);
                    binding.tvWinClubName.setVisibility(View.GONE);
                } else if (!TextUtils.isEmpty(left1Name) && !TextUtils.isEmpty(right1Name)) {//两个都有人
                    binding.tvLeftName.setText("" + left1Name);
                    binding.tvRightName.setText("" + right1Name);
                    binding.tvLeftClubName.setText("" + club1Name);
                    binding.tvRightClubName.setText("" + club2Name);
                } else {//其中一个有人
                    if (TextUtils.isEmpty(item.getTableNum())) {//如果球台为空  那么如果哪个为空  哪个就显示轮空
                        if (TextUtils.isEmpty(left1Name)) {
                            binding.tvLeftName.setText("轮空");
                            binding.tvLeftName.setVisibility(View.VISIBLE);

                            binding.tvLeftClubName.setVisibility(View.GONE);
                            binding.tvWinTeamName.setText("" + item.getWin1Name());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                            binding.tvWinClubName.setText("" + item.getWinClubName());
                            binding.tvWinClubName.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvLeftName.setText("" + left1Name);
                            binding.tvLeftClubName.setText("" + club1Name);
                        }
                        if (TextUtils.isEmpty(right1Name)) {
                            binding.tvRightName.setText("轮空");
                            binding.tvRightName.setVisibility(View.VISIBLE);
                            binding.tvRightClubName.setVisibility(View.GONE);

                            binding.tvWinTeamName.setText("" + item.getWin1Name());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                            binding.tvWinClubName.setText("" + item.getWinClubName());
                            binding.tvWinClubName.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvRightName.setText("" + right1Name);
                            binding.tvRightClubName.setText("" + club2Name);
                        }
                    } else {
                        binding.tvTableNum.setText("" + tableNum + "台");
                        if (!TextUtils.isEmpty(left1Name)) {
                            binding.tvLeftName.setText("" + left1Name);
                            binding.tvLeftClubName.setText("" + club1Name);
                        } else {
                            binding.tvLeftName.setText("虚位待赛");
                            binding.tvLeftClubName.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(right1Name)) {
                            binding.tvRightName.setText("" + right1Name);
                            binding.tvRightClubName.setText("" + club2Name);
                        } else {
                            binding.tvRightName.setText("虚位待赛");
                            binding.tvRightClubName.setVisibility(View.GONE);

                        }
                    }

                }
                //进行中
                if (item.getStatus().equals("2")) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.VISIBLE);
                    /*判断分数  设置*/
                    if (item.getLeftCount() >= item.getRightCount()) {
                        binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                    } else {
                        binding.tvScore.setTextColor(Color.parseColor("#333333"));
                    }
                    binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());

                }

            } else {
                /*已结束*/
                binding.llNoStart.setVisibility(View.GONE);
                binding.tvScore.setVisibility(View.VISIBLE);
                binding.tvWinTeamName.setText("" + item.getWin1Name());
                binding.tvWinClubName.setText("" + item.getWinClubName());

                binding.tvWinClubName.setVisibility(View.VISIBLE);
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                /*判断分数  设置*/
                if (item.getLeftCount() >= item.getRightCount()) {
                    binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                } else {
                    binding.tvScore.setTextColor(Color.parseColor("#333333"));
                }
                binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());
                binding.rlScore.setEnabled(false);
                if (!TextUtils.isEmpty(left1Name)) {
                    binding.tvLeftName.setText("" + left1Name);
                    binding.tvLeftClubName.setText("" + club1Name);
                    binding.tvLeftName.setVisibility(View.VISIBLE);
                    binding.tvLeftClubName.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(right1Name)) {
                    binding.tvRightName.setText("" + right1Name);
                    binding.tvRightClubName.setText("" + club2Name);
                    binding.tvRightName.setVisibility(View.VISIBLE);
                    binding.tvRightClubName.setVisibility(View.VISIBLE);
                }


                if (TextUtils.isEmpty(item.getTableNum())) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(left1Name)) {

                        binding.tvLeftName.setText("轮空");
                        binding.tvLeftName.setVisibility(View.VISIBLE);
                        binding.tvLeftClubName.setVisibility(View.GONE);
                    }
                    if (TextUtils.isEmpty(right1Name)) {
                        binding.tvRightName.setText("" + club2Name);
                        binding.tvRightName.setVisibility(View.VISIBLE);

                        binding.tvRightName.setText("轮空");
                        binding.tvRightName.setVisibility(View.VISIBLE);
                        binding.tvRightClubName.setVisibility(View.GONE);
                    }
                }
            }

        } else {
            /*双打和混双*/
            /*left 1 left2  right 1 right 2*/

            if (TextUtils.isEmpty(item.getStatus())
                    || item.getStatus().equals("2") ||
                    item.getStatus().equals("7") || item.getStatus().equals("8") || item.getStatus().equals("9") || item.getStatus().equals("10")
            ) {
                binding.tvLeftClubName.setVisibility(View.VISIBLE);
                binding.tvRightClubName.setVisibility(View.VISIBLE);
                binding.tvScore.setVisibility(View.GONE);
                /*未进行*/
                binding.tvWinTeamName.setText("虚位待赛");
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                binding.tvDay.setText("" + day);
                binding.tvHour.setText("" + hour);
                binding.tvWinClubName.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(left1Name) && TextUtils.isEmpty(right1Name)) {//两个都为空 展示虚伪待赛

                    binding.tvLeftName.setText("虚位待赛");
                    binding.tvRightName.setText("虚位待赛");
                    binding.tvLeftClubName.setVisibility(View.GONE);
                    binding.tvRightClubName.setVisibility(View.GONE);
                    binding.tvWinClubName.setVisibility(View.GONE);

                } else if (!TextUtils.isEmpty(left1Name) && !TextUtils.isEmpty(right1Name)) {//两个都有人
                    binding.tvLeftName.setText("" + left1Name + "/" + left2Name);
                    binding.tvRightName.setText("" + right1Name + "/" + right2Name);
                    binding.tvLeftClubName.setText("" + club1Name);
                    binding.tvRightClubName.setText("" + club2Name);
                } else {//其中一个有人
                    if (TextUtils.isEmpty(item.getTableNum())) {//如果球台为空  那么如果哪个为空  哪个就显示轮空
                        if (TextUtils.isEmpty(left1Name)) {
                            binding.tvLeftName.setText("轮空");
                            binding.tvLeftClubName.setVisibility(View.GONE);
                            binding.tvLeftName.setVisibility(View.VISIBLE);
                            binding.tvWinTeamName.setText("" + right1Name + "/" + right2Name);
                            binding.tvWinClubName.setText("" + item.getWinClubName());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                            binding.tvWinClubName.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvLeftName.setText("" + left1Name + "/" + left2Name);
                            binding.tvLeftClubName.setText("" + club1Name);
                        }
                        if (TextUtils.isEmpty(right1Name)) {
                            binding.tvRightName.setText("轮空");
                            binding.tvRightClubName.setVisibility(View.GONE);
                            binding.tvRightName.setVisibility(View.VISIBLE);

                            binding.tvWinTeamName.setText("" + left1Name + "/" + left2Name);
                            binding.tvScore.setTextColor(Color.parseColor("#333333"));
                            binding.tvWinClubName.setText("" + item.getWinClubName());
                            binding.tvWinTeamName.setVisibility(View.VISIBLE);
                            binding.tvWinClubName.setVisibility(View.VISIBLE);

                        } else {
                            binding.tvRightName.setText("" + right1Name + "/" + right2Name);
                            binding.tvRightClubName.setText("" + club2Name);
                        }
                    } else {
                        binding.tvTableNum.setText("" + tableNum + "台");
                        if (!TextUtils.isEmpty(left1Name)) {

                            binding.tvLeftName.setText("" + left1Name + "/" + left2Name);
                            binding.tvLeftClubName.setText("" + club1Name);
                        } else {
                            binding.tvLeftName.setText("虚位待赛");
                            binding.tvLeftClubName.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(right1Name)) {

                            binding.tvRightName.setText("" + right1Name + "/" + right2Name);
                            binding.tvRightClubName.setText("" + club2Name);
                        } else {
                            binding.tvRightName.setText("虚位待赛");
                            binding.tvRightClubName.setVisibility(View.GONE);
                        }
                    }

                }
                //进行中
                if (item.getStatus().equals("2")) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.VISIBLE);
                    /*判断分数  设置*/
                    if (item.getLeftCount() >= item.getRightCount()) {
                        binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                    } else {
                        binding.tvScore.setTextColor(Color.parseColor("#333333"));
                    }
                    binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());

                }
            } else {
                /*已结束*/
                binding.llNoStart.setVisibility(View.GONE);
                binding.tvScore.setVisibility(View.VISIBLE);
                /*判断分数  设置*/
                if (item.getLeftCount() >= item.getRightCount()) {
                    binding.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                    binding.tvWinTeamName.setText("" + left1Name + "/" + left2Name);
                    binding.tvWinClubName.setText("" + club1Name);
                } else {
                    binding.tvWinTeamName.setText("" + right1Name + "/" + right2Name);
                    binding.tvScore.setTextColor(Color.parseColor("#333333"));
                    binding.tvWinClubName.setText("" + club2Name);
                }
                binding.tvWinClubName.setVisibility(View.VISIBLE);
                binding.tvWinTeamName.setVisibility(View.VISIBLE);
                binding.rlScore.setEnabled(false);

                binding.tvScore.setText("" + item.getLeftCount() + " : " + item.getRightCount());

                if (!TextUtils.isEmpty(left1Name)) {

                    binding.tvLeftName.setText("" + left1Name + "/" + left2Name);
                    binding.tvLeftClubName.setText("" + club1Name);
                    binding.tvLeftName.setVisibility(View.VISIBLE);
                    binding.tvLeftClubName.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(right1Name)) {

                    binding.tvRightName.setText("" + right1Name + "/" + right2Name);
                    binding.tvRightClubName.setText("" + club2Name);
                    binding.tvRightName.setVisibility(View.VISIBLE);
                    binding.tvRightClubName.setVisibility(View.VISIBLE);
                }



                if (TextUtils.isEmpty(item.getTableNum())) {
                    binding.llNoStart.setVisibility(View.GONE);
                    binding.tvScore.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(left1Name)) {

                        binding.tvLeftName.setText("轮空");
                        binding.tvLeftClubName.setVisibility(View.GONE);
                        binding.tvLeftName.setVisibility(View.VISIBLE);
                    }
                    if (TextUtils.isEmpty(right1Name)) {

                        binding.tvRightName.setText("轮空");
                        binding.tvRightName.setVisibility(View.VISIBLE);
                        binding.tvRightClubName.setVisibility(View.GONE);
                    }
                }
            }


        }

        binding.llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1 团体  2单打  34 双打
                if (itemType == 1) {
                    if (!TextUtils.isEmpty(club1Name)) {
                        myListener.OnMyListener(3, itemType, item, 1);
                    }
                } else if (itemType == 2) {
                    if (!TextUtils.isEmpty(left1Name)) {
                        myListener.OnMyListener(3, itemType, item, 1);
                    }
                } else {
                    if (!TextUtils.isEmpty(left1Name) && !TextUtils.isEmpty(left2Name)) {
                        myListener.OnMyListener(3, itemType, item, 1);
                    }
                }
            }
        });

        binding.llTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemType == 1) {
                    if (!TextUtils.isEmpty(club2Name)) {
                        myListener.OnMyListener(3, itemType, item, 2);

                    }
                } else if (itemType == 2) {
                    if (!TextUtils.isEmpty(right1Name)) {
                        myListener.OnMyListener(3, itemType, item, 2);

                    }
                } else {
                    if (!TextUtils.isEmpty(right1Name) && !TextUtils.isEmpty(right2Name)) {
                        myListener.OnMyListener(3, itemType, item, 2);
                    }
                }
            }
        });
        binding.rlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.OnMyListener(2, itemType, item, 0);
            }
        });
        binding.tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.OnMyListener(1, itemType, item, 0);
            }
        });

        binding.llThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemType == 1) {
                    if (!TextUtils.isEmpty(winClubName)) {
                        myListener.OnMyListener(3, itemType, item, 3);

                    }
                } else if (itemType == 2) {
                    if (!TextUtils.isEmpty(win1Name)) {
                        myListener.OnMyListener(3, itemType, item, 3);

                    }
                } else {
                    if (!TextUtils.isEmpty(win1Name) && !TextUtils.isEmpty(win2Name)) {
                        myListener.OnMyListener(3, itemType, item, 3);
                    }
                }
            }
        });

    }
    /**
     * 1、除雾
     *
     * 除雾按键有前风挡以及后风挡，按下对应按钮开启风挡玻璃的除雾功能。
     *
     * 2、暖风
     *
     * 只要将温度调高即可开启暖风。在空调加热时，应当关闭A/C按键。因为空调制冷开关只有制冷时才能使用得到
     * 压缩机，暖气直接由冷却液系统以及玻璃加热丝供给。
     *
     * 3、AUTO
     *
     * 当开启AUTO功能，空调进入智能调控阶段，不再需要人工进行设置。空调系统经由传感器信号进行判断，是否应
     * 当调高/低风量的大小，以及此时应当进行制冷/热的动作。
     * 4、内/外循环
     *
     * 内循环与外循环的区别在于：内循环的空气始终由车内提供，外循环则让外界空气进来换掉原有的车内空气。
     * 拥堵路段或空气质量差的环境使用内循环。高速时每隔一段时间要切换成外循环换换气，以防空气不新鲜而造成健康问题。
     *
     * 5、温区同步
     *
     * 开启温区同步功能后，只需要改变驾驶位的温度，其他的温区就会跟随着发生一样的变化。关闭此温区同步后，
     * 每个分区可以自行个性化的设置，满足不同位置乘员需要。
     */

}
