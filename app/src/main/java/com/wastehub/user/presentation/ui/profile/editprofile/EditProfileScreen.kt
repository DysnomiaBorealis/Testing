package com.wastehub.user.presentation.ui.profile.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wastehub.user.R
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.wastehub.user.data.remote.model.changeprofile.AddressUpdateRequest
import com.wastehub.user.data.remote.model.changeprofile.UserUpdateResult
import com.wastehub.user.databinding.FragmentEditProfileScreenBinding
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.presentation.viewmodel.profile.editprofile.EditProfileScreenViewModel
import com.wastehub.user.presentation.viewmodel.profile.editprofile.EditSharedDataViewModel
import com.wastehub.user.ui.customview.ButtonCustom
import com.wastehub.user.ui.customview.ButtonProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileScreen : Fragment(), View.OnFocusChangeListener {

    private var _binding: FragmentEditProfileScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var button: ButtonProgress
    private lateinit var btnUpdate: View

    private lateinit var btnCoordinate : View
    private lateinit var stateBtnCoordinate : ButtonCustom

    private val editProfile: EditProfileScreenViewModel by viewModels()
    private val retrieveData: EditSharedDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(editProfile) {
            originalFullName = retrieveData.originalFullName
            originalEmail = retrieveData.originalEmail
            originalPhoneNumber = retrieveData.originalPhoneNumber
            originalAddress = retrieveData.originalAddress
        }

        binding.apply {
            textInputFullname.setText(retrieveData.fullName)
            textInputEmail.setText(retrieveData.email)
            textInputPhoneNumber.setText(retrieveData.phoneNumber)
            textInputAddress.setText(retrieveData.address)
        }

        btnUpdate = view.findViewById(R.id.btnSave)
        button = ButtonProgress(requireContext(), "Update", btnUpdate)

        btnCoordinate = view.findViewById(R.id.btnCoordinate)
        stateBtnCoordinate = ButtonCustom(R.drawable.ic_button_coordinate, "Koordinate", btnCoordinate)

        setupListeners()

        editProfile.updateStatus.observe(viewLifecycleOwner) { userUpdateResult ->
            when (userUpdateResult) {
                is UserUpdateResult.Success -> {
                    Toast.makeText(context, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is UserUpdateResult.Error -> {
                    Toast.makeText(context, userUpdateResult.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setupListeners() {
        btnUpdate.setOnClickListener {
            button.onPressed()
            onUpdate()
        }

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnCoordinate.setOnClickListener {
            stateBtnCoordinate.onPressed()
            Toast.makeText(requireContext(), "Ini Toast Koordinate", Toast.LENGTH_SHORT).show()
        }

        binding.apply {
            textInputFullname.onFocusChangeListener = this@EditProfileScreen
            textInputEmail.onFocusChangeListener = this@EditProfileScreen
            textInputPhoneNumber.onFocusChangeListener = this@EditProfileScreen
            textInputAddress.onFocusChangeListener = this@EditProfileScreen
            textInputCoordinate.onFocusChangeListener = this@EditProfileScreen
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when(v?.id) {
            R.id.textInputFullname -> if (!hasFocus) validateFullName()
            R.id.textInputEmail -> if (!hasFocus) validateEmail()
            R.id.textInputPhoneNumber -> if (!hasFocus) validatePhoneNumber()
            R.id.textInputAddress -> if (!hasFocus) validateAddress()
            R.id.textInputCoordinate -> if (!hasFocus) validateCoordinates()
        }
    }

    private fun validateFullName(): Boolean {
        val result = editProfile.validateFullName(binding.textInputFullname.text.toString())
        return handleValidationResult(binding.textLayoutFullName, result)
    }


    private fun validateEmail(): Boolean {
        val result = editProfile.validateEmail(binding.textInputEmail.text.toString())
        return handleValidationResult(binding.textLayoutEmail, result)
    }

    private fun validatePhoneNumber(): Boolean {
        val result = editProfile.validatePhoneNumber(binding.textInputPhoneNumber.text.toString())
        return handleValidationResult(binding.textLayoutPhoneNumber, result)
    }

    private fun validateAddress(): Boolean {
        val result = editProfile.validateAddress(binding.textInputAddress.text.toString())
        return handleValidationResult(binding.textLayoutAddress, result)
    }

    private fun validateCoordinates(): Boolean {
        val result = editProfile.validateCoordinates(binding.textInputCoordinate.text.toString())
        return handleValidationResult(binding.textLayoutCoordinate, result)
    }

    private fun clearError(textLayout: TextInputLayout) {
        textLayout.isErrorEnabled = false
        textLayout.error = null
    }

    private fun handleValidationResult(textLayout: TextInputLayout, result: ValidationResult): Boolean {
        if (!result.isSuccessful) {
            textLayout.error = result.errorMessage
            return false
        } else {
            clearError(textLayout)
            return true
        }
    }


    private fun validateFieldSuccess(): Boolean {
        return validateFullName() && validateEmail() && validatePhoneNumber() && validateAddress() && validateCoordinates()
    }

    private fun onUpdate() {
        if (validateFieldSuccess()) {
            val fullName = binding.textInputFullname.text.toString()
            val email = binding.textInputEmail.text.toString()
            val phoneNumber = binding.textInputPhoneNumber.text.toString()
            val address = binding.textInputAddress.text.toString()
            val userId = "user_id_here"

            val addressUpdateRequest = AddressUpdateRequest(note = address)

            editProfile.updateUserProfile(userId, fullName, email, phoneNumber, "user_role_here", "user_avatar_here", address, addressUpdateRequest)

            retrieveData.apply {
                originalFullName = fullName
                originalEmail = email
                originalPhoneNumber = phoneNumber
                originalAddress = address
            }
        } else {
            Toast.makeText(context, "Please correct the errors.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}