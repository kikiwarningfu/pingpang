package heyong.intellectPinPang.cn;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import heyong.intellectPinPang.R;


/**
 * Created by you on 2017/9/11.
 */

public class ClubChoiceCityHolder extends RecyclerView.ViewHolder {

    //    public final ImageView iv_header;
    public final View viewDivider;
    public final TextView tv_name;

    public ClubChoiceCityHolder(View itemView) {
        super(itemView);
//        iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
        tv_name = (TextView) itemView.findViewById(R.id.tv_city);
        viewDivider = (View) itemView.findViewById(R.id.view_divider);
    }
}
