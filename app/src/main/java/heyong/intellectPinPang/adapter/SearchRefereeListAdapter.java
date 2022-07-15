package heyong.intellectPinPang.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.elvishew.xlog.XLog;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.SearchRefreeListBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;

import heyong.intellectPinPang.databinding.ItemSearchRefereeCompetitionStartOneBinding;
import heyong.intellectPinPang.databinding.ItemSearchRefereeCompetitionStartTwoBinding;

//游客
public class SearchRefereeListAdapter extends BaseQuickAdapter<SearchRefreeListBean.ArrangesBean, BaseViewHolder> {
    private String matchStaus = "";
    private String localTableNum = "";

    public void setmatchStatus(String status) {
        if (TextUtils.isEmpty(status)) {
            matchStaus = "0";
        } else {
            this.matchStaus = status;
        }
    }

    public void setLocalTableNum(String tableNum) {
        this.localTableNum = tableNum;
    }

    public String getMatchStatus() {
        return matchStaus;
    }

    public SearchRefereeListAdapter() {
        super(0);
        MultiTypeDelegate<SearchRefreeListBean.ArrangesBean> multiTypeDelegate =
                new MultiTypeDelegate<SearchRefreeListBean.ArrangesBean>() {
                    @Override
                    protected int getItemType(SearchRefreeListBean.ArrangesBean o) {
                        return Integer.parseInt(o.getItemType());
                    }
                };
        multiTypeDelegate.registerItemType(0, R.layout.item_search_referee_competition_start_one);
        multiTypeDelegate.registerItemType(1, R.layout.item_search_referee_competition_start_two);

        setMultiTypeDelegate(multiTypeDelegate);
    }


    @Override
    protected void convert(BaseViewHolder helper, SearchRefreeListBean.ArrangesBean item) {

        switch (helper.getItemViewType()) {//开始   已结束
            case 0://单打
                String itemType = item.getItemType();

                ItemSearchRefereeCompetitionStartOneBinding binding = DataBindingUtil.bind(helper.getConvertView());
                if (item.getTitleType().equals("1")) {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    binding.cardView.setBackgroundResource(R.drawable.img_one_referee);
                    binding.llContent.setBackground(null);
                } else {
                    binding.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
                    binding.cardView.setBackgroundResource(0);
                    binding.llContent.setBackgroundResource(R.drawable.img_second_referee2);

                }
                if(item.getTableNum()==Integer.parseInt(localTableNum))
                {
                    binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_tiaotai_after);
                    binding.tvGameEndStatus.setTextColor(Color.parseColor("#FFFFFF"));
                }else
                {
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    binding.tvGameEndStatus.setBackgroundResource(R.drawable.shape_tiaotai_before);
                    binding.tvGameEndStatus.setTextColor(Color.parseColor("#4795ED"));
                }
                binding.tvTableNum.setText("" + item.getTableNum() + "台");
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


                //status返回2 进行中
                binding.tvBeginTime.setText("" + item.getBeginTime());
                break;
            case 1://双打
                ItemSearchRefereeCompetitionStartTwoBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
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
                binding2.tvLeftNameOne.setText("" + item.getPlayer1Name());
                binding2.tvLeftNameTwo.setText("" + item.getPlayer2Name());
                binding2.tvRightNameOne.setText("" + item.getPlayer3Name());
                binding2.tvRightNameTwo.setText("" + item.getPlayer4Name());
                binding2.tvBeginTime.setText("" + item.getBeginTime());
                binding2.tvLeftClubName.setText("" + item.getClub1Name());
                binding2.tvRightClubName.setText("" + item.getClub2Name());
                binding2.tvTableNum.setText("" + item.getTableNum() + "台");
                if(item.getTableNum()==Integer.parseInt(localTableNum))
                {
                    binding2.tvGameEndStatus.setBackgroundResource(R.drawable.shape_tiaotai_after);
                    binding2.tvGameEndStatus.setTextColor(Color.parseColor("#FFFFFF"));
                }else
                {
                    helper.addOnClickListener(R.id.tv_game_end_status);
                    binding2.tvGameEndStatus.setBackgroundResource(R.drawable.shape_tiaotai_before);
                    binding2.tvGameEndStatus.setTextColor(Color.parseColor("#4795ED"));
                }
                break;
//            case 2://待赛
//                ItemRefereeCompetitionDaisaiBinding binding3 = DataBindingUtil.bind(helper.getConvertView());
//                if (item.getTitleType().equals("1")) {
//                    binding3.tvPosition.setBackgroundColor(Color.parseColor("#ff4795ed"));
//                    binding3.cardView.setBackgroundResource(R.drawable.img_one_referee);
//                    binding3.llContent.setBackgroundResource(0);
//                } else {
//                    binding3.tvPosition.setBackgroundColor(Color.parseColor("#FFF26D4A"));
//                    binding3.cardView.setBackgroundResource(0);
//                    binding3.llContent.setBackgroundResource(R.drawable.img_second_referee2);
//
//                }
//                binding3.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
//                binding3.tvTitle.setText("" + item.getTitle());
//                binding3.tvBeginTime.setTextColor(Color.parseColor("#333333"));
//                binding3.tvBeginTime.setText("" + item.getBeginTime());
//
//
//
//                break;

        }
    }
}
