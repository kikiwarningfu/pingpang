package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamAndClubAdapter;
import heyong.intellectPinPang.bean.competition.PersonalClubBean;
import heyong.intellectPinPang.databinding.ActivitySportsPersonalChannelBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 运动员个人报名通道
 */
public class SportsPersonalChannelActivity extends BaseActivity<ActivitySportsPersonalChannelBinding, CompetitionViewModel> implements View.OnClickListener {
    public static final String MATCH_ID = "match_id";
    CommonPopupWindow mMovieTicketWindow;
    public static final String MATCH_TITLE = "match_title";
    private String strMatchTitle = "";
    private String matchId = "";
    private List<String> newList = new ArrayList<>();
    List<PersonalClubBean.DataBean> data = new ArrayList<>();

    private String teamTitleId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sports_personal_channel;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {


        mBinding.setListener(this);
        matchId = getIntent().getStringExtra(MATCH_ID);
        strMatchTitle = getIntent().getStringExtra(MATCH_TITLE);
        mBinding.tvMatchTitle.setText("" + strMatchTitle);
//        mBinding.topbar.setCenterText(""+strMatchTitle);
        mBinding.llTextSelect.setText("请选择");


        showLoading();
        mViewModel.personOnClub("" + matchId);

        mViewModel.personOnClubLiveData.observe(this, new Observer<ResponseBean<PersonalClubBean>>() {
            @Override
            public void onChanged(ResponseBean<PersonalClubBean> responseBean) {
                dismissLoading();
                newList.clear();
                data = responseBean.getData().getData();

            }
        });

        mViewModel.getClubLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        SpannableString spannableString = new SpannableString("您已成功向俱乐部提交比赛报名申请，注意\n关注  我的-消息  界面查看报名结果");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 21, spannableString.length() - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(SportsPersonalChannelActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                    //您已将报名提交到俱乐部


                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_bg_container://选择俱乐部和团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*掉接口*/
                if (data != null && data.size() > 0) {
                    showPopMatchForm();
                }

                break;
            case R.id.tv_commit://提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(teamTitleId)) {
                    new MessageDialogBuilder(SportsPersonalChannelActivity.this)
                            .setMessage("")
                            .setTvTitle("您没有符合条件的俱乐部，请加入一个俱乐部成为运动员，或走其他通道报名")
                            .setTvSure("确定")
                            .setTvCancle("取消")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                } else {
                    new MessageDialogBuilder(SportsPersonalChannelActivity.this)
                            .setMessage("")
                            .setTvTitle("是否提交到俱乐部？")
                            .setTvSure("确定")
                            .setTvCancle("取消")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showLoading();
                                    //俱乐部团队报名
                                    mViewModel.getClub(matchId, teamTitleId);
                                }
                            })
                            .show();
                }
                break;
        }
    }

    public void showPopMatchForm() {
        mMovieTicketWindow = new CommonPopupWindow.Builder(SportsPersonalChannelActivity.this)
                .setView(R.layout.pop_select_and_team)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        RecyclerView rvTeamAndDialog = view.findViewById(R.id.rv_club_and_team);
                        TeamAndClubAdapter teamAndClubAdapter = new TeamAndClubAdapter();
                        rvTeamAndDialog.setAdapter(teamAndClubAdapter);
                        rvTeamAndDialog.setLayoutManager(new LinearLayoutManager(SportsPersonalChannelActivity.this, RecyclerView.VERTICAL, false));
                        teamAndClubAdapter.setNewData(data);
                        teamAndClubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    mMovieTicketWindow.dismiss();
                                    mBinding.llTextSelect.setText("" + teamAndClubAdapter.getData().get(position).getTeamTitle());
                                    teamTitleId = "" + teamAndClubAdapter.getData().get(position).getId();
                                    Log.e("okhttp", "okhttponItemClick: " + teamTitleId);
                                }
                            }
                        });
                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(mBinding.llTextContainer);
        }
    }


}