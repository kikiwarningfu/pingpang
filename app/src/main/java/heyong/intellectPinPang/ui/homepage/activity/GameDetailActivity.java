package heyong.intellectPinPang.ui.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dnwalter.formlayoutmanager.entity.Monster;
import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.helper.MyFormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.dnwalter.formlayoutmanager.layoutmanager.MyFormLayoutManager;
import com.dnwalter.formlayoutmanager.layoutmanager.NormalFormLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superluo.textbannerlibrary.utils.DisplayUtils;
//import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.xq.fasterdialog.dialog.CustomDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.FillFormAdapter;
import heyong.intellectPinPang.adapter.game.EleminateMatchAdapter;
import heyong.intellectPinPang.adapter.game.FormStageAdapter;
import heyong.intellectPinPang.adapter.game.GameDetailLeftGroupListAdapter;
import heyong.intellectPinPang.adapter.game.GameDetailTopGroupTopAdapter;
import heyong.intellectPinPang.adapter.game.GameGroupLittleAdapter;
import heyong.intellectPinPang.adapter.game.GameProjectAdapter;
import heyong.intellectPinPang.adapter.game.GameSexAdapter;
import heyong.intellectPinPang.adapter.game.IntegralCalculateOfficialAdapter;
import heyong.intellectPinPang.adapter.game.MatchScoreAllAdapter;
import heyong.intellectPinPang.adapter.game.MonsterOfficialHAdapter;
import heyong.intellectPinPang.adapter.game.MonsterOfficialHGroupListAdapter;
import heyong.intellectPinPang.adapter.game.MyRefereeDisplayAdapter;
import heyong.intellectPinPang.adapter.game.TitleGroupLeftListAdapter;
import heyong.intellectPinPang.adapter.game.TitleGroupTopListAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.IntergalPeopleDetailOfficialAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.MonsterIntergalDetailAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.TitleIntergalLeftOfficialAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.TitleIntergalTopOfficialAdapter;
import heyong.intellectPinPang.bean.competition.MatchAgainstDataBean;
import heyong.intellectPinPang.bean.competition.JudgeJoinMatchStatusBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeGroupListBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeKnockOutMatchBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeMatchScoreBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeRoundBean;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.bean.competition.QueryComputeInfoBean;
import heyong.intellectPinPang.bean.competition.QueryPointDetailBean;
import heyong.intellectPinPang.bean.competition.SecondNavigationFourBean;
import heyong.intellectPinPang.bean.competition.TitleBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ActivityGameDetailBinding;
import heyong.intellectPinPang.live.activity.ReferenceVisitorMatchActivity;
import heyong.intellectPinPang.live.adapter.MyTagFillFormAdapter;
import heyong.intellectPinPang.live.adapter.MyTagGameGroupAdapter;
import heyong.intellectPinPang.ui.club.activity.MemberShipDetailsActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsScoreNewOfficialActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.ChiefJudgeConsoleActivity;
import heyong.intellectPinPang.ui.competition.activity.CoachTeamDetailActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.MyEventActivity;
import heyong.intellectPinPang.ui.competition.activity.RefereeCompetitionInterfaceActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.TeamNumbersActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.ui.shopmall.fragment.ShopMallKotlinActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * ????????????
 * <p>
 * ????????? ??????  ?????????
 * ???????????? ?????? ??????????????????   ??????????????????    ???????????? ????????????????????????
 * ???????????? ?????? ???????????? ??????  ???????????? ??????  ????????????-?????? ??????
 * ????????????-??????????????????  ??????????????????  ???????????? ??????
 * ?????????-????????????  ????????????  ????????????    ????????????  ???????????? ?????? ??????  ????????????  ????????????  ????????????
 * <p>
 * <p>
 * ????????????  ????????? ?????????
 * ????????? ?????? ?????? ?????????   ???1????????????  ???2??????????????????????????????
 * /*
 */
/*?????????????????????*/
/*????????????   ll_elimination_detail
 * ???????????? ?????????  ll_the_group_list
 * ?????????   ll_report_card
 * ????????????   ll_first_stage*/
//SinglesDetailScoreOfficialActivity
//DoubleDetailScoreOfficialActivity

//    ???????????? ????????????
//    ????????? ???????????? ??????????????????????????????????????????????????? ??????????????? ????????????
//    ?????????  ???????????? ?????????????????????
//    ?????????  ????????????????????????
//    ?????????  ???????????????

//
public class GameDetailActivity extends BaseActivity<ActivityGameDetailBinding, HomePageViewModel> implements View.OnClickListener {
    public static final String TAG = GameDetailActivity.class.getSimpleName();
    public static final String MATCH_ID = "match_id";//?????????id
    public static final String MATCH_TITLE = "match_title";
    private String matchTitle = "";
    //    GameGroupAdapter gameGroupAdapter;
    MyTagGameGroupAdapter myTagGameGroupAdapter;
    GameSexAdapter gameSexAdapter;
    GameProjectAdapter gameProjectAdapter;
    GameGroupLittleAdapter gameGroupLittleAdapter;
    FormStageAdapter formStageAdapter;
    MyRefereeDisplayAdapter refereeDisplayAdapter;
    int matchRule = 0;
    MyDividerItemDecoration mSixDiD;

    private RecyclerView rvTopTitle;
    private RecyclerView rvLeftTitle;
    private RecyclerView mRecyclerView;

    private RecyclerView rvGroupTopList;
    private RecyclerView rvGroupLeftList;
    private RecyclerView mRecyclerViewGroup;
    NormalFormLayoutManager normalFormLayoutManager;
    private int width;
    private String formTitle = "";//??????
    private String strMatchId = "";//??????id
    private String projectItemId = "";//?????????id
    private String groupNumId = "";//?????????id
    private String matchFormatId = "";//?????????????????????id
    private String matchFormatName = "";//??????
    private int showType = -1;//type???1 ????????? type???2 ???????????? ????????????
    private boolean isShowScreenCondition = true;//?????????????????????????????????

    List<MatchScreenBean.ProjectBean> project = new ArrayList<>();
    List<MatchArrangeRoundBean.ArrangeDataBean> arrangeData = new ArrayList<>();//??????????????????????????????
    private List<TitleBean> topTitles = new ArrayList<>();
    private List<TitleBean> leftTitles = new ArrayList<>();
    private List<Monster> monsterList = new ArrayList<>();

    private List<TitleBean> topGroupListTitles = new ArrayList<>();
    private List<TitleBean> leftGroupListTitles = new ArrayList<>();
    private List<Monster> monsterGroupList = new ArrayList<>();
    MyFormScrollHelper formScrollHelperChuzu;
    public int wdith;
    private int winCount = 0;
    MyFormLayoutManager myFormLayoutManager;
    MonsterOfficialHAdapter monsterOfficialHAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_detail;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_search_sports};
        return ids;
    }

    FormScrollHelper formScrollHelperGroup;
    private String groupName = "";
    MatchScreenFormatBean matchScreenFormatBean;
    String itemTiles = "";
    MyRefreshAnimHeader mRefreshAnimHeader;
    private int inputType = 0;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        //????????? ???????????????????????? ?????????recyclerview  rv_competition_system   ???????????????????????????
        //?????????   ??????RecyclerView   rv_competition_system  ?????????????????????
        mRefreshAnimHeader = new MyRefreshAnimHeader(GameDetailActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        refereeDisplayAdapter = new MyRefereeDisplayAdapter();
        eleminateMatchAdapter = new EleminateMatchAdapter();
        mBinding.swFresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                clearData();
                if (inputType == 1)//getMatchTaoTaiArrangeData
                {
                    mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                } else if (inputType == 2)//getMatchArrangeData
                {
                    showLoading();
                    mViewModel.getMatchArrangeData("" + strMatchId,
                            "" + projectItemId, "" + groupNumId,
                            "" + matchFormatId, "" + matchFormatName, "" + groupName);
                }


                mBinding.swFresh.finishRefresh();
            }
        });
        mBinding.swFresh.setEnableLoadmore(false);
        mBinding.setListener(this);
        WindowManager wm = getWindowManager();
        wdith = wm.getDefaultDisplay().getWidth();
        showType = 1;
        // ????????????
        wm = getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        mBinding.tvCelOrition.setText("????????????");

        mRecyclerViewGroup = findViewById(R.id.rv_group_for_List);
        rvGroupTopList = findViewById(R.id.rv_top_group_list);
        rvGroupLeftList = findViewById(R.id.rv_left_group_list);
        formScrollHelperGroup = new FormScrollHelper();
        myFormLayoutManager = new MyFormLayoutManager(topTitles.size(), mRecyclerViewGroup);

        formScrollHelperGroup.connectRecyclerView(mRecyclerViewGroup);
        formScrollHelperGroup.connectRecyclerView(rvGroupTopList);
        //        formScrollHelperGroup.connectRecyclerView(rvGroupLeftList);
        normalFormLayoutManager = new NormalFormLayoutManager(topTitles.size());
        mRecyclerView = findViewById(R.id.rv_form_list);
        rvTopTitle = findViewById(R.id.rv_top_list);
        rvLeftTitle = findViewById(R.id.rv_left_list);

        formScrollHelperChuzu = new MyFormScrollHelper();
        formScrollHelperChuzu.connectRecyclerView(mRecyclerView);
        formScrollHelperChuzu.connectRecyclerView(rvTopTitle);
        formScrollHelperChuzu.connectRecyclerView(rvLeftTitle);

        strMatchId = getIntent().getStringExtra(MATCH_ID);
        matchTitle = getIntent().getStringExtra(MATCH_TITLE);
        mBinding.tvCenter.setText("" + matchTitle);
        mViewModel.getMatchScreen(strMatchId);
        mViewModel.judgeJoinMatchStatus(strMatchId);
        mViewModel.phpAesLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                String str = "&authorize_token=" + responseBean.getData();
                showSurprise(str);
            }
        });
        mSixDiD = new MyDividerItemDecoration(GameDetailActivity.this, 10,
                getResources().getColor(R.color.white));
        /*search*/
        mBinding.etSearchSports.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //???????????????
                    CommonUtilis.hideSoftKeyboard(GameDetailActivity.this);
                    /*????????????*/
                    toast("????????????");
                    return true;
                }
                return false;
            }
        });

        mViewModel.judgeMatchStatusLiveData.observe(this, new Observer<ResponseBean<JudgeJoinMatchStatusBean>>() {
            @Override
            public void onChanged(ResponseBean<JudgeJoinMatchStatusBean> judgeJoinMatchStatusBeanResponseBean) {
                if (judgeJoinMatchStatusBeanResponseBean.getErrorCode().equals("200")) {
                    JudgeJoinMatchStatusBean data = judgeJoinMatchStatusBeanResponseBean.getData();
                    if (data != null) {
                        int joinStatus = Integer.parseInt(data.getJoinStatus());
                        int role = Integer.parseInt(data.getRole());
                        if (joinStatus == 0) {
                            /*?????????*/
                            mBinding.tvRight.setVisibility(View.GONE);
                        } else {
                            if (role == 1 || role == 2 || role == 3 || role == 4) {
                                mBinding.tvRight.setVisibility(View.VISIBLE);
                                mBinding.tvRight.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        /*??????*/
                                        if (role == 1) {
                                            /*?????????   ???????????????????????????????????????*/
                                            goActivity(MyEventActivity.class);
                                        } else if (role == 2) {
                                            /*?????????  ????????? ?????????????????????*/
                                            Intent intent = new Intent(GameDetailActivity.this, CoachTeamDetailActivity.class);
                                            intent.putExtra(CoachTeamDetailActivity.MATCH_ID, "" + strMatchId);
                                            startActivity(intent);
                                        } else if (role == 3) {
                                            /*????????? RefereeCompetitionInterfaceActivity */
                                            Intent intent = new Intent();
                                            intent.setClass(GameDetailActivity.this, RefereeCompetitionInterfaceActivity.class);
                                            intent.putExtra(RefereeCompetitionInterfaceActivity.CODE, "2");
                                            intent.putExtra(RefereeCompetitionInterfaceActivity.MATCH_ID, "" + strMatchId);
                                            startActivity(intent);
                                        } else {
                                            /*????????? ChiefJudgeConsoleActivity */
                                            Intent intent = new Intent();
                                            intent.setClass(GameDetailActivity.this, ChiefJudgeConsoleActivity.class);
                                            intent.putExtra(ChiefJudgeConsoleActivity.MATCHID, "" + strMatchId);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                /*????????????*/
                                mBinding.tvRight.setVisibility(View.GONE);
                            }
                        }

                    } else {
                        toast("" + judgeJoinMatchStatusBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + judgeJoinMatchStatusBeanResponseBean.getMessage());
                }
            }
        });


        /*?????????????????? */
        mViewModel.queryComputeInfoLiveData.observe(this, new Observer<ResponseBean<QueryComputeInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryComputeInfoBean> queryComputeInfoBeanResponseBean) {
                if (queryComputeInfoBeanResponseBean.getData() != null) {
                    showPopIntegralWindow(queryComputeInfoBeanResponseBean.getData());
                } else {
                    toast("" + queryComputeInfoBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.getMatchAgainstDataLiveData.observe(this, new Observer<ResponseBean<List<MatchAgainstDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<MatchAgainstDataBean>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {
                    mBinding.llFillFormList.setVisibility(View.VISIBLE);

                    List<MatchAgainstDataBean> data = listResponseBean.getData();
                    FillFormAdapter fillFormAdapter = new FillFormAdapter();
                    if (data != null && data.size() > 0) {
                        for (int m = 0; m < data.size(); m++) {
                            if (m == 0) {
                                data.get(m).setSelect(true);

                            } else {
                                data.get(m).setSelect(false);
                            }
                        }
                        List<MatchAgainstDataBean.OpponentBean> opponent = data.get(0).getOpponent();
                        mBinding.rvDuizhen.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.VERTICAL, false));
                        mBinding.rvDuizhen.setAdapter(fillFormAdapter);
                        fillFormAdapter.setNewData(opponent);

                        MyTagFillFormAdapter myTagRegisterAdapter = new MyTagFillFormAdapter(GameDetailActivity.this, data);
                        mBinding.rvCompetitionProject.setAdapter(myTagRegisterAdapter);
                        mBinding.rvCompetitionProject.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                            @Override
                            public boolean onTagClick(View view, int position, FlowLayout parent) {
                                for (int i = 0; i < data.size(); i++) {
                                    data.get(i).setSelect(false);
                                }
                                data.get(position).setSelect(true);
                                List<MatchAgainstDataBean.OpponentBean> opponent = data.get(position).getOpponent();
                                mBinding.rvDuizhen.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.VERTICAL, false));
                                mBinding.rvDuizhen.setAdapter(fillFormAdapter);
                                fillFormAdapter.setNewData(opponent);
                                myTagRegisterAdapter.notifyDatas(data);
//                                projectItem = data.get(position).getProjectItem();
//                                data.get(position).setSelect(true);
//                                myTagRegisterAdapter.notifyDatas(data);
//
//                                projectItemId = "" + data.get(position).getId();
//                                projectId = "" + data.get(position).getProjectId();
                                return false;
                            }
                        });
                    } else {
                        mBinding.llFillFormList.setVisibility(View.GONE);
                    }
                } else {
                    mBinding.llFillFormList.setVisibility(View.GONE);

                }
            }
        });

        /*????????????????????????????????????*/
        mViewModel.getMatchScreenLiveData.observe(this, new Observer<ResponseBean<MatchScreenBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchScreenBean> res) {
                project.clear();
                if (res.getData() != null) {
                    project = res.getData().getProject();
                    for (int i = 0; i < project.size(); i++) {
                        MatchScreenBean.ProjectBean projectBean = project.get(i);
                        if (i == 0) {
                            projectBean.setSelect(true);
                        } else {
                            projectBean.setSelect(false);
                        }
                        List<MatchScreenBean.ProjectBean.SexBean> sex = projectBean.getSex();
                        for (int m = 0; m < sex.size(); m++) {
                            if (m == 0) {
                                sex.get(m).setSelect(true);
                            } else {
                                sex.get(m).setSelect(false);
                            }
                            List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = sex.get(m).getItem();
                            for (int n = 0; n < item.size(); n++) {
                                if (n == 0) {
                                    item.get(n).setSelect(true);
                                } else {
                                    item.get(n).setSelect(false);
                                }
                            }
                        }
                    }
                    //                    gameGroupAdapter = new GameGroupAdapter();
                    myTagGameGroupAdapter = new MyTagGameGroupAdapter(GameDetailActivity.this, project);
                    //                    mBinding.rvGroup.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.HORIZONTAL, false));
                    //                    mBinding.rvGroup.setAdapter(gameGroupAdapter);
                    mBinding.rvGroup.setAdapter(myTagGameGroupAdapter);
                    //                    gameGroupAdapter.setNewData(project);

                    mBinding.rvGroup.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {

                            boolean fastClick = isFastClick();
                            if (!fastClick) {
                                for (int i = 0; i < project.size(); i++) {
                                    project.get(i).setSelect(false);
                                    List<MatchScreenBean.ProjectBean.SexBean> sex = project.get(i).getSex();
                                    for (int i1 = 0; i1 < sex.size(); i1++) {
                                        sex.get(i1).setSelect(false);
                                        List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = sex.get(i1).getItem();
                                        for (int i2 = 0; i2 < item.size(); i2++) {
                                            item.get(i2).setSelect(false);
                                        }
                                    }
                                }
                                project.get(position).setSelect(true);
                                MatchScreenBean.ProjectBean projectBean = project.get(position);
                                List<MatchScreenBean.ProjectBean.SexBean> sex = projectBean.getSex();
                                Log.e(TAG, "onItemClick:json=== " + new Gson().toJson(sex));
                                for (int m = 0; m < sex.size(); m++) {
                                    //                                if (m == 0) {
                                    Log.e(TAG, "onItemClick: m===0");
                                    if (m == 0) {
                                        sex.get(m).setSelect(true);
                                    } else {
                                        sex.get(m).setSelect(false);
                                    }
                                    List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = sex.get(m).getItem();
                                    for (int n = 0; n < item.size(); n++) {
                                        if (n == 0) {
                                            Log.e(TAG, "onItemClick: n===0");
                                            item.get(n).setSelect(true);
                                            projectItemId = "" + item.get(n).getId();
                                            //                                        Log.e(TAG, "onItemClick: " + new Gson().toJson(sex));
                                            Log.e(TAG, "onItemClick: projectItemId===" + projectItemId);

                                        } else {
                                            Log.e(TAG, "onItemClick: n====" + n);
                                            item.get(n).setSelect(false);
                                        }
                                    }
                                    sex.get(m).setItem(item);
                                    projectBean.setSex(sex);

                                }
                                //                            Log.e(TAG, "onItemClick: project====" + new Gson().toJson(project));
                                gameSexAdapter.setNewData(project.get(position).getSex());
                                //                            Log.e(TAG, "onItemClick: sex????????????===" + new Gson().toJson(project.get(position).getSex()));
                                //                                gameGroupAdapter.notifyDataSetChanged();
                                myTagGameGroupAdapter.setData(project);
                                gameSexAdapter.notifyDataSetChanged();
                                List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = project.get(position).getSex().get(0).getItem();
                                Log.e(TAG, "onItemClick: item====" + new Gson().toJson(item));
                                for (int i = 0; i < item.size(); i++) {
                                    if (i == 0) {
                                        item.get(i).setSelect(true);
                                        projectItemId = "" + item.get(0).getId();
                                    } else {
                                        item.get(i).setSelect(false);
                                    }
                                }
                                gameProjectAdapter.setNewData(item);
                                clearData();
                                Log.e(TAG, "onChanged: okhttp===22222");
                                mViewModel.getMatchScreenFormat("" + strMatchId, "" + projectItemId);
                            }
                            return false;
                        }
                    });
                    if (project.size() > 0 && project.get(0).getSex().size() > 0 && project.get(0).getSex().get(0).getItem().size() > 0) {
                        long id = project.get(0).getSex().get(0).getItem().get(0).getId();
                        Log.e(TAG, "onChanged: okhttp===11111");
                        mViewModel.getMatchScreenFormat("" + strMatchId, "" + id);
                    }

                    gameSexAdapter = new GameSexAdapter();
                    mBinding.rvSex.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.HORIZONTAL, false));
                    mBinding.rvSex.setAdapter(gameSexAdapter);
                    gameSexAdapter.setNewData(project.get(0).getSex());
                    /*????????????*/
                    gameSexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            boolean fastClick = isFastClick();
                            if (!fastClick) {
                                List<MatchScreenBean.ProjectBean.SexBean> data = gameSexAdapter.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    data.get(i).setSelect(false);
                                }
                                data.get(position).setSelect(true);
                                List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = data.get(position).getItem();
                                for (int m = 0; m < item.size(); m++) {
                                    if (m == 0) {
                                        item.get(m).setSelect(true);
                                        projectItemId = "" + item.get(m).getId();
                                    } else {
                                        item.get(m).setSelect(false);
                                    }
                                }
                                gameSexAdapter.notifyDataSetChanged();

                                gameProjectAdapter.setNewData(item);
                                clearData();
                                Log.e(TAG, "onChanged: okhttp===33333");
                                mViewModel.getMatchScreenFormat("" + strMatchId, "" + projectItemId);
                            }

                        }
                    });
                    gameProjectAdapter = new GameProjectAdapter();
                    mBinding.rvProject.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.HORIZONTAL, false));
                    mBinding.rvProject.setAdapter(gameProjectAdapter);
                    List<MatchScreenBean.ProjectBean.SexBean.ItemBean> item = project.get(0).getSex().get(0).getItem();
                    for (int i = 0; i < item.size(); i++) {
                        if (i == 0) {
                            item.get(i).setSelect(true);
                            projectItemId = "" + item.get(0).getId();
                        } else {
                            item.get(i).setSelect(false);
                        }
                    }
                    gameProjectAdapter.setNewData(item);
                    /*????????????*/
                    gameProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            boolean fastClick = isFastClick();
                            if (!fastClick) {
                                List<MatchScreenBean.ProjectBean.SexBean.ItemBean> data = gameProjectAdapter.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    data.get(i).setSelect(false);
                                }
                                data.get(position).setSelect(true);
                                long id = data.get(position).getId();
                                projectItemId = "" + id;

                                gameProjectAdapter.notifyDataSetChanged();
                                clearData();
                                Log.e(TAG, "onChanged: okhttp===4444444");
                                mViewModel.getMatchScreenFormat("" + strMatchId, "" + projectItemId);
                            }
                        }
                    });


                } else {
                    toast("" + res.getMessage());
                }
            }
        });
        //1??????2??????3??????+??????
        mViewModel.getMatchScreenFormatLiveData.observe(this, new Observer<ResponseBean<MatchScreenFormatBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchScreenFormatBean> matchScreenFormatBeanResponseBean) {
                if (matchScreenFormatBeanResponseBean.getErrorCode().equals("200")) {
                    if (matchScreenFormatBeanResponseBean.getData() != null) {
                        matchScreenFormatBean = matchScreenFormatBeanResponseBean.getData();
                        matchRule = Integer.parseInt(matchScreenFormatBean.getMatchRule());
                        //1??????2??????3??????+??????
                        if (matchRule == 1)//??????   ?????????????????????????????????  ??????????????? ????????????
                        {
                            groupNumId = "";
                            /*????????????   ???????????????View*/
                            /*????????????*/
                            List<MatchScreenFormatBean.MatchTypeSingleBean> matchTypeSingle = matchScreenFormatBean.getMatchTypeSingle();
                            gameGroupLittleAdapter = new GameGroupLittleAdapter();
                            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(GameDetailActivity.this);
                            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//??????????????????????????????????????????
                            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//?????????????????????
//justifyContent ???????????????????????????????????????????????????
                            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//???????????????????????????
                            mBinding.rvLittleGroup.setLayoutManager(flexboxLayoutManager);
                            mBinding.rvLittleGroup.setAdapter(gameGroupLittleAdapter);
                            mBinding.llFormStage.setVisibility(View.GONE);
                            List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = new ArrayList<>();
                            for (int i = 0; i < matchTypeSingle.size(); i++) {
                                MatchScreenFormatBean.MatchTypeBean.InfoBean infoBean = new MatchScreenFormatBean.MatchTypeBean.InfoBean();
                                if (i == 0) {
                                    infoBean.setSelect(true);
                                } else {
                                    infoBean.setSelect(false);
                                }
                                infoBean.setId(Integer.parseInt(matchTypeSingle.get(i).getCode()));
                                infoBean.setTitleName("" + matchTypeSingle.get(i).getTitle());
                                info.add(infoBean);
                            }
                            mBinding.tvGroupTitle.setVisibility(View.INVISIBLE);

                            if (info.size() > 0) {
                                gameGroupLittleAdapter.setNewData(info);
                                mBinding.llLittleGroup.setVisibility(View.VISIBLE);
                                matchFormatId = "" + info.get(0).getId();
                                matchFormatName = info.get(0).getTitle();
                                if (info.get(0).getTitleName().equals("?????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.VISIBLE);
                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                } else if (info.get(0).getTitleName().equals("?????????")) {
                                    //
                                    mBinding.llReportCard.setVisibility(View.VISIBLE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                }

                            } else {
                                mBinding.llLittleGroup.setVisibility(View.GONE);
                                gameGroupLittleAdapter.setNewData(new ArrayList<>());
                            }
                            //????????? ??????????????????
                            Log.e(TAG, "onChanged: 999999999999999999");
                            inputType = 2;
                            showLoading();
                            mViewModel.getMatchArrangeData("" + strMatchId,
                                    "" + projectItemId, "" + groupNumId,
                                    "" + matchFormatId, "" + matchFormatName, "");
                            gameGroupLittleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    clearData();
                                    List<MatchScreenFormatBean.MatchTypeBean.InfoBean> data1 = gameGroupLittleAdapter.getData();
                                    for (int i = 0; i < data1.size(); i++) {
                                        data1.get(i).setSelect(false);
                                    }
                                    data1.get(position).setSelect(true);
                                    gameGroupLittleAdapter.setNewData(data1);
                                    if (data1.get(position).getTitleName().equals("?????????")) {
                                        mBinding.llTheGroupList.setVisibility(View.VISIBLE);
                                        mBinding.llReportCard.setVisibility(View.GONE);
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                        mBinding.llEliminationDetail.setVisibility(View.GONE);
                                        mBinding.llFirstStage.setVisibility(View.GONE);
                                    } else if (data1.get(position).getTitleName().equals("?????????")) {
                                        mBinding.llReportCard.setVisibility(View.VISIBLE);
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                        mBinding.llTheGroupList.setVisibility(View.GONE);
                                        mBinding.llFillFormList.setVisibility(View.GONE);

                                        mBinding.llEliminationDetail.setVisibility(View.GONE);
                                        mBinding.llFirstStage.setVisibility(View.GONE);
                                    }
                                    matchFormatId = "" + data1.get(position).getId();
                                    matchFormatName = data1.get(position).getTitle();
                                    Log.e(TAG, "onItemClick: 88888888888888888888888888888");
                                    inputType = 2;
                                    //????????? ??????????????????
                                    showLoading();
                                    mViewModel.getMatchArrangeData("" + strMatchId,
                                            "" + projectItemId, "" + groupNumId,
                                            "" + matchFormatId, "" + matchFormatName, "");
                                }
                            });

                        } else if (matchRule == 2)//?????????  ????????????????????? ???????????? ?????????????????????
                        {
                            /*????????????  ????????????   ??????+??????   ???????????? ???????????????  ??????????????????????????? */
                            /*???????????? ?????????????????????*/
                            List<MatchScreenFormatBean.MatchTypeBean> matchType = matchScreenFormatBean.getMatchType();
                            formStageAdapter = new FormStageAdapter(GameDetailActivity.this, width);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(GameDetailActivity.this, 4);
                            mBinding.rvFormStage.removeItemDecoration(mSixDiD);
                            mSixDiD = new MyDividerItemDecoration(GameDetailActivity.this, 10,
                                    GameDetailActivity.this.getResources().getColor(R.color.white));
                            mBinding.rvFormStage.addItemDecoration(mSixDiD);
                            //??????manager
                            mBinding.llFormStage.setVisibility(View.VISIBLE);
                            mBinding.rvFormStage.setLayoutManager(gridLayoutManager);
                            mBinding.rvFormStage.setAdapter(formStageAdapter);
                            mBinding.llFormStage.setVisibility(View.VISIBLE);
                            mBinding.tvGroupTitle.setVisibility(View.VISIBLE);

                            gameGroupLittleAdapter = new GameGroupLittleAdapter();
                            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(GameDetailActivity.this);
                            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//??????????????????????????????????????????
                            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//?????????????????????
//justifyContent ???????????????????????????????????????????????????
                            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//???????????????????????????
                            mBinding.rvLittleGroup.setLayoutManager(flexboxLayoutManager);
                            mBinding.rvLittleGroup.setAdapter(gameGroupLittleAdapter);

                            MatchScreenFormatBean.MatchTypeBean matchTypeBean = matchType.get(0);
                            if (matchTypeBean.getInfo() != null && matchTypeBean.getInfo().size() > 0) {
                                mBinding.llLittleGroup.setVisibility(View.VISIBLE);
                                List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = matchTypeBean.getInfo();
                                for (int i = 0; i < info.size(); i++) {
                                    if (i == 0) {
                                        info.get(i).setSelect(true);
                                        groupNumId = "" + info.get(0).getId();
                                        itemTiles = "" + info.get(0).getTitleName();
                                    } else {
                                        info.get(i).setSelect(false);
                                    }
                                }
                                gameGroupLittleAdapter.setNewData(info);
                                mBinding.llTheGroupList.setVisibility(View.GONE);
                                mBinding.llFillFormList.setVisibility(View.GONE);

                                mBinding.llReportCard.setVisibility(View.GONE);

                                mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                                mBinding.llFirstStage.setVisibility(View.GONE);

                            } else {
                                gameGroupLittleAdapter.setNewData(new ArrayList<>());
                                mBinding.llLittleGroup.setVisibility(View.GONE);
                            }
                            Log.e(TAG, "onChanged: itemTiles===" + itemTiles);
                            for (int i = 0; i < matchType.size(); i++) {
                                if (i == 0) {
                                    matchType.get(i).setSelect(true);
                                    matchFormatId = matchType.get(i).getCode();
                                    matchFormatName = matchType.get(i).getTitle();
                                } else {
                                    matchType.get(i).setSelect(false);
                                }
                            }
                            formStageAdapter.setNewData(matchType);
                            inputType = 1;
                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                            Log.e(TAG, "onChanged: 77777777777777777777777777");
                            //?????????????????????????????? ?????????????????? ??????????????????????????????

                            formStageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        clearData();
                                        MatchScreenFormatBean.MatchTypeBean matchTypeBean1 = formStageAdapter.getData().get(position);
                                        List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = matchTypeBean1.getInfo();
                                        List<MatchScreenFormatBean.MatchTypeBean> data1 = formStageAdapter.getData();
                                        for (int i = 0; i < data1.size(); i++) {
                                            data1.get(i).setSelect(false);
                                        }
                                        data1.get(position).setSelect(true);
                                        matchFormatId = "" + data1.get(position).getCode();
                                        matchFormatName = "" + data1.get(position).getTitle();
                                        formStageAdapter.setNewData(data1);
                                        eleminateMatchAdapter.setClickRankingPosition(0);
                                        if (info != null && info.size() > 0) {
                                            for (int m = 0; m < info.size(); m++) {
                                                if (m == 0) {
                                                    info.get(m).setSelect(true);
                                                    groupNumId = "" + info.get(0).getId();
                                                    itemTiles = "" + info.get(0).getTitleName();
                                                } else {
                                                    info.get(m).setSelect(false);
                                                }
                                            }
                                            gameGroupLittleAdapter.setNewData(info);

                                            mBinding.llLittleGroup.setVisibility(View.VISIBLE);
                                        } else {
                                            groupNumId = "";
                                            gameGroupLittleAdapter.setNewData(new ArrayList<>());
                                            mBinding.llLittleGroup.setVisibility(View.GONE);
                                        }
                                        Log.e(TAG, "onItemClick: 66666666666666666");
                                        //                                mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                        //                                        "" + projectItemId, "" + groupNumId,
                                        //                                        "" + matchFormatId, "" + matchFormatName, "");
                                        inputType = 1;
                                        if (info == null) {
                                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "");
                                        } else {
                                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                                        }
                                    }

                                }
                            });
                            gameGroupLittleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        clearData();
                                        List<MatchScreenFormatBean.MatchTypeBean.InfoBean> data1 = gameGroupLittleAdapter.getData();
                                        for (int i = 0; i < data1.size(); i++) {
                                            data1.get(i).setSelect(false);
                                        }
                                        data1.get(position).setSelect(true);
                                        gameGroupLittleAdapter.setNewData(data1);
                                        groupNumId = "" + data1.get(position).getId();
                                        itemTiles = "" + data1.get(position).getTitleName();
                                        Log.e(TAG, "onItemClick: 55555555555555555");
                                        //                                mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                        //                                        "" + projectItemId, "" + groupNumId,
                                        //                                        "" + matchFormatId, "" + matchFormatName, "");
                                        mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                                        inputType = 1;
                                        mBinding.llTheGroupList.setVisibility(View.GONE);
                                        mBinding.llFillFormList.setVisibility(View.GONE);

                                        mBinding.llReportCard.setVisibility(View.GONE);
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                        mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                                        mBinding.llFirstStage.setVisibility(View.GONE);
                                    }
                                }
                            });


                        } else if (matchRule == 3) {//?????????+?????????   ????????????  ????????????  ????????????  ?????????
                            //                        List??????
                            /*?????????????????? ????????????  */
                            List<MatchScreenFormatBean.MatchTypeBean> matchType = matchScreenFormatBean.getMatchType();
                            formStageAdapter = new FormStageAdapter(GameDetailActivity.this, width);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(GameDetailActivity.this, 4);
                            mBinding.rvFormStage.removeItemDecoration(mSixDiD);
                            mSixDiD = new MyDividerItemDecoration(GameDetailActivity.this, 10,
                                    GameDetailActivity.this.getResources().getColor(R.color.white));
                            mBinding.rvFormStage.addItemDecoration(mSixDiD);
                            //??????manager
                            mBinding.llFormStage.setVisibility(View.VISIBLE);
                            mBinding.rvFormStage.setLayoutManager(gridLayoutManager);
                            mBinding.rvFormStage.setAdapter(formStageAdapter);
                            /*?????????????????????  ??????????????????*/
                            gameGroupLittleAdapter = new GameGroupLittleAdapter();
                            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(GameDetailActivity.this);
                            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//??????????????????????????????????????????
                            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//?????????????????????
//justifyContent ???????????????????????????????????????????????????
                            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//???????????????????????????
                            mBinding.rvLittleGroup.setLayoutManager(flexboxLayoutManager);
                            mBinding.rvLittleGroup.setAdapter(gameGroupLittleAdapter);

                            mBinding.tvGroupTitle.setVisibility(View.VISIBLE);
                            MatchScreenFormatBean.MatchTypeBean matchTypeBean = matchType.get(0);
                            if (matchTypeBean.getInfo() != null && matchTypeBean.getInfo().size() > 0) {
                                mBinding.llLittleGroup.setVisibility(View.VISIBLE);
                                List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = matchTypeBean.getInfo();
                                for (int i = 0; i < info.size(); i++) {
                                    if (i == 0) {
                                        info.get(i).setSelect(true);
                                        groupNumId = "" + info.get(0).getId();
                                    } else {
                                        info.get(i).setSelect(false);
                                    }
                                }
                                /*??????????????????*/
                                gameGroupLittleAdapter.setNewData(info);
                                /*??????*/
                                if (matchTypeBean.getTitle().equals("?????????") || matchTypeBean.getTitle().equals("????????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.VISIBLE);
                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                } else if (matchTypeBean.getTitle().equals("?????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.VISIBLE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                } else if (matchTypeBean.getTitle().equals("????????????") || matchTypeBean.getTitle().equals("?????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);

                                    if (info.get(0).getTitleName().equals("?????????")) {
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    } else {
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    }
                                } else if (matchTypeBean.getTitle().equals("????????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.VISIBLE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                } else {
                                }
                            } else {
                                mBinding.llTheGroupList.setVisibility(View.GONE);
                                mBinding.llFillFormList.setVisibility(View.GONE);

                                mBinding.llReportCard.setVisibility(View.GONE);
                                mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                mBinding.llEliminationDetail.setVisibility(View.GONE);
                                gameGroupLittleAdapter.setNewData(new ArrayList<>());
                                mBinding.llLittleGroup.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < matchType.size(); i++) {
                                if (i == 0) {
                                    matchType.get(i).setSelect(true);
                                    matchFormatId = matchType.get(i).getCode();
                                    matchFormatName = matchType.get(i).getTitle();
                                } else {
                                    matchType.get(i).setSelect(false);
                                }
                            }
                            formStageAdapter.setNewData(matchType);
                            try {
                                groupName = gameGroupLittleAdapter.getData().get(0).getTitleName();
                                Log.e(TAG, "onChanged: 444444444444444");
                                if (matchTypeBean.getTitle().equals("?????????") || matchTypeBean.getTitle().equals("????????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.VISIBLE);
                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                    showLoading();
                                    mViewModel.getMatchArrangeData("" + strMatchId,
                                            "" + projectItemId, "" + groupNumId,
                                            "" + matchFormatId, "" + matchFormatName, "" + groupName);
                                    inputType = 2;
                                } else if (matchTypeBean.getTitle().equals("?????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.VISIBLE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);
                                    inputType = 1;
                                    mViewModel.getSecondGradeNavigation("" + projectItemId);
                                } else if (matchTypeBean.getTitle().equals("????????????")
                                        || matchTypeBean.getTitle().equals("?????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                                    mBinding.llFirstStage.setVisibility(View.GONE);

                                    if (groupName.equals("?????????")) {
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    } else {
                                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    }

                                    //                                mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                    //                                        "" + projectItemId, "" + groupNumId,
                                    //                                        "" + matchFormatId, "" + matchFormatName, "");
                                    mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                                    inputType = 1;
                                } else if (matchTypeBean.getTitle().equals("????????????")) {
                                    mBinding.llTheGroupList.setVisibility(View.GONE);
                                    mBinding.llFillFormList.setVisibility(View.GONE);

                                    mBinding.llReportCard.setVisibility(View.GONE);
                                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                                    mBinding.llFirstStage.setVisibility(View.VISIBLE);
                                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                    showLoading();
                                    mViewModel.getMatchArrangeData("" + strMatchId,
                                            "" + projectItemId, "" + groupNumId,
                                            "" + matchFormatId, "" + matchFormatName, "" + groupName);
                                    inputType = 2;
                                } else {
                                }
                            } catch (Exception e) {
                                //                            toast("??????????????????");
                            }

                            formStageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        clearData();
                                        MatchScreenFormatBean.MatchTypeBean matchTypeBean1 = formStageAdapter.getData().get(position);
                                        List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = matchTypeBean1.getInfo();
                                        List<MatchScreenFormatBean.MatchTypeBean> data1 = formStageAdapter.getData();
                                        for (int i = 0; i < data1.size(); i++) {
                                            data1.get(i).setSelect(false);
                                        }
                                        data1.get(position).setSelect(true);
                                        matchFormatId = "" + data1.get(position).getCode();
                                        matchFormatName = "" + data1.get(position).getTitle();

                                        formStageAdapter.setNewData(data1);
                                        eleminateMatchAdapter.setClickRankingPosition(0);
                                        if (info != null && info.size() > 0) {
                                            for (int m = 0; m < info.size(); m++) {
                                                if (m == 0) {
                                                    info.get(m).setSelect(true);
                                                    groupNumId = "" + info.get(0).getId();
                                                } else {
                                                    info.get(m).setSelect(false);
                                                }
                                            }
                                            gameGroupLittleAdapter.setNewData(info);
                                            mBinding.llLittleGroup.setVisibility(View.VISIBLE);
                                        } else {
                                            gameGroupLittleAdapter.setNewData(new ArrayList<>());
                                            mBinding.llLittleGroup.setVisibility(View.GONE);
                                            groupNumId = "";
                                        }
                                        Log.e(TAG, "onItemClick: 333333333333");
                                        if (matchFormatName.equals("?????????") || matchFormatName.equals("????????????")) {
                                            if (gameGroupLittleAdapter.getData().size() > 0) {
                                                groupName = gameGroupLittleAdapter.getData().get(0).getTitleName();
                                            } else {
                                                groupName = "";
                                            }
                                            inputType = 2;
                                            showLoading();
                                            mViewModel.getMatchArrangeData("" + strMatchId,
                                                    "" + projectItemId, "" + groupNumId,
                                                    "" + matchFormatId, "" + matchFormatName, "" + groupName);
                                        } else if (matchFormatName.equals("?????????")) {
                                            inputType = 1;
                                            mViewModel.getSecondGradeNavigation("" + projectItemId);
                                        } else if (matchFormatName.equals("????????????") || matchFormatName.equals("?????????")) {
                                            inputType = 1;
                                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                                        } else if (matchFormatName.equals("????????????")) {
                                            inputType = 2;
                                            showLoading();
                                            mViewModel.getMatchArrangeData("" + strMatchId,
                                                    "" + projectItemId, "" + groupNumId,
                                                    "" + matchFormatId, "" + matchFormatName, "");
                                        } else {
                                        }
                                    }
                                }
                            });

//                            /*????????????????????? */
                            gameGroupLittleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        clearData();
                                        List<MatchScreenFormatBean.MatchTypeBean.InfoBean> data1 = gameGroupLittleAdapter.getData();
                                        for (int i = 0; i < data1.size(); i++) {
                                            data1.get(i).setSelect(false);
                                        }
                                        data1.get(position).setSelect(true);
                                        gameGroupLittleAdapter.setNewData(data1);
                                        groupNumId = "" + data1.get(position).getId();
                                        groupName = gameGroupLittleAdapter.getData().get(position).getTitleName();
                                        Log.e(TAG, "onItemClick: ?????????????????????==================" + new Gson().toJson(data1.get(position)));

                                        if (data1.get(position).getTitleName().equals("?????????") || data1.get(position).getTitleName().equals("????????????")) {
                                            mBinding.llTheGroupList.setVisibility(View.VISIBLE);

                                            mBinding.llReportCard.setVisibility(View.GONE);
                                            mBinding.llEliminationDetail.setVisibility(View.GONE);
                                            mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                            mBinding.llFirstStage.setVisibility(View.GONE);
                                            inputType = 2;
                                            showLoading();
                                            mViewModel.getMatchArrangeData("" + strMatchId,
                                                    "" + projectItemId, "" + groupNumId,
                                                    "" + matchFormatId, "" + matchFormatName
                                                    , "" + groupName);
                                        } else if (data1.get(position).getTitleName().equals("?????????") || (matchFormatName.equals("????????????") || (matchFormatName.equals("?????????")))) {
                                            mBinding.llTheGroupList.setVisibility(View.GONE);
                                            mBinding.llFillFormList.setVisibility(View.GONE);

                                            mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                            mBinding.llEliminationDetail.setVisibility(View.GONE);
                                            mBinding.llFirstStage.setVisibility(View.GONE);
                                            inputType = 1;
                                            if (data1.get(position).getTitleName().equals("?????????")) {
                                                mBinding.llReportCard.setVisibility(View.VISIBLE);
                                                mViewModel.getSecondGradeNavigation("" + projectItemId);
                                            } else {
                                                mBinding.llReportCard.setVisibility(View.GONE);

                                                mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);
                                            }


                                        } else if (data1.get(position).getTitleName().equals("????????????") || data1.get(position).getTitleName().equals("?????????")
                                                || (matchFormatName.equals("????????????") || (matchFormatName.equals("?????????")))) {
                                            mBinding.llTheGroupList.setVisibility(View.GONE);
                                            mBinding.llFillFormList.setVisibility(View.GONE);

                                            mBinding.llReportCard.setVisibility(View.GONE);
                                            mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                                            mBinding.llFirstStage.setVisibility(View.GONE);
                                            inputType = 1;
                                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);


                                        } else if (data1.get(position).getTitleName().equals("????????????")) {
                                            mBinding.llTheGroupList.setVisibility(View.GONE);
                                            mBinding.llFillFormList.setVisibility(View.GONE);

                                            mBinding.llReportCard.setVisibility(View.GONE);
                                            mBinding.llEliminationDetail.setVisibility(View.GONE);
                                            mBinding.llFirstStage.setVisibility(View.VISIBLE);
                                            mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                            inputType = 2;
                                            showLoading();
                                            mViewModel.getMatchArrangeData("" + strMatchId,
                                                    "" + projectItemId, "" + groupNumId,
                                                    "" + matchFormatId, "" + matchFormatName
                                                    , "" + groupName);
                                        } else {

                                            mBinding.llTheGroupList.setVisibility(View.VISIBLE);
                                            mBinding.llReportCard.setVisibility(View.GONE);
                                            mBinding.llEliminationDetail.setVisibility(View.GONE);
                                            mBinding.llTaotaiRanking.setVisibility(View.GONE);
                                            mBinding.llFirstStage.setVisibility(View.GONE);
                                            inputType = 2;
                                            showLoading();
                                            mViewModel.getMatchArrangeData("" + strMatchId,
                                                    "" + projectItemId, "" + groupNumId,
                                                    "" + matchFormatId, "" + matchFormatName
                                                    , "" + groupName);
//                                            mViewModel.getSecondTaoTaiNavigation("" + projectItemId, "" + groupNumId);

                                        }
                                    }
                                }
                            });
                        }


                    }
                } else {
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    toast("" + matchScreenFormatBeanResponseBean.getMessage());
                }
            }
        });

        /*?????????????????????*/
        mViewModel.getSecondTaoTaiLiveData.observe(this, new Observer<ResponseBean<SecondNavigationFourBean>>() {
            @Override
            public void onChanged(ResponseBean<SecondNavigationFourBean> secondNavigationFourBeanResponseBean) {
                if (secondNavigationFourBeanResponseBean.getErrorCode().equals("200")) {

                    SecondNavigationFourBean data = secondNavigationFourBeanResponseBean.getData();
                    if (data != null) {
                        eleminateMatchAdapter.setClickRankingPosition(0);

                        String navigation = "";
                        winCount = data.getWinCount();
                        if (data.getNavigation() != null && data.getNavigation().size() > 0) {
                            //???????????? ??????????????? ??????????????????  ?????? ??????  ??????????????? ??????
                            mBinding.llTaotaiRanking.setVisibility(View.VISIBLE);

                            navigation = data.getNavigation().get(0);
                            Log.e(TAG, "onChanged: navigation===" + navigation);

                            List<RefereeDisplayTimeBean> myNavigationFourBeans = new ArrayList<>();
                            for (int i = 0; i < data.getNavigation().size(); i++) {
                                RefereeDisplayTimeBean secondMyNavigationFourBean = new RefereeDisplayTimeBean();
                                secondMyNavigationFourBean.setTime("" + data.getNavigation().get(i));
                                if (i == 0) {
                                    secondMyNavigationFourBean.setSelect(true);
                                } else {
                                    secondMyNavigationFourBean.setSelect(false);
                                }
                                myNavigationFourBeans.add(secondMyNavigationFourBean);
                            }
                            mBinding.rvRanking.setAdapter(refereeDisplayAdapter);
                            Log.e(TAG, "onChanged:myNavigationFourBeans==== " + new Gson().toJson(myNavigationFourBeans));

                            refereeDisplayAdapter.setNewData(myNavigationFourBeans);
                            mBinding.rvRanking.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.HORIZONTAL, false));
                            refereeDisplayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        List<RefereeDisplayTimeBean> data1 = refereeDisplayAdapter.getData();
                                        for (int i = 0; i < refereeDisplayAdapter.getData().size(); i++) {

                                            RefereeDisplayTimeBean refereeDisplayTimeBean1 = data1.get(i);
                                            refereeDisplayTimeBean1.setSelect(false);
                                            data1.set(i, refereeDisplayTimeBean1);
                                        }
                                        eleminateMatchAdapter.setClickRankingPosition(position);

                                        data1.get(position).setSelect(true);
                                        refereeDisplayAdapter.setNewData(data1);
                                        clearData();
                                        showLoading();
                                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                                "" + projectItemId, "" + groupNumId,
                                                "" + matchFormatId, "" + matchFormatName, "", "" + data1.get(position).getTime());
                                    }
                                }
                            });


                        } else {
                            //                            winCount = 0;
                            winCount = data.getWinCount();
                            //????????????  ????????????   ?????? ??????
                            navigation = "";
                            mBinding.llTaotaiRanking.setVisibility(View.GONE);

                        }
                        showLoading();
                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                "" + projectItemId, "" + groupNumId,
                                "" + matchFormatId, "" + matchFormatName, "", "" + navigation);
                    } else {
                        winCount = 0;
                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                        refereeDisplayAdapter.setNewData(new ArrayList<>());
                        showLoading();
                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                "" + projectItemId, "" + groupNumId,
                                "" + matchFormatId, "" + matchFormatName, "", "");
                    }

                } else if (secondNavigationFourBeanResponseBean.getErrorCode().equals("100000-100040")) {
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    refereeDisplayAdapter.setNewData(new ArrayList<>());
                    winCount = 0;
                    showLoading();
                    mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                            "" + projectItemId, "" + groupNumId,
                            "" + matchFormatId, "" + matchFormatName, "", "");
                } else {
                    winCount = 0;
                    toast("" + secondNavigationFourBeanResponseBean.getMessage());
                }
            }
        });
        /* ????????? ???????????? ??????*/
        mViewModel.getSecondNavigation4LiveData.observe(this, new Observer<ResponseBean<SecondNavigationFourBean>>() {
            @Override
            public void onChanged(ResponseBean<SecondNavigationFourBean> secondNavigationFourBeanResponseBean) {
                if (secondNavigationFourBeanResponseBean.getErrorCode().equals("200")) {
                    SecondNavigationFourBean data = secondNavigationFourBeanResponseBean.getData();
                    if (data != null) {
                        eleminateMatchAdapter.setClickRankingPosition(0);
                        winCount = data.getWinCount();
                        String navigation = "";
                        if (data.getNavigation() != null && data.getNavigation().size() > 0) {
                            mBinding.llTaotaiRanking.setVisibility(View.VISIBLE);

                            //???????????? ??????????????? ??????????????????  ?????? ??????  ??????????????? ??????
                            navigation = data.getNavigation().get(0);
                            Log.e(TAG, "onChanged: navigation===" + navigation);
                            List<RefereeDisplayTimeBean> myNavigationFourBeans = new ArrayList<>();
                            for (int i = 0; i < data.getNavigation().size(); i++) {
                                RefereeDisplayTimeBean secondMyNavigationFourBean = new RefereeDisplayTimeBean();
                                secondMyNavigationFourBean.setTime("" + data.getNavigation().get(i));
                                if (i == 0) {
                                    secondMyNavigationFourBean.setSelect(true);
                                } else {
                                    secondMyNavigationFourBean.setSelect(false);
                                }
                                myNavigationFourBeans.add(secondMyNavigationFourBean);
                            }
                            mBinding.rvRanking.setAdapter(refereeDisplayAdapter);
                            mBinding.rvRanking.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, RecyclerView.HORIZONTAL, false));
                            refereeDisplayAdapter.setNewData(myNavigationFourBeans);
                            Log.e(TAG, "onChanged:myNavigationFourBeans==== " + new Gson().toJson(myNavigationFourBeans));
                            refereeDisplayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        List<RefereeDisplayTimeBean> data1 = refereeDisplayAdapter.getData();
                                        for (int i = 0; i < refereeDisplayAdapter.getData().size(); i++) {
                                            RefereeDisplayTimeBean refereeDisplayTimeBean1 = data1.get(i);
                                            refereeDisplayTimeBean1.setSelect(false);
                                            data1.set(i, refereeDisplayTimeBean1);
                                        }
                                        data1.get(position).setSelect(true);
                                        eleminateMatchAdapter.setClickRankingPosition(position);
                                        refereeDisplayAdapter.setNewData(data1);
                                        clearData();
                                        showLoading();
                                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                                "" + projectItemId, "" + groupNumId,
                                                "" + matchFormatId, "" + matchFormatName, "", "" + data1.get(position).getTime());
                                    }
                                }
                            });
                        } else {
                            //????????????  ????????????   ?????? ??????
                            navigation = "";
                            winCount = data.getWinCount();
                            mBinding.llTaotaiRanking.setVisibility(View.GONE);
                        }
                        showLoading();
                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                "" + projectItemId, "" + groupNumId,
                                "" + matchFormatId, "" + matchFormatName, "", "" + navigation);
                    } else {
                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                        refereeDisplayAdapter.setNewData(new ArrayList<>());
                        winCount = 0;
                        showLoading();
                        mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                                "" + projectItemId, "" + groupNumId,
                                "" + matchFormatId, "" + matchFormatName, "", "");
                    }

                } else if (secondNavigationFourBeanResponseBean.getErrorCode().equals("100000-100040")) {
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    refereeDisplayAdapter.setNewData(new ArrayList<>());
                    winCount = 0;
                    showLoading();
                    mViewModel.getMatchTaoTaiArrangeData("" + strMatchId,
                            "" + projectItemId, "" + groupNumId,
                            "" + matchFormatId, "" + matchFormatName, "", "");
                } else {
                    winCount = 0;
                    toast("" + secondNavigationFourBeanResponseBean.getMessage());
                }


            }
        });

        //1 ?????? 2 ??????  3 ??????   4 ?????????????????????????????????
        /*?????????*/
        mViewModel.getMatchArrangeRoundLiveData.observe(this, new Observer<ResponseBean<MatchArrangeRoundBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchArrangeRoundBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    if (arrangeData != null) {
                        arrangeData.clear();
                    }
                    MatchArrangeRoundBean data = responseBean.getData();
                    String itemType = data.getItemType();
                    int types = Integer.parseInt(itemType);//1 ?????? 2 ?????? 3 ?????? 4??????
                    /*1:??????????????????????????????2:???????????????????????????,3????????????  4?????????*/
                    arrangeData = data.getArrangeData();
                    formTitle = data.getFormTitle();
                    String name = data.getName();
                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llReportCard.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    mBinding.tvTheGroupListTitle.setText("" + formTitle + "????????????");
                    initTheGroupList(types, formTitle, name);


                }/**/ else {
                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llReportCard.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    toast("" + responseBean.getMessage());
                }
            }
        });
        /*?????????*/
        mViewModel.getMatchKnockOutMatchLiveData.observe(this, new Observer<ResponseBean<MatchArrangeKnockOutMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchArrangeKnockOutMatchBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    MatchArrangeKnockOutMatchBean data = responseBean.getData();
                    String itemType = data.getItemType();
                    int types = Integer.parseInt(itemType);//1 ?????? 2 ?????? 3 ?????? 4??????
                    formTitle = responseBean.getData().getFormTitle();
                    mBinding.tvTheGroupListTitle.setText("" + formTitle + "????????????");
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llFirstStage.setVisibility(View.GONE);
                    //                    mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
                    mBinding.llReportCard.setVisibility(View.GONE);
                    if (data != null && data.getOutList() != null) {
                        initEliminationMatches(types, data);
                    } else {
                        toast("?????????");
                    }
                } else {
                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llReportCard.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);

                    toast("" + responseBean.getMessage());
                }

            }
        });
        /*????????????*/
        mViewModel.getMatchGroupListLiveData.observe(this, new Observer<ResponseBean<MatchArrangeGroupListBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchArrangeGroupListBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    MatchArrangeGroupListBean data = responseBean.getData();
                    String itemType = data.getItemType();
                    int types = Integer.parseInt(itemType);//1 ?????? 2 ?????? 3 ?????? 4??????
                    formTitle = data.getFromTitle();
                    mBinding.tvFirstStageTitle.setText("" + formTitle + "????????????");
                    List<MatchArrangeGroupListBean.GroupDtoBean> groupDto = data.getGroupDto();
                    if (groupDto != null && groupDto.size() > 0) {

                        //                        mBinding.llFirstStage.setVisibility(View.VISIBLE);
                        mBinding.llTheGroupList.setVisibility(View.GONE);
                        mBinding.llFillFormList.setVisibility(View.GONE);

                        mBinding.llEliminationDetail.setVisibility(View.GONE);
                        mBinding.llReportCard.setVisibility(View.GONE);
                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                        initChuZu(types, data);
                    } else {
                        toast("????????????");
                        mBinding.llTheGroupList.setVisibility(View.GONE);
                        mBinding.llFillFormList.setVisibility(View.GONE);

                        mBinding.llEliminationDetail.setVisibility(View.GONE);
                        mBinding.llReportCard.setVisibility(View.GONE);
                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    }


                } else {
                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llReportCard.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    toast("" + responseBean.getMessage());
                }

            }
        });
        /*?????????*/
        mViewModel.getMatchScoreLiveData.observe(this, new Observer<ResponseBean<MatchArrangeMatchScoreBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchArrangeMatchScoreBean> responseBean) {
                dismissLoading();
                /*?????????*/
                if (responseBean.getErrorCode().equals("200")) {
                    MatchArrangeMatchScoreBean data = responseBean.getData();

                    String itemType = data.getItemType();
                    int types = Integer.parseInt(itemType);//1 ?????? 2 ?????? 3 ?????? 4??????
                    formTitle = data.getFormTitle();
                    mBinding.tvReportCardTitle.setText("" + formTitle + "????????????");
                    //                    mBinding.llReportCard.setVisibility(View.VISIBLE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    //???????????? ?????????????????????
                    //                    mViewModel.getSecondNavigation4("" + projectItemId, "");
                    initMatchScore(types, data);
                } else {
                    mBinding.llEliminationDetail.setVisibility(View.GONE);
                    mBinding.llFirstStage.setVisibility(View.GONE);
                    mBinding.llTheGroupList.setVisibility(View.GONE);
                    mBinding.llFillFormList.setVisibility(View.GONE);

                    mBinding.llReportCard.setVisibility(View.GONE);
                    mBinding.llTaotaiRanking.setVisibility(View.GONE);
                    toast("" + responseBean.getMessage());
                }

            }
        });





        /*??????????????????*/
        mViewModel.queryPointsDetailsLiveData.observe(this, new Observer<ResponseBean<QueryPointDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryPointDetailBean> responseBean) {

                if (responseBean.getData().getPointsDetailsDtos() != null && responseBean.getData().getPointsDetailsDtos().size() > 0) {
                    QueryPointDetailBean data = responseBean.getData();
                    int itemType = Integer.parseInt(data.getItemType());
                    leftOfficialIntergal.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                    topOfficialIntergal.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));


                    listOfficial.clear();
                    titleLeftOfficial.clear();
                    topListOfficial.clear();
                    List<QueryPointDetailBean.PointsDetailsDtosBean> pointsDetailsDtos = data.getPointsDetailsDtos();
                    List<String> chess2 = pointsDetailsDtos.get(0).getChess();

                    for (int i = 0; i < pointsDetailsDtos.size(); i++) {
                        List<String> chess1 = pointsDetailsDtos.get(i).getChess();
                        /*??????????????????????????? ????????????*/
                        String arrange = "";
                        if (itemType == 1 || itemType == 2) {
                            arrange = pointsDetailsDtos.get(i).getPlayer1() + "vs" + pointsDetailsDtos.get(i).getPlayer3();
                        } else {
                            arrange = (pointsDetailsDtos.get(i).getPlayer1() + "\n" + pointsDetailsDtos.get(i).getPlayer2())
                                    + "vs" + (pointsDetailsDtos.get(i).getPlayer3() + "\n" + pointsDetailsDtos.get(i).getPlayer4());
                        }
                        titleLeftOfficial.add(arrange);
                        for (int j = 0; j < chess1.size(); j++) {
                            Monster monster = new Monster();

                            monster.setIntergal(chess1.get(j));
                            listOfficial.add(monster);
                        }
                    }
                    int size = chess2.size();
                    for (int j = 0; j < size; j++) {
                        if (j == size - 1) {
                            topListOfficial.add("?????????");
                        } else {
                            String round = CommonUtilis.numberToChinese(j + 1);
                            topListOfficial.add("???" + round + "???");
                        }
                    }

                    TitleIntergalTopOfficialAdapter topAdapters = new TitleIntergalTopOfficialAdapter();
                    TitleIntergalLeftOfficialAdapter leftAdapter = new TitleIntergalLeftOfficialAdapter();
                    leftOfficialIntergal.setAdapter(leftAdapter);
                    topOfficialIntergal.setAdapter(topAdapters);
                    leftAdapter.setNewDatas(titleLeftOfficial);
                    topAdapters.setDataList(topListOfficial);

                    FormLayoutManager layoutManager = new FormLayoutManager(chess2.size(), detailOfficialIntergal);
                    detailOfficialIntergal.setLayoutManager(layoutManager);

                    MonsterIntergalDetailAdapter adapter = new MonsterIntergalDetailAdapter(GameDetailActivity.this, listOfficial);
                    detailOfficialIntergal.setAdapter(adapter);


                    //                    MonsterIntergalDetailAdapter adapter = new MonsterIntergalDetailAdapter(GameUnderWayScoreActivity.this, list);
                    //                    mIntergalDetailView.setAdapter(adapter);
                    //                    ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
                    //                    layoutParams.height = leftTitles.size() * 88;
                    //                    rvLeftTitle.setLayoutParams(layoutParams);
                    //
                    //                    ViewGroup.LayoutParams layoutParams1 = rvTopTitle.getLayoutParams();
                    //                    layoutParams1.height = leftTitles.size() * 88;
                    //                    rvTopTitle.setLayoutParams(layoutParams1);
                    //                    ViewGroup.LayoutParams layoutParams = rvIntergalTopTitle.getLayoutParams();
                    //                    rvIntergalTopTitle.setLayoutParams(layoutParams);
                    //                    FormScrollHelper formScrollHelper = new FormScrollHelper();
                    //                    formScrollHelper.connectRecyclerView(mIntergalDetailView);
                    //                    formScrollHelper.connectRecyclerView(rvIntergalTopTitle);
                    //                    formScrollHelper.connectRecyclerView(rvIntergalLeftTitle);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    private void clearData() {
        mBinding.llEliminationDetail.setVisibility(View.GONE);
        mBinding.llFirstStage.setVisibility(View.GONE);
        mBinding.llTheGroupList.setVisibility(View.GONE);
        mBinding.llFillFormList.setVisibility(View.GONE);

        mBinding.llReportCard.setVisibility(View.GONE);
//        int size = refereeDisplayAdapter.getData().size();
//        if (size == 1) {
//
//        } else {
//            mBinding.llTaotaiRanking.setVisibility(View.GONE);
//        }
    }

    FormLayoutManager formLayoutManager;

    /*?????????   ????????????*/
    private void initTheGroupList(int types, String groupName, String name) {
        monsterList.clear();

        mViewModel.getMatchAgainstData("" + strMatchId, "" + projectItemId, "" + groupNumId, "" + matchFormatId,
                "" + matchFormatName, "");
        TextView tvNumGroupList = findViewById(R.id.tv_num_group_list);
        tvNumGroupList.setVisibility(View.VISIBLE);
        if (name.equals("?????????")) {
            tvNumGroupList.setText(name);
        } else {
            tvNumGroupList.setText(name + "???");
        }
        //1 ?????? 2 ?????? 3 ?????? 4??????
        if (arrangeData != null && arrangeData.size() > 0) {
            if (topTitles != null) {
                topTitles.clear();
            }
            if (leftTitles != null) {
                leftTitles.clear();
            }

            for (int i = 0; i < arrangeData.size(); i++) {
                TitleBean titleBean = new TitleBean();
                titleBean.setType(1);
                titleBean.setShowType(1);
                String clubName = arrangeData.get(i).getClubName();
                String player1Name = arrangeData.get(i).getPlayer1Name();
                String player2Name = arrangeData.get(i).getPlayer2Name();
                String joinId = "" + arrangeData.get(i).getJoinid();
                String matchid = "" + arrangeData.get(i).getMatchid();
                String clubId = "" + arrangeData.get(i).getClubId();
                if (types == 1) {
                    titleBean.setTitle("" + clubName);
                } else if (types == 2) {
                    titleBean.setTitle("" + player1Name);
                    titleBean.setClubName("" + clubName);
                } else {
                    titleBean.setTitle("" + player1Name + "/" + player2Name);
                    titleBean.setClubName("" + clubName);
                }
                titleBean.setId(joinId);
                titleBean.setMatchId(matchid);
                titleBean.setClubId(clubId);

                titleBean.setItemType("" + types);
                titleBean.setLeftPosition("" + arrangeData.get(i).getPlayer1Name());
                topTitles.add(titleBean);
                leftTitles.add(titleBean);
                if (showType == 1) {
                    if (i == arrangeData.size() - 1) {
                        TitleBean titleBeanTop = new TitleBean("??????", -1);
                        titleBeanTop.setShowType(555);
                        topTitles.add(titleBeanTop);
                        TitleBean titleBeanTop2 = new TitleBean("??????", -2);
                        titleBeanTop2.setShowType(555);
                        topTitles.add(titleBeanTop2);

                        TitleBean titleBeanTop3 = new TitleBean("??????", -3);
                        titleBeanTop3.setShowType(555);
                        topTitles.add(titleBeanTop3);

                    }
                } else if (showType == 2) {
                    if (i == arrangeData.size() - 1) {
                        TitleBean titleBeanLeft = new TitleBean("??????", -1);
                        leftTitles.add(titleBeanLeft);
                        TitleBean titleBeanLeft2 = new TitleBean("??????", -2);
                        leftTitles.add(titleBeanLeft2);
                        TitleBean titleBeanLeft3 = new TitleBean("??????", -3);
                        leftTitles.add(titleBeanLeft3);
                    }
                }
                MatchArrangeRoundBean.ArrangeDataBean resultListDataBean = arrangeData.get(i);
                List<MatchArrangeRoundBean.ArrangeDataBean.ArrangeListBean> arrangeList1 = resultListDataBean.getArrangeList();
                for (int j = 0; j < arrangeList1.size(); j++) {
                    MatchArrangeRoundBean.ArrangeDataBean.ArrangeListBean arrangeList1Bean = arrangeList1.get(j);
                    Monster monster = new Monster();
                    monster.setId("" + arrangeList1Bean.getId());
                    monster.setTeamId("" + resultListDataBean.getGroupNum());
                    if (TextUtils.isEmpty(String.valueOf(arrangeList1Bean.getLeftPosition()))) {
                        monster.setType(0);
                        monster.setShowType(2);
                    } else {
                        monster.setShowType(2);
                        //1 3 4 5 6 ??????
                        if (TextUtils.isEmpty(arrangeList1Bean.getStatus())) {
                            //                                   /*?????????*/
                            monster.setType(1);
                            monster.setTableNum("" + arrangeList1Bean.getTableNum());//?????????
                            monster.setId("" + arrangeList1Bean.getId());
                            monster.setTableNum("" + arrangeList1Bean.getTableNum());
                            monster.setMatchId("" + arrangeList1Bean.getMatchId());
                            monster.setDay("" + arrangeList1Bean.getDay());
                            //????????????????????????
                            String substring = arrangeList1Bean.getTime().substring(0, arrangeList1Bean.getTime().length() - 3);
                            monster.setMinute("" + substring);
                            //                            int id = monster.getId();
                            //                            String matchId = monster.getMatchId();
                            //                            String tableNum = monster.getTableNum();
                        } else if (arrangeList1Bean.getStatus().equals("1") || arrangeList1Bean.getStatus().equals("3")
                                || arrangeList1Bean.getStatus().equals("4") || arrangeList1Bean.getStatus().equals("5")
                                || arrangeList1Bean.getStatus().equals("6") || arrangeList1Bean.getStatus().equals("2")) {
                            /*????????????????????????*/
                            int leftWavier;
                            int rightWavier;
                            int leftWincount;
                            int rightWinCount;
                            if (showType == 1) {
                                leftWavier = Integer.parseInt(arrangeList1Bean.getLeftWaiver());
                                rightWavier = Integer.parseInt(arrangeList1Bean.getRightWaiver());
                                leftWincount = Integer.parseInt(arrangeList1Bean.getLeftWinCount());
                                rightWinCount = Integer.parseInt(arrangeList1Bean.getRightWinCount());
                            } else {
                                leftWavier = Integer.parseInt(arrangeList1Bean.getRightWaiver());
                                rightWavier = Integer.parseInt(arrangeList1Bean.getLeftWaiver());
                                leftWincount = Integer.parseInt(arrangeList1Bean.getRightWinCount());
                                rightWinCount = Integer.parseInt(arrangeList1Bean.getLeftWinCount());
                            }
                            /*????????????????????????*/
                            /*???????????????*/
                            if (leftWavier == 1 && rightWavier == 1) {
                                monster.setType(6);
                                monster.setScore("W-" + leftWincount + " : " + "W-" + rightWinCount);
                                monster.setShowScore(0);
                            } else {

                                if (leftWavier == 1 && rightWavier == 0) {

                                    monster.setScore("W-" + leftWincount + " : " + "" + rightWinCount);
                                    if (leftWincount == rightWinCount) {
                                        monster.setShowScore(0);
                                    } else if (leftWincount > rightWinCount) {
                                        monster.setShowScore(2);
                                    } else {
                                        monster.setShowScore(0);
                                    }
                                    monster.setType(5);
                                } else if (leftWavier == 0 && rightWavier == 1) {
                                    monster.setType(4);
                                    if (leftWincount == rightWinCount) {
                                        monster.setShowScore(0);
                                    } else if (leftWincount > rightWinCount) {
                                        monster.setShowScore(2);
                                    } else {
                                        monster.setShowScore(0);
                                    }
                                    monster.setScore("" + "" + leftWincount + " : W-" + rightWinCount);
                                } else {
                                    //                    null?????????,1????????????2????????????3?????????????????????4?????????????????????6?????????????????????7???????????????????????????8???????????????????????????9??????????????????10???????????????

                                    if (arrangeList1Bean.getStatus().equals("2")) {
                                        monster.setType(9);//????????????2

                                        /*type?????????7*/
                                        if (leftWincount == rightWinCount) {
                                            monster.setShowScore(2);
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                            /*???????????? ??????????????? ??????????????????*/
                                        } else if (leftWincount > rightWinCount) {
                                            /*???????????? ??????????????????*/
                                            monster.setShowScore(2);
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                        } else {
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                            monster.setShowScore(1);
                                        }
                                    } else {
                                        /*type?????????7*/
                                        if (leftWincount == rightWinCount) {
                                            monster.setType(9);//????????????2
                                            monster.setShowScore(2);
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                            /*???????????? ??????????????? ??????????????????*/
                                        } else if (leftWincount > rightWinCount) {
                                            /*???????????? ??????????????????*/
                                            monster.setType(2);//????????????2
                                            monster.setShowScore(2);
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                        } else {
                                            monster.setType(3);//????????????3
                                            monster.setScore("" + leftWincount + " : " + rightWinCount);
                                            monster.setShowScore(1);
                                        }
                                    }
                                }
                            }
                        } else {
                            /*?????????*/
                            monster.setType(1);
                            monster.setTableNum("" + arrangeList1Bean.getTableNum());//?????????
                            monster.setId("" + arrangeList1Bean.getId());
                            monster.setTableNum("" + arrangeList1Bean.getTableNum());
                            monster.setMatchId("" + arrangeList1Bean.getMatchId());
                            monster.setDay("" + arrangeList1Bean.getDay());
                            //????????????????????????
                            String substring = arrangeList1Bean.getTime().substring(0, arrangeList1Bean.getTime().length() - 3);
                            monster.setMinute("" + substring);

                        }
                    }
                    monsterList.add(monster);


                    if (showType == 1) {
                        if (j == arrangeList1.size() - 1) {
                            Monster monster1 = new Monster();
                            monster1.setType(-1);
                            if (showType == 1) {
                                monster1.setShowType(1);
                            } else {
                                monster1.setShowType(2);
                            }

                            monster1.setIntergal("" + resultListDataBean.getIntegral());
                            monsterList.add(monster1);
                            Monster monster2 = new Monster();
                            if (TextUtils.isEmpty(resultListDataBean.getResult2())) {
                                if (TextUtils.isEmpty(resultListDataBean.getResult1())) {
                                    monster2.setResult("");
                                } else {
                                    monster2.setResult("1-" + resultListDataBean.getResult1());
                                }
                            } else {
                                monster2.setResult("2-" + resultListDataBean.getResult2());
                            }
                            monster2.setType(-2);
                            monster2.setIntergal("" + resultListDataBean.getIntegral());
                            monster2.setTeamId("" + resultListDataBean.getGroupNum());
                            if (showType == 1) {
                                monster2.setShowType(1);
                            } else {
                                monster2.setShowType(2);
                            }
                            monsterList.add(monster2);
                            Monster monster3 = new Monster();
                            monster3.setRanking("" + resultListDataBean.getRanking());
                            monster3.setQianNum(resultListDataBean.getQianNum());
                            monster3.setType(-3);
                            if (showType == 1) {
                                monster3.setShowType(1);
                            } else {
                                monster3.setShowType(2);
                            }
                            monsterList.add(monster3);

                        }

                    }

                }
            }

            if (showType == 2) {
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        for (int j = 0; j < arrangeData.size(); j++) {
                            Monster monster = new Monster();
                            monster.setType(-1);
                            if (showType == 1) {
                                monster.setShowType(1);
                            } else {
                                monster.setShowType(2);
                            }

                            monster.setId("" + arrangeData.get(j).getId());
                            monster.setTeamId("" + arrangeData.get(j).getGroupNum());
                            monster.setIntergal("" + arrangeData.get(j).getIntegral());
                            monsterList.add(monster);
                        }
                    } else if (i == 1) {
                        for (int j = 0; j < arrangeData.size(); j++) {
                            Monster monster = new Monster();
                            monster.setId("" + arrangeData.get(j).getId());
                            if (TextUtils.isEmpty(arrangeData.get(j).getResult2())) {
                                if (TextUtils.isEmpty(arrangeData.get(j).getResult1())) {
                                    monster.setResult("");
                                } else {
                                    monster.setResult("1-" + arrangeData.get(j).getResult1());
                                }
                            } else {
                                monster.setResult("2-" + arrangeData.get(j).getResult2());
                            }
                            monster.setType(-2);
                            monster.setIntergal("" + arrangeData.get(j).getIntegral());
                            monster.setTeamId("" + arrangeData.get(j).getGroupNum());

                            if (showType == 1) {
                                monster.setShowType(1);
                            } else {
                                monster.setShowType(2);
                            }

                            monsterList.add(monster);
                        }

                    } else if (i == 2) {
                        for (int j = 0; j < arrangeData.size(); j++) {
                            Monster monster = new Monster();
                            monster.setId("" + arrangeData.get(j).getId());
                            monster.setRanking("" + arrangeData.get(j).getRanking());
                            monster.setQianNum(arrangeData.get(j).getQianNum());

                            monster.setType(-3);
                            if (showType == 1) {
                                monster.setShowType(1);
                            } else {
                                monster.setShowType(2);
                            }
                            monsterList.add(monster);
                        }

                    }
                }
            }
            Gson gson = new Gson();
            String s = gson.toJson(monsterList);
            //            Log.e(TAG, "onChanged: size===" + monsterList.size());
            //            Log.e(TAG, "onChanged: okhttp===" + s);

            /*?????? ?????????????????? ????????????????????????*/
            /*?????????????????????????????? ???????????????*/


            rvGroupTopList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            TitleGroupTopListAdapter topAdapter = new TitleGroupTopListAdapter(topTitles);
            rvGroupTopList.setAdapter(topAdapter);
            /*?????????????????????   ????????????????????? ??????????????????  ????????????????????????*/
            Log.e(TAG, "initTheGroupList:leftTitles-=== " + new Gson().toJson(leftTitles));
            TitleGroupLeftListAdapter titleGroupLeftListAdapter = new TitleGroupLeftListAdapter(leftTitles);
            rvGroupLeftList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvGroupLeftList.setAdapter(titleGroupLeftListAdapter);


//            formLayoutManager = new FormLayoutManager(topTitles.size(), mRecyclerViewGroup);

            if (monsterOfficialHAdapter == null) {
                monsterOfficialHAdapter = new MonsterOfficialHAdapter(GameDetailActivity.this, monsterList, leftTitles.size(), topTitles.size());
            } else {
                monsterOfficialHAdapter.setList(monsterList, leftTitles.size(), topTitles.size());
            }
            Log.e(TAG, "initTheGroupList: top size===" + topTitles.size());
            Log.e(TAG, "initTheGroupList: left size===" + leftTitles.size());
            mRecyclerViewGroup.setAdapter(monsterOfficialHAdapter);
            myFormLayoutManager.setColumnCount(topTitles.size());
            mRecyclerViewGroup.setLayoutManager(myFormLayoutManager);


            topAdapter.setMyListener(new TitleGroupTopListAdapter.onOwnMyListener() {
                @Override
                public void OnMyListener(int itemType, TitleBean titleBean) {
                    /*?????????????????????????????????  1?????? 2?????? */
                    String id = titleBean.getId();
                    String clubId = titleBean.getClubId();
                    String matchId = titleBean.getMatchId();
                    if (titleBean.getTitle().equals("??????") || titleBean.getTitle().equals("??????") ||
                            titleBean.getTitle().equals("??????")) {

                    } else {
                        if (!TextUtils.isEmpty(titleBean.getLeftPosition())) {
                            Map<String, Object> game = new HashMap<String, Object>();
                            game.put("ciculate_gameDetail_userInfo", "?????????");//?????????????????????????????????????????????
                            MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                            TCAgent.onEvent(GameDetailActivity.this, "ciculate_gameDetail_userInfo", "?????????");
                            if (itemType == 2) {
                                MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                        "" + id, 1);
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(GameDetailActivity.this, TeamNumbersActivity.class);
                                intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                                intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + matchId);
                                intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + clubId);
                                startActivity(intent);
                            }
                        }
                    }
                }
            });
            titleGroupLeftListAdapter.setMyListener(new TitleGroupLeftListAdapter.onOwnMyListener() {
                @Override
                public void OnMyListener(int itemType, TitleBean titleBean) {
                    /*?????????????????????????????????  1?????? 2?????? */
                    String id = titleBean.getId();
                    String clubId = titleBean.getClubId();
                    String matchId = titleBean.getMatchId();
                    if (titleBean.getTitle().equals("??????") || titleBean.getTitle().equals("??????") ||
                            titleBean.getTitle().equals("??????")) {

                    } else {
                        if (itemType == 2) {
                            if (!TextUtils.isEmpty(titleBean.getLeftPosition())) {
                                Map<String, Object> game = new HashMap<String, Object>();
                                game.put("ciculate_gameDetail_userInfo", "?????????");//?????????????????????????????????????????????
                                MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                                TCAgent.onEvent(GameDetailActivity.this, "ciculate_gameDetail_userInfo", "?????????");
                                if (!TextUtils.isEmpty(id)) {
                                    MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                            "" + id, 1);
                                }
                            }
                        } else {
                            if (!TextUtils.isEmpty(id)) {
                                if (!TextUtils.isEmpty(titleBean.getLeftPosition())) {
                                    Map<String, Object> game = new HashMap<String, Object>();
                                    game.put("ciculate_gameDetail_userInfo", "?????????");//?????????????????????????????????????????????
                                    MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                                    TCAgent.onEvent(GameDetailActivity.this, "ciculate_gameDetail_userInfo", "?????????");
                                    Intent intent = new Intent();
                                    intent.setClass(GameDetailActivity.this, TeamNumbersActivity.class);
                                    intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                                    intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + matchId);
                                    intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + clubId);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
            });


            monsterOfficialHAdapter.setListener(new MonsterOfficialHAdapter.onListener() {
                @Override
                public void OnListener(Monster monster, int position, int type) {
                    String teamId = monster.getTeamId();
                    String id = "" + monster.getId();
                    String left1Name = monster.getLeft1Name();
                    switch (type) {
                        case 0:
                            showLoading();
                            mViewModel.phpAes();

                            break;
                        case 1://?????????tableNum   ??????????????????
                            Map<String, Object> game = new HashMap<String, Object>();
                            game.put("ciculate_gameDetail_tableNum", "?????????");//?????????????????????????????????????????????
                            MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                            TCAgent.onEvent(GameDetailActivity.this, "ciculate_gameDetail_tableNum", "?????????");

                            String matchId = monster.getMatchId();
                            String tableNum = monster.getTableNum();
                            ReferenceVisitorMatchActivity.goMatchActivity(GameDetailActivity.this,
                                    "" + tableNum, "" + matchId, "" + id, "1");
                            break;
                        case -2://??????   ?????????????????????UI
                            //                        ???????????? ??????
                            Log.e(TAG, "OnListener: left1Name===" + left1Name);
                            String result = monster.getResult();
                            if (!TextUtils.isEmpty(result)) {
                                String intergal = monster.getIntergal();
                                mViewModel.queryComputeInfo("" + strMatchId, "" + projectItemId, "" + teamId
                                        , "" + intergal);
                            }
                            break;
                        case 2://?????????
                        case 5://?????????  ????????????  //??????????????????id ??????????????? ??????????????????
                        case 3://?????????
                        case 4:////?????????  ????????????
                        case 6://???????????????
                            Map<String, Object> game6 = new HashMap<String, Object>();
                            game6.put("ciculate_gameDetail_score", "?????????");//?????????????????????????????????????????????
                            MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game6);
//                            TCAgent.onEvent(GameDetailActivity.this, " ciculate_gameDetail_score", "?????????");

                            if (types == 1) {
                                //1 ??????
                                Intent intent = new Intent(GameDetailActivity.this, TeamResultsScoreOfficialActivity.class);
                                intent.putExtra(TeamResultsScoreOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            } else if (types == 2) {
                                //??????
                                Intent intent = new Intent(GameDetailActivity.this, SinglesDetailScoreOfficialActivity.class);
                                intent.putExtra(SinglesDetailScoreOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            } else {
                                /*??????*/
                                //                                DoubleDetailScoreOfficialActivity
                                Intent intent = new Intent(GameDetailActivity.this, DoubleDetailScoreOfficialActivity.class);
                                intent.putExtra(DoubleDetailScoreOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            }

                            break;
                        case 9:
                            Map<String, Object> game9 = new HashMap<String, Object>();
                            game9.put("ciculate_gameDetail_score", "?????????");//?????????????????????????????????????????????
                            MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game9);
//                            TCAgent.onEvent(GameDetailActivity.this, " ciculate_gameDetail_score", "?????????");

                            if (types == 1) {
                                //1 ??????
                                Intent intent = new Intent(GameDetailActivity.this, TeamResultsScoreNewOfficialActivity.class);
                                intent.putExtra(TeamResultsScoreNewOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            } else if (types == 2) {
                                //??????
                                Intent intent = new Intent(GameDetailActivity.this, SinglesDetailScoreOfficialActivity.class);
                                intent.putExtra(SinglesDetailScoreOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            } else {
                                /*??????*/
                                //                                DoubleDetailScoreOfficialActivity
                                Intent intent = new Intent(GameDetailActivity.this, DoubleDetailScoreOfficialActivity.class);
                                intent.putExtra(DoubleDetailScoreOfficialActivity.IDS, "" + id);
                                startActivity(intent);
                            }


                            break;
                    }
                }
            });

            //
            ViewGroup.LayoutParams layoutParams = rvGroupLeftList.getLayoutParams();
            layoutParams.height = 150 * leftTitles.size();
            rvGroupLeftList.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams1 = mRecyclerViewGroup.getLayoutParams();
            layoutParams1.height = 150 * leftTitles.size();
            mRecyclerViewGroup.setLayoutParams(layoutParams1);
            mBinding.llTheGroupList.setVisibility(View.VISIBLE);
            mBinding.llFillFormList.setVisibility(View.VISIBLE);

        } else {
            mBinding.llTheGroupList.setVisibility(View.GONE);
            mBinding.llFillFormList.setVisibility(View.GONE);

        }
        //
    }


    /*????????????*/
    private void initChuZu(int types, MatchArrangeGroupListBean data) {
        int joinCount = data.getJoinCount();
        monsterGroupList.clear();
        TextView tvNum = findViewById(R.id.tv_chuzu_num);
        tvNum.setText("????????????");
        if (data.getGroupDto() != null && data.getGroupDto().size() > 0) {

            if (topGroupListTitles != null) {
                topGroupListTitles.clear();
            }
            if (leftGroupListTitles != null) {
                leftGroupListTitles.clear();
            }

            for (int m = 0; m < joinCount; m++) {
                TitleBean titleBean1 = new TitleBean();
                String round1 = CommonUtilis.numberToChinese(m + 1);
                titleBean1.setTopTitle("???" + round1);
                topGroupListTitles.add(titleBean1);
            }
            List<MatchArrangeGroupListBean.GroupDtoBean> groupDto = data.getGroupDto();
            try {
                for (int i = 0; i < groupDto.size(); i++) {
                    //                types  1 ?????? 2 ?????? 3 ?????? 4??????
                    TitleBean titleBean = new TitleBean();
                    String round = CommonUtilis.numberToChinese(i + 1);
                    titleBean.setLeftTilte("" + groupDto.get(i).getGroupNumName());
                    titleBean.setTopTitle("???" + round);
                    leftGroupListTitles.add(titleBean);
                    tvNum.setVisibility(View.VISIBLE);


                    MatchArrangeGroupListBean.GroupDtoBean resultListDataBean = groupDto.get(i);
                    List<MatchArrangeGroupListBean.GroupDtoBean.GroupDataBean> arrangeList1 = resultListDataBean.getGroupData();
                    if (arrangeList1 != null && arrangeList1.size() > 0) {
                        for (int j = 0; j < arrangeList1.size(); j++) {
                            MatchArrangeGroupListBean.GroupDtoBean.GroupDataBean groupDataBean = arrangeList1.get(j);
                            Monster monster = new Monster();
                            monster.setId("" + groupDataBean.getId());
                            monster.setClubId("" + groupDataBean.getClubId());
                            monster.setMatchId("" + groupDataBean.getMatchId());
                            monster.setType(types);
                            if (types == 1) {
                                monster.setShowPlayerName("" + groupDataBean.getClubName());
                            } else if (types == 2) {
                                monster.setShowPlayerName("" + groupDataBean.getPlayer1Name());
                                monster.setClubName("" + groupDataBean.getClubName());
                            } else {
                                monster.setShowPlayerName("" + groupDataBean.getPlayer1Name() + "/" + groupDataBean.getPlayer2Name());
                                monster.setClubName("" + groupDataBean.getClubName());
                            }
                            monster.setId("" + groupDataBean.getId());
                            monsterGroupList.add(monster);
                        }
                        Log.e(TAG, "initChuZu: ===" + monsterGroupList.size());
                    } else {
                        mBinding.llEliminationDetail.setVisibility(View.GONE);
                        mBinding.llFirstStage.setVisibility(View.GONE);
                        mBinding.llTheGroupList.setVisibility(View.GONE);
                        mBinding.llFillFormList.setVisibility(View.GONE);

                        mBinding.llReportCard.setVisibility(View.GONE);
                        mBinding.llTaotaiRanking.setVisibility(View.GONE);
                        toast("????????????");
                    }
                }
                mBinding.llFirstStage.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                mBinding.llFirstStage.setVisibility(View.GONE);
            }


        } else {
            mBinding.llFirstStage.setVisibility(View.GONE);

        }
        GameDetailLeftGroupListAdapter leftGroupListAdapter = new GameDetailLeftGroupListAdapter(leftGroupListTitles);
        GameDetailTopGroupTopAdapter topAdapter = new GameDetailTopGroupTopAdapter(topGroupListTitles);
        MonsterOfficialHGroupListAdapter monsterOfficialHGroupListAdapter = new MonsterOfficialHGroupListAdapter(GameDetailActivity.this,
                monsterGroupList, leftGroupListTitles.size(), topGroupListTitles.size());
        rvLeftTitle.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        ////1 ?????? 2 ?????? 3 ?????? 4??????
//        if(joinCount)
        if (joinCount < 3) {
            tvNum.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "initChuZu: view????????? " + "getWidth=====" + tvNum.getWidth()
                            + "allWidth===" + width);
                    int divider = DisplayUtils.dip2px(GameDetailActivity.this, 10);

                    int itemWidth = width - tvNum.getWidth() - divider;
                    rvTopTitle.setLayoutManager(new GridLayoutManager(GameDetailActivity.this, joinCount));
                    topAdapter.setitemwitdh(itemWidth);
                    monsterOfficialHGroupListAdapter.setItemWidth(itemWidth);
                    GridLayoutManager manager = new GridLayoutManager(GameDetailActivity.this, joinCount);
                    manager.setOrientation(RecyclerView.VERTICAL);
                    mRecyclerView.setLayoutManager(manager);
                }
            });
        } else {
            rvTopTitle.setLayoutManager(new LinearLayoutManager(GameDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
            MyFormLayoutManager layoutManager = new MyFormLayoutManager(joinCount, mRecyclerView);
            layoutManager.setColumnCount(joinCount);
            mRecyclerView.setLayoutManager(layoutManager);
        }


        rvLeftTitle.setAdapter(leftGroupListAdapter);
        rvTopTitle.setAdapter(topAdapter);


        mRecyclerView.setAdapter(monsterOfficialHGroupListAdapter);


        ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
        layoutParams.height = 180 * leftGroupListTitles.size();
        rvLeftTitle.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
        layoutParams1.height = 180 * leftGroupListTitles.size();
        if (joinCount < 3) {
            layoutParams1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mRecyclerView.setLayoutParams(layoutParams1);


        monsterOfficialHGroupListAdapter.setListener(new MonsterOfficialHGroupListAdapter.onListener() {
            @Override
            public void OnListener(String id, String matchId, String clubId, String name) {
                if (types == 2)//??????
                {
                    if (!TextUtils.isEmpty(name)) {
                        MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                "" + id, 1);
                    }
                } else {
                    if (!TextUtils.isEmpty(name)) {
                        Intent intent = new Intent();
                        intent.setClass(GameDetailActivity.this, TeamNumbersActivity.class);
                        intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                        intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + matchId);
                        intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + clubId);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    EleminateMatchAdapter eleminateMatchAdapter;

    //????????????   ?????? ????????????  ?????????????????? ???????????? ?????????????????????
    /*?????????????????????recyclerview   ????????????*/
    private void initEliminationMatches(int types, MatchArrangeKnockOutMatchBean data) {
        eleminateMatchAdapter.setTypes(types);
        //        tv_elimination_detail_title
        mBinding.tvEliminationDetailTitle.setText("" + data.getFormTitle());
        mBinding.rvEleminateMatch.setAdapter(eleminateMatchAdapter);
        Log.e(TAG, "initEliminationMatches: groupName====" + groupName);
        Log.e(TAG, "initEliminationMatches: groupNumId===" + groupNumId);
        Log.e(TAG, "initEliminationMatches: matchScreenFormatBean===" + new Gson().toJson(matchScreenFormatBean));
        try {
            if (matchScreenFormatBean != null) {
                String matchRule = matchScreenFormatBean.getMatchRule();
                if (matchRule.equals("2") || matchRule.equals("3")) {
                    for (int m = 0; m < matchScreenFormatBean.getMatchType().size(); m++) {
                        if (matchScreenFormatBean.getMatchType().get(m).getTitle().equals("?????????") ||
                                matchScreenFormatBean.getMatchType().get(m).getTitle().equals("????????????")) {
                            List<MatchScreenFormatBean.MatchTypeBean.InfoBean> info = matchScreenFormatBean.getMatchType().get(m).getInfo();
                            boolean isHaveQianghao = false;
                            int clickPosition = 0;
                            Log.e(TAG, "initEliminationMatches: groupNumId====" + groupNumId);
                            for (int i = 0; i < info.size(); i++) {
                                if (info.get(i).getTitleName().equals("??????")) {
                                    isHaveQianghao = true;
                                }
                                Log.e(TAG, "initEliminationMatches: i===" + (info.get(i).getId()));
                                if (groupNumId.equals(String.valueOf(info.get(i).getId()))) {
                                    clickPosition = i;
                                }
                            }
                            if (matchScreenFormatBean.getMatchType().get(m).getTitle().equals("??????")) {
                                eleminateMatchAdapter.setTyClickTypeItem("??????");
                            } else if (matchScreenFormatBean.getMatchType().get(m).getTitle().equals("?????????")) {
                                eleminateMatchAdapter.setQuNum(winCount);
                                eleminateMatchAdapter.setTyClickTypeItem("?????????");
                            } else if (matchScreenFormatBean.getMatchType().get(m).getTitle().equals("??????")) {
                                eleminateMatchAdapter.setQuNum(winCount);
                                eleminateMatchAdapter.setTyClickTypeItem("??????");
                            } else {
                                if (matchScreenFormatBean.getMatchType().get(m).getTitle().equals("????????????")) {
                                    for (int mm = 0; mm < gameGroupLittleAdapter.getData().size(); mm++) {
                                        MatchScreenFormatBean.MatchTypeBean.InfoBean infoBean = gameGroupLittleAdapter.getData().get(mm);
                                        if (infoBean.isSelect()) {
                                            if (infoBean.getTitleName().equals("??????")) {
                                                eleminateMatchAdapter.setTyClickTypeItem("??????");
                                            } else if (infoBean.getTitleName().equals("?????????")) {
                                                eleminateMatchAdapter.setQuNum(winCount);
                                                eleminateMatchAdapter.setTyClickTypeItem("?????????");
                                            } else if (infoBean.getTitleName().equals("??????")) {
                                                eleminateMatchAdapter.setQuNum(winCount);
                                                eleminateMatchAdapter.setTyClickTypeItem("??????");
                                            } else {
                                                eleminateMatchAdapter.setQuNum(winCount);
                                                eleminateMatchAdapter.setTyClickTypeItem("??????");
                                            }
                                        }

                                    }
                                } else {
                                    eleminateMatchAdapter.setQuNum(winCount);
                                    eleminateMatchAdapter.setTyClickTypeItem("??????");
                                }

                            }
                            Log.e(TAG, "initEliminationMatches: clickPosition===" + clickPosition);
                            eleminateMatchAdapter.setclickPosition(clickPosition);
                            eleminateMatchAdapter.setQianghao(isHaveQianghao);
                        }
                    }

                }
            }
        } catch (Exception e) {

        }

        mBinding.rvEleminateMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if (data != null && data.getOutList().size() > 0) {
            //            ll_elimination_detail
            mBinding.llEliminationDetail.setVisibility(View.VISIBLE);
            Log.e(TAG, "initEliminationMatches: " + data.getFormTitle());
            Log.e(TAG, "initEliminationMatches:  itemTiles=== " + itemTiles);
            if (!TextUtils.isEmpty(itemTiles)) {
                if (itemTiles.equals("??????")) {
                    eleminateMatchAdapter.setTyClickTypeItem("??????");
                } else if (itemTiles.equals("?????????")) {
                    eleminateMatchAdapter.setQuNum(winCount);
                    eleminateMatchAdapter.setTyClickTypeItem("?????????");
                } else if (itemTiles.equals("??????")) {
                    eleminateMatchAdapter.setQuNum(winCount);
                    eleminateMatchAdapter.setTyClickTypeItem("??????");
                } else {
                    eleminateMatchAdapter.setQuNum(winCount);
                    eleminateMatchAdapter.setTyClickTypeItem("??????");
                }
            }
            List<MatchArrangeKnockOutMatchBean.OutListBean> outList = data.getOutList();
            eleminateMatchAdapter.setNewData(outList);
        } else {
            mBinding.llEliminationDetail.setVisibility(View.GONE);

            eleminateMatchAdapter.setNewData(new ArrayList<>());
        }
        eleminateMatchAdapter.setMyListener(new EleminateMatchAdapter.onOwnMyListener() {
            @Override
            public void OnMyListener(int clickType, int itemType, MatchArrangeKnockOutMatchBean.OutListBean item
                    , int ownerOrderType) {
                //1 ??????   2 ??????   3 ??????/?????????  4 ??????(?????? ????????????)  clickType  itemType  MatchArrangeKnockOutMatchBean.OutListBean item
                if (clickType == 1) {
                    /*?????? //1 ?????? 2 ?????? 3 ?????? 4??????*/
                    long id = item.getId();
                    Map<String, Object> game = new HashMap<String, Object>();
                    game.put("eliminate_gameDetail_score", "?????????");//?????????????????????????????????????????????
                    MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                    TCAgent.onEvent(GameDetailActivity.this, "eliminate_gameDetail_score", "?????????");
                    if (itemType == 2) {
                        /*??????*/
                        Intent intent = new Intent(GameDetailActivity.this, SinglesDetailScoreOfficialActivity.class);
                        intent.putExtra(SinglesDetailScoreOfficialActivity.IDS, "" + id);
                        startActivity(intent);
                    } else if (itemType == 1) {
                        /*??????*/
                        Intent intent = new Intent(GameDetailActivity.this, TeamResultsScoreOfficialActivity.class);
                        intent.putExtra(TeamResultsScoreOfficialActivity.IDS, "" + id);
                        startActivity(intent);
                    } else {

                        /*??????*/
                        Intent intent = new Intent(GameDetailActivity.this, DoubleDetailScoreOfficialActivity.class);
                        intent.putExtra(DoubleDetailScoreOfficialActivity.IDS, "" + id);
                        startActivity(intent);
                    }

                } else if (clickType == 2) {
                    /*??????*/
                    Map<String, Object> game = new HashMap<String, Object>();
                    game.put("eliminate_gameDetail_tableNum", "?????????");//?????????????????????????????????????????????
                    MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);
//                    TCAgent.onEvent(GameDetailActivity.this, "eliminate_gameDetail_tableNum", "?????????");

                    /*????????? ????????????*/
                    long id = item.getId();
                    String matchId = item.getMatchId();
                    String tableNum = item.getTableNum();
                    if (!TextUtils.isEmpty(tableNum)) {
                        ReferenceVisitorMatchActivity.goMatchActivity(GameDetailActivity.this,
                                "" + tableNum, "" + matchId, "" + id, "1");
                    }

                } else if (clickType == 3) {
                    Map<String, Object> game = new HashMap<String, Object>();
                    game.put("eliminate_gameDetail_userInfo", "?????????");//?????????????????????????????????????????????
                    MobclickAgent.onEventObject(GameDetailActivity.this, "gameDetail", game);

//                    TCAgent.onEvent(GameDetailActivity.this, "eliminate_gameDetail_userInfo", "?????????");

                    long idLeft = item.getIdLeft();
                    long idRight = item.getIdRight();
                    long winId = item.getWinId();
                    /*??????????????? ???????????????????????????*/
                    if (itemType == 2) {
                        /*??????*/
                        if (ownerOrderType == 1) {
                            MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                    "" + idLeft, 1);
                        } else if (ownerOrderType == 2) {
                            MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                    "" + idRight, 1);
                        } else if (ownerOrderType == 3) {
                            MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                    "" + winId, 1);
                        }
                    } else {
                        /*??????  ?????????????????????  */
                        Intent intent = new Intent();
                        intent.setClass(GameDetailActivity.this, TeamNumbersActivity.class);
                        if (ownerOrderType == 1) {
                            /*??????*/
                            String id = "" + item.getIdLeft();
                            String club1Id1 = item.getClub1Id();
                            intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                            intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + item.getMatchId());
                            intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + club1Id1);
                            startActivity(intent);
                        } else if (ownerOrderType == 2) {
                            /*??????*/
                            String id = "" + item.getIdRight();
                            String club1Id2 = item.getClub2Id();
                            intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                            intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + item.getMatchId());
                            intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + club1Id2);
                            startActivity(intent);
                        } else {
                            long winId1 = item.getWinId();
                            String winClubId = item.getWinClubId();
                            /*????????????*/
                            intent.putExtra(TeamNumbersActivity.IDS, "" + winId1);
                            intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + item.getMatchId());
                            intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + winClubId);
                            startActivity(intent);
                        }


                    }

                }

            }
        });


    }


    /*?????????????????????   ????????????---?????????  ???????????????????????? ??????*/
    private void initMatchScore(int types, MatchArrangeMatchScoreBean data) {
        //        tv_report_card_title
        mBinding.tvReportCardTitle.setText("" + data.getFormTitle() + "?????????");
        List<MatchArrangeMatchScoreBean.ResultListBean> resultList = data.getResultList();
        MatchScoreAllAdapter matchScoreAllAdapter = new MatchScoreAllAdapter(GameDetailActivity.this, types);
        mBinding.rvMatchScore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvMatchScore.setAdapter(matchScoreAllAdapter);
        if (resultList == null || resultList.size() == 0) {
            mBinding.llReportCard.setVisibility(View.GONE);
        } else {
            if (resultList.size() == 1) {
                List<MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean> resultInfo = resultList.get(0).getResultInfo();
                if (resultInfo == null || resultInfo.size() == 0) {
                    mBinding.llReportCard.setVisibility(View.GONE);
                } else {
                    mBinding.llReportCard.setVisibility(View.VISIBLE);
                    matchScoreAllAdapter.setNewData(resultList);
                }
            } else {
                mBinding.llReportCard.setVisibility(View.VISIBLE);
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getResultInfo() == null || resultList.get(i).getResultInfo().size() == 0) {
                        MatchArrangeMatchScoreBean.ResultListBean resultListBean = resultList.get(i);
                        List<MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean> resultInfoBeans = new ArrayList<>();
                        resultInfoBeans.add(new MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean());
                        resultListBean.setResultInfo(resultInfoBeans);
                        resultList.set(i, resultListBean);
                    }
                }
                //??????
                //                resultList.addAll(resultListBeanList);
                //                Log.e(TAG, "initMatchScore: ??????"+resultList.size() );
                //??????
                //                resultList.retainAll(resultListBeanList);
                //                Log.e(TAG, "initMatchScore: ??????"+resultList.size() );
                //??????
                //                list1.removeAll(list2);
                //                resultList.removeAll(resultListBeanList);
                //                Log.e(TAG, "initMatchScore: ??????"+resultList.size() );
                matchScoreAllAdapter.setNewData(resultList);
            }
        }
        //        types 1 ?????? 2?????? 3??????  4??????
        matchScoreAllAdapter.setMyListener(new MatchScoreAllAdapter.onOwnMyListener() {
            @Override
            public void OnMyListener(MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean titleBean) {
                String clubId = titleBean.getClubId();
                long id = titleBean.getId();
                String matchId = titleBean.getMatchId();
                String player1Name = titleBean.getPlayer1Name();
                if (types == 2) {
                    /*??????*/
                    if (!TextUtils.isEmpty(player1Name)) {
                        MemberShipDetailsActivity.goActivity(GameDetailActivity.this,
                                "" + id, 1);
                    }
                } else {
                    if (id == 0 || TextUtils.isEmpty(matchId)) {

                    } else {
                        Intent intent = new Intent();
                        intent.setClass(GameDetailActivity.this, TeamNumbersActivity.class);
                        intent.putExtra(TeamNumbersActivity.IDS, "" + id);
                        intent.putExtra(TeamNumbersActivity.MATCH_ID, "" + matchId);
                        intent.putExtra(TeamNumbersActivity.CLUB_ID, "" + clubId);
                        startActivity(intent);
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

            //            case R.id.tv_right:
            //
            //                mViewModel.queryComputeInfo("" + strMatchId, "" + projectItemId, "");
            //
            //                break;
            case R.id.rl_select_contation://????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (isShowScreenCondition) {
                    isShowScreenCondition = false;
                    mBinding.llSelectContation.setVisibility(View.GONE);
                } else {
                    isShowScreenCondition = true;
                    mBinding.llSelectContation.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_orition:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (showType == 1) {
                    showType = 2;
                    mBinding.tvCelOrition.setText("????????????");
                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_up);

                } else {
                    showType = 1;
                    mBinding.tvCelOrition.setText("????????????");
                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_down);


                }

                inputType = 2;
                showLoading();
                mViewModel.getMatchArrangeData("" + strMatchId,
                        "" + projectItemId, "" + groupNumId,
                        "" + matchFormatId, "" + matchFormatName, "" + groupName);
                break;
        }
    }

    RecyclerView topOfficialIntergal;
    RecyclerView leftOfficialIntergal;
    RecyclerView detailOfficialIntergal;
    List<String> titleLeftOfficial = new ArrayList<>();
    List<String> topListOfficial = new ArrayList<>();
    List<Monster> listOfficial = new ArrayList<>();

    /*??????????????????????????? ?????????*/
    private void showPopIntegralWindow(QueryComputeInfoBean data) {
        List<QueryComputeInfoBean.FirstBean> first = data.getFirst();
        List<QueryComputeInfoBean.SecondBean> second = data.getSecond();

        String itemType = data.getItemType();
        CustomDialog customDialogConfirm = new CustomDialog(GameDetailActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_intergal_calculation_process);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        RecyclerView rvPeople = customView.findViewById(R.id.rv_people);
        rvPeople.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        IntergalPeopleDetailOfficialAdapter intergalPeopleDetailAdapter = new IntergalPeopleDetailOfficialAdapter(itemType);
        rvPeople.setAdapter(intergalPeopleDetailAdapter);
        if (first != null && first.size() > 0) {
            for (int i = 0; i < first.size(); i++) {
                if (i == 0) {
                    first.get(i).setSelect(true);
                } else {
                    first.get(i).setSelect(false);
                }
            }
            intergalPeopleDetailAdapter.setNewData(first);
        }
        intergalPeopleDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    QueryComputeInfoBean.FirstBean firstBean = intergalPeopleDetailAdapter.getData().get(position);
                    long userId = firstBean.getJoinId();
                    long projectItemId = firstBean.getItemId();
                    String groupNum = firstBean.getGroupNum();

                    List<QueryComputeInfoBean.FirstBean> data1 = intergalPeopleDetailAdapter.getData();
                    for (int j = 0; j < data1.size(); j++) {
                        data1.get(j).setSelect(false);
                    }
                    data1.get(position).setSelect(true);
                    intergalPeopleDetailAdapter.setNewData(data1);

                    mViewModel.queryPointsDetails("" + userId, "" + projectItemId, "" + groupNum
                            , "" + data.getIntegral());
                }

            }
        });
        List<String> dataList = new ArrayList<>();
        if (first != null && first.size() > 0) {
            dataList.add("");
        }
        if (second != null && second.size() > 0) {
            dataList.add("");
        }
        RecyclerView rvIntegralCalculate = customView.findViewById(R.id.rv_integral_calculate);
        rvIntegralCalculate.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        IntegralCalculateOfficialAdapter integralCalculateAdapter = new IntegralCalculateOfficialAdapter(GameDetailActivity.this, data.getItemType());
        rvIntegralCalculate.setAdapter(integralCalculateAdapter);

        integralCalculateAdapter.setNewData(dataList);
        integralCalculateAdapter.setDatas(first, second);

        ImageView ivclose = customView.findViewById(R.id.iv_close);

        topOfficialIntergal = customView.findViewById(R.id.rv_top_intergal_detail);
        leftOfficialIntergal = customView.findViewById(R.id.rv_left_intergal_detail);
        detailOfficialIntergal = customView.findViewById(R.id.rv_intergal_detail);

        FormScrollHelper formScrollHelpernorMal = new FormScrollHelper();
        formScrollHelpernorMal.connectRecyclerView(detailOfficialIntergal);
        formScrollHelpernorMal.connectRecyclerView(topOfficialIntergal);
        formScrollHelpernorMal.connectRecyclerView(leftOfficialIntergal);
        if (first != null && first.size() > 0) {
            long userId = first.get(0).getJoinId();
            long projectItemId = first.get(0).getItemId();
            String groupNum = first.get(0).getGroupNum();
            mViewModel.queryPointsDetails("" + userId, "" + projectItemId, "" + groupNum
                    , "" + data.getIntegral());
        }
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogConfirm.dismiss();
            }
        });
    }


    /*?????????????????????*/
    public void showSurprise(String token) {
        CustomDialog customDialogConfirm = new CustomDialog(GameDetailActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_surprise);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();

        ImageView ivClose = customView.findViewById(R.id.iv_close);
        ImageView ivAdvertise = customView.findViewById(R.id.iv_advertise);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });
        ivAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ShopMallKotlinActivity.Companion.start(GameDetailActivity.this,"http://www.xlttsports.com/cwap/cwap_product_detail.html?gid=188" + token);
                Log.e(TAG, "onClick: " + "http://www.xlttsports.com/cwap/cwap_product_detail.html?gid=188" + token);
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }
            }
        });
    }
//tvTest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //?????????
//tvTest.getPaint().setAntiAlias(true);//?????????

}