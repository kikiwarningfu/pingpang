package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveMatchDetailBean;
import heyong.intellectPinPang.databinding.ActivityDetailOfOfferBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.ui.mine.activity.live.ApplyLiveQualificationActivity;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 悬赏令详情界面
 */
public class DetailOfOfferActivity extends BaseActivity<ActivityDetailOfOfferBinding, LiveViewModel> implements View.OnClickListener {
    public static final String REQUEST_ID = "request_id";
    private String strRequestId = "";
    LiveMatchDetailBean data;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_detail_of_offer;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strRequestId = getIntent().getStringExtra(REQUEST_ID);
        mViewModel.liveMatchPublishDetail(strRequestId);


        mViewModel.liveMatchPublishDetailLiveData.observe(this, new Observer<ResponseBean<LiveMatchDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchDetailBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {

                    data = responseBean.getData();
                    if (data != null) {

                        List<LiveMatchDetailBean.LeftBean> left = data.getLeft();
                        List<LiveMatchDetailBean.RightBean> right = data.getRight();
                        mBinding.tvMatchTitle.setText("" + data.getMatchName());
                        mBinding.tvGroupTitle.setText("" + data.getTitle());
                        mBinding.tvBeginTime.setText("比赛时间：" + data.getGameTime());
                        mBinding.tvAddress.setText("比赛地点：" + data.getAddress());
                        mBinding.tvTableNum.setText("比赛球台：" + data.getTableNum() + "台");

                        if (left != null && left.size() == 1) {   //单打
                            mBinding.llOne.setVisibility(View.VISIBLE);
                            mBinding.llTwo.setVisibility(View.GONE);
                            if (left != null && left.size() > 0) {
                                LiveMatchDetailBean.LeftBean leftBean = left.get(0);
                                mBinding.tvLeftName.setText("" + leftBean.getName());
                                ImageLoader.loadImage(mBinding.viewLeftLogo, leftBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            } else {
                                ImageLoader.loadImage(mBinding.viewLeftLogo, R.drawable.img_default_avatar);
                                mBinding.tvLeftName.setText("");
                            }
                            if (right != null && right.size() > 0) {
                                LiveMatchDetailBean.RightBean rightBean = right.get(0);
                                mBinding.tvRightName.setText("" + rightBean.getName());
                                ImageLoader.loadImage(mBinding.viewRightOval, rightBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            } else {
                                ImageLoader.loadImage(mBinding.viewRightOval, R.drawable.img_default_avatar);
                                mBinding.tvRightName.setText("");
                            }
                        } else if (left != null && left.size() == 2) {    //双打
                            mBinding.llOne.setVisibility(View.GONE);
                            mBinding.llTwo.setVisibility(View.VISIBLE);
                            try {
                                if (left != null && left.size() >= 2) {
                                    LiveMatchDetailBean.LeftBean leftBean = left.get(0);
                                    LiveMatchDetailBean.LeftBean leftBean1 = left.get(1);
                                    mBinding.tvLeftNameOne.setText("" + leftBean.getName());
                                    mBinding.tvLeftNameTwo.setText("" + leftBean1.getName());
                                    ImageLoader.loadImage(mBinding.ivLeftOneLogo, leftBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivLeftTwoLogo, leftBean1.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                } else {
                                    mBinding.tvLeftNameOne.setText("");
                                    mBinding.tvLeftNameTwo.setText("");
                                    ImageLoader.loadImage(mBinding.ivLeftOneLogo, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivLeftTwoLogo, R.drawable.img_default_avatar);

                                }
                                if (right != null && right.size() >= 2) {
                                    LiveMatchDetailBean.RightBean rightBean = right.get(0);
                                    LiveMatchDetailBean.RightBean rightBean1 = right.get(1);
                                    mBinding.tvRightNameOne.setText("" + rightBean.getName());
                                    mBinding.tvRightNameTwo.setText("" + rightBean1.getName());
                                    ImageLoader.loadImage(mBinding.ivRightLogoOne, rightBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivRightLogoTwo, rightBean1.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                } else {
                                    mBinding.tvRightNameOne.setText("");
                                    mBinding.tvRightNameTwo.setText("");
                                    ImageLoader.loadImage(mBinding.ivRightLogoOne, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivRightLogoTwo, R.drawable.img_default_avatar);
                                }
                            } catch (Exception e) {

                            }

                        } else {     //不可能存在的情况
                            mBinding.llOne.setVisibility(View.GONE);
                            mBinding.llTwo.setVisibility(View.GONE);
                        }
                        int matchMethod = data.getMatchMethod();

                        mBinding.tvMoney.setText("¥" + CommonUtilis.getTwoDecimal(String.valueOf(data.getMoney())));
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.liveMatchAcceptLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {


                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(DetailOfOfferActivity.this)
                            .setMessage("比赛时间：" + data.getGameTime() + "\n"
                                    + "请按规定准时进行直播")
                            .setTvTitle("接取成功")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                } else if (responseBean.getErrorCode().equals("300000-100001")) {
                    new MessageDialogBuilder(DetailOfOfferActivity.this)
                            .setMessage("")
                            .setTvTitle("您还未拥有接单资格，是否申请\n" +
                                    "成为接单人员？\n")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("去申请")
                            .setSureListener(v1 ->
                                    goAnother()
                            )
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    private void goAnother() {
        goActivity(ApplyLiveQualificationActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                String gameTime = data.getGameTime();
                int tableNum = data.getTableNum();

                new MessageDialogBuilder(DetailOfOfferActivity.this)
                        .setMessage("比赛时间：" + gameTime + "\n"
                                + "比赛球台：" + tableNum + "号台")
                        .setTvTitle("是否接取悬赏?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int requestId = data.getRequestId();
                                mViewModel.liveMatchAccept("" + requestId);
                            }
                        })
                        .show();


                break;

        }
    }


}