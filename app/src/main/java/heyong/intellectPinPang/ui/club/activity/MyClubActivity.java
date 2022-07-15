package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import heyong.intellectPinPang.adapter.game.MyClubAdapter;
import heyong.intellectPinPang.bean.competition.MyClubListBean;
import heyong.intellectPinPang.databinding.ActivityMyClubBinding;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 我的俱乐部  status  //1待审核2审核通过3审核未通过
 */
public class MyClubActivity extends BaseActivity<ActivityMyClubBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {

    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = MyClubActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    MyClubAdapter myClubAdapter;
    List<MyClubListBean.ListBean> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_club;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        myClubAdapter = new MyClubAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyClubActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvCoachDetail.setAdapter(myClubAdapter);
        mBinding.rvCoachDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        myClubAdapter.setOnItemChildClickListener(this);
        myClubAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.getMyClub("" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mViewModel.getMyClubLiveData.observe(this, new Observer<ResponseBean<MyClubListBean>>() {
            @Override
            public void onChanged(ResponseBean<MyClubListBean> loginBeanResponseBean) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

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
                        myClubAdapter.setNewData(list);
                    }

                }


                if (myClubAdapter.getData().size() > 0) {
                    mBinding.llEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llEmptyView.setVisibility(View.VISIBLE);
                    mBinding.tvContent.setText("您还未加入任何俱乐部");
                }
            }
        });
        mViewModel.getMyClub("" + pageNum, "" + pageSize);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            MyClubListBean.ListBean listBean = myClubAdapter.getData().get(position);
            String status = listBean.getStatus();
            switch (status) {
                case "1"://审核中
                    toast("您的申请未通过");
                    break;
                case "2"://已加入
                    long id = listBean.getId();
                    Intent intent = new Intent(MyClubActivity.this, ClubDetailActivity.class);
                    intent.putExtra(ClubDetailActivity.CLUB_ID, "" + id);
                    startActivity(intent);
                    break;
            }
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        mViewModel.getMyClub("" + pageNum, "" + pageSize);

    }
}