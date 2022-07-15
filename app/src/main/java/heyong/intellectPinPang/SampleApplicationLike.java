package heyong.intellectPinPang;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter;
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.faceunity.FURenderer;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.entry.DefaultApplicationLike;
//import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
//import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.api.AdApi;
import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.ApiConfigration;
import heyong.handong.framework.api.FriendApi;
import heyong.handong.framework.api.LiveApi;
import heyong.handong.framework.api.OtherApi;
import heyong.handong.framework.api.OtherHoutaiApi;
import heyong.intellectPinPang.bean.Constants;
import heyong.intellectPinPang.event.HotFixAddEvent;
import heyong.intellectPinPang.model.AddQueryParameterInterceptor;
import heyong.intellectPinPang.model.FriendService;
import heyong.intellectPinPang.soundnet.framework.PreprocessorFaceUnity;
import heyong.intellectPinPang.soundnet.vlive.Config;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.RtcEventHandler;
import heyong.intellectPinPang.soundnet.vlive.protocol.ClientProxy;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import heyong.intellectPinPang.soundnet.vlive.utils.UserUtil;
import heyong.intellectPinPang.utils.GlideImageLoader;
import heyong.intellectPinPang.utils.ProxyHelper;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.view.CropImageView;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.RtmClient;

import static android.content.Context.ACTIVITY_SERVICE;

//import com.meituan.android.walle.WalleChannelReader;

/**
 * 自定义ApplicationLike类.
 * <p>
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br/>
 *
 * @author wenjiewu
 * @since 2016/11/7
 */
public class SampleApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker.SampleApplicationLike";
    private static Context instance;



    private void configApi() {
        ApiConfigration apiConfig = new ApiConfigration();
        apiConfig
//                .debugBaseUrl("http://192.168.1.27")
//                .debugBaseUrl("http://192.168.101.27:9590")
//                .releaseBaseUrl("http://192.168.101.27:9590")
//                .releaseBaseUrl(""+baseUrl+"9591")
//                .debugBaseUrl(""+baseUrl+"9591")
                .releaseBaseUrl("" + Constants.littleServer + Constants.port)
                .debugBaseUrl("" + Constants.littleServer + Constants.port)
//                .debugBaseUrl("http://47.93.151.11:9590")
//                .releaseBaseUrl("http://47.93.151.11:9590")
                .addInterceptor(new AddQueryParameterInterceptor());
        Api.config(apiConfig);
        OtherApi.config(apiConfig, Constants.littleServer, "" + Constants.pinPaiport);//品牌 调用接口
        OtherHoutaiApi.config(apiConfig, Constants.uatBgUrl);//后台地址
        FriendApi.config(apiConfig, Constants.friendServer);//后台地址
        LiveApi.config(apiConfig, Constants.LiveServer);//后台地址
        AdApi.config(apiConfig, Constants.LiveLocalServer, "" + Constants.adPort);//广告模块接口

//        LiveApi.config(apiConfig, Constants.liveUrl);
    }

    Handler mainHandler;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        instance = base;
        MultiDex.install(base);
        Beta.installTinker(this);

    }

    private void initX5Web() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(instance, cb);
    }


    public static Context getContext() {
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(
            Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }


    /**
     * OnekeyShare oks = new OnekeyShare();
     * // title标题，微信、QQ和QQ空间等平台使用
     * oks.setTitle(getString(R.string.share));
     * // titleUrl QQ和QQ空间跳转链接
     * oks.setTitleUrl("http://sharesdk.cn");
     * // text是分享文本，所有平台都需要这个字段
     * oks.setText("我是分享文本");
     * // setImageUrl是网络图片的url
     * oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
     * // url在微信、Facebook等平台中使用
     * oks.setUrl("http://sharesdk.cn");
     * // 启动分享GUI
     * oks.show(MobSDK.getContext());
     * 登录
     * Platform plat = ShareSDK.getPlatform(QQ.NAME);
     * //移除授权状态和本地缓存，下次授权会重新授权
     * plat.removeAccount(true);
     * //SSO授权，传false默认是客户端授权
     * plat.SSOSetting(false);
     * //授权回调监听，监听oncomplete，onerror，oncancel三种状态
     * plat.setPlatformActionListener(platformActionListener);
     * //抖音登录适配安卓9.0dd
     * //ShareSDK.setActivity(MainActivity.this);
     * //要数据不要功能，主要体现在不会重复出现授权界面
     * plat.showUser(null);
     */
    public Application application() {
        return getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Resources res = super.getResources(getContext().getResources());
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());




        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
//                Toast.makeText(getApplication(), "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplication(), "应用开始修复", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "补丁开始下载", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
//                Toast.makeText(getApplication(),
//                        String.format(Locale.getDefault(), "%s %d%%",
//                                Beta.strNotificationDownloading,
//                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Toast.makeText(getApplication(), "修复中", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "正在安装补丁", Toast.LENGTH_SHORT).show();
//                Beta.applyDownloadedPatch();
                EventBus.getDefault().post(new HotFixAddEvent());
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplication(), "修复失败", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplication(), "修复成功", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "补丁安装成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplication(), "修复失败", Toast.LENGTH_SHORT).show();

//                Toast.makeText(getApplication(), "补丁安装失败", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "补丁安装失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {

            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        // 多渠道需求塞入
        // String channel = WalleChannelReader.getChannel(getApplication());
        // Bugly.setAppChannel(getApplication(), channel);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(getApplication(), "459ad70c8c", true);

        ProxyHelper.initProxy(getApplication());

//        MobclickAgent.setScenarioType(instance, MobclickAgent.EScenarioType.E_UM_NORMAL);
        Utils.init(instance);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);

        initImagePicker();//图片选择加载器初始化

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(3)
                //                    .methodOffset(1)
                //                    .logStrategy()
                .tag("ping")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

//        Logger.e(s+"e");
//        Logger.w(s+"w");
//        Logger.i(s+"i");
//        Logger.d(s+"d");
//        Logger.v(s+"v");

        Logger.addLogAdapter(new DiskLogAdapter());
        configApi();
        //初始化友盟
        initUmengPush();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(instance);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(getContext());
        builder.statusBarDrawable = R.drawable.img_logo_start;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL//
                | Notification.FLAG_SHOW_LIGHTS; // 设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = //
                Notification.DEFAULT_SOUND | // 设置为铃声
                        Notification.DEFAULT_VIBRATE | // 设置为、震动
                        Notification.DEFAULT_LIGHTS; // 设置为呼吸灯闪烁
        JPushInterface.setPushNotificationBuilder(1, builder);


        //解决9.0对非官方公开API 方法或接口有弹窗提示
        closeAndroidPDialog();

        //用户信用信息中 带颜色的CardView
//        CardUtils.init();

        AccountHelper.getInstance().setOnLogoutListener(() -> {


//      UmHelper.removeAlias(AccountHelper.getUserId(), this);
        });
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        //判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        String ip = intToIp(ipAddress);
//        Log.e(TAG, "onCreate: okhttp===" + ip);
//        AccountHelper.setIpAddress(ip);
//
//        MyApp myApplication = (MyApp) this.instance;
//
//
//        myReceiver = new MyReceiver();
//        intentFilter = new IntentFilter("chanchawReceiver");


//        JLibrary.InitEntry(this); //移动安全联盟统一SDK初始化
//        TCAgent.LOG_ON = true;
//        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
//        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
//        TCAgent.init(instance, "CED82CE4C4B14BDBAF9512B6F50F4D7D", "tengxun");
//        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
//        TCAgent.setReportUncaughtExceptions(true);
//        initBuglyHotFix();

        mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                //需要在主线程执行的逻辑
                initX5Web();
            }
        });
    }


    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }





    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //192.168.101.34
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    private void initUmengPush() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
// 参数一：当前上下文context；
// 参数二：应用申请的Appkey（需替换）；
// 参数三：渠道名称；
// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(getApplication(), "5fb5c904257f6b73c0971c20", "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "870f263f105b91cddb4f2f1d4e77ff4d");
        UMConfigure.setLogEnabled(true);
////        你的初始化未完成，请在Application的onCreate中调用 UMConfigure.init(this,appkey,channel,UMConfigure.DEVICE_TYPE_PHONE,"");/
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.setProcessEvent(true);

//        MiPushRegistar.register(this, "2882303761518956140", "5191895653140");//小米
//        HuaWeiRegister.register(this);//华为的环信的注册
//        OppoRegister.register(this, "6176a5bfb8a24cf2ba8fccd5786b8d60", "a5f69b9f47b045e38c145ef9630afa78");
//        MeizuRegister.register(this, "138914", "03bc4b10dcde42bfadb65672138308b9");//魅族
//        VivoRegister.register(this);

//        PlatformConfig.setWeixin("wx40be8143c8e8956d", "091510810f843ff687e13797a1c9d773");//
//        PlatformConfig.setQQZone("1110086555", "zNBwhAr43WXce1ra");//
////        PlatformConfig.setQQZone("ID1110086555", "zNBwhAr43WXce1ra");//
//        PlatformConfig.setSinaWeibo("3365249122", "66646c5f7f48b66703f600b716a633e1", "https://www.baidu.com");
//        PlatformConfig.setAlipay("2019022035349826");

        //获取消息推送代理示例
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setNoDisturbMode(23, 0, 7, 0);//SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
//        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
//        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动
//        mPushAgent.setDisplayNotificationNumber(2);//通知栏按数量显示
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.e(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//        });
//
//        UmengMessageHandler umengMessageHandler = new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                Log.e(TAG, "getNotification:1 " + new Gson().toJson(msg));
////                Looper.prepare();
////                Looper.loop();
//                //Log.e(TAG, "umengMessageHandler: builder_id===" + msg.builder_id);
//                /**
//                 * context:上下文
//                 * uMessage:表示当前传递过来的消息，在消息中，我们通过变量builder_id判断使用哪种样式
//                 */
//                Map<String, String> extra = msg.extra;
//                if (extra != null) {
//                    String ids = extra.get("id");
//                    String text = msg.text;
////                    String title = extra.get("text");
//                    EventBus.getDefault().post(new ShowSuccessEvent("" + ids, "" + text));
////                    String type = extra.get("type");
////                    if (type != null && !type.equals("") && !TextUtils.isEmpty(type)) {
////                        if (Integer.parseInt(type) == 1) {
//////                            if (!MusicPlayerService.getInstance().isPlaying() && MusicPlayerService.getInstance().getPlayingMusic() == null) {
//////
//////                            } else {
//////                                /*停止循环播放的音乐*/
//////                                MusicPlayerService.getInstance().pause();
//////                            }
////                        }
////                    }
//                }
//
//                return CreateNotification(context, msg);
//            }
//
//            private Notification CreateNotification(Context context, UMessage msg) {
//                Log.e(TAG, "getNotification:2 " + new Gson().toJson(msg));
//
//                switch (msg.builder_id) {
//                    case 1:
//                        //创建通知栏对象
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
//                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                                getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//                        return builder.getNotification();
//                    default:
//                        Log.e(TAG, "CreateNotification: 系统Notification" );
//                        return super.getNotification(context, msg);
//                }
//            }
//
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage uMessage) {
//                //自定义消息的内容是放在uMessage中的custome参数中的
//                Log.e(TAG, "getNotification:3 " + new Gson().toJson(uMessage));
//
//                if (uMessage == null) {
//                    return;
//                }
//                HashMap<String, String> hm = (HashMap<String, String>) uMessage.extra;
//                String type = "";
//                String url = "";
//                String innerUrl = "";
//                if (hm != null && hm.size() > 0) {
//                    if (hm.containsKey("type")) {
//                        type = hm.get("type");
//                    }
//                    if (hm.containsKey("url")) {
//                        url = hm.get("url");
//                    }
//                    if (hm.containsKey("innerUrl")) {
//                        innerUrl = hm.get("innerUrl");
//                    }
//                }
//                if (type.equalsIgnoreCase("login")) {
//                    //跳转到登录页面
//                } else if (type.equalsIgnoreCase("regist")) {
//                    //跳转到注册页面
//                } else if (type.equalsIgnoreCase("im")) {
//                    //跳转到聊天页面
//                } else {
//                    //跳转到web页面
//                    if (TextUtils.isEmpty(innerUrl)) {
//                        innerUrl = "http://www.baidu.com";
//                    }
//                }
//
//            }
//        };
//        mPushAgent.setMessageHandler(umengMessageHandler);
//        String pushIntentServiceClass = mPushAgent.getPushIntentServiceClass();
//        Log.e(TAG, "initUmengPush: "+pushIntentServiceClass );
////        adHandler.handleMessage();
//
//
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//
//            @Override
//            public void handleMessage(Context context, UMessage uMessage) {
//                super.handleMessage(context, uMessage);
////            String str=message.replaceAll("\\\\", "");//将URL中的反斜杠替换为空  加上之后收不到消息
//                    Log.e(TAG, "message=" + uMessage);    //消息体
//                    Log.e(TAG, "custom=" + uMessage.custom);    //自定义消息的内容
//                    Log.e(TAG, "title=" + uMessage.title);    //通知标题
//                    Log.e(TAG, "text=" + uMessage.text);    //通知内容
//                    //消息处理
//                    Map<String, String> extra = uMessage.extra;
//                    String ids = extra.get("id");
//                    Intent intent=new Intent();
//                    //跳转到  抽签的界面
//                    intent.setClass(context, CoachDrawLotsHostAndGusetActivity.class);
//                    intent.putExtra("ids", ids);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//            }
//
//
//            @Override
//            public void launchApp(Context context, UMessage msg) {
//                super.launchApp(context, msg);
//
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage msg) {
//                super.openUrl(context, msg);
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
//            }
//
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//
//            }
//        };
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }

    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    {
        PlatformConfig.setWeixin("wx41a81a9c33549fd1", "bb59e8262fea490c1564e079afb7d6ea");//
        PlatformConfig.setQQZone("1110991572", "crKyIBYMdixkOHeh");//
        PlatformConfig.setSinaWeibo("3365249122", "66646c5f7f48b66703f600b716a633e1", "https://www.baidu.com");
        PlatformConfig.setAlipay("2019022035349826");
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> new MaterialHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> new ClassicsFooter(context));
    }

    public SampleApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }


}
