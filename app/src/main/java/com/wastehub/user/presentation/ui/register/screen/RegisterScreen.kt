package com.wastehub.user.presentation.ui.register.screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wastehub.user.R
import com.wastehub.user.databinding.FragmentRegisterScreenBinding
import com.wastehub.user.presentation.ui.login.LoginActivity

class RegisterScreen : Fragment() {

    private var _binding: FragmentRegisterScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_registerScreen_to_formInformationScreen)
            }

            btnAlreadyRegister.setOnClickListener {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }
    }

}