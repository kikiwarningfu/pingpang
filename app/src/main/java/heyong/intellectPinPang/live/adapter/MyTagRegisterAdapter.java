package heyong.intellectPinPang.live.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CompetitionItemBean;

/**
 *
 */
public class MyTagRegisterAdapter extends TagAdapter<CompetitionItemBean> {
    private Context context;
    List<CompetitionItemBean> datas = new ArrayList<>();

    public MyTagRegisterAdapter(Context context, List<CompetitionItemBean> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
    }

    public void notifyDatas(List<CompetitionItemBean> datas) {
        this.datas = datas;
        notifyDataChanged();
    }

    @Override
    public View getView(FlowLayout parent, int position, CompetitionItemBean tagBean) {
        View inflate = View.inflate(context, R.layout.item_my_tag_register, null);
        TextView tvName = inflate.findViewById(R.id.tv_name);
        tvName.setText(""+tagBean.getProjectName()+"" + tagBean.getItemTitle());

        if (datas.get(position).isSelect()) {
            Drawable drawable;
            drawable = context.getResources().getDrawable(R.drawable.icon_login_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
            tvName.setTextColor(Color.parseColor("#FF4795ED"));
        } else {
            Drawable drawable;
            drawable = context.getResources().getDrawable(R.drawable.icon_login_unselected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
            tvName.setTextColor(Color.parseColor("#FF333333"));

        }
        return tvName;
    }
}
