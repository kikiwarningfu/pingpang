package com.maotianxia.app.widget

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class CountDownTextView(context: Context, attributeSet: AttributeSet) : AppCompatTextView(context, attributeSet) {

    private val mHandler: Handler = Handler()
    private val text = "剩余%d分钟自动关闭"
    private var startTime: Long = 0
    private var countDown: CountDown? = null

    var finishListener:(() -> Unit)? = null

    fun startTime(startTime: Long) {
        this.startTime = startTime
        if (isAttachedToWindow) {
            caculate()
        }
    }

    private fun caculate() {
        var timer = countDown
        if (timer != null) {
            timer.cancel()
        }
        if (startTime <= 0){
            return
        }
        val timeInMillis = Calendar.getInstance().timeInMillis
        val surplusTime = 30 * 60 * 1000 - (timeInMillis - startTime)

        countDown = CountDown(surplusTime, 60000)
        countDown?.start()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        caculate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countDown?.cancel()
        countDown = null
    }

    inner class CountDown : CountDownTimer {

        constructor(millisInFuture: Long, countDownInterval: Long) : super(millisInFuture, countDownInterval)

        override fun onTick(millisUntilFinished: Long) {
            val d = millisUntilFinished / (60 * 1000)
            setText(text.format(d))
        }

        override fun onFinish() {
            finishListener?.invoke()
        }

    }
}