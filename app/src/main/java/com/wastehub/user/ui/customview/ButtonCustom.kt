package com.wastehub.user.ui.customview

import android.os.Looper
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wastehub.user.R

class ButtonCustom(iconResources: Int, textButton: String, customButtonView: View) {

    private val textView: TextView = customButtonView.findViewById(R.id.custom_text_button)
    private val iconButton: ImageView = customButtonView.findViewById(R.id.custom_button_icon)
    private val cardView: CardView = customButtonView.findViewById(R.id.custom_card_view_button)
    private val constraintLayout: ConstraintLayout = customButtonView.findViewById(R.id.custom_button_layout)
    init {
        textView.text = textButton
        iconButton.setImageResource(iconResources)
    }

    fun onPressed() {
        constraintLayout.setBackgroundColor(cardView.resources.getColor(R.color.grey_200))
        Handler(Looper.getMainLooper()).postDelayed({
            constraintLayout.setBackgroundColor(cardView.resources.getColor(R.color.white))
        }, 300)
    }


}