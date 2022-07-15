package heyong.intellectPinPang.ui.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendCircleReplyDetailAdapter;
import heyong.intellectPinPang.bean.friend.MyReplyListBean;
import heyong.intellectPinPang.bean.friend.ReplyPageBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ActivityFriendCircleReplyDetailBinding;
import heyong.intellectPinPang.event.ClearShowPopKeyEvent;
import heyong.intellectPinPang.event.ShowPopKeyEvent;
import heyong.intellectPinPang.ui.friendcircle.fragment.EmotionMainFragment;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;
import heyong.intellectPinPang.utils.SpanStringUtils;

import static heyong.intellectPinPang.SampleApplicationLike.getContext;
import static heyong.intellectPinPang.utils.EmotionUtils.EMOTION_CLASSIC_TYPE;

/**
 * 消息正文
 */
public class FriendCircleReplyDetailActivity extends BaseActivity<ActivityFriendCircleReplyDetailBinding, PublishEditCircleViewModel>
        implements View.OnClickListener, EmotionMainFragment.FragmentInteraction, OnRefreshListener {
    public static final String MSG_ID = "comment_id";
    private String strCommentId = "";
    public static final String DY_NAID = "dy_naid";
    private String strDynaId = "";
    private EmotionMainFragment emotionMainFragment;

    FriendCircleReplyDetailAdapter mFriendCircleReplyDetailAdapter;
    MyReplyListBean data;
    private String imagePath = "";
    private String imageKey = "";

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<ReplyPageBean.ListBean> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_circle_reply_detail;
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strCommentId = getIntent().getStringExtra(MSG_ID);
        strDynaId = getIntent().getStringExtra(DY_NAID);
        mBinding.setListener(this);
        mViewModel.getReplyList("" + strDynaId, "" + AccountHelper.getUserId(), "" + strCommentId);
        getCommentData();
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.e(TAG, "keyBoardShow: height==="+height );
            }

            @Override
            public void keyBoardHide(int height) {
                Log.e(TAG, "keyBoardHide: height==="+height );

            }
        });
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setEnableRefresh(true);//是否启用下拉刷新功能
        mBinding.swFresh.setEnableLoadmore(true);//是否启用上拉加载功能
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.e(TAG, "onLoadmore: 滑动到底部" );
                if (haveMore) {
                    pageNum++;
                    getCommentData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }


            }
        });
        mBinding.nescrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //Log.e(TAG, "onScrollChange: " + scrollX +"---" + scrollY + "----" +oldScrollX + "---" + oldScrollY );
                //监听滚动状态

                if (scrollY > oldScrollY) {//向下滚动
                    Log.i(TAG, "Scroll DOWN");
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                    hideSoftKeyBoard(FriendCircleReplyDetailActivity.this);
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                }
                if (scrollY < oldScrollY) {//向上滚动
                    Log.i(TAG, "Scroll UP");
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                    hideSoftKeyBoard(FriendCircleReplyDetailActivity.this);
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                }

                if (scrollY == 0) {// 滚动到顶
                    Log.i(TAG, "TOP SCROLL");
                }
                // 滚动到底
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
                }
            }
        });
//        List<MyReplyListBean.ReplyListBean> replyList = data.getReplyList();
//
//        if (replyList != null && replyList.size() > 0) {
//            mFriendCircleReplyDetailAdapter.setNewData(replyList);
//
//        } else {
//            mFriendCircleReplyDetailAdapter.setNewData(new ArrayList<>());
//        }
        mViewModel.getReplyListLiveData.observe(this, new Observer<ResponseBean<MyReplyListBean>>() {
            @Override
            public void onChanged(ResponseBean<MyReplyListBean> responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    data = responseBean.getData();
                    if (data != null) {
                        long addtime = data.getAddtime();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm",
                                    Locale.getDefault());
                            String time1 = sdf.format(addtime);
                            mBinding.tvTime.setText("" + time1);
                        } catch (Exception e) {
                            Log.e(TAG, "onChanged: time===" + addtime);
                            mBinding.tvTime.setText("");
                        }
                        mBinding.tvContent.setText(SpanStringUtils.getEmotionContent(EMOTION_CLASSIC_TYPE,
                                getContext(), mBinding.tvContent, data.getContent()));
                        mBinding.tvLikeCount.setText("" + data.getLikeCount());
                        MyReplyListBean.CurrentUserLikeBean currentUserLike = data.getCurrentUserLike();
//                        {"dynaId":"50","id":"75","userId":"585843408703476912"}
                        Log.e(TAG, "okhttp dynaId" + data.getDynaId());
                        Log.e(TAG, "okhttp id" + data.getId());
                        Log.e(TAG, "okhttp userId" + data.getUserId());
                        if (currentUserLike == null) {
//                            ImageLoader.loadImage(mBinding.ivDianzan, R.drawable.img_circle_not_agree);
                            ImageLoader.loadImage(mBinding.ivBottomDianzan, R.drawable.img_circle_not_agree);
                        } else {
//                            ImageLoader.loadImage(mBinding.ivDianzan, R.drawable.img_circle_agree);
                            ImageLoader.loadImage(mBinding.ivBottomDianzan, R.drawable.img_circle_agree);
                        }


                    } else {
                        toast("" + responseBean.getMessage());
//                        mFriendCircleReplyDetailAdapter.setNewData(new ArrayList<>());

                    }
                } else {
                    toast("" + responseBean.getMessage());
//                    mFriendCircleReplyDetailAdapter.setNewData(new ArrayList<>());

                }
            }
        });
        mFriendCircleReplyDetailAdapter = new FriendCircleReplyDetailAdapter();
        mBinding.rvDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvDetail.setAdapter(mFriendCircleReplyDetailAdapter);
        mFriendCircleReplyDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_dianzan:
                        ReplyPageBean.ListBean.CurrentUserLikeBean currentUserLike = mFriendCircleReplyDetailAdapter.getData().get(position).getCurrentUserLike();
                        ReplyPageBean.ListBean listBean = mFriendCircleReplyDetailAdapter.getData().get(position);


                        int replyId = mFriendCircleReplyDetailAdapter.getData().get(position).getId();
//                        String currentUserLike = mFriendCircleReplyDetailAdapter.getData().get(position).getCurrentUserLike();
                            if (currentUserLike == null) {
                                mViewModel.thumbsUpMomentMessage("" + data.getDynaId(),
                                        "" + AccountHelper.getUserId(), "" + data.getComId(), "" + replyId);
                            } else {
                                mViewModel.cancelThumbsUpMomentMessage("" + currentUserLike.getId(),
                                        "" + data.getDynaId(),
                                        "" + AccountHelper.getUserId(), "" + data.getComId(), "" + replyId);
                        }


                        break;
                    case R.id.tv_reply://回复
                        int mReplyId = mFriendCircleReplyDetailAdapter.getData().get(position).getId();
                        mBinding.llBottom.setVisibility(View.GONE);
                        mBinding.flEmotionviewMain.setVisibility(View.VISIBLE);
////                showSoftInput(mBinding.etContent);
////                showSoftInput(mBinding.etContent);
//                mBinding.etContent.setFocusable(true);
//                mBinding.etContent.setFocusableInTouchMode(true);
//                mBinding.etContent.requestFocus();

//                        initEmotionMainFragment();
                        if (emotionMainFragment == null) {
                            //构建传递参数
                            Bundle bundle = new Bundle();
                            //绑定主内容编辑框
                            bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
                            //隐藏控件
                            bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
                            bundle.putString(EmotionMainFragment.REPLT_ID, "" + mReplyId);
                            bundle.putString(EmotionMainFragment.REPLTY_NAME, "回复：");
                            //替换fragment
                            //创建修改实例
                            emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
                            emotionMainFragment.bindToContentView(mBinding.rvDetail);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            // Replace whatever is in thefragment_container view with this fragment,
                            // and add the transaction to the backstack
                            transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
                            transaction.addToBackStack(null);
                            //提交修改
                            transaction.commit();
                        } else {
                            RxBus.getDefault().post(new ShowPopKeyEvent("回复：", ""+mReplyId));
                        }


                        break;
                }
            }
        });
        mFriendCircleReplyDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mBinding.llBottom.setVisibility(View.VISIBLE);
                mBinding.flEmotionviewMain.setVisibility(View.GONE);
                hideSoftKeyBoard(FriendCircleReplyDetailActivity.this);
                mBinding.llBottom.setVisibility(View.VISIBLE);
                mBinding.flEmotionviewMain.setVisibility(View.GONE);
                switch (view.getId()) {
                    case R.id.iv_dianzan:
                        showLoading();
                        int replyId = mFriendCircleReplyDetailAdapter.getData().get(position).getId();
//                        mViewModel.thumbsUpMomentMessage("" + data.getDynaId(),
//                                "" + AccountHelper.getUserId(), "" + data.getComId(), "" + replyId);
                        ReplyPageBean.ListBean replyListBean = mFriendCircleReplyDetailAdapter.getData().get(position);
                        if (replyListBean.getCurrentUserLike() == null) {
                            mViewModel.thumbsUpMomentMessage("" + data.getDynaId(),
                                    "" + AccountHelper.getUserId(), "" + data.getComId(), "" + replyId);
                        } else {
                            mViewModel.cancelThumbsUpMomentMessage("" + replyListBean.getCurrentUserLike().getId(),
                                    "" + data.getDynaId(),
                                    "" + AccountHelper.getUserId(), "" + data.getComId(), "" + replyId);
                        }

                        break;
                }
            }
        });
        mViewModel.commentMomentMessageReplyLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                toast("" + responseBean.getMessage());
                dismissLoading();
                mViewModel.getReplyList("" + strDynaId, "" + AccountHelper.getUserId(), "" + strCommentId);
                getCommentData();
            }
        });
        mViewModel.thumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                mViewModel.getReplyList("" + strDynaId, "" + AccountHelper.getUserId(), "" + strCommentId);

            }
        });
        mViewModel.cancelThumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                mViewModel.getReplyList("" + strDynaId, "" + AccountHelper.getUserId(), "" + strCommentId);

            }
        });
        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    UploadManager uploadManager = new UploadManager();
                    //                    // 设置图片名字
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("暂无权限");
                }
            }
        });

        mViewModel.getReplyPageLiveData.observe(this, new Observer<ResponseBean<ReplyPageBean>>() {
            @Override
            public void onChanged(ResponseBean<ReplyPageBean> replyCommentPageBeanResponseBean) {

                if (replyCommentPageBeanResponseBean.getErrorCode().equals("0")) {
                    if (pageNum == 1) {
                        list.clear();
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                    } else {
                        mBinding.swFresh.finishLoadmore();
                    }
                    if (list != null) {
                        list.addAll(replyCommentPageBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        mFriendCircleReplyDetailAdapter.setNewData(list);
                    }
                } else {
                    mBinding.swFresh.finishRefresh();
                    mBinding.swFresh.finishLoadmore();
                }

            }
        });
    }

    String key = "";
    String picPath = "";

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        //xlttsports
        uploadManager.put(picPath, "" + key, token, new UpCompletionHandler() {
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                dismissLoading();
                //                oX5vp7Hub8m1zO7QdGYIxZQQBeK9BmBsLxPgURNA:dVgNxp_0wpFkdXJsSkrtECd9alY=:eyJkZWFkbGluZSI6MTYwNzA3NDI1OCwicmV0dXJuQm9keVZvIjp7ImgiOiIkKGltYWdlSW5mby5o
                //                        ZWlnaHQpIiwiaGFzaCI6IiQoZXRhZykiLCJuYW1lIjoiJChmbmFtZSkiLCJzaXplIjoiJChmc2l6
                //                ZSkiLCJ3IjoiJChpbWFnZUluZm8ud2lkdGgpIn0sInNjb3BlIjoieGx0dHNwb3J0czphbmRyb2lk
                //                        X2ltZ18yMDIwMTIwNDE2MzA1ODE3OSJ9

                //                oX5vp7Hub8m1zO7QdGYIxZQQBeK9BmBsLxPgURNA:RE7olg9Gft4O6ce10-3fgepqOyA=:eyJzY29wZSI6InhsdHRzcG9ydHMiLCJkZWFkbGluZSI6MTYwNzA3NDI4NX0=
                if (info.isOK()) {
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    processPath = "";
                    if (!TextUtils.isEmpty(processReplyId)) {
                        mViewModel.replyDynamic("" + strDynaId, "" + AccountHelper.getUserId(), "" + processStr, "" + strCommentId
                                , "" + processReplyId, "" + headpicPath);
                    } else {
                        mViewModel.replyDynamic("" + strDynaId, "" + AccountHelper.getUserId(), "" + processStr, "" + strCommentId
                                , "", "" + headpicPath);
                    }
                    Log.e(TAG, "complete: 上传后的图片===" + headpicPath);
                } else {
                }
                //上传至阡陌链接
                //uploadpictoQianMo(headpicPath, picPath);

            }
        }, null);

    }

    private void uploadImg2QiNiu(String localpicPath) {
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 3) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        key = "android_img_" + sdf.format(new Date()) + sb.toString();
        picPath = localpicPath;
        mViewModel.getUploadToken(key);

    }

    /**
     * 显示软键盘
     */
    private void showSoftInput(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, 0);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_content:

                mBinding.llBottom.setVisibility(View.VISIBLE);
                mBinding.flEmotionviewMain.setVisibility(View.GONE);
                hideSoftKeyBoard(FriendCircleReplyDetailActivity.this);
                mBinding.llBottom.setVisibility(View.VISIBLE);
                mBinding.flEmotionviewMain.setVisibility(View.GONE);

                break;
            case R.id.ll_write_comment:
//                mBinding.etContent.setText("");
                mBinding.llBottom.setVisibility(View.GONE);
                mBinding.flEmotionviewMain.setVisibility(View.VISIBLE);
////                showSoftInput(mBinding.etContent);
////                showSoftInput(mBinding.etContent);
//                mBinding.etContent.setFocusable(true);
//                mBinding.etContent.setFocusableInTouchMode(true);
//                mBinding.etContent.requestFocus();

                initEmotionMainFragment();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(mBinding.etContent, 0);

                break;
//            case R.id.iv_dianzan:
            case R.id.iv_bottom_dianzan:
                MyReplyListBean.CurrentUserLikeBean currentUserLike1 = data.getCurrentUserLike();
                showLoading();
                if (currentUserLike1 == null) {
                    mViewModel.thumbsUpMomentMessage("" + data.getDynaId(),
                            "" + AccountHelper.getUserId(), "" + data.getComId(), "");
                } else {
                    mViewModel.cancelThumbsUpMomentMessage("" + currentUserLike1.getId(),
                            "" + data.getDynaId(),
                            "" + AccountHelper.getUserId(), "" + data.getComId(), "");
                }

                break;
        }
    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        if (emotionMainFragment == null) {
            //构建传递参数
            Bundle bundle = new Bundle();
            //绑定主内容编辑框
            bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
            //隐藏控件
            bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
            //替换fragment
            //创建修改实例
            emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
            emotionMainFragment.bindToContentView(mBinding.rvDetail);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in thefragment_container view with this fragment,
            // and add the transaction to the backstack
            transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
            transaction.addToBackStack(null);
            //提交修改
            transaction.commit();
        } else {
            RxBus.getDefault().post(new ShowPopKeyEvent());
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (emotionMainFragment != null) {
            if (!emotionMainFragment.isInterceptBackPress()) {
                super.onBackPressed();
            }
        }
    }

    private String processStr;
    private String processPath;
    private String processReplyId;


    @Override
    public void process(String str, String path, String replyPid) {
        processStr = str;
        processPath = path;
        processReplyId = replyPid;

        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(processPath)) {
                uploadImg2QiNiu(processPath);
            } else {
                if (!TextUtils.isEmpty(replyPid)) {
                    mViewModel.replyDynamic("" + strDynaId, "" + AccountHelper.getUserId(), "" + str, "" + strCommentId
                            , "" + replyPid, "");
                } else {
                    mViewModel.replyDynamic("" + strDynaId, "" + AccountHelper.getUserId(), "" + str, "" + strCommentId
                            , "", "");
                }
            }
            RxBus.getDefault().post(new ClearShowPopKeyEvent());
        }

        hideSoftKeyBoard(FriendCircleReplyDetailActivity.this);
        mBinding.llBottom.setVisibility(View.VISIBLE);
        mBinding.flEmotionviewMain.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getCommentData();
    }
    public void getCommentData() {
        mViewModel.getReplyPage(""+strDynaId,""+ AccountHelper.getUserId(),"" + strCommentId,"" + pageNum, "" + pageSize);
    }
}
