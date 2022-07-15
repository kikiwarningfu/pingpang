package heyong.intellectPinPang.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveUserAppraiseBean;
import heyong.intellectPinPang.databinding.ActivityEvaluationRewardDetailBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;

/**
 * 评价详情界面UI
 */
public class EvaluationRewardDetailActivity extends BaseActivity<ActivityEvaluationRewardDetailBinding, LiveViewModel> implements View.OnClickListener {
    public static final String REQUEST_ID = "request_id";
    private String strRequestId = "";
    public static final String MATCH_TITLE = "match_title";
    private String strMatchTitle = "";
    public static final String GROUP_TITLE = "group_title";
    private String strGroupTitle = "";
    public static final String SHOW_PLAYER = "show_player";
    private String strShowPlayer = "";


    public static void goEvaluationRewardActivity(Context context, String RequsetId, String matchTitle, String groupTitle, String showPlayer) {
        Intent intent = new Intent(context, EvaluationRewardDetailActivity.class);
        intent.putExtra(REQUEST_ID, RequsetId);
        intent.putExtra(MATCH_TITLE, matchTitle);
        intent.putExtra(GROUP_TITLE, groupTitle);
        intent.putExtra(SHOW_PLAYER, showPlayer);
        context.startActivity(intent);


    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_evaluation_reward_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strRequestId = getIntent().getStringExtra(REQUEST_ID);
        strMatchTitle = getIntent().getStringExtra(MATCH_TITLE);
        strGroupTitle = getIntent().getStringExtra(GROUP_TITLE);
        strShowPlayer = getIntent().getStringExtra(SHOW_PLAYER);

        mBinding.tvMatchTitle.setText("" + strMatchTitle);
        mBinding.tvGroupTitle.setText("" + strGroupTitle);
        mBinding.tvShowPlayer.setText("" + strShowPlayer);

        mViewModel.liveUserAppraise(strRequestId);

        mViewModel.liveUserAppraiseMyLiveData.observe(this, new Observer<ResponseBean<LiveUserAppraiseBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserAppraiseBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    LiveUserAppraiseBean data = responseBean.getData();
                    if (data != null) {
                        int start = data.getStart();
                        switch (start) {
                            case 1:
                                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);
                                break;
                            case 2:
                                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);
                                break;
                            case 3:
                                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);
                                break;
                            case 4:
                                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);
                                break;
                            case 5:
                                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_stars_select);
                                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_stars_select);
                                break;
                            default:
                                break;

                        }
                        String content = data.getContent();
                        mBinding.tvContent.setText("" + content);
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}