package heyong.intellectPinPang.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import heyong.intellectPinPang.R;

public class MessageDialogBuilderException extends Dialog {
    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvSure;
    private View view;

    public MessageDialogBuilderException(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20000000")));
        this.setContentView(R.layout.dialog_message_execapetion);
        tvTitle = findViewById(R.id.tv_title);
        tvMessage = findViewById(R.id.tv_message);
        tvSure = findViewById(R.id.tv_sure);
        view = findViewById(R.id.view_divider);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels - 100;
        window.setAttributes(lp);


        tvSure.setOnClickListener(v -> {
            dismiss();
            if (listener1 != null) {
                listener1.onClick(tvSure);
            }
        });
    }


    public MessageDialogBuilderException setBtnCancleHint(boolean isHineCancle) {
        if (isHineCancle) {
//            btnCancle.setVisibility(View.GONE);
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
        return this;

    }

    public MessageDialogBuilderException setTvTitle(String title) {
        tvTitle.setText("" + title);
        return this;
    }

    public MessageDialogBuilderException setMessage(String message) {
        tvMessage.setText("" + message);
        return this;
    }

    public MessageDialogBuilderException setTvSure(String sure) {
        tvSure.setText(sure);
        return this;
    }

//    public MessageDialogBuilderException setTvCancle(String cancle) {
//        btnCancle.setText(cancle);
//        return this;
//    }

    private View.OnClickListener listener1;

    public MessageDialogBuilderException setSureListener(View.OnClickListener listener) {
        listener1 = listener;
        return this;
    }


    private View.OnClickListener listener;

    public MessageDialogBuilderException setCancleListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }
}
