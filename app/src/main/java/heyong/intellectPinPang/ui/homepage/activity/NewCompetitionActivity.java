package heyong.intellectPinPang.ui.homepage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.TimeSelectView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.gsonbean.CreateEventsBean;
import heyong.intellectPinPang.databinding.ActivityNewCompetitionBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

import static heyong.intellectPinPang.ui.club.activity.CreateClubActivity.getTime;

/**
 * 新建比赛项目   专业 级别   没有混合这个选项  选完混双 其他都不显示
 */
public class NewCompetitionActivity extends BaseActivity<ActivityNewCompetitionBinding, BaseViewModel> implements View.OnClickListener {
    public static final String POSITION = "position";
    private int position;
    public static final String COMPETITION_LEVEL = "competitionLevel";
    public static final String GROUP_NAME = "group_name";
    private String strGroupName;
    private int competitionLevel = -1;//0是业余级别  1  是专业级别
    private int sexType = -1;//0是男  1是女  2 是混合   3 是混双
    private int competitionItemType = -1;//0是团体  1是单打  2是团队
    public static final String TAG = NewCompetitionActivity.class.getSimpleName();
    public static final String SHUANGDA_CANSELECT_MAN = "shuangda_can_select_man";
    public static final String SHUANGDA_CANSELECT_WOMAN = "shuangda_can_select_woman";
    public static final String HUNSHUANG_CLICK = "hunashuang_click";
    private String shuangdaCanSelectMan = "";
    private String shuangdaCanSelectWoMan = "";
    private String manMinAge = "";
    private String manMaxAge = "";
    private String womanMinAge = "";
    private String womanMaxAge = "";
    private String hunShuangClick = "";
    List<CreateEventsBean.ProjectListBean> data;

    private boolean isShowAge = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_new_competition;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        position = getIntent().getIntExtra(POSITION, 0);
        shuangdaCanSelectMan = getIntent().getStringExtra(SHUANGDA_CANSELECT_MAN);
        shuangdaCanSelectWoMan = getIntent().getStringExtra(SHUANGDA_CANSELECT_WOMAN);
        strGroupName = getIntent().getStringExtra(GROUP_NAME);
        hunShuangClick = getIntent().getStringExtra(HUNSHUANG_CLICK);
        data = (List<CreateEventsBean.ProjectListBean>) getIntent().getSerializableExtra("data");

        competitionLevel = getIntent().getIntExtra(COMPETITION_LEVEL, 0);
        mBinding.tvGroupName.setText("" + strGroupName);
        /**/
        if (competitionLevel == 1) {
            /*专业级别*/
            mBinding.llHunhe.setEnabled(false);
            ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.img_locked_circle);
            mBinding.tvHunhe.setTextColor(Color.parseColor("#CCCCCC"));
        } else {
            /*业余级别*/
            mBinding.llHunhe.setEnabled(true);
            ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_unselected);
            mBinding.tvHunhe.setTextColor(Color.parseColor("#333333"));
        }
        isShowAge = false;
        mBinding.rlTeamType.setVisibility(View.GONE);
        mBinding.llNanWomanTime.setVisibility(View.GONE);
        mBinding.llNoLimitAge.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_no_limit_age:
                if (isShowAge) {
                    //true  展示
                    isShowAge = false;
                    ImageLoader.loadImage(mBinding.ivNoLimitAge, R.drawable.icon_login_unselected);
                    mBinding.tvNoLimitAge.setTextColor(Color.parseColor("#FF333333"));
                    mBinding.rlTeamType.setVisibility(View.VISIBLE);
                    mBinding.llNanWomanTime.setVisibility(View.VISIBLE);

                    if (sexType == 0) {//男
                        mBinding.rlTeamType.setVisibility(View.VISIBLE);
                        mBinding.llNanWomanTime.setVisibility(View.GONE);
                    } else if (sexType == 1) {//女
                        mBinding.rlTeamType.setVisibility(View.VISIBLE);
                        mBinding.llNanWomanTime.setVisibility(View.GONE);
                    } else {
                        mBinding.rlTeamType.setVisibility(View.GONE);
                        mBinding.llNanWomanTime.setVisibility(View.VISIBLE);
                    }

                } else {
                    isShowAge = true;
                    ImageLoader.loadImage(mBinding.ivNoLimitAge, R.drawable.icon_login_selected);
                    mBinding.tvNoLimitAge.setTextColor(Color.parseColor("#FF4795ED"));
                    mBinding.rlTeamType.setVisibility(View.GONE);
                    mBinding.llNanWomanTime.setVisibility(View.GONE);
                }


                break;
            case R.id.rl_team_type:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimeSelectView pvTime = new TimeSelectView.Builder(NewCompetitionActivity.this, new TimeSelectView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        String timeMax = getTime(dateMin);
                        String timeMin = getTime(dateMax);
                        mBinding.tvTeamType.setText("" + timeMin + "  至  " + timeMax);
                        mBinding.tvTeamType.setTextColor(Color.parseColor("#333333"));
                        if (sexType == 0) {
                            manMinAge = getTime(dateMax);
                            manMaxAge = getTime(dateMin);
                        } else if (sexType == 1) {
                            womanMinAge = getTime(dateMax);
                            womanMaxAge = getTime(dateMin);
                        }
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        //                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#333333"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();

                break;
            case R.id.ll_woman_time_select:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimeSelectView pvTimeSelectWoman = new TimeSelectView.Builder(NewCompetitionActivity.this, new TimeSelectView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        String timeMax = getTime(dateMin);
                        String timeMin = getTime(dateMax);
                        mBinding.tvWomanSelect.setText("" + timeMin + "  至  " + timeMax);
                        mBinding.tvWomanSelect.setTextColor(Color.parseColor("#333333"));

                        womanMinAge = getTime(dateMax);
                        womanMaxAge = getTime(dateMin);

                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        //                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#666666"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTimeSelectWoman.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTimeSelectWoman.show();

                break;
            case R.id.ll_man_time_select:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimeSelectView pvTimeSelectEndMan = new TimeSelectView.Builder(NewCompetitionActivity.this, new TimeSelectView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        String timeMax = getTime(dateMin);
                        String timeMin = getTime(dateMax);
                        mBinding.tvManTime.setText("" + timeMin + "  至  " + timeMax);
                        //                        private String manMinAge = "";
                        //                        private String manMaxAge = "";
                        mBinding.tvManTime.setTextColor(Color.parseColor("#333333"));
                        manMaxAge = getTime(dateMin);
                        manMinAge = getTime(dateMax);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        //                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#666666"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTimeSelectEndMan.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTimeSelectEndMan.show();
                break;
            case R.id.ll_man://男
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                sexType = 0;
                setAllClick();
                ImageLoader.loadImage(mBinding.ivMan, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivWoman, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivHunshuang, R.drawable.icon_login_unselected);
                if (competitionLevel == 0) {
                    ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_unselected);
                }
                mBinding.rlTeamType.setVisibility(View.VISIBLE);
                mBinding.llNanWomanTime.setVisibility(View.GONE);
                mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                isShowAge = false;
                break;
            case R.id.ll_woman://女
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                sexType = 1;
                setAllClick();
                ImageLoader.loadImage(mBinding.ivMan, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivWoman, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivHunshuang, R.drawable.icon_login_unselected);
                if (competitionLevel == 0) {
                    ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_unselected);
                }
                mBinding.rlTeamType.setVisibility(View.VISIBLE);
                mBinding.llNanWomanTime.setVisibility(View.GONE);
                mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                isShowAge = false;
                break;
            case R.id.ll_hunhe://混合
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                sexType = 2;
                setAllClick();
                if (competitionLevel == 0) {//业余 显示
                    ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_selected);
                }
                mBinding.rlTeamType.setVisibility(View.GONE);
                mBinding.llNanWomanTime.setVisibility(View.VISIBLE);
                mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                isShowAge = false;
                break;
            case R.id.ll_hunshuang://混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (hunShuangClick.equals("true")) {
                    if (competitionLevel == 1) {//专业级
                        setAllClick();
                        //业余级别
                        sexType = 3;
                        ImageLoader.loadImage(mBinding.ivMan, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivWoman, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivHunshuang, R.drawable.icon_login_selected);
                        if (competitionLevel == 0) {
                            ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_unselected);
                        }
                        mBinding.rlTeamType.setVisibility(View.GONE);
                        mBinding.llNanWomanTime.setVisibility(View.VISIBLE);
                        mBinding.llNoLimitAge.setVisibility(View.VISIBLE);

                        isShowAge = false;
                        mBinding.llTuanti.setEnabled(false);
                        mBinding.llDanda.setEnabled(false);
                        mBinding.llShuangda.setEnabled(false);
                        mBinding.tvTuanti.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.img_locked_circle);
                        mBinding.tvDanda.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivDanda, R.drawable.img_locked_circle);
                        mBinding.tvShuangda.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.img_locked_circle);
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = data.get(i).getProjectItemList();
                                for (int j = 0; j < projectItemList.size(); j++) {
                                    CreateEventsBean.ProjectListBean.ProjectItemListBean projectItemListBean = projectItemList.get(j);
                                    if (projectItemListBean.getItemTitle().contains("男团体")) {
                                        manMinAge = projectItemListBean.getManMinAge();
                                        manMaxAge = projectItemListBean.getManMaxAge();
                                    }
                                    if (projectItemListBean.getItemTitle().contains("女团体")) {
                                        womanMinAge = projectItemListBean.getWomanMinAge();
                                        womanMaxAge = projectItemListBean.getWomanMaxAge();
                                    }
                                }
                            }
                        }

                    } else {
                        //业余级别
                        sexType = 3;
                        setAllClick();
                        ImageLoader.loadImage(mBinding.ivMan, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivWoman, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivHunshuang, R.drawable.icon_login_selected);

                        if (competitionLevel == 0) {
                            ImageLoader.loadImage(mBinding.ivHunhe, R.drawable.icon_login_unselected);
                        }
                        mBinding.rlTeamType.setVisibility(View.GONE);
                        mBinding.llNanWomanTime.setVisibility(View.VISIBLE);
                        mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                        isShowAge = false;
                        mBinding.llTuanti.setEnabled(false);
                        mBinding.llDanda.setEnabled(false);
                        mBinding.llShuangda.setEnabled(false);
                        mBinding.tvTuanti.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.img_locked_circle);
                        mBinding.tvDanda.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivDanda, R.drawable.img_locked_circle);
                        mBinding.tvShuangda.setTextColor(Color.parseColor("#CCCCCC"));
                        ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.img_locked_circle);
                    }

                } else {
                    toast("请先创建男子和女子团体 然后才能选择混双");
                }


                break;
            case R.id.ll_tuanti://团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                competitionItemType = 0;
                ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_unselected);

                break;
            case R.id.ll_danda://单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                competitionItemType = 1;
                ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_unselected);


                break;
            case R.id.ll_shuangda://双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ////0是业余级别  1  是专业级别
                if (competitionLevel == 1) {
                    if (sexType == 0) {
                        //男
                        competitionItemType = 2;
                        ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_selected);
                        mBinding.rlTeamType.setVisibility(View.VISIBLE);
                        mBinding.llNanWomanTime.setVisibility(View.GONE);
                        mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                        isShowAge = false;
                    } else if (sexType == 1) {
                        //女
                        competitionItemType = 2;
                        ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_selected);
                        mBinding.rlTeamType.setVisibility(View.VISIBLE);
                        mBinding.llNanWomanTime.setVisibility(View.GONE);
                        mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                        isShowAge = false;
                    } else {
                        competitionItemType = 2;
                        ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_unselected);
                        ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_selected);
                        mBinding.rlTeamType.setVisibility(View.GONE);
                        mBinding.llNanWomanTime.setVisibility(View.VISIBLE);
                        mBinding.llNoLimitAge.setVisibility(View.VISIBLE);
                        isShowAge = false;
                    }

                } else {
                    competitionItemType = 2;
                    ImageLoader.loadImage(mBinding.ivTuanti, R.drawable.icon_login_unselected);
                    ImageLoader.loadImage(mBinding.ivDanda, R.drawable.icon_login_unselected);
                    ImageLoader.loadImage(mBinding.ivShuangda, R.drawable.icon_login_selected);
                }


                break;
            case R.id.ll_add_project:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String appendTitle = "";
                if (sexType == -1) {
                    toast("请选择性别");
                    return;
                }
                if (sexType != 3) {
                    if (competitionItemType == -1) {
                        toast("请选择比赛项目");
                        return;
                    }
                }
                if (competitionItemType == 2) {
                    if (sexType == 0) {
                        /*判断 是否能够男子双打*/
                        if (shuangdaCanSelectMan.equals("true")) {

                        } else {
                            /*false  不能进行双打*/
                            new MessageDialogBuilder(NewCompetitionActivity.this)
                                    .setTvTitle("专业级比赛中,要求参加双打的运动员\n" +
                                            "需要在报名团体赛的运动员中选择，\n" +
                                            "所以新建双打比赛前请先新建团体比赛\n" +
                                            "                            ")
                                    .setMessage("")
                                    .setTvSure("确定")
                                    .setBtnCancleHint(true)

                                    .show();
                            return;
                        }
                    }
                    if (sexType == 1) {
                        if (shuangdaCanSelectWoMan.equals("true")) {

                        } else {
                            /*false  不能进行双打*/
                            new MessageDialogBuilder(NewCompetitionActivity.this)
                                    .setTvTitle("专业级比赛中,要求参加双打的运动员\n" +
                                            "需要在报名团体赛的运动员中选择，\n" +
                                            "所以新建双打比赛前请先新建团体比赛\n" +
                                            "                            ")
                                    .setMessage("")
                                    .setTvSure("确定")
                                    .setBtnCancleHint(true)

                                    .show();
                            return;
                        }
                    }

                }
                CreateEventsBean.ProjectListBean.ProjectItemListBean createCompetitionCategoriesChildBean = new CreateEventsBean.ProjectListBean.ProjectItemListBean();
                if (competitionLevel == 0) {
                    //0是业余  1是专业的
                    switch (sexType)////0是男  1是女  2 是混合   3 是混双
                    {
                        case 0:
                            appendTitle = "男";
                            break;
                        case 1:
                            appendTitle = "女";
                            break;
                        case 2:
                            appendTitle = "混合";
                            break;
                        case 3:
                            appendTitle = "混双";
                            break;
                    }
                } else {
                    /*专业没有混合的选项*/
                    switch (sexType)////0是男  1是女  2 是混合   3 是混双
                    {
                        case 0:
                            appendTitle = "男";
                            break;
                        case 1:
                            appendTitle = "女";
                            break;
                        case 3:
                            appendTitle = "混双";
                            break;
                    }
                }
                if (appendTitle.equals("混双")) {
                    //1团体2单打3双打,4混双
                    createCompetitionCategoriesChildBean.setProjectItem("4");

                } else {
                    //0是团体  1是单打  2是双打
                    if (competitionItemType == 0) {
                        createCompetitionCategoriesChildBean.setProjectItem("1");
                        appendTitle = appendTitle + "团体";
                    } else if (competitionItemType == 1) {
                        createCompetitionCategoriesChildBean.setProjectItem("2");
                        appendTitle = appendTitle + "单打";
                    } else if (competitionItemType == 2) {
                        createCompetitionCategoriesChildBean.setProjectItem("3");
                        appendTitle = appendTitle + "双打";
                    }
                }
                createCompetitionCategoriesChildBean.setItemTitle(appendTitle);
                if (sexType == 0) {

                    if (isShowAge) {
                        //不限年龄
                        manMaxAge = "不限年龄";
                        manMinAge = "不限年龄";
                    } else {
                        if (TextUtils.isEmpty(manMaxAge)) {
                            toast("请选择年龄范围");
                            return;
                        }
                    }
                    createCompetitionCategoriesChildBean.setManMaxAge(manMaxAge);
                    createCompetitionCategoriesChildBean.setManMinAge(manMinAge);
                } else if (sexType == 1) {
                    if (isShowAge) {
                        //不限年龄
                        womanMaxAge = "不限年龄";
                        womanMinAge = "不限年龄";
                    } else {
                        if (TextUtils.isEmpty(womanMaxAge)) {
                            toast("请选择年龄范围");
                            return;
                        }
                    }
                    createCompetitionCategoriesChildBean.setWomanMaxAge(womanMaxAge);
                    createCompetitionCategoriesChildBean.setWomanMinAge(womanMinAge);
                } else {
                    if (isShowAge) {
                        //不限年龄
                        manMaxAge = "不限年龄";
                        manMinAge = "不限年龄";
                        womanMaxAge = "不限年龄";
                        womanMinAge = "不限年龄";
                    } else {
                        if (TextUtils.isEmpty(manMaxAge)) {
                            toast("请选择年龄范围");
                            return;
                        }
                        if (TextUtils.isEmpty(womanMaxAge)) {
                            toast("请选择年龄范围");
                            return;
                        }
                    }
                    createCompetitionCategoriesChildBean.setManMaxAge(manMaxAge);
                    createCompetitionCategoriesChildBean.setManMinAge(manMinAge);
                    createCompetitionCategoriesChildBean.setWomanMaxAge(womanMaxAge);
                    createCompetitionCategoriesChildBean.setWomanMinAge(womanMinAge);
                }
                createCompetitionCategoriesChildBean.setMaxLimit("");
                createCompetitionCategoriesChildBean.setMinLimit("");//
                createCompetitionCategoriesChildBean.setSexType("" + (sexType + 1));//

                Intent intent = new Intent();
                intent.putExtra("bean", createCompetitionCategoriesChildBean);
                intent.putExtra("textData", "" + position);
                setResult(RESULT_OK, intent);
                finish();


                break;
        }
    }

    public void setAllClick() {
        ImageLoader.loadImage(mBinding.ivMan, R.drawable.icon_login_unselected);
        ImageLoader.loadImage(mBinding.ivWoman, R.drawable.icon_login_unselected);
        ImageLoader.loadImage(mBinding.ivHunshuang, R.drawable.icon_login_unselected);

        mBinding.tvMan.setTextColor(Color.parseColor("#333333"));
        mBinding.tvWoman.setTextColor(Color.parseColor("#333333"));
        mBinding.tvHunshuang.setTextColor(Color.parseColor("#333333"));
        mBinding.tvTuanti.setTextColor(Color.parseColor("#333333"));
        mBinding.tvDanda.setTextColor(Color.parseColor("#333333"));
        mBinding.tvShuangda.setTextColor(Color.parseColor("#333333"));


        mBinding.llMan.setEnabled(true);
        mBinding.llWoman.setEnabled(true);
        mBinding.llHunshuang.setEnabled(true);
        mBinding.llTuanti.setEnabled(true);
        mBinding.llDanda.setEnabled(true);
        mBinding.llShuangda.setEnabled(true);
    }
}