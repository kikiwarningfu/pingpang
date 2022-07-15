package heyong.handong.framework.utils;

import android.view.View;

public interface MyClickEventHandler<T> {

    void handleClick(View view, T bean);

}
