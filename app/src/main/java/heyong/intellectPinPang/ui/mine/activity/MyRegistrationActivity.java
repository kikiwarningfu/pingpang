package heyong.intellectPinPang.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.MyRegistrationAdapter;
import heyong.intellectPinPang.bean.competition.EnListBean;
import heyong.intellectPinPang.databinding.ActivityMyRegistrationBinding;
import heyong.intellectPinPang.ui.competition.activity.EventDetailPaiedActivity;
import heyong.intellectPinPang.ui.competition.activity.EventDetailToPayActivity;
import heyong.intellectPinPang.ui.competition.activity.OutRaceOfDetailActivity;
import heyong.intellectPinPang.ui.competition.activity.SelectionsOfEventActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 我的报名
 */
public class MyRegistrationActivity extends BaseActivity<ActivityMyRegistrationBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    MyRegistrationAdapter myEventAdapter;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = MyRegistrationActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<EnListBean.ListBean> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_registration;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        getData();
        myEventAdapter = new MyRegistrationAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyRegistrationActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvMineEvent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvMineEvent.setAdapter(myEventAdapter);
        myEventAdapter.setOnItemChildClickListener(this);
        myEventAdapter.setOnItemClickListener(this);
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
        mViewModel.myEnlistLiveData.observe(this, new Observer<ResponseBean<EnListBean>>() {
            @Override
            public void onChanged(ResponseBean<EnListBean> loginBeanResponseBean) {
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

                    if (list != null) {

                        list.addAll(loginBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        myEventAdapter.setNewData(list);
                        Log.e(TAG, "onChanged: " + myEventAdapter.getData().size());
                    }
                }
                if (myEventAdapter.getData().size() > 0) {
                    mBinding.llEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llEmptyView.setVisibility(View.VISIBLE);
                }
                Log.e(TAG, "initView: size===" + myEventAdapter.getData().size());
            }
        });

        mViewModel.deleteXlEnrollMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("删除订单成功");
                    pageNum = 1;
                    getData();
                } else {
                    toast("删除失败");
                }
            }
        });

        mViewModel.signOutMatchOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("退赛成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
    }

    private void getData() {
        mViewModel.myEnlist("" + pageNum, "" + pageSize);

    }

    @Override
    public void onClick(View v) {


    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            EnListBean.ListBean enListBean = myEventAdapter.getData().get(position);
            long baoBiaoId = enListBean.getId();
            long matchId = enListBean.getMatchId();
            switch (view.getId()) {
                case R.id.tv_submit://去提交  修改或者去提交
                    //enrollTypet
                    int enrollTypet = enListBean.getEnrollTypet();
                    if (enrollTypet == 1)//个人
                    {
                        Intent intent = new Intent(MyRegistrationActivity.this, SelectionsOfEventActivity.class);
                        intent.putExtra(SelectionsOfEventActivity.ORDER_ID, "" + baoBiaoId);
                        intent.putExtra(SelectionsOfEventActivity.MATCH_ID, "" + matchId);
                        startActivity(intent);
                    } else if (enrollTypet == 2)//俱乐部
                    {
                        Intent intent = new Intent(MyRegistrationActivity.this, EventDetailToPayActivity.class);
                        intent.putExtra(EventDetailToPayActivity.ORDER_ID, "" + baoBiaoId);
                        intent.putExtra(EventDetailToPayActivity.MATCH_ID, "" + matchId);
                        startActivity(intent);
                    } else {
                        toast("裁判员订单 敬请期待");
                    }
                    break;
                case R.id.iv_delete://删除
                    String matchTitle = enListBean.getMatchTitle();
                    new MessageDialogBuilder(MyRegistrationActivity.this)
                            .setMessage("您还未提交【" + matchTitle + "】\n" +
                                    "删除后，将不再保存报名表")
                            .setTvTitle("确定要删除报名吗？")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteEnrollMatch(baoBiaoId);
                                }
                            })
                            .show();


                    break;
                case R.id.tv_sign_up_detail://报名详情
                    Log.e(TAG, "onItemChildClick: " + baoBiaoId);
                    Intent intent1 = new Intent(MyRegistrationActivity.this, EventDetailPaiedActivity.class);
                    intent1.putExtra(EventDetailPaiedActivity.BAOMING_ID, "" + baoBiaoId);
                    intent1.putExtra(EventDetailPaiedActivity.MATCH_ID, "" + matchId);
                    startActivity(intent1);

                    break;
                case R.id.tv_tuisai://我要退赛  掉 我要退赛的接口
                    new MessageDialogBuilder(MyRegistrationActivity.this)
                            .setMessage("")
                            .setTvTitle("是否选择退赛?")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mViewModel.signOutMatchOrder("" + baoBiaoId);
                                }
                            })
                            .show();


                    break;
                case R.id.tv_tuisai_detail://退赛详情

                    /*update time  调用订单详情的接口  再写个接口*/
                    Intent intent = new Intent();
                    intent.setClass(MyRegistrationActivity.this, OutRaceOfDetailActivity.class);
                    intent.putExtra(OutRaceOfDetailActivity.ORDERID, "" + baoBiaoId);
                    startActivity(intent);

                    break;
            }
        }
    }

    /**
     * 删除报名
     *
     * @param id
     */
    private void deleteEnrollMatch(long id) {
        mViewModel.deleteXlEnrollMatch("" + id);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }
}