package heyong.intellectPinPang.model;


import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by whf on 2017/2/3 下午3:54.   在MYAPP里面使用   .addInterceptor(new AddQueryParameterInterceptor());
 * Copyright (c) 2017. alpha, Inc. All rights reserved.
 */

public class AddQueryParameterInterceptor22 implements Interceptor {
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
//            if (!TextUtils.isEmpty(AccountHelper.getBearer()) && !AccountHelper.getBearer().equals(Constants.BASIC_INTERCEPTOR)) {
//                String isLoginVisitor = AccountHelper.getIsLoginVisitor();
//                if (!TextUtils.isEmpty(isLoginVisitor) && AccountHelper.getIsLoginVisitor().equals("true")) {
//                } else {
//                    if (!TextUtils.isEmpty(AccountHelper.getSpRefreshToken())) {
//                        AccountHelper.setNoToken("");
//                        String newToken = getNewToken();
//                        if (TextUtils.isEmpty(newToken)) {
//                            if (!TextUtils.isEmpty(AccountHelper.getBearer())) {
//                            }
//                        } else {
//                            request = chain.request()
//                                    .newBuilder()
//                                    .build();
//                        }
//
//                    } else {
//                        if (!TextUtils.isEmpty(AccountHelper.getBearer())) {
//                        }
//                    }
//                }
//            } else {
//            }
        }
        return chain.proceed(request);
    }

    //用同步方法获取新的Token
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        String newToken = "";
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "refresh_token")
//                .add("refresh_token", "" + AccountHelper.getSpRefreshToken())
                .build();

        String urls = "uaa/oauth/token";//请求地址
        String result = "";
        try {
            result = HttpTool.okPost(body, urls, basic);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (!TextUtils.isEmpty(result)) {
//            Gson gson2 = new Gson();
//            Long successTime = Long.valueOf(0);
//            try {
//
//                long currentTimeMillis = System.currentTimeMillis();
//                NewBarerBean newBarerBean = gson2.fromJson(result, NewBarerBean.class);
//                long currentTimes = System.currentTimeMillis() / 1000;
//
//                if (!TextUtils.isEmpty(AccountHelper.getAccountSuccessTime())) {
//                    successTime = Long.valueOf(AccountHelper.getAccountSuccessTime());
//                    if (!TextUtils.isEmpty(newBarerBean.getRefresh_token())) {
//
//                        AccountHelper.setAccountSuccessTime(String.valueOf(currentTimeMillis / 1000));
//                        AccountHelper.setSpExpiresIn("" + (newBarerBean.getExpires_in() + currentTimeMillis / 1000));
//                        AccountHelper.setSpRefreshToken(newBarerBean.getRefresh_token());
//                        if (!TextUtils.isEmpty(newBarerBean.getAccess_token())) {
//                            newToken = "Bearer" + newBarerBean.getAccess_token();
//                            AccountHelper.setBearer(newToken);
//                        }
//                    } else {
//
//                        if (newBarerBean.getResp_code().equals("400")) {
//                            if (currentTimes - successTime >= 500000) {
//                                if (successTime != 0) {
//                                    logOut();
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    if (!TextUtils.isEmpty(newBarerBean.getRefresh_token())) {
//                        AccountHelper.setAccountSuccessTime(String.valueOf(currentTimeMillis / 1000));
//                        AccountHelper.setSpExpiresIn("" + (newBarerBean.getExpires_in() + currentTimeMillis / 1000));
//                        AccountHelper.setSpRefreshToken(newBarerBean.getRefresh_token());
//                        if (!TextUtils.isEmpty(newBarerBean.getAccess_token())) {
//                            newToken = "Bearer" + newBarerBean.getAccess_token();
//                            AccountHelper.setBearer(newToken);
//                        }
//                    } else {
//                        if (newBarerBean.getResp_code().equals("400")) {
//                            if (currentTimes - successTime >= 500000) {
//                                if (successTime != 0) {
//                                    logOut();
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                ErrorBean errorBean = gson2.fromJson(result, ErrorBean.class);
//                long currentTimes = System.currentTimeMillis() / 1000;
//                if (!TextUtils.isEmpty(AccountHelper.getAccountSuccessTime())) {
//                    Long successTime1 = Long.valueOf(AccountHelper.getAccountSuccessTime());
//                    if (errorBean.getResp_code().equals("400")) {
//                        if (currentTimes - successTime1 >= 500000) {
//                            if (successTime1 != 0) {
//                                logOut();
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            Log.e(TAG, "getNewToken: new Token为空");
//            newToken = "";
//        }
        return newToken;
    }

//    private void logOut() {
//        String isLogin = AccountHelper.getIsLogin();
//        if (!isLogin.equals("false")) {
//            RxBus.getDefault().post(new LoginTokenOutEvent());
//            AccountHelper.setNoToken("yes");
//        }
//    }

}
