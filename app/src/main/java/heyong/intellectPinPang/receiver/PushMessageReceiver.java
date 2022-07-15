package heyong.intellectPinPang.receiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.List;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.ui.MipushTestActivity;
import heyong.intellectPinPang.utils.NotifyUtil;
import heyong.intellectPinPang.utils.Utils;

public class PushMessageReceiver extends JPushMessageReceiver {

    private static final String TAG = "JPush-Receiver";


    //======================下面的都是消息的回调=========================================//

    /**
     * 收到自定义消息回调
     *
     * @param context
     * @param message 自定义消息
     */
    @Override
    public void onMessage(Context context, CustomMessage message) {
        Log.e(TAG, "[onMessage]:" + message);
        /*处理自定义消息*/
//        notify_normal_singLine(context);
        if (!isBackground(context)) {
            Log.e(TAG, "onMessage: 在前台运行");
            //            EventBus.getDefault().post(new ShowSuccessEvent("" + 1, "测试一下"));
            //            Intent intent = new Intent("com.jiguang.demo.message");
            //            intent.putExtra("msg", "msg");
            //            context.sendBroadcast(intent);
            Intent jumpIntent = new Intent(context, MipushTestActivity.class);
            //        Intent intent = Utils.parseNotificationMessage(jumpIntent, message);
            jumpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityUtils.startActivity(jumpIntent);
        } else {
            Log.e(TAG, "onMessage: 不在前台运行");
            //            Intent jumpIntent=new Intent(context,MipushTestActivity.class);
            //            //        Intent intent = Utils.parseNotificationMessage(jumpIntent, message);
            //            jumpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //            ActivityUtils.startActivity(jumpIntent);
//            notify_normal_singLine(context);

            Intent intent = new Intent(context, MipushTestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pIntent = PendingIntent.getActivity(context,
                    requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            int smallIcon = R.drawable.img_logo_start;
            String ticker = "您有一条新通知";
            String title = "双十一大优惠！！！";
            String content = "仿真皮肤充气娃娃，女朋友带回家！";

            //实例化工具类，并且调用接口
            NotifyUtil notify1 = new NotifyUtil(context, 1);
            notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
            currentNotify = notify1;

        }

        //        RxBusUtils.get().post(KEY_PUSH_MESSAGE, PushMessage.wrap(MessageType.TYPE_CUSTOM, message));
    }

    private NotifyUtil currentNotify;
    int requestCode = (int) SystemClock.uptimeMillis();

    private void notify_normal_singLine(Context context) {
        //设置想要展示的数据内容
        Intent intent = new Intent(context, MipushTestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.img_logo_start;
        String ticker = "您有一条新通知";
        String title = "双十一大优惠！！！";
        String content = "仿真皮肤充气娃娃，女朋友带回家！";

        //实例化工具类，并且调用接口
        NotifyUtil notify1 = new NotifyUtil(context, 1);
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
        currentNotify = notify1;

    }

    //震动milliseconds毫秒
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public static boolean isBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            //            StaticMethod.debugEMSG(topActivity.getPackageName() + " : " + context.getPackageName());
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 收到通知回调
     *
     * @param context
     * @param message 通知消息
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived]:" + message);
        //        RxBusUtils.get().post(KEY_PUSH_MESSAGE, PushMessage.wrap(MessageType.TYPE_NOTIFICATION, message));
    }

    /**
     * 点击通知回调
     *
     * @param context
     * @param message 通知消息
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened]:" + message);
        //自定义打开到通知栏点击后的容器页
        Intent jumpIntent = new Intent(context, MipushTestActivity.class);
        Intent intent = Utils.parseNotificationMessage(jumpIntent, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityUtils.startActivity(intent);
    }


    /**
     * 清除通知回调
     * <p>
     * 说明:
     * 1.同时删除多条通知，可能不会多次触发清除通知的回调
     * 2.只有用户手动清除才有回调，调接口清除不会有回调
     *
     * @param context
     * @param message 通知消息
     */
    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived]:" + message);

    }


    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏上的按钮:" + intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA));
    }


    //======================下面的都是操作的回调=========================================//

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister]:" + registrationId);
        //        RxBusUtils.get().post(KEY_PUSH_EVENT, new PushEvent(EventType.TYPE_REGISTER, true, registrationId));
    }

    /**
     * 连接状态发生变化
     *
     * @param context
     * @param isConnected 是否已连接
     */
    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected]:" + isConnected);
        //        RxBusUtils.get().post(KEY_PUSH_EVENT, new PushEvent(EventType.TYPE_CONNECT_STATUS_CHANGED, isConnected));
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult]:" + cmdMessage);

    }

    /**
     * 所有和标签相关操作结果
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onTagOperatorResult]:" + jPushMessage);
        //        PushEvent pushEvent = new PushEvent(jPushMessage.getSequence(), jPushMessage.getErrorCode() == 0)
        //                .setData(JPushInterface.getStringTags(jPushMessage.getTags()));
        //        RxBusUtils.get().post(KEY_PUSH_EVENT, pushEvent);
    }


    /**
     * 所有和别名相关操作结果
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onAliasOperatorResult]:" + jPushMessage);
        //        PushEvent pushEvent = new PushEvent(jPushMessage.getSequence(), jPushMessage.getErrorCode() == 0)
        //                .setData(jPushMessage.getAlias());
        //        RxBusUtils.get().post(KEY_PUSH_EVENT, pushEvent);
    }

    /**
     * 标签状态检测结果
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onCheckTagOperatorResult]:" + jPushMessage);
        //        PushEvent pushEvent = new PushEvent(jPushMessage.getSequence(), jPushMessage.getErrorCode() == 0)
        //                .setData(jPushMessage);
        //        RxBusUtils.get().post(KEY_PUSH_EVENT, pushEvent);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onMobileNumberOperatorResult]:" + jPushMessage);
    }

}