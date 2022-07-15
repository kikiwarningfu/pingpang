package heyong.intellectPinPang.ui.mine.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

//import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.live.activity.BeginStartLiveActivity;
import heyong.intellectPinPang.live.activity.WatchLiveActivity;
import heyong.intellectPinPang.soundnet.LivePrepareActivity;
import heyong.intellectPinPang.soundnet.SingleHostLiveActivity;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.handong.framework.utils.SystemUtil;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyBaseInfoBean;
import heyong.intellectPinPang.databinding.FragmentNewMineBinding;
//import heyong.intellectPinPang.live.activity.BeginLiveActivity;
import heyong.intellectPinPang.ui.club.activity.MyClubActivity;
import heyong.intellectPinPang.ui.coachdisplay.RefereeDisplayActivity;
import heyong.intellectPinPang.ui.competition.activity.MyEventActivity;
import heyong.intellectPinPang.ui.competition.activity.PersonalInformationActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.ui.mine.activity.ContactUsActivity;
import heyong.intellectPinPang.ui.mine.activity.MyGradeActivity;
import heyong.intellectPinPang.ui.mine.activity.MyMessageActivity;
import heyong.intellectPinPang.ui.mine.activity.MyRegistrationActivity;
import heyong.intellectPinPang.ui.mine.activity.RealNameImageActivity;
import heyong.intellectPinPang.ui.mine.activity.RealNameSuccessActivity;
import heyong.intellectPinPang.ui.mine.activity.RefereeDetailAcitivty;
import heyong.intellectPinPang.ui.mine.activity.SettingActivity;
import heyong.intellectPinPang.ui.mine.activity.live.MyLiveActivity;
import heyong.intellectPinPang.ui.mine.activity.live.MyWalletActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.lzy.imagepicker.util.StatusBarUtil;

/**
 * @Name：weiying
 * @Description：个人中心
 * @Author：whf 修改人：whf
 * 修改备注：
 */

public class MineFragment extends BaseFragment<FragmentNewMineBinding, MineViewModel> implements View.OnClickListener {
    public static final String TAG = MineFragment.class.getSimpleName();
    private String identificationStatus = "";
    private String role = "";
    private int changeNum = 0;

    public MineFragment() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (viewModel != null) {
                viewModel.myBaseInfo();
            }

        } else {
        }
        int version = SystemUtil.getVersion();
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(getActivity());
            StatusBarUtil.transparencyBar(getActivity()); //设置状态栏全透明
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.myBaseInfo();
        }
    }

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_new_mine;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
//        XLog.e("okhttp message");
        binding.setListener(this);
        //白底黑字
//        ToastUtils.showToast(getActivity(), "版本2");
        int version = SystemUtil.getVersion();
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(getActivity());
            StatusBarUtil.transparencyBar(getActivity()); //设置状态栏全透明
        }
        if (viewModel != null) {
            viewModel.myBaseInfo();
        }
        viewModel.myBaseInfoLiveData.observe(this, new Observer<ResponseBean<MyBaseInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MyBaseInfoBean> myBaseInfoBeanResponseBean) {
                if (myBaseInfoBeanResponseBean.getErrorCode().equals("200")) {

                    MyBaseInfoBean data = myBaseInfoBeanResponseBean.getData();
                    if (data != null) {
                        String backImgUrl = data.getBackImgUrl();
//                        ImageLoader.loadImage(binding.ivBgUrl, backImgUrl);
                        ImageLoader.loadImage(binding.ivMyLogo, "" + data.getHeadImg(), R.drawable.img_default_avatar
                                , R.drawable.img_default_avatar);
                        binding.tvNickName.setText("" + data.getNickName());
                        AccountHelper.setTelephone("" + data.getAccount());
                        if (data.getSex().equals("1")) {
                            ImageLoader.loadImage(binding.ivSex, R.drawable.img_sex_man_blue);
                        } else {
                            ImageLoader.loadImage(binding.ivSex, R.drawable.img_sex_woman);
                        }
                        role = data.getRole();
                        if (TextUtils.isEmpty(role)) {
                            binding.tvRole.setVisibility(View.GONE);
                        } else {
                            binding.tvRole.setText("" + role);
                            binding.tvRole.setVisibility(View.VISIBLE);
                        }
                        if (data.getMessageCount() == 0) {
                            binding.tvCount.setVisibility(View.GONE);
                        } else {
                            binding.tvCount.setVisibility(View.VISIBLE);
                            binding.tvCount.setText("" + data.getMessageCount());
                        }
                        identificationStatus = data.getIdentificationStatus();

                    }
                }
            }
        });
    }


    //    {"coed":"1","description":"哈哈哈","downloadUrl":"1","usable":"0"}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_wallet://我的钱包

//                goActivity(MyWalletActivity.class);
                toast("暂未开放");
                break;
            case R.id.ll_mine_live:

                startActivity(new Intent(getActivity(), MyLiveActivity.class));

                break;
            case R.id.tv_update_personal_data://个人信息
//                int a = 0;
//                int b = 5;
//                int c = b / a;
//                binding.tvRole.setText("字符是：" + c);
//                binding.tvRole.setText("字符是：" + 2);
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(PersonalInformationActivity.class);
//                  BeginLiveActivity.Companion.startActivity(getActivity(),"619492060864861360");
//                Intent intent = new Intent(getActivity(), LivePrepareActivity.class);
//                intent.putExtra(Global.Constants.TAB_KEY, 1);
//                intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, true);
//                intent.putExtra(Global.Constants.KEY_CREATE_ROOM, true);
//                intent.putExtra(Global.Constants.KEY_MATCH_ID, "619492060864861360");
////                intent.putExtra(Global.Constants.KEY_ROOM_OWNER_ID,
////                       "347658454774591488");
//                startActivity(intent);
//                goActivity(LivePrepareActivity.class);
                break;
            case R.id.ll_my_event://我的赛事
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> game = new HashMap<String, Object>();
                game.put("minePage_match", "我的赛事");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(getActivity(), "我的赛事", game);
//                TCAgent.onEvent(getActivity(), "minePage_match");
                goActivity(MyEventActivity.class);
//                Intent inten2t = new Intent(getActivity(), SingleHostLiveActivity.class);
//                inten2t.putExtra(Global.Constants.TAB_KEY, 1);
//                inten2t.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, false);
//                inten2t.putExtra(Global.Constants.KEY_CREATE_ROOM, false);
//                inten2t.putExtra(Global.Constants.KEY_MATCH_ID, "618846888955106480");
//                startActivity(inten2t);

                break;
            case R.id.ll_mine_message://我的消息
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
                goActivity(MyMessageActivity.class);
//                goActivity(WatchLiveActivity.class);
                break;

            case R.id.ll_my_grade://我的成绩
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamegrade = new HashMap<String, Object>();
                gamegrade.put("minePage_grade", "我的成绩");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(getActivity(), "我的成绩", gamegrade);
//                TCAgent.onEvent(getActivity(), "minePage_grade");

                goActivity(MyGradeActivity.class);
                break;

            case R.id.ll_real_name_authentication://实名认证
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (identificationStatus.equals("1")) {
                    //未实名
                    goActivity(RealNameImageActivity.class);
                } else {
                    goActivity(RealNameSuccessActivity.class);
                }
                break;
            case R.id.ll_my_club://我的俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gameclub = new HashMap<String, Object>();
                gameclub.put("minePage_clubList", "我的俱乐部");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(getActivity(), "我的俱乐部", gameclub);
//                TCAgent.onEvent(getActivity(), "minePage_clubList");
                goActivity(MyClubActivity.class);

                break;

            case R.id.ll_my_refree://裁判员   3  是第二个    1 是第二个  2 是第三个
//                viewModel.judgeMyInfo();
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(RefereeDetailAcitivty.class);
                break;
            case R.id.ll_my_setting://设置
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(SettingActivity.class);

                break;

            case R.id.ll_my_registration://我的报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamesignUp = new HashMap<String, Object>();
                gamesignUp.put("minePage_signUp", "我的报名");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(getActivity(), "我的报名", gamesignUp);
//                TCAgent.onEvent(getActivity(), "minePage_signUp");
                goActivity(MyRegistrationActivity.class);

                break;
            case R.id.ll_my_connect_us://联系我们
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(ContactUsActivity.class);

                break;
            case R.id.ll_live://我的直播
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(MyLiveActivity.class);

                break;
            case R.id.ll_yanshi://裁判员演示
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(RefereeDisplayActivity.class);

                break;
            case R.id.ll_equip_set://器材设置
                toast("暂未开放");

                break;
            case R.id.ll_point_management://积分管理

                toast("暂未开放");
                break;
        }
    }
}
