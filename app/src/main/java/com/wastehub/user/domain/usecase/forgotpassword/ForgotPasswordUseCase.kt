package com.wastehub.user.domain.usecase.forgotpassword

import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword
import com.wastehub.user.domain.repository.UserRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(phoneNumber: String): ForgotPassword {
        return userRepository.forgotPasswordUser(phoneNumber)
    }
}