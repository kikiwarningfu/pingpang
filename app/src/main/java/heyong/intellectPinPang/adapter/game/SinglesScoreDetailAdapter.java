package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sxu.shadowdrawable.ShadowDrawable;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.databinding.ItemSingleScoreDetailBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

import static com.qmuiteam.qmui.util.QMUIDisplayHelper.dpToPx;

/**
 * 单打比赛成绩
 */
public class SinglesScoreDetailAdapter extends BaseQuickAdapter<QueryChessInfoBean.XlClubContestArrangeChessesBean, BaseViewHolder> {
    private boolean isFirst = false;
    private boolean isSubmit = false;
    private int oldPosition = -1;

    public void setSubmit(boolean isSubmit, int oldPosition) {
        this.isSubmit = isSubmit;
        this.oldPosition = oldPosition;
    }

    public SinglesScoreDetailAdapter(boolean isFirst) {
        super(R.layout.item_single_score_detail);
        this.isFirst = isFirst;
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryChessInfoBean.XlClubContestArrangeChessesBean item) {
        ItemSingleScoreDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        int adapterPosition = helper.getAdapterPosition();
        String round = CommonUtilis.numberToChinese(adapterPosition + 1);
        binding.tvPosition.setText("第" + round + "局");
        boolean canChange = item.isCanChange();
        binding.etOneScore.setText(""+item.getLeftIntegral());
        binding.etTwoScore.setText(""+item.getRightIntegral());
//        binding.llOneContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray_little);
//        binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray_little);
        // 实例：设置背景为颜色为#3D5AFE，圆角为8dp, 阴影颜色为#66000000，宽度为10dp的背景


//        if (canChange) {
            /**/
            /*显示为修改  */
//            binding.tvLogin.setText("修改");
            binding.etOneScore.setEnabled(false);
            binding.etTwoScore.setEnabled(false);
//        } else {
//            /*显示为确定  需要判断两个EditText的分数  然后请求接口  */
//            if (!isFirst) {
//                binding.etOneScore.setEnabled(false);
//                binding.etTwoScore.setEnabled(false);
//                isFirst = false;
//            }
//            binding.tvLogin.setText("确定");
//
//        }
//        binding.etTwoScore.setOnFocusChangeListener(new View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    if (!TextUtils.isEmpty(item.getRightIntegral())) {
//                        /*有焦点的时候*/
////                        if(helper.getAdapterPosition()==E)
//                        if(canChange) {
////                            binding.tvLogin.setText("修改");
//                            binding.etTwoScore.setEnabled(true);
//                        }
//                    }
////                    notifyDataSetChanged();
//                } else {
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//        });
//        binding.etOneScore.setOnFocusChangeListener(new View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    if (!TextUtils.isEmpty(item.getLeftIntegral())) {
//                        /*有焦点的时候*/
//                        if(canChange) {
////                            binding.tvLogin.setText("修改");
//                            binding.etOneScore.setEnabled(true);
//                        }
//                    }
//                } else {
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//        });
//        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean canChange = item.isCanChange();
//                if (canChange) {
//                    /*判断两个EditText的值*/
//                    binding.tvLogin.setText("确定");
//                    item.setCanChange(false);
//                    binding.etOneScore.setEnabled(false);
//                    binding.etTwoScore.setEnabled(false);
//
//                } else {
//                    binding.etOneScore.setEnabled(true);
//                    binding.etTwoScore.setEnabled(true);
//                    String strScoreOne = binding.etOneScore.getText().toString().trim();
//                    String strScoreTwo = binding.etTwoScore.getText().toString().trim();
//                    if (!TextUtils.isEmpty(strScoreOne) && !TextUtils.isEmpty(strScoreTwo)) {
//                        int scoreOne = Integer.parseInt(strScoreOne);
//                        int scoreTwo = Integer.parseInt(strScoreTwo);
//                        if (scoreOne >= 11 || scoreTwo >= 11) {
//                            if ((scoreOne >= 11 && (scoreOne - scoreTwo) >= 2) || (scoreTwo >= 11 && (scoreTwo - scoreOne) >= 2)) {
//                                if (!TextUtils.isEmpty(item.getLeftIntegral()) && !TextUtils.isEmpty(item.getRightIntegral())) {
//                                    if(canChange) {
//                                        binding.tvLogin.setText("修改");
//                                        /*调用接口回调*/
//                                        item.setCanChange(true);
//                                    }
//                                }
//
//                                if (myListener != null) {
//                                    myListener.OnMyListener(scoreOne, scoreTwo, item, helper.getAdapterPosition(), binding.tvLogin.getText().toString());
//                                }
//                            } else {
//                                ToastUtils.showShort("本局未决出胜负");
//                                return;
//                            }
//
//                            /*判断两个EditText的值*/
//                            if (!TextUtils.isEmpty(item.getLeftIntegral()) && !TextUtils.isEmpty(item.getRightIntegral())) {
//                                if(canChange) {
//                                    binding.tvLogin.setText("修改");
//                                    item.setCanChange(true);
//                                }
//                            }
//
//                        } else {
//                            ToastUtils.showShort("本局未决出胜负");
//                            return;
//                        }
//
//
//                    } else {
//                        ToastUtils.showShort("请完善对局比分");
//                        return;
//                    }
//
//                }
//
//
//            }
//        });
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
