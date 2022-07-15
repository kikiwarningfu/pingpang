package heyong.intellectPinPang.live.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.TagBean;

/**
 * Create On 2019/9/26 11:45
 * Copyrights 2019/9/26 handongkeji All rights reserved
 * ClassName:MyTagHistoryAdapter
 * PackageName:com.farunwanjia.app.ui.search.adapter
 * Site:http://www.handongkeji.com
 * Author: wanghongfu
 * Date: 2019/9/26 11:45
 * Description:
 */
public class MyTagHistoryAdapter extends TagAdapter<TagBean> {
    private Context context;
    List<TagBean> datas = new ArrayList<>();

    public MyTagHistoryAdapter(Context context, List<TagBean> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
    }
    @Override
    public View getView(FlowLayout parent, int position, TagBean tagBean) {
        View inflate = View.inflate(context, R.layout.item_search_record, null);
        TextView tvName = inflate.findViewById(R.id.tv_name);
        tvName.setText(tagBean.getName());
//        if (datas.get(position).isSelect()) {
//            tv.setTextColor(activity.getResources().getColor(R.color.white));
//            tv.setBackgroundResource(R.drawable.shape_shopping_selected_flow_solid);
//        } else {
//            tv.setTextColor(activity.getResources().getColor(R.color.color_6));
//            tv.setBackgroundResource( R.drawable.shape_shopping_def_flow_solid);
//        }
        return tvName;
    }
}
