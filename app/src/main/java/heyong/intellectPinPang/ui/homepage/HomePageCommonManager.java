package heyong.intellectPinPang.ui.homepage;

import android.app.Activity;
import android.content.Context;

import android.view.View;


import heyong.intellectPinPang.utils.AppManager;
import heyong.intellectPinPang.widget.AlertDialog;

import static heyong.intellectPinPang.ui.MyHomePageActivity.ApplicationInfo;


public class HomePageCommonManager {
    private static HomePageCommonManager instance;

    /**
     * 单例模式
     *f
     * @return AppManager
     */
    public static HomePageCommonManager getInstance() {
        if (instance == null) {
            instance = new HomePageCommonManager();
        }
        return instance;
    }

    private HomePageCommonManager() {
    }


    /**
     * lack permission dialog
     */
    public static void showMissingDialog(AlertDialog myDialog, Context context) {
        myDialog.setGone().setTitle("帮助")
                .setMsg("当前应用缺少定位权限。请点击 设置-权限管理 -打开所需权限。最后点击两次后退按钮，即可返回。")

                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Activity context1 = (Activity) context;
//                                context1.finish();
                                AppManager.getAppManager().finishAllActivity();
                                AppManager.getAppManager().AppExit();
                                System.exit(0);
                            }
                        }).setPositiveButton("去设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInfo((Activity) context);
            }
        }).show();
    }
}
