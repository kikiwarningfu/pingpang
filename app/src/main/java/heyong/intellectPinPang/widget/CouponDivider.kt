package com.maotianxia.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.AdaptScreenUtils

class CouponDivider(context: Context, attrs: AttributeSet) : View(context, attrs) {

    lateinit var paint: Paint

    val strokeWidth = AdaptScreenUtils.pt2Px(2f).toFloat()

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint.color = Color.parseColor("#fff7f7f7")
        paint.strokeWidth = strokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.parseColor("#fff7f7f7")
        paint.style = Paint.Style.FILL_AND_STROKE

        canvas.drawArc(0f,
                -measuredWidth / 2f,
                measuredWidth * 1f,
                measuredWidth / 2f,
                0f,
                180f,
                false,
                paint)

        canvas.drawArc(0f,
                measuredHeight - measuredWidth / 2f,
                measuredWidth * 1f,
                measuredHeight + measuredWidth / 2f,
                180f,
                360f,
                false,
                paint)

        paint.color = Color.parseColor("#fff0f0f0")

        val count = ((measuredHeight - measuredWidth - strokeWidth * 2) / (strokeWidth * 2)).toInt()
        val array = FloatArray(count * 2) {
            if (it % 2 == 0) measuredWidth / 2f else measuredWidth / 2f + strokeWidth * 1.5f + it * strokeWidth
        }
        canvas.drawPoints(array, paint)
    }

}