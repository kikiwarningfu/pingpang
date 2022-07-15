package com.dnwalter.formlayoutmanager.entity;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.adapter.BaseMyFormAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public abstract class NormalBaseHMyFormAdapter<T> extends NormalBaseMyFormAdapter<T> {
    public static final String TAG = NormalBaseHMyFormAdapter.class.getSimpleName();
    private int hangSize = 0;

    public NormalBaseHMyFormAdapter(Context context) {
        super(context);
    }

    public NormalBaseHMyFormAdapter(Context context, int hangSize) {
        super(context);
        this.hangSize = hangSize;
    }

    public NormalBaseHMyFormAdapter(Context context, List list, int hangSize) {
        super(context);
//        this.hangSize = hangSize;
//        Log.e(TAG, "BaseHMyFormAdapter: hangSize===" + hangSize + new Gson().toJson(list));
    }

    public void setHangSize(int hangSize) {
        this.hangSize = hangSize;
    }

    @Override
    public int getRowCount() {
        Log.e(TAG, "getRowCount: hangsize==" + hangSize);
        return hangSize;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List<Integer> colorList = new ArrayList<>();

        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        Log.e(TAG, "onBindViewHolder: position====" + position +
                "rowIndex===" + rowIndex + "columnIndex====" + columnIndex + "columnCount===" + getColumnCount());

//        T model = mList.get(rowIndex);
        String rowData = "";
//        if (model instanceof BaseFormModel) {
//            List<Integer> textColors = getTextColors((BaseFormModel) model);
//            List<Integer> bgColors = getBgColors((BaseFormModel) model);
//            if (textColors.size() > 0) {
//                colorList.add(textColors.get(columnIndex));
//            }
//            if (bgColors.size() > 0) {
//                colorList.add(bgColors.get(columnIndex));
//            }
//
//            Integer[] colors = new Integer[colorList.size()];
//            colorList.toArray(colors);
//            setData(holder, rowIndex, columnIndex, rowData, colors);
//
//        }

        setData(holder, rowIndex, columnIndex, rowData, position);


    }

    /**
     * 获取一行的数据
     *
     * @param model
     * @param index 列的下标
     * @return
     */
    protected abstract String getRowData(T model, int index);
}
