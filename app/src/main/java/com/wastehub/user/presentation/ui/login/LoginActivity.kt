package com.wastehub.user.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.wastehub.user.R
import com.wastehub.user.common.utils.KeyboardUtil
import com.wastehub.user.common.utils.SessionManager
import com.wastehub.user.common.utils.SnackbarUtils
import com.wastehub.user.data.remote.model.login.LoginResult
import com.wastehub.user.databinding.ActivityLoginBinding
import com.wastehub.user.presentation.ui.home.HomeActivity
import com.wastehub.user.presentation.ui.login.reset_password.ResetPasswordActivity
import com.wastehub.user.presentation.ui.register.RegisterActivity
import com.wastehub.user.presentation.viewmodel.login.LoginViewModel
import com.wastehub.user.presentation.viewmodel.session.SessionViewModel
import com.wastehub.user.ui.customview.ButtonProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var binding: ActivityLoginBinding
    private val login: LoginViewModel by viewModels()
    private val session: SessionViewModel by viewModels()

    @Inject
    lateinit var preferences: SessionManager

    private lateinit var button: ButtonProgress
    private lateinit var btnLogin: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLogin = findViewById(R.id.btnLogin)
        button = ButtonProgress(this@LoginActivity, "Masuk", btnLogin)

        setupListeners()
        checkSession()
    }

    private fun checkSession() {
        lifecycleScope.launchWhenStarted {
            preferences.isUserLoggedIn.collect { isLoggedIn  ->
                if (isLoggedIn == true) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            button.onPressed()
            submit()
        }

        binding.apply {
            textInputPhoneNumber.onFocusChangeListener = this@LoginActivity
            textInputPassword.onFocusChangeListener = this@LoginActivity
            textInputPassword.setOnKeyListener(this@LoginActivity)

            textInputPhoneNumber.setOnClickListener {
                textInputPhoneNumber.isFocusable  = true
                textInputPhoneNumber.isFocusableInTouchMode = true
                textLayoutPhoneNumber.requestFocus()
                KeyboardUtil.showKeyboard(this@LoginActivity, textInputPhoneNumber)
            }

            textInputPassword.setOnClickListener {
                textInputPassword.isFocusable  = true
                textInputPassword.isFocusableInTouchMode = true
                textInputPassword.requestFocus()
                KeyboardUtil.showKeyboard(this@LoginActivity, textInputPassword)
            }

            btnDontHaveAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.textInputPhoneNumber.text.toString()
        val validationResult = login.validatePhoneNumber(phoneNumber)

        if (!validationResult.isSuccessful) {
            binding.textLayoutPhoneNumber.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }

    private fun validatePassword(shouldUpdateField: Boolean = true): Boolean {
        val password = binding.textInputPassword.text.toString()
        val validationResult = login.validatePassword(password)

        if (!validationResult.isSuccessful && shouldUpdateField) {
            binding.textLayoutPassword.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }

    private fun validateFieldSuccess(): Boolean {
        var isValid = true

        if (!validatePhoneNumber()) isValid = false
        if (!validatePassword()) isValid = false

        return isValid
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.textInputPhoneNumber -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutPhoneNumber)
                        binding.textLayoutPassword.endIconMode = TextInputLayout.END_ICON_NONE
                        if (binding.textLayoutPhoneNumber.endIconDrawable == null) {
                            binding.textLayoutPhoneNumber.setEndIconDrawable(R.drawable.ic_clear_text)
                        }
                    } else {
                        validatePhoneNumber()
                        binding.textLayoutPhoneNumber.endIconDrawable = null
                    }
                }

                R.id.textInputPassword -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutPassword)
                        binding.textLayoutPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else {
                        validatePassword()
                    }
                }
            }
        }

    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent!!.action == KeyEvent.ACTION_UP) {
            submit()
        }

        return false
    }

    private fun submit() {
        if (validateFieldSuccess()) {
            val phoneNumberInput = binding.textInputPhoneNumber.text.toString()
            val passwordInput = binding.textInputPassword.text.toString()

            lifecycleScope.launch {
                login.loginUser("+62$phoneNumberInput", passwordInput)
            }

            doLogin()
        } else {
            button.afterProgress()
        }
    }

    private fun doLogin() {
        login.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Error -> {
                    SnackbarUtils.showSnackbar(binding.root, result.error, Snackbar.LENGTH_SHORT)
                    button.afterProgress()
                }

                is LoginResult.Success -> {
                    button.afterProgress()
                    session.saveSession( true)
                    session.saveToken(result.userAccess.accessToken, result.userAccess.refreshToken)
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun clearError(textLayout: TextInputLayout) {
        if (textLayout.isErrorEnabled) {
            textLayout.isErrorEnabled = false
        }
    }
}