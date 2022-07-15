package heyong.intellectPinPang.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import heyong.intellectPinPang.R;

public class MessageDialogTitleBuilder extends Dialog {
    private TextView tvMessage;
    private TextView btnCancle;
    private TextView tvSure;
    private View view;

    public MessageDialogTitleBuilder(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20000000")));
        this.setContentView(R.layout.dialog_message_title);
        tvMessage = findViewById(R.id.tv_message);
        btnCancle = findViewById(R.id.tv_cancel);
        tvSure = findViewById(R.id.tv_sure);
        view = findViewById(R.id.view_divider);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels - 100;
        window.setAttributes(lp);


        btnCancle.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onClick(tvSure);
            }
        });
        tvSure.setOnClickListener(v -> {
            dismiss();
            if (listener1 != null) {
                listener1.onClick(tvSure);
            }
        });
    }


    public MessageDialogTitleBuilder setBtnCancleHint(boolean isHineCancle) {
        if (isHineCancle) {
            btnCancle.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
        return this;

    }



    public MessageDialogTitleBuilder setMessage(String message) {
        tvMessage.setText( ""+message);
        return this;
    }
    public MessageDialogTitleBuilder setTvCancel(String sure) {
        btnCancle.setText(sure);
        return this;
    }

    public MessageDialogTitleBuilder setTvSure(String sure) {
        tvSure.setText(sure);
        return this;
    }

    public MessageDialogTitleBuilder setTvCancle(String cancle) {
        btnCancle.setText(cancle);
        return this;
    }

    private View.OnClickListener listener1;

    public MessageDialogTitleBuilder setSureListener(View.OnClickListener listener) {
        listener1 = listener;
        return this;
    }


    private View.OnClickListener listener;

    public MessageDialogTitleBuilder setCancleListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }
}
