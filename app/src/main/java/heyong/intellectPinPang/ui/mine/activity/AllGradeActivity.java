package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.AllGradeAdapter;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.databinding.ActivityAllGradeBinding;
import heyong.intellectPinPang.ui.club.activity.MemberShipDetailsActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 全部成绩界面UI   所有成绩
 */
public class AllGradeActivity extends BaseActivity<ActivityAllGradeBinding, MineViewModel> implements View.OnClickListener {
    AllGradeAdapter allGradeAdapter;
    public static final String TAG = AllGradeActivity.class.getSimpleName();
    public static final String RANKING = "ranking";
    private String strRanking = "";
    public static final String ROLE = "role";
    private String strRole = "";
    public static final String ITEM = "item";
    private String strItem = "";
    int socreType = 1;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_all_grade;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strRanking = getIntent().getStringExtra(RANKING);
        strRole = getIntent().getStringExtra(ROLE);
        strItem = getIntent().getStringExtra(ITEM);
        Log.e(TAG, "okhttp initView: " + strItem);
        if (strItem.equals("2")) {
            //单打
            allGradeAdapter = new AllGradeAdapter(2);
        } else {
            allGradeAdapter = new AllGradeAdapter(1);
        }
        mViewModel.awardsMatchList("" + strRole, "" + socreType, "" + strRanking, "" + strItem);
        //判断是否是单打  如果是单打 隐藏 对手的那个东西
        mBinding.rvAwardWinningScore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvAwardWinningScore.setAdapter(allGradeAdapter);
        allGradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    long arrangeId = allGradeAdapter.getData().get(position).getArrangeId();
                    long userId = allGradeAdapter.getData().get(position).getUserId();
                    switch (view.getId()) {
                        case R.id.tv_duishou_name_danda://对手姓名  单打
                            if (userId != 0) {
                                MemberShipDetailsActivity.goActivity(AllGradeActivity.this, "" + userId, 1);
                            }

                            break;
                        case R.id.tv_score:
                            if (arrangeId != 0) {
                                if (strItem.equals("1")) {
                                    Intent intent = new Intent();
                                    intent.setClass(AllGradeActivity.this, TeamResultsScoreOfficialActivity.class);
                                    intent.putExtra("ids", "" + arrangeId);
                                    startActivity(intent);
                                } else if (strItem.equals("2")) {
                                    //单打
                                    Intent intent2 = new Intent();
                                    intent2.setClass(AllGradeActivity.this, SinglesDetailScoreOfficialActivity.class);
                                    intent2.putExtra("ids", "" + arrangeId);
                                    startActivity(intent2);
                                } else {
                                    //双打
                                    Intent intent3 = new Intent();
                                    intent3.setClass(AllGradeActivity.this, DoubleDetailScoreOfficialActivity.class);
                                    intent3.putExtra("ids", "" + arrangeId);
                                    startActivity(intent3);
                                }
                            }


                            break;
                    }
                }
            }
        });
        mViewModel.awardsMatchListLiveData.observe(this, new Observer<ResponseBean<List<AwardsMathListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<AwardsMathListBean>> responseBean) {
                if (responseBean.getData() != null && responseBean.getData().size() > 0) {
                    List<AwardsMathListBean> data = responseBean.getData();
                    if (responseBean.getData().size() > 0) {
                        if (strItem.equals("1")) {
                            //1 团体
                            mBinding.tvMatchTitle.setText("" + responseBean.getData().get(0).getMathTitle() + "团体");

                        } else if (strItem.equals("2")) {
                            mBinding.tvMatchTitle.setText("" + responseBean.getData().get(0).getMathTitle() + "单打");

                        } else if (strItem.equals("3")) {
                            mBinding.tvMatchTitle.setText("" + responseBean.getData().get(0).getMathTitle() + "双打");
                        } else {
                            mBinding.tvMatchTitle.setText("" + responseBean.getData().get(0).getMathTitle() + "混双");
                        }
                    }
                    allGradeAdapter.setNewData(data);
                } else {
                    if (!responseBean.getErrorCode().equals("200")) {
                        toast("" + responseBean.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();

                break;
        }

    }
}