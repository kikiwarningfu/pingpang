package heyong.intellectPinPang.ui.club.fragment.child;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.club.ClubAdapter;
import heyong.intellectPinPang.bean.competition.ClubBeans;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.FragmentFreedomGroupBinding;
import heyong.intellectPinPang.event.CityEventSet;
import heyong.intellectPinPang.event.FreedomGroupEmptyEvent;
import heyong.intellectPinPang.event.FreedomGroupEvent;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.club.activity.ClubDetailActivity;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.widget.SpaceItemDecoration;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * 自由团体
 */
public class FreedomGroupFragment extends BaseFragment<FragmentFreedomGroupBinding, ClubViewModel> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    private SpaceItemDecoration mpopulardidLike;
    ClubAdapter clubAdapter;
    List<ClubBeans> clubBeans = new ArrayList<>();

    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG=AllClubFragment.class.getSimpleName();
    String textCode="1";
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<XlClubInfoListBean.ListBean> list=new ArrayList<>();


    public FreedomGroupFragment() {
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(viewModel!=null) {
            getData("" + textCode);
        }
    }

    private void getData(String s) {
        viewModel.getXlClubInfoList("" + pageNum, "" + pageSize,
                ""+viewModel.localTeamTitle.get(), ""+viewModel.localCity.get(), ""+textCode);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(viewModel!=null) {
            getData(""+textCode);
        }
    }
    public static FreedomGroupFragment newInstance() {
        Bundle args = new Bundle();
        FreedomGroupFragment fragment = new FreedomGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_freedom_group;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        clubAdapter = new ClubAdapter(getActivity());
        mRefreshAnimHeader = new MyRefreshAnimHeader(getActivity());
        if (binding.swFresh.isRefreshing()){
            binding.swFresh.finishRefresh();
        }
//        binding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        RxBus.getDefault().toObservable(FreedomGroupEvent.class).subscribe(tagEvents -> {
            viewModel.localTeamTitle.set(""+tagEvents.getEtClubName());
            pageNum=1;
            getData(""+textCode);
        });
        RxBus.getDefault().toObservable(FreedomGroupEmptyEvent.class).subscribe(tagEvents -> {
            viewModel.localTeamTitle.set(""+tagEvents.getEtClubName());
        });

        RxBus.getDefault().toObservable(CityEventSet.class).subscribe(tagEvents -> {
            Log.e(TAG, "initView: "+tagEvents.getName() );
            viewModel.localCity.set(""+tagEvents.getName());
            pageNum=1;
            getData(""+textCode);
        });

        binding.mRecyclerView.removeItemDecoration(mpopulardidLike);
        mpopulardidLike = new SpaceItemDecoration(20);
        binding.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.mRecyclerView.addItemDecoration(mpopulardidLike);

        binding.mRecyclerView.setAdapter(clubAdapter);
        clubAdapter.setOnItemChildClickListener(this);
        clubAdapter.setOnItemClickListener(this);
        binding.swFresh.setOnRefreshListener(this);
        binding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(haveMore){
                    pageNum++;
                    getData(textCode);
                }else {
                    binding.swFresh.finishLoadmore();
                }
            }
        });
        viewModel.getXlClubInfoListLiveData.observe(this, new Observer<ResponseBean<XlClubInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubInfoListBean> loginBeanResponseBean) {
//                if(loginBeanResponseBean.isSuccess()) {
                    if(loginBeanResponseBean.getErrorCode().equals("200")){

                        if(pageNum == 1){
                        list.clear();
                        if(binding.swFresh!=null)
                        {
                            binding.swFresh.finishRefresh();
                        }
                    }else {
                        binding.swFresh.finishLoadmore();
                    }
                    if (list != null){
                        list.addAll(loginBeanResponseBean.getData().getList());
                        if(list != null && list.size() < pageSize){
                            haveMore = false;
                            binding.swFresh.setEnableLoadmore(false);
                        }
                        clubAdapter.setNewData(list);

                    }
                }else
                {

                }
            }
        });
        getData(textCode);
//        RxBus.getDefault().toObservable()
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            long id = clubAdapter.getData().get(position).getId();
            Intent intent = new Intent(getActivity(), ClubDetailActivity.class);
            intent.putExtra(ClubDetailActivity.CLUB_ID, "" + id);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        /*刷新数据 page=1*/

        binding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData(textCode);
    }
}
