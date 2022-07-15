package heyong.intellectPinPang.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import heyong.intellectPinPang.R;


public class MipushTestActivity extends Activity {
    TextView tvMessage;
    private static String TAG = MipushTestActivity.class.getName();
    public static MipushTestActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mipush);
        tvMessage = findViewById(R.id.tv_message);

        TextView tv = new TextView(this);
        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
//            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            tv.setText("Title : " + 1 + "  " + "Content : " + 1);
        }
        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

//    @Override
//    public void onMessage(Intent intent) {
//        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
//        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//        Log.i(TAG, body);
//        instance = this;
//        tvMessage.setText("" + body);
//        Log.e(TAG, "onMessage: " + body);
//        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
////            String str=message.replaceAll("\\\\", "");//将URL中的反斜杠替换为空  加上之后收不到消息
//        UMessage msg = null;
//        try {
//            msg = new UMessage(new JSONObject(message));
//            Log.e(TAG, "message=" + message);    //消息体
//            Log.e(TAG, "custom=" + msg.custom);    //自定义消息的内容
//            Log.e(TAG, "title=" + msg.title);    //通知标题
//            Log.e(TAG, "text=" + msg.text);    //通知内容
//            //消息处理
//            Map<String, String> extra = msg.extra;
//            String ids = extra.get("id");
//            //跳转到  抽签的界面
//            intent.setClass(MipushTestActivity.this, CoachDrawLotsHostAndGusetActivity.class);
//            intent.putExtra("ids", ids);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            MipushTestActivity.this.finish();
//            finish();
//
//            Log.e(TAG, "onMessage: " + new Gson().toJson(msg.extra));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
////        try {
////            AppManager.getAppManager().addActivity(this);
////            intent.setClass(MipushTestActivity.this, HomePageActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            startActivity(intent);
////            MipushTestActivity.this.finish();
////        } catch (Exception e) {
////            startActivity(new Intent(MipushTestActivity.this, LoginActivity.class));
////            finish();
////        }
////        RxBus.getDefault().post();
//    }
}
