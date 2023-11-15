package com.wastehub.user.presentation.ui.register.screen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.wastehub.user.MainActivity
import com.wastehub.user.R
import com.wastehub.user.common.utils.CountdownUtil
import com.wastehub.user.common.utils.KeyboardUtil
import com.wastehub.user.databinding.FragmentOtpVerificationScreenBinding
import com.wastehub.user.presentation.viewmodel.register.SharedDataViewModel

class OtpVerificationScreen : Fragment(), TextWatcher, View.OnKeyListener {

    private var _binding: FragmentOtpVerificationScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedDataViewModel by activityViewModels()

    private var currentInputOtpPosition = 1
    private var resendEnabled = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOtpVerificationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setVerificationMessage()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setVerificationMessage() {
        val email = viewModel.email
        val verificationMessage = "Silahkan masukkan kode OTP yang dikirimkan pada email <b><font color=\"#558005\">${email}</font></b> untuk verifikasi."

        binding.textVerificationMessage.text = Html.fromHtml(verificationMessage, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun setupListeners() {

        binding.apply {
            textInputOTP1.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP2.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP3.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP4.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP5.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP6.addTextChangedListener(this@OtpVerificationScreen)
            textInputOTP1.setOnKeyListener(this@OtpVerificationScreen)
            textInputOTP2.setOnKeyListener(this@OtpVerificationScreen)
            textInputOTP3.setOnKeyListener(this@OtpVerificationScreen)
            textInputOTP4.setOnKeyListener(this@OtpVerificationScreen)
            textInputOTP5.setOnKeyListener(this@OtpVerificationScreen)
            textInputOTP6.setOnKeyListener(this@OtpVerificationScreen)

            btnVerifyOTP.setOnClickListener {
                onVerifyOTP()
            }

            btnResendOTP.setOnClickListener {
                if (resendEnabled) {
                    startCountdownTimer()
                }
            }

            btnBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(enteredOtp: Editable) {
        if (enteredOtp.isNotEmpty()) {
            when (currentInputOtpPosition) {
                1 -> {
                    currentInputOtpPosition = 2
                    KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP2)
                }
                2 -> {
                    currentInputOtpPosition = 3
                    KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP3)
                }
                3 -> {
                    currentInputOtpPosition = 4
                    KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP4)
                }
                4 -> {
                    currentInputOtpPosition = 5
                    KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP5)
                }
                5 -> {
                    currentInputOtpPosition = 6
                    KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP6)
                }
                6 -> {
                    currentInputOtpPosition = 7
                    binding.btnVerifyOTP.isEnabled = true
                }
                7 -> {
                    binding.btnVerifyOTP.isEnabled = true
                }
            }
        } else {
            if (currentInputOtpPosition < 7) {
                binding.btnVerifyOTP.isEnabled = false
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent?.action == KeyEvent.ACTION_DOWN) {
            if (currentInputOtpPosition > 0) {
                currentInputOtpPosition--

                val previousTextInput = when (currentInputOtpPosition) {
                    1 -> binding.textInputOTP1
                    2 -> binding.textInputOTP2
                    3 -> binding.textInputOTP3
                    4 -> binding.textInputOTP4
                    5 -> binding.textInputOTP5
                    6 -> binding.textInputOTP6
                    else -> null
                }

                previousTextInput?.let {
                    KeyboardUtil.showKeyboard(requireContext(), it)
                    it.text = null
                }

                if (currentInputOtpPosition == 7) {
                    binding.textInputOTP6.text = null
                    currentInputOtpPosition = 6
                } else if (currentInputOtpPosition == 0) {
                    currentInputOtpPosition = 1
                }

                return true
            }
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (currentInputOtpPosition >= 7) {
                KeyboardUtil.hideKeyboard(view, context)
                return true
            }
        }
        return false
    }

    private fun onVerifyOTP() {
        val testOtp = "123456"
        val enteredOtp = binding.textInputOTP1.text.toString() +
                binding.textInputOTP2.text.toString()  +
                binding.textInputOTP3.text.toString() +
                binding.textInputOTP4.text.toString() +
                binding.textInputOTP5.text.toString() +
                binding.textInputOTP6.text.toString()

        if (enteredOtp.length == 6 && enteredOtp == testOtp) {

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val errorMessage = "Kode OTP salah. Silakan coba lagi."
            Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT)
                .setTextColor(resources.getColor(android.R.color.white))
                .setBackgroundTint(resources.getColor(R.color.red_300))
                .show()

            binding.textInputOTP1.text = null
            binding.textInputOTP2.text = null
            binding.textInputOTP3.text = null
            binding.textInputOTP4.text = null
            binding.textInputOTP5.text = null
            binding.textInputOTP6.text = null

            currentInputOtpPosition = 1
            KeyboardUtil.showKeyboard(requireContext(), binding.textInputOTP1)
            binding.btnVerifyOTP.isEnabled = false
        }
    }

    private fun startCountdownTimer() {
        CountdownUtil.startCountdownTimer(
            requireContext(),
            binding.btnResendOTP,
            binding.indicatorResendOTP,
            { },
            { resendEnabled = true }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CountdownUtil.destroyCountdownTimer()
    }

}