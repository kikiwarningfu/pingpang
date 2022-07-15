package heyong.intellectPinPang.adapter.game;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CoashRoleListBean;
import heyong.intellectPinPang.databinding.ItemRefereeRolenameListBinding;

/*
列表
 */
public class RefreRoleNameListAdapter extends BaseQuickAdapter<CoashRoleListBean, BaseViewHolder> {


    public RefreRoleNameListAdapter() {
        super(R.layout.item_referee_rolename_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoashRoleListBean item) {
        ItemRefereeRolenameListBinding binding= DataBindingUtil.bind(helper.getConvertView());
        TextView tvALevel = helper.getView(R.id.tv_amateur_level);
        View viewDivier = helper.getView(R.id.view_divider);
        tvALevel.setText("" + item.getRoleName());
        if (helper.getAdapterPosition() == getData().size() - 1) {
            viewDivier.setVisibility(View.GONE);
        } else {
            viewDivier.setVisibility(View.VISIBLE);
        }

    }
}
