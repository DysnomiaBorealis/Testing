package com.wastehub.user.presentation.ui.register.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.wastehub.user.R
import com.wastehub.user.common.utils.KeyboardUtil
import com.wastehub.user.databinding.FragmentFormInformationScreenBinding
import com.wastehub.user.presentation.viewmodel.register.RegisterViewModel
import com.wastehub.user.presentation.viewmodel.register.SharedDataViewModel
import com.wastehub.user.ui.customview.ButtonProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormInformationScreen : Fragment(), View.OnFocusChangeListener, View.OnKeyListener {

    private var _binding: FragmentFormInformationScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var button: ButtonProgress
    private lateinit var btnNext: View

    private val register: RegisterViewModel by viewModels()
    private val retrieveData: SharedDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormInformationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext = view.findViewById(R.id.btnNext)
        button = ButtonProgress(requireContext(), "Lanjut", btnNext)

        setupListeners()
    }

    private fun setupListeners() {
        btnNext.setOnClickListener {
            button.onPressed()
            onSubmit()
        }

        binding.apply {
            textInputFullname.onFocusChangeListener = this@FormInformationScreen
            textInputPhoneNumber.onFocusChangeListener = this@FormInformationScreen
            textInputEmail.onFocusChangeListener = this@FormInformationScreen

            btnBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun validateFullName(): Boolean {
        val fullName = binding.textInputFullname.text.toString()
        val validationResult = register.validateFullName(fullName)

        if (!validationResult.isSuccessful) {
            binding.textLayoutFullName.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.textInputPhoneNumber.text.toString()
        val validationResult = register.validatePhoneNumber(phoneNumber)

        if (!validationResult.isSuccessful) {
            binding.textLayoutPhoneNumber.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }


    private fun validateEmail(): Boolean {
        val email = binding.textInputEmail.text.toString()
        val validationResult = register.validateEmail(email)

        if (!validationResult.isSuccessful) {
            binding.textLayoutEmail.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
        }

        return validationResult.errorMessage == null
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.textInputFullname -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutFullName)
                        if (binding.textLayoutFullName.endIconDrawable == null) {
                            binding.textLayoutFullName.setEndIconDrawable(R.drawable.ic_clear_text)
                        }
                    } else {
                        validateFullName()
                        binding.textLayoutFullName.endIconDrawable = null
                    }
                }

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

                R.id.textInputEmail -> {
                    if (hasFocus) {
                        clearError(binding.textLayoutEmail)
                        if (binding.textLayoutEmail.endIconDrawable == null) {
                            binding.textLayoutEmail.setEndIconDrawable(R.drawable.ic_clear_text)
                        }
                    } else {
                        validateEmail()
                        KeyboardUtil.hideKeyboard(view, requireContext())
                        binding.textLayoutEmail.endIconDrawable = null
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (view == binding.textLayoutEmail.editText) {
                KeyboardUtil.hideKeyboard(view, context)
                return true
            }
            onSubmit()
            return true
        }
        return false
    }

    private fun validateFieldSuccess(): Boolean {
        var isValid = true

        if (!validateFullName()) isValid = false
        if (!validatePhoneNumber()) isValid = false
        if (!validateEmail()) isValid = false

        return isValid
    }

    private fun onSubmit() {
        if (validateFieldSuccess()) {
            retrieveData.fullName = binding.textInputFullname.text.toString()
            retrieveData.email = binding.textInputEmail.text.toString()
            retrieveData.phoneNumber = "+62" + binding.textInputPhoneNumber.text.toString()

            Handler(Looper.getMainLooper()).postDelayed({
                button.afterProgress()
            }, 3000)
            
            findNavController().navigate(R.id.action_formInformationScreen_to_formPasswordScreen)
        } else {
            button.afterProgress()
        }
    }

    private fun clearError(textLayout: TextInputLayout) {
        if (textLayout.isErrorEnabled) {
            textLayout.isErrorEnabled = false
        }
    }
}