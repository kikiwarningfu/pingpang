package heyong.intellectPinPang.ui.coachdisplay;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesScoreDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivitySinglesScoreDetailOfficialBinding;

public class DisplaySinglesDetailScoreOfficialActivity extends BaseActivity<ActivitySinglesScoreDetailOfficialBinding, BaseViewModel> implements View.OnClickListener {

    private SinglesScoreDetailOfficialAdapter singlesDetailAdapter;

    public static final String IDS = "ids";
    private String ids = "";
    private boolean isShowRefee = false;
    private boolean isShowSign = false;
    XlMatchInfoArrangeChessBean xlMatchInfoArrangeChessBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_singles_score_detail_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        xlMatchInfoArrangeChessBean = (XlMatchInfoArrangeChessBean) getIntent().getSerializableExtra("data");
        singlesDetailAdapter = new SinglesScoreDetailOfficialAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);


        ImageLoader.loadImage(mBinding.viewLeftOval, xlMatchInfoArrangeChessBean.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        ImageLoader.loadImage(mBinding.viewRightOval, xlMatchInfoArrangeChessBean.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        mBinding.tvLeftName.setText("" + xlMatchInfoArrangeChessBean.getPlayer1());
        mBinding.tvRightName.setText("" + xlMatchInfoArrangeChessBean.getPlayer3());
        mBinding.tvLeftWinCount.setText("" + xlMatchInfoArrangeChessBean.getLeftCount());
        mBinding.tvRightWinCount.setText("" + xlMatchInfoArrangeChessBean.getRightCount());
        mBinding.tvLeftClubName.setText("" + xlMatchInfoArrangeChessBean.getClub1Name());
        mBinding.tvRightClubName.setText("" + xlMatchInfoArrangeChessBean.getClub2Name());
        mBinding.tvLeftClubNameTwo.setText("" + xlMatchInfoArrangeChessBean.getClub1Name());
        mBinding.tvRightClubNameTwo.setText("" + xlMatchInfoArrangeChessBean.getClub2Name());

        List<XlMatchInfoArrangeChessBean.ChessBean> xlClubContestArrangeChesses = xlMatchInfoArrangeChessBean.getChess();
        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
            singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
        } else {
            singlesDetailAdapter.setNewData(new ArrayList<>());
        }
//                        int status = Integer.parseInt(data.getStatus());
//                        if (status == 3) {//左边已确认成绩
//                            isShowSign = false;
//                            ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                        } else if (status == 4) {//右边已经确认
//                            isShowSign = false;
//                            ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                        } else if (status == 5) {//两边都确认
//                            isShowSign = true;
//                            ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                            ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                        } else {
//                            isShowSign = false;
//                        }
//                        if (!TextUtils.isEmpty(data.getRefeeQ()) || !data.getRefeeQ().equals("2")) {
//                            ImageLoader.loadImage(mBinding.ivSignTwo, data.getRefeeQ());
//                            isShowRefee = true;
//                        } else {
//                            isShowRefee = false;
//                        }
//
//                        if (isShowSign && isShowRefee) {
//                            ImageLoader.loadImage(mBinding.ivZhang, data.getZhangImg());
//                        }else
//                        {
//                            mBinding.ivZhang.setVisibility(View.GONE);
//                        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;

        }
    }
}
