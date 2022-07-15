package heyong.intellectPinPang.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.MessageCoachFormAdapter;
import heyong.intellectPinPang.bean.competition.SubmitTeamArrangeBean;

public class MessageCoachFormBuilder extends Dialog {
    private TextView tvTitle;
    private TextView btnCancle;
    private TextView tvSure;
    private View view;
    private RecyclerView rvHomePage;
    SubmitTeamArrangeBean submitTeamArrangeBean;

    public MessageCoachFormBuilder(Context context, SubmitTeamArrangeBean submitTeamArrangeBean) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20000000")));
        this.setContentView(R.layout.dialog_coach_form_message);
        tvTitle = findViewById(R.id.tv_title);
        btnCancle = findViewById(R.id.tv_cancel);
        tvSure = findViewById(R.id.tv_sure);
        view = findViewById(R.id.view_divider);
        rvHomePage = findViewById(R.id.rv_coach_dialog_message);
        MessageCoachFormAdapter messageCoachFormAdapter=new MessageCoachFormAdapter();
        rvHomePage.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        rvHomePage.setAdapter(messageCoachFormAdapter);
        this.submitTeamArrangeBean = submitTeamArrangeBean;
        List<SubmitTeamArrangeBean.TeamArrangesBean> teamArranges = submitTeamArrangeBean.getTeamArranges();
        List<String> contents = new ArrayList<>();
        for (int m = 0; m < teamArranges.size(); m++) {
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean = teamArranges.get(m);
            int hitType = Integer.parseInt(teamArrangesBean.getHitType());
            if (hitType == 1) {
                //单打
                contents.add(""+(m+1)+". " + teamArrangesBean.getPlayer1Set() + "  " + teamArrangesBean.getPlayer1Name());
            } else {
                //双打
                contents.add(""+(m+1)+". " + teamArrangesBean.getPlayer1Set() + "  " + teamArrangesBean.getPlayer1Name()
                        + "  " + teamArrangesBean.getPlayer2Set() + "  " + teamArrangesBean.getPlayer2Name());
            }
        }
        messageCoachFormAdapter.setNewData(contents);


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


    public MessageCoachFormBuilder setBtnCancleHint(boolean isHineCancle) {
        if (isHineCancle) {
            btnCancle.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
        return this;

    }

    public MessageCoachFormBuilder setTvTitle(String title) {
        tvTitle.setText("" + title);
        return this;
    }


    public MessageCoachFormBuilder setTvCancel(String sure) {
        btnCancle.setText(sure);
        return this;
    }

    public MessageCoachFormBuilder setCancelAble(boolean cancelAble) {
        setCancelable(cancelAble);
        return this;
    }

    public MessageCoachFormBuilder setTvSure(String sure) {
        tvSure.setText(sure);
        return this;
    }

    public MessageCoachFormBuilder setTvCancle(String cancle) {
        btnCancle.setText(cancle);
        return this;
    }

    private View.OnClickListener listener1;

    public MessageCoachFormBuilder setSureListener(View.OnClickListener listener) {
        listener1 = listener;
        return this;
    }


    private View.OnClickListener listener;

    public MessageCoachFormBuilder setCancleListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }
}
