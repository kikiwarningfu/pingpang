package heyong.intellectPinPang.adapter.game;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMyOwnHFormAdapter<T> extends BaseMyOwnFormAdapter<T> {

    public BaseMyOwnHFormAdapter(Context context) {
        super(context);
    }

    public BaseMyOwnHFormAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getRowCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List<Integer> colorList = new ArrayList<>();
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        T model = mList.get(rowIndex);
        String rowData = getRowData(model, columnIndex);
        if (model instanceof BaseFormModel){
            List<Integer> textColors = getTextColors((BaseFormModel) model);
            List<Integer> bgColors = getBgColors((BaseFormModel) model);
            if (textColors.size() > 0){
                colorList.add(textColors.get(columnIndex));
            }
            if (bgColors.size() > 0){
                colorList.add(bgColors.get(columnIndex));
            }

            Integer[] colors = new Integer[colorList.size()];
            colorList.toArray(colors);
            setData(holder, rowIndex, columnIndex, rowData, colors);
        }

        setNewData(holder, rowIndex, columnIndex, rowData,position);
    }

    /**
     * 获取一行的数据
     * @param model
     * @param index 列的下标
     * @return
     */
    protected abstract String getRowData(T model, int index);
}
