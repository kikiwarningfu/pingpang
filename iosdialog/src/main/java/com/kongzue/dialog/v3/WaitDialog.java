package com.kongzue.dialog.v3;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2019/4/9 14:08
 */
public class WaitDialog extends TipDialog {
    
    public WaitDialog() {
    }
    
    public static TipDialog show(AppCompatActivity context, String message,boolean isCancelble) {
        return TipDialog.showWait(context, message,isCancelble);
    }
    
    public static TipDialog show(AppCompatActivity context, int messageResId,boolean isCancelble) {
        return TipDialog.showWait(context, messageResId,isCancelble);
    }
    
    @Override
    public void show() {
        setDismissEvent();
        showDialog();
    }
    
    public WaitDialog setCustomDialogStyleId(int customDialogStyleId) {
        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setTheme(...) 来修改对话框主题或风格。");
            return this;
        }
        this.customDialogStyleId = customDialogStyleId;
        return this;
    }
    
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}