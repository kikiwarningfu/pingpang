package heyong.intellectPinPang.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.NotificationMessage;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.SampleApplicationLike;

public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /*public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, SampleApplicationLike.getContext().getResources().getDisplayMetrics());
    }*/

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 将JSON字符串转换为集合
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }


    /**
     * 防止多次点击的处理
     */
    private final static long DEFAULT_TIME = 800;

    public static boolean isInvalidClick(@io.reactivex.annotations.NonNull View target) {
        return isInvalidClick(target, DEFAULT_TIME);
    }
    /**
     * 使用方式
     * button.setOnClickListener(new View.OnClickListener() {
     *             @Override
     *             public void onClick(View v) {
     *                 if (Utils.isInvalidClick(v, 5000))
     *                     return;
     *
     *             }
     *         });
     */
    /**
     * @param target      防止多次点击的View
     * @param defaultTime 超时时间
     * @return
     */
    public static boolean isInvalidClick(@io.reactivex.annotations.NonNull View target, @IntRange(from = 0) long defaultTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;
        Object o = target.getTag(R.id.invalid_click);
        if (o == null) {
            target.setTag(R.id.invalid_click, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < defaultTime;
        if (!isInvalid)
            target.setTag(R.id.invalid_click, curTimeStamp);
        return isInvalid;
    }


    /**
     * String数组转Set
     *
     * @param values
     * @return
     */
    public static Set<String> array2Set(String... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    /**
     * 以“,”隔开的字符串转数组
     *
     * @param values
     * @return
     */
    public static String[] string2Array(String values) {
        if (!TextUtils.isEmpty(values)) {
            return values.split(",");
        }
        return new String[0];
    }


    /**
     * DeepLink的格式：
     * <p>
     * jpush://com.xuexiang.jpush/notification?pageName=xxxxx&title=这是一个通知&content=这是通知的内容&extraMsg=xxxxxxxxx&keyValue={"param1": "1111", "param2": "2222"}
     *
     * @param uri
     * @param bundle
     * @return
     */
    public static Bundle parseNotificationDeepLinkUri(@NonNull Uri uri, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString("pageName", uri.getQueryParameter("pageName"));
        //通知标题
        bundle.putString("title", uri.getQueryParameter("title"));
        //通知内容
        bundle.putString("content", uri.getQueryParameter("content"));
        //通知附带拓展内容
        bundle.putString("extraMsg", uri.getQueryParameter("extraMsg"));
        //通知附带键值对
        bundle.putString("keyValue", uri.getQueryParameter("keyValue"));
        return bundle;
    }
    public static String getShowVisitorWan(double num) {
        String number = "";
        if (num > 10000) {
            number = "" + (num / 10000) + "万";
        } else {
            int i = new Double(num).intValue();
            number = "" +i;
        }
        return number;
    }
    public static String getShowVisitor(double num) {
        String number = "";
        if (num > 10000) {
            number = "" + (num / 10000) + "w";
        } else {
            int i = new Double(num).intValue();
            number = "" +i;
        }
        return number;
    }

    /**
     * 解析极光通知消息：NotificationMessage
     *
     * @param message
     * @return
     */
    public static Intent parseNotificationMessage(@NonNull Intent intent, NotificationMessage message) {
        //这只是一个例子，暂时把跳转的目标页设为 "通知信息展示"
        intent.putExtra("pageName", "通知信息展示");
        //通知标题
        intent.putExtra("title", message.notificationTitle);
        //通知内容
        intent.putExtra("content", message.notificationContent);
        //通知附带拓展内容
        intent.putExtra("extraMsg", message.notificationExtras);
        //通知附带键值对
        intent.putExtra("keyValue", message.notificationExtras);
        return intent;
    }

}
