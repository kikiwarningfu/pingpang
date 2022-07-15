package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import heyong.intellectPinPang.adapter.game.RefereeExperienceAdapter;
import heyong.intellectPinPang.bean.competition.ThroughListBean;
import heyong.intellectPinPang.databinding.ActivityRefereeExperienceBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 制裁经历
 */
public class RefereeExperienceActivity extends BaseActivity<ActivityRefereeExperienceBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    RefereeExperienceAdapter refereeExperienceAdapter;
    private int type = 2;


    List<ThroughListBean> list = new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = RefereeExperienceActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_referee_experience;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
        mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
        mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
        mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
        type = 2;

        mViewModel.goThroughLiveData.observe(this, new Observer<ResponseBean<List<ThroughListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ThroughListBean>> responseBean) {
                List<ThroughListBean> data = responseBean.getData();
                if (data != null && data.size() > 0) {
                    int allCount = data.get(0).getAllCount();

                    if (pageNum == 1) {
                        list.clear();
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                    } else {
                        mBinding.swFresh.finishLoadmore();
                    }

                    if (list != null) {
                        list.addAll(data);
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        refereeExperienceAdapter.setNewData(list);
                    }
                    mBinding.tvAllChang.setText("共计：" + refereeExperienceAdapter.getData().size() + "场");

                } else {
                    mBinding.tvAllChang.setText("共计：" + 0 + "场");
                }
            }
        });
//
        refereeExperienceAdapter = new RefereeExperienceAdapter();
        mBinding.rvRefereeExperience.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        mBinding.rvRefereeExperience.setAdapter(refereeExperienceAdapter);
        mRefreshAnimHeader = new MyRefreshAnimHeader(RefereeExperienceActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);


        mBinding.rvRefereeExperience.setAdapter(refereeExperienceAdapter);
        refereeExperienceAdapter.setOnItemChildClickListener(this);
        refereeExperienceAdapter.setOnItemClickListener(this);
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

        getData();
    }

    public void getData() {
        mViewModel.goThrough("" + type);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_under_way://裁判员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.swFresh.setEnableLoadmore(true);
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                type = 2;
                mViewModel.goThrough("" + type);

                break;
            case R.id.ll_finished://裁判长
                if (AntiShakeUtils.isInvalidClick(v))
                    return;

                mBinding.swFresh.setEnableLoadmore(true);
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_gray_e5);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_blue);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#FFFFFF"));
                type = 1;
                mViewModel.goThrough("" + type);

                break;
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            long matchId = refereeExperienceAdapter.getData().get(position).getMatchId();
            String matchTitle = refereeExperienceAdapter.getData().get(position).getMathTitle();
            Intent intent = new Intent(RefereeExperienceActivity.this, GameDetailActivity.class);
            intent.putExtra(GameDetailActivity.MATCH_ID, "" + matchId);
            intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + matchTitle);
            startActivity(intent);
        }


    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        /*刷新数据 page=1*/
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }
}