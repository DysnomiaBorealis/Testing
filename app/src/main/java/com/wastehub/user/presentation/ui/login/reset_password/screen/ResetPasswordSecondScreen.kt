package com.wastehub.user.presentation.ui.login.reset_password.screen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.wastehub.user.databinding.FragmentResetPasswordSecondScreenBinding
import com.wastehub.user.presentation.ui.login.LoginActivity
import com.wastehub.user.presentation.viewmodel.login.LoginViewModel
import com.wastehub.user.presentation.viewmodel.login.SharedEmailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordSecondScreen : Fragment() {

    private var _binding: FragmentResetPasswordSecondScreenBinding? = null
    private val binding get() = _binding!!

    private val getData: SharedEmailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordSecondScreenBinding.inflate(inflater, container, false)
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
        val email = getData.email
        val obscuringEmail = email.replaceRange(3, email.indexOf('@'), "*****")

        val verificationMessage = "Kami telah mengirim link tautan untuk me-reset password anda pada email <font color=\"#558005\"><b>$obscuringEmail</b></font> berikut"
        binding.textVerificationMessage.text = Html.fromHtml(verificationMessage, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnBackLoginScreen.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}