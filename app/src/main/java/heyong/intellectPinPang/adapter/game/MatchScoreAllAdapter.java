package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchArrangeMatchScoreBean;
import heyong.intellectPinPang.databinding.ItemMatchScoreAllBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

public class MatchScoreAllAdapter extends BaseQuickAdapter<MatchArrangeMatchScoreBean.ResultListBean, BaseViewHolder> {
    private Context context;
    private int types;

    public MatchScoreAllAdapter(Context context, int types) {
        super(R.layout.item_match_score_all);
        this.context = context;
        this.types = types;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchArrangeMatchScoreBean.ResultListBean item) {
        ItemMatchScoreAllBinding binding = DataBindingUtil.bind(helper.getConvertView());

        String rank = item.getRank();
        int i = Integer.parseInt(rank);
        if (i == 1) {
            binding.ivRanking.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(binding.ivRanking, R.drawable.img_detail_champion);
            binding.tvRankName.setText("冠军");

        } else if (i == 2) {
            binding.ivRanking.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(binding.ivRanking, R.drawable.img_detail_second);
            binding.tvRankName.setText("亚军");

        } else if (i == 3) {
            binding.ivRanking.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(binding.ivRanking, R.drawable.img_detail_third);
            binding.tvRankName.setText("第" + CommonUtilis.numberToChinese(i)+"名");

        } else {
            binding.ivRanking.setVisibility(View.GONE);
            binding.tvRankName.setText("第" + CommonUtilis.numberToChinese(i)+"名");
        }


        binding.rvRankingForm.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        MatchScoreItemAdapter matchScoreItemAdapter = new MatchScoreItemAdapter(types);
        binding.rvRankingForm.setAdapter(matchScoreItemAdapter);
        matchScoreItemAdapter.setNewData(item.getResultInfo());
        matchScoreItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    if (myListener != null) {
                        MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean resultInfoBean = matchScoreItemAdapter.getData().get(position);
                        myListener.OnMyListener(resultInfoBean);
                    }
                }
            }
        });


    }

    /**
     * 定义一个接口
     */
    public interface onOwnMyListener {
        void OnMyListener(MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean titleBean);
    }

    /**
     * 定义一个变量储存数据
     */
    private onOwnMyListener myListener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setMyListener(onOwnMyListener listener) {
        this.myListener = listener;
    }
}
