package heyong.intellectPinPang.ui.friendcircle.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendCircleAdapter;
import heyong.intellectPinPang.bean.emotion.PictureList;
import heyong.intellectPinPang.bean.friend.DynamicListBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ItemFriendCircleRecommendBinding;
import heyong.intellectPinPang.event.RecommendFriendEvent;
import heyong.intellectPinPang.event.SwipQuanziEvent;
import heyong.intellectPinPang.ui.friendcircle.FriendCircleDetailActivity;
import heyong.intellectPinPang.ui.friendcircle.MyFriendCircleActivity;
import heyong.intellectPinPang.ui.friendcircle.PlayVideoActivity;
import heyong.intellectPinPang.ui.friendcircle.PublishEditCircleViewModel;
import heyong.intellectPinPang.utils.GlideSimpleTarget;
import heyong.intellectPinPang.widget.ScrollSpeedLinearLayoutManger;
import heyong.intellectPinPang.widget.SpaceDecoration;

import static permissions.dispatcher.PermissionUtils.verifyPermissions;

public class FriendCirlceRecommendFragment extends BaseFragment<ItemFriendCircleRecommendBinding, PublishEditCircleViewModel> implements ImageWatcher.OnPictureLongPressListener, ImageWatcher.Loader, MyFriendCircleActivity.AgainRequestCountListener, AMapLocationListener, OnRefreshListener {
//    String gson = "[{\"comments_list\":[],\"content\":\"???????????????\",\"createon\":\"3??????\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190383\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"??????????????????????????????\",\"share_url\":\"\",\"type\":4,\"user_id\":\"12\",\"user_name\":\"?????????\"},{\"comments_list\":[],\"content\":\"??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\",\"createon\":\"3??????\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190383\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"?????????????????????\",\"share_url\":\"\",\"type\":1,\"user_id\":\"12\",\"user_name\":\"?????????\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168009921\\u0026di\\u003dfe2e9e5bc508130558a9954c2a8bd28f\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fxuexi.leawo.cn%2Fuploads%2Fallimg%2F160926%2F134225K60-2.gif\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"http://pic.3h3.com/up/2014-3/20143314140858312456.gif\",\"http://imgsrc.baidu.com/forum/w\\u003d580/sign\\u003d852e30338435e5dd902ca5d746c7a7f5/bd3eb13533fa828b6cf24d82fc1f4134960a5afa.jpg\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168118542\\u0026di\\u003d437ba348dfe4bd91afa5e5761f318cee\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201410%2F17%2F20141017094107_VdNJu.gif\",\"https://f12.baidu.com/it/u\\u003d3294379970,949120920\\u0026fm\\u003d72\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168240132\\u0026di\\u003d226605ee54960b3135cbeebf12d1e219\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fphotocdn.sohu.com%2F20150928%2Fmp33561543_1443397655340_1.gif\"],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190381\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":2,\"user_id\":\"3372840\",\"user_name\":\"??????????????????\"},{\"comments_list\":[{\"circle_id\":\"190380\",\"content\":\"??????\",\"createon\":1577085821,\"id\":\"15563\",\"to_user_id\":\"0\",\"to_user_name\":\"\",\"type\":\"0\",\"user_id\":\"3191031\",\"user_name\":\"??????????????????\"}],\"content\":\"\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190380\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":1,\"latitude\":\"\",\"like_list\":[{\"user_id\":\"3191031\",\"user_name\":\"??????????????????\"}],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"?????????????????????????????????,??????????????????\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"????????????\"},{\"comments_list\":[],\"content\":\"??????\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[\"http://img.my.csdn.net/uploads/201701/17/1484647897_1978.jpg\"],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190379\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":3,\"user_id\":\"3372793\",\"user_name\":\"????????????\",\"video\":\"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168389150\\u0026di\\u003d2fd5c826af5394b62777fd132dff7d8f\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201701%2F17%2F20170117112406_zixk5.thumb.700_0.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168397716\\u0026di\\u003d909dcfb1bf7a7fe37041ec5914c34c4a\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs7.rr.itc.cn%2Fg%2FwapChange%2F20159_11_19%2Fa4m9779610717481352.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168437965\\u0026di\\u003df91b9c858eecf75799af00df525eab9a\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F201510_31_11%2Fa6cjhv9612585370352.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168465415\\u0026di\\u003d8c7e9f70a33c4e442427f5e4bd21db1e\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20162_24_14%2Fa69tdw8577863829596.gif\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"http://imgsrc.baidu.com/forum/w\\u003d580/sign\\u003d852e30338435e5dd902ca5d746c7a7f5/bd3eb13533fa828b6cf24d82fc1f4134960a5afa.jpg\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\"],\"head_img\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"id\":\"190378\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":1,\"latitude\":\"\",\"like_list\":[{\"user_id\":\"3191031\",\"user_name\":\"??????bob\"},{\"user_id\":\"3191032\",\"user_name\":\"8765766786786\"},{\"user_id\":\"3191033\",\"user_name\":\"??????\"},{\"user_id\":\"3191034\",\"user_name\":\"??????\"}],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":2,\"user_id\":\"3372793\",\"user_name\":\"????????????\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190377\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"share_title\":\"????????????????????????????????????????????????\",\"share_url\":\"\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"????????????\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4??????\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"id\":\"190376\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"share_title\":\"??????????????????\",\"share_url\":\"\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"????????????\"}]\n" +
//            "\n" +
//            "\n";

    private FriendCircleAdapter circleAdapter;
    ScrollSpeedLinearLayoutManger layoutManger;
    public static final String TAG = FriendCirlceRecommendFragment.class.getSimpleName();
    private int page = 1;
    private int pageSize = 10;
    private boolean haveMore = true;

    private AMapLocationClient mlocationClient;//????????????
    private AMapLocationClientOption mLocationOption = null;//????????????
    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";
    private int hotType = 1;
    List<DynamicListBean.ListBean> list = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        haveMore = true;
        getData(hotType);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_friend_circle_recommend;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
//        List<CircleBean> totalList = new Gson().fromJson(gson, new TypeToken<List<CircleBean>>() {
//        }.getType());
//        viewModel.getMomentList("" + page, "" + pageSize, "", "", "", "", "", "");
        if (binding.swFresh.isRefreshing()) {
            binding.swFresh.finishRefresh();
        }
        getData(1);

        binding.swFresh.setOnRefreshListener(this);

        mlocationClient = new AMapLocationClient(getActivity());
//?????????????????????
        mLocationOption = new AMapLocationClientOption();
//??????????????????
        mlocationClient.setLocationListener(this);
//???????????????????????????????????????Battery_Saving?????????????????????Device_Sensors??????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//??????????????????,????????????,?????????2000ms
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
//??????????????????
        mlocationClient.setLocationOption(mLocationOption);
// ????????????????????????????????????????????????????????????????????????????????????????????????????????????
// ??????????????????????????????????????????????????????????????????1000ms?????????????????????????????????stopLocation()???????????????????????????
// ???????????????????????????????????????????????????onDestroy()??????
// ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
//????????????
        binding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    page++;
                    getData(hotType);
                } else {
                    binding.swFresh.finishLoadmore();
                }
            }
        });
        viewModel.DynamicDetailBeanLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("0")) {
                    page = 1;
                    haveMore = true;
                    getData(hotType);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        viewModel.cancelThumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("0")) {
                    page = 1;
                    haveMore = true;
                    getData(hotType);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        circleAdapter = new FriendCircleAdapter(getActivity());

        layoutManger = new ScrollSpeedLinearLayoutManger(getActivity());
        binding.recyclerView.setLayoutManager(layoutManger);
        MyFriendCircleActivity.setAgainRequestCountListener(this);

        layoutManger.setSpeedSlow();
        binding.recyclerView.addItemDecoration(new SpaceDecoration(getActivity()));
        binding.recyclerView.setAdapter(circleAdapter);
        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_play:
                    case R.id.video_view:
                    case R.id.iv_play:
                        Log.e(TAG, "onItemClick: " + circleAdapter.getData().get(position).getMyvideoList().get(0));
                        Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                        intent.putExtra("url", circleAdapter.getData().get(position).getMyvideoList().get(0));
                        startActivity(intent);

                        break;
                    case R.id.iv_user_logo:

                        Intent intent1 = new Intent(getActivity(), FriendCircleDetailActivity.class);
                        intent1.putExtra(FriendCircleDetailActivity.IDS, "" + circleAdapter.getData().get(position).getId());
                        intent1.putExtra(FriendCircleDetailActivity.USER_IDS, "" + circleAdapter.getData().get(position).getUserId());
                        startActivity(intent1);

                        break;

                }
            }
        });
        circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_play:
                    case R.id.video_view:
                    case R.id.iv_play:
                        Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                        intent.putExtra("url", Objects.requireNonNull(circleAdapter.getItem(position)).getMyvideoList().get(0));
                        startActivity(intent);

                        break;
                    case R.id.iv_user_logo:

                        Intent intent1 = new Intent(getActivity(), FriendCircleDetailActivity.class);
                        intent1.putExtra(FriendCircleDetailActivity.IDS, "" + circleAdapter.getData().get(position).getId());
                        intent1.putExtra(FriendCircleDetailActivity.USER_IDS, "" + circleAdapter.getData().get(position).getUserId());

                        startActivity(intent1);

                        break;

                    case R.id.iv_hands_up:
//                        int likeFlag = circleAdapter.getData().get(position).getLikeFlag();
                        DynamicListBean.ListBean.CurrentUserLikeBean currentUserLike = circleAdapter.getData().get(position).getCurrentUserLike();

                        showLoading();
                        if (currentUserLike == null) {
                            viewModel.thumbsUpMomentMessage("" + circleAdapter.getData().get(position).getId(),
                                    "" + AccountHelper.getUserId(), "", "");
                        } else {
                            viewModel.cancelThumbsUpMomentMessage("" + currentUserLike.getId(),
                                    "" + circleAdapter.getData().get(position).getId(),
                                    "" + AccountHelper.getUserId(), "", "");
                        }
                        circleAdapter.notifyDataSetChanged();


                        break;
                    case R.id.ll_share:
                        showLoading();
                        viewModel.shareDynamic("" + circleAdapter.getData().get(position).getId());

                        break;
                }
            }
        });
        RxBus.getDefault().toObservable(SwipQuanziEvent.class).subscribe(tagEvents -> {
            page = 1;
            haveMore = true;
            getData(hotType);

        });
        viewModel.thumbsUpMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    page = 1;
                    haveMore = true;
                    getData(hotType);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        viewModel.getMomentListLiveData.observe(this, new Observer<ResponseBean<DynamicListBean>>() {
            @Override
            public void onChanged(ResponseBean<DynamicListBean> dynamicListBeanResponseBean) {
                if (dynamicListBeanResponseBean.getErrorCode().equals("0")) {
                    DynamicListBean data = dynamicListBeanResponseBean.getData();
                    if (data != null) {
                        if (data.getList().size() > 0) {
                            for (int i = 0; i < data.getList().size(); i++) {
                                DynamicListBean.ListBean listBean = data.getList().get(i);
                                int type = listBean.getType();
                                if (type == 1) {
                                    //?????????
                                    listBean.setType(0);
                                } else if (type == 2) {
                                    String content = listBean.getContent();
                                    if (TextUtils.isEmpty(content)) {
                                        listBean.setType(2);
                                    } else {
                                        listBean.setType(1);
                                    }
                                } else if (type == 3) {
                                    //?????????  ??????+??????
                                    String content = listBean.getContent();
                                    if (TextUtils.isEmpty(content)) {
                                        listBean.setType(3);
                                    } else {
                                        listBean.setType(4);
                                    }
                                }
                                String videoListJson = listBean.getVideoList();
                                List<PictureList> totalList = new Gson().fromJson(videoListJson, new TypeToken<List<PictureList>>() {
                                }.getType());
                                List<String> myVideoListNew = new ArrayList<>();
                                if (totalList != null && totalList.size() > 0) {
                                    for (int i1 = 0; i1 < totalList.size(); i1++) {
                                        String fileUrl = totalList.get(i1).getFilePixels().getFileUrl();
                                        myVideoListNew.add(fileUrl);
                                    }
                                }
                                listBean.setMyvideoList(myVideoListNew);
                                String pictureJson = listBean.getPictureList();
                                List<PictureList> totalPicture = new Gson().fromJson(pictureJson, new TypeToken<List<PictureList>>() {
                                }.getType());
                                List<String> myVideoListPicture = new ArrayList<>();
                                if (totalPicture != null && totalPicture.size() > 0) {
                                    for (int m = 0; m < totalPicture.size(); m++) {
                                        String fileUrl = totalPicture.get(m).getFilePixels().getFileUrl();
                                        myVideoListPicture.add(fileUrl);
                                    }
                                }
                                listBean.setMypictureList(myVideoListPicture);
                                data.getList().set(i, listBean);
                            }
//                          circleAdapter.setNewData(totalList);
                            if (page == 1) {
                                list.clear();
                                if (binding.swFresh != null) {
                                    binding.swFresh.finishRefresh();
                                }
                            } else {
                                binding.swFresh.finishLoadmore();
                            }
                            if (list != null) {
                                list.addAll(data.getList());
                                if (list != null && list.size() < pageSize) {
                                    haveMore = false;
                                    binding.swFresh.setEnableLoadmore(false);
                                }
                                circleAdapter.setNewData(list);
                            }
                        }else
                        {
                            binding.swFresh.finishRefresh();
                            binding.swFresh.finishLoadmore();
                            circleAdapter.setNewData(new ArrayList<>());
                        }
                    }else
                    {
                        binding.swFresh.finishRefresh();
                        binding.swFresh.finishLoadmore();
                        circleAdapter.setNewData(new ArrayList<>());
                    }
                } else {
                    binding.swFresh.finishRefresh();
                    binding.swFresh.finishLoadmore();

                    circleAdapter.setNewData(new ArrayList<>());
                }


            }
        });
    }

    @Override
    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("????????????");
        alert.setMessage("????????????????????????????");
        alert.setNegativeButton("??????", null);
        alert.setPositiveButton("??????", (dialog, which) -> {


        });
        alert.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageRecommendFriendEvent(RecommendFriendEvent event) {

        Log.e(TAG, "messageCityEvent: loadType" + event.getLoadType());

    }

    @Override
    public void load(Context context, Uri uri, ImageWatcher.LoadCallback lc) {
        Glide.with(context).asBitmap().load(uri.toString()).into(new GlideSimpleTarget(lc));
    }

    /*************************************** ????????????******************************************************/
//????????????????????????????????????????????????true???????????????????????????????????????????????????????????????
    private boolean needCheckBackLocation = false;
    //???????????????target > 28????????????????????????????????????????????????"????????????"???????????????
    private static String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    /**
     * ????????????????????????????????????????????????
     */
    private boolean isNeedCheck = true;
    /**
     * ?????????????????????????????????
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            BACK_LOCATION_PERMISSION
    };

    public void getData(int hotType) {
        if (hotType == 1) {
            //??????
            viewModel.getMomentList("" + page, "" + pageSize, "", "", "1", "", "", "");


        } else if (hotType == 2) {
            //??????
            /*????????????*/
            if (Build.VERSION.SDK_INT > 28
                    && getActivity().getApplicationContext().getApplicationInfo().targetSdkVersion > 28) {
                needPermissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        BACK_LOCATION_PERMISSION
                };
                needCheckBackLocation = true;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    //????????????
//            Log.e(TAG, "initLocation: ?????????????????????");
                    Log.e(TAG, "initGetMei: ????????????fragment ????????????");
                    if (isNeedCheck) {
                        checkPermissions(needPermissions);
                    }
                } else {
                    Log.e(TAG, "initGetMei: ??????????????????fragment ????????????");
                    mlocationClient.startLocation();
//            Log.e(TAG, "initLocation: ??????????????????????????? ??????????????????");

                }

            }
//            mlocationClient.startLocation();

        } else if (hotType == 3) {
            //??????
            viewModel.getMomentList("" + page, "" + pageSize, "3", "", "", "", "", "");


        }
    }

    @Override
    public void updateRecommend(int hotType1) {
//        toast("????????????===" + hotType1);
        Log.e(TAG, "updateRecommend: ????????????" + hotType1);
        hotType = hotType1;
        getData(hotType1);

    }

    @Override
    public void updateAttention(int hotType) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageToQuanziEvent(SwipQuanziEvent event) {
        page = 1;
        haveMore = true;
        getData(hotType);
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && getActivity().getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    try {
                        String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                        Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});
                        method.invoke(this, array, 0);
                    } catch (Throwable e) {

                    }
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private List<String> findDeniedPermissions(String[] permissions) {
        try {
            List<String> needRequestPermissonList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= 23 && getActivity().getApplicationInfo().targetSdkVersion >= 23) {
                for (String perm : permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                            || shouldShowMyRequestPermissionRationale(perm)) {
                        if (!needCheckBackLocation
                                && BACK_LOCATION_PERMISSION.equals(perm)) {
                            continue;
                        }
                        needRequestPermissonList.add(perm);
                    }
                }
            }
            return needRequestPermissonList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            Method method = getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer permissionInt = (Integer) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            Method method = getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean permissionInt = (Boolean) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return false;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //?????????????????????????????????????????????
                amapLocation.getLocationType();//??????????????????????????????????????????????????????????????????????????????
                amapLocation.getLatitude();//????????????
                amapLocation.getLongitude();//????????????
                amapLocation.getAccuracy();//??????????????????
                String city = amapLocation.getCity();
                Log.e(TAG, "onLocationChanged: city===" + city);
                Log.e(TAG, "onLocationChanged: " + new Gson().toJson(amapLocation));
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//????????????
//                String citys = city.replace("???", "");
                Log.e(TAG, "onLocationChanged: getDescription=" + amapLocation.getDescription());
                Log.e(TAG, "onLocationChanged: getCity=" + amapLocation.getCity());
                Log.e(TAG, "onLocationChanged: getAoiName=" + amapLocation.getAoiName());
                Log.e(TAG, "onLocationChanged: getAddress=" + amapLocation.getAddress());
                Log.e(TAG, "onLocationChanged: getDistrict=" + amapLocation.getDistrict());
                Log.e(TAG, "onLocationChanged: getPoiName=" + amapLocation.getPoiName());
                Log.e(TAG, "onLocationChanged: getLocationDetail=" + amapLocation.getLocationDetail());
//                citys = city;
                mProvince = amapLocation.getProvince();
                mCity = amapLocation.getCity();
                mArea = amapLocation.getDistrict();
                viewModel.getMomentList("" + page, "" + pageSize, "3",
                        "", "", "" + mProvince,
                        "" + mCity, "" + mArea);

//                keyWord = mBinding.etSearchKeyword.getText().toString();
//                Log.e(TAG, "getdatda: " + citys + keyWord);
//                getData();
            } else {
                //??????????????????ErrCode???????????????errInfo???????????????????????????????????????
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private static final int PERMISSON_REQUESTCODE = 0;


    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (requestCode == PERMISSON_REQUESTCODE) {
                    if (!verifyPermissions(paramArrayOfInt)) {
                        showMissingPermissionDialog();
                        isNeedCheck = false;
                    } else {
                        mlocationClient.startLocation();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("??????");
            builder.setMessage("?????????????????????????????????\\n\\n?????????\\\"??????\\\"-\\\"??????\\\"-??????????????????");

            // ??????, ????????????
            builder.setNegativeButton("??????",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                getActivity().finish();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setPositiveButton("??????",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startAppSettings();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setCancelable(false);

            builder.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        haveMore = true;
        getData(hotType);
    }
}
