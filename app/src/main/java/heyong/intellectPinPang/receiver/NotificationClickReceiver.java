package heyong.intellectPinPang.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import heyong.handong.framework.ShowSuccessEvent;
import heyong.intellectPinPang.ui.MyHomePageActivity;

public class NotificationClickReceiver extends BroadcastReceiver {
    public static final String TAG = NotificationClickReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.e("NotificationClickReceiver");
        String ids = intent.getStringExtra("ids");
        String titles = intent.getStringExtra("titles");
        Intent newIntent = new Intent(context, MyHomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish: ");
                EventBus.getDefault().post(new ShowSuccessEvent("" + ids, ""+titles));

            }
        }.start();

    }
}

