package heyong.intellectPinPang.bean;



import android.os.Binder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.BeautyItemData;

/**
 * 10.0.2.15
 * 静态函数 adb connect 127.0.0.1:7555
 * gradlew processDebugManifest --stacktrace
 */
public class Constants {
    public static final String BASIC_INTERCEPTOR = "Basic YnJvd3Nlcjpicm93c2Vy";
    public static String BaseUrl = "http://api.healthbedmate.com/";
    public static String API_DEBUG_SERVER_URL = "http://49.233.136.163:8085/";//闪屏页接口
    public static String pinPaiport = "19580";

//      public static String littleServer="http://192.168.101.75:";//常浩地址    或者蒲公英
//      public static String littleServer="http://192.168.0.110:";//常浩地址    或者蒲公英

//    public static String littleServer = "http://47.93.151.11:"; //测试服务器正式地址
    public static String LiveLocalServer = "http://121.89.242.57:"; //测试服务器正式地址
//    public static String port = "9590";//测试port
//    public static String uatBgUrl = "http://39.103.221.204:9580"; //测试环境后台地址
    public static String adPort="19580";

    public static String littleServer="http://39.103.221.204:";//正式服务器上的地址
    public static String port="19590";//服务器port
    public static String uatBgUrl="http://39.103.221.204:19580";//正式环境后台地址

    public static String friendServer="http://39.103.221.204:28080";//朋友圈
    public static  String LiveServer="http://121.89.242.57:9570";//  直播线上
//    public static  String LiveServer="http://192.168.101.75:9570";
    //后台地址
    public static String allUrl = littleServer + port;//正式端口号   轮训接口
//    public static String liveUrl = "http://app.xlttsports.com/";//直播正式地址

    //阿里云直播
    private static List<String> live_push_button;
    private static List<String> live_pull_button;
    private static List<String> live_play_button;
    private static List<String> live_audience_button;
    private static List<String> live_anchor_button;
    private static List<BeautyItemData> live_beauty_skin_name;
    private static HashMap<String, Integer> live_img_soure;

    public static HashMap<String, Integer> getLive_img_soure() {
        return live_img_soure;
    }

    public static final String HTTP_DNS_ACCOUNT_ID = "182692";//线上

    public static final String LIVE_EXTRA_INFO = "alilivesdk-demo-user-unkonwn";

    static {

        /**
         * anchor页面按钮
         */
        live_anchor_button = new ArrayList<String>();
        live_img_soure = new HashMap<>();
        live_img_soure.put("开始推流", R.drawable.live_push);
        live_img_soure.put("美颜", R.drawable.live_beauty);
        live_img_soure.put("音效", R.drawable.live_sound);
        live_img_soure.put("摄像头", R.drawable.live_carmer);
        live_img_soure.put("PK", R.drawable.live_pk);
        live_img_soure.put("静音", R.drawable.mute_btn);
        live_img_soure.put("调节参数", R.drawable.live_adjust_parm);
        live_img_soure.put("数据指标", R.drawable.live_data);
        live_anchor_button.add("开始推流");
        live_anchor_button.add("美颜");
        live_anchor_button.add("音效");
        live_anchor_button.add("摄像头");
        live_anchor_button.add("PK");
        live_anchor_button.add("静音");
        live_anchor_button.add("调节参数");
        live_anchor_button.add("数据指标");

        /**
         * 观众页面按钮
         */
        live_audience_button = new ArrayList<>();
        live_audience_button.add("连麦");
        live_audience_button.add("美颜");
        live_audience_button.add("数据指标");
        live_img_soure.put("连麦", R.drawable.live_microphone);
        live_img_soure.put("美颜", R.drawable.live_beauty);
        live_img_soure.put("数据指标", R.drawable.live_data);

        /**
         * push页面的按钮
         * */
        live_push_button = new ArrayList<>();
        live_push_button.add("开始推流");
        live_push_button.add("美颜");
        live_push_button.add("音效");
        live_push_button.add("静音");
        live_push_button.add("摄像头");
        live_push_button.add("调节参数");
        live_push_button.add("数据指标");

        /**
         * pull页面按钮
         */
        live_pull_button = new ArrayList<>();
        live_pull_button.add("结束观看");
        live_pull_button.add("静音");
        live_pull_button.add("听筒切换");
        live_img_soure.put("结束观看", R.drawable.finish_play);
        live_img_soure.put("听筒切换", R.drawable.telephone_change);

        /**
         * play页面按钮
         */
        live_play_button = new ArrayList<>();
        live_play_button.add("结束观看");

        /**
         * beauty页面的美肌
         * */
        live_beauty_skin_name = new ArrayList<>();
        live_beauty_skin_name.add(new BeautyItemData("磨皮", false, 60));
    }

    public static List<String> getPushActivityButtonList() {
        return live_push_button;
    }

    public static List<String> getPullActivityButtonList() {
        return live_pull_button;
    }

    public static List<String> getPlayActivityButtonList() {
        return live_play_button;
    }

    public static List<String> getAudienceButtonList() {
        return live_audience_button;
    }

    public static List<String> getAnchorActivityButtonList() {
        return live_anchor_button;
    }

    public static List<BeautyItemData> getBeautySkinNameList() {
        return live_beauty_skin_name;
    }


    public static String AccessKey = "9CDbYNN0SXLc5Uv-W1awvSJEHHhgEcWR81jRMD83";//此处填你自己的AccessKey
    public static String SecretKey = "uiH9K-39xgGmWxmS3vVMxhEUKthDoVQgn22kNImx";//此处填你自己的SecretKey
    // 小视频相关配置请参考:https://cloud.tencent.com/document/product/584/15540
    // ************在腾讯云开通各项服务后，将您的配置替换到如下的几个定义中************
    // 业务Server的地址
    public static final String APP_SVR_URL = "http://148.70.58.174:8001"; //如果您的服务器没有部署https证书，这里需要用http

    // 小视频做统计用的，您可以不用关心
    public static final String DEFAULT_ELK_HOST = "https://qcloud.com";
    // BGM列表地址
    public static final String SVR_BGM_GET_URL = "http://downlist.hdelves.com/bgmlist/bgm_list.json";

    // 设置第三方平台的appid和appsecrect，大部分平台进行分享操作需要在第三方平台创建应用并提交审核，通过后拿到appid和appsecrect并填入这里，具体申请方式请参考http://dev.umeng.com/social/android/operation
    // 有关友盟组件更多资料请参考这里：http://dev.umeng.com/social/android/quick-integration
    public static final String WEIXIN_SHARE_ID = "";
    public static final String WEIXIN_SHARE_SECRECT = "";

    public static final String SINA_WEIBO_SHARE_ID = "";
    public static final String SINA_WEIBO_SHARE_SECRECT = "";
    public static final String SINA_WEIBO_SHARE_REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

    public static final String QQZONE_SHARE_ID = "";
    public static final String QQZONE_SHARE_SECRECT = "";

    // bugly组件Appid，bugly为腾讯提供的用于App Crash收集和分析的组件
    public static final String BUGLY_APPID = "0";
    //**********************************************************************

    /**
     * 常量字符串
     */
    public static final String USER_ID = "userid";
    public static final String USER_PWD = "userpwd";

    // 主播退出广播字段
    public static final String COVER_PIC = "cover_pic";
    public static final String PLAY_URL = "play_url";
    public static final String PUSHER_AVATAR = "pusher_avatar";
    public static final String PUSHER_ID = "pusher_id";
    public static final String PUSHER_NAME = "pusher_name";
    public static final String FILE_ID = "file_id";
    public static final String TIMESTAMP = "timestamp";
    public static final String ACTIVITY_RESULT = "activity_result";

    public static final String TCLIVE_INFO_LIST = "txlive_info_list";
    public static final String TCLIVE_INFO_POSITION = "txlive_info_position";
    /**
     * UGC 编辑的的参数
     */
    public static final String VIDEO_EDITER_PATH = "key_video_editer_path"; // 路径的key
    public static final String RECORD_CONFIG_BITE_RATE = "record_config_bite_rate";
    public static final String RECORD_CONFIG_FPS = "record_config_fps";
    /**
     * UGC小视频录制信息
     */
    public static final String VIDEO_RECORD_TYPE = "type";
    public static final String VIDEO_RECORD_RESULT = "result";
    public static final String VIDEO_RECORD_DESCMSG = "descmsg";
    public static final String VIDEO_RECORD_VIDEPATH = "path";
    public static final String VIDEO_RECORD_COVERPATH = "coverpath";
    public static final String VIDEO_RECORD_ROTATION = "rotation";
    public static final String VIDEO_RECORD_NO_CACHE = "nocache";
    public static final String VIDEO_RECORD_DURATION = "duration";
    public static final String VIDEO_RECORD_RESOLUTION = "resolution";
    public static final String VIDEO_RECORD_AUDIO_SAMPLE_RATE_TYPE = "audio_sample_rate"; // 音频采样率

    public static final int VIDEO_RECORD_TYPE_PUBLISH = 1;   // 推流端录制
    public static final int VIDEO_RECORD_TYPE_PLAY = 2;   // 播放端录制
    public static final int VIDEO_RECORD_TYPE_UGC_RECORD = 3;   // 短视频录制
    public static final int VIDEO_RECORD_TYPE_EDIT = 4;   // 短视频编辑
    public static final int VIDEO_RECORD_TYPE_FOLLOW_SHOT = 5;   // 短视频合拍

    public static final String OUTPUT_DIR_NAME = "TXUGC";

    // 网络类型
    public static final int NETTYPE_NONE = 0;
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_4G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_2G = 4;

    // UGCEditer
    public static final String INTENT_KEY_MULTI_PIC_LIST = "pic_list"; // 图片列表
    public static final String INTENT_KEY_MULTI_CHOOSE = "multi_video";

    public static final String DEFAULT_MEDIA_PACK_FOLDER = "txrtmp";      // UGC编辑器输出目录

    // bgm activity request code and intent extra
    public static final int ACTIVITY_BGM_REQUEST_CODE = 1;
    public static final String BGM_POSITION = "bgm_position";
    public static final String BGM_PATH = "bgm_path";
    public static final String BGM_NAME = "bgm_name";

    public static final int ACTIVITY_OTHER_REQUEST_CODE = 2;

    public static final String KEY_FRAGMENT = "fragment_type";
    public static final int TYPE_EDITER_BGM = 1;
    public static final int TYPE_EDITER_MOTION = 2;
    public static final int TYPE_EDITER_SPEED = 3;
    public static final int TYPE_EDITER_FILTER = 4;
    public static final int TYPE_EDITER_PASTER = 5;
    public static final int TYPE_EDITER_SUBTITLE = 6;

    // 短视频licence名称
    public static final String UGC_LICENCE_NAME = "TXUgcSDK.licence";

    // ELK统计上报事件
    public static final String ELK_ACTION_START_UP = "startup";
    public static final String ELK_ACTION_STAY_TIME = "staytime";
    public static final String ELK_ACTION_PICTURE_EDIT = "pictureedit";
    public static final String ELK_ACTION_REGISTER = "register";
    public static final String ELK_ACTION_INSTALL = "install";
    public static final String ELK_ACTION_LOGIN = "login";
    public static final String ELK_ACTION_VIDEO_EDIT = "videoedit";
    public static final String ELK_ACTION_VIDEO_JOINER = "videojoiner";
    public static final String ELK_ACTION_VIDEO_SIGN = "videosign";
    public static final String ELK_ACTION_VIDEO_UPLOAD_VOD = "videouploadvod";
    public static final String ELK_ACTION_VIDEO_UPLOAD_SERVER = "videouploadserver";
    public static final String ELK_ACTION_START_RECORD = "startrecord";
    public static final String ELK_ACTION_VIDEO_RECORD = "videorecord";
    public static final String ELK_ACTION_VOD_PLAY = "vodplay";
    public static final String ELK_ACTION_VIDEO_CHORUS = "videochorus";

    // 合唱演示视频地址
    public static final String CHORUS_URL = "http://1400100725.vod2.myqcloud.com/8b7d5993vodgzp1400100725/d864a3545285890780576877210/ss2W2I8oIn4A.mp4";

    // EventBus Msg
    public static final int EVENT_MSG_PUBLISH_DONE = 1; // 上传视频成功
    public static final int EVENT_MSG_SAVE_DONE = 2; // 保存视频成功

    // SP record draft录制草稿
    public static final String SP_NAME_RECORD = "record";
    public static final String SP_KEY_RECORD_LAST_DRAFT = "record_last_draft";
    public static final String SP_KEY_RECORD_HISTORY_DRAFT = "record_history_draft";


    /**
     * 版本号
     */
    public static final String VERSION = "7.2.7";
    /**
     * 块大小，不能改变
     */
    public static final int BLOCK_SIZE = 4 * 1024 * 1024;
    /**
     * 所有都是UTF-8编码
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * 连接超时时间 单位秒(默认10s)
     */
    public static final int CONNECT_TIMEOUT = 10;
    /**
     * 写超时时间 单位秒(默认 0 , 不超时)
     */
    public static final int WRITE_TIMEOUT = 0;
    /**
     * 回复超时时间 单位秒(默认30s)
     */
    public static final int READ_TIMEOUT = 30;
    /**
     * 底层HTTP库所有的并发执行的请求数量
     */
    public static final int DISPATCHER_MAX_REQUESTS = 64;
    /**
     * 底层HTTP库对每个独立的Host进行并发请求的数量
     */
    public static final int DISPATCHER_MAX_REQUESTS_PER_HOST = 16;
    /**
     * 底层HTTP库中复用连接对象的最大空闲数量
     */
    public static final int CONNECTION_POOL_MAX_IDLE_COUNT = 32;
    /**
     * 底层HTTP库中复用连接对象的回收周期（单位分钟）
     */
    public static final int CONNECTION_POOL_MAX_IDLE_MINUTES = 5;

    private Constants() {
    }
}
