package com.wastehub.user.domain.usecase.login

import com.wastehub.user.domain.model.auth.login.UserAccess
import com.wastehub.user.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String, password: String): UserAccess {
        return userRepository.loginUser(phoneNumber, password)
    }
}