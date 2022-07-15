package heyong.intellectPinPang.ui.friendcircle.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.emotion.ImageModel;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.event.ClearShowPopKeyEvent;
import heyong.intellectPinPang.event.ShowPopKeyEvent;
import heyong.intellectPinPang.ui.friendcircle.adapter.HorizontalRecyclerviewAdapter;
import heyong.intellectPinPang.ui.friendcircle.adapter.NoHorizontalScrollerVPAdapter;
import heyong.intellectPinPang.utils.EmotionUtils;
import heyong.intellectPinPang.utils.GlobalOnItemClickManagerUtils;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.GifSizeFilter;
import heyong.intellectPinPang.widget.emotionkeyboardview.EmotionKeyboard;
import heyong.intellectPinPang.widget.emotionkeyboardview.NoHorizontalScrollerViewPager;

import static android.app.Activity.RESULT_OK;

/**
 * Created by zejian
 * Time  16/1/6 下午5:26
 * Email shinezejian@163.com
 * Description:表情主界面
 */
public class EmotionMainFragment extends EmtionBaseFragment {
    public static final String TAG = EmotionMainFragment.class.getSimpleName();
    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT = "bind_to_edittext";
    //是否隐藏bar上的编辑框和发生按钮
    public static final String HIDE_BAR_EDITTEXT_AND_BTN = "hide bar's editText and btn";
    public static final String REPLTY_NAME = "reply_name";
    public static final String REPLT_ID = "reply_id";

    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private int CurrentPosition = 0;
    //底部水平tab
    private RecyclerView recyclerview_horizontal;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;

    private EditText bar_edit_text;
    //    private ImageView bar_image_add_btn;
    private TextView bar_btn_send;
    //    private LinearLayout rl_editbar_bg;
    private ImageView ivFangDa;
    //需要绑定的内容view
    private View contentView;

    //不可横向滚动的ViewPager
    private NoHorizontalScrollerViewPager viewPager;

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;

    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private boolean isHidenBarEditTextAndBtn = false;

    List<Fragment> fragments = new ArrayList<>();

    private FragmentInteraction listterner;

    private boolean isFullScrren = false;

    private ImageView ivUploadClose, ivUploadImage;
    private RelativeLayout rlUploadImage;
    private ImageView ivToUpload;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private String mReplyId = "";

    private String pathImage = "";

    // 1 定义了所有activity必须实现的接口方法
    public interface FragmentInteraction {
        void process(String str, String path, String replyId);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentInteraction) {
            listterner = (FragmentInteraction) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }


    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false);
        isHidenBarEditTextAndBtn = args.getBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN);
        //获取判断绑定对象的参数
        isBindToBarEditText = args.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT);
        mReplyId = args.getString(EmotionMainFragment.REPLT_ID);
        if (mReplyId == null) {
            mReplyId = "";
        }
        String replyContent = args.getString(EmotionMainFragment.REPLTY_NAME);
        if (replyContent == null) {
            replyContent = "";
        }
        initView(rootView);
        mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))//绑定表情面板
                .bindToContent(contentView)//绑定内容view
//                .bindToEditText(((EditText) contentView))//判断绑定那种EditView
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.bar_edit_text)))//判断绑定那种EditView
                .bindToEmotionButton(rootView.findViewById(R.id.emotion_button))//绑定表情按钮
                .build();
        initListener();
        replaceFragment();
//        initDatas();
        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(getActivity());

        if (isBindToBarEditText) {
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(bar_edit_text);

        } else {
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText((EditText) contentView);
            mEmotionKeyboard.bindToEditText((EditText) contentView);
        }
        forceOpenSoftKeyboard(getActivity());
        RxBus.getDefault().toObservable(ShowPopKeyEvent.class).subscribe(tagEvents -> {
            String contenet = tagEvents.getContenet();
            String replyId = tagEvents.getReplyId();
            if (!TextUtils.isEmpty(replyId)) {
                mReplyId = replyId;
            } else {
                mReplyId = "";
            }
            Log.e(TAG, "onCreateView: ");
            if (!TextUtils.isEmpty(contenet)) {
                bar_edit_text.setHint("@" + contenet);
            }
            forceOpenSoftKeyboard(getActivity());

        });
        RxBus.getDefault().toObservable(ClearShowPopKeyEvent.class).subscribe(tagEvents -> {
            bar_edit_text.setText("");
        });
        if (!TextUtils.isEmpty(replyContent)) {
            bar_edit_text.setHint("@" + replyContent);
        }
        return rootView;
    }

    /**
     * 显示软键盘
     */
    private void showSoftInput(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, 0);
    }

    /**
     * 绑定内容view
     *
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        viewPager = (NoHorizontalScrollerViewPager) rootView.findViewById(R.id.vp_emotionview_layout);
        recyclerview_horizontal = (RecyclerView) rootView.findViewById(R.id.recyclerview_horizontal);
        bar_edit_text = (EditText) rootView.findViewById(R.id.bar_edit_text);
        ivFangDa = rootView.findViewById(R.id.iv_fangda);
        rlUploadImage = rootView.findViewById(R.id.rl_upload_image);
        ivUploadImage = rootView.findViewById(R.id.iv_upload_image);
        ivUploadClose = rootView.findViewById(R.id.iv_upload_close);
        ivToUpload = rootView.findViewById(R.id.iv_to_upload);
        ivUploadClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlUploadImage.setVisibility(View.GONE);
                pathImage = "";

            }
        });

        ivToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new RxBusSubscriber<Boolean>() {
                            @Override
                            protected void onEvent(Boolean aBoolean) {
                                if (aBoolean) {

                                    Matisse.from(EmotionMainFragment.this)
                                            //选择图片
                                            .choose(MimeType.ofImage())
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                            .maxSelectable(1)
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .imageEngine(new PicassoEngine())
                                            .forResult(REQUEST_CODE_CHOOSE);


                                } else {
                                    Toast.makeText(getActivity(), "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT);
                                    //只要有一个权限被拒绝，就会执行

                                    showMissingPermissionDialog();
                                }
                            }
                        });

            }
        });

//        bar_image_add_btn= (ImageView) rootView.findViewById(R.id.bar_image_add_btn);
        bar_btn_send = (TextView) rootView.findViewById(R.id.bar_btn_send);
//        rl_editbar_bg = (LinearLayout) rootView.findViewById(R.id.rl_editbar_bg);
        if (isHidenBarEditTextAndBtn) {//隐藏
            bar_edit_text.setVisibility(View.GONE);
//            bar_image_add_btn.setVisibility(View.GONE);
            bar_btn_send.setVisibility(View.GONE);
//            rl_editbar_bg.setBackgroundResource(R.color.bg_edittext_color);
        } else {
            bar_edit_text.setVisibility(View.VISIBLE);
//            bar_image_add_btn.setVisibility(View.VISIBLE);
            bar_btn_send.setVisibility(View.VISIBLE);
//            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);
        }
        bar_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listterner != null) {
                    Log.e("mReplyId", "onClick: " + mReplyId);
                    listterner.process("" + bar_edit_text.getText().toString(), "" + pathImage, "" + mReplyId);
                }
            }
        });
        ivFangDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScrren) {
                    isFullScrren = false;
                    ViewGroup.LayoutParams layoutParams = bar_edit_text.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    bar_edit_text.setLayoutParams(layoutParams);
                } else {
                    isFullScrren = true;
                    ViewGroup.LayoutParams layoutParams = bar_edit_text.getLayoutParams();
                    layoutParams.height = 900;
                    bar_edit_text.setLayoutParams(layoutParams);
                }

            }
        });
    }

    public void forceOpenSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private ArrayList<String> dataList = new ArrayList<String>();//上传图片的list 集合

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            List<String> pathList = Matisse.obtainPathResult(data);


            Log.e("OnActivityResult ", "-=====" + String.valueOf(Matisse.obtainOriginalState(data))
                    + "   ====" + Matisse.obtainResult(data));
            if (pathList.size() == 1) {
                String picPath = pathList.get(0);
                pathImage = picPath;
                ImageLoader.loadImage(ivUploadImage, picPath);
                rlUploadImage.setVisibility(View.VISIBLE);


//                    Log.e(TAG, "onActivityResult: 单张图片 路径===" + pathList.get(0));
//                    showLoading();
//                    picSize = 1;
//                    List<String> images = new ArrayList<>();
//                    dataList.clear();
//                    Log.e(TAG, "onActivityResult: pathList===" + new Gson().toJson(pathList));
//                    for (String item : pathList) {
//                        images.add(item);
//                        dataList.add(item);
//                    }
////                        mViewModel.imgItems.addAll(images);
//                    Log.e(TAG, "getUploadTokenLiveDataImage: size====" + mViewModel.imgItems.size());
//
//                    mBinding.gridImageView.setImages(images);
//                    uoloadMatchImage(pathList.get(0));
            }


        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("当前应用缺少必要权限。\\n\\n请点击\\\"设置\\\"-\\\"权限\\\"-打开所需权限");

            // 拒绝, 退出应用
            builder.setNegativeButton("取消",
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

            builder.setPositiveButton("设置",
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
     * 启动应用的设置
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

    /**
     * 初始化监听器
     */
    protected void initListener() {

    }


    private void replaceFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1 = (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        Bundle b = null;
//        for (int i=0;i<7;i++){
        b = new Bundle();
//            b.putString("Interge","Fragment-"+i);
        FragmentNormal fg = FragmentNormal.newInstance(FragmentNormal.class, b);
        fragments.add(fg);
//        }

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        return mEmotionKeyboard.interceptBackPress();
    }


    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected void initDatas() {
        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
//        for (int i=0 ; i<fragments.size(); i++){
//            if(i==0){
        ImageModel model1 = new ImageModel();
        model1.icon = getResources().getDrawable(R.drawable.ic_emotion);
        model1.flag = "经典笑脸";
        model1.isSelected = true;
        list.add(model1);
//            }else {
//                ImageModel model = new ImageModel();
//                model.icon = getResources().getDrawable(R.drawable.ic_plus);
//                model.flag = "其他笑脸" + i;
//                model.isSelected = false;
//                list.add(model);
//            }
//        }

        //记录底部默认选中第一个
        CurrentPosition = 0;
        AccountHelper.setInteger(CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(getActivity(), list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = AccountHelper.getInteger(CURRENT_POSITION_FLAG);
//                int oldPosition = SharedPreferencedUtils.getInteger(getActivity(), CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                AccountHelper.setInteger(CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        listterner = null;
    }
}


