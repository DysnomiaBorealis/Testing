package com.wastehub.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wastehub.user.databinding.FragmentProfileScreenBinding
import com.wastehub.user.presentation.ui.login.LoginActivity
import com.wastehub.user.presentation.viewmodel.ChangePasswordViewModel
import com.wastehub.user.presentation.viewmodel.session.SessionViewModel
import com.wastehub.user.ui.customview.ButtonCustom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileScreen : Fragment() {

    private val session: SessionViewModel by viewModels()
    private val changePassword: ChangePasswordViewModel by viewModels()

    private var _binding: FragmentProfileScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var stateBtnChangeDetailInformation: ButtonCustom
    private lateinit var stateBtnChangePassword: ButtonCustom
    private lateinit var stateBtnHistorySubscribe: ButtonCustom
    private lateinit var stateBtnLogout: ButtonCustom

    private lateinit var btnChangeDetailInformation: View
    private lateinit var btnChangePassword: View
    private lateinit var btnHistorySubscribe: View
    private lateinit var btnLogout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnChangeDetailInformation = view.findViewById(R.id.btnChangeDetailInformation)
        stateBtnChangeDetailInformation = ButtonCustom(R.drawable.ic_button_information_account, "Ubah Informasi Akun", btnChangeDetailInformation)

        btnChangePassword = view.findViewById(R.id.btnChangePassword)
        stateBtnChangePassword = ButtonCustom(R.drawable.ic_button_change_password, "Ubah Kata Sandi", btnChangePassword)

        btnHistorySubscribe = view.findViewById(R.id.btnHistorySubscribe)
        stateBtnHistorySubscribe = ButtonCustom(R.drawable.ic_button_history_subscribe, "Riwayat Berlangganan", btnHistorySubscribe)

        btnLogout = view.findViewById(R.id.btnLogout)
        stateBtnLogout = ButtonCustom(R.drawable.ic_button_logout, "Keluar", btnLogout)

        setupListeners()
    }

    private fun setupListeners() {
        btnChangeDetailInformation.setOnClickListener {
            stateBtnChangeDetailInformation.onPressed()
            Toast.makeText(requireContext(), "Ini Toast Change Information", Toast.LENGTH_SHORT).show()
        }

        btnChangePassword.setOnClickListener {
            stateBtnChangePassword.onPressed()
          /*  changePassword.changePassword("rafli1234", "rafli123")
            changePassword.changePasswordResult.observe(viewLifecycleOwner) { result ->
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            }*/
        }

        btnLogout.setOnClickListener {
            stateBtnLogout.onPressed()
            session.saveSession(false)
            session.deleteToken()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

        btnHistorySubscribe.setOnClickListener {
            stateBtnHistorySubscribe.onPressed()
            Toast.makeText(requireContext(), "Ini Toast History", Toast.LENGTH_SHORT).show()
        }
    }

}