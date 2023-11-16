package com.wastehub.user.domain.usecase.changeprofile

import com.wastehub.user.domain.model.changeprofile.UserProfileData
import com.wastehub.user.domain.repository.UserRepository
import javax.inject.Inject

class ChangeProfileUseCase@Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        _id: String,
        name: String,
        email: String,
        phoneNumber: String,
        role: String,
        avatar: String
    ): UserProfileData {
        return userRepository.changeProfile(_id, name, email, phoneNumber, role, avatar)
    }
}

