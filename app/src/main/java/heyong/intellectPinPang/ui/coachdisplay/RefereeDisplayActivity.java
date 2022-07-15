package heyong.intellectPinPang.ui.coachdisplay;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter;
import heyong.intellectPinPang.bean.competition.DisplayBeginArrangeBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ActivityRefereeDisplayBinding;
import heyong.intellectPinPang.event.BeginArrangeDisplayChouEvent;
import heyong.intellectPinPang.event.EndTeamEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.OnRecyclerItemClickListener;

/**
 * 裁判员展示界面   主队ABCAB  客队XYZXZ
 */
public class RefereeDisplayActivity extends BaseActivity<ActivityRefereeDisplayBinding, BaseViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private static final int REQUEST_CODE_C = 1;
    List<RefereeDisPlayBean> refereeDisPlayBeanList = new ArrayList<>();
    //showStus 1显示开始按钮  2 显示详情  显示比分
    List<RefereeDisplayTimeBean> refereeDisplayTimeBeans = new ArrayList<>();

    RefereeDisplayAdapter refereeDisplayAdapter;
    RefereeDisplayListAdapter refereeDisplayListAdapter;
    private String showTime = "";
    private int inputType = 0;
    private ItemTouchHelper mItemTouchHelper;
    private int type = 0;
    private boolean iScanTuodong = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_referee_display;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        refereeDisplayAdapter = new RefereeDisplayAdapter();

        mBinding.setListener(this);
        refereeDisPlayBeanList.clear();
        RefereeDisPlayBean refereeDisPlayBean1 = new RefereeDisPlayBean();
        refereeDisPlayBean1.setLeftLogo("1");
        refereeDisPlayBean1.setLeftName("超级战神队");
        refereeDisPlayBean1.setRightLogo("2");
        refereeDisPlayBean1.setRightName("小亮俱乐部");
        refereeDisPlayBean1.setScore("0:0");
        refereeDisPlayBean1.setShowStatus(1);
        refereeDisPlayBean1.setTime("08:00");
        refereeDisPlayBean1.setItemType(1);
        refereeDisPlayBean1.setTitle("成人组男子团体【第一阶段A组】");


        RefereeDisPlayBean refereeDisPlayBean2 = new RefereeDisPlayBean();
        refereeDisPlayBean2.setLeftLogo("https://img0.baidu.com/it/u=307273849,3381047345&fm=26&fmt=auto&gp=0.jpg");
        refereeDisPlayBean2.setLeftName("郭德纲");
        refereeDisPlayBean2.setRightLogo("https://img1.baidu.com/it/u=1438419357,3159337688&fm=26&fmt=auto&gp=0.jpg");
        refereeDisPlayBean2.setRightName("于谦");
        refereeDisPlayBean2.setScore("0:0");
        refereeDisPlayBean2.setShowStatus(1);
        refereeDisPlayBean2.setTime("14:00");
        refereeDisPlayBean2.setLeftWinCount(0);
        refereeDisPlayBean2.setRightWinCount(0);
        refereeDisPlayBean2.setClubName1("逗哏俱乐部");
        refereeDisPlayBean2.setClubName2("捧哏俱乐部");
        refereeDisPlayBean2.setShowGiveUp(true);
        refereeDisPlayBean2.setTitle("成人组男子单打【淘汰赛】");
        refereeDisPlayBean2.setItemType(2);
        refereeDisPlayBeanList.add(refereeDisPlayBean1);
        refereeDisPlayBeanList.add(refereeDisPlayBean2);
        refereeDisplayTimeBeans.clear();
        showTime = "6月11日";
        for (int i = 0; i < 7; i++) {
            RefereeDisplayTimeBean refereeDisplayTimeBean = new RefereeDisplayTimeBean();
            if (i == 1) {
                refereeDisplayTimeBean.setSelect(true);
            } else {
                refereeDisplayTimeBean.setSelect(false);
            }
            refereeDisplayTimeBean.setIds("" + i);
            refereeDisplayTimeBean.setTime("6月" + (10 + i) + "日");
            refereeDisplayTimeBeans.add(refereeDisplayTimeBean);
        }
        mBinding.rvTime.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mBinding.rvTime.setAdapter(refereeDisplayAdapter);
        refereeDisplayAdapter.setNewData(refereeDisplayTimeBeans);

        refereeDisplayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (AntiShakeUtils.isInvalidClick(view))
                    return;
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
                    showTime = data1.get(position).getTime();
                    if (data1.get(position).getTime().equals("6月11日")) {
                        refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
                    } else {
                        refereeDisplayListAdapter.setNewData(new ArrayList<>());
                    }
                    dealwithShowData();
                }


            }
        });
        refereeDisplayListAdapter = new RefereeDisplayListAdapter(refereeDisPlayBeanList);
        refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
        mBinding.rvGameList.setAdapter(refereeDisplayListAdapter);
        mBinding.rvGameList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        refereeDisplayListAdapter.setOnItemChildClickListener(this);
        refereeDisplayListAdapter.setOnItemClickListener(this);


        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖动支持向下和向上
                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;//滑动支持向左和向右
     /*   //Grid部分功能
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN |ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlag = 0;*/
                return makeMovementFlags(dragFlag, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                List<RefereeDisPlayBean> data = refereeDisplayListAdapter.getData();
                int fromPosition = viewHolder.getAdapterPosition();
                String arrangeId = "";
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(data, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(data, i, i - 1);
                    }
                }
                Log.e(TAG, "onMove: fromPosition===" + fromPosition + "    toPosition===" + toPosition);
                refereeDisplayListAdapter.notifyItemMoved(fromPosition, toPosition);
                //调接口
                //                mViewModel.sortTableNumArrange("" + arrangeId, "" + toPosition);
                refereeDisplayListAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //                int position = viewHolder.getAdapterPosition();
                //                myAdapter.notifyItemRemoved(position);
                //                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                //                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                //                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                //                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //                viewHolder.itemView.setBackgroundColor(0);
            }
        });
    }

    private void dealwithShowData() {
        if (showTime.equals("6月11日")) {
            refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
            mBinding.tvAll.setText("总场次(" + refereeDisPlayBeanList.size() + ")");
        } else {
            refereeDisplayListAdapter.setNewData(new ArrayList<>());
            mBinding.tvAll.setText("总场次(" + 0 + ")");
        }

        List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
        List<RefereeDisPlayBean> data = refereeDisPlayBeanList;
        for (int i = 0; i < data.size(); i++) {
            int showStatus = data.get(i).getShowStatus();
            if (showStatus == 1) {
                daisaiList.add(data.get(i));
            }
        }
        if (showTime.equals("6月11日")) {
            //            refereeDisplayListAdapter.setNewData(daisaiList);
            mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
        } else {
            refereeDisplayListAdapter.setNewData(new ArrayList<>());
            mBinding.tvDaisai.setText("待赛(" + 0 + ")");
        }

        List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
        List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
        for (int i = 0; i < dataAll.size(); i++) {
            int showStatus = dataAll.get(i).getShowStatus();
            if (showStatus == 2) {
                daisaiEnd.add(dataAll.get(i));
            }
        }
        if (showTime.equals("6月11日")) {
            //            refereeDisplayListAdapter.setNewData(daisaiEnd);
            mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
        } else {
            refereeDisplayListAdapter.setNewData(new ArrayList<>());
            mBinding.tvEnd.setText("已结束(" + 0 + ")");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                inputType = 0;
                initClearView();
                mBinding.tvAll.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvAll.setBackgroundResource(R.drawable.shape_club_blue);

                if (showTime.equals("6月11日")) {
                    refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
                    mBinding.tvAll.setText("总场次(" + refereeDisPlayBeanList.size() + ")");
                } else {
                    refereeDisplayListAdapter.setNewData(new ArrayList<>());
                    mBinding.tvAll.setText("总场次(" + 0 + ")");
                }
                break;
            case R.id.tv_daisai:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                inputType = 1;
                initClearView();
                mBinding.tvDaisai.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#FFFFFFFF"));
                List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
                List<RefereeDisPlayBean> data = refereeDisPlayBeanList;
                for (int i = 0; i < data.size(); i++) {
                    int showStatus = data.get(i).getShowStatus();
                    if (showStatus == 1) {
                        daisaiList.add(data.get(i));
                    }
                }
                if (showTime.equals("6月11日")) {
                    refereeDisplayListAdapter.setNewData(daisaiList);
                    mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
                } else {
                    refereeDisplayListAdapter.setNewData(new ArrayList<>());
                    mBinding.tvDaisai.setText("待赛(" + 0 + ")");
                }

                break;
            case R.id.tv_end:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                inputType = 2;
                initClearView();
                mBinding.tvEnd.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvEnd.setTextColor(Color.parseColor("#FFFFFFFF"));

                List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
                List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
                for (int i = 0; i < dataAll.size(); i++) {
                    int showStatus = dataAll.get(i).getShowStatus();
                    if (showStatus == 2) {
                        daisaiEnd.add(dataAll.get(i));
                    }
                }
                if (showTime.equals("6月11日")) {
                    refereeDisplayListAdapter.setNewData(daisaiEnd);
                    mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
                } else {
                    refereeDisplayListAdapter.setNewData(new ArrayList<>());
                    mBinding.tvEnd.setText("已结束(" + 0 + ")");
                }
                break;
            case R.id.tv_paixu://排序
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (type == 0) {
                    type = 1;
                    iScanTuodong = true;
                    mBinding.tvPaixu.setText("完成");
                    refereeDisplayListAdapter.enableDragItem(mItemTouchHelper);
                    mItemTouchHelper.attachToRecyclerView(mBinding.rvGameList);
                    refereeDisplayListAdapter.isShowDragable(true);

                    mBinding.rvGameList.addOnItemTouchListener(new OnRecyclerItemClickListener(mBinding.rvGameList) {
                        @Override
                        public void onItemClick(RecyclerView.ViewHolder vh) {

                        }

                        @Override
                        public void onItemLongClick(RecyclerView.ViewHolder vh) {
                            //判断被拖拽的是否是前两个，如果不是则执行拖拽
                            //                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                            if (iScanTuodong) {
                                mItemTouchHelper.startDrag(vh);

                                //获取系统震动服务
                                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                                vib.vibrate(70);
                            }

                            //                }
                        }
                    });
                    refereeDisplayListAdapter.notifyDataSetChanged();
                } else {
                    type = 0;

                    refereeDisplayListAdapter.isShowDragable(false);
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
                    refereeDisplayListAdapter.disableDragItem();
                    refereeDisplayListAdapter.notifyDataSetChanged();
                }


                break;
        }
    }

    private void initClearView() {
        mBinding.tvAll.setTextColor(Color.parseColor("#FF333333"));
        mBinding.tvAll.setBackground(null);
        mBinding.tvDaisai.setTextColor(Color.parseColor("#FF333333"));
        mBinding.tvDaisai.setBackground(null);
        mBinding.tvEnd.setTextColor(Color.parseColor("#FF333333"));
        mBinding.tvEnd.setBackground(null);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            RefereeDisPlayBean refereeDisPlayBean = refereeDisplayListAdapter.getData().get(position);
            int showStatus = refereeDisPlayBean.getShowStatus();
            int itemType = refereeDisPlayBean.getItemType();

            switch (view.getId()) {
                case R.id.tv_game_end_status:
                    switch (showStatus) {
                        case 1://开始
                            switch (itemType) {
                                case 1://团体
                                    goActivity(DisplayDrawForHostAndVistors.class);

                                    break;
                                case 2://单打

                                    goActivity(DisplaySingleFirstOptionActivity.class);
                                    break;
                            }

                            break;
                        case 2://详情
                            switch (itemType) {
                                case 1://团体

                                    Intent intentTeam = new Intent(RefereeDisplayActivity.this, DisplayTeamResultsOfficialScoreActivity.class);
                                    intentTeam.putExtra("data", displayBeginArrangeBean);
                                    startActivity(intentTeam);


                                    break;
                                case 2://单打
                                    XlMatchInfoArrangeChessBean xlMatchInfoArrangeChessBean = new XlMatchInfoArrangeChessBean();
                                    Intent intentSingle = new Intent(RefereeDisplayActivity.this, DisplaySinglesDetailScoreOfficialActivity.class);
                                    RefereeDisPlayBean refereeDisPlayBean1 = refereeDisplayListAdapter.getData().get(position);
                                    List<RefereeDisPlayBean.ChessBean> chess = refereeDisPlayBean1.getChess();
                                    List<XlMatchInfoArrangeChessBean.ChessBean> chessSingle = new ArrayList<>();
                                    for (int i = 0; i < chess.size(); i++) {
                                        XlMatchInfoArrangeChessBean.ChessBean chessBean = new XlMatchInfoArrangeChessBean.ChessBean();
                                        chessBean.setLeftCount(chess.get(i).getLeftCount());
                                        chessBean.setRightCount(chess.get(i).getRightCount());
                                        chessBean.setLeftWavier("" + chess.get(i).getLeftWavier());
                                        chessBean.setRightWavier("" + chess.get(i).getRightWavier());
                                        chessSingle.add(chessBean);
                                    }
                                    xlMatchInfoArrangeChessBean.setChess(chessSingle);
                                    xlMatchInfoArrangeChessBean.setHeadImg1(refereeDisPlayBean.getLeftLogo());
                                    xlMatchInfoArrangeChessBean.setHeadImg3(refereeDisPlayBean.getRightLogo());
                                    xlMatchInfoArrangeChessBean.setPlayer1(refereeDisPlayBean.getLeftName());
                                    xlMatchInfoArrangeChessBean.setPlayer3(refereeDisPlayBean.getRightName());
                                    xlMatchInfoArrangeChessBean.setLeftCount("" + refereeDisPlayBean.getLeftWinCount());
                                    xlMatchInfoArrangeChessBean.setRightCount("" + refereeDisPlayBean.getRightWinCount());
                                    xlMatchInfoArrangeChessBean.setClub1Name(refereeDisPlayBean.getClubName1());
                                    xlMatchInfoArrangeChessBean.setClub2Name(refereeDisPlayBean.getClubName2());
                                    intentSingle.putExtra("data", xlMatchInfoArrangeChessBean);
                                    startActivity(intentSingle);

                                    break;
                            }

                            break;

                    }
                    break;


                case R.id.tv_give_up:
                    List<RefereeDisPlayBean> data = refereeDisplayListAdapter.getData();
                    Intent intentmm = new Intent(RefereeDisplayActivity.this, DisplayWavierUpActivity.class);
                    intentmm.putExtra("data", refereeDisPlayBean);
                    intentmm.putExtra("position", "" + position);
                    intentmm.putExtra("dataList", "" + new Gson().toJson(data));
                    startActivityForResult(intentmm, REQUEST_CODE_C);


                    break;
            }
        }
    }

    DisplayBeginArrangeBean displayBeginArrangeBean;

    //修改   获取团体的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EndTeamEventsBus(EndTeamEvent event) {
        String beginData = event.getStrJson();
        Log.e(TAG, "EndTeamEventsBus: =========" + beginData);
        displayBeginArrangeBean = new Gson().fromJson(beginData, DisplayBeginArrangeBean.class);
        int leftAllWinCount = event.getLeftAllWinCount();
        int rightAllWinCount = event.getRightAllWinCount();
        List<RefereeDisPlayBean> data = refereeDisPlayBeanList;
        RefereeDisPlayBean refereeDisPlayBeans = data.get(0);

        refereeDisPlayBeans.setLeftLogo("1");
        refereeDisPlayBeans.setLeftName("超级战神队");
        refereeDisPlayBeans.setRightLogo("2");
        refereeDisPlayBeans.setRightName("小亮俱乐部");
        refereeDisPlayBeans.setScore("" + leftAllWinCount + ":" + rightAllWinCount);
        refereeDisPlayBeans.setShowStatus(2);
        refereeDisPlayBeans.setTime("08:00");
        refereeDisPlayBeans.setLeftWinCount(leftAllWinCount);
        refereeDisPlayBeans.setRightWinCount(rightAllWinCount);
        refereeDisPlayBeans.setItemType(1);
        refereeDisPlayBeans.setShowGiveUp(true);

        refereeDisPlayBeans.setTitle("成人组男子团体【第一阶段A组】");
        data.set(0, refereeDisPlayBeans);
        refereeDisplayListAdapter.setNewData(data);
        if (inputType == 1) {
            List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
            List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
            for (int i = 0; i < dataAll.size(); i++) {
                int showStatus = dataAll.get(i).getShowStatus();
                if (showStatus == 1) {
                    daisaiList.add(dataAll.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                refereeDisplayListAdapter.setNewData(daisaiList);
                mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
            } else {
                refereeDisplayListAdapter.setNewData(new ArrayList<>());
                mBinding.tvDaisai.setText("待赛(" + 0 + ")");
            }


            List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
            for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                if (showStatus == 2) {
                    daisaiEnd.add(refereeDisPlayBeanList.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
            } else {
                mBinding.tvEnd.setText("已结束(" + 0 + ")");
            }
        } else if (inputType == 0) {
            refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);

            List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
            List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
            for (int i = 0; i < dataAll.size(); i++) {
                int showStatus = dataAll.get(i).getShowStatus();
                if (showStatus == 1) {
                    daisaiList.add(dataAll.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
            } else {
                mBinding.tvDaisai.setText("待赛(" + 0 + ")");
            }


            List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
            for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                if (showStatus == 2) {
                    daisaiEnd.add(refereeDisPlayBeanList.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
            } else {
                mBinding.tvEnd.setText("已结束(" + 0 + ")");
            }
        }
    }


    //修改  单打的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BeginArrangeDisplayEventBus(BeginArrangeDisplayChouEvent events) {
        String beginData = events.getBeginData();
        DisplayBeginArrangeBean beginArrangeBean = new Gson().fromJson(beginData, DisplayBeginArrangeBean.class);
        List<RefereeDisPlayBean> data = refereeDisPlayBeanList;
        RefereeDisPlayBean refereeDisPlayBeans = data.get(1);
        int leftCount = beginArrangeBean.getLeftCount();
        int rightCount = beginArrangeBean.getRightCount();
        refereeDisPlayBeans.setLeftLogo("https://img0.baidu.com/it/u=307273849,3381047345&fm=26&fmt=auto&gp=0.jpg");
        refereeDisPlayBeans.setLeftName("郭德纲");
        refereeDisPlayBeans.setRightLogo("https://img1.baidu.com/it/u=1438419357,3159337688&fm=26&fmt=auto&gp=0.jpg");
        refereeDisPlayBeans.setRightName("于谦");
        refereeDisPlayBeans.setLeftWinCount(leftCount);
        refereeDisPlayBeans.setRightWinCount(rightCount);
        refereeDisPlayBeans.setLeftWavier(beginArrangeBean.getLeftWaiver());
        refereeDisPlayBeans.setRightWavier(beginArrangeBean.getRightWaiver());
        refereeDisPlayBeans.setShowStatus(2);
        refereeDisPlayBeans.setTime("14:00");
        List<RefereeDisPlayBean.ChessBean> chessBeans = new ArrayList<>();
        for (int i = 0; i < beginArrangeBean.getChess().size(); i++) {
            RefereeDisPlayBean.ChessBean chessBean = new RefereeDisPlayBean.ChessBean();
            DisplayBeginArrangeBean.ChessBean chessBean1 = beginArrangeBean.getChess().get(i);
            chessBean.setLeftWavier(chessBean1.getLeftWavier());
            chessBean.setRightWavier(chessBean1.getRightWavier());
            chessBean.setLeftCount(chessBean1.getLeftCount());
            chessBean.setRightCount(chessBean1.getRightCount());
            //
            chessBeans.add(chessBean);
        }
        refereeDisPlayBeans.setChess(chessBeans);
        refereeDisPlayBeans.setTitle("成人组男子单打【淘汰赛】");
        refereeDisPlayBeans.setItemType(2);
        refereeDisPlayBeans.setShowGiveUp(true);

        data.set(1, refereeDisPlayBeans);
        refereeDisplayListAdapter.setNewData(data);
        if (inputType == 1) {
            List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
            List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
            for (int i = 0; i < dataAll.size(); i++) {
                int showStatus = dataAll.get(i).getShowStatus();
                if (showStatus == 1) {
                    daisaiList.add(dataAll.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                refereeDisplayListAdapter.setNewData(daisaiList);
                mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
            } else {
                refereeDisplayListAdapter.setNewData(new ArrayList<>());
                mBinding.tvDaisai.setText("待赛(" + 0 + ")");
            }


            List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
            for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                if (showStatus == 2) {
                    daisaiEnd.add(refereeDisPlayBeanList.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
            } else {
                mBinding.tvEnd.setText("已结束(" + 0 + ")");
            }
        } else if (inputType == 0) {
            refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
            List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
            List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
            for (int i = 0; i < dataAll.size(); i++) {
                int showStatus = dataAll.get(i).getShowStatus();
                if (showStatus == 1) {
                    daisaiList.add(dataAll.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
            } else {
                mBinding.tvDaisai.setText("待赛(" + 0 + ")");
            }


            List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
            for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                if (showStatus == 2) {
                    daisaiEnd.add(refereeDisPlayBeanList.get(i));
                }
            }
            if (showTime.equals("6月11日")) {
                mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
            } else {
                mBinding.tvEnd.setText("已结束(" + 0 + ")");
            }

        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RefereeDisPlayBean refereeDisPlayBean = refereeDisplayListAdapter.getData().get(position);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String totalList = data.getStringExtra("totalList");
            if (totalList != null) {
                Log.e(TAG, "onActivityResult: totalList===" + totalList);
                refereeDisPlayBeanList = new Gson().fromJson(totalList, new TypeToken<List<RefereeDisPlayBean>>() {
                }.getType());
                refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);
                if (inputType == 1) {
                    List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
                    List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
                    for (int i = 0; i < dataAll.size(); i++) {
                        int showStatus = dataAll.get(i).getShowStatus();
                        if (showStatus == 1) {
                            daisaiList.add(dataAll.get(i));
                        }
                    }
                    if (showTime.equals("6月11日")) {
                        refereeDisplayListAdapter.setNewData(daisaiList);
                        mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
                    } else {
                        refereeDisplayListAdapter.setNewData(new ArrayList<>());
                        mBinding.tvDaisai.setText("待赛(" + 0 + ")");
                    }


                    List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
                    for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                        int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                        if (showStatus == 2) {
                            daisaiEnd.add(refereeDisPlayBeanList.get(i));
                        }
                    }
                    if (showTime.equals("6月11日")) {
                        mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
                    } else {
                        mBinding.tvEnd.setText("已结束(" + 0 + ")");
                    }
                } else if (inputType == 0) {
                    refereeDisplayListAdapter.setNewData(refereeDisPlayBeanList);

                    List<RefereeDisPlayBean> daisaiList = new ArrayList<>();
                    List<RefereeDisPlayBean> dataAll = refereeDisPlayBeanList;
                    for (int i = 0; i < dataAll.size(); i++) {
                        int showStatus = dataAll.get(i).getShowStatus();
                        if (showStatus == 1) {
                            daisaiList.add(dataAll.get(i));
                        }
                    }
                    if (showTime.equals("6月11日")) {
                        mBinding.tvDaisai.setText("待赛(" + daisaiList.size() + ")");
                    } else {
                        mBinding.tvDaisai.setText("待赛(" + 0 + ")");
                    }


                    List<RefereeDisPlayBean> daisaiEnd = new ArrayList<>();
                    for (int i = 0; i < refereeDisPlayBeanList.size(); i++) {
                        int showStatus = refereeDisPlayBeanList.get(i).getShowStatus();
                        if (showStatus == 2) {
                            daisaiEnd.add(refereeDisPlayBeanList.get(i));
                        }
                    }
                    if (showTime.equals("6月11日")) {
                        mBinding.tvEnd.setText("已结束(" + daisaiEnd.size() + ")");
                    } else {
                        mBinding.tvEnd.setText("已结束(" + 0 + ")");
                    }
                }
            }
        }

    }
}