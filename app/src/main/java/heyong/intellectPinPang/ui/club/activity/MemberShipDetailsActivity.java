package heyong.intellectPinPang.ui.club.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubMemberBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoBean;
import heyong.intellectPinPang.databinding.ActivityMemberShipDetailsBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;


/**
 * 运动员详情
 */
public class MemberShipDetailsActivity extends BaseActivity<ActivityMemberShipDetailsBinding, ClubViewModel> implements View.OnClickListener {
    CommonPopupWindow mMovieTicketWindow;
    public static final String TYPES = "types";
    public static final String USER_ID = "user_id";
    public static final String SPORTS_USER_ID = "sports_user_id";
    public static final String IDENTIFY = "identify";
    private String userId = "";
    boolean inCharge;
    int memberType = 0;
    private String sportsUserId = "";
    private int identify = 0;//1 是运动员详情  2 是俱乐部详情

    public static void goActivity(Context context, String userId, int identify) {
        Intent intent = new Intent(context, MemberShipDetailsActivity.class);
        intent.putExtra(USER_ID, userId);
        intent.putExtra(IDENTIFY, identify);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_member_ship_details;
    }

    /*incharge  先判断是否为负责人   如果是负责人  如果不是负责人   */
//    memberType	int	用户类型   1 负责人 2 教练员  3普通人
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        userId = getIntent().getStringExtra(USER_ID);
        identify = getIntent().getIntExtra(IDENTIFY, 0);
        if (identify == 1) {
            //运动员详情
            mViewModel.getXlUserInfo(userId);
        } else {
            //俱乐部详情
            mViewModel.getXlClubMember(userId);
        }


        mViewModel.updateMemberInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if(responseBean.getErrorCode().equals("200"))
                {
                    mMovieTicketWindow.dismiss();
                    toast("修改成功");
                    mViewModel.getXlClubMember(userId);
                }else
                {
                    toast("" + responseBean.getMessage());
                }


            }
        });
        mViewModel.deleteXlClubMemberListLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if(responseBean.getErrorCode().equals("200")){
                    if (mMovieTicketWindow != null) {
                        mMovieTicketWindow.dismiss();
                        toast("移除成功");
                        finish();
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.getXlUserInfoLiveData.observe(this, new Observer<ResponseBean<XlUserInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<XlUserInfoBean> xlUserInfoBeanResponseBean) {
                if (xlUserInfoBeanResponseBean.getErrorCode().equals("200")) {
                    XlUserInfoBean data = xlUserInfoBeanResponseBean.getData();
                    if (data != null) {
                        ImageLoader.loadImage(mBinding.ivLogo, data.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        mBinding.tvNameAge.setText("" + data.getName() + "【" + data.getAge()+"岁" + "】");
                        mBinding.tvAddress.setText("" + data.getAddress());
                        mBinding.tvPersonalClub.setText("" + data.getOwnSign());
                        mBinding.tvCompetitionAll.setText("" + data.getTotalCount());
                        mBinding.tvWinCount.setText("" + data.getWinCount());
                        mBinding.tvLoseCount.setText("" + data.getLoseCount());
                        sportsUserId = "" + data.getId();
                        if (data.getWinningProbability().equals(".00%")) {
                            mBinding.tvWinnerProbablity.setText("0.00%");
                        } else {
                            mBinding.tvWinnerProbablity.setText("" + data.getWinningProbability());
                        }
                        try {
                            int i = Integer.parseInt(data.getClapHand());
                            if (i == 1) {
                                /*zuo*/
                                mBinding.tvChipaishou.setText("左手");
                            } else {
                                mBinding.tvChipaishou.setText("右手");
                            }
                            int sex = Integer.parseInt(data.getSex());
                            if (sex == 1) {
                                ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_man_blue);
                            } else {
                                ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_woman);
                            }
                        }catch (Exception e)
                        {
                            mBinding.tvChipaishou.setText("左手");
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_man_blue);
                        }

                        /*不是负责人*/
                        mBinding.topBar.setCenterText("运动员详情");
                        mBinding.llEditMember.setVisibility(View.GONE);
                        mBinding.tvIdentify.setVisibility(View.GONE);
                    }
                } else {
                    toast("" + xlUserInfoBeanResponseBean.getMessage());
                }


            }
        });
        mViewModel.getXlClubMemberLiveData.observe(this, new Observer<ResponseBean<XlClubMemberBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubMemberBean> responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        XlClubMemberBean data = responseBean.getData();
                    if (data != null) {
                        inCharge = data.isInCharge();
                        ImageLoader.loadImage(mBinding.ivLogo, data.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        mBinding.tvNameAge.setText("" + data.getName() + "【" + data.getAge() + "】");
                        mBinding.tvAddress.setText("" + data.getAddress());
                        mBinding.tvPersonalClub.setText("" + data.getOwnSign());
                        mBinding.tvCompetitionAll.setText("" + data.getTotalCount());
                        mBinding.tvWinCount.setText("" + data.getWinCount());
                        mBinding.tvLoseCount.setText("" + data.getLoseCount());
                        sportsUserId = "" + data.getUserId();
                        if (data.getWinningProbability().equals(".00%")) {
                            mBinding.tvWinnerProbablity.setText("0.00%");
                        } else {
                            mBinding.tvWinnerProbablity.setText("" + data.getWinningProbability());
                        }
                        int i = Integer.parseInt(data.getClapHand());
                        if (i == 1) {
                            /*zuo*/
                            mBinding.tvChipaishou.setText("左手");
                        } else {
                            mBinding.tvChipaishou.setText("右手");
                        }

                        int sex = data.getSex();
                        if (sex == 1) {
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_man_blue);
                        } else {
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_woman);
                        }
                        memberType = Integer.parseInt(data.getMemberType());
                        Log.e(TAG, "onChanged: memberType==="+memberType );
                        if (inCharge) {
                            /*是负责人  显示会员详情*/
                            if (memberType == 1) {
                                //负责人
                                /*是自己的*/
                                mBinding.topBar.setCenterText("会员详情");
                                mBinding.llEditMember.setVisibility(View.GONE);
                            } else {
                                mBinding.topBar.setCenterText("会员详情");
                                mBinding.llEditMember.setVisibility(View.VISIBLE);
                            }

                        } else {
                            /*不是负责人*/
                            mBinding.topBar.setCenterText("运动员详情");
                            mBinding.llEditMember.setVisibility(View.GONE);
                        }

                        if (memberType == 1) {
                            mBinding.tvIdentify.setBackgroundResource(R.drawable.shape_yellow_identify_solid_corners);
                            mBinding.tvIdentify.setText("负责人");
                            mBinding.tvIdentify.setTextColor(Color.parseColor("#FDD363"));
                        } else if (memberType == 2) {
                            mBinding.tvIdentify.setBackgroundResource(R.drawable.shape_blue_identify_solid_corners);
                            mBinding.tvIdentify.setText("教练员");
                            mBinding.tvIdentify.setTextColor(Color.parseColor("#4795ED"));
                        } else if (memberType == 3) {
                            mBinding.tvIdentify.setBackgroundResource(R.drawable.shape_gray_identify_solid_corners);
                            mBinding.tvIdentify.setText("普通会员");
                            mBinding.tvIdentify.setTextColor(Color.parseColor("#666666"));
                        }
                    }
                } else {

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recent_games_of_athletes://运动员近期比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent(MemberShipDetailsActivity.this, RecentGamesAthletesActivity.class);
                intent.putExtra(RecentGamesAthletesActivity.SPORTS_ID, "" + sportsUserId);
                startActivity(intent);
                break;
            case R.id.ll_edit_member:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                switch (memberType) {
                    case 1://负责人

                        break;
                    case 2://教练员
                        if (mMovieTicketWindow != null && mMovieTicketWindow.isShowing()) {
                        } else {
                            showPopCity(R.layout.pop_edit_number);
                        }
                        break;
                    case 3://普通人
                        if (mMovieTicketWindow != null && mMovieTicketWindow.isShowing()) {
                        } else {
                            showPopCity(R.layout.pop_add_number);
                        }
                        break;
                }

                break;
        }
    }

    public void showPopCity(int viewId) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(MemberShipDetailsActivity.this)
                .setView(viewId)
                .setBackGroundLevel(0.5f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        TextView tvEditMember = view.findViewById(R.id.tv_album);
                        TextView tvTakeOut = view.findViewById(R.id.tv_take);
                        TextView tvCancel = view.findViewById(R.id.tv_cancel);
                        tvEditMember.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                /*设为教练员身份  取消教练员身份*/
                                if (memberType == 2) {
                                    /*已经是教练员   调用取消教练员身份*/
                                    mViewModel.updateMemberInfo(userId, "3");
                                } else if (memberType == 3) {
                                    /*已经是普通人*/
                                    mViewModel.updateMemberInfo(userId, "2");
                                }
                            }
                        });
                        /*移除俱乐部*/
                        tvTakeOut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                mViewModel.deleteXlClubMember(userId);
                            }
                        });
                        /*取消*/
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                mMovieTicketWindow.dismiss();
                            }
                        });
                    }
                })
                .setOutsideTouchable(false)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .create();
        View view2 = View.inflate(MemberShipDetailsActivity.this, R.layout.activity_member_ship_details, null);
        mMovieTicketWindow.showAtLocation(view2, Gravity.CENTER, 0, 0);
    }
}