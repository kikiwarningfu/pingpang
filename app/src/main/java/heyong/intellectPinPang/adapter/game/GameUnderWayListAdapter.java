package heyong.intellectPinPang.adapter.game;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.databinding.ItemGameUnderWayListBinding;

/**
 * 比赛进行中的适配器
 */
public class GameUnderWayListAdapter extends BaseQuickAdapter<SingleHitDataBean.ContestArrangeListBeanX, BaseViewHolder> {
    private Context context;

    public GameUnderWayListAdapter(Context context) {
        super(R.layout.item_game_under_way_list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SingleHitDataBean.ContestArrangeListBeanX item) {
        ItemGameUnderWayListBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.rvSingleRoundList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        GameUnderWayAdapter gameUnderWayAdapter = new GameUnderWayAdapter();
        binding.rvSingleRoundList.setAdapter(gameUnderWayAdapter);
        gameUnderWayAdapter.setNewData(item.getContestArrangeList());
        binding.tvSingleRoundNum.setText("第" + item.getRounds() + "轮");
        gameUnderWayAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_update_status://开始比赛
                    boolean fastClick = isFastClick();
                    if (!fastClick) {
                        SingleHitDataBean.ContestArrangeListBeanX.ContestArrangeListBean contestArrangeListBean = gameUnderWayAdapter.getData().get(position);
                        long id = contestArrangeListBean.getId();
                        if (listener != null) {
                            listener.OnMyListener("" + id, contestArrangeListBean, position);
                        }
                    }
                    break;
            }
        });

    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnMyListener(String id, SingleHitDataBean.ContestArrangeListBeanX.ContestArrangeListBean bean,
                        int position);
    }

    /**
     * 定义一个变量储存数据
     */
    private onMyListener listener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setListener(onMyListener listener) {
        this.listener = listener;
    }
}
