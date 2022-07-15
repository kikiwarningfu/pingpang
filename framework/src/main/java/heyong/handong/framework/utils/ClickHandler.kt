package heyong.handong.framework.utils

import android.view.View

public interface ClickHandler<T> {
    fun handleClick(view: View, bean: T)
}