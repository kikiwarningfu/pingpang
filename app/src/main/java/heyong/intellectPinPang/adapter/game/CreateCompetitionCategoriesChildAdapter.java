package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.gsonbean.CreateEventsBean;
import heyong.intellectPinPang.databinding.ItemCreateCompetitionCategoryChildBinding;

/**
 * 创建比赛组别
 */
public class CreateCompetitionCategoriesChildAdapter extends BaseQuickAdapter<CreateEventsBean.ProjectListBean.ProjectItemListBean, BaseViewHolder> {
    private Context context;

    public CreateCompetitionCategoriesChildAdapter(Context context) {
        super(R.layout.item_create_competition_category_child);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CreateEventsBean.ProjectListBean.ProjectItemListBean item) {
        helper.addOnClickListener(R.id.ll_del);
        ItemCreateCompetitionCategoryChildBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvZuName.setText("" + item.getItemTitle());
        if (item.getManMaxAge().equals("不限年龄") || item.getWomanMaxAge().equals("不限年龄")
                || item.getManMinAge().equals("不限年龄") || item.getWomanMinAge().equals("不限年龄")) {
            binding.tvAge.setText("不限年龄");
        } else {
            if (!TextUtils.isEmpty(item.getManMaxAge()) && !TextUtils.isEmpty(item.getWomanMaxAge())) {
                binding.tvAge.setText("" + item.getManMinAge() + "至" + item.getManMaxAge() + "\n"
                        + item.getWomanMinAge() + "至" + item.getWomanMaxAge());
            } else if (!TextUtils.isEmpty(item.getManMaxAge())) {
                binding.tvAge.setText("" + item.getManMinAge() + "至" + item.getManMaxAge());
            } else {
                binding.tvAge.setText("" + item.getWomanMinAge() + "至" + item.getWomanMaxAge());
            }
        }


    }
}
