package heyong.intellectPinPang.ui.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ielse.imagewatcher.ImageWatcher;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Objects;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendCircleAdapter;
import heyong.intellectPinPang.bean.friend.DynamicListBean;
import heyong.intellectPinPang.databinding.ItemFriendCircleRecommendBinding;
import heyong.intellectPinPang.event.AttentionFriendEvent;
import heyong.intellectPinPang.ui.friendcircle.FriendCircleDetailActivity;
import heyong.intellectPinPang.ui.friendcircle.PlayVideoActivity;
import heyong.intellectPinPang.ui.friendcircle.PublishEditCircleViewModel;
import heyong.intellectPinPang.utils.GlideSimpleTarget;
import heyong.intellectPinPang.widget.ScrollSpeedLinearLayoutManger;
import heyong.intellectPinPang.widget.SpaceDecoration;

public class FriendCirlceAttentionFragment extends BaseFragment<ItemFriendCircleRecommendBinding, PublishEditCircleViewModel>
        implements ImageWatcher.OnPictureLongPressListener, ImageWatcher.Loader {
    String gson = "[{\"comments_list\":[],\"content\":\"热修复测试\",\"createon\":\"3天前\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190383\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"茫茫的长白大山瀚的原\",\"share_url\":\"\",\"type\":4,\"user_id\":\"12\",\"user_name\":\"机器猫\"},{\"comments_list\":[],\"content\":\"茫茫的长白大山瀚的草原，浩始森林，大山脚下，原始森林环抱中散落着几十户人家的，一个小山村，茅草房，对面炕，烟筒立在屋后边。在村东头有一个独立的房子，那就是青年点，窗前有一道小溪流过。学子在这里吃饭，由这里出发每天随社员去地里干活。干的活要么上山伐，树，抬树，要么砍柳树毛子开荒种地。在山里，可听那吆呵声：“顺山倒了！”放树谨防回头棒！，树上的枯枝打到别的树上再蹦回来，这回头棒打人最厉害。\",\"createon\":\"3天前\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190383\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"事件分发的过程\",\"share_url\":\"\",\"type\":1,\"user_id\":\"12\",\"user_name\":\"机器猫\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168009921\\u0026di\\u003dfe2e9e5bc508130558a9954c2a8bd28f\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fxuexi.leawo.cn%2Fuploads%2Fallimg%2F160926%2F134225K60-2.gif\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"http://pic.3h3.com/up/2014-3/20143314140858312456.gif\",\"http://imgsrc.baidu.com/forum/w\\u003d580/sign\\u003d852e30338435e5dd902ca5d746c7a7f5/bd3eb13533fa828b6cf24d82fc1f4134960a5afa.jpg\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168118542\\u0026di\\u003d437ba348dfe4bd91afa5e5761f318cee\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201410%2F17%2F20141017094107_VdNJu.gif\",\"https://f12.baidu.com/it/u\\u003d3294379970,949120920\\u0026fm\\u003d72\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168240132\\u0026di\\u003d226605ee54960b3135cbeebf12d1e219\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fphotocdn.sohu.com%2F20150928%2Fmp33561543_1443397655340_1.gif\"],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190381\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":2,\"user_id\":\"3372840\",\"user_name\":\"一笑的小馆子\"},{\"comments_list\":[{\"circle_id\":\"190380\",\"content\":\"巴结\",\"createon\":1577085821,\"id\":\"15563\",\"to_user_id\":\"0\",\"to_user_name\":\"\",\"type\":\"0\",\"user_id\":\"3191031\",\"user_name\":\"一笑的小管子\"}],\"content\":\"\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190380\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":1,\"latitude\":\"\",\"like_list\":[{\"user_id\":\"3191031\",\"user_name\":\"一笑的小管子\"}],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg\",\"share_title\":\"《王者荣耀》真的很好玩,快来一起玩吧\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"天宫一号\"},{\"comments_list\":[],\"content\":\"哈哈\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[\"http://img.my.csdn.net/uploads/201701/17/1484647897_1978.jpg\"],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190379\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":3,\"user_id\":\"3372793\",\"user_name\":\"天宫一号\",\"video\":\"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168389150\\u0026di\\u003d2fd5c826af5394b62777fd132dff7d8f\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201701%2F17%2F20170117112406_zixk5.thumb.700_0.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168397716\\u0026di\\u003d909dcfb1bf7a7fe37041ec5914c34c4a\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs7.rr.itc.cn%2Fg%2FwapChange%2F20159_11_19%2Fa4m9779610717481352.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168437965\\u0026di\\u003df91b9c858eecf75799af00df525eab9a\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F201510_31_11%2Fa6cjhv9612585370352.gif\",\"https://timgsa.baidu.com/timg?image\\u0026quality\\u003d80\\u0026size\\u003db9999_10000\\u0026sec\\u003d1542168465415\\u0026di\\u003d8c7e9f70a33c4e442427f5e4bd21db1e\\u0026imgtype\\u003d0\\u0026src\\u003dhttp%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20162_24_14%2Fa69tdw8577863829596.gif\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\",\"http://imgsrc.baidu.com/forum/w\\u003d580/sign\\u003d852e30338435e5dd902ca5d746c7a7f5/bd3eb13533fa828b6cf24d82fc1f4134960a5afa.jpg\",\"http://pic2.52pk.com/files/allimg/150324/104923F49-12.jpg\"],\"head_img\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"id\":\"190378\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":1,\"latitude\":\"\",\"like_list\":[{\"user_id\":\"3191031\",\"user_name\":\"一搏bob\"},{\"user_id\":\"3191032\",\"user_name\":\"8765766786786\"},{\"user_id\":\"3191033\",\"user_name\":\"花花\"},{\"user_id\":\"3191034\",\"user_name\":\"丁当\"}],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"\",\"share_title\":\"\",\"share_url\":\"\",\"type\":2,\"user_id\":\"3372793\",\"user_name\":\"天宫一号\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull\",\"id\":\"190377\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"share_title\":\"原始森林环抱中散落着几十户人家的\",\"share_url\":\"\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"天宫一号\"},{\"comments_list\":[],\"content\":\"\",\"createon\":\"4天前\",\"deleteon\":\"0\",\"files\":[],\"head_img\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"id\":\"190376\",\"isExpanded\":false,\"isShowAll\":false,\"isShowCheckAll\":false,\"is_del\":\"0\",\"is_like\":0,\"latitude\":\"\",\"like_list\":[],\"longitude\":\"\",\"position\":\"\",\"share_image\":\"http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA\",\"share_title\":\"今天天气很热\",\"share_url\":\"\",\"type\":4,\"user_id\":\"3372793\",\"user_name\":\"天宫一号\"}]\n" +
            "\n" +
            "\n";

    private FriendCircleAdapter circleAdapter;
    ScrollSpeedLinearLayoutManger layoutManger;
    private int loadDataType = 1;
    public static final String TAG = FriendCirlceAttentionFragment.class.getSimpleName();

    @Override
    public int getLayoutRes() {
        return R.layout.item_friend_circle_recommend;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
//        List<CircleBean> totalList = new Gson().fromJson(gson, new TypeToken<List<CircleBean>>() {
//        }.getType());
//        MyFriendCircleActivity.setAgainRequestCountListener(this);

        //初始化仿微信图片滑动加载器
//        binding.imageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(getActivity()));
//        binding.imageWatcher.setErrorImageRes(R.mipmap.error_picture);
//        binding.imageWatcher.setOnPictureLongPressListener(this);
//        binding.imageWatcher.setLoader(this);
        circleAdapter = new FriendCircleAdapter(getActivity());

        layoutManger = new ScrollSpeedLinearLayoutManger(getActivity());
        binding.recyclerView.setLayoutManager(layoutManger);
        circleAdapter.setNewData(new ArrayList<>());

        layoutManger.setSpeedSlow();
        binding.recyclerView.addItemDecoration(new SpaceDecoration(getActivity()));
        binding.recyclerView.setAdapter(circleAdapter);
        circleAdapter.setMyListener(new FriendCircleAdapter.onOwnMyListener() {
            @Override
            public void onMySelfDetail(DynamicListBean.ListBean titleBean) {
                Intent intent1 = new Intent(getActivity(), FriendCircleDetailActivity.class);
                intent1.putExtra(FriendCircleDetailActivity.IDS, "" + titleBean.getId());
                intent1.putExtra(FriendCircleDetailActivity.USER_IDS, "" + titleBean.getUserId());
                startActivity(intent1);
            }
        });

        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
                    case R.id.rl_content:


                        toast("1111111111");
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
                    case R.id.rl_content:


                        toast("1111111111");
                        break;
                }
            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageAttentionFriendEvent(AttentionFriendEvent event) {

        Log.e(TAG, "messageCityEvent: loadType" + event.getLoadType());

    }

    @Override
    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("保存图片");
        alert.setMessage("你确定要保存图片吗?");
        alert.setNegativeButton("取消", null);
        alert.setPositiveButton("确定", (dialog, which) -> {
            //            rxPermissions
            //                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            //                    .subscribe(granted -> {
            //                        if (granted) {
            //                            if (uri != null) {// Always true pre-M
            //                                savePhoto(uri);
            //                            }
            //                        } else {
            //                            ToastUtils.ToastShort("缺少必要权限,请授予权限");
            //                        }
            //                    });
            //            dialog.dismiss();

        });
        alert.show();
    }

    @Override
    public void load(Context context, Uri uri, ImageWatcher.LoadCallback lc) {
        Glide.with(context).asBitmap().load(uri.toString()).into(new GlideSimpleTarget(lc));
    }

//    @Override
//    public void updateRecommend(int hotType) {
//        Log.e(TAG, "updateRecommend: 更新接口1====" + hotType);
//    }
//
//    @Override
//    public void updateAttention(int hotType) {
//        Log.e(TAG, "updateAttention: hotType==="+hotType );
//    }
}
