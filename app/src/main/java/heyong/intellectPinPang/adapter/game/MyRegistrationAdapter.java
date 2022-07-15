package heyong.intellectPinPang.adapter.game;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;


import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.EnListBean;
import heyong.intellectPinPang.databinding.ItemMyRegistrationCompleteBinding;
import heyong.intellectPinPang.databinding.ItemMyRegistrationNoCompleteBinding;
import heyong.intellectPinPang.databinding.ItemMyRegistrationWithDrawBinding;
import heyong.intellectPinPang.utils.MyDateUtils;


/**
 * 我的报名列表
 */
public class MyRegistrationAdapter extends BaseQuickAdapter<EnListBean.ListBean, BaseViewHolder> {
    public MyRegistrationAdapter() {
        super(0);
        MultiTypeDelegate<EnListBean.ListBean> multiTypeDelegate = new MultiTypeDelegate<EnListBean.ListBean>() {
            @Override
            protected int getItemType(EnListBean.ListBean o) {
                return Integer.parseInt(o.getFreeStatus());//1未支付 2支付成功 3取消订单(后台过滤 不返回) 4退款中 5退款成功
            }
        };
        multiTypeDelegate.registerItemType(1, R.layout.item_my_registration_no_complete);//未支付
        multiTypeDelegate.registerItemType(2, R.layout.item_my_registration_complete);//已支付
        multiTypeDelegate.registerItemType(3, R.layout.item_my_registration_complete);//取消支付
        multiTypeDelegate.registerItemType(4, R.layout.item_my_registration_with_draw);//退款中
        multiTypeDelegate.registerItemType(5, R.layout.item_my_registration_with_draw);//退款成功
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnListBean.ListBean item) {
        switch (helper.getItemViewType()) {
            case 3:

                ItemMyRegistrationCompleteBinding binding3111 = DataBindingUtil.bind(helper.getConvertView());
                binding3111.tvTitle.setText("" + item.getMatchTitle());
                String matchTime111 = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getCreateTime());
                binding3111.tvTime.setText("报名截止时间：" + matchTime111);
//                helper.addOnClickListener(R.id.tv_sign_up_detail);//报名详情
//                helper.addOnClickListener(R.id.tv_tuisai);//我要退赛
                binding3111.tvTuisai.setBackgroundResource(R.drawable.shape_club_gray);
                binding3111.tvSignUpDetail.setBackgroundResource(R.drawable.shape_club_gray);

                binding3111.tvPrice.setText("¥" + item.getAllFree());
                binding3111.tvSignStatus.setText("报名取消");
                break;
            case 1://0


                ItemMyRegistrationNoCompleteBinding binding1 = DataBindingUtil.bind(helper.getConvertView());
                binding1.tvTitle.setText("" + item.getMatchTitle());
                binding1.tvPrice.setText("¥" + item.getAllFree());
                binding1.tvPayStatus.setText("未支付");
                String matchTime = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getCreateTime());
                binding1.tvTime.setText("" + matchTime);
                helper.addOnClickListener(R.id.tv_submit);
                helper.addOnClickListener(R.id.iv_delete);
                break;
            case 2://1
                ItemMyRegistrationCompleteBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
                binding2.tvTitle.setText("" + item.getMatchTitle());
                String matchTime1 = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getCreateTime());
                binding2.tvTime.setText("报名截止时间：" + matchTime1);
                helper.addOnClickListener(R.id.tv_sign_up_detail);//报名详情
                helper.addOnClickListener(R.id.tv_tuisai);//我要退赛
                binding2.tvPrice.setText("¥" + item.getAllFree());
                binding2.tvSignStatus.setText("报名成功");

                break;
            case 4:
                ItemMyRegistrationWithDrawBinding binding3 = DataBindingUtil.bind(helper.getConvertView());
                binding3.tvTitle.setText("" + item.getMatchTitle());
                binding3.tvPrice.setText("¥" + item.getAllFree());
                if (item.getAllFree() == 0) {
                    binding3.tvPayStatus.setText("退款中");
                    binding3.tvPayStatus.setVisibility(View.INVISIBLE);
                } else {
                    binding3.tvPayStatus.setText("退款中");
                    binding3.tvPayStatus.setVisibility(View.VISIBLE);

                }
                String matchTime2 = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getCreateTime());
                binding3.tvTime.setText("报名截止时间：" + matchTime2);
                helper.addOnClickListener(R.id.tv_tuisai_detail);

                break;
            case 5:
                ItemMyRegistrationWithDrawBinding binding4 = DataBindingUtil.bind(helper.getConvertView());

                binding4.tvTitle.setText("" + item.getMatchTitle());
                binding4.tvPrice.setText("¥" + item.getAllFree());
                binding4.tvPayStatus.setText("退款成功");
                String matchTime3 = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", item.getCreateTime());
                binding4.tvTime.setText("报名截止时间：" + matchTime3);
                helper.addOnClickListener(R.id.tv_tuisai_detail);
                break;


        }
    }
}
