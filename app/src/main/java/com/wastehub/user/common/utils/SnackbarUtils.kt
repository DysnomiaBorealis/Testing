package com.wastehub.user.common.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object SnackbarUtils {
    fun showSnackbar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration).show()
    }

    fun showSnackbar(fragment: Fragment, message: String, duration: Int) {
        fragment.view?.let { view ->
            Snackbar.make(view, message, duration).show()
        }
    }
}
