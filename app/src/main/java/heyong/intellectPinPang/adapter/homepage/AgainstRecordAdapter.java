package heyong.intellectPinPang.adapter.homepage;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemAgainstRecordBinding;

/**
 * 对战记录
 */
public class AgainstRecordAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public AgainstRecordAdapter() {
        super(R.layout.item_against_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemAgainstRecordBinding binding= DataBindingUtil.bind(helper.getConvertView());



    }



}
