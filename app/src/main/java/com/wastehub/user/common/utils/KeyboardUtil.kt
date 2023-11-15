package com.wastehub.user.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtil {

    fun hideKeyboard(view: View?, context: Context?) {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun showKeyboard(context: Context?, textInputOTP: EditText) {
        textInputOTP.requestFocus()
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.showSoftInput(textInputOTP, InputMethodManager.SHOW_IMPLICIT)
    }

}