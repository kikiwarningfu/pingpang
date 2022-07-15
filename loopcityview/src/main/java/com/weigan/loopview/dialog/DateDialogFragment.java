package com.weigan.loopview.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.weigan.loopview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class DateDialogFragment extends DialogFragment {


    private final List<String> months;
    private final List<String> years;
    private final List<String> days = new ArrayList<>();
    private int[] dayCounts = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private LoopView loopYear;
    private LoopView loopMonth;
    private LoopView loopDay;

    private String cancel;
    private String ensure;
    private String title;

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public void setEnsure(String ensure) {
        this.ensure = ensure;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface OnDateGetListener {
        void onDateCallback(int year, int month, int day);
    }

    private OnDateGetListener listener;

    public DateDialogFragment() {

        setStyle(STYLE_NO_TITLE,0);
        years = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1949; i < year + 1; i++) {
            years.add(String.valueOf(i).concat("年"));
        }
        months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
    }

    public void setListener(OnDateGetListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_date, container, false);

        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        TextView tvEnsure = rootView.findViewById(R.id.tv_ensure);
        tvEnsure.setText(ensure);
        //确定
        tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String date = years.get(loopYear.getSelectedItem())
                            .concat(months.get(loopMonth.getSelectedItem()))
                            .concat(days.get(loopDay.getSelectedItem()));

                    int year = Integer.parseInt(years.get(loopYear.getSelectedItem()).replace("年", ""));
                    int month = Integer.parseInt(months.get(loopMonth.getSelectedItem()).replace("月", ""));
                    int day = Integer.parseInt(days.get(loopDay.getSelectedItem()).replace("日", ""));
                    listener.onDateCallback(year, month, day);
                }
                dismiss();
            }
        });

        TextView tvCancel = rootView.findViewById(R.id.tv_cancel);
        tvCancel.setText(cancel);
        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loopYear = rootView.findViewById(R.id.loop_year);
        loopMonth = rootView.findViewById(R.id.loop_month);
        loopDay = rootView.findViewById(R.id.loop_day);

        loopYear.setItems(years);
        loopYear.setCurrentPosition(50);
        loopMonth.setCurrentPosition(0);

        loopMonth.setItems(months);
        setDays();

        loopYear.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                setDays();
            }
        });

        loopMonth.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                setDays();
            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    private void setDays() {

        int yearIndex = loopYear.getSelectedItem();
        int monthIndex = loopMonth.getSelectedItem();

        int year = Integer.parseInt(years.get(yearIndex).replace("年", ""));
        int lastDayCount = dayCounts[monthIndex];

        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            dayCounts[1] = 29;
        } else {
            dayCounts[1] = 28;
        }
        days.clear();
        for (int i = 1; i < dayCounts[monthIndex] + 1; i++) {
            days.add(String.valueOf(i).concat("日"));
        }
        loopDay.setItems(days);
        loopDay.setInitPosition(0);

    }
}
