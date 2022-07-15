package heyong.intellectPinPang;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.elvishew.xlog.BuildConfig;
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
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import org.jetbrains.annotations.Nullable;

import heyong.intellectPinPang.live.AgoraManager;
import heyong.intellectPinPang.soundnet.framework.PreprocessorFaceUnity;
import heyong.intellectPinPang.soundnet.vlive.Config;
import heyong.intellectPinPang.soundnet.vlive.agora.AgoraEngine;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.RtcEventHandler;
import heyong.intellectPinPang.soundnet.vlive.protocol.ClientProxy;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import heyong.intellectPinPang.soundnet.vlive.utils.UserUtil;
import heyong.intellectPinPang.utils.GlobalSettings;
import io.agora.capture.video.camera.CameraManager;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.RtmClient;

//import org.android.agoo.huawei.HuaWeiRegister;
//import org.android.agoo.mezu.MeizuRegister;
//import org.android.agoo.oppo.OppoRegister;
//import org.android.agoo.vivo.VivoRegister;
//import org.android.agoo.xiaomi.MiPushRegistar;


/**
 * @ClassName:MyApp
 * @PackageName:com.hulianchuxing.app
 * @Create On 2018/1/24 0024   11:04 test
 * @author:wanghongfu
 * @Copyrights 2018/1/24 0024 handongkeji All rights reserved.
 * <p>
 * E:\Bugly-Android-Demo-master\BuglyHotfixEasyDemo
 * API Key  重置 API Key
 * 28f7411a10dd30b6ec2364c6d1ec276c
 * <p>
 * CED82CE4C4B14BDBAF9512B6F50F4D7D   APP ID
 * talkingdata.ced82ce4c4b14bdbaf9512b6f50f4d7d  灵动分析Schema
 * <p>
 * <p>
 * User Key 
 * 19e6e3c25fa84365a7e3ed2b9f13caf9
 * //3.4.1 5.1.1    6.5  4.1.0
 */
public class MyApp extends TinkerApplication {
    private SharedPreferences mPref;
    private Config mConfig;
    private CameraManager mCameraVideoManager;
    private GlobalSettings globalSettings;
    private AgoraEngine mAgoraEngine;

    public MyApp() {
        super(ShareConstants.TINKER_ENABLE_ALL, "heyong.intellectPinPang.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = getSharedPreferences(Global.Constants.SF_NAME, Context.MODE_PRIVATE);
        mConfig = new Config(this);
        initXLog();
        //初始化引擎
        initEngine(getResources().getString(R.string.agora_app_id));

//        AgoraManager.getInstance().init(this);
        initVideoGlobally();
    }

    public Config config() {
        return mConfig;
    }

    public SharedPreferences preferences() {
        return mPref;
    }

    public void initEngine(String appId) {
        mAgoraEngine = new AgoraEngine(this, appId);
    }

    public RtcEngine rtcEngine() {
        return mAgoraEngine != null ? mAgoraEngine.rtcEngine() : null;
    }

    public RtmClient rtmClient() {
        return mAgoraEngine != null ? mAgoraEngine.rtmClient() : null;
    }

    public ClientProxy proxy() {
        return ClientProxy.instance();
    }

    public void registerRtcHandler(RtcEventHandler handler) {
        mAgoraEngine.registerRtcHandler(handler);
    }

    public void removeRtcHandler(RtcEventHandler handler) {
        mAgoraEngine.removeRtcHandler(handler);
    }

    public CameraManager cameraVideoManager() {
        return mCameraVideoManager;
    }

    private void initVideoGlobally() {
        new Thread(() -> {
            //FURenderer.initFURenderer(getApplicationContext());
            PreprocessorFaceUnity preprocessor =
                    new PreprocessorFaceUnity(this);
            mCameraVideoManager = new CameraManager(
                    this, preprocessor);
            mCameraVideoManager.setCameraStateListener(preprocessor);
        }).start();
    }

    public GlobalSettings getGlobalSettings() {
        if (globalSettings == null) {
            globalSettings = new GlobalSettings();
        }
        return globalSettings;
    }

    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ?
                        LogLevel.DEBUG : LogLevel.INFO)                         // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                .tag("Okhttp")                                               // Specify TAG, default: "X-LOG"
                //.t()                                                            // Enable thread info, disabled by default
                .st(Global.Constants.LOG_CLASS_DEPTH)                           // Enable stack trace info with depth 2, disabled by default
                // .b()                                                            // Enable border, disabled by default
                .jsonFormatter(new DefaultJsonFormatter())                      // Default: DefaultJsonFormatter
                .xmlFormatter(new DefaultXmlFormatter())                        // Default: DefaultXmlFormatter
                .throwableFormatter(new DefaultThrowableFormatter())            // Default: DefaultThrowableFormatter
                .threadFormatter(new DefaultThreadFormatter())                  // Default: DefaultThreadFormatter
                .stackTraceFormatter(new DefaultStackTraceFormatter())          // Default: DefaultStackTraceFormatter
                .build();

        Printer androidPrinter = new AndroidPrinter();                          // Printer that print the log using android.util.Log

        String flatPattern = "{d yy/MM/dd HH:mm:ss} {l}|{t}: {m}";
        Printer filePrinter = new FilePrinter                                   // Printer that print the log to the file system
                .Builder(UserUtil.appLogFolderPath(this))         // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())                 // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(new FileSizeBackupStrategy(
                        Global.Constants.APP_LOG_SIZE))                         // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(new FileLastModifiedCleanStrategy(
                        Global.Constants.LOG_DURATION))
                .flattener(new PatternFlattener(flatPattern))                   // Default: DefaultFlattener
                .build();

        XLog.init(                                                              // Initialize XLog
                config,                                                         // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,
                filePrinter);
    }


    @Override
    public void onTerminate() {
        XLog.i("onApplicationTerminate");
        super.onTerminate();
        mAgoraEngine.release();
    }


}