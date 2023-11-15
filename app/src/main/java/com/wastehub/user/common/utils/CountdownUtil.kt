package com.wastehub.user.common.utils

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.wastehub.user.R

object CountdownUtil {
    private var countdownTimer: CountDownTimer? = null

    fun startCountdownTimer(
        context: Context,
        button: TextView,
        indicator: TextView,
        onTick: (Long) -> Unit,
        onFinish: () -> Unit
    ) {
        countdownTimer?.cancel()

        countdownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val indicatorTextOtp = context.getString(R.string.text_indicator_resend_otp)
                indicator.visibility = View.VISIBLE
                indicator.text = "$indicatorTextOtp $secondsLeft"
                button.isEnabled = false
                button.setTextColor(ContextCompat.getColor(button.context, R.color.grey_300))
                onTick(secondsLeft)
            }

            override fun onFinish() {
                indicator.visibility = View.GONE
                button.isEnabled = true
                button.setTextColor(ContextCompat.getColor(button.context, R.color.green_500))
                onFinish()
            }
        }

        countdownTimer?.start()
    }

    fun destroyCountdownTimer() {
        countdownTimer?.cancel()
        countdownTimer = null
    }
}