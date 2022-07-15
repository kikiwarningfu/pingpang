package heyong.intellectPinPang.ui.competition.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.Constants;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.QuerySubmitBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.databinding.ActivityDrawForHostAndVistorsBinding;
import heyong.intellectPinPang.model.RequestService;
import heyong.intellectPinPang.ui.club.activity.CoachFillMatchFormActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsOfficialActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 裁判员 主客队抽签页面
 */
public class DrawForHostAndVistors extends BaseActivity<ActivityDrawForHostAndVistorsBinding, HomePageViewModel> implements View.OnClickListener {
    ArrangeDrawDataBean arrangeDrawBean;
    ArrangeDrawDataBean data;
    CountDownTimer fillMatchTimer;
    private int lunTimes = 0;
    private long arrangeIds;
    MatchBaseInfoBean matchBaseInfoBean;
    private String cliclkPosition = "";


    @Override
    protected void onResume() {
        super.onResume();
        if (fillMatchTimer != null) {
            mViewModel.querySubmit("" + data.getArrangeId(),
                    "" + data.getLeft1Name(), "" + data.getRight1Name());
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_draw_for_host_and_vistors;
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        cliclkPosition = getIntent().getStringExtra("cliclkPosition");
        arrangeDrawBean = (ArrangeDrawDataBean) getIntent().getSerializableExtra("data");
        mBinding.llLeftCommit.setEnabled(false);
        mBinding.llRightCommit.setEnabled(false);
        if (arrangeDrawBean != null) {
            ImageLoader.loadImage(mBinding.ivLeftLogo, arrangeDrawBean.getLeft1Img(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
            ImageLoader.loadImage(mBinding.ivRightLogo, arrangeDrawBean.getRight1Img(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
            mBinding.tvLeftName.setText("" + arrangeDrawBean.getLeft1Name());
            mBinding.tvRightName.setText("" + arrangeDrawBean.getRight1Name());
            mBinding.tvTitleName.setText(""+arrangeDrawBean.getTitle());
            showLoading();
            mViewModel.arrangeDraw(arrangeDrawBean);
        }
        mViewModel.arrangeDrawLiveData.observe(this, new Observer<ResponseBean<ArrangeDrawDataBean>>() {
            @Override
            public void onChanged(ResponseBean<ArrangeDrawDataBean> arrangeDrawDataBeanResponseBean) {
                dismissLoading();
                if (arrangeDrawDataBeanResponseBean.getErrorCode().equals("200")) {
                    data = arrangeDrawDataBeanResponseBean.getData();
                    arrangeIds = data.getArrangeId();


                    if (data != null) {

                    } else {
                        toast("" + arrangeDrawDataBeanResponseBean.getMessage());
                    }

                } else {
                    toast("" + arrangeDrawDataBeanResponseBean.getMessage());
                }
            }
        });

        mViewModel.sendMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    //掉接口
                    showSurprise();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.querySubmitLiveData.observe(this, new Observer<ResponseBean<QuerySubmitBean>>() {
            @Override
            public void onChanged(ResponseBean<QuerySubmitBean> querySubmitBeanResponseBean) {
                if (querySubmitBeanResponseBean.getErrorCode().equals("200")) {
                    QuerySubmitBean data = querySubmitBeanResponseBean.getData();
                    if (data != null) {
                        int code = Integer.parseInt(data.getCode());
                        dealData(code);
                    } else {
                        toast("" + querySubmitBeanResponseBean.getMessage());
                    }

                } else {
                    toast("" + querySubmitBeanResponseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                if (data != null) {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    ObjectrotationAnim(data.getAngle() + 360 * 8 + 180);
                } else {
                    toast("data  error");
                }
                break;
            case R.id.tv_send://开始比赛  跳转到团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent();
                intent.setClass(this, TeamResultsOfficialActivity.class);
                intent.putExtra("ids", "" + arrangeIds);
                intent.putExtra("cliclkPosition", "" + cliclkPosition);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_left_commit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchBaseInfoBean = new MatchBaseInfoBean("" + data.getArrangeId(), "" + data.getMatchId(), "" + data.getTitle(),
                        "" + data.getAngle(),
                        "" + data.getLeftClubId(), "" + data.getLeftId(), "" + data.getLeftType(),
                        "" + data.getLeft1Name(), "" + data.getLeft1Img(),
                        "" + data.getRightClubId(), "" + data.getRightId(), "" + data.getRightType(),
                        "" + data.getRight1Name(), "" + data.getRight1Img(),
                        "" + 1, "" + data.getAppLogId());
                CoachFillMatchFormActivity.goActivity(DrawForHostAndVistors.this, matchBaseInfoBean);
                break;
            case R.id.ll_right_commit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchBaseInfoBean = new MatchBaseInfoBean("" + data.getArrangeId(), "" + data.getMatchId(), "" + data.getTitle(),
                        "" + data.getAngle(),
                        "" + data.getLeftClubId(), "" + data.getLeftId(), "" + data.getLeftType(),
                        "" + data.getLeft1Name(), "" + data.getLeft1Img(),
                        "" + data.getRightClubId(), "" + data.getRightId(), "" + data.getRightType(),
                        "" + data.getRight1Name(), "" + data.getRight1Img(),
                        "" + 2, "" + data.getAppLogId());
                CoachFillMatchFormActivity.goActivity(DrawForHostAndVistors.this, matchBaseInfoBean);

                break;
        }
    }


    //    开始轮训  lunxun
    public void getData() {
        Log.e(TAG, "getData: 请求数据");
        // 步骤1：创建Retrofit对象
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "" + AccountHelper.getToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("" + Constants.allUrl) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(client)
                .build();

        // 步骤2：创建 网络请求接口 的实例
        RequestService request = retrofit.create(RequestService.class);

        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + data.getArrangeId());
        postBean.setLeft1Name("" + data.getLeft1Name());
        postBean.setRight1Name("" + data.getRight1Name());

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<ResponseBean<QuerySubmitBean>> observable = request.querySubmit(requestBody);

        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (lunTimes > 60) {
                            Log.e(TAG, "apply: " + lunTimes);
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                    }
                });

            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new io.reactivex.Observer<ResponseBean<QuerySubmitBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBean<QuerySubmitBean> result) {
                        // e.接收服务器返回的数据
                        Log.e(TAG, "onNext: " + result.toString() + " hahahah" + new Gson().toJson(result));
                        //轮训结束 设置 ==120
                        lunTimes++;
                        Log.e(TAG, "onNext: lunTimes" + lunTimes);
                        if (result.getErrorCode().equals("200")) {
                            QuerySubmitBean data = result.getData();
                            if (data != null) {
                                int code = Integer.parseInt(data.getCode());
                                dealWithData(code);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void dealWithData(int code) {
        if (code == 7) {
            //左边
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("已提交");
            mBinding.tvRightCommit.setText("等待提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(false);
            mBinding.llRightCommit.setEnabled(true);
        } else if (code == 8) {
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("等待提交");
            mBinding.tvRightCommit.setText("已提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(true);
            mBinding.llRightCommit.setEnabled(false);
        } else if (code == 9) {//两边都提交
            mBinding.tvTopTitle.setText("双方教练员已经提交对阵");
            mBinding.tvCountdownTimer.setText("名单，请开始比赛");
            mBinding.tvLeftCommit.setText("已提交");
            mBinding.tvRightCommit.setText("已提交");
            mBinding.tvSend.setBackgroundResource(R.drawable.shape_club_blue);
            mBinding.tvSend.setEnabled(true);
            mBinding.llLeftCommit.setEnabled(false);
            mBinding.tvRightCommit.setEnabled(false);
            lunTimes = 60;
            if (fillMatchTimer != null) {
                fillMatchTimer.cancel();
            }

        } else {
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("等待提交");
            mBinding.tvRightCommit.setText("等待提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(true);
            mBinding.tvRightCommit.setEnabled(true);
        }
    }

    private void dealData(int code) {
        if (code == 7) {
            //左边
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("已提交");
            mBinding.tvRightCommit.setText("等待提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(false);
            mBinding.llRightCommit.setEnabled(true);
        } else if (code == 8) {
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("等待提交");
            mBinding.tvRightCommit.setText("已提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(true);
            mBinding.llRightCommit.setEnabled(false);
        } else if (code == 9) {//两边都提交
            mBinding.tvTopTitle.setText("双方教练员已经提交对阵");
            mBinding.tvCountdownTimer.setText("名单，请开始比赛");
            mBinding.tvLeftCommit.setText("已提交");
            mBinding.tvRightCommit.setText("已提交");
            mBinding.tvSend.setBackgroundResource(R.drawable.shape_club_blue);
            mBinding.llLeftCommit.setEnabled(false);
            mBinding.llRightCommit.setEnabled(false);

            mBinding.tvSend.setEnabled(true);
            lunTimes = 60;
            if (fillMatchTimer != null) {
                fillMatchTimer.cancel();
            }

        } else {
            mBinding.tvTopTitle.setText("教练员正在填写对阵表");
//            mBinding.tvCountdownTimer.setText("5:00");
            mBinding.tvLeftCommit.setText("等待提交");
            mBinding.tvRightCommit.setText("等待提交");
            mBinding.tvSend.setEnabled(false);
            mBinding.llLeftCommit.setEnabled(true);
            mBinding.llRightCommit.setEnabled(true);
        }
    }

    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle) {
        Float aFloat = Float.valueOf(String.valueOf(angle));
        //构造ObjectAnimator对象的方法
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.imgZhizhen, "rotation",
                0.0F, aFloat
        );//设置先顺时针360度旋转然后逆时针360度旋转动画
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);//设置旋转时间
        animator.start();//开始执行动画（顺时针旋转动画）
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.tvStart.setVisibility(View.GONE);
                String leftClubId = data.getLeftClubId();
                String rightClubId = data.getRightClubId();
                if (leftClubId.equals(rightClubId)) {
                    new MessageDialogBuilder(DrawForHostAndVistors.this)
                            .setMessage("")
                            .setTvTitle("请裁判员填写对阵名单？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBinding.tvLeftClubName.setText("" + data.getLeft1Name());
                                    mBinding.tvRightClubName.setText("" + data.getRight1Name());
//
                                    mBinding.llLeftCommit.setEnabled(true);
                                    mBinding.llRightCommit.setEnabled(true);
                                    mBinding.tvLeftCommit.setVisibility(View.VISIBLE);
                                    mBinding.tvLeftCommit.setText("等待提交");
                                    mBinding.tvRightCommit.setText("等待提交");
                                    mBinding.tvTopTitle.setText("教练员正在填写对阵表");
                                    mBinding.tvCountdownTimer.setText("5:00");
                                    mBinding.tvSend.setEnabled(false);
                                    mBinding.tvSend.setBackgroundResource(R.drawable.shape_gray_corners_solid);
                                    mBinding.tvSend.setTextColor(Color.parseColor("#ffffff"));
                                    mBinding.tvRightCommit.setVisibility(View.VISIBLE);
//                    mBinding.llLeftCommit.setEnabled(false);
//                    mBinding.llRightCommit.setEnabled(false);
                                    //开始调用接口  轮训 去请求
                                    fillMatchTimer = new CountDownTimer(5 * 60 * 1000 - 1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            {
//                            Log.e(TAG, "onTick: test==="+MyDateUtils.getHourMinute(10 * 60 * 1000 / 1000) );
//                            Log.e(TAG, "onTick: 倒计时"+ MyDateUtils.getHourMinute(millisUntilFinished / 1000));
                                                DecimalFormat dec = new DecimalFormat("##.##");
                                                int floor = (int) Math.floor(millisUntilFinished / 60000);
                                                if (floor == 10) {
                                                    Log.e(TAG, "onClick: ===" + "10" + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
                                                    mBinding.tvCountdownTimer.setText("" + "10" + ":" + dec.format((millisUntilFinished % 60000) / 1000));
                                                } else {
//                                    Log.e(TAG, "onClick: ===" + "0" + floor + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
                                                    mBinding.tvCountdownTimer.setText("" + timeParse(millisUntilFinished));
//                                    mBinding.tvCountdownTimer.setText("" + "0" + floor + ":" + dec.format((millisUntilFinished % 60000) / 1000));

                                                }
                                            }
                                        }

                                        @Override
                                        public void onFinish() {
                                            mBinding.tvCountdownTimer.setText("00:00");
                                            //去请求接口
                                            if (data != null) {
                                                mViewModel.querySubmit("" + data.getArrangeId(),
                                                        "" + data.getLeft1Name(), "" + data.getRight1Name());
                                            }

                                        }
                                    }.start();
                                    if (data != null) {
                                        Log.e(TAG, "onClick: 开始轮训");
                                        getData();
                                    }
                                }
                            })
                            .show();
                } else {
                    showLoading();
                    mViewModel.pushMessageToCoachAndroid("" + data.getArrangeId());
                }
            }
        });
    }

    public void showSurprise() {
        CustomDialog customDialogConfirm = new CustomDialog(DrawForHostAndVistors.this);
        customDialogConfirm.setCustomView(R.layout.pop_send_two_coach);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }


    private void showAskConfirmView(View customView, CustomDialog customDialog) {

        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        //判断是否为1
//        if (Integer.parseInt(data.getLeftType()) == 1) {
        tvLeftName.setText("主：" + data.getLeft1Name());
        tvRightName.setText("客：" + data.getRight1Name());
//        } else {
//            tvLeftName.setText("主：" + data.getRight1Name());
//            tvRightName.setText("客：" + data.getLeft1Name());
//        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                    int leftType = Integer.parseInt(data.getLeftType());

                    mBinding.tvLeftClubName.setText("" + data.getLeft1Name());
                    mBinding.tvRightClubName.setText("" + data.getRight1Name());
//
                    mBinding.tvLeftCommit.setVisibility(View.VISIBLE);
                    mBinding.tvLeftCommit.setText("等待提交");
                    mBinding.tvRightCommit.setText("等待提交");
                    mBinding.tvTopTitle.setText("教练员正在填写对阵表");
                    mBinding.llLeftCommit.setEnabled(true);
                    mBinding.llRightCommit.setEnabled(true);
                    mBinding.tvCountdownTimer.setText("5:00");
                    mBinding.tvSend.setEnabled(false);
                    mBinding.tvSend.setBackgroundResource(R.drawable.shape_gray_corners_solid);
                    mBinding.tvSend.setTextColor(Color.parseColor("#ffffff"));
                    mBinding.tvRightCommit.setVisibility(View.VISIBLE);
//                    mBinding.llLeftCommit.setEnabled(false);
//                    mBinding.llRightCommit.setEnabled(false);
                    //开始调用接口  轮训 去请求
                    fillMatchTimer = new CountDownTimer(5 * 60 * 1000 - 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            {
//                            Log.e(TAG, "onTick: test==="+MyDateUtils.getHourMinute(10 * 60 * 1000 / 1000) );
//                            Log.e(TAG, "onTick: 倒计时"+ MyDateUtils.getHourMinute(millisUntilFinished / 1000));
                                DecimalFormat dec = new DecimalFormat("##.##");
                                int floor = (int) Math.floor(millisUntilFinished / 60000);
                                if (floor == 10) {
                                    Log.e(TAG, "onClick: ===" + "10" + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
                                    mBinding.tvCountdownTimer.setText("" + "10" + ":" + dec.format((millisUntilFinished % 60000) / 1000));
                                } else {
//                                    Log.e(TAG, "onClick: ===" + "0" + floor + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
                                    mBinding.tvCountdownTimer.setText("" + timeParse(millisUntilFinished));
//                                    mBinding.tvCountdownTimer.setText("" + "0" + floor + ":" + dec.format((millisUntilFinished % 60000) / 1000));

                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            mBinding.tvCountdownTimer.setText("00:00");
                            //去请求接口
                            if (data != null) {
                                mViewModel.querySubmit("" + data.getArrangeId(),
                                        "" + data.getLeft1Name(), "" + data.getRight1Name());
                            }


                        }
                    }.start();
                    if (data != null) {
                        Log.e(TAG, "onClick: 开始轮训");
                        getData();
                    }

                }

            }
        });


    }

    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lunTimes = 60;
        Log.e(TAG, "onBackPressed: ");
        if (fillMatchTimer != null) {

            fillMatchTimer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        lunTimes = 60;
        if (fillMatchTimer != null) {
            fillMatchTimer.cancel();
        }
    }
}