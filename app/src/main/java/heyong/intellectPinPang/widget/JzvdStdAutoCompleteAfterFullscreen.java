package heyong.intellectPinPang.widget;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

public class JzvdStdAutoCompleteAfterFullscreen extends JzvdStd {
    public JzvdStdAutoCompleteAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdAutoCompleteAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startVideo() {
        if (screen == SCREEN_FULLSCREEN) {
            Log.d(TAG, "startVideo [" + this.hashCode() + "] ");
            JZMediaInterface.SAVED_SURFACE = null;
            addTextureView();
            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            JZMediaPlayer.instance().positionInList = positionInList;
            onStatePreparing();
        } else {
            super.startVideo();
        }
    }

    @Override
    public void onCompletion() {
        if (screen == SCREEN_FULLSCREEN) {
            onStateAutoComplete();
        } else {
            super.onCompletion();
        }

    }
}