package heyong.intellectPinPang.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.utils.Utils;

/**
 * 一个简单的圆角textview
 */
public class RoundTextView extends AppCompatTextView {

    private float cornerRadius;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundTextView(@NonNull Context context) {
        this(context,null);
    }

    public RoundTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView, defStyleAttr, 0);

        if (attributes != null) {
            cornerRadius = attributes.getDimension(R.styleable.RoundTextView_radius, Utils.dp2px(3f));
            int roundColor = attributes.getColor(R.styleable.RoundTextView_round_color, Color.BLACK);
            attributes.recycle();

            paint.setColor(roundColor);
        }
    }

    public void setRoundColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    public void setCornerRadius(float radius) {
        cornerRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius, paint);
        super.onDraw(canvas);
    }
}
