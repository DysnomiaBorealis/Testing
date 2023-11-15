package com.wastehub.user.presentation.ui.login.reset_password.screen

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.wastehub.user.R
import com.wastehub.user.common.utils.SnackbarUtils
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordResult
import com.wastehub.user.databinding.FragmentResetPasswordFirstScreenBinding
import com.wastehub.user.presentation.viewmodel.login.LoginViewModel
import com.wastehub.user.presentation.viewmodel.login.SharedEmailViewModel
import com.wastehub.user.ui.customview.ButtonProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFirstScreen : Fragment(), View.OnFocusChangeListener, View.OnKeyListener {

    private var _binding: FragmentResetPasswordFirstScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var button: ButtonProgress
    private lateinit var btnSubmit: View

    private val viewModel: LoginViewModel by viewModels()
    private val retrieveData: SharedEmailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmit = view.findViewById(R.id.btnSubmit)
        button = ButtonProgress(requireContext(), "Kirim", btnSubmit)

        setupListeners()
    }

    private fun setupListeners() {
        btnSubmit.setOnClickListener {
            button.onPressed()
            onSubmit()
        }

        binding.apply {
            textInputPhoneNumber.onFocusChangeListener = this@ResetPasswordFirstScreen

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.textInputPhoneNumber.text.toString()
        val validationResult = viewModel.validatePhoneNumber(phoneNumber)

        if (!validationResult.isSuccessful) {
            binding.textLayoutPhoneNumber.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.textInputPhoneNumber -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutPhoneNumber)
                        if (binding.textLayoutPhoneNumber.endIconDrawable == null) {
                            binding.textLayoutPhoneNumber.setEndIconDrawable(R.drawable.ic_clear_text)
                        }
                    } else {
                        validatePhoneNumber()
                        binding.textLayoutPhoneNumber.endIconDrawable = null
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

    private fun validateFieldSuccess(): Boolean {
        var isValid = true

        if (!validatePhoneNumber()) isValid = false

        return isValid
    }


    private fun onSubmit() {
        if (validateFieldSuccess()) {
            val phoneNumber = "+62" + binding.textInputPhoneNumber.text.toString()

            Log.d("PHONEEE", phoneNumber)

            lifecycleScope.launch {
                viewModel.forgotPasswordUser(phoneNumber)
            }

            sendRequestResetPassword()
        } else {
            button.afterProgress()
        }
    }

    private fun sendRequestResetPassword() {
        viewModel.forgotPasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ForgotPasswordResult.Error -> {
                    SnackbarUtils.showSnackbar(binding.root, result.error, Snackbar.LENGTH_SHORT)
                    button.afterProgress()
                }
                is ForgotPasswordResult.Success -> {
                    button.afterProgress()
                    retrieveData.email = result.forgotPassword.email
                    findNavController().navigate(R.id.action_resetPasswordFirstScreen_to_resetPasswordSecondScreen)
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