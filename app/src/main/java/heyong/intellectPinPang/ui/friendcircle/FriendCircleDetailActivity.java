package heyong.intellectPinPang.ui.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.Random;
import java.util.Set;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendCircleCommentAdapter;
import heyong.intellectPinPang.adapter.friendcircle.NineImageAdapter;
import heyong.intellectPinPang.bean.friend.DynamicDetailBean;
import heyong.intellectPinPang.bean.emotion.PictureList;
import heyong.intellectPinPang.bean.friend.ReplyCommentPageBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ActivityFriendCircleDetailBinding;
import heyong.intellectPinPang.event.ClearShowPopKeyEvent;
import heyong.intellectPinPang.event.ShowPopKeyEvent;
import heyong.intellectPinPang.ui.friendcircle.fragment.EmotionMainFragment;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.lzy.imagepicker.ui.ImageBroseNewActivity;

import static heyong.intellectPinPang.SampleApplicationLike.getContext;

/**
 * 朋友圈详情界面UI
 */
public class FriendCircleDetailActivity extends BaseActivity<ActivityFriendCircleDetailBinding, PublishEditCircleViewModel>
        implements OnRefreshListener, View.OnClickListener, EmotionMainFragment.FragmentInteraction {
    FriendCircleCommentAdapter mFriendCircleCommentAdapter;
    List<String> datas = new ArrayList<>();
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String IDS = "ids";
    public static final String USER_IDS = "user_id";
    private String strId = "";
    private String videoPath = "";
    private String strUserId = "";
    private String dynaId = "";
    private int clickPosition = -1;
    DynamicDetailBean data;
    private EmotionMainFragment emotionMainFragment;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<ReplyCommentPageBean.ListBean> list = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getCommentData();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_circle_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strId = getIntent().getStringExtra(IDS);
//        strUserId = getIntent().getStringExtra(USER_IDS);
        strUserId = "" + AccountHelper.getUserId();
        mViewModel.getMomentDetailById(strId, strUserId);
        mBinding.setListener(this);
        getCommentData();

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.e(TAG, "keyBoardShow: height===" + height);
            }

            @Override
            public void keyBoardHide(int height) {
                Log.e(TAG, "keyBoardHide: height===" + height);

            }
        });
        mBinding.nescrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //Log.e(TAG, "onScrollChange: " + scrollX +"---" + scrollY + "----" +oldScrollX + "---" + oldScrollY );
                //监听滚动状态

                if (scrollY > oldScrollY) {//向下滚动
//                    Log.e(TAG, "Scroll DOWN");
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                    hideSoftKeyBoard(FriendCircleDetailActivity.this);
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                }
                if (scrollY < oldScrollY) {//向上滚动
//                    Log.e(TAG, "Scroll UP");
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                    hideSoftKeyBoard(FriendCircleDetailActivity.this);
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    mBinding.flEmotionviewMain.setVisibility(View.GONE);
                }

            }
        });


        mViewModel.commentMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    toast("发送成功");
                    //刷新评论
                    getCommentData();
//                    mViewModel.getMomentDetailById(strId, strUserId);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.deleteMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("0")) {
                    toast("删除成功");
                    finish();
                } else {
                    toast("删除失败");
                }
            }
        });
        mViewModel.getMomentDetailByIdLiveData.observe(this, new Observer<ResponseBean<DynamicDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<DynamicDetailBean> dynamicDetailBeanResponseBean) {
                if (dynamicDetailBeanResponseBean.getErrorCode().equals("0")) {
                    data = dynamicDetailBeanResponseBean.getData();
                    if (data != null) {
                        dynaId = "" + data.getId();
                        int type = data.getType();
                        boolean collectVisible = data.isCollectVisible();
                        if (collectVisible) {
                            ImageLoader.loadImage(mBinding.ivShoucang, R.drawable.img_collect_commit_yes);
                        } else {
                            ImageLoader.loadImage(mBinding.ivShoucang, R.drawable.img_collect_commit_not);
                        }

                        DynamicDetailBean.CurrentUserLikeBean currentUserLike = data.getCurrentUserLike();
                        if (currentUserLike == null) {
                            ImageLoader.loadImage(mBinding.ivDianzan, R.drawable.img_circle_not_agree);

                        } else {
                            ImageLoader.loadImage(mBinding.ivDianzan, R.drawable.img_circle_agree);

                        }
                        mBinding.tvLikeCount.setText("" + data.getLikeCount());
                        mBinding.tvCommentCount.setText("评论 " + data.getCommentCount());
                        if (clickPosition != -1) {
                            mBinding.rvComment.smoothScrollToPosition(clickPosition);
                        }
                        String pictureList = data.getPictureList();
                        String videoList = data.getVideoList();
                        long userId1 = data.getUserId();
                        if (userId1 == AccountHelper.getUserId()) {
                            //自己的
                            mBinding.tvDelete.setVisibility(View.VISIBLE);
                            mBinding.tvAttention.setVisibility(View.GONE);
                        } else {
                            mBinding.tvDelete.setVisibility(View.GONE);
                            mBinding.tvAttention.setVisibility(View.VISIBLE);
                        }
                        if (type == 1) {
                            //纯文字
                            type = 0;//文本
                            mBinding.rlVideoView.setVisibility(View.GONE);
                            mBinding.layoutNine.setVisibility(View.GONE);
                        } else if (type == 2) {
                            String content = data.getContent();
                            mBinding.rlVideoView.setVisibility(View.GONE);
                            mBinding.layoutNine.setVisibility(View.VISIBLE);
                            if (TextUtils.isEmpty(content)) {
                                type = 2;//图片
                            } else {
                                type = 1;//文本+图片
                            }
                            String pictureJson = data.getPictureList();
                            List<PictureList> totalPicture = new Gson().fromJson(pictureJson, new TypeToken<List<PictureList>>() {
                            }.getType());
                            List<String> myVideoListPicture = new ArrayList<>();
                            if (totalPicture != null && totalPicture.size() > 0) {
                                for (int m = 0; m < totalPicture.size(); m++) {
                                    String fileUrl = totalPicture.get(m).getFilePixels().getFileUrl();
                                    myVideoListPicture.add(fileUrl);
                                }
                                mBinding.layoutNine.setSingleImageSize(80, 120);
                                RequestOptions mRequestOptions = new RequestOptions().centerCrop();
                                DrawableTransitionOptions mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
                                if (myVideoListPicture != null && myVideoListPicture.size() > 0) {
                                    mBinding.layoutNine.setAdapter(new NineImageAdapter(FriendCircleDetailActivity.this, mRequestOptions, mDrawableTransitionOptions, myVideoListPicture));
                                    mBinding.layoutNine.setOnImageClickListener((position, view) -> {

                                        Intent intent = new Intent(FriendCircleDetailActivity.this, ImageBroseNewActivity.class);
                                        intent.putExtra("path", myVideoListPicture.get(position));
                                        intent.putExtra("current_position", position);
                                        intent.putExtra("is_delete", "0");
                                        startActivity(intent);
                                    });
                                }
                            } else {
                                mBinding.layoutNine.setVisibility(View.GONE);
                            }
                        } else if (type == 3) {
                            //纯视频  视频+文本
                            mBinding.layoutNine.setVisibility(View.GONE);
                            mBinding.rlVideoView.setVisibility(View.VISIBLE);

                            String content = data.getContent();
                            if (TextUtils.isEmpty(content)) {
                                type = 3;//视频
                            } else {
                                type = 4;//文本+视频
                            }
                            String videoListJson = data.getVideoList();
                            List<PictureList> totalList = new Gson().fromJson(videoListJson, new TypeToken<List<PictureList>>() {
                            }.getType());
                            List<String> myVideoListNew = new ArrayList<>();
                            if (totalList != null && totalList.size() > 0) {
                                for (int i1 = 0; i1 < totalList.size(); i1++) {
                                    String fileUrl = totalList.get(i1).getFilePixels().getFileUrl();
                                    myVideoListNew.add(fileUrl);
                                }
                                videoPath = myVideoListNew.get(0);
                                Glide.with(FriendCircleDetailActivity.this)
                                        .setDefaultRequestOptions(
                                                new RequestOptions()
                                                        .frame(0)
                                                        .centerCrop()
                                        )
                                        .load(myVideoListNew.get(0))
                                        .into(mBinding.videoView);
                            } else {
                                mBinding.rlVideoView.setVisibility(View.GONE);
                            }
                        }
                        mBinding.tvContent.setText("" + data.getContent());
                        mBinding.tvCommentNum.setText("" + data.getCommentCount());
                    }
                } else {
                    toast("" + dynamicDetailBeanResponseBean.getMessage());
                }
            }
        });

        mFriendCircleCommentAdapter = new FriendCircleCommentAdapter();
        mBinding.rvComment.setAdapter(mFriendCircleCommentAdapter);
        mRefreshAnimHeader = new MyRefreshAnimHeader(FriendCircleDetailActivity.this);
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        for (int m = 0; m < 10; m++) {
//            datas.add("1");
//        }
        mBinding.rvComment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        mFriendCircleCommentAdapter.setNewData(datas);
//        mBinding.swFresh.setOnRefreshListener()
        mBinding.swFresh.setEnableRefresh(false);
        mBinding.swFresh.setOnRefreshListener(this);
        mFriendCircleCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_dianzan:
                        clickPosition = position;
                        ReplyCommentPageBean.ListBean.CurrentUserLikeBean currentUserLike = mFriendCircleCommentAdapter.getData().get(position).getCurrentUserLike();
                        if (currentUserLike == null) {
                            mViewModel.thumbsUpMomentMessage("" + dynaId,
                                    "" + AccountHelper.getUserId(), "" + mFriendCircleCommentAdapter.getData().get(position).getId(), "");
                        } else {
                            mViewModel.cancelThumbsUpMomentMessage("" + currentUserLike.getId(),
                                    "" + dynaId,
                                    "" + AccountHelper.getUserId(), "" + mFriendCircleCommentAdapter.getData().get(position).getId(), "");
                        }

                        break;
                    case R.id.ll_content:
                        int id = mFriendCircleCommentAdapter.getData().get(position).getId();
                        long dynaId = mFriendCircleCommentAdapter.getData().get(position).getDynaId();
                        startActivity(new Intent(FriendCircleDetailActivity.this, FriendCircleReplyDetailActivity.class).putExtra(
                                FriendCircleReplyDetailActivity.MSG_ID, "" + id
                                ).putExtra(FriendCircleReplyDetailActivity.DY_NAID, "" + dynaId)
                        );


                        break;
                }
            }
        });
        mViewModel.thumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    toast("" + responseBean.getMessage());

                    mViewModel.getMomentDetailById(strId, strUserId);
                    getCommentData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.cancelThumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    toast("" + responseBean.getMessage());
                    mViewModel.getMomentDetailById(strId, strUserId);
                    getCommentData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mBinding.swFresh.setEnableRefresh(true);//是否启用下拉刷新功能
        mBinding.swFresh.setEnableLoadmore(true);//是否启用上拉加载功能
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.e(TAG, "onLoadmore: 滑动到底部");
                if (haveMore) {
                    pageNum++;
                    getCommentData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }


            }
        });
        mViewModel.thumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                mViewModel.getMomentDetailById(strId, strUserId);
                getCommentData();
            }
        });
        mViewModel.cancelThumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();

                mViewModel.getMomentDetailById(strId, strUserId);
                getCommentData();
            }
        });
        mViewModel.cancelCollectDynamicLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();

                mViewModel.getMomentDetailById(strId, strUserId);
            }
        });
        mViewModel.collectDynamicLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();

                mViewModel.getMomentDetailById(strId, strUserId);
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
        mViewModel.replyCommentPageBeanLiveData.observe(this, new Observer<ResponseBean<ReplyCommentPageBean>>() {
            @Override
            public void onChanged(ResponseBean<ReplyCommentPageBean> replyCommentPageBeanResponseBean) {

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
                        mFriendCircleCommentAdapter.setNewData(list);
                    }
                } else {
                    mBinding.swFresh.finishRefresh();
                    mBinding.swFresh.finishLoadmore();
                }

            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getCommentData();
    }

    public void getCommentData() {
        mViewModel.replyCommentPage("" + pageNum, "" + pageSize, "" + strId, "" + strUserId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_video_view:
                if (!TextUtils.isEmpty(videoPath)) {
                    Intent intent = new Intent(FriendCircleDetailActivity.this, PlayVideoActivity.class);
                    intent.putExtra("url", videoPath);
                    startActivity(intent);
                }

                break;
            case R.id.iv_dianzan:
            case R.id.ll_dianzan:
                //获取数据的对象 来 点赞和取消点赞  和外面的点赞一样
                if (data != null) {
                    showLoading();
                    DynamicDetailBean.CurrentUserLikeBean currentUserLike = data.getCurrentUserLike();
//                    int id = currentUserLike.getId();
                    if (currentUserLike == null) {
                        mViewModel.thumbsUpMomentMessage("" + data.getId(),
                                "" + AccountHelper.getUserId(), "", "");
                    } else {
                        mViewModel.cancelThumbsUpMomentMessage("" + currentUserLike.getId(),
                                "" + currentUserLike.getDynaId(),
                                "" + AccountHelper.getUserId(), "", "");
                    }


                }

                break;
            case R.id.ll_content:
                mBinding.llBottom.setVisibility(View.VISIBLE);
                mBinding.flEmotionviewMain.setVisibility(View.GONE);
                hideSoftKeyBoard(FriendCircleDetailActivity.this);
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
            case R.id.tv_delete:
                showLoading();
                mViewModel.deleteDynamic("" + dynaId, "" + strUserId);


                break;
            case R.id.ll_shoucang:
                boolean collectVisible = data.isCollectVisible();
                if (collectVisible) {
                    mViewModel.cancelCollectDynamic("" + dynaId, "" + strUserId);
                } else {
                    mViewModel.collectDynamic("" + dynaId, "" + strUserId);
                }
                break;
            case R.id.rl_content_text:
                int top = mBinding.llCommentHight.getTop();
                mBinding.nescrollview.smoothScrollTo(0, top);


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
            emotionMainFragment.bindToContentView(mBinding.rvComment);
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
    private String processPrid;
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
                    mViewModel.commentDynamic("" + dynaId, "" + strUserId, "" + processStr, "", "" + headpicPath);

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

    @Override
    public void process(String str, String path, String prid) {
        processStr = str;
        processPath = path;
        processPrid = prid;//回复的id  这里用不上

        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(processPath)) {
//                mViewModel.commentDynamic("" + dynaId, "" + strUserId, "" + str, "");
                uploadImg2QiNiu(processPath);
            } else {
                mViewModel.commentDynamic("" + dynaId, "" + strUserId, "" + str, "", "");
            }
            RxBus.getDefault().post(new ClearShowPopKeyEvent());
        }

        hideSoftKeyBoard(FriendCircleDetailActivity.this);
        mBinding.llBottom.setVisibility(View.VISIBLE);
        mBinding.flEmotionviewMain.setVisibility(View.GONE);
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

}
