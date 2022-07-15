package heyong.handongkeji.autoupdata;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class UpdataService extends Service {

    private static final String TAG = "UpdataService";

    private long id;
    private DownloadManager downloadManager;
    private DownloadReciver downloadReciver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
            return START_STICKY_COMPATIBILITY;
        }
        String downUrl = intent.getStringExtra("downUrl");
        downloadApk(downUrl);
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadApk(String downUrl) {
        Log.d(TAG, "downloadApk: " + downUrl);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        String appName = getPackageName().replace(".", "_");
//        request.setDestinationInExternalFilesDir(this, null, appName+".apk");
        request.setDestinationInExternalFilesDir(this,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
                getAppName(getApplicationContext())+".apk");
        id = downloadManager.enqueue(request);
        downloadReciver = new DownloadReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReciver, filter);
    }
    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadReciver);
    }

    private void install(Uri uri) {
        String path = ProviderUtils.getImageAbsolutePath(this, uri);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri uriFile = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriFile = UpdateProvider.getUriForFile(this, getPackageName() + ".autoupdata.provider", new File(path));
        } else {
            uriFile = Uri.fromFile(new File(path));
        }

        intent.setDataAndType(uriFile, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
        stopSelf();
    }

    class DownloadReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                if (longExtra == id) {
                    Uri uri = downloadManager.getUriForDownloadedFile(id);
                    Log.d(TAG, "onReceive: " + uri);
                    if (uri != null) {
                        install(uri);
                    }
                }



            }
        }
    }

}
