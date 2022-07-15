package heyong.intellectPinPang.ui.club.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CyclicGroupAdapter;
import heyong.intellectPinPang.adapter.game.IntegralCalculateAdapter;
import heyong.intellectPinPang.adapter.game.MonsterHAdapter;
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayAdapter;
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayTopAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.IntergalPeopleDetailAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.MonsterIntergalDetailAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.TitleIntergalLeftAdapter;
import heyong.intellectPinPang.adapter.intergaldetail.TitleIntergalTopAdapter;
import heyong.intellectPinPang.bean.competition.CalculationInfoBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.ReckonInfoDataBean;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.bean.competition.TitleBean;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ActivityGameUnderWayScoreBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 比赛进行中   单打 队内比赛   SinglesDetailScoreActivity
 */
public class GameUnderWayScoreActivity extends BaseActivity<ActivityGameUnderWayScoreBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTEST_ID = "contest_id";
    public static final String TAG = GameUnderWayScoreActivity.class.getSimpleName();
    TextView tvNum;
    public static final String MATCH_TITLE = "match_title";
    private String strMatchTitle = "";
    private String strContestId = "";
    private String strTeamLeftId = "";
    private String GroupTitle = "";

    private RecyclerView rvTopTitle;
    private RecyclerView rvLeftTitle;
    private RecyclerView mRecyclerView;

    private List<TitleBean> topTitle = new ArrayList<>();
    private List<TitleBean> leftTitles = new ArrayList<>();
    private int type = -1;//type为1 是横向 type为2 的是竖向
    List<Monster> monsterList = new ArrayList<>();
    MyDividerItemDecoration mSixDiD;

    TitleGameUnderWayAdapter leftAdapter;
    TitleGameUnderWayTopAdapter topAdapter;
    MonsterHAdapter monsterHAdapter;
    FormScrollHelper formScrollHelper;
    FormLayoutManager layoutManager;

    /*SinglesDetailScoreActivity  单打 查看比赛成绩  点击跳转到单打的界面成绩*/
    /*TeamResultsScoreActivity    点击以后挑战到双打的页面成绩 只能看*/
    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_under_way_score;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        type = 1;
        strContestId = getIntent().getStringExtra(CONTEST_ID);
        strMatchTitle = getIntent().getStringExtra(MATCH_TITLE);
        mBinding.tvCenter.setText(strMatchTitle);
        mViewModel.getClubContestTeam(strContestId);
        LinearLayoutManager cyclilayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(GameUnderWayScoreActivity.this, 5);
        mBinding.rvCycliGroup.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(GameUnderWayScoreActivity.this, 5,
                getResources().getColor(R.color.white));
        mBinding.rvCycliGroup.addItemDecoration(mSixDiD);*/
        mBinding.rvCycliGroup.setLayoutManager(cyclilayoutManager);
        CyclicGroupAdapter cyclicGroupAdapter = new CyclicGroupAdapter();
        mBinding.rvCycliGroup.setAdapter(cyclicGroupAdapter);
        mBinding.tvRight.setVisibility(View.INVISIBLE);
        mRecyclerView = findViewById(R.id.rv_form_list);
        rvTopTitle = findViewById(R.id.rv_top_list);
        rvLeftTitle = findViewById(R.id.rv_left_list);
        tvNum = findViewById(R.id.tv_num);
        leftAdapter = new TitleGameUnderWayAdapter(leftTitles);
        topAdapter = new TitleGameUnderWayTopAdapter(topTitle);
        topAdapter.setType(1);
        monsterHAdapter = new MonsterHAdapter(GameUnderWayScoreActivity.this, monsterList);
        monsterHAdapter.setType(1);
        mBinding.tvCelOrition.setText("竖向表格");

        mViewModel.calculationInfoLiveData.observe(this, new Observer<ResponseBean<CalculationInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<CalculationInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (responseBean.getData() != null) {
                        if (responseBean.getData().getFirst() != null && responseBean.getData().getFirst().size() > 0) {
                            showPopIntegralWindow(responseBean.getData());
                        } else {
                            toast("计算数据有误");
                            dismissLoading();
                        }
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });
        monsterHAdapter.setListener(new MonsterHAdapter.onListener() {
            @Override
            public void OnListener(Monster monster, int position, int type) {
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
                    case 2://左边赢
//                        toast("左边赢");

                            //单打
                            Intent intent = new Intent(GameUnderWayScoreActivity.this, SinglesDetailScoreActivity.class);
                            intent.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                            intent.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                     startActivity(intent);
                        break;
                    case 5://左边赢  右边弃权  //判断这个人的id 是否是自己 然后显示黑色

//                        toast("右边弃权");
                        Intent intent1 = new Intent(GameUnderWayScoreActivity.this, SinglesDetailScoreActivity.class);
                        intent1.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent1.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                        startActivity(intent1);

                        break;
                    case 3://右边赢
//                        toast("右边赢");
                        Intent intent2 = new Intent(GameUnderWayScoreActivity.this, SinglesDetailScoreActivity.class);
                        intent2.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent2.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                        startActivity(intent2);
                        break;
                    case 4:////右边赢  左边弃权
//                        toast("左边弃权");
                        Intent intent3 = new Intent(GameUnderWayScoreActivity.this, SinglesDetailScoreActivity.class);
                        intent3.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent3.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                        startActivity(intent3);
                        break;
                    case 6://两边都弃权
//                        toast("两边都弃权");
                        Intent intent4 = new Intent(GameUnderWayScoreActivity.this, SinglesDetailScoreActivity.class);
                        intent4.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId);
                        intent4.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);
                        startActivity(intent4);
                        break;
                }
            }
        });
        formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTopTitle);
        formScrollHelper.connectRecyclerView(rvLeftTitle);


        cyclicGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    List<ClubContestTeamBean> data = cyclicGroupAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }
                    data.get(position).setSelect(true);
                    cyclicGroupAdapter.setNewData(data);
                    long id = cyclicGroupAdapter.getData().get(position).getId();
                    strTeamLeftId = "" + id;
                    GroupTitle = cyclicGroupAdapter.getData().get(position).getTeamNum();
                    monsterList.clear();
                    mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);
                    mViewModel.getXlClubContestInfo("" + strContestId, "" + strTeamLeftId);
                }
            }
        });
        mViewModel.getXlClubContestInfoLiveData.observe(this, new Observer<ResponseBean<XlClubContestInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubContestInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {

                    List<String> contestArrangeList = responseBean.getData().getContestArrangeList();
                    int size = contestArrangeList.size();
                    if (size >= 1) {
                        switch (size) {
                            case 1:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                break;
                            case 2:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));


                                break;
                            case 3:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                break;
                            case 4:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                mBinding.tvText4.setText("" + contestArrangeList.get(3));
                                break;
                            case 5:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                mBinding.tvText4.setText("" + contestArrangeList.get(3));
                                mBinding.tvText5.setText("" + contestArrangeList.get(4));
                                break;
                            case 6:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                mBinding.tvText4.setText("" + contestArrangeList.get(3));
                                mBinding.tvText5.setText("" + contestArrangeList.get(4));
                                mBinding.tvText6.setText("" + contestArrangeList.get(5));
                                break;
                            case 7:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                mBinding.tvText4.setText("" + contestArrangeList.get(3));
                                mBinding.tvText5.setText("" + contestArrangeList.get(4));
                                mBinding.tvText6.setText("" + contestArrangeList.get(5));
                                mBinding.tvText7.setText("" + contestArrangeList.get(6));
                                break;
                            case 8:
                                mBinding.tvText1.setText("" + contestArrangeList.get(0));
                                mBinding.tvText2.setText("" + contestArrangeList.get(1));
                                mBinding.tvText3.setText("" + contestArrangeList.get(2));
                                mBinding.tvText4.setText("" + contestArrangeList.get(3));
                                mBinding.tvText5.setText("" + contestArrangeList.get(4));
                                mBinding.tvText6.setText("" + contestArrangeList.get(5));
                                mBinding.tvText7.setText("" + contestArrangeList.get(6));
                                mBinding.tvText8.setText("" + contestArrangeList.get(7));
                                break;
                        }
                    }

                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });

        mViewModel.reckonInfoDataLiveData.observe(this, new Observer<ResponseBean<List<ReckonInfoDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ReckonInfoDataBean>> calculationInfoBeanResponseBean) {
                dismissLoading();
                if (calculationInfoBeanResponseBean.getData() != null && calculationInfoBeanResponseBean.getData().size() > 0) {
                    List<ReckonInfoDataBean> data = calculationInfoBeanResponseBean.getData();
                    List<String> chess = data.get(0).getChess();
                    leftPopAdapter = new TitleIntergalLeftAdapter();
                    rvIntergalLeftTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayScoreActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvIntergalLeftTitle.setAdapter(leftPopAdapter);
                    rvIntergalTopTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayScoreActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    topPopAdapter = new TitleIntergalTopAdapter();
                    rvIntergalTopTitle.setAdapter(topPopAdapter);
                    layoutManager = new FormLayoutManager(chess.size(), mIntergalDetailView);
                    mIntergalDetailView.setLayoutManager(layoutManager);
                    list.clear();
                    titleLeft.clear();
                    topList.clear();
                    for (int i = 0; i < data.size(); i++) {
                        List<String> chess1 = data.get(i).getChess();
                        String arrange = data.get(i).getArrange();
                        titleLeft.add(arrange);
                        for (int j = 0; j < chess1.size(); j++) {
                            Monster monster = new Monster();
                            monster.setIntergal(chess1.get(j));
                            list.add(monster);
                        }
                    }
                    int size = chess.size();
                    for (int j = 0; j < size; j++) {
                        if (j == size - 1) {
                            topList.add("总比分");
                        } else {
                            String round = CommonUtilis.numberToChinese(j + 1);
                            topList.add("第" + round + "局");
                        }
                    }
                    leftPopAdapter.setNewDatas(titleLeft);
                    topPopAdapter.setDataList(topList);
                    Log.e(TAG, "onChanged: =" + titleLeft.size());
                    Log.e(TAG, "onChanged: ==" + topList.size());
                    Log.e(TAG, "onChanged: ===" + list.size());
                    MonsterIntergalDetailAdapter adapter = new MonsterIntergalDetailAdapter(GameUnderWayScoreActivity.this, list);
                    mIntergalDetailView.setAdapter(adapter);
//                    ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
//                    layoutParams.height = leftTitles.size() * 88;
//                    rvLeftTitle.setLayoutParams(layoutParams);
//
//                    ViewGroup.LayoutParams layoutParams1 = rvTopTitle.getLayoutParams();
//                    layoutParams1.height = leftTitles.size() * 88;
//                    rvTopTitle.setLayoutParams(layoutParams1);
                    ViewGroup.LayoutParams layoutParams = rvIntergalTopTitle.getLayoutParams();
                    rvIntergalTopTitle.setLayoutParams(layoutParams);

                }

            }
        });
        mViewModel.getClubContestTeamLiveData.observe(this, new Observer<ResponseBean<List<ClubContestTeamBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ClubContestTeamBean>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {

                    showLoading();
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
                            GroupTitle = data.get(0).getTeamNum();
                            strTeamLeftId = "" + data.get(0).getId();
                            mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId);
                            mViewModel.getXlClubContestInfo("" + strContestId, "" + strTeamLeftId);
                        } catch (Exception e) {
                            dismissLoading();
                            toast("数据异常");
                            mBinding.llContent.setVisibility(View.INVISIBLE);
                        }


                    }

                } else {
                    toast("" + listResponseBean.getMessage());
                }

            }
        });
        mViewModel.getClubContestTeamDataLiveData.observe(this, new Observer<ResponseBean<SingleHitDataBean>>() {
            @Override
            public void onChanged(ResponseBean<SingleHitDataBean> clubContestTeamDataBeanResponseBean) {
                if (clubContestTeamDataBeanResponseBean.getErrorCode().equals("200")) {


                    SingleHitDataBean data = clubContestTeamDataBeanResponseBean.getData();
                    if (data != null) {
                        List<SingleHitDataBean.ContestArrangeListBeanX> contestArrangeList = data.getContestArrangeList();
                        List<SingleHitDataBean.ResultListDataBean> resultListData = data.getResultListData();
                        if (topTitle != null) {
                            topTitle.clear();
                        }
                        if (leftTitles != null) {
                            leftTitles.clear();
                        }
                        for (int i = 0; i < resultListData.size(); i++) {
                            TitleBean titleBean = new TitleBean("" + resultListData.get(i).getUserName(), 1);
                            topTitle.add(titleBean);
                            leftTitles.add(titleBean);
                            if (type == 1) {
                                if (i == resultListData.size() - 1) {
                                    TitleBean titleBeanTop = new TitleBean("积分", -1);
                                    topTitle.add(titleBeanTop);
                                    TitleBean titleBeanTop2 = new TitleBean("计算", -2);
                                    topTitle.add(titleBeanTop2);
                                    TitleBean titleBeanTop3 = new TitleBean("名次", -3);
                                    topTitle.add(titleBeanTop3);
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
                                Log.e(TAG, "onChanged: position===" + arrangeList1Bean.getLeft1Name() + "===" + j);
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
                                        /*两边都弃权*/
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
                                        Log.e(TAG, "onChanged: type===" + type + "leftWavier===" + leftWavier + "righwWavier===" + rightWavier
                                                + "leftWinCount===" + leftWincount + "rightWinCount===" + rightWinCount);
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
                                                monster.setScore("" + "" + leftWincount + ":W-" + rightWinCount);
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
                                        monster2.setIntergal(""+resultListDataBean.getIntegral());
                                        monster2.setTeamId("" + resultListDataBean.getTeamId());
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
                                        monster.setIntergal("" + resultListData2.get(j).getIntegral());
                                        monster.setType(-1);
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
                                        monster.setTeamId("" + resultListData2.get(j).getTeamId());
                                        monster.setType(-2);
                                        monster.setIntergal(""+resultListData2.get(j).getIntegral());
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
                        Log.e(TAG, "onChanged: size===" + monsterList.size());
                        Log.e(TAG, "onChanged: okhttp===" + s);

                        dealData(contestArrangeList);
                    }

                } else {
                    toast("" + clubContestTeamDataBeanResponseBean.getMessage());
                }

                dismissLoading();
            }
        });

    }

    /*展示积分详情的设置 和弹窗*/
    private void showPopIntegralWindow(CalculationInfoBean data) {
        CustomDialog customDialogConfirm = new CustomDialog(GameUnderWayScoreActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_intergal_calculation_process);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showPopIntegralCalculationProcess(customView, customDialogConfirm, data);
    }

    List<String> titleLeft = new ArrayList<>();
    List<String> topList = new ArrayList<>();
    List<Monster> list = new ArrayList<>();
    TitleIntergalTopAdapter topPopAdapter;
    TitleIntergalLeftAdapter leftPopAdapter;

    RecyclerView rvIntergalTopTitle;
    RecyclerView rvIntergalLeftTitle;
    RecyclerView mIntergalDetailView;
    FormScrollHelper formScrollHelpermy;

    private void showPopIntegralCalculationProcess(View customView, CustomDialog customDialogConfirm, CalculationInfoBean data) {

        List<CalculationInfoBean.FirstBean> first = data.getFirst();
        List<CalculationInfoBean.SecondBean> second = data.getSecond();
        List<String> dataList = new ArrayList<>();
        if (first != null && first.size() > 0) {
            dataList.add("");
        }
        if (second != null && second.size() > 0) {
            dataList.add("");
        }
        ImageView ivclose = customView.findViewById(R.id.iv_close);
        RecyclerView rvPeople = customView.findViewById(R.id.rv_people);
        RecyclerView rvIntegralCalculate = customView.findViewById(R.id.rv_integral_calculate);
        rvIntergalTopTitle = customView.findViewById(R.id.rv_top_intergal_detail);
        rvIntergalLeftTitle = customView.findViewById(R.id.rv_left_intergal_detail);
        mIntergalDetailView = customView.findViewById(R.id.rv_intergal_detail);


        formScrollHelpermy = new FormScrollHelper();
        formScrollHelpermy.connectRecyclerView(mIntergalDetailView);
        formScrollHelpermy.connectRecyclerView(rvIntergalTopTitle);
        formScrollHelpermy.connectRecyclerView(rvIntergalLeftTitle);


        /*设置上面的1级计算 和2级计算 */
        rvIntegralCalculate.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        IntegralCalculateAdapter integralCalculateAdapter = new IntegralCalculateAdapter(GameUnderWayScoreActivity.this);
        rvIntegralCalculate.setAdapter(integralCalculateAdapter);
        integralCalculateAdapter.setNewData(dataList);
        integralCalculateAdapter.setDatas(first, second);


        rvPeople.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        IntergalPeopleDetailAdapter intergalPeopleDetailAdapter = new IntergalPeopleDetailAdapter();
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
        /*设置人数*/
        intergalPeopleDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    /*调用接口*/
                    CalculationInfoBean.FirstBean firstBean = intergalPeopleDetailAdapter.getData().get(position);
                    long userId = firstBean.getUserId();
                    long matchId = firstBean.getMatchId();
                    List<CalculationInfoBean.FirstBean> data1 = intergalPeopleDetailAdapter.getData();
                    for (int j = 0; j < data1.size(); j++) {
                        data1.get(j).setSelect(false);
                    }
                    data1.get(position).setSelect(true);
                    intergalPeopleDetailAdapter.setNewData(data1);
                    showLoading();
                    mViewModel.reckonInfoData("" + userId, "" + matchId);
                }

            }
        });
        long userId = first.get(0).getUserId();
        long matchId = first.get(0).getMatchId();

        mViewModel.reckonInfoData("" + userId, "" + matchId);

        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogConfirm.dismiss();
            }
        });

    }


    private void dealData(List<SingleHitDataBean.ContestArrangeListBeanX> contestArrangeList) {

        if (!TextUtils.isEmpty(GroupTitle)) {
            tvNum.setText("" + GroupTitle);
        }
//        if (type == 2) {
//            ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
//            layoutParams.height = (leftTitles.size()) * 88 * 2;
//            rvLeftTitle.setLayoutParams(layoutParams);
//
//            ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
//            layoutParams1.height = (leftTitles.size()) * 88 * 2;
//            mRecyclerView.setLayoutParams(layoutParams1);
//        } else {
//            ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
//            layoutParams.height = (leftTitles.size()) * 88 * 2;
//            rvLeftTitle.setLayoutParams(layoutParams);
//
//            ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
//            layoutParams1.height = (leftTitles.size()) * 88 * 2;
//            mRecyclerView.setLayoutParams(layoutParams1);
//        }

        rvLeftTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayScoreActivity.this, LinearLayoutManager.VERTICAL, false));

        rvLeftTitle.setAdapter(leftAdapter);

        rvTopTitle.setLayoutManager(new LinearLayoutManager(GameUnderWayScoreActivity.this, LinearLayoutManager.HORIZONTAL, false));

        rvTopTitle.setAdapter(topAdapter);
//        rvLeftTitle.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener(){
//
//                    @Override
//                    public void onGlobalLayout(){
//                        //只需要获取一次高度，获取后移除监听器
//                        rvLeftTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        //这里高度应该定义为成员变量，定义为局部为展示代码方便
//                        int height = rvLeftTitle.getHeight();
//                        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
//                        Log.e(TAG, "dealData: mRecyclerVIew height"+layoutParams1.height );
//                        layoutParams1.height = height;
//                        mRecyclerView.setLayoutParams(layoutParams1);
//                    }
//
//                });
        ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
        layoutParams.height = 150 * leftTitles.size();
        rvLeftTitle.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
        layoutParams1.height = 150 * leftTitles.size();
        mRecyclerView.setLayoutParams(layoutParams1);
        FormLayoutManager layoutManager = new FormLayoutManager(topTitle.size(), mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(monsterHAdapter);


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
                    monsterHAdapter.setType(2);
                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_up);
                } else {
                    type = 1;
                    mBinding.tvCelOrition.setText("竖向表格");
                    monsterHAdapter.setType(1);
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
                new MessageDialogBuilder(GameUnderWayScoreActivity.this)
                        .setMessage("")
                        .setTvTitle("是否要结束比赛？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                finish()
                        )
                        .show();

                break;
        }
    }
}