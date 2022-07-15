package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ItemSingleScoreDetailBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 单打比赛成绩
 */
public class SinglesScoreDetailOfficialAdapter extends BaseQuickAdapter<XlMatchInfoArrangeChessBean.ChessBean, BaseViewHolder> {
    private boolean isFirst = false;
    private boolean isSubmit = false;
    private int oldPosition = -1;

    public void setSubmit(boolean isSubmit, int oldPosition) {
        this.isSubmit = isSubmit;
        this.oldPosition = oldPosition;
    }

    public SinglesScoreDetailOfficialAdapter(boolean isFirst) {
        super(R.layout.item_single_score_detail);
        this.isFirst = isFirst;
    }

    @Override
    protected void convert(BaseViewHolder helper, XlMatchInfoArrangeChessBean.ChessBean item) {
        ItemSingleScoreDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        int adapterPosition = helper.getAdapterPosition();
        String round = CommonUtilis.numberToChinese(adapterPosition + 1);
        binding.tvPosition.setText("第" + round + "局");
        binding.etOneScore.setText("" + item.getLeftCount());
        binding.etTwoScore.setText("" + item.getRightCount());
//        binding.llOneContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray_little);
//        binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray_little);
        binding.etOneScore.setEnabled(false);
        binding.etTwoScore.setEnabled(false);

    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnMyListener(int scoreOne, int scoreTwo, QueryChessInfoBean.XlClubContestArrangeChessesBean item, int position
                , String textStatus);
    }

    /**
     * 定义一个变量储存数据
     */
    private onMyListener myListener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setMyListener(onMyListener listener) {
        this.myListener = listener;
    }
}
