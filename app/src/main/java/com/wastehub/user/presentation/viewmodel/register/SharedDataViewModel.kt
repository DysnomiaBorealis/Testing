package com.wastehub.user.presentation.viewmodel.register

import androidx.lifecycle.ViewModel

class SharedDataViewModel: ViewModel() {
    var fullName: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var password: String = ""
}