package heyong.intellectPinPang.cn;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import heyong.intellectPinPang.R;


/**
 * Created by you on 2017/9/11.
 */

public class SelectTeamNumberHolder extends RecyclerView.ViewHolder {

    //    public final ImageView iv_header;
    public final View viewDivider;
    public final TextView tv_name;
    public final TextView tvSelect;
    public final RelativeLayout rlContainer;

    public SelectTeamNumberHolder(View itemView) {
        super(itemView);
//        iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
        tv_name = (TextView) itemView.findViewById(R.id.tv_city);
        tvSelect = (TextView) itemView.findViewById(R.id.tv_select);
        viewDivider = (View) itemView.findViewById(R.id.view_divider);
        rlContainer = (RelativeLayout) itemView.findViewById(R.id.rl_container);
    }


}
