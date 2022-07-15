package heyong.intellectPinPang.adapter.teamregister;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.databinding.ItemTeamRegisterHunshuangBinding;
import heyong.intellectPinPang.databinding.ItemTeamRegisterTuantiBinding;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 俱乐部报名团体人员
 */
public class TeanRegisterShuangDaYeyuAdapter extends BaseQuickAdapter<MatchOrderInfoBean.PlayersBean.TeamsBean, BaseViewHolder> {
    private int width;
    private Context context;
    MyDividerItemDecoration mSixDiD;
    private String teamTitle;

    public TeanRegisterShuangDaYeyuAdapter(Context context, int width, MyDividerItemDecoration mSixDiD, String teamTitle) {
        super(R.layout.item_team_register_hunshuang);
        this.context = context;
        this.width = width;
        this.mSixDiD = mSixDiD;
        this.teamTitle = teamTitle;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchOrderInfoBean.PlayersBean.TeamsBean item) {
        ItemTeamRegisterHunshuangBinding binding = DataBindingUtil.bind(helper.getConvertView());
        View viewDivider = helper.getView(R.id.view_divider);

        binding.tvLeftName.setText("" + teamTitle);
        List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = item.getPlayer();
        binding.tvDuiwuName.setText("" );
        if (player != null && player.size() > 0) {
            binding.tvAlreadySelectNum.setText("" + player.size());
            TeanRegisterSingleShuangDaItemAdapter shuangDaAdapter = new TeanRegisterSingleShuangDaItemAdapter(context, width);
            binding.rvTuanti.setAdapter(shuangDaAdapter);
//            GridLayoutManager gridLayoutManagerCoach = new GridLayoutManager(context, 5);
            binding.rvTuanti.removeItemDecoration(mSixDiD);
            binding.rvTuanti.addItemDecoration(mSixDiD);
            binding.rvTuanti.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
            int size = player.size();
            int mContentSize = size / 2;
            List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> playernew = new ArrayList<>();
            for (int i = 0; i < mContentSize; i++) {
                playernew.add(player.get(i));
            }
            shuangDaAdapter.setNewData(playernew);
            shuangDaAdapter.setMyPlayerDataList(player);
        } else {
            binding.tvAlreadySelectNum.setText("" + 0);
        }


        if (helper.getAdapterPosition() == getData().size() - 1) {
            viewDivider.setVisibility(View.GONE);
        } else {
            viewDivider.setVisibility(View.VISIBLE);
        }

    }
}
