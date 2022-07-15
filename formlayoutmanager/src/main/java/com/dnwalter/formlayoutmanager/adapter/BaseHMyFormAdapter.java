package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHMyFormAdapter<T> extends BaseMyFormAdapter<T> {
    public static final String TAG = BaseHMyFormAdapter.class.getSimpleName();
    private int hangSize = 0;

    public BaseHMyFormAdapter(Context context) {
        super(context);
    }

    public BaseHMyFormAdapter(Context context, int hangSize) {
        super(context);
        this.hangSize = hangSize;
    }

    public BaseHMyFormAdapter(Context context, List list, int hangSize) {
        super(context, list);
        this.hangSize = hangSize;
    }

    public void setHangSize(int hangSize) {
        this.hangSize = hangSize;
    }

    @Override
    public int getRowCount() {
        return hangSize;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List<Integer> colorList = new ArrayList<>();

        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();

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
