package heyong.intellectPinPang.soundnet.actionsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import heyong.intellectPinPang.MyApp;
import heyong.intellectPinPang.SampleApplicationLike;

public abstract class AbstractActionSheet extends RelativeLayout {
    public AbstractActionSheet(Context context) {
        super(context);
    }

    public AbstractActionSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractActionSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface AbsActionSheetListener {

    }

    public abstract void setActionSheetListener(AbsActionSheetListener listener);

    protected MyApp application() {
        return (MyApp) getContext().getApplicationContext();
    }
}