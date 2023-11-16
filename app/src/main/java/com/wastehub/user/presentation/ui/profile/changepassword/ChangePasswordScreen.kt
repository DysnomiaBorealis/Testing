package com.wastehub.user.presentation.ui.profile.changepassword

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.wastehub.user.R
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable
import android.text.TextWatcher
import com.wastehub.user.presentation.viewmodel.profile.changepassword.ChangePasswordScreenViewModel
import com.wastehub.user.presentation.viewmodel.profile.changepassword.ChangePasswordSharedDataViewModel

@AndroidEntryPoint
class ChangePasswordScreen : Fragment() {

    private val viewModel: ChangePasswordScreenViewModel by viewModels()
    private val sharedViewModel: ChangePasswordSharedDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val oldPasswordEditText = view.findViewById<TextInputEditText>(R.id.textInputPassword)
        val newPasswordEditText = view.findViewById<TextInputEditText>(R.id.textInputNewPassword)
        val confirmPasswordEditText = view.findViewById<TextInputEditText>(R.id.textInputConfirmPassword)

        setTextWatcher(oldPasswordEditText) { sharedViewModel.updateOriginalPassword(it) }
        setTextWatcher(newPasswordEditText) { sharedViewModel.updateNewPassword(it) }
        setTextWatcher(confirmPasswordEditText) { sharedViewModel.updateConfirmPassword(it) }

        viewModel.validationResult.observe(viewLifecycleOwner) { validationResult ->
            if (!validationResult.isSuccessful) {
                Toast.makeText(context, validationResult.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.updateStatus.observe(viewLifecycleOwner) { statusMessage ->
            Toast.makeText(context, statusMessage, Toast.LENGTH_SHORT).show()
            if (statusMessage == "Password changed successfully") {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        view.findViewById<View>(R.id.btnChangePassword).setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            viewModel.validateAndChangePassword(oldPassword, newPassword, confirmPassword)
        }
    }

    private fun setTextWatcher(editText: TextInputEditText, updateFunction: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateFunction(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}