package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamAndClubAdapter;
import heyong.intellectPinPang.databinding.ActivityClubAndTeamRegisterChannelBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogTitleBuilder;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 俱乐部/团体报道
 */
public class ClubAndTeamRegisterChannelActivity extends BaseActivity<ActivityClubAndTeamRegisterChannelBinding, BaseViewModel> implements View.OnClickListener {
    private CommonPopupWindow mMovieTicketWindow;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_club_and_team_register_channel;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_club_and_team:
//                showPopMatchForm();

                break;
            case R.id.tv_next_step:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogTitleBuilder(ClubAndTeamRegisterChannelActivity.this)
                        .setMessage("您所选择的俱乐部已经进行过一次报名\n" +
                                "是否进行新一次报名？\n"
                        )
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showClubJoinError();
                            }
                        })
                        .show();
                break;
        }
    }

    private void showClubJoinError() {
        new MessageDialogTitleBuilder(ClubAndTeamRegisterChannelActivity.this)
                .setMessage("您所选择的俱乐部已经由其他教练员\n" +
                        "进行报名，请联系教练员“彭于晏”\n"+
                        "进行修改报名\n"
                )
                .setTvCancel("取消")
                .setBtnCancleHint(true)
                .setTvSure("确定")
                .setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    public void showPopMatchForm() {
        mMovieTicketWindow = new CommonPopupWindow.Builder(ClubAndTeamRegisterChannelActivity.this)
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
                        rvTeamAndDialog.setLayoutManager(new LinearLayoutManager(ClubAndTeamRegisterChannelActivity.this, RecyclerView.VERTICAL, false));
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