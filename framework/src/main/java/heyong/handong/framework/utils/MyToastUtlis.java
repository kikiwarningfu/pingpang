package heyong.handong.framework.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.framework.R;


public class MyToastUtlis {


    public static void showToastThread(String message, Context context) {

        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    showMyToast(message, context);
                    Looper.loop();
                } catch (Exception e) {

                    Looper.prepare();
                    showMyToast(message, context);
                    Looper.loop();
                }
            }
        }.start();
    }

    private static void showMyToast(String massage, Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
//
//        //获取TextView
//        TextView title = (TextView) view.findViewById(R.id.toast_tv);
//        //设置显示的内容
//        title.setText(massage);
//        Toast toast = new Toast(context);
//        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
//        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 70);
//        //设置显示时间
//        toast.setDuration(Toast.LENGTH_SHORT);
//
//        toast.setView(view);
//        toast.show();
        Toast toast = Toast.makeText(context,""+massage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 70);
        toast.show();

    }

}
