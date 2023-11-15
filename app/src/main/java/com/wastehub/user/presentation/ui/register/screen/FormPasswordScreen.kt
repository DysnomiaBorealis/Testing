package com.wastehub.user.presentation.ui.register.screen

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.wastehub.user.MainActivity
import com.wastehub.user.R
import com.wastehub.user.common.utils.SnackbarUtils
import com.wastehub.user.data.remote.model.register.RegisterResult
import com.wastehub.user.databinding.FragmentFormPasswordScreenBinding
import com.wastehub.user.presentation.ui.home.HomeActivity
import com.wastehub.user.presentation.ui.login.LoginActivity
import com.wastehub.user.presentation.viewmodel.register.RegisterViewModel
import com.wastehub.user.presentation.viewmodel.register.SharedDataViewModel
import com.wastehub.user.ui.customview.ButtonProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FormPasswordScreen : Fragment(), View.OnFocusChangeListener, View.OnKeyListener,
    TextWatcher {

    private var _binding: FragmentFormPasswordScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var button: ButtonProgress
    private lateinit var btnRegister: View

    private val getData: SharedDataViewModel by activityViewModels()

    private val register: RegisterViewModel by viewModels()
    private var colorValidationSuccess: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormPasswordScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister = view.findViewById(R.id.btnRegister)
        button = ButtonProgress(requireContext(), "Daftar Akun", btnRegister)

        colorValidationSuccess = ContextCompat.getColor(requireContext(), R.color.green_500)

        setupListeners()
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            button.onPressed()
            onSubmit()
        }

        binding.apply {
            textInputPassword.onFocusChangeListener = this@FormPasswordScreen
            textInputConfirmPassword.onFocusChangeListener = this@FormPasswordScreen
            textInputConfirmPassword.setOnKeyListener(this@FormPasswordScreen)
            textInputConfirmPassword.addTextChangedListener(this@FormPasswordScreen)

            btnBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun validatePasswordAndConfirmPassword(
        shouldUpdateField: Boolean = true
    ): Boolean {
        val password = binding.textInputPassword.text.toString()
        val confirmPassword = binding.textInputConfirmPassword.text.toString()
        val passwordValidationResult = register.validatePassword(password)
        val confirmPasswordValidationResult = register.validateConfirmPassword(confirmPassword)

        if (!passwordValidationResult.isSuccessful) {
            binding.textLayoutPassword.apply {
                isErrorEnabled = true
                error = passwordValidationResult.errorMessage
            }
        } else {
            clearError(binding.textLayoutPassword)
        }

        if (!confirmPasswordValidationResult.isSuccessful) {
            binding.textLayoutConfirmPassword.apply {
                isErrorEnabled = true
                error = confirmPasswordValidationResult.errorMessage
            }
        } else {
            clearError(binding.textLayoutConfirmPassword)
        }

        return passwordValidationResult.errorMessage == null &&
                confirmPasswordValidationResult.errorMessage == null
    }

    private fun validatePasswordAndConfirmPasswordMatches(shouldUpdateField: Boolean = true): Boolean {
        val password = binding.textInputPassword.text.toString()
        val confirmPassword = binding.textInputConfirmPassword.text.toString()
        val validationResult = register.validatePasswordAndConfirmPasswordMatches(password, confirmPassword)

        if (!validationResult.isSuccessful && shouldUpdateField) {
            binding.textLayoutConfirmPassword.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }


    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.textInputPassword -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutPassword)
                        binding.textLayoutPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else {
                        if (validatePasswordAndConfirmPassword() && binding.textInputConfirmPassword.text!!.isNotEmpty() && validatePasswordAndConfirmPasswordMatches()) {
                            clearError(binding.textLayoutConfirmPassword)
                            binding.textLayoutConfirmPassword.apply {
                                setStartIconDrawable(R.drawable.ic_checkmark)
                                setStartIconTintList(ColorStateList.valueOf(colorValidationSuccess))
                            }
                        }
                    }
                }
                R.id.textInputConfirmPassword -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutConfirmPassword)
                    } else {
                        if (validatePasswordAndConfirmPassword() && validatePasswordAndConfirmPasswordMatches()) {
                            clearError(binding.textLayoutPassword)
                            binding.textLayoutConfirmPassword.apply {
                                setStartIconDrawable(R.drawable.ic_checkmark)
                                setStartIconTintList(ColorStateList.valueOf(colorValidationSuccess))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP) {
            onSubmit()
        }
        return false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if (validatePasswordAndConfirmPassword(shouldUpdateField = false) && validatePasswordAndConfirmPasswordMatches(shouldUpdateField = false)) {
            binding.textLayoutConfirmPassword.apply {
                if (isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.ic_checkmark)
                setStartIconTintList(ColorStateList.valueOf(colorValidationSuccess))
            }
        } else {
            if (binding.textLayoutConfirmPassword.startIconDrawable != null) {
                binding.textLayoutConfirmPassword.setStartIconDrawable(R.drawable.ic_unlock)
                binding.textLayoutConfirmPassword.setStartIconTintList(ColorStateList.valueOf(Color.DKGRAY))
            }
        }
    }

    private fun validateFieldSuccess(): Boolean {
        var isValid = true

        if (!validatePasswordAndConfirmPassword()) isValid = false
        if (isValid && !validatePasswordAndConfirmPasswordMatches()) isValid = false

        return isValid
    }

    private fun onSubmit() {
        if (validateFieldSuccess()) {
            val fullName = getData.fullName
            val email = getData.email
            val phoneNumber = getData.phoneNumber
            val password = binding.textInputPassword.text.toString()

            lifecycleScope.launch {
                register.registerUser(fullName, password, email, phoneNumber)
            }

            doRegister()
        } else {
            button.afterProgress()
        }
    }

    private fun doRegister() {
        register.registerResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is RegisterResult.Error -> {
                    SnackbarUtils.showSnackbar(binding.root, result.error, Snackbar.LENGTH_SHORT)
                    button.afterProgress()
                }
                is RegisterResult.Success -> {
                    button.afterProgress()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
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
