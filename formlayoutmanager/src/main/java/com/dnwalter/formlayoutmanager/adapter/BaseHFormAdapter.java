package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHFormAdapter<T> extends BaseFormAdapter<T> {
    public static final String TAG = BaseHFormAdapter.class.getSimpleName();

    public BaseHFormAdapter(Context context) {
        super(context);
    }

    public BaseHFormAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getRowCount() {
//        Log.e(TAG, "getRowCount: listSize==="+mList.size() );
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List<Integer> colorList = new ArrayList<>();
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
//        Log.e(TAG, "onBindViewHolder: rowIndex==="+rowIndex );
//        Log.e(TAG, "onBindViewHolder: " );
        T model = mList.get(rowIndex);
        String rowData = getRowData(model, columnIndex);

//        Log.e(TAG, "onBindViewHolder: position===="+position+
//        "rowIndex==="+rowIndex+"columnIndex===="+columnIndex+"columnCount==="+getColumnCount());
        if (model instanceof BaseFormModel) {
            List<Integer> textColors = getTextColors((BaseFormModel) model);
            List<Integer> bgColors = getBgColors((BaseFormModel) model);
            if (textColors.size() > 0) {
                colorList.add(textColors.get(columnIndex));
            }
            if (bgColors.size() > 0) {
                colorList.add(bgColors.get(columnIndex));
            }

            Integer[] colors = new Integer[colorList.size()];
            colorList.toArray(colors);
//            setData(holder, rowIndex, columnIndex, rowData, colors);
        }
        setData(holder,position);
//        setData(holder, rowIndex, columnIndex, model,position);
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
