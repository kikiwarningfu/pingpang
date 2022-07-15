package heyong.intellectPinPang.model;

import android.text.TextUtils;
import android.util.Log;


import java.io.IOException;

import heyong.handong.framework.account.AccountHelper;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangshijia on 2017/2/3 下午3:54.
 * Copyright (c) 2017. alpha, Inc. All rights reserved.
 */

public class AddQueryParameterInterceptor implements Interceptor {
    public static final String TAG=AddQueryParameterInterceptor.class.getSimpleName();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        //String method = originalRequest.method();
        // Headers headers = originalRequest.headers();
        String token = AccountHelper.getToken();
//        String uid = String.valueOf(UserCenter.getInstance().getCurrentUser().uid);
        HttpUrl modifiedUrl = originalRequest.url().newBuilder()

//                .addQueryParameter("token", TextUtils.isEmpty(AccountHelper.getToken()) ? "" : token)
//                .addQueryParameter("Authorization", basic)
//                .addQueryParameter("uid", UserCenter.getInstance().getCurrentUser().uid == 0 ? "" : uid)
                .build();
        RequestBody body = originalRequest.body();
        if (!TextUtils.isEmpty(AccountHelper.getToken())) {
            request = originalRequest.newBuilder().header("Authorization", AccountHelper.getToken())
                    .header("ip", AccountHelper.getIpAddress())
                    .url(modifiedUrl).build();
        } else {
            request = originalRequest.newBuilder().header("ip", AccountHelper.getIpAddress()).url(modifiedUrl).build();
        }

        return chain.proceed(request);
    }
}
