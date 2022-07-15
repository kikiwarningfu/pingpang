package heyong.intellectPinPang.ui.club.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dnwalter.formlayoutmanager.entity.Monster;
import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CyclicGroupAdapter;
import heyong.intellectPinPang.adapter.game.GameUnderWayListAdapter;
import heyong.intellectPinPang.adapter.game.MonsterHAdapter;
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayAdapter;
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayTopAdapter;
import heyong.intellectPinPang.bean.competition.ClubContestStatisticsBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.bean.competition.TitleBean;
import heyong.intellectPinPang.databinding.ActivityGameUnderWayBinding;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.Utils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.magicindicator.MagicIndicator;
import heyong.intellectPinPang.widget.magicindicator.ViewPagerHelper;
import heyong.intellectPinPang.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import heyong.intellectPinPang.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import heyong.intellectPinPang.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import heyong.intellectPinPang.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import heyong.intellectPinPang.widget.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * 比赛进行中   单打 对内比赛
 */
public class GameUnderWayActivity extends BaseActivity<ActivityGameUnderWayBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTEST_ID = "contest_id";
    public static final String CLUB_ID = "club_id";
    public static final String TAG = GameUnderWayActivity.class.getSimpleName();
    private String strContestId = "";
    private RecyclerView rvTopTitle;
    private RecyclerView rvLeftTitle;
    private RecyclerView mRecyclerView;
    TextView tvNum;
    private List<TitleBean> toptitle = new ArrayList<>();
    private List<TitleBean> leftTitles = new ArrayList<>();
    private int type = -1;//type为1 是横向 type为2 的是竖向
    List<Monster> monsterList = new ArrayList<>();
    MyDividerItemDecoration mSixDiD;
    private String strTeamLeftId = "";

    private String GroupTitle = "";
    TitleGameUnderWayAdapter leftAdapter;
    TitleGameUnderWayTopAdapter topAdapter;
    MonsterHAdapter adapter;
    FormScrollHelper formScrollHelper;
    private int circlePosition = 0;
    public static final String MATCH_TYITLE = "match_title";
    private String strMatchTitle = "";
    private String clubId;
    CyclicGroupAdapter cyclicGroupAdapter;
    ClubContestStatisticsBean contestStatBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_under_way;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        type = 1;
        strContestId = getIntent().getStringExtra(CONTEST_ID);
        strMatchTitle = getIntent().getStringExtra(MATCH_TYITLE);
        clubId = getIntent().getStringExtra(CLUB_ID);
        mBinding.tvCenter.setText("" + strMatchTitle);
        if (monsterList != null && monsterList.size() > 0) {
            monsterList.clear();
        }
        mViewModel.getClubContestTeam(strContestId);
        mViewModel.getClubContestStatistics(strContestId, clubId);

        //mBinding.tvRight.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        /*mBinding.rvCycliGroup.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(GameUnderWayActivity.this, 5,
                getResources().getColor(R.color.white));
        mBinding.rvCycliGroup.addItemDecoration(mSixDiD);*/
        mBinding.rvCycliGroup.setLayoutManager(layoutManager);
        cyclicGroupAdapter = new CyclicGroupAdapter();
        mBinding.rvCycliGroup.setAdapter(cyclicGroupAdapter);

        mRecyclerView = findViewById(R.id.rv_form_list);
        rvTopTitle = findViewById(R.id.rv_top_list);
        rvLeftTitle = findViewById(R.id.rv_left_list);
        tvNum = findViewById(R.id.tv_num);
        leftAdapter = new TitleGameUnderWayAdapter(leftTitles);
        topAdapter = new TitleGameUnderWayTopAdapter(toptitle);
        topAdapter.setType(1);
        adapter = new MonsterHAdapter(GameUnderWayActivity.this, monsterList);
        adapter.setType(1);
        mBinding.tvCelOrition.setText("竖向表格");


        formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTopTitle);
        formScrollHelper.connectRecyclerView(rvLeftTitle);
        loadLiveDataObservers();
        cyclicGroupAdapter.setOnItemClickListener((adapter, view, position) -> {
            boolean fastClick = isFastClick();
            if (!fastClick) {
                List<ClubContestTeamBean> data = cyclicGroupAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelect(false);
                }
                circlePosition = position;
                data.get(position).setSelect(true);
                cyclicGroupAdapter.setNewData(data);
                long id = cyclicGroupAdapter.getData().get(position).getId();
                strTeamLeftId = "" + id;
                GroupTitle = cyclicGroupAdapter.getData().get(position).getTeamNum();
                monsterList.clear();
                mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);
            }
        });


    }

    private void loadLiveDataObservers() {
        mViewModel.ClubContestStatisticsLiveData.observe(this, responseBean -> {
            if (responseBean.getErrorCode().equals("200")) {
                contestStatBean = responseBean.getData();
                mBinding.tvEndOfGame.setVisibility(contestStatBean.isEndButton() ? View.VISIBLE : View.GONE);//结束比赛按钮的显示逻辑
                mBinding.tvAllCount.setText(getString(R.string.contest_all_count, contestStatBean.getAllCount()));
                mBinding.tvNotStartedCount.setText(getString(R.string.contest_unstart_count, contestStatBean.getNotStartedCount()));
                mBinding.tvEndCount.setText(getString(R.string.contest_end_count, contestStatBean.getEndCount()));
            } else {
                toast("ClubContestStatisticsLiveData: Respone Message = " + responseBean.getMessage());
            }
        });
        /*总的结束比赛*/
        mViewModel.updateXlClubContestInfoLiveData.observe(this, responseBean -> {
            /*结束比赛*/
            if (responseBean.getErrorCode().equals("200")) {
                toast("提交成功");
                finish();
            } else {
                new MessageDialogBuilder(GameUnderWayActivity.this)
                        .setMessage("")
                        .setTvTitle("比赛还有局未完成，是否要强制结束比赛？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId, true)
                        )
                        .show();
                //toast("" + responseBean.getMessage());
            }
        });
        mViewModel.getClubContestTeamLiveData.observe(this, listResponseBean -> {
            if (listResponseBean.getErrorCode().equals("200")) {

                List<ClubContestTeamBean> data = listResponseBean.getData();
                if (data != null) {
                    try {
                        for (int i = 0; i < data.size(); i++) {
                            if (i == 0) {
                                data.get(i).setSelect(true);
                            } else {
                                data.get(i).setSelect(false);
                            }
                        }
                        cyclicGroupAdapter.setNewData(data);
                        circlePosition = 0;
                        GroupTitle = data.get(0).getTeamNum();
                        strTeamLeftId = "" + data.get(0).getId();
                        mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);
                    } catch (Exception e) {

                    }

                } else {

                }

            } else {
                toast("" + listResponseBean.getMessage());
            }

        });

        mViewModel.getClubContestTeamDataLiveData.observe(this, clubContestTeamDataBeanResponseBean -> {
            if (clubContestTeamDataBeanResponseBean.getErrorCode().equals("200")) {

                SingleHitDataBean data = clubContestTeamDataBeanResponseBean.getData();
                if (data != null) {
                    List<SingleHitDataBean.ContestArrangeListBeanX> contestArrangeList = data.getContestArrangeList();

                    List<SingleHitDataBean.ResultListDataBean> resultListData = data.getResultListData();
                    if (toptitle != null) {
                        toptitle.clear();
                    }
                    if (leftTitles != null) {
                        leftTitles.clear();
                    }
                    for (int i = 0; i < resultListData.size(); i++) {
                        TitleBean titleBean = new TitleBean("" + resultListData.get(i).getUserName(), 1);
                        toptitle.add(titleBean);
                        leftTitles.add(titleBean);
                        if (type == 1) {
                            if (i == resultListData.size() - 1) {
                                TitleBean titleBeanTop = new TitleBean("积分", -1);
                                toptitle.add(titleBeanTop);
                                TitleBean titleBeanTop2 = new TitleBean("计算", -2);
                                toptitle.add(titleBeanTop2);
                                TitleBean titleBeanTop3 = new TitleBean("名次", -3);
                                toptitle.add(titleBeanTop3);
                            }
                        } else if (type == 2) {
                            if (i == resultListData.size() - 1) {
                                TitleBean titleBeanLeft = new TitleBean("积分", -1);
                                leftTitles.add(titleBeanLeft);
                                TitleBean titleBeanLeft2 = new TitleBean("计算", -2);
                                leftTitles.add(titleBeanLeft2);
                                TitleBean titleBeanLeft3 = new TitleBean("名次", -3);
                                leftTitles.add(titleBeanLeft3);
                            }
                        }
                        SingleHitDataBean.ResultListDataBean resultListDataBean = resultListData.get(i);
                        List<SingleHitDataBean.ResultListDataBean.ArrangeList1Bean> arrangeList1 = resultListDataBean.getArrangeList1();
                        for (int j = 0; j < arrangeList1.size(); j++) {
                            SingleHitDataBean.ResultListDataBean.ArrangeList1Bean arrangeList1Bean = arrangeList1.get(j);
                            Monster monster = new Monster();
                            if (TextUtils.isEmpty(String.valueOf(arrangeList1Bean.getLeft1Name()))) {
                                monster.setType(0);
                            } else {
                                if (TextUtils.isEmpty(arrangeList1Bean.getStatus())) {
//                                   /*未开始*/
                                    monster.setType(1);
                                    monster.setTableNum("" + arrangeList1Bean.getTableNum());//球台数

                                } else if (arrangeList1Bean.getStatus().equals("1") || arrangeList1Bean.getStatus().equals("3")
                                        || arrangeList1Bean.getStatus().equals("4") || arrangeList1Bean.getStatus().equals("5")
                                        || arrangeList1Bean.getStatus().equals("6")||arrangeList1Bean.getStatus().equals("2")) {
                                    /*已结束的四种状态*/
                                    int leftWavier;
                                    int rightWavier;
                                    int leftWincount;
                                    int rightWinCount;
                                    if (type == 1) {
                                        leftWavier = arrangeList1Bean.getLeftWavier();
                                        rightWavier = arrangeList1Bean.getRightWavier();
                                        leftWincount = arrangeList1Bean.getLeftWinCount();
                                        rightWinCount = arrangeList1Bean.getRightWinCount();
                                    } else {

                                        leftWavier = arrangeList1Bean.getRightWavier();
                                        rightWavier = arrangeList1Bean.getLeftWavier();
                                        leftWincount = arrangeList1Bean.getRightWinCount();
                                        rightWinCount = arrangeList1Bean.getLeftWinCount();
                                    }
                                    /*两边都弃权*/
                                    if (leftWavier == 1 && rightWavier == 1) {
                                        int leftWinCount11 = arrangeList1Bean.getLeftWinCount();
                                        int rightWinCount11 = arrangeList1Bean.getRightWinCount();
                                        monster.setShowScore(0);
                                        monster.setType(6);
                                        monster.setScore("W-" + leftWinCount11 + " : " + "W-" + rightWinCount11);
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
                                            if (leftWincount == rightWinCount) {
                                                monster.setShowScore(0);
                                            } else if (leftWincount > rightWinCount) {
                                                monster.setShowScore(2);
                                            } else {
                                                monster.setShowScore(0);
                                            }
                                            monster.setType(4);
                                            monster.setScore("" + "" + leftWincount + " : W-" + rightWinCount);
                                        } else {
                                            /*type设置为7*/
                                            if (leftWincount == rightWinCount) {
                                                /*判断计算 是一个人赢 还是一个人输*/
                                            } else if (leftWincount > rightWinCount) {
                                                /*左侧比分 大于右侧比分*/
                                                monster.setType(2);//左边赢是2
                                                monster.setScore("" + leftWincount + " : " + rightWinCount);
                                                monster.setShowScore(2);
                                            } else {
                                                monster.setType(3);//右边赢是3
                                                monster.setScore("" + leftWincount + " : " + rightWinCount);
                                                monster.setShowScore(1);
                                            }
                                        }
                                    }
                                } else {
                                    monster.setType(1);
                                    monster.setTableNum("" + arrangeList1Bean.getTableNum());//球台数
                                }
                            }
                            monster.setContestId("" + arrangeList1Bean.getId());
                            monster.setItemType(""+arrangeList1Bean.getContestType());
                            monsterList.add(monster);


                            if (type == 1) {
                                if (j == arrangeList1.size() - 1) {
                                    Monster monster1 = new Monster();
                                    monster1.setType(-1);
                                    monster1.setTeamId("" + resultListDataBean.getTeamId());
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

                                    monsterList.add(monster2);
                                    Monster monster3 = new Monster();
                                    monster3.setRanking("" + resultListDataBean.getRanking());
                                    monster3.setType(-3);

                                    monsterList.add(monster3);

                                }

                            }

                        }
                    }

                    List<SingleHitDataBean.ResultListDataBean> resultListData2 = data.getResultListData();

                    if (type == 2) {
                        for (int i = 0; i < 3; i++) {
                            if (i == 0) {
                                for (int j = 0; j < resultListData2.size(); j++) {
                                    Monster monster = new Monster();
                                    monster.setType(-1);
                                    monster.setTeamId("" + resultListData2.get(j).getTeamId());
                                    monster.setIntergal("" + resultListData2.get(j).getIntegral());
                                    monsterList.add(monster);
                                }
                            } else if (i == 1) {
                                for (int j = 0; j < resultListData2.size(); j++) {
                                    Monster monster = new Monster();
                                    if (TextUtils.isEmpty(resultListData2.get(j).getResult2())) {
                                        if (TextUtils.isEmpty(resultListData2.get(j).getResult1())) {
                                            monster.setResult("");
                                        } else {
                                            monster.setResult("1-" + resultListData2.get(j).getResult1());
                                        }
                                    } else {
                                        monster.setResult("2-" + resultListData2.get(j).getResult2());
                                    }
                                    monster.setType(-2);
                                    monsterList.add(monster);
                                }

                            } else if (i == 2) {
                                for (int j = 0; j < resultListData2.size(); j++) {
                                    Monster monster = new Monster();
                                    monster.setRanking("" + resultListData2.get(j).getRanking());
                                    monster.setType(-3);
                                    monsterList.add(monster);
                                }

                            }
                        }
                    }
                    Gson gson = new Gson();
                    String s = gson.toJson(monsterList);
                    dealData(contestArrangeList);
                }


            } else {
                toast("" + clubContestTeamDataBeanResponseBean.getMessage());
            }

        });
    }
    /**
     * 对阵列表可横向滑动的title
     * @param vp
     * @param contestArrangeList
     */
    private void initMagicIndicator(ViewPager2 vp, List<SingleHitDataBean.ContestArrangeListBeanX> contestArrangeList) {

        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator1);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return contestArrangeList == null ? 0 : contestArrangeList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText("第"+contestArrangeList.get(index).getRounds()+"轮");
                clipPagerTitleView.setTextColor(Color.parseColor("#333333"));
                clipPagerTitleView.setClipColor(Color.parseColor("#4795ED"));
                clipPagerTitleView.setTextSize(Utils.dp2px(16));
                clipPagerTitleView.setHorizontalPadding((int) Utils.dp2px(15));
                clipPagerTitleView.setOnClickListener(v -> vp.setCurrentItem(index));
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vp);
    }

    private void dealData(List<SingleHitDataBean.ContestArrangeListBeanX> contestArrangeList) {

        if (!TextUtils.isEmpty(GroupTitle)) {
            tvNum.setText("" + GroupTitle);
        }

//        if (leftTitles.size() >= 10) {
//            ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
//            layoutParams.height = leftTitles.size() * 88;
//            rvLeftTitle.setLayoutParams(layoutParams);
//            ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
//            layoutParams1.height = leftTitles.size() * 88;
//            mRecyclerView.setLayoutParams(layoutParams1);
//        }
        rvLeftTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayActivity.this, LinearLayoutManager.VERTICAL, false));

        rvLeftTitle.setAdapter(leftAdapter);

        rvTopTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayActivity.this, LinearLayoutManager.HORIZONTAL, false));

        rvTopTitle.setAdapter(topAdapter);

        FormLayoutManager layoutManager = new FormLayoutManager(toptitle.size(), mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
        layoutParams.height = 150 * leftTitles.size();
        rvLeftTitle.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
        layoutParams1.height = 150 * leftTitles.size();
        mRecyclerView.setLayoutParams(layoutParams1);


//        rvLeftTitle.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                    @Override
//                    public void onGlobalLayout() {
//                        //只需要获取一次高度，获取后移除监听器
//                        rvLeftTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        //这里高度应该定义为成员变量，定义为局部为展示代码方便
//                        int height = rvLeftTitle.getHeight();
//                        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
//                        Log.e(TAG, "dealData: mRecyclerVIew height" + layoutParams1.height);
//                        layoutParams1.height = height;
//                        mRecyclerView.setLayoutParams(layoutParams1);
//                    }
//
//                });


        adapter.setListener((monster, position, type) -> {
            String teamId = monster.getTeamId();
            Log.e(TAG, "OnListener: monster==" + new Gson().toJson(monster));
            String arrangeId = monster.getContestId();
            String intergal = monster.getIntergal();
            switch (type) {
                case -2://计算
                    showLoading();
                    //                        请求接口 弹窗
                    mViewModel.calculationInfo(teamId,intergal);
                    break;
                case 1://录入
                    if (contestStatBean.isEnterButton()) {
                        Intent intent = new Intent(GameUnderWayActivity.this, SinglesDetailActivity.class);
                        intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 1);
                        startActivity(intent);
                    } else {
                        toast("您不是参赛队员，不可录入比分");
                    }

                    break;
                case 6://两边都弃权
                case 4:////右边赢  左边弃权
                case 3://右边赢
                case 5://左边赢  右边弃权  //判断这个人的id 是否是自己 然后显示黑色
                case 2://左边赢
                    //                        toast("左边赢");
                    //单打
                    //                        toast("右边弃权");
                    //                        toast("右边赢");
                    //                        toast("左边弃权");
                    //                        toast("两边都弃权");
                    Intent intent;
                    if (contestStatBean.isEnterButton()) {
                        intent = new Intent(GameUnderWayActivity.this, SinglesDetailActivity.class);
                        intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2);
                    } else {
                        intent = new Intent(GameUnderWayActivity.this, SinglesDetailScoreActivity.class);
                        intent.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                    }
                    startActivity(intent);
                    break;

            }
        });
        /*GameUnderWayListAdapter gameUnderWayListAdapter = new GameUnderWayListAdapter(GameUnderWayActivity.this);
        mBinding.rvVsList.setLayoutManager(new LinearLayoutManager(GameUnderWayActivity.this, RecyclerView.VERTICAL, false));
        mBinding.rvVsList.setAdapter(gameUnderWayListAdapter);
        gameUnderWayListAdapter.setNewData(contestArrangeList);
        gameUnderWayListAdapter.setListener((id, bean, position) -> {
            //        clickType;//按钮类型 1是开始，创建初始数据，2是修改，查询数据

            Intent intent = new Intent(GameUnderWayActivity.this, SinglesDetailActivity.class);
            intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, "" + id);
            if (TextUtils.isEmpty(bean.getStatus())) {
                intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 1);
            } else {
                intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2);
            }

            startActivityForResult(intent, 1);
        });*/
        //对阵列表数据展示
        ViewPager2 vp = findViewById(R.id.vp_vs_list);
        GameUnderWayListAdapter gameUnderWayListAdapter = new GameUnderWayListAdapter(GameUnderWayActivity.this);
        vp.setAdapter(gameUnderWayListAdapter);
//        mBinding.rvVsList.setLayoutManager(new LinearLayoutManager(GameUnderWayActivity.this, RecyclerView.VERTICAL, false));
//        mBinding.rvVsList.setAdapter(gameUnderWayListAdapter);
        gameUnderWayListAdapter.setNewData(contestArrangeList);
        gameUnderWayListAdapter.setListener((id, bean, position) -> {
            //        clickType;//按钮类型 1是开始，创建初始数据，2是修改，查询数据
            if (contestStatBean.isEnterButton()) {
                Intent intent = new Intent(GameUnderWayActivity.this, SinglesDetailActivity.class);
                intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, "" + id);
                if (TextUtils.isEmpty(bean.getStatus())) {
                    intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 1);
                } else {
                    intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2);
                }
                startActivityForResult(intent, 1);
            } else {
                toast("您不是参赛队员，不可录入比分");
            }
        });
        //ExamplePagerAdapter gameUnderWayListAdapter = new ExamplePagerAdapter(contestArrangeList);
        //vp.setAdapter(gameUnderWayListAdapter);
        initMagicIndicator(vp,contestArrangeList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                List<ClubContestTeamBean> datas = cyclicGroupAdapter.getData();
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setSelect(false);
                }
                datas.get(circlePosition).setSelect(true);
                cyclicGroupAdapter.setNewData(datas);
                long id = cyclicGroupAdapter.getData().get(circlePosition).getId();
                strTeamLeftId = "" + id;
                GroupTitle = cyclicGroupAdapter.getData().get(circlePosition).getTeamNum();
                monsterList.clear();
                mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_orition:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (type == 1) {
                    type = 2;
                    mBinding.tvCelOrition.setText("横向表格");
                    adapter.setType(2);

                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_up);
                } else {
                    type = 1;
                    mBinding.tvCelOrition.setText("竖向表格");
                    adapter.setType(1);
                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_down);

                }
                monsterList.clear();

                mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);
                break;
            case R.id.iv_finish:

                finish();
                break;
            case R.id.tv_end_of_game://结束比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(GameUnderWayActivity.this)
                        .setMessage("")
                        .setTvTitle("是否要结束比赛？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId, false)
                        )
                        .show();

                break;
        }
    }
}