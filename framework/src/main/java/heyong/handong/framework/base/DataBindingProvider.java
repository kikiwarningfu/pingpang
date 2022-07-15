package heyong.handong.framework.base;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public interface DataBindingProvider {

     @LayoutRes
    int getLayoutRes();

     void initView(@Nullable Bundle savedInstanceState);

}
