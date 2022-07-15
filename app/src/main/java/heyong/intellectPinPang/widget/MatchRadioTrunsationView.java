package heyong.intellectPinPang.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.MatchWheelTime;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.AddRefereeTableAdapter;
import heyong.intellectPinPang.bean.competition.AddRefereeBean;

import static com.bigkoo.pickerview.TimePickerView.Type.HOURS_MINS;
import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * 比赛调台
 */
public class MatchRadioTrunsationView extends BasePickerView implements View.OnClickListener {
    private List<String> list = new ArrayList<>();
    private int width = 0;
    private int layoutRes;
    private CustomListener customListener;


    MatchWheelTime wheelTime; //自定义控件
    private TextView btnSubmit, btnCancel,tvTableNum; //确定、取消按钮
    private OnTimeSelectListener timeSelectListener;//回调接口
    private int gravity = Gravity.CENTER;//内容显示位置 默认居中

    private String Str_Submit;//确定按钮字符串
    private String Str_Cancel;//取消按钮字符串
    private String Str_Title;//标题字符串

    private int Color_Submit;//确定按钮颜色
    private int Color_Cancel;//取消按钮颜色
    private int Color_Title;//标题颜色

    private int Color_Background_Wheel;//滚轮背景颜色
    private int Color_Background_Title;//标题背景颜色

    private int Size_Submit_Cancel;//确定取消按钮大小
    private int Size_Title;//标题字体大小
    private int Size_Content;//内容字体大小

    private Calendar date;//当前选中时间
    private Calendar startDate;//开始时间
    private Calendar endDate;//终止时间
    private int startYear;//开始年份
    private int endYear;//结尾年份
    private MyDividerItemDecoration mSixDiD;
    private boolean cyclic;//是否循环
    private boolean cancelable;//是否能取消
    private boolean isCenterLabel;//是否只显示中间的label

    private int textColorOut; //分割线以外的文字颜色
    private int textColorCenter; //分割线之间的文字颜色
    private int dividerColor; //分割线的颜色

    // 条目间距倍数 默认1.6
    private float lineSpacingMultiplier = 1.6F;
    private boolean isDialog;//是否是对话框模式
    private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;
    private WheelView.DividerType dividerType;//分隔线类型

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private String selectItem;
    private String myTableNum;

    //构造方法
    public MatchRadioTrunsationView(Builder builder) {
        super(builder.context);
        this.timeSelectListener = builder.timeSelectListener;
        this.gravity = builder.gravity;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;
        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;
        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;
        this.startYear = builder.startYear;
        this.endYear = builder.endYear;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.date = builder.date;
        this.cyclic = builder.cyclic;
        this.isCenterLabel = builder.isCenterLabel;
        this.cancelable = builder.cancelable;
        this.label_year = builder.label_year;
        this.label_month = builder.label_month;
        this.label_day = builder.label_day;
        this.label_hours = builder.label_hours;
        this.label_mins = builder.label_mins;
        this.label_seconds = builder.label_seconds;
        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.width = builder.width;
        this.mSixDiD = builder.mSixDiD;
        this.list = builder.list;
        this.myTableNum = builder.myTableNum;
        initView(builder.context);
    }


    //建造器
    public static class Builder {
        private int layoutRes = R.layout.pop_radio_up_search_trunsation;
        private CustomListener customListener;
        private Context context;
        private OnTimeSelectListener timeSelectListener;

        private int gravity = Gravity.CENTER;//内容显示位置 默认居中

        private String Str_Submit;//确定按钮文字
        private String Str_Cancel;//取消按钮文字
        private String Str_Title;//标题文字

        private int Color_Submit;//确定按钮颜色
        private int Color_Cancel;//取消按钮颜色
        private int Color_Title;//标题颜色

        private int Color_Background_Wheel;//滚轮背景颜色
        private int Color_Background_Title;//标题背景颜色

        private int Size_Submit_Cancel = 17;//确定取消按钮大小
        private int Size_Title = 18;//标题字体大小
        private int Size_Content = 18;//内容字体大小
        private Calendar date;//当前选中时间
        private Calendar startDate;//开始时间
        private Calendar endDate;//终止时间
        private int startYear;//开始年份
        private int endYear;//结尾年份

        private boolean cyclic = false;//是否循环
        private boolean cancelable = true;//是否能取消
        private boolean isCenterLabel = true;//是否只显示中间的label

        private int textColorOut; //分割线以外的文字颜色
        private int textColorCenter; //分割线之间的文字颜色
        private int dividerColor; //分割线的颜色
        private WheelView.DividerType dividerType;//分隔线类型
        // 条目间距倍数 默认1.6
        private float lineSpacingMultiplier = 1.6F;

        private boolean isDialog;//是否是对话框模式

        private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;//单位
        public List<String> list;
        public int width;
        MyDividerItemDecoration mSixDiD;
        public String myTableNum;

        //Required
        public Builder(String myTableNum, List<String> list, MyDividerItemDecoration mSixDiD, int width, Context context, OnTimeSelectListener listener) {
            this.context = context;
            this.timeSelectListener = listener;
            this.list = list;
            this.width = width;
            this.mSixDiD = mSixDiD;
            this.myTableNum = myTableNum;
        }


        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setSubmitText(String Str_Submit) {
            this.Str_Submit = Str_Submit;
            return this;
        }

        public Builder isDialog(boolean isDialog) {
            this.isDialog = isDialog;
            return this;
        }

        public Builder setCancelText(String Str_Cancel) {
            this.Str_Cancel = Str_Cancel;
            return this;
        }

        public Builder setTitleText(String Str_Title) {
            this.Str_Title = Str_Title;
            return this;
        }

        public Builder setSubmitColor(int Color_Submit) {
            this.Color_Submit = Color_Submit;
            return this;
        }

        public Builder setCancelColor(int Color_Cancel) {
            this.Color_Cancel = Color_Cancel;
            return this;
        }

        public Builder setBgColor(int Color_Background_Wheel) {
            this.Color_Background_Wheel = Color_Background_Wheel;
            return this;
        }

        public Builder setTitleBgColor(int Color_Background_Title) {
            this.Color_Background_Title = Color_Background_Title;
            return this;
        }

        public Builder setTitleColor(int Color_Title) {
            this.Color_Title = Color_Title;
            return this;
        }

        public Builder setSubCalSize(int Size_Submit_Cancel) {
            this.Size_Submit_Cancel = Size_Submit_Cancel;
            return this;
        }

        public Builder setTitleSize(int Size_Title) {
            this.Size_Title = Size_Title;
            return this;
        }

        public Builder setContentSize(int Size_Content) {
            this.Size_Content = Size_Content;
            return this;
        }

        /**
         * 因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         *
         * @param date
         * @return
         */
        public Builder setDate(Calendar date) {
            this.date = date;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener customListener) {
            this.layoutRes = res;
            this.customListener = customListener;
            return this;
        }

        public Builder setRange(int startYear, int endYear) {
            this.startYear = startYear;
            this.endYear = endYear;
            return this;
        }

        /**
         * 设置起始时间
         * 因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         *
         * @return
         */

        public Builder setRangDate(Calendar startDate, Calendar endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }


        /**
         * 设置间距倍数,但是只能在1.2-2.0f之间
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 设置分割线的颜色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 设置分割线的类型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            this.dividerType = dividerType;
            return this;
        }

        /**
         * 设置分割线之间的文字的颜色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            this.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 设置分割线以外文字的颜色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            this.textColorOut = textColorOut;
            return this;
        }

        public Builder isCyclic(boolean cyclic) {
            this.cyclic = cyclic;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
            this.label_year = label_year;
            this.label_month = label_month;
            this.label_day = label_day;
            this.label_hours = label_hours;
            this.label_mins = label_mins;
            this.label_seconds = label_seconds;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            this.isCenterLabel = isCenterLabel;
            return this;
        }


        public MatchRadioTrunsationView build() {
            return new MatchRadioTrunsationView(this);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable(cancelable);

        initViews();
        init();
        initEvents();

        if (customListener == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_radio_up_search_trunsation, contentContainer);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            RecyclerView rvlist = (RecyclerView) findViewById(R.id.rv_add_people);
            rvlist.removeItemDecoration(mSixDiD);
            rvlist.addItemDecoration(mSixDiD);
            AddRefereeTableAdapter addRefereeAdapter = new AddRefereeTableAdapter(width, context);
            rvlist.setLayoutManager(new GridLayoutManager(context, 4));
            rvlist.setAdapter(addRefereeAdapter);

            List<AddRefereeBean> addRefereeBeans = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {

                AddRefereeBean addRefereeBean = new AddRefereeBean();
                if (list.get(i).equals(myTableNum)) {
                    addRefereeBean.setCanClickable(false);
                } else {
                    addRefereeBean.setCanClickable(true);
                }
                addRefereeBean.setTableNum("" + list.get(i));
                addRefereeBean.setSelect(false);
                addRefereeBeans.add(addRefereeBean);
            }
            addRefereeAdapter.setNewData(addRefereeBeans);

            addRefereeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    List<AddRefereeBean> data = addRefereeAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }
                    data.get(position).setSelect(true);
                    addRefereeAdapter.setNewData(data);
                    selectItem = "" + data.get(position).getTableNum();

                }
            });
            addRefereeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    boolean fastClick = isFastClick();
                    if (!fastClick) {
                        List<AddRefereeBean> data = addRefereeAdapter.getData();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(false);
                        }
                        data.get(position).setSelect(true);
                        addRefereeAdapter.setNewData(data);
                        selectItem = "" + data.get(position).getTableNum();
                    }
                }
            });
            //确定和取消按钮
            btnSubmit = (TextView) findViewById(R.id.btnSubmit);
            tvTableNum = (TextView) findViewById(R.id.tv_table_num);
            btnCancel = (TextView) findViewById(R.id.btnCancel);
            btnCancel = (TextView) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(context.getResources().getString(com.bigkoo.pickerview.R.string.pickerview_submit));
            btnCancel.setText(context.getResources().getString(com.bigkoo.pickerview.R.string.pickerview_cancel));

            tvTableNum.setText(""+myTableNum+"台");
            //设置文字大小
            btnSubmit.setTextSize(Size_Submit_Cancel);
            btnCancel.setTextSize(Size_Submit_Cancel);

        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(layoutRes, contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(com.bigkoo.pickerview.R.id.timepicker);

        timePickerView.setBackgroundColor(Color_Background_Wheel == 0 ? bgColor_default : Color_Background_Wheel);

        wheelTime = new MatchWheelTime(timePickerView, HOURS_MINS, gravity, Size_Content);

        if (startYear != 0 && endYear != 0 && startYear <= endYear) {
            setRange();
        }

        if (startDate != null && endDate != null) {
            if (startDate.getTimeInMillis() <= endDate.getTimeInMillis()) {
                setRangDate();
            }
        } else if (startDate != null && endDate == null) {
            setRangDate();
        } else if (startDate == null && endDate != null) {
            setRangDate();
        }

        setTime();
        wheelTime.setLabels(label_year, label_month, label_day, label_hours, label_mins, label_seconds);

        setOutSideCancelable(cancelable);
        wheelTime.setCyclic(cyclic);
        wheelTime.setDividerColor(dividerColor);
        wheelTime.setDividerType(dividerType);
        wheelTime.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelTime.setTextColorOut(textColorOut);
        wheelTime.setTextColorCenter(textColorCenter);
        wheelTime.isCenterLabel(isCenterLabel);
    }


    /**
     * 设置默认时间
     */
    public void setDate(Calendar date) {
        this.date = date;
        setTime();
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);

    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(startDate, endDate);
        //如果设置了时间范围
        if (startDate != null && endDate != null) {
            //判断一下默认时间是否设置了，或者是否在起始终止时间范围内
            if (date == null || date.getTimeInMillis() < startDate.getTimeInMillis()
                    || date.getTimeInMillis() > endDate.getTimeInMillis()) {
                date = startDate;
            }
        } else if (startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            date = startDate;
        } else if (endDate != null) {
            date = endDate;
        }
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime() {
        int year, month, day, hours, minute, seconds;

        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = date.get(Calendar.YEAR);
            month = date.get(Calendar.MONTH);
            day = date.get(Calendar.DAY_OF_MONTH);
            hours = date.get(Calendar.HOUR_OF_DAY);
            minute = date.get(Calendar.MINUTE);
            seconds = date.get(Calendar.SECOND);
        }


        wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            returnData();
        }
    }

    public void returnData() {
        if (timeSelectListener != null) {

            String time = wheelTime.getTime();
            timeSelectListener.onTimeSelect(time, clickView, selectItem);

        }
        dismiss();
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(String time, View v, String item);
    }

    @Override
    public boolean isDialog() {
        return isDialog;
    }
}
