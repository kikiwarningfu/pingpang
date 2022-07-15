package heyong.handong.framework.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.NetworkUtils;
import com.handongkeji.framework.R;

/**
 * 在首页显示网络状态的view
 * @ClassName:NetworkHintView

 * @PackageName:com.nevermore.oceans.widget

 * @Create On 2018/4/28 0028   15:19

 * @Site:http://www.handongkeji.com

 * @author:zhouhao

 * @Copyrights 2018/4/28 0028 handongkeji All rights reserved.
 */
public class NetworkHintView extends RelativeLayout {

    GenericLifecycleObserver observer = new GenericLifecycleObserver() {
        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            switch (event) {
                case ON_RESUME:
                    checkNetworkState();
                    break;
            }
        }
    };
    private NetworkReciver networkReciver;

    public NetworkHintView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.network_hint_layout, this, true);
        findViewById(R.id.rlNoNet).setOnClickListener(v -> {
            NetworkUtils.openWirelessSettings();
        });

    }

    public NetworkHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetworkHintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NetworkHintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        networkReciver = new NetworkReciver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(networkReciver, filter);
        ((FragmentActivity) getContext()).getLifecycle().addObserver(observer);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (networkReciver != null) {
            getContext().unregisterReceiver(networkReciver);
        }
        ((FragmentActivity) getContext()).getLifecycle().removeObserver(observer);
    }

    private void checkNetworkState() {
        setVisibility(NetworkUtils.isConnected() ? View.GONE : View.VISIBLE);
    }

    class NetworkReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetworkState();
        }
    }

}
