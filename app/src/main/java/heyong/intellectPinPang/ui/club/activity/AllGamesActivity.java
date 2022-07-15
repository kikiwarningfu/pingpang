package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import heyong.intellectPinPang.adapter.game.AllGameAdapter;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.databinding.ActivityAllGamesBinding;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.club.fragment.child.AllClubFragment;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;


/**
 * 所有场次比赛   运动员比赛经历 跳转的界面  需要掉接口 加分页
 */
public class AllGamesActivity extends BaseActivity <ActivityAllGamesBinding, ClubViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {

    AllGameAdapter allGameAdapter;

    List<XlClubInfoListBean.ListBean> list=new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = AllClubFragment.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_all_games;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        allGameAdapter=new AllGameAdapter();
        mBinding.rvAllGame.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mBinding.rvAllGame.setAdapter(allGameAdapter);

        mRefreshAnimHeader = new MyRefreshAnimHeader(AllGamesActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        allGameAdapter=new AllGameAdapter();
        mBinding.rvAllGame.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mBinding.rvAllGame.setAdapter(allGameAdapter);
        allGameAdapter.setOnItemChildClickListener(this);
        allGameAdapter.setOnItemClickListener(this);
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
        mViewModel.getXlClubInfoListLiveData.observe(this, new Observer<ResponseBean<XlClubInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubInfoListBean> loginBeanResponseBean) {
                if(loginBeanResponseBean.getErrorCode().equals("200")) {

                        if (pageNum == 1) {
                            list.clear();
                            if (mBinding.swFresh != null) {
                                mBinding.swFresh.finishRefresh();
                            }
                        } else {
                            mBinding.swFresh.finishLoadmore();
                        }
                        if (list != null) {
                            list.addAll(loginBeanResponseBean.getData().getList());
                            if (list != null && list.size() < pageSize) {
                                haveMore = false;
                                mBinding.swFresh.setEnableLoadmore(false);
                            }
                            allGameAdapter.setNewData(list);
                        }

                }else
                {
                    toast(""+loginBeanResponseBean.getMessage());
                }
            }
        });
        getData();
    }


    private void getData( ) {
        mViewModel.getXlClubInfoList("" + pageNum, "" + pageSize,
                ""+mViewModel.localTeamTitle.get(), "" + mViewModel.localCity.get(), "");
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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