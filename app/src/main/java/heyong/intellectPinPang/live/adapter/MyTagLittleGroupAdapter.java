package heyong.intellectPinPang.live.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elvishew.xlog.XLog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.databinding.ItemGroupSelectLittleBinding;

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
public class MyTagLittleGroupAdapter extends BaseQuickAdapter<MatchScreenFormatBean.MatchTypeBean.InfoBean, BaseViewHolder> {

    public MyTagLittleGroupAdapter() {
        super(R.layout.item_group_select_little);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenFormatBean.MatchTypeBean.InfoBean infoBean) {
        ItemGroupSelectLittleBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvText.setText("" + infoBean.getTitleName());
//        XLog.e(infoBean.getTitleName());
        if (infoBean.isSelect()) {
            binding.tvText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            binding.tvText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            binding.tvText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvText.setTextColor(Color.parseColor("#333333"));
        }
    }
}
