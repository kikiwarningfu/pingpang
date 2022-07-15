package heyong.intellectPinPang.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import heyong.intellectPinPang.R;


/**
 * 编辑会员弹窗
 *
 * @ClassName:PhotoSelDialog
 * @PackageName:com.denengapp.app.dialog
 * @Create On 2018/9/26 0026   09:52
 * @Site:http://www.handongkeji.com
 * @author:pengjingjing
 * @Copyrights 2018/9/26 0026 handongkeji All rights reserved.
 */

public class EditMemberDialog extends Dialog implements View.OnClickListener {
    LinearLayout ll_out;
    TextView tv_take, tv_album, tv_cancel;

    public EditMemberDialog(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20000000")));
        getWindow().setGravity(Gravity.BOTTOM);
        this.setContentView(R.layout.pop_edit_number);
        ll_out = findViewById(R.id.ll_out);
        tv_take = findViewById(R.id.tv_take);
        tv_album = findViewById(R.id.tv_album);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_take.setOnClickListener(this);
        tv_album.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.height = context.getResources().getDisplayMetrics().heightPixels;
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();//拍摄
        //取消
        //从手机相册选择
        if (i == R.id.tv_take) {
            if (listener != null) {
                listener.takePhoto();
            }
        } else if (i == R.id.tv_cancel) {
        } else if (i == R.id.tv_album) {
            if (listener != null) {
                listener.selectPhoto();
            }
        }
        dismiss();
    }

    public interface OnSelectListener {
        void takePhoto();

        void selectPhoto();
    }

    private OnSelectListener listener;

    public void setClickListener(OnSelectListener listener) {
        this.listener = listener;
    }


}
