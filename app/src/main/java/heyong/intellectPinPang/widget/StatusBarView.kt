package com.maotianxia.app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils

class StatusBarView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val statusBarHeight = BarUtils.getStatusBarHeight()
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(statusBarHeight,MeasureSpec.EXACTLY))
    }

}