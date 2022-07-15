package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefereeCompetitionInterfaceAdapter;
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ActivityRefereeCompetitionInterfaceBinding;
import heyong.intellectPinPang.event.SwipMatchEvent;
import heyong.intellectPinPang.event.ToTargetEvent;
import heyong.intellectPinPang.iterface.IHCallback;
import heyong.intellectPinPang.ui.club.activity.TeamResultsOfficialActivity;
import heyong.intellectPinPang.ui.coachdisplay.WavierUpActivity;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 裁判员比赛界面
 */
public class RefereeCompetitionInterfaceActivity extends
        BaseActivity<ActivityRefereeCompetitionInterfaceBinding, HomePageViewModel> implements View.OnClickListener, OnRefreshListener {
    RefereeCompetitionInterfaceAdapter refereeCompetitionInterfaceAdapter;
    ItemTouchHelper itemTouchHelper;
    public static final String TAG = RefereeCompetitionInterfaceActivity.class.getSimpleName();
    private int type = 0;
    public static final String MATCH_ID = "match_id";
    public static final String IDS = "id";
    public static final String CODE = "code";
    private String strMatchId = "";
    MyDividerItemDecoration mSixDiD;

    private String strTableNum = "";

    private String code = "";
    private String mIds = "";
    private String status = "1";
    private String times = "";
    MyRefreshAnimHeader mRefreshAnimHeader;


    Calendar beginCalendar;
    Calendar endCalendar;
    private boolean iScanTuodong = false;

    List<TableNumArrangeBean.ArrangesBean> mItems = new ArrayList<>();
    TableNumArrangeBean.ArrangesBean arrangesBean;
    String matchTitle = "";
    private int pageNum = 1;
    private int pageSize = 20;
    private boolean haveMore = true;
    TableNumArrangeBean tableNumArrangeBean;
    private int clickPosition = 0;
    RefereeDisplayAdapter refereeDisplayAdapter;
    private ItemTouchHelper.Callback ihCallback;

    private String timeCodeDisplay = "";


    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(times)) {
            haveMore = true;
            pageNum = 1;

            clickPosition = 0;
            showLoading();
            mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
                    "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_referee_competition_interface;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        String brand = android.os.Build.BRAND;
        String PRODUCT = Build.PRODUCT;
        String DEVICE = Build.DEVICE;
        String BOARD = Build.BOARD;

        Log.e(TAG, "initView: " + brand + "   " + PRODUCT + "   " + DEVICE + "   " + BOARD);
        status = "1";
        mIds = getIntent().getStringExtra(IDS);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        code = getIntent().getStringExtra(CODE);
        refereeDisplayAdapter = new RefereeDisplayAdapter();
        mBinding.rvTime.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mBinding.rvTime.setAdapter(refereeDisplayAdapter);
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
                    refereeDisplayAdapter.setNewData(data1);
                    timeCodeDisplay=data1.get(position).getTime();
                    times = data1.get(position).getUpTime();
                    haveMore = true;
                    pageNum = 1;

                    clickPosition = 0;
                    showLoading();
                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
                            "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);

                }

            }
        });
        mRefreshAnimHeader = new MyRefreshAnimHeader(RefereeCompetitionInterfaceActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                            "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });


        mViewModel.dateLimit("" + strMatchId);
        refereeCompetitionInterfaceAdapter = new RefereeCompetitionInterfaceAdapter(mItems);
        mBinding.rvRefereeAllMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvRefereeAllMatch.setAdapter(refereeCompetitionInterfaceAdapter);
        refereeCompetitionInterfaceAdapter.isShowDragable(false);
        mSixDiD = new MyDividerItemDecoration(RefereeCompetitionInterfaceActivity.this, 5,
                getResources().getColor(R.color.white));
        ihCallback = new IHCallback(refereeCompetitionInterfaceAdapter);
        itemTouchHelper = new ItemTouchHelper(ihCallback);
        mViewModel.sortTableNumArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.dateLimitLiveData.observe(this, new Observer<ResponseBean<DateLimitBean>>() {
            @Override
            public void onChanged(ResponseBean<DateLimitBean> dateLimitBeanResponseBean) {
                if (dateLimitBeanResponseBean.getErrorCode().equals("200")) {
                    DateLimitBean data = dateLimitBeanResponseBean.getData();
                    if (data != null) {
                        long beginTime = data.getBeginTime();
                        long endTime = data.getEndTime();
                        String beginStrTime = MyDateUtils.getDateToString(beginTime);
                        String endStrTime = MyDateUtils.getDateToString(endTime);
                        Log.e(TAG, "onChanged: beginTime==" + beginStrTime + "===endTime" + endStrTime);
                        beginCalendar = MyDateUtils.getCalendar(beginStrTime);
                        endCalendar = MyDateUtils.getCalendar(endStrTime);
                        List<Date> datesBetweenTwoDate;
                        if (beginCalendar.equals(endCalendar)) {
                            datesBetweenTwoDate = new ArrayList<>();
                            datesBetweenTwoDate.add(beginCalendar.getTime());
                        } else {
                            datesBetweenTwoDate = MyDateUtils.getDatesBetweenTwoDate(beginCalendar.getTime(), endCalendar.getTime());
                        }
                        times = beginStrTime;
                        List<RefereeDisplayTimeBean> refereeDisplayTimeBeanList = new ArrayList<>();
                        for (int i = 0; i < datesBetweenTwoDate.size(); i++) {
                            RefereeDisplayTimeBean refereeDisplayTimeBean = new RefereeDisplayTimeBean();
                            Date date = datesBetweenTwoDate.get(i);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String transformDate = simpleDateFormat.format(date);
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM月dd日");
                            String transformDate2 = simpleDateFormat2.format(date);
                            if (i == 0) {
                                timeCodeDisplay = transformDate2;
                                refereeDisplayTimeBean.setSelect(true);
                            } else {
                                refereeDisplayTimeBean.setSelect(false);
                            }
                            refereeDisplayTimeBean.setTime(transformDate2);
                            refereeDisplayTimeBean.setUpTime(transformDate);
                            refereeDisplayTimeBeanList.add(refereeDisplayTimeBean);
                        }
                        refereeDisplayAdapter.setNewData(refereeDisplayTimeBeanList);
                    }
                } else {
                    toast("" + dateLimitBeanResponseBean.getMessage());
                }
                mViewModel.refereeInTableNum("" + strMatchId);

            }
        });
        mViewModel.refereeInTableNumLiveData.observe(this, new Observer<ResponseBean<List<String>>>() {
            @Override
            public void onChanged(ResponseBean<List<String>> listResponseBean) {
                if (listResponseBean.getData() != null && listResponseBean.getData().size() > 0) {
                    strTableNum = listResponseBean.getData().get(0);
                    mBinding.tvTaishu.setText("" + strTableNum + " 台");
                }
                initData();
            }
        });
        mViewModel.judgeRefereeUpdateScoreLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("100000-100048")) {
                    toast("已修改过一次，无法再次修改，请联系裁判长修改");
                } else if (responseBean.getErrorCode().equals("200")) {
                    goAnotherJump(arrangesBean, itemType);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.tableNumArrangeChiefLiveData.observe(this, new Observer<ResponseBean<TableNumArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<TableNumArrangeBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    tableNumArrangeBean = responseBean.getData();
                    if (tableNumArrangeBean != null) {
                        mBinding.tvAll.setText("总场次" + "(" + tableNumArrangeBean.getTotal() + ")");
                        mBinding.tvDaisai.setText("待赛" + "(" + tableNumArrangeBean.getNotBeginCount() + ")");
                        mBinding.tvEnd.setText("已结束" + "(" + tableNumArrangeBean.getEndCount() + ")");
                        matchTitle = tableNumArrangeBean.getMatchTitle();
                        List<TableNumArrangeBean.ArrangesBean> arranges = tableNumArrangeBean.getArranges();
                        refereeCompetitionInterfaceAdapter.setmatchStatus(tableNumArrangeBean.getStatus());
                        if (arranges != null && arranges.size() > 0) {
                            if (pageNum == 1) {
                                mItems.clear();
                            }
                            for (int i = 0; i < arranges.size(); i++) {
                                TableNumArrangeBean.ArrangesBean arrangesBean = arranges.get(i);
                                int status = Integer.parseInt(arrangesBean.getStatus());
                                if (status == 10) {
                                    arrangesBean.setShowItemType(2);
                                } else {
                                    if (status == 0 && (TextUtils.isEmpty(arrangesBean.getPlayer1Name()) || TextUtils.isEmpty(arrangesBean.getPlayer3Name()))) {
                                        arrangesBean.setShowItemType(2);
                                    } else {
                                        //1 团体  2单打  3 双打  4混双
                                        int itemType = Integer.parseInt(arrangesBean.getItemType());
                                        if (itemType == 1 || itemType == 2) {
                                            arrangesBean.setShowItemType(0);
                                        } else {
                                            arrangesBean.setShowItemType(1);
                                        }
                                    }
                                }
                            }
                            if (arranges != null) {
                                mItems.addAll(arranges);
                                if (arranges != null && arranges.size() < pageSize) {
                                    haveMore = false;
                                    mBinding.swFresh.setEnableLoadmore(false);
                                } else {
                                    mBinding.swFresh.setEnableLoadmore(true);
                                }
                                refereeCompetitionInterfaceAdapter.setNewData(mItems);
                            }
                            if (mBinding.swFresh != null) {
                                mBinding.swFresh.finishRefresh();
                                mBinding.swFresh.finishLoadmore();
                            }
                        } else {
                            if (pageNum == 1) {
                                mItems.clear();
                            }
                            mBinding.swFresh.finishRefresh();
                            mBinding.swFresh.finishLoadmore();

                            refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                        }
                    } else {
                        toast("" + responseBean.getMessage());
                        if (pageNum == 1) {
                            mItems.clear();
                        }
                        refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                        mBinding.tvAll.setText("总场次" + "(" + 0 + ")");
                        mBinding.tvDaisai.setText("待赛" + "(" + 0 + ")");
                        mBinding.tvEnd.setText("已结束" + "(" + 0 + ")");
                    }
                } else {
                    toast("" + responseBean.getMessage());
                    if (pageNum == 1) {
                        mItems.clear();
                    }
                    refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                    mBinding.tvAll.setText("总场次" + "(" + 0 + ")");
                    mBinding.tvDaisai.setText("待赛" + "(" + 0 + ")");
                    mBinding.tvEnd.setText("已结束" + "(" + 0 + ")");
                }
                if (mBinding.swFresh != null) {
                    mBinding.swFresh.finishRefresh();
                }
            }
        });
        refereeCompetitionInterfaceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {

                        case R.id.tv_give_up://弃权
//                            showGiveUpDialog();
                            TableNumArrangeBean.ArrangesBean arrangesBean = refereeCompetitionInterfaceAdapter.getData().get(position);
                            Intent intentmm = new Intent(RefereeCompetitionInterfaceActivity.this, WavierUpActivity.class);
                            intentmm.putExtra("data", arrangesBean);
                            startActivity(intentmm);


                            break;
                        case R.id.tv_game_end_status:
                        case R.id.ll_score:
                            clickPosition = position;
                            RefereeCompetitionInterfaceActivity.this.arrangesBean = refereeCompetitionInterfaceAdapter.getData().get(position);
                            int status = Integer.parseInt(RefereeCompetitionInterfaceActivity.this.arrangesBean.getStatus());
                            String matchStatus = refereeCompetitionInterfaceAdapter.getMatchStatus();
                            if (status == 0) {//未开始
                                if (Integer.parseInt(matchStatus) == 5) {
                                    showSurprise(RefereeCompetitionInterfaceActivity.this.arrangesBean);
                                }
                            } else if (status == 9 || status == 1 || status == 3 || status == 4 || status == 5 || status == 6 || status == 2) {
                                int itemType = Integer.parseInt(RefereeCompetitionInterfaceActivity.this.arrangesBean.getItemType());
                                if (itemType == 1) {
                                    Intent intent = new Intent();
                                    intent.setClass(RefereeCompetitionInterfaceActivity.this, TeamResultsOfficialActivity.class);
                                    intent.putExtra("ids", "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getId());
                                    intent.putExtra("cliclkPosition", "" + clickPosition);
                                    startActivity(intent);
                                } else if (itemType == 2) {
                                    Intent intent2 = new Intent();
                                    intent2.setClass(RefereeCompetitionInterfaceActivity.this, SinglesDetailOfficialActivity.class);
                                    intent2.putExtra("ids", "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getId());
                                    intent2.putExtra("cliclkPosition", "" + clickPosition);

                                    startActivity(intent2);
                                } else {
                                    Intent intent3 = new Intent();
                                    intent3.setClass(RefereeCompetitionInterfaceActivity.this, DoubleDetailOfficialActivity.class);
                                    intent3.putExtra("ids", "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getId());
                                    intent3.putExtra("cliclkPosition", "" + clickPosition);
                                    startActivity(intent3);
                                }

                            } else {//已结束{
//                            判断 单打 双打还是团体
                                int itemType = Integer.parseInt(RefereeCompetitionInterfaceActivity.this.arrangesBean.getItemType());
                                if (itemType == 1) {
                                    Intent intent = new Intent();
                                    intent.setClass(RefereeCompetitionInterfaceActivity.this, DrawForHostAndVistors.class);
                                    ArrangeDrawDataBean arrangeDrawDataBean = new ArrangeDrawDataBean("" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getTitle(),
                                            "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getItemType(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getId(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer1Id()
                                            , "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer1Name(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getHeadImg1(),
                                            "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer2Name(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getHeadImg2(),
                                            "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer3Id(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer3Name(), "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getHeadImg3()
                                            , "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getPlayer4Name()
                                            , "" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getHeadImg4(), "" + strMatchId);
                                    intent.putExtra("data", (Serializable) arrangeDrawDataBean);
                                    intent.putExtra("cliclkPosition", "" + clickPosition);

                                    startActivity(intent);
                                } else {
                                    //详情  掉接口  判断是否还可以修改  如果可以  弹窗
                                    mViewModel.judgeRefereeUpdateScore("" + RefereeCompetitionInterfaceActivity.this.arrangesBean.getId());
                                }

                            }
                            break;
                    }
                }
            }
        });


//        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//
//            /**
//             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
//             * @param recyclerView
//             * @param viewHolder
//             * @return
//             */
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//
////                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖动支持向下和向上
////                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;//滑动支持向左和向右
//     /*   //Grid部分功能
//        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN |ItemTouchHelper.START | ItemTouchHelper.END;
//        int swipeFlag = 0;*/
////                return makeMovementFlags(dragFlag, 0);
//                //允许上下的拖动
//                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                //只允许从右向左侧滑
//                int swipeFlags = ItemTouchHelper.LEFT;
//                return makeMovementFlags(dragFlags, swipeFlags);
//
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                //得到当拖拽的viewHolder的Position
//                List<TableNumArrangeBean.ArrangesBean> data = refereeCompetitionInterfaceAdapter.getData();
//                int fromPosition = viewHolder.getAdapterPosition();
//                String arrangeId = "";
//                //拿到当前拖拽到的item的viewHolder
//                int toPosition = target.getAdapterPosition();
//                if (fromPosition < toPosition) {
//                    for (int i = fromPosition; i < toPosition; i++) {
//                        Collections.swap(data, i, i + 1);
//                    }
//                } else {
//                    for (int i = fromPosition; i > toPosition; i--) {
//                        Collections.swap(data, i, i - 1);
//                    }
//                }
//                arrangeId = "" + data.get(fromPosition).getId();
//                Log.e(TAG, "onMove: fromPosition===" + fromPosition + "    toPosition===" + toPosition);
//                refereeCompetitionInterfaceAdapter.notifyItemMoved(fromPosition, toPosition);
//                //调接口
//                mViewModel.sortTableNumArrange("" + arrangeId, "" + toPosition);
//                refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
////                int position = viewHolder.getAdapterPosition();
////                myAdapter.notifyItemRemoved(position);
////                datas.remove(position);
//            }
//
//            /**
//             * 重写拖拽可用
//             * @return
//             */
//            @Override
//            public boolean isLongPressDragEnabled() {
//                return false;
//            }
//
//            /**
//             * 长按选中Item的时候开始调用
//             *
//             * @param viewHolder
//             * @param actionState
//             */
//            @Override
//            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
////                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
////                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
////                }
//                super.onSelectedChanged(viewHolder, actionState);
//            }
//
//            /**
//             * 手指松开的时候还原
//             * @param recyclerView
//             * @param viewHolder
//             */
//            @Override
//            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                super.clearView(recyclerView, viewHolder);
////                viewHolder.itemView.setBackgroundColor(0);
//            }
//        });
        mBinding.viewAll.setVisibility(View.INVISIBLE);
        mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
        mBinding.viewDaisai.setVisibility(View.VISIBLE);
        mBinding.viewEnd.setVisibility(View.INVISIBLE);
        mBinding.tvDaisai.setTextColor(Color.parseColor("#4795ED"));
        mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));

    }

    //展示弃权
    private void showGiveUpDialog() {
        CustomDialog customDialogConfirm = new CustomDialog(RefereeCompetitionInterfaceActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_show_give_up);
//        customDialogConfirm.setCancelable(false);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView llOneGiveUp = customView.findViewById(R.id.ll_one_give_up);
        ImageView ivOneGiveUp = customView.findViewById(R.id.iv_left_give_up);
        ImageView ivRightGiveUp = customView.findViewById(R.id.iv_right_give_up);
        ImageView ivAllGiveUp = customView.findViewById(R.id.iv_all_give_up);
        clearViews(ivOneGiveUp, ivRightGiveUp, ivAllGiveUp);
        llOneGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViews(ivOneGiveUp, ivRightGiveUp, ivAllGiveUp);
                ivOneGiveUp.setImageResource(R.drawable.icon_login_selected);
            }
        });
        ivRightGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViews(ivOneGiveUp, ivRightGiveUp, ivAllGiveUp);
                ivRightGiveUp.setImageResource(R.drawable.icon_login_selected);
            }
        });
        ivAllGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViews(ivOneGiveUp, ivRightGiveUp, ivAllGiveUp);
                ivAllGiveUp.setImageResource(R.drawable.icon_login_selected);
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();

                }

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                    //                    if (timer != null) {
                    //                        timer.cancel();
                    //                    }
                }
            }
        });

    }

    private void clearViews(ImageView ivLeftGiveUp, ImageView ivRightGiveUp, ImageView ivAllGiveUp) {

        ivLeftGiveUp.setImageResource(R.drawable.icon_login_unselected);
        ivRightGiveUp.setImageResource(R.drawable.icon_login_unselected);
        ivAllGiveUp.setImageResource(R.drawable.icon_login_unselected);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageToTargetEvent(ToTargetEvent event) {
        int adapterPosition = event.getAdapterPosition();
        long id = event.getId();
        Log.e(TAG, "messageToTargetEvent: id===" + id + "position====" + adapterPosition);
//        showLoading();
        mViewModel.sortTableNumArrange("" + id, "" + adapterPosition);
    }

    public void showSurprise(TableNumArrangeBean.ArrangesBean arrangesBean) {
        CustomDialog customDialogConfirm = new CustomDialog(RefereeCompetitionInterfaceActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_start_competition);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm, arrangesBean);
    }


    private void initData() {
        showLoading();
        mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);
    }

    int itemType = 0;

    private void showAskConfirmView(View customView, CustomDialog customDialog, TableNumArrangeBean.ArrangesBean arrangesBean) {

        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvrightName = customView.findViewById(R.id.tv_right_name);
        TextView tvBeginTime = customView.findViewById(R.id.tv_time);
        tvBeginTime.setText("" + arrangesBean.getBeginTime());
        itemType = Integer.parseInt(arrangesBean.getItemType());
        if (itemType == 1 || itemType == 2) {
            tvLeftName.setText("" + arrangesBean.getPlayer1Name());
            tvrightName.setText("" + arrangesBean.getPlayer3Name());
        } else {
            tvLeftName.setText("" + arrangesBean.getPlayer1Name() + "/" + arrangesBean.getPlayer2Name());
            tvrightName.setText("" + arrangesBean.getPlayer3Name() + "/" + arrangesBean.getPlayer4Name());
        }
        tvSure.setText("确定");
        tvSure.setEnabled(true);
        tvSure.setBackgroundResource(R.drawable.shape_club_blue);
//        timer = new CountDownTimer(3000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                tvSure.setText("(确定" + millisUntilFinished / 1000 + ")");
//                tvSure.setEnabled(false);
//                tvSure.setBackgroundResource(R.drawable.shape_club_gray);
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();


        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
//                    if (timer != null) {
//                        timer.cancel();
//                    }
                    goJump(arrangesBean, itemType);
                }

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
//                    if (timer != null) {
//                        timer.cancel();
//                    }
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageSwipEvent(SwipMatchEvent event) {
        if (status.equals("1")) {
            String clickPosition = event.getClickPosition();
            if (!TextUtils.isEmpty(clickPosition)) {
                int myClickPosition = Integer.parseInt(clickPosition);
                if (mItems != null && mItems.size() > 0) {
                    mItems.remove(myClickPosition);
                    refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                }
            }
        } else {
            Log.e(TAG, "messageSwipEvent: ===" + event.getClickPosition());
            String clickPosition = event.getClickPosition();
            if (!TextUtils.isEmpty(clickPosition)) {
                int myClickPosition = Integer.parseInt(clickPosition);

                if (mItems != null && mItems.size() > 0) {
                    if (myClickPosition != 0) {
                        TableNumArrangeBean.ArrangesBean arrangesBean = mItems.get(myClickPosition);

//                    null未开始,1已结束，2进行中，3左边已确认成绩4右边已确认成绩6双方都确认成绩7团队左方填完对阵表8团队右边填完对阵表9，双方都填完10，虚位待赛
                        arrangesBean.setStatus("1");
                        arrangesBean.setLeftWaiver(event.getLeftWavier());
                        arrangesBean.setRightWaiver(event.getRightWavier());
                        arrangesBean.setLeftWinCount(event.getLeftCount());
                        arrangesBean.setRightWinCount(event.getRightCount());
                        mItems.set(myClickPosition, arrangesBean);
                        refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }

    private void goAnotherJump(TableNumArrangeBean.ArrangesBean arrangesBean, int itemType) {
        itemType = Integer.parseInt(arrangesBean.getItemType());
        //判断是单打 还是双打   还是团体
        if (itemType == 1) {//团体   TeamResultOfficialActivity
            Intent intent = new Intent();
            intent.setClass(RefereeCompetitionInterfaceActivity.this, TeamResultsOfficialActivity.class);
            intent.putExtra("ids", "" + arrangesBean.getId());
            intent.putExtra("cliclkPosition", "" + clickPosition);

            startActivity(intent);

        } else if (itemType == 2) {//单打
            Intent intent3 = new Intent();
            intent3.setClass(RefereeCompetitionInterfaceActivity.this, SinglesDetailOfficialActivity.class);
            intent3.putExtra("ids", "" + arrangesBean.getId());
            intent3.putExtra("cliclkPosition", "" + clickPosition);

            startActivity(intent3);
        } else {
            //双打
            Intent intent2 = new Intent();
            intent2.setClass(RefereeCompetitionInterfaceActivity.this, DoubleDetailOfficialActivity.class);
            intent2.putExtra("ids", "" + arrangesBean.getId());
            intent2.putExtra("cliclkPosition", "" + clickPosition);
            startActivity(intent2);
        }
    }

    private void goJump(TableNumArrangeBean.ArrangesBean arrangesBean, int itemType) {
        ArrangeDrawDataBean arrangeDrawBean = new ArrangeDrawDataBean(
                "" + arrangesBean.getTitle(), "" + arrangesBean.getItemType(),
                "" + arrangesBean.getId(), "" + arrangesBean.getPlayer1Id(), "" + arrangesBean.getPlayer1Name()
                , "" + arrangesBean.getHeadImg1(), "" + arrangesBean.getPlayer2Name(),
                "" + arrangesBean.getHeadImg2(), "" + arrangesBean.getPlayer3Id(),
                "" + arrangesBean.getPlayer3Name(), "" + arrangesBean.getHeadImg3()
                , "" + arrangesBean.getPlayer4Name(), "" + arrangesBean.getHeadImg4(), "" + strMatchId
        );
        //SingleFirstOptionActivity

        //判断是单打 还是双打   还是团体
        if (itemType == 1) {//团体   TeamResultOfficialActivity
            Intent intent = new Intent();
            intent.setClass(RefereeCompetitionInterfaceActivity.this, DrawForHostAndVistors.class);
            intent.putExtra("data", (Serializable) arrangeDrawBean);
            intent.putExtra("cliclkPosition", "" + clickPosition);

            startActivity(intent);

        } else {//单打 还是双打 跳转到这个界面
            Intent intent = new Intent();
            intent.setClass(RefereeCompetitionInterfaceActivity.this, SingleFirstOptionActivity.class);
            intent.putExtra("cliclkPosition", "" + clickPosition);

            intent.putExtra("data", (Serializable) arrangeDrawBean);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent(RefereeCompetitionInterfaceActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.MATCH_ID, "" + strMatchId);
                intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + matchTitle);
                startActivity(intent);
                break;
            case R.id.iv_finish:

                finish();
                break;
            case R.id.tv_paixu://排序
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (type == 0) {
                    type = 1;
                    mBinding.swFresh.setEnableRefresh(false);
                    mBinding.swFresh.setEnabled(false);

                    iScanTuodong = true;
                    mBinding.tvPaixu.setText("完成");

                    itemTouchHelper.attachToRecyclerView(mBinding.rvRefereeAllMatch);
                    refereeCompetitionInterfaceAdapter.enableDragItem(itemTouchHelper);
                    refereeCompetitionInterfaceAdapter.isShowDragable(true);


                    refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                } else {
                    type = 0;
                    mBinding.swFresh.setEnableRefresh(true);
                    mBinding.swFresh.setEnabled(true);
                    refereeCompetitionInterfaceAdapter.isShowDragable(false);
                    iScanTuodong = false;
//                    haveMore = true;
//                    pageNum = 1;
//                    pageSize = 8;
//                    clickPosition = 0;
//
//                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
//                            "" + mIds, "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
////                            "" + mIds, "" + times + " 00:00:00", "" + status, "" + code);
                    mBinding.tvPaixu.setText("排序");
                    refereeCompetitionInterfaceAdapter.disableDragItem();
                    refereeCompetitionInterfaceAdapter.notifyDataSetChanged();
                }


                break;
            case R.id.ll_all:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewAll.setVisibility(View.VISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
                mBinding.viewEnd.setVisibility(View.INVISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
                status = "";
                clickPosition = 0;
                haveMore = true;
                pageNum = 1;

                showLoading();
                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                        "" + mIds, "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);

                break;

            case R.id.ll_end:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clickPosition = 0;
                mBinding.viewAll.setVisibility(View.INVISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
                mBinding.viewEnd.setVisibility(View.VISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#4795ED"));
                status = "2";
                haveMore = true;
                pageNum = 1;

                showLoading();
                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
                        "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);

                break;
            case R.id.ll_daisai:
                status = "1";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewAll.setVisibility(View.INVISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
                mBinding.viewDaisai.setVisibility(View.VISIBLE);
                mBinding.viewEnd.setVisibility(View.INVISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
                haveMore = true;
                pageNum = 1;

                clickPosition = 0;
                showLoading();
                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                        "" + mIds, "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);


                break;
            case R.id.ll_search_jump:
                SearchRefereeListActivity.Companion.startActivity(this, strMatchId,
                        timeCodeDisplay,strTableNum,"" + times + " 00:00:00");
                break;

//            case R.id.ll_time:
//                showTime(beginCalendar, endCalendar);
//
//
//                break;
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        haveMore = true;
        pageNum = 1;

        clickPosition = 0;
        initData();


    }
}