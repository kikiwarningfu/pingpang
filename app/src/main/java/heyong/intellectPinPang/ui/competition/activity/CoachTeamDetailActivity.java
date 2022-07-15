package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CoachTeamAdapter;
import heyong.intellectPinPang.bean.competition.CoachMatchDtaBean;
import heyong.intellectPinPang.databinding.ActivityCoachTeamDetailBinding;
import heyong.intellectPinPang.live.activity.ReferenceVisitorMatchActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 教练员带队详情界面UI
 */
public class CoachTeamDetailActivity extends BaseActivity<ActivityCoachTeamDetailBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    public static final String MATCH_ID = "matchId";
    private String matchId = "";
    private int projectType = 1;//团体 2单打  3双打      1团体2单打3双打,4混双
    private int sexType = 1;//1男子 2女子 3 混合  4混双
    private int status = 1;//1 进行中  2已结束
    CoachTeamAdapter coachTeamAdapter;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = CoachTeamDetailActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<CoachMatchDtaBean> list = new ArrayList<>();

    boolean nanziCheck = false;
    boolean nvziCheck = false;
    boolean hunheCheck = false;
    boolean hunShuangCheck = false;

    boolean tuantiCheck = false;
    boolean dandaCheck = false;
    boolean shuangdaCheck = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_coach_team_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        matchId = getIntent().getStringExtra(MATCH_ID);

        coachTeamAdapter = new CoachTeamAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(CoachTeamDetailActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
        getData();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvCoachDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvCoachDetail.setAdapter(coachTeamAdapter);
        coachTeamAdapter.setOnItemChildClickListener(this);
        coachTeamAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    getData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mViewModel.getCoachMatchDtaLiveData.observe(this, new Observer<ResponseBean<List<CoachMatchDtaBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<CoachMatchDtaBean>> loginBeanResponseBean) {
//                if (loginBeanResponseBean.isSuccess()) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

                    if (pageNum == 1) {
                        list.clear();
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                    } else {
                        mBinding.swFresh.finishLoadmore();
                    }
                    if (loginBeanResponseBean.getData() != null) {
                        list.addAll(loginBeanResponseBean.getData());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        coachTeamAdapter.setNewData(list);
                        Log.e(TAG, "onChanged: " + coachTeamAdapter.getData().size());
                    }
                }
                if (coachTeamAdapter.getData().size() > 0) {
                    mBinding.llEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llEmptyView.setVisibility(View.VISIBLE);
                }
                Log.e(TAG, "initView: size===" + coachTeamAdapter.getData().size());
            }

        });
    }

    private void getData() {
        if (projectType == 566) {
            if ((tuantiCheck || dandaCheck || shuangdaCheck) && (nanziCheck || nvziCheck || hunheCheck)) {
                Log.e(TAG, "getData: tuantiCheck===" + tuantiCheck + "dandaCheck===" + dandaCheck + "shuangdaCheck===" + shuangdaCheck + "\n"
                        + "nanziCheck====" + nanziCheck + "nvziCheck===" + nvziCheck + "hunheCheck====" + hunheCheck);

                if (tuantiCheck) {
                    projectType = 1;
                }
                if (dandaCheck) {
                    projectType = 2;
                }
                if (shuangdaCheck) {
                    projectType = 3;
                }
//{"matchId":"560467575248607872","projectType":"","sexType":"1","status":"1"}
                mViewModel.getCoachMatchDta("" + matchId, "" + projectType, "" + sexType, "" + status);
            } else {
                Log.e(TAG, "okhttp getData: 不请求数据");
                sexType = -1;
            }
        } else {
            if (sexType != -1) {
                Log.e(TAG, "okjttp getData: " + projectType);
                mViewModel.getCoachMatchDta("" + matchId, "" + projectType, "" + sexType, "" + status);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_under_way://进行中
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                status = 1;
                getData();

                break;
            case R.id.ll_finished://已结束
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_gray_e5);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_blue);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#FFFFFF"));
                status = 2;
                getData();

                break;
            case R.id.tv_sex_type_nan://男
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSexTypeNan.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvSexTypeNv.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunhe.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNan.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvSexTypeNv.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunhe.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                nanziCheck = true;
                nvziCheck = false;
                hunheCheck = false;
                hunShuangCheck = false;
                sexType = 1;
                getData();


                break;
            case R.id.tv_sex_type_nv://女
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSexTypeNan.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNv.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvSexTypeHunhe.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNan.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeNv.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvSexTypeHunhe.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                nanziCheck = false;
                nvziCheck = true;
                hunheCheck = false;
                hunShuangCheck = false;
                sexType = 2;
                getData();


                break;
            case R.id.tv_sex_type_hunhe:///混合
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSexTypeNan.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNv.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunhe.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNan.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeNv.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunhe.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                nanziCheck = false;
                nvziCheck = false;
                hunheCheck = true;
                hunShuangCheck = false;
                sexType = 3;
                getData();


                break;
            case R.id.tv_sex_type_hunshuang://混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSexTypeNan.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNv.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunhe.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                sexType = 4;
                mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvDanda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvSexTypeNan.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeNv.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunhe.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF4795ED"));
                projectType = 4;
                mBinding.tvTuanti.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvDanda.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvShuangda.setTextColor(Color.parseColor("#FF666666"));
                nanziCheck = false;
                nvziCheck = false;
                hunheCheck = false;
                hunShuangCheck = true;
                getData();
                break;
            case R.id.tv_tuanti://团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (projectType == 4) {
                    mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                    mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                    projectType = 566;
                } else {
                    projectType = 1;
                }
                mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvDanda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvTuanti.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvDanda.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvShuangda.setTextColor(Color.parseColor("#FF666666"));
                Log.e(TAG, "onClick: projectType===" + projectType);
                tuantiCheck = true;
                dandaCheck = false;
                shuangdaCheck = false;
                getData();


                break;
            case R.id.tv_danda://2单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (projectType == 4) {
                    mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                    mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                    projectType = 566;

                } else {
                    projectType = 2;

                }
                tuantiCheck = false;
                dandaCheck = true;
                shuangdaCheck = false;
                mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvDanda.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvTuanti.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvDanda.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvShuangda.setTextColor(Color.parseColor("#FF666666"));
                Log.e(TAG, "onClick: projectType===" + projectType);
                getData();
                break;
            case R.id.tv_shuangda://3 双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (projectType == 4) {
                    mBinding.tvSexTypeHunshuang.setTextColor(Color.parseColor("#FF666666"));
                    mBinding.tvSexTypeHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                    projectType = 566;
                } else {
                    projectType = 3;
                }
                mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvDanda.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
                mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_group_solid_stroke);
                mBinding.tvTuanti.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvDanda.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvShuangda.setTextColor(Color.parseColor("#FF4795ED"));
                tuantiCheck = false;
                dandaCheck = false;
                shuangdaCheck = true;
                Log.e(TAG, "onClick: projectType===" + projectType);
                getData();
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            CoachMatchDtaBean coachMatchDtaBean = coachTeamAdapter.getData().get(position);
            ReferenceVisitorMatchActivity.goMatchActivity(CoachTeamDetailActivity.this,
                    "" + coachMatchDtaBean.getTableNum(), "" + matchId, "" + coachMatchDtaBean.getId(), "2");
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            CoachMatchDtaBean coachMatchDtaBean = coachTeamAdapter.getData().get(position);
            ReferenceVisitorMatchActivity.goMatchActivity(CoachTeamDetailActivity.this,
                    "" + coachMatchDtaBean.getTableNum(), "" + matchId, "" + coachMatchDtaBean.getId(), "2");
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }
}