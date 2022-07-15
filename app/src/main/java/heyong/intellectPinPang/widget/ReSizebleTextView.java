package heyong.intellectPinPang.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class ReSizebleTextView extends AppCompatTextView {

    public ReSizebleTextView(Context context) {
        super(context);
    }

    public ReSizebleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReSizebleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float unSelectedTextSize = 14;
    private float selectedTextSize = 16;

    @Override
    public void setSelected(boolean selected) {
        setTextSize(selected ? selectedTextSize : unSelectedTextSize);
        super.setSelected(selected);
    }

    public float getUnSelectedTextSize() {
        return unSelectedTextSize;
    }

    public void setUnSelectedTextSize(float unSelectedTextSize) {
        this.unSelectedTextSize = unSelectedTextSize;
    }

    public float getSelectedTextSize() {
        return selectedTextSize;
    }

    public void setSelectedTextSize(float selectedTextSize) {
        this.selectedTextSize = selectedTextSize;
    }
}
