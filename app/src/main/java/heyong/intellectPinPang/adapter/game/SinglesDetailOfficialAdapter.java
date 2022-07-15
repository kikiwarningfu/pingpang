package heyong.intellectPinPang.adapter.game;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.databinding.ItemSingleDetailBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 单打比赛成绩
 */
public class SinglesDetailOfficialAdapter extends BaseQuickAdapter<BeginArrangeBean.ChessBean, BaseViewHolder> {
    private boolean isFirst = false;
    private boolean isSubmit = false;
    private int oldPosition = -1;
    public static final String TAG=SinglesDetailAdapter.class.getSimpleName();
    public void setSubmit(boolean isSubmit, int oldPosition) {
        this.isSubmit = isSubmit;
        this.oldPosition = oldPosition;
    }

    public SinglesDetailOfficialAdapter(boolean isFirst) {
        super(R.layout.item_single_detail);
        this.isFirst = isFirst;
    }

    @Override
    protected void convert(BaseViewHolder helper, BeginArrangeBean.ChessBean item) {
        ItemSingleDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        int adapterPosition = helper.getAdapterPosition();
        String round = CommonUtilis.numberToChinese(adapterPosition + 1);
        binding.tvPosition.setText("第" + round + "局");
        if (!TextUtils.isEmpty(item.getLeftCount())) {
            binding.etOneScore.setText("" + item.getLeftCount());
        }
        if (!TextUtils.isEmpty(item.getRightCount())) {
            binding.etTwoScore.setText("" + item.getRightCount());
        }
//        helper.addOnClickListener(R.id.ll_one_content);
//        helper.addOnClickListener(R.id.ll_two_content);
//        helper.addOnClickListener(R.id.et_one_score);
//        helper.addOnClickListener(R.id.et_two_score);
        int status = Integer.parseInt(item.getStatus());
        if (status == 3) {
            binding.etOneScore.setEnabled(false);
            binding.etTwoScore.setEnabled(false);
            binding.llOneContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
            binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
            isFirst = false;
            binding.tvLogin.setText("确定");
            binding.tvLogin.setBackgroundResource(R.drawable.shape_club_blue);
        } else {
            if(item.isTextSubmit())
            {
                binding.tvLogin.setText("确定");
                binding.tvLogin.setBackgroundResource(R.drawable.shape_club_blue);
            }else
            {
                binding.tvLogin.setText("修改");
                binding.tvLogin.setBackgroundResource(R.drawable.shape_big_red_corners);

            }
            if (item.isFirst()) {
                binding.etOneScore.setEnabled(true);
                binding.etTwoScore.setEnabled(true);
                binding.llOneContent.setBackgroundResource(R.drawable.shape_big_blue_corners);
                binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_blue_corners);

//                shape_big_gray_corners_solid_gray   shape_big_blue_corners
            } else {
                binding.etOneScore.setEnabled(false);
                binding.etTwoScore.setEnabled(false);
                binding.llOneContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
                binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
            }

        }

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canChange = item.isCanChange();
                String status = item.getStatus();
                int i = Integer.parseInt(status);
                if (i == 3) {
                    item.setCanChange(false);
                    /*判断两个EditText的值*/
                    binding.tvLogin.setText("确定");
                    binding.tvLogin.setBackgroundResource(R.drawable.shape_club_blue);

                    binding.etOneScore.setEnabled(false);
                    binding.etTwoScore.setEnabled(false);
                    binding.llOneContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
                    binding.llTwoContent.setBackgroundResource(R.drawable.shape_big_gray_corners_solid_gray);
                } else {
//                    if (canChange) {
//                        item.setCanChange(false);
//                        /*判断两个EditText的值*/
////                        binding.tvLogin.setText("确定");
//                        binding.etOneScore.setEnabled(false);
//                        binding.etTwoScore.setEnabled(false);
//
//                    } else {
//                        item.setCanChange(true);
//                        binding.etOneScore.setEnabled(true);
//                        binding.etTwoScore.setEnabled(true);
                    String strScoreOne = binding.etOneScore.getText().toString().trim();
                    String strScoreTwo = binding.etTwoScore.getText().toString().trim();
                    if (!TextUtils.isEmpty(strScoreOne) && !TextUtils.isEmpty(strScoreTwo)) {
                        int scoreOne = Integer.parseInt(strScoreOne);
                        int scoreTwo = Integer.parseInt(strScoreTwo);
                        if (scoreOne >= 11 && scoreTwo >= 11) {
                            if ((scoreOne - scoreTwo) >= 2 || (scoreTwo - scoreOne) >= 2) {
                                if (myListener != null) {
                                    myListener.OnMyListener(scoreOne, scoreTwo, item, helper.getAdapterPosition(),
                                            binding.tvLogin.getText().toString(), isFirst);
                                }
                            } else {
                                ToastUtils.showShort("本局未决出胜负");
                                return;
                            }
                        } else if (scoreOne >= 11 || scoreTwo >= 11) {

                            if (myListener != null) {
                                myListener.OnMyListener(scoreOne, scoreTwo, item, helper.getAdapterPosition(),
                                        binding.tvLogin.getText().toString(), isFirst);
                            }
                        } else {
                            ToastUtils.showShort("本局未决出胜负");
                            return;
                        }
                    } else {
                        ToastUtils.showShort("请完善对局比分");
                        return;
                    }

                }
//                }


            }
        });
    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnMyListener(int scoreOne, int scoreTwo, BeginArrangeBean.ChessBean item, int position
                , String textStatus, boolean isFirst);
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
