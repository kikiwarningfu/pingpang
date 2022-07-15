package heyong.handong.framework.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;


import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by whf on 2017/2/3 下午3:54.
 * Copyright (c) 2017. alpha, Inc. All rights reserved.
 */

public class AddQueryParameterInterceptor implements Interceptor {
    private static final String TAG = "okhttp-addquery-paramr";
    String basic = Credentials.basic("browser", "browser");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request request = null;
        synchronized (AddQueryParameterInterceptor.class) {
//        String basic = Credentials.basic("android", "android");
            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    .build();
            Log.e(TAG, "okhttp Url===: " + originalRequest.url());

            request = chain.request()
                    .newBuilder()
//                    .header("Authorization", newToken)
                    .build();
        }


        return chain.proceed(request);
    }

    //用同步方法获取新的Token


}
