package heyong.intellectPinPang.ui.coachdisplay;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.display.WavierTeaqmResultAdapter;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.databinding.ActivityWavierTeamResultBinding;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 团体比赛弃权
 */
public class WavierTeamResultActivity extends BaseActivity<ActivityWavierTeamResultBinding, HomePageViewModel> implements View.OnClickListener, OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    MyRefreshAnimHeader mRefreshAnimHeader;
    WavierTeaqmResultAdapter mWavierTeaqmResultAdapter;
    public static final String ARRANGE_ID = "arrange_id";
    private String strArrangeId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_wavier_team_result;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strArrangeId = getIntent().getStringExtra(ARRANGE_ID);
        mViewModel.beginArrange(strArrangeId);

        mRefreshAnimHeader = new MyRefreshAnimHeader(this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mWavierTeaqmResultAdapter = new WavierTeaqmResultAdapter();
        mBinding.mRecyclerView.setAdapter(mWavierTeaqmResultAdapter);
        mWavierTeaqmResultAdapter.setOnItemChildClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mViewModel.beginArrangeLiveData.observe(this, new Observer<ResponseBean<BeginArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<BeginArrangeBean> beginArrangeBeanResponseBean) {
                if (beginArrangeBeanResponseBean.getErrorCode().equals("200")) {
                    BeginArrangeBean data = beginArrangeBeanResponseBean.getData();
                    if (data != null) {
                        List<BeginArrangeBean.ChessBean> chess = data.getChess();
                        if (chess != null && chess.size() > 0) {
                            mWavierTeaqmResultAdapter.setNewData(chess);
                        } else {
                            toast("" + beginArrangeBeanResponseBean.getMessage());
                        }
                    } else {
                        toast("" + beginArrangeBeanResponseBean.getMessage());
                    }


                } else {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        BeginArrangeBean.ChessBean chessBean = mWavierTeaqmResultAdapter.getData().get(position);
        String leftName = chessBean.getPlayer1();
        String leftName1 = chessBean.getPlayer2();
        String rightName = chessBean.getPlayer3();
        String rightName2 = chessBean.getPlayer4();
        String hitType = chessBean.getHitType();
        String allLeftName = "";
        String allRightName = "";
        String round = CommonUtilis.numberToChinese(position + 1);

        switch (hitType) {
            case "1"://单打
                allLeftName = leftName;
                allRightName = rightName;
                break;
            case "2"://双打
                allLeftName = leftName + " " + leftName1;
                allRightName = rightName + " " + rightName2;
                break;
        }
        switch (view.getId()) {
            case R.id.tv_double_give_up://双弃权
                new MessageDialogBuilder(WavierTeamResultActivity.this)
                        .setMessage("")
                        .setTvTitle(""+"第" + round + "盘" +"双弃权"+" !")
                        .setTvSure("确定")
                        .setBtnCancleHint(true)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;
            case R.id.tv_left_give_up://左边弃权
                new MessageDialogBuilder(WavierTeamResultActivity.this)
                        .setMessage("")
                        .setTvTitle(""+"第" + round + "盘" +allLeftName+" !")
                        .setTvSure("确定")
                        .setBtnCancleHint(true)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;
            case R.id.tv_right_give_up://右边弃权
                new MessageDialogBuilder(WavierTeamResultActivity.this)
                        .setMessage("")
                        .setTvTitle(""+"第" + round + "盘" +allRightName+" !")
                        .setTvSure("确定")
                        .setBtnCancleHint(true)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;

        }
    }
}
