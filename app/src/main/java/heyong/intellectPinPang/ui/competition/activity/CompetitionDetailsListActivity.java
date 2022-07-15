package heyong.intellectPinPang.ui.competition.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CompetitionDetailListAdapter;
import heyong.intellectPinPang.databinding.ActivityCompetitionDetailsListBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MyDateUtils;

/**
 * 比赛详情-我相关的比赛列表  暂未用到  和游客共用一个
 */
public class CompetitionDetailsListActivity extends BaseActivity<ActivityCompetitionDetailsListBinding, BaseViewModel> implements View.OnClickListener {
    CompetitionDetailListAdapter competitionDetailListAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_competition_details_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        competitionDetailListAdapter = new CompetitionDetailListAdapter();
        mBinding.rvCompetitionDetailList.setAdapter(competitionDetailListAdapter);
        mBinding.rvCompetitionDetailList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        competitionDetailListAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_time_select://选择时间
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimePickerView pvTime = new TimePickerView.Builder(CompetitionDetailsListActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, View v) {
                        String time = getTimeMinute(dateMax);
                        mBinding.tvTime.setText(""+time);
                        String week = MyDateUtils.dataWeek(dateMax);
                        mBinding.tvWeek.setText(""+week);
//                        mBinding.tvDeadlineForRegistrationTime.setText("" + time);
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
                        .setLoop(false)
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;

        }
    }

    public static String getTimeMinute(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}