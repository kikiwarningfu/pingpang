package heyong.intellectPinPang.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration{
    private Paint mPaint;
    private int mDividerHeight = 0;

    public GridSpaceItemDecoration(Context context, int dividerHeight, int dividerColor) {
        mDividerHeight = dividerHeight;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDividerHeight;//矩形的底部赋值分割线的高度
        outRect.top = mDividerHeight;
        outRect.right = mDividerHeight;
        outRect.left = mDividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();//获取到子View的个数
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mDividerHeight;//子View底部添加分割线的高度
            c.drawRect(left, top, right, bottom, mPaint);//绘制
        }
    }

}
