package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamAndClubAdapter;
import heyong.intellectPinPang.databinding.ActivityClubRegistrationChanncelBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

//俱乐部（管理人员）报名通道
public class ClubRegistrationChanncelActivity extends BaseActivity<ActivityClubRegistrationChanncelBinding, BaseViewModel> implements View.OnClickListener {
    private int type = 0;
    CommonPopupWindow mMovieTicketWindow;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_club_registration_channcel;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        mBinding.llTextSelect.setText("请选择");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_text_container://选择俱乐部和团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopMatchForm();
                break;
            case R.id.tv_commit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;

                if (type == 0) {
                    toast("请选择报名方式");
                    return;
                } else if (type == 1) {
                    //个人报名
//                    goActivity(SelectionsOfEventActivity.class);
                } else if (type == 2) {
                    //俱乐部团队报名

                    SpannableString spannableString = new SpannableString("您已成功向俱乐部提交比赛报名申请，注意\n关注  我的-消息  界面查看报名结果");
//                spannableString.setSpan(new AbsoluteSizeSpan(60), 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 21, spannableString.length() - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#ffff00")), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(ClubRegistrationChanncelActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("去认证")
                            .setBtnCancleHint(false)
//                        .setSureListener(v1 ->

//                        )
                            .show();
                }


                break;
        }
    }

    public void showPopMatchForm() {
        mMovieTicketWindow = new CommonPopupWindow.Builder(ClubRegistrationChanncelActivity.this)
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
                        rvTeamAndDialog.setLayoutManager(new LinearLayoutManager(ClubRegistrationChanncelActivity.this, RecyclerView.VERTICAL, false));
//                        teamAndClubAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
                        teamAndClubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    mMovieTicketWindow.dismiss();
                                    mBinding.llTextSelect.setText("" + teamAndClubAdapter.getData().get(position));
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