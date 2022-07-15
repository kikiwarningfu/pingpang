package heyong.intellectPinPang.adapter.live;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyPublishBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.databinding.ItemMyAcceptCompleteBinding;
import heyong.intellectPinPang.databinding.ItemMyAcceptNotStartBinding;
import heyong.intellectPinPang.databinding.ItemMyAcceptUnfinishedBinding;
import heyong.intellectPinPang.databinding.ItemMyAcceptWithDrawBinding;

/**
 * 我接取的
 */
public class LiveAcceptAdapter extends BaseQuickAdapter<LiveUserOrderListBean.ListBean, BaseViewHolder> {
    /**
     * 1 全部
     * 待直播  开始直播  放弃悬赏
     * 已完成
     * 未完成
     * 已提现
     */
    private int orderType = 1;

    public void setOrderType(int orderType) {
        this.orderType = orderType;
        notifyDataSetChanged();
    }

    public LiveAcceptAdapter() {

        super(0);
        MultiTypeDelegate<LiveUserOrderListBean.ListBean> multiTypeDelegate = new MultiTypeDelegate<LiveUserOrderListBean.ListBean>() {
            @Override
            protected int getItemType(LiveUserOrderListBean.ListBean o) {
                return o.getShowOwnerStatus();
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.item_my_accept_not_start);//待直播
        multiTypeDelegate.registerItemType(1, R.layout.item_my_accept_not_start);//待直播
        multiTypeDelegate.registerItemType(2, R.layout.item_my_accept_unfinished);//未完成

        multiTypeDelegate.registerItemType(3, R.layout.item_my_accept_complete);//已完成
        multiTypeDelegate.registerItemType(4, R.layout.item_my_accept_with_draw);//已提现
        multiTypeDelegate.registerItemType(5, R.layout.item_my_accept_unusual_order);//异常订单
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveUserOrderListBean.ListBean item) {
        List<LiveUserOrderListBean.ListBean.LeftBean> left = item.getLeft();
        List<LiveUserOrderListBean.ListBean.RightBean> right = item.getRight();
        int payStatus = item.getPayStatus();
        int receiveStatus = item.getReceiveStatus();
        int isAppraise = item.getIsAppraise();
        int isWithDraw = item.getIsWithDraw();
        switch (helper.getItemViewType()) {
            case 0:
                ItemMyAcceptNotStartBinding notStartBinding1 = DataBindingUtil.bind(helper.getConvertView());
                notStartBinding1.tvMatchTitle.setText("" + item.getMatchName());
//                notStartBinding1.tvPayStatus.setText("待直播");

                if (left.size() == 1) {
                    notStartBinding1.llVisibleContentLeft.setVisibility(View.GONE);
                    notStartBinding1.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(notStartBinding1.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding1.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding1.tvLeftTwoName.setText("" + left.get(0).getName());
                    notStartBinding1.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    notStartBinding1.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    notStartBinding1.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(notStartBinding1.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding1.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding1.tvLeftNameOne.setText("" + left.get(0).getName());
                    notStartBinding1.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(notStartBinding1.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding1.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding1.tvRightOneName.setText("" + right.get(0).getName());
                    notStartBinding1.tvRightTwoName.setText("" + right.get(1).getName());
                }
                notStartBinding1.tvGroupTitle.setText("" + item.getTitle());

                notStartBinding1.tvAddress.setText("" + item.getAddress());
                notStartBinding1.tvTime.setText("" + item.getGameTime());
                notStartBinding1.tvTableNum.setText("" + item.getTableNum() + "台");
//                helper.addOnClickListener(R.id.tv_give_up_no_start);//放弃悬赏
//                helper.addOnClickListener(R.id.tv_start_live_no_start);//开始直播
//                notStartBinding1.tvGiveUpNoStart.setVisibility(View.GONE);
//                notStartBinding1.tvStartLiveNoStart.setVisibility(View.GONE);
//                if (item.getLiveType() == 1) {
//                    //公开的
//                    notStartBinding1.tvLiveMySelf.setVisibility(View.GONE);
//                } else {
//                    notStartBinding1.tvLiveMySelf.setVisibility(View.VISIBLE);
//
//                }
                break;
            case 1://待直播
                ItemMyAcceptNotStartBinding notStartBinding = DataBindingUtil.bind(helper.getConvertView());
                notStartBinding.tvMatchTitle.setText("" + item.getMatchName());
//                notStartBinding.tvPayStatus.setText("待直播");

                if (left.size() == 1) {
                    notStartBinding.llVisibleContentLeft.setVisibility(View.GONE);
                    notStartBinding.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(notStartBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding.tvLeftTwoName.setText("" + left.get(0).getName());
                    notStartBinding.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    notStartBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    notStartBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(notStartBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding.tvLeftNameOne.setText("" + left.get(0).getName());
                    notStartBinding.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(notStartBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(notStartBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    notStartBinding.tvRightOneName.setText("" + right.get(0).getName());
                    notStartBinding.tvRightTwoName.setText("" + right.get(1).getName());
                }
                notStartBinding.tvGroupTitle.setText("" + item.getTitle());

                notStartBinding.tvAddress.setText("" + item.getAddress());
                notStartBinding.tvTime.setText("" + item.getGameTime());
                notStartBinding.tvTableNum.setText("" + item.getTableNum() + "台");
//                helper.addOnClickListener(R.id.tv_give_up_no_start);//放弃悬赏
//                helper.addOnClickListener(R.id.tv_start_live_no_start);//开始直播
//                if (item.getLiveType() == 1) {
//                    //公开的
//                    notStartBinding.tvLiveMySelf.setVisibility(View.GONE);
//                } else {
//                    notStartBinding.tvLiveMySelf.setVisibility(View.VISIBLE);
//
//                }

                break;
            case 2://已完成  //item_my_accept_complete
//                ItemMyAcceptCompleteBinding acceptCompleteBinding = DataBindingUtil.bind(helper.getConvertView());
//                int liveType = item.getLiveType();
//                if (liveType == 1) {
//                    //公开
//                    acceptCompleteBinding.tvSeeMySelf.setVisibility(View.GONE);
//                } else {
//                    acceptCompleteBinding.tvSeeMySelf.setVisibility(View.VISIBLE);
//                }
//                if (left.size() == 1) {
//                    acceptCompleteBinding.llVisibleContentLeft.setVisibility(View.GONE);
//                    acceptCompleteBinding.llRightVisibleContent.setVisibility(View.GONE);
//                    ImageLoader.loadImage(acceptCompleteBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptCompleteBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptCompleteBinding.tvLeftTwoName.setText("" + left.get(0).getName());
//                    acceptCompleteBinding.tvRightOneName.setText("" + right.get(0).getName());
//                } else if (left.size() == 2) {
//                    acceptCompleteBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
//                    acceptCompleteBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
//                    ImageLoader.loadImage(acceptCompleteBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptCompleteBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptCompleteBinding.tvLeftNameOne.setText("" + left.get(0).getName());
//                    acceptCompleteBinding.tvLeftTwoName.setText("" + left.get(1).getName());
//                    ImageLoader.loadImage(acceptCompleteBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptCompleteBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptCompleteBinding.tvRightOneName.setText("" + right.get(0).getName());
//                    acceptCompleteBinding.tvRightTwoName.setText("" + right.get(1).getName());
//                }
//                if (isAppraise == 1) {
//                    //已评价
//                    acceptCompleteBinding.tvSeeCommentAcceptComplete.setVisibility(View.VISIBLE);
//                    helper.addOnClickListener(R.id.tv_see_comment_accept_complete);
//                } else {
//                    //未评价
//                    acceptCompleteBinding.tvSeeCommentAcceptComplete.setVisibility(View.GONE);
//                }
//                helper.addOnClickListener(R.id.tv_see_huifang_complete);//观看回放
//                acceptCompleteBinding.tvPayStatus.setText("已完成");
//                acceptCompleteBinding.tvMatchTitle.setText("" + item.getMatchName());
//                acceptCompleteBinding.tvGroupTitle.setText("" + item.getTitle());
//                acceptCompleteBinding.tvAddress.setText("" + item.getAddress());
//                acceptCompleteBinding.tvTime.setText("" + item.getGameTime());
//                acceptCompleteBinding.tvTableNum.setText("" + item.getTableNum() + "台");
//                acceptCompleteBinding.tvMoney.setText("¥" + item.getMoney());
//
//                if (isWithDraw == 0) {//已完成
//
//                    /*还需要判断一个是否是自己的申请退款的*/
//                    if (liveType == 2) {
//                        //自己的单 显示申请退款   删除订单 退款详情 观看回放
//                        acceptCompleteBinding.tvContenet.setText("下单人申请退款成功");
//                        acceptCompleteBinding.tvDelteOrder.setVisibility(View.VISIBLE);
//                        helper.addOnClickListener(R.id.tv_delte_order);
//                        acceptCompleteBinding.tvRefundDetailComplete.setVisibility(View.VISIBLE);
//                        helper.addOnClickListener(R.id.tv_refund_detail_complete);
//                        acceptCompleteBinding.tvSeeHuifangComplete.setVisibility(View.VISIBLE);
//                        helper.addOnClickListener(R.id.tv_see_huifang_complete);
//                        acceptCompleteBinding.tvTixianMoney.setVisibility(View.GONE);
//                        acceptCompleteBinding.tvSeeCommentAcceptComplete.setVisibility(View.GONE);
//                    } else {
//                        //不能提现    显示查看评价 观看回放
//                        acceptCompleteBinding.tvContenet.setText("直播结束后三天可提现");
//                        acceptCompleteBinding.tvSeeCommentAcceptComplete.setVisibility(View.VISIBLE);
//                        acceptCompleteBinding.tvSeeHuifangComplete.setVisibility(View.VISIBLE);
//                        acceptCompleteBinding.tvDelteOrder.setVisibility(View.GONE);
//                        acceptCompleteBinding.tvRefundDetailComplete.setVisibility(View.GONE);
//                        acceptCompleteBinding.tvTixianMoney.setVisibility(View.GONE);
//                        helper.addOnClickListener(R.id.tv_see_comment_accept_complete);
//                        helper.addOnClickListener(R.id.tv_see_huifang_complete);
//                    }
//                } else {
//                    //能提现   查看评价 观看回放   提现赏金
//
//                    acceptCompleteBinding.tvContenet.setText("");
//                    acceptCompleteBinding.tvSeeCommentAcceptComplete.setVisibility(View.VISIBLE);
//                    acceptCompleteBinding.tvSeeHuifangComplete.setVisibility(View.VISIBLE);
//                    acceptCompleteBinding.tvDelteOrder.setVisibility(View.GONE);
//                    acceptCompleteBinding.tvRefundDetailComplete.setVisibility(View.GONE);
//                    acceptCompleteBinding.tvTixianMoney.setVisibility(View.VISIBLE);
//                    helper.addOnClickListener(R.id.tv_see_comment_accept_complete);
//                    helper.addOnClickListener(R.id.tv_see_huifang_complete);
//                    helper.addOnClickListener(R.id.tv_tixian_money);
//                }
                break;
            case 3://未完成  //item_my_accept_unfinished
//                ItemMyAcceptUnfinishedBinding unfinishedBinding = DataBindingUtil.bind(helper.getConvertView());
//
//                unfinishedBinding.tvMatchTitle.setText("" + item.getMatchName());
//
//                if (left.size() == 1) {
//                    unfinishedBinding.llVisibleContentLeft.setVisibility(View.GONE);
//                    unfinishedBinding.llRightVisibleContent.setVisibility(View.GONE);
//                    ImageLoader.loadImage(unfinishedBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(unfinishedBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    unfinishedBinding.tvLeftTwoName.setText("" + left.get(0).getName());
//                    unfinishedBinding.tvRightOneName.setText("" + right.get(0).getName());
//                } else if (left.size() == 2) {
//                    unfinishedBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
//                    unfinishedBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
//                    ImageLoader.loadImage(unfinishedBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(unfinishedBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    unfinishedBinding.tvLeftNameOne.setText("" + left.get(0).getName());
//                    unfinishedBinding.tvLeftTwoName.setText("" + left.get(1).getName());
//                    ImageLoader.loadImage(unfinishedBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(unfinishedBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    unfinishedBinding.tvRightOneName.setText("" + right.get(0).getName());
//                    unfinishedBinding.tvRightTwoName.setText("" + right.get(1).getName());
//                }
//                unfinishedBinding.tvGroupTitle.setText("" + item.getTitle());
//                unfinishedBinding.tvAddress.setText("" + item.getAddress());
//                unfinishedBinding.tvTime.setText("" + item.getGameTime());
//                unfinishedBinding.tvTableNum.setText("" + item.getTableNum() + "台");
//                unfinishedBinding.tvMoney.setText("¥" + item.getMoney());

                //未完成
//                unfinishedBinding.tvPayStatus.setText("未完成");
//                unfinishedBinding.tvLiveStatus.setText("已放弃订单");
//                unfinishedBinding.tvDeleteOrderUnfinish.setVisibility(View.VISIBLE);
//                unfinishedBinding.tvRefundDetailUnfinish.setVisibility(View.GONE);
//                unfinishedBinding.tvSeeHuifangUnfinished.setVisibility(View.GONE);
//                helper.addOnClickListener(R.id.tv_delete_order_unfinish);//删除订单

                break;
            case 4://已提现 //item_my_accept_with_draw
//                ItemMyAcceptWithDrawBinding acceptWithDrawBinding = DataBindingUtil.bind(helper.getConvertView());
//                acceptWithDrawBinding.tvMatchTitle.setText("" + item.getMatchName());
//
//                if (left.size() == 1) {
//                    acceptWithDrawBinding.llVisibleContentLeft.setVisibility(View.GONE);
//                    acceptWithDrawBinding.llRightVisibleContent.setVisibility(View.GONE);
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptWithDrawBinding.tvLeftTwoName.setText("" + left.get(0).getName());
//                    acceptWithDrawBinding.tvRightOneName.setText("" + right.get(0).getName());
//                } else if (left.size() == 2) {
//                    acceptWithDrawBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
//                    acceptWithDrawBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptWithDrawBinding.tvLeftNameOne.setText("" + left.get(0).getName());
//                    acceptWithDrawBinding.tvLeftTwoName.setText("" + left.get(1).getName());
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    ImageLoader.loadImage(acceptWithDrawBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                    acceptWithDrawBinding.tvRightOneName.setText("" + right.get(0).getName());
//                    acceptWithDrawBinding.tvRightTwoName.setText("" + right.get(1).getName());
//                }
//                acceptWithDrawBinding.tvGroupTitle.setText("" + item.getTitle());
//                acceptWithDrawBinding.tvAddress.setText("" + item.getAddress());
//                acceptWithDrawBinding.tvTime.setText("" + item.getGameTime());
//                acceptWithDrawBinding.tvTableNum.setText("" + item.getTableNum() + "台");
//                acceptWithDrawBinding.tvMoney.setText("¥" + item.getMoney());
//
//
//                if (payStatus == 4 && isWithDraw == 2) {//提现中
//                    acceptWithDrawBinding.tvLiveStatus.setText("提现中");
//                    acceptWithDrawBinding.tvPayStatus.setText("提现中");
//                    acceptWithDrawBinding.llWithDrawIng.setVisibility(View.VISIBLE);
//                    acceptWithDrawBinding.llWithDrawSuccess.setVisibility(View.GONE);
//                    acceptWithDrawBinding.llWithDrawSuccessFail.setVisibility(View.GONE);
//                    helper.addOnClickListener(R.id.tv_see_comment_with_draw_ing);//去看评价
//                    helper.addOnClickListener(R.id.tv_detail_with_draw_ing);//提现详情
//
//                } else if (payStatus == 4 && isWithDraw == 3) {//体现成功
//                    acceptWithDrawBinding.tvLiveStatus.setText("");
//                    acceptWithDrawBinding.tvPayStatus.setText("提现成功");
//                    acceptWithDrawBinding.llWithDrawIng.setVisibility(View.GONE);
//                    acceptWithDrawBinding.llWithDrawSuccess.setVisibility(View.VISIBLE);
//                    acceptWithDrawBinding.llWithDrawSuccessFail.setVisibility(View.GONE);
//                    helper.addOnClickListener(R.id.tv_delete_order_success);//删除订单
//                    helper.addOnClickListener(R.id.tv_see_commtent_success);//查看评价
//                    helper.addOnClickListener(R.id.tv_detail_success);//提现详情
//
//                } else if (payStatus == 4  && isWithDraw == 4) {//提现失败
//                    acceptWithDrawBinding.tvLiveStatus.setText("");
//                    acceptWithDrawBinding.tvPayStatus.setText("提现失败");
//                    acceptWithDrawBinding.llWithDrawIng.setVisibility(View.GONE);
//                    acceptWithDrawBinding.llWithDrawSuccess.setVisibility(View.GONE);
//                    acceptWithDrawBinding.llWithDrawSuccessFail.setVisibility(View.VISIBLE);
//                    helper.addOnClickListener(R.id.tv_see_with_draw_fail);//查看评价
//                    helper.addOnClickListener(R.id.tv_detail_with_draw_fail);//提现详情
//                    helper.addOnClickListener(R.id.tv_again_tixian_draw_fail);//再次提现
//                }
                break;
        }
    }


}
