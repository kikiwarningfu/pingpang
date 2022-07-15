package heyong.intellectPinPang.adapter.game;

import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;

public class MessageCoachFormAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MessageCoachFormAdapter() {
        super(R.layout.item_message_coach_form);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvContent = helper.getView(R.id.tv_content);
        tvContent.setText("" + item);
    }
}
