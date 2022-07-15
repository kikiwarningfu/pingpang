package heyong.intellectPinPang.ui.competition.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.XLog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CompetitionAdapter;
import heyong.intellectPinPang.bean.competition.SearchMathListBean;
import heyong.intellectPinPang.databinding.FragmentCloudConsultantBinding;
import heyong.intellectPinPang.ui.mine.activity.live.LiveOrderListActivity;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity;
import heyong.lzy.imagepicker.util.StatusBarUtil;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * @Name：weiying
 * @Description：赛事
 * @Author：whf 修改人：whf
 * <p>
 * <p>
 * 修改备注：
 */
public class CompetitionFragment extends
        BaseFragment<FragmentCloudConsultantBinding, CompetitionViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    private CompetitionAdapter competitionAdapter;
    List<SearchMathListBean.ListBean> list = new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = CompetitionFragment.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    private String cityLevel = "";
    private String matchLevel = "";
    private String matchStatus = "";


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            StatusBarUtil.statusBarLightMode(getActivity());

        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: CompetitionFragment");
        if (!hidden) {
//            initLocation();
            StatusBarUtil.statusBarLightMode(getActivity());

        }
    }

    public CompetitionFragment() {

    }

    public static CompetitionFragment newInstance() {

        Bundle args = new Bundle();
        CompetitionFragment fragment = new CompetitionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cloud_consultant;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding.setListener(this);
        StatusBarUtil.statusBarLightMode(getActivity());
        competitionAdapter = new CompetitionAdapter(getActivity());
        mRefreshAnimHeader = new MyRefreshAnimHeader(getActivity());
        if (binding.swFresh.isRefreshing()) {
            binding.swFresh.finishRefresh();
        }
//        binding.swFresh.setRefreshHeader(mRefreshAnimHeader);


        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        binding.mRecyclerView.setAdapter(competitionAdapter);
        competitionAdapter.setOnItemChildClickListener(this);
        competitionAdapter.setOnItemClickListener(this);
        binding.swFresh.setOnRefreshListener(this);

        binding.etSearchTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    getData();
                }

                return false;
            }
        });
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
        viewModel.SearchMathListBeanLiveData.observe(this, new Observer<ResponseBean<SearchMathListBean>>() {
            @Override
            public void onChanged(ResponseBean<SearchMathListBean> loginBeanResponseBean) {
//                if (loginBeanResponseBean.isSuccess()) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

                    if (pageNum == 1) {
                        list.clear();
                        if (binding.swFresh != null) {
                            binding.swFresh.finishRefresh();
                        }
                    } else {
                        binding.swFresh.finishLoadmore();
                    }
                    if (list != null) {
                        list.addAll(loginBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            binding.swFresh.setEnableLoadmore(false);
                        }
                        competitionAdapter.setNewData(list);
                    }
                } else {
                    binding.swFresh.finishRefresh();
                    binding.swFresh.finishLoadmore();
                }
            }
        });
        getData();
    }

    public void getData() {
        String string = binding.etSearchTitle.getText().toString();
        viewModel.searchMatchTitle("" + matchStatus, "" + cityLevel, "" + matchLevel, "" + string, "" + pageNum, "" + pageSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_all_status://全部状态
                binding.tvRegisterAllStatus.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvRegisterAllStatus.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvRegisterOf.setBackground(null);
                binding.tvRegisterOf.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterUnderWay.setBackground(null);
                binding.tvRegisterUnderWay.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterEnd.setBackground(null);
                binding.tvRegisterEnd.setTextColor(Color.parseColor("#ff333333"));
                matchStatus = "";
                binding.swFresh.setEnableLoadmore(true);
                pageNum = 1;
                haveMore = true;
                getData();
                break;
            case R.id.tv_register_of://报名中
                binding.tvRegisterAllStatus.setBackground(null);
                binding.tvRegisterAllStatus.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterOf.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvRegisterOf.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvRegisterUnderWay.setBackground(null);
                binding.tvRegisterEnd.setBackground(null);
                binding.tvRegisterUnderWay.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterEnd.setTextColor(Color.parseColor("#ff333333"));
                matchStatus = "1";
                binding.swFresh.setEnableLoadmore(true);
                pageNum = 1;
                haveMore = true;
                getData();
                break;
            case R.id.tv_register_under_way://进行中
                binding.tvRegisterAllStatus.setBackground(null);
                binding.tvRegisterAllStatus.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterOf.setBackground(null);
                binding.tvRegisterOf.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterUnderWay.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvRegisterUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvRegisterEnd.setBackground(null);
                binding.tvRegisterEnd.setTextColor(Color.parseColor("#ff333333"));
                matchStatus = "2";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_register_end://比赛结束
                binding.tvRegisterAllStatus.setBackground(null);
                binding.tvRegisterAllStatus.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterOf.setBackground(null);
                binding.tvRegisterOf.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterUnderWay.setBackground(null);
                binding.tvRegisterUnderWay.setTextColor(Color.parseColor("#ff333333"));
                binding.tvRegisterEnd.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvRegisterEnd.setTextColor(Color.parseColor("#FFFFFF"));
                matchStatus = "3";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_city_level_all://全部
                binding.tvCityLevelAll.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvCityLevelAll.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvCityLevelCountry.setBackground(null);
                binding.tvCityLevelCountry.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelProvince.setBackground(null);
                binding.tvCityLevelProvince.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCity.setBackground(null);
                binding.tvCityLevelCity.setTextColor(Color.parseColor("#ff333333"));
                cityLevel = "";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_city_level_country://全国级

                binding.tvCityLevelAll.setBackground(null);
                binding.tvCityLevelAll.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCountry.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvCityLevelCountry.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvCityLevelProvince.setBackground(null);
                binding.tvCityLevelProvince.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCity.setBackground(null);
                binding.tvCityLevelCity.setTextColor(Color.parseColor("#ff333333"));
                cityLevel = "全国级";
                binding.swFresh.setEnableLoadmore(true);
                pageNum = 1;
                haveMore = true;
                getData();
                break;
            case R.id.tv_city_level_province://省级

                binding.tvCityLevelAll.setBackground(null);
                binding.tvCityLevelAll.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCountry.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCountry.setBackground(null);
                binding.tvCityLevelProvince.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvCityLevelProvince.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvCityLevelCity.setBackground(null);
                binding.tvCityLevelCity.setTextColor(Color.parseColor("#ff333333"));
                cityLevel = "省级";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_city_level_city://市级

                binding.tvCityLevelAll.setBackground(null);
                binding.tvCityLevelAll.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCountry.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelCountry.setBackground(null);
                binding.tvCityLevelProvince.setTextColor(Color.parseColor("#ff333333"));
                binding.tvCityLevelProvince.setBackground(null);
                binding.tvCityLevelCity.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvCityLevelCity.setBackgroundResource(R.drawable.shape_club_blue);
                cityLevel = "市级";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_level_all://全部水平级
                binding.tvLevelAll.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvLevelAll.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvLevelProfessional.setBackground(null);
                binding.tvLevelProfessional.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelAmateur.setBackground(null);
                binding.tvLevelAmateur.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelChildren.setBackground(null);
                binding.tvLevelChildren.setTextColor(Color.parseColor("#ff333333"));

                matchLevel = "";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_level_professional://专业级别

                binding.tvLevelAll.setBackground(null);
                binding.tvLevelAll.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelProfessional.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvLevelProfessional.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvLevelAmateur.setBackground(null);
                binding.tvLevelChildren.setBackground(null);
                binding.tvLevelAmateur.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelChildren.setTextColor(Color.parseColor("#ff333333"));
                matchLevel = "专业级";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;
            case R.id.tv_level_amateur://业余级别
                binding.tvLevelAll.setBackground(null);
                binding.tvLevelAll.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelProfessional.setBackground(null);
                binding.tvLevelProfessional.setTextColor(Color.parseColor("#ff333333"));
                binding.tvLevelAmateur.setBackgroundResource(R.drawable.shape_club_blue);
                binding.tvLevelAmateur.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvLevelChildren.setBackground(null);
                binding.tvLevelChildren.setTextColor(Color.parseColor("#ff333333"));
                matchLevel = "业余级";
                binding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                getData();
                break;

        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_live_order:

                SearchMathListBean.ListBean listBean1 = competitionAdapter.getData().get(position);
                long id1 = listBean1.getId();
                LiveOrderListActivity.Companion.startActivity(getActivity(), "" + id1);
                break;
            case R.id.tv_competition_detail:
                SearchMathListBean.ListBean listBean = competitionAdapter.getData().get(position);
                long id = listBean.getId();
                Intent intent = new Intent(getActivity(), EventSignUpActivity.class);
                intent.putExtra(EventSignUpActivity.SIGNUPID, "" + id);
                startActivity(intent);
                break;
        }
//        boolean fastClick = isFastClick();
//        if (!fastClick) {
//            SearchMathListBean.ListBean listBean = competitionAdapter.getData().get(position);
//            long id = listBean.getId();
//            Intent intent = new Intent(getActivity(), EventSignUpActivity.class);
//            intent.putExtra(EventSignUpActivity.SIGNUPID, "" + id);
//            startActivity(intent);
//        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        XLog.e("2222");

        boolean fastClick = isFastClick();
        if (!fastClick) {
            SearchMathListBean.ListBean listBean = competitionAdapter.getData().get(position);
            long id = listBean.getId();
            Intent intent = new Intent(getActivity(), EventSignUpActivity.class);
            intent.putExtra(EventSignUpActivity.SIGNUPID, "" + id);
            startActivity(intent);
        }


    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        binding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }
}
