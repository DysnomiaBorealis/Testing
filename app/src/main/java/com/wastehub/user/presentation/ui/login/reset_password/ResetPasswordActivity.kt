package com.wastehub.user.presentation.ui.login.reset_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wastehub.user.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
    }
}