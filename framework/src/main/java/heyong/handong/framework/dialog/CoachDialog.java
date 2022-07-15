package heyong.handong.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.SizeUtils;
import com.handongkeji.framework.R;
import com.handongkeji.framework.databinding.DialogCoachMessageBinding;
import com.handongkeji.framework.databinding.DialogCoachMessageBindingImpl;
import com.handongkeji.framework.databinding.DialogTokenLayoutBinding;

public class CoachDialog extends BaseDialog {

    private static boolean showing;

    private String message;
    private String ids;
    CountDownTimer timer;
    ImageView ivclose;
    TextView tvMessage;
    TextView tvTitle;
    TextView tvSubmit;
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_coach_message;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mBinding.llOut.setClipToOutline(true);
//        mBinding.llOut.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                int dp2px = SizeUtils.dp2px(7);
//                outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight(), dp2px);
//            }
//        });
        ivclose=view.findViewById(R.id.iv_close);
        tvMessage=view.findViewById(R.id.tv_message);
        tvTitle=view.findViewById(R.id.tv_title);
        tvSubmit=view.findViewById(R.id.tv_submit);

        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvMessage.setText("" + millisUntilFinished / 1000 + "S");
              tvSubmit.setEnabled(true);
                tvSubmit.setBackgroundResource(R.drawable.shape_club_blue);
            }

            @Override
            public void onFinish() {
                tvMessage.setText("00:00");
//                tvSure.setText("继续比赛");
//                tvDownTime.setVisibility(View.GONE);
                tvSubmit.setEnabled(false);
                tvSubmit.setBackgroundResource(R.drawable.shape_club_gray);
            }
        }.start();
       ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
                dismiss();
            }
        });
       tvSubmit.setOnClickListener(v -> {
            if (confirmListener != null) {
                confirmListener.onClick(v);
                Intent intent = new Intent("com.example.SECOND_ACTIVITY");
                intent.putExtra("ids", ids);
                startActivity(intent);
            }
            if (timer != null) {
                timer.cancel();
            }
            dismissAllowingStateLoss();
        });
        tvTitle.setText(message);
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new NonBackDialog(this.getActivity(), this.getTheme());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private class NonBackDialog extends Dialog {

        public NonBackDialog(@NonNull Context context) {
            super(context);
        }

        public NonBackDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        public void onBackPressed() {
            //  禁用返回
//            super.onBackPressed();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private View.OnClickListener confirmListener;

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!showing) {
            super.show(manager, tag);
            showing = true;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        showing = false;
    }

}
