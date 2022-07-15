package heyong.intellectPinPang.adapter.live;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyPublishBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.databinding.ItemMyPublishCompleteBinding;
import heyong.intellectPinPang.databinding.ItemMyPublishPayBinding;
import heyong.intellectPinPang.databinding.ItemMyPublishReunfdBinding;
import heyong.intellectPinPang.databinding.ItemMyPublishUnpayBinding;
import heyong.intellectPinPang.event.CityEvent;
import heyong.intellectPinPang.event.LiveOrderEvent;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.utils.TimeTools;

/**
 * 我发布的适配器
 */
public class MyPublishAdapter extends BaseQuickAdapter<LiveUserOrderListBean.ListBean, MyPublishAdapter.ProductViewHolder> {
    /**
     * 已支付   开始观看  取消订单  按钮
     * 已完成  （1 未确认 申请退款 观看回放 确认订单） （2 已确认 观看回放 评价订单（查看评价） （3 观看回放））
     * 退款 （1 退款中  退款详情） （2 退款成功 退款详情） （3 退款失败 观看回放 确认订单 退款详情）
     */

    //获取当前时间戳
    long timeStamp = System.currentTimeMillis();
    private int orderType = 1;
    private int width;
    private SparseArray<CountDownTimer> timerArray;

    public void setOrderType(int orderType) {
        this.orderType = orderType;
        notifyDataSetChanged();
    }

    public MyPublishAdapter() {

        super(0);
        MultiTypeDelegate<LiveUserOrderListBean.ListBean> multiTypeDelegate =
                new MultiTypeDelegate<LiveUserOrderListBean.ListBean>() {
                    @Override
                    protected int getItemType(LiveUserOrderListBean.ListBean o) {
//                Log.e(TAG, "getItemType: type==="+o.getType());
                        Log.e(TAG, "getItemType: " + o.getShowOwnerStatus());
                        return o.getShowOwnerStatus();
                    }
                };
        multiTypeDelegate.registerItemType(0, R.layout.item_my_publish_unpay);
        multiTypeDelegate.registerItemType(2, R.layout.item_my_publish_pay);//已支付
        multiTypeDelegate.registerItemType(3, R.layout.item_my_publish_complete);//已完成
        multiTypeDelegate.registerItemType(4, R.layout.item_my_publish_reunfd);//退款
        setMultiTypeDelegate(multiTypeDelegate);
//        startTime(getData());
        if (timerArray == null) {
            timerArray = new SparseArray<>();
        }

    }

    @Override
    protected void convert(ProductViewHolder helper, LiveUserOrderListBean.ListBean item) {
        List<LiveUserOrderListBean.ListBean.LeftBean> left = item.getLeft();
        List<LiveUserOrderListBean.ListBean.RightBean> right = item.getRight();
        int payStatus = item.getPayStatus();
        int receiveStatus = item.getReceiveStatus();
        int isAppraise = item.getIsAppraise();

//        setTimeShow(helper);
        switch (helper.getItemViewType()) {
            case 0:
                ItemMyPublishUnpayBinding binding0 = DataBindingUtil.bind(helper.getConvertView());
                binding0.tvMatchTitle.setText("" + item.getMatchName());
                binding0.tvPayStatus.setText("");

                if (left.size() == 1) {
                    binding0.llVisibleContentLeft.setVisibility(View.GONE);
                    binding0.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(binding0.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding0.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding0.tvLeftTwoName.setText("" + left.get(0).getName());
                    binding0.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    binding0.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    binding0.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(binding0.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding0.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding0.tvLeftNameOne.setText("" + left.get(0).getName());
                    binding0.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(binding0.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding0.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding0.tvRightOneName.setText("" + right.get(0).getName());
                    binding0.tvRightTwoName.setText("" + right.get(1).getName());
                }
                binding0.tvGroupTitle.setText("" + item.getTitle());

                binding0.tvAddress.setText("" + item.getAddress());
                binding0.tvTime.setText("" + item.getGameTime());
                binding0.tvTableNum.setText("" + item.getTableNum() + "台");
                binding0.tvMoney.setText("¥" + item.getMoney());
                helper.addOnClickListener(R.id.tv_delete_order_un_pay);//删除订单
                helper.addOnClickListener(R.id.tv_to_pay_un_pay);//去支付
                binding0.tvDeleteOrderUnPay.setVisibility(View.GONE);
                binding0.tvToPayUnPay.setVisibility(View.GONE);
                break;
            case 1://未支付   倒计时
                ItemMyPublishUnpayBinding binding = DataBindingUtil.bind(helper.getConvertView());
                binding.tvMatchTitle.setText("" + item.getMatchName());
                binding.tvPayStatus.setText("未支付");

                if (left.size() == 1) {
                    binding.llVisibleContentLeft.setVisibility(View.GONE);
                    binding.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(binding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding.tvLeftTwoName.setText("" + left.get(0).getName());
                    binding.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    binding.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    binding.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(binding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding.tvLeftNameOne.setText("" + left.get(0).getName());
                    binding.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(binding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    binding.tvRightOneName.setText("" + right.get(0).getName());
                    binding.tvRightTwoName.setText("" + right.get(1).getName());
                }
                binding.tvGroupTitle.setText("" + item.getTitle());

                binding.tvAddress.setText("" + item.getAddress());
                binding.tvTime.setText("" + item.getGameTime());
                binding.tvTableNum.setText("" + item.getTableNum() + "台");
                binding.tvMoney.setText("¥" + item.getMoney());
                if (payStatus == -1) {
                    try {
                        long time = 0;
//                        String longCountDouwnTimes = MyDateUtils.dataDayHourMinute(item.getCreateTime());
                        /*倒计时的时间大于当前的时间*/
//                        long l = Long.parseLong(longCountDouwnTimes);
                        long l = item.getCloseSec();
                        if (l > timeStamp) {
                            time = l - timeStamp;
                        } else {
                            /*倒计时的时间小于当前时间*/
                            binding.tvContent.setText("订单已取消");
                        }
                        if (time != 0) {
                            if (helper.countDownTimer != null) {
                                helper.countDownTimer.cancel();
                            }
                            helper.countDownTimer = new CountDownTimer(time, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    TimeTools.getCountTimeByLong2(millisUntilFinished, new TimeTools.CallBack() {
                                        @Override
                                        public void back(String day, String hour, String minute, String second) {
                                            binding.tvContent.setText("" + minute + ":" + second + "后订单取消");
                                            binding.tvContent.setTextColor(Color.parseColor("#CE2626"));
                                            binding.tvDeleteOrderUnPay.setVisibility(View.GONE);
                                            binding.tvToPayUnPay.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }

                                @Override
                                public void onFinish() {
                                    binding.tvContent.setText("订单已取消");
                                    binding.tvContent.setTextColor(Color.parseColor("#999999"));
                                    binding.tvDeleteOrderUnPay.setVisibility(View.VISIBLE);
                                    binding.tvToPayUnPay.setVisibility(View.GONE);
                                    EventBus.getDefault().post(new LiveOrderEvent());
                                }
                            }.start();
                            timerArray.put(helper.countDownTimer.hashCode(), helper.countDownTimer);
                        } else {
                            binding.tvContent.setText("订单已取消");
                            binding.tvContent.setTextColor(Color.parseColor("#999999"));
                            binding.tvDeleteOrderUnPay.setVisibility(View.VISIBLE);
                            binding.tvToPayUnPay.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        binding.tvContent.setText("5分钟后订单自动取消");
                        binding.tvContent.setTextColor(Color.parseColor("#CE2626"));
                        binding.tvDeleteOrderUnPay.setVisibility(View.GONE);
                        binding.tvToPayUnPay.setVisibility(View.VISIBLE);
                    }

                } else if (payStatus == 5) {
                    binding.tvContent.setText("订单已取消");
                    binding.tvContent.setTextColor(Color.parseColor("#999999"));
                    binding.tvDeleteOrderUnPay.setVisibility(View.VISIBLE);
                    binding.tvToPayUnPay.setVisibility(View.GONE);
                }
                helper.addOnClickListener(R.id.tv_delete_order_un_pay);//删除订单
                helper.addOnClickListener(R.id.tv_to_pay_un_pay);//去支付

                break;
            case 2://已支付   订单状态需要区分未接取 和已接取
                ItemMyPublishPayBinding payBinding = DataBindingUtil.bind(helper.getConvertView());
                payBinding.tvMatchTitle.setText("" + item.getMatchName());
                if (left.size() == 1) {
                    payBinding.llVisibleContentLeft.setVisibility(View.GONE);
                    payBinding.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(payBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(payBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    payBinding.tvLeftTwoName.setText("" + left.get(0).getName());
                    payBinding.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    payBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    payBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(payBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(payBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    payBinding.tvLeftNameOne.setText("" + left.get(0).getName());
                    payBinding.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(payBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(payBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    payBinding.tvRightOneName.setText("" + right.get(0).getName());
                    payBinding.tvRightTwoName.setText("" + right.get(1).getName());
                }
//                if (payStatus == 0 && receiveStatus == -1) {
//                    payBinding.tvPayStatus.setText("未接取");
//                    payBinding.tvLiveStatus.setText("未接取-直播未开始");
//                    payBinding.tvStartLivePublish.setVisibility(View.GONE);
//                    payBinding.tvCancelOrderPublish.setVisibility(View.VISIBLE);
//                    helper.addOnClickListener(R.id.tv_cancel_order_publish);
//                } else if (payStatus == 1 && receiveStatus == 3) {
//                    payBinding.tvPayStatus.setText("已接取");
//                    payBinding.tvLiveStatus.setText("直播进行中");
//                    payBinding.tvStartLivePublish.setVisibility(View.VISIBLE);
//                    payBinding.tvCancelOrderPublish.setVisibility(View.GONE);
//                    helper.addOnClickListener(R.id.tv_start_live_publish);
//                } else if (payStatus == 1 && receiveStatus == 1) {
//                    payBinding.tvPayStatus.setText("已接取");
//                    payBinding.tvLiveStatus.setText("已接取-直播未开始");
//                    payBinding.tvStartLivePublish.setVisibility(View.VISIBLE);
//                    payBinding.tvCancelOrderPublish.setVisibility(View.GONE);
//                    helper.addOnClickListener(R.id.tv_start_live_publish);
//                }
//                payBinding.tvGroupTitle.setText("" + item.getTitle());
//                payBinding.tvAddress.setText("" + item.getAddress());
//                payBinding.tvTime.setText("" + item.getGameTime());
//                payBinding.tvTableNum.setText("" + item.getTableNum() + "台");
//                payBinding.tvMoney.setText("¥" + item.getMoney());
                break;
            case 3://已完成
                ItemMyPublishCompleteBinding completeBinding = DataBindingUtil.bind(helper.getConvertView());
                completeBinding.tvMatchTitle.setText("" + item.getMatchName());
                if (left.size() == 1) {
                    completeBinding.llVisibleContentLeft.setVisibility(View.GONE);
                    completeBinding.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(completeBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(completeBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    completeBinding.tvLeftTwoName.setText("" + left.get(0).getName());
                    completeBinding.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    completeBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    completeBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(completeBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(completeBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    completeBinding.tvLeftNameOne.setText("" + left.get(0).getName());
                    completeBinding.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(completeBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(completeBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    completeBinding.tvRightOneName.setText("" + right.get(0).getName());
                    completeBinding.tvRightTwoName.setText("" + right.get(1).getName());
                }
                completeBinding.tvGroupTitle.setText("" + item.getTitle());
                completeBinding.tvAddress.setText("" + item.getAddress());
                completeBinding.tvTime.setText("" + item.getGameTime());
                completeBinding.tvTableNum.setText("" + item.getTableNum() + "台");
                completeBinding.tvMoney.setText("¥" + item.getMoney());
//                if (payStatus == 4) {
//                    if (isAppraise == 1) {
//                        //是  未评价  显示 申请退款  观看回放    评价直播
//                        /*判断是不是自己的单*/
//
//                        completeBinding.tvApplyRefundComplete.setVisibility(View.GONE);
//                        helper.addOnClickListener(R.id.tv_start_live_complete);
//                        helper.addOnClickListener(R.id.tv_see_comment);
//                        completeBinding.tvCommentLiveComplete.setVisibility(View.GONE);
//                        completeBinding.tvSeeComment.setVisibility(View.VISIBLE);
//                        completeBinding.tvLiveStatus.setText("已评价");
//
//                    } else {
//                        if (item.getLiveType() == 2) {
//                            completeBinding.tvApplyRefundComplete.setVisibility(View.GONE);
//
//                        } else {
//                            completeBinding.tvApplyRefundComplete.setVisibility(View.VISIBLE);
//                            helper.addOnClickListener(R.id.tv_apply_refund_complete);
//
//                        }
//                        //否
//                        completeBinding.tvSeeComment.setVisibility(View.GONE);
//                        completeBinding.tvLiveStatus.setText("待评价-3日后自动评价");
//                        helper.addOnClickListener(R.id.tv_start_live_complete);
//                        helper.addOnClickListener(R.id.tv_comment_live_complete);
//
//                    }
//                }
//                int liveType = item.getLiveType();
//                if (liveType == 1) {//公开  tv_see_is_my_self
//                    completeBinding.tvSeeIsMySelf.setVisibility(View.GONE);
//                } else {
//                    completeBinding.tvSeeIsMySelf.setVisibility(View.VISIBLE);
//
//                }

                break;
            case 4://退款

                ItemMyPublishReunfdBinding refundBinding = DataBindingUtil.bind(helper.getConvertView());
                refundBinding.tvMatchTitle.setText("" + item.getMatchName());
                if (left.size() == 1) {
                    refundBinding.llVisibleContentLeft.setVisibility(View.GONE);
                    refundBinding.llRightVisibleContent.setVisibility(View.GONE);
                    ImageLoader.loadImage(refundBinding.ivLeftTwoLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(refundBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    refundBinding.tvLeftTwoName.setText("" + left.get(0).getName());
                    refundBinding.tvRightOneName.setText("" + right.get(0).getName());
                } else if (left.size() == 2) {
                    refundBinding.llVisibleContentLeft.setVisibility(View.VISIBLE);
                    refundBinding.llRightVisibleContent.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(refundBinding.ivLeftOneLogo, left.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(refundBinding.ivLeftTwoLogo, left.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    refundBinding.tvLeftNameOne.setText("" + left.get(0).getName());
                    refundBinding.tvLeftTwoName.setText("" + left.get(1).getName());
                    ImageLoader.loadImage(refundBinding.ivRightOneLogo, right.get(0).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(refundBinding.ivRightTwoLogo, right.get(1).getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    refundBinding.tvRightOneName.setText("" + right.get(0).getName());
                    refundBinding.tvRightTwoName.setText("" + right.get(1).getName());
                }

                refundBinding.tvGroupTitle.setText("" + item.getTitle());
                refundBinding.tvAddress.setText("" + item.getAddress());
                refundBinding.tvTime.setText("" + item.getGameTime());
                refundBinding.tvTableNum.setText("" + item.getTableNum() + "台");
                refundBinding.tvMoney.setText("¥" + item.getMoney());
//
//                if (payStatus == 3) {
//                    refundBinding.tvRefundStatus.setText("退款成功");
//                    refundBinding.tvLiveStatus.setText("退款成功");
//                    //退款成功  没有 回放按钮
//                    if (receiveStatus == -1) {
//                        //订单无人接取===退款成功
////                        tv_watch_huifang_refund
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.VISIBLE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.GONE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        helper.addOnClickListener(R.id.tv_delete_order_refund);
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                    } else if (receiveStatus == 4)//结束
//                    {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.VISIBLE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.VISIBLE);//观看回放
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        helper.addOnClickListener(R.id.tv_delete_order_refund);
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                        helper.addOnClickListener(R.id.tv_watch_huifang_refund);
//                    } else {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.GONE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.GONE);//退款详情
//                    }
//                } else if (payStatus == 6) {//退款失败
//                    refundBinding.tvRefundStatus.setText("退款失败");
//                    refundBinding.tvLiveStatus.setText("退款失败");
//                    //退款失败   不显示
//                    if (isAppraise == 0)//观看回放  评价订单  退款详情
//                    {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.VISIBLE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.VISIBLE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                        helper.addOnClickListener(R.id.tv_refund_envalite_order);
//                        helper.addOnClickListener(R.id.tv_watch_huifang_refund);
//                    } else {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.VISIBLE);//观看回放
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                        helper.addOnClickListener(R.id.tv_watch_huifang_refund);
//                    }
//                } else if (payStatus == 2) {
//                    //退款中
//                    if (receiveStatus == -1) {
//                        //未接单
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.GONE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                        refundBinding.tvRefundStatus.setText("退款中");
//                        refundBinding.tvLiveStatus.setText("退款中");
//                    } else if (receiveStatus == 4) {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.VISIBLE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.VISIBLE);//退款详情
//                        helper.addOnClickListener(R.id.tv_refund_detail);
//                        helper.addOnClickListener(R.id.tv_watch_huifang_refund);
//                        refundBinding.tvRefundStatus.setText("退款中");
//                        refundBinding.tvLiveStatus.setText("直播过程不完整");
//                    } else {
//                        refundBinding.tvDeleteOrderRefund.setVisibility(View.GONE);//删除订单
//                        refundBinding.tvWatchHuifangRefund.setVisibility(View.GONE);//观看回放
//                        refundBinding.tvRefundEnvaliteOrder.setVisibility(View.GONE);//评价订单
//                        refundBinding.tvRefundDetail.setVisibility(View.GONE);//退款详情
//                    }
//                }
                break;
        }
    }


    public static class ProductViewHolder extends BaseViewHolder {
        private CountDownTimer countDownTimer;

        public ProductViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }

    }
}
