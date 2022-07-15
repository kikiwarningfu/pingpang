package heyong.intellectPinPang.cn;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import heyong.intellectPinPang.R;

public class HeaderHolder  extends RecyclerView.ViewHolder {
    public final TextView tv_header;
    public HeaderHolder(View itemView) {
        super(itemView);
        tv_header = (TextView) itemView.findViewById(R.id.tv_header);
    }
}