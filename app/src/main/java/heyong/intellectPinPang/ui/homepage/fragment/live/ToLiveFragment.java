package heyong.intellectPinPang.ui.homepage.fragment.live;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.XLog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.adapter.live.ToBuyAdapter;
import heyong.intellectPinPang.bean.live.LiveHallBean;
import heyong.intellectPinPang.event.ToLiveEvent;
import heyong.intellectPinPang.live.activity.WatchLiveActivity;
import heyong.intellectPinPang.soundnet.SingleHostLiveActivity;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.live.PlayBackLiveAdapter;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.FragmentToLiveBinding;
import heyong.intellectPinPang.event.AllLiveEvent;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.ui.mine.event.LiveHallChangeEvent;
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.club.fragment.child.AllClubFragment;
import heyong.intellectPinPang.widget.SpaceItemDecoration;

/**
 * 直播的fragment
 */
public class ToLiveFragment extends BaseFragment<FragmentToLiveBinding, LiveViewModel> implements
        View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {


    private ToBuyAdapter playBackLiveAdapter;
    List<LiveHallBean.ListBean> list = new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = AllClubFragment.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    private SpaceItemDecoration mHighlightReplays;
    private String matchId = "";
    private String searchWord = "";

    public ToLiveFragment(String strMatchId) {
        this.matchId = strMatchId;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_to_live;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        playBackLiveAdapter = new ToBuyAdapter(getActivity());
        binding.setListener(this);
        EventBus.getDefault().register(this);

        mRefreshAnimHeader = new MyRefreshAnimHeader(getActivity());
        if (binding.swFresh.isRefreshing()) {
            binding.swFresh.finishRefresh();
        }
//        binding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        XLog.e(matchId);

        binding.rvPlayBack.removeItemDecoration(mHighlightReplays);
        mHighlightReplays = new SpaceItemDecoration(20);
        binding.rvPlayBack.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.rvPlayBack.addItemDecoration(mHighlightReplays);
        binding.rvPlayBack.setAdapter(playBackLiveAdapter);
        playBackLiveAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        playBackLiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                long id = playBackLiveAdapter.getData().get(position).getId();

                SingleHostLiveActivity.goActivity(getActivity(), false, ""+id);

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
        viewModel.liveBroadcastMatchAgainstListData.observe(this, new Observer<ResponseBean<LiveHallBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveHallBean> loginBeanResponseBean) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {
                    if (pageNum == 1) {
                        list.clear();
                        if (binding.swFresh != null) {
                            binding.swFresh.finishRefresh();
                        }
                    } else {
                        binding.swFresh.finishLoadmore();
                    }
                    if (loginBeanResponseBean.getData() != null) {
                        if (loginBeanResponseBean.getData().getList() != null && loginBeanResponseBean.getData().getSize() > 0) {
                            list.addAll(loginBeanResponseBean.getData().getList());
                            if (list != null && list.size() < pageSize) {
                                haveMore = false;
                                binding.swFresh.setEnableLoadmore(false);
                            }
                        }
                    }
                    playBackLiveAdapter.setNewData(list);
                } else {
                    playBackLiveAdapter.setNewData(new ArrayList<>());
                    toast("" + loginBeanResponseBean.getMessage());
                }
                if (playBackLiveAdapter.getData().size() <= 0 && playBackLiveAdapter.getData().size() <= 0) {
                    binding.llHaveContent.setVisibility(View.GONE);
                    binding.llEmptyView.setVisibility(View.VISIBLE);
                } else {
                    binding.llHaveContent.setVisibility(View.VISIBLE);
                    binding.llEmptyView.setVisibility(View.GONE);
                }

            }
        });


        RxBus.getDefault().toObservable(ToLiveEvent.class).subscribe(tagEvents -> {
            searchWord = tagEvents.getTitle();
            XLog.e("" + tagEvents.getTitle());
            getData();
        });
        getData();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        long id = allLiveAdapter.getData().get(position).getId();
//        Intent intent = new Intent(getActivity(), ClubDetailActivity.class);
//        intent.putExtra(ClubDetailActivity.CLUB_ID, "" + id);
//        startActivity(intent);
//        goActivity(AliPlayVideoActivity.class);
    }

    @Subscribe
    public void funOrderUpdateTime(LiveHallChangeEvent event) {
        XLog.e(event.getTimeUpdate());

        searchWord = event.getTimeUpdate();
        getData();
    }

    private void getData() {
        viewModel.liveBroadcastMatchAgainstList("" + matchId, "", "" + pageNum, "" + pageSize, "" + searchWord, "2");
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        //        /*刷新数据 page=1*/
        binding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }
}
