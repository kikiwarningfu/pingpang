package heyong.handong.framework.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.imagepicker.BuildConfig;

import heyong.handong.framework.cookie.PersistentCookieStore;
import heyong.handong.framework.gson.BaseBeanTypeAdapterFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sf on 2018/1/31.
 */

public class Api {

    public static final int MAX_CORE_SIZE = Runtime.getRuntime().availableProcessors()+1;
    public static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(MAX_CORE_SIZE, MAX_CORE_SIZE * 10, 1,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    private static Retrofit RETROFIT;
    private static ApiConfigration config;
    private static String baseUrl;

    public static void config(ApiConfigration configration) {
        config = configration;
        Api.baseUrl = BuildConfig.DEBUG ? configration.debugBaseUrl : configration.releaseBaseUrl;
        RETROFIT = getRetrofit();
    }

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static <T> T getApiService(Class<T> clazz) {
        return RETROFIT.create(clazz);
    }

    private static Retrofit getRetrofit() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //这里添加的小米系的手机使用公司的网络，网络卡顿，而测试机好几个小米系 判断是IPV4 和IPV6  解决网络卡顿的问题
//                这里需要详细的看
//                .dns(DnsSelector.byName("ipv4only"))
                .cookieJar(new CookieJar() {

                    private final PersistentCookieStore cookieStore = new PersistentCookieStore();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (cookies != null && cookies.size() > 0) {
                            for (Cookie item : cookies) {
                                cookieStore.add(url, item);
                            }
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies;
                    }
                });

        for (Interceptor interceptor : config.interceptors) {
            builder.addInterceptor(interceptor);
        }

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new BaseBeanTypeAdapterFactory())
                .create();
        OkHttpClient build = builder.build();
        build.dispatcher().setMaxRequests(1);
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.from(EXECUTOR)))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(build)
                .build();
    }

}
