package com.wastehub.user.domain.usecase.register

import com.wastehub.user.domain.model.auth.register.UserData
import com.wastehub.user.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(fullName: String, password: String, email: String, phoneNumber: String): UserData {
        return userRepository.registerUser(fullName, password, email, phoneNumber)
    }
}