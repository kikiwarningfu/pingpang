package heyong.intellectPinPang.utils;

import android.annotation.SuppressLint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;

import heyong.intellectPinPang.MyApp;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.SampleApplicationLike;

//SoundPool.play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
//参数依次是：
//soundID：Load()返回的声音ID号
//leftVolume：左声道音量设置
//rightVolume：右声道音量设置
//priority：指定播放声音的优先级，数值越高，优先级越大。
//loop：指定是否循环：-1表示无限循环，0表示不循环，其他值表示要重复播放的次数
//rate：指定播放速率：1.0的播放率可以使声音按照其原始频率，而2.0的播放速率，可以使声音按照其
//原始频率的两倍播放。如果为0.5的播放率，则播放速率是原始频率的一半。播放速率的取值范围是0.5至2.0。

public class SoundHelper {
    private SoundPool soundPool;
    private int idSure;
    private int idCanel;
    private int idOrder;

    private static SoundHelper helper;

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public SoundHelper() {
        Context context = SampleApplicationLike.getContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setMaxStreams(10);
            // spb.setAudioAttributes(null); // 转换音频格式
            soundPool = spb.build(); // 创建SoundPool对象

        } else {
            soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        }
//        idSure = soundPool.load(context, R.raw.h_sure_open, 1);
//        idCanel = soundPool.load(context, R.raw.h_cancel_back, 1);
//        idOrder = soundPool.load(context, R.raw.order, 1);
    }

    public static SoundHelper get() {
        if (null == helper) {
            helper = new SoundHelper();
        }
        return helper;
    }

    public void palyOrder() {
        soundPool.play(idOrder, 1, 1, 10, 0, 1);

    }

    public void palySure() {
//        if (BaseApplication.get().isOpenSoundBtn() && DefaultShared.isH()) {
            soundPool.play(idSure, 1, 1, 0, 0, 1);
//        }

    }

    public void palyCancel() {
//        if (BaseApplication.get().isOpenSoundBtn() && DefaultShared.isH()) {
            soundPool.play(idCanel, 1, 1, 0, 0, 1);
//        }
    }


}
