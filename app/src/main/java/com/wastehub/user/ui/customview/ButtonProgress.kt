package com.wastehub.user.ui.customview

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wastehub.user.R

class ButtonProgress(context: Context, text: String, customButtonView: View) {

    private val cardView: CardView = customButtonView.findViewById(R.id.cardViewCustomButton)
    private val constraintLayout: ConstraintLayout = customButtonView.findViewById(R.id.customButtonLayout)
    private var progressBar: ProgressBar = customButtonView.findViewById(R.id.customButtonProgressBar)
    private val textView: TextView = customButtonView.findViewById(R.id.customButtonText)
    private var fadeInAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)

    init {
        textView.text = text
    }
    fun onPressed() {
        constraintLayout.setBackgroundColor(cardView.resources.getColor(R.color.green_200))
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.INVISIBLE
        progressBar.animation = fadeInAnimation
    }

    fun afterProgress() {
        constraintLayout.setBackgroundColor(cardView.resources.getColor(R.color.green_500))
        progressBar.visibility = View.INVISIBLE
        textView.visibility = View.VISIBLE
    }
}
