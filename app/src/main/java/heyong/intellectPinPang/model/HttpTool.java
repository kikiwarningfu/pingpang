package heyong.intellectPinPang.model;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.bean.Constants;
import heyong.intellectPinPang.bean.entity.EntryParameter;
import heyong.intellectPinPang.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by whf on 2016/10/14.
 * 网络请求帮助类
 */
public class HttpTool {
    private static final String TAG = HttpTool.class.getSimpleName();

    //新添加 okHttp部分***********************开始    //Cookie缓存区
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final int SUCCESS_CODE = 1;
    private static final int STATUS_2 = 200;
    private static final int MULTIDEVICE_CODE = 602;
    private static final int ACCOUNT_FROZEN_CODE = 609;
    private static final int TOKEN_EXPIRE_CODE = 403;

    public final MutableLiveData<Boolean> tokenExpir = new MutableLiveData<>();
    public final MutableLiveData<Boolean> error = new MutableLiveData<>();
    public final MutableLiveData<Boolean> emptyData = new MutableLiveData<>();

    /**
     * okHttp post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String okPost(RequestBody body, String urls, String header) throws Exception {
        try {
            String url = Constants.BaseUrl + urls;
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", header)
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
            }

            Call call = OkHttpUtil.getInstance().newCall(request);


            Response response = call.execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 回掉筛选
     *
     * @param str
     * @return
     * @throws Exception
     */
    private static String getresponse(String str) throws Exception {
        try {
//            Logger.e(str);
//            Gson gson = new Gson();
//            BaseResponse data = gson.fromJson(str, BaseResponse.class);

//            switch (data.get_metadata().getCode()) {
//                case 1101://token失效
//                    EventBus.getDefault().post(BaseActivity.TOKEN_INVALID);
//                    PublicApplication.loginInfo.edit().putString("token", "").apply();
//                    break;
//               /* case 403://用户无权限访问该资源，请求失败
//                    EventBus.getDefault().post(BaseActivity.LACK_JURISDICTION);
//                    break;
//                case 500://服务器程序错误
//                    EventBus.getDefault().post(BaseActivity.SERVICE_ERROR);
//                    break;*/
//                case 1000://强制升级APP版本
//                    EventBus.getDefault().post(BaseActivity.APP_EXPIRED);
//                    PublicApplication.loginInfo.edit().putString("token", "").apply();
//                    break;
//            }
        } catch (Exception e) {
            //Log.e(TAG, "getresponse: ", e);
            return "";
        }
        return str;
    }

    public static String okPostLive(RequestBody body, String urls) throws Exception {
        try {
            String url = Constants.API_DEBUG_SERVER_URL + urls;
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            //注：这里不能返回null，否则会报NULLException的错误。
                            //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", AccountHelper.getToken())
//
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
//                    .addHeader("device","android")
//                    .addHeader("device-id",PublicApplication.loginInfo.getString("device-id", ""))
//                    .addHeader("device-name",PublicApplication.loginInfo.getString("device-name", ""))
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
//            Call call = SSLContext1(okHttpClient).newCall(request);//
            Response response = call.execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String okPost(RequestBody body, String urls) throws Exception {
        try {
            String url = Constants.API_DEBUG_SERVER_URL + urls;
//            //Log.e(TAG, "okPost:url=" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            //注：这里不能返回null，否则会报NULLException的错误。
                            //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            /*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);
            SSLContext1(okHttpClient);*///

            Request request = new Request.Builder()
                    .url(url)
//
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
//                    .addHeader("device","android")
//                    .addHeader("device-id",PublicApplication.loginInfo.getString("device-id", ""))
//                    .addHeader("device-name",PublicApplication.loginInfo.getString("device-name", ""))
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
//            Call call = SSLContext1(okHttpClient).newCall(request);//
            Response response = call.execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String okPostTest(RequestBody body, String urls) throws Exception {
        try {
            String url = urls;
//            //Log.e(TAG, "okPost:url=" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 对入参进行加工
     * 本方法无sign(无加密)
     *
     * @param entry
     * @return
     */
    public static RequestBody getBody(List<EntryParameter> entry) {
        FormBody.Builder builder = new FormBody.Builder();
        for (int i = 0; i < entry.size(); i++) {
            builder.add(entry.get(i).getName(), entry.get(i).getValue());
        }
        RequestBody body = builder.build();
        return body;
    }

    /**
     * okHttp get请求
     *
     * @return
     * @throws Exception
     */
    public static String okShengxiaoGet(String urls) throws Exception {
        try {
            String url = urls;
//            //Log.e(TAG, "okGet: url:" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
//

            String basic = Credentials.basic("android", "android");
            Request request;
            if (!TextUtils.isEmpty(AccountHelper.getBearer())) {
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "" + AccountHelper.getBearer())
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
                        .get()
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "" + basic)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
                        .get()
                        .build();
            }


            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            /*Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                String headerName = responseHeaders.name(i);
                String headerValue = responseHeaders.get(headerName);
                System.out.print("TAG----------->Name:" + headerName + "------------>Value:" + headerValue + "\n");
            }*/
            String s = response.body().string();
            return getresponse(s);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp get请求
     *
     * @return
     * @throws Exception
     */
    public static String okGet(String urls) throws Exception {
        try {
            String url = urls;
//            //Log.e(TAG, "okGet: url:" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
//
            Request request = new Request.Builder()
                    .url(url)

//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
//                    .addHeader("device","android")
//                    .addHeader("device-id",PublicApplication.loginInfo.getString("device-id", ""))
//                    .addHeader("device-name",PublicApplication.loginInfo.getString("device-name", ""))
                    .get()
                    .build();

            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            /*Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                String headerName = responseHeaders.name(i);
                String headerValue = responseHeaders.get(headerName);
                System.out.print("TAG----------->Name:" + headerName + "------------>Value:" + headerValue + "\n");
            }*/
            String s = response.body().string();
            return getresponse(s);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp get请求
     *
     * @return
     * @throws Exception
     */
    public static String WxLogin(RequestBody bodys) throws Exception {
        try {
            String baseUrl = Constants.BaseUrl + "uaa/oauth/token?";

//            //Log.e(TAG, "okGet: url:" + baseUrl);
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
//
            Request request = new Request.Builder()
                    .url(baseUrl)
                    .method("POST", bodys)
                    .addHeader("Authorization", Constants.BASIC_INTERCEPTOR)
                    .build();

            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
//                //Log.e(TAG, "AddQueryParameterInterceptor  RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            /*Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                String headerName = responseHeaders.name(i);
                String headerValue = responseHeaders.get(headerName);
                System.out.print("TAG----------->Name:" + headerName + "------------>Value:" + headerValue + "\n");
            }*/
            String s = response.body().string();
            return getresponse(s);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 图片上传部分
     * 经过测试可用
     */
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client = new OkHttpClient();

    public static String uploadImg(MultipartBody.Builder builder, String url) {
        try {
           /* MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            File f = new File(mImgUrls);
            builder.addFormDataPart("json", "user/EditAvatar");// 补充完毕地址
            builder.addFormDataPart("avatar", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            builder.addFormDataPart("user_id", userId);*/

            MultipartBody requestBody = builder
                    .build();

            //构建请求
            Request request = new Request.Builder()
//                    .url(Constants.API_DEBUG_SERVER_URL + url)//地址
                    .post(requestBody)//添加请求体
                    .addHeader("Content-Type", "application/json")

                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            return getresponse(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * put请求
     *
     * @return
     */
   /* public static String doPut(MediaType mediaType, String uploadUrl, String localPath) {
        try {
            File file = new File(localPath);
            RequestBody body = RequestBody.create(mediaType, file);
            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .put(body)
                    .build();
            //修改各种 Timeout
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            //如果不需要可以直接写成 OkHttpClient client = new OkHttpClient.Builder().build();

            Response response = client
                    .newCall(request)
                    .execute();
            return response.body().string() + ":" + response.code();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //上传JPG图片 t odo 待测试
    public String putImg(String uploadUrl, String localPath) throws IOException {
        MediaType imageType = MediaType.parse("image/jpeg; charset=utf-8");
        return doPut(imageType, uploadUrl, localPath);
    }*/


}
