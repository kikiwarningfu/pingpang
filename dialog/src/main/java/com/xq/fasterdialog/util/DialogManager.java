package com.xq.fasterdialog.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xq.fasterdialog.dialog.base.BaseDialog;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.xq.androidfaster.util.tools.Utils.getApp;

public class DialogManager {

    private static LinkedHashMap<Integer,BaseDialog> map_dialog = new LinkedHashMap<>();

    public static void showAnywhere(DialogDelegateActivity.DialogContextProvider contextProvider){
        DialogDelegateActivity.startActivity(contextProvider);
    }

    public static void showDialog(BaseDialog dialog){

        getDialogMap().put(dialog.hashCode()&0x0000ffff,dialog);

        dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
            @Override
            public void onDismiss(BaseDialog dialog) {
                getDialogMap().remove(dialog.hashCode()&0x0000ffff);
            }
        }).show();
    }

    public static void dismissDialog(){

        Map.Entry<Integer,BaseDialog> entry = getTail(getDialogMap());

        if (entry != null && entry.getValue() != null) entry.getValue().dismiss();

    }

    private  static  <K, V> Map.Entry<K, V> getTail(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    private synchronized static Map<Integer,BaseDialog> getDialogMap(){
        return map_dialog;
    }

    public static class DialogDelegateActivity extends AppCompatActivity {

        private static DialogContextProvider contextProvider;

        public static void startActivity(DialogContextProvider contextProvider){
            DialogDelegateActivity.contextProvider = contextProvider;
            Intent intent = new Intent(getApp(), DialogDelegateActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApp().startActivity(intent);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            BaseDialog dialog = contextProvider.createDialog(this);
            dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
                @Override
                public void onDismiss(BaseDialog dialog) {
                    finish();
                }
            });
            dialog.show();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            contextProvider = null;
        }

        public static interface DialogContextProvider{

            public BaseDialog createDialog(Context context);

        }
    }

}
