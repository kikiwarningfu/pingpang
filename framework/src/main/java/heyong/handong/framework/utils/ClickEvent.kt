package heyong.handong.framework.utils

import android.os.SystemClock
import android.view.View
import com.handongkeji.framework.R

class ClickEvent {

    companion object {

        fun shouldClick(view: View): Boolean {
            val tag = view.getTag(R.id.click_event)
            if (tag == null) {
                view.setTag(R.id.click_event, SystemClock.uptimeMillis())
                return true
            } else {
                val uptimeMillis = SystemClock.uptimeMillis()
                val l = tag as Long
                if (uptimeMillis - l >= 500) {
                    view.setTag(R.id.click_event, SystemClock.uptimeMillis())
                    return true
                }
            }
            return false
        }
    }


}