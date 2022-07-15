package heyong.intellectPinPang.utils;

import okhttp3.OkHttpClient;

public class OkHttpUtil {
    private static volatile OkHttpClient singleton;

    //非常有必要，要不此类还是可以被new，但是无法避免反射，好恶心
    private OkHttpUtil() {

    }

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpUtil.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient.Builder()

                            .build();
                    singleton.dispatcher().setMaxRequests(1);
                }
            }
        }
        return singleton;
    }

    public void getData() {

    }
}
