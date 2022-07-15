package heyong.intellectPinPang.ui.homepage.fragment.newlive;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.live.ToBuyChildAdapter;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.FragmentNewHuifangBinding;
import heyong.intellectPinPang.event.AllLiveEvent;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.widget.SpaceItemDecoration;

/**
 * 新的回放界面
 */
public class NewToBuyFragment extends BaseFragment<FragmentNewHuifangBinding, BaseViewModel>
        implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, OnRefreshListener {
    private ToBuyChildAdapter allZhiBoAdapter;
    List<LiveListBean.ListBean> list = new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = NewToBuyFragment.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    private SpaceItemDecoration mpopulardidLike;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_new_huifang;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding.setListener(this);
        allZhiBoAdapter = new ToBuyChildAdapter(getActivity());
        binding.setListener(this);
        mRefreshAnimHeader = new MyRefreshAnimHeader(getActivity());
        if (binding.swFresh.isRefreshing()) {
            binding.swFresh.finishRefresh();
        }
//        binding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        binding.mRecyclerView.removeItemDecoration(mpopulardidLike);
        mpopulardidLike = new SpaceItemDecoration(20);
        binding.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.mRecyclerView.addItemDecoration(mpopulardidLike);
        binding.mRecyclerView.setAdapter(allZhiBoAdapter);


        List<LiveListBean.ListBean> listBeans=new ArrayList<>();
        listBeans.add(new LiveListBean.ListBean());
        listBeans.add(new LiveListBean.ListBean());
        listBeans.add(new LiveListBean.ListBean());
        listBeans.add(new LiveListBean.ListBean());
        listBeans.add(new LiveListBean.ListBean());
        allZhiBoAdapter.setNewData(listBeans);

        allZhiBoAdapter.setOnItemChildClickListener(this);
        allZhiBoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                LiveListBean.ListBean listBean = allZhiBoAdapter.getData().get(position);
//                AliPlayVideoActivity.goAliPlayVideoActivity(getActivity(), ""+listBean.getRequestId(),
//                        "ZHIBO");
            }
        });

        binding.swFresh.setOnRefreshListener(this);

        binding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    getData();

                } else {
                    binding.swFresh.finishLoadmore();
                }
            }
        });
//        viewModel.liveLuBOMatchListLiveDataLiveData.observe(this, new Observer<ResponseBean<LiveListBean>>() {
//            @Override
//            public void onChanged(ResponseBean<LiveListBean> loginBeanResponseBean) {
//                if (loginBeanResponseBean.getErrorCode().equals("200")) {
//                    if (pageNum == 1) {
//                        list.clear();
//                        if (binding.swFresh != null) {
//                            binding.swFresh.finishRefresh();
//                        }
//                    } else {
//                        binding.swFresh.finishLoadmore();
//                    }
//                    if (loginBeanResponseBean.getData() != null) {
//                        if (loginBeanResponseBean.getData().getList() != null && loginBeanResponseBean.getData().getSize() > 0) {
//                            list.addAll(loginBeanResponseBean.getData().getList());
//                            if (list != null && list.size() < pageSize) {
//                                haveMore = false;
//                                binding.swFresh.setEnableLoadmore(false);
//                            }
//                        }
//                    }
//                    allLuBoAdapter.setNewData(list);
//                } else {
//                    allLuBoAdapter.setNewData(new ArrayList<>());
//                    toast("" + loginBeanResponseBean.getMessage());
//                }
//                if (allLuBoAdapter.getData().size() <= 0 && allZhiBoAdapter.getData().size() <= 0) {
//                    binding.llHaveContent.setVisibility(View.GONE);
//                    binding.llEmptyView.setVisibility(View.VISIBLE);
//                } else {
//                    binding.llHaveContent.setVisibility(View.VISIBLE);
//                    binding.llEmptyView.setVisibility(View.GONE);
//                }
//
//            }
//        });
//
//
//        viewModel.liveZhiBoMatchListLiveDataLiveData.observe(this, new Observer<ResponseBean<LiveListBean>>() {
//            @Override
//            public void onChanged(ResponseBean<LiveListBean> liveMatchDetailBeanResponseBean) {
//                if (liveMatchDetailBeanResponseBean.getErrorCode().equals("200")) {
//                    LiveListBean data = liveMatchDetailBeanResponseBean.getData();
//                    if (data != null) {
//                        List<LiveListBean.ListBean> list = data.getList();
//                        if (list != null && list.size() > 0) {
//                            allZhiBoAdapter.setNewData(list);
//                        } else {
//                            allZhiBoAdapter.setNewData(new ArrayList<>());
//                        }
//                    } else {
//                        allZhiBoAdapter.setNewData(new ArrayList<>());
//                    }
//                } else {
//                    toast("" + liveMatchDetailBeanResponseBean.getMessage());
//                }
//            }
//        });
        RxBus.getDefault().toObservable(AllLiveEvent.class).subscribe(tagEvents -> {
//            getData();
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        getZhiboList();
        getData();
    }

    @Override
    public void onClick(View v) {

    }
    private void getZhiboList() {


    }

    private void getData() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        binding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getZhiboList();
        getData();
    }
}
