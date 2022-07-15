package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityEvaluationRewardBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;

/**
 * 评价悬赏
 */
public class EvaluationRewardActivity extends BaseActivity<ActivityEvaluationRewardBinding, LiveViewModel> implements View.OnClickListener {
    public static final String REQUEST_ID = "request_id";
    private String strRequestId = "";
    private int star = 1;
    public static final String MATCH_TITLE = "match_title";
    public static final String GROUP_TITLE = "group_title";
    public static final String PLAYER_SHOW = "player_show";

    private String strMatchTitle = "";
    private String strGroupTitle = "";
    private String strPlayerShow = "";

    public static void goEvaluationRewardActivity(Context context, String matchTitle, String groupTitle, String playerShow,String requestId) {
        Intent intent = new Intent(context, EvaluationRewardActivity.class);
        intent.putExtra(MATCH_TITLE, matchTitle);
        intent.putExtra(GROUP_TITLE, groupTitle);
        intent.putExtra(PLAYER_SHOW, playerShow);
        intent.putExtra(REQUEST_ID, requestId);
        context.startActivity(intent);

    }
    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_comment};
        return ids;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_evaluation_reward;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strMatchTitle = getIntent().getStringExtra(MATCH_TITLE);
        strGroupTitle = getIntent().getStringExtra(GROUP_TITLE);
        strPlayerShow = getIntent().getStringExtra(PLAYER_SHOW);

        strRequestId = getIntent().getStringExtra(REQUEST_ID);
        mBinding.tvMatchTitle.setText("" + strMatchTitle);
        mBinding.tvGroupTitle.setText("" + strGroupTitle);
        mBinding.tvPlayerShow.setText("" + strPlayerShow);

        ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
        ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_starts_unselect);
        ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_starts_unselect);
        ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
        ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);

        mViewModel.liveUserAppraiseLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("评价成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_stars_one:
                star = 1;
                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);

                break;
            case R.id.iv_stars_two:
                star = 2;
                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);

                break;
            case R.id.iv_stars_three:
                star = 3;
                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);

                break;
            case R.id.iv_stars_four:
                star = 4;
                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_starts_unselect);
                break;
            case R.id.iv_stars_five:
                star = 5;
                ImageLoader.loadImage(mBinding.ivStarsOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsThree, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsFour, R.drawable.img_stars_select);
                ImageLoader.loadImage(mBinding.ivStarsFive, R.drawable.img_stars_select);

                break;
            case R.id.tv_commit:
                String strComment = mBinding.etComment.getText().toString();
                if (TextUtils.isEmpty(strComment)) {
                    toast("请输入评论");
                    return;
                }
                showLoading();
                mViewModel.liveUserAppraise("" + strRequestId, "" + star, "" + strComment);
                break;
        }
    }
}