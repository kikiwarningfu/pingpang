package heyong.intellectPinPang.adapter.game;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SimpleCalculationInfoBean;
import heyong.intellectPinPang.databinding.ItemIntegralCalculateMiddleBinding;

public class IntegrakCalculateMiddleAdapter extends BaseQuickAdapter<SimpleCalculationInfoBean, BaseViewHolder> {
    private Context context;

    public IntegrakCalculateMiddleAdapter(Context context) {
        super(R.layout.item_integral_calculate_middle);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleCalculationInfoBean item) {
        ItemIntegralCalculateMiddleBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText(""+item.getName());
        binding.tvRankingNumber.setText(""+item.getRanking());
        List<SimpleCalculationInfoBean.ListInfoBean> listInfo = item.getListInfo();

        RecyclerView rvCalculateView = helper.getView(R.id.rv_calculate_view);
        rvCalculateView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        IntegralCalculateItemAdapter integralCalculateItemAdapter = new IntegralCalculateItemAdapter(context);
        rvCalculateView.setAdapter(integralCalculateItemAdapter);
        integralCalculateItemAdapter.setNewData(listInfo);



    }
}
