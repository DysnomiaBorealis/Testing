package com.wastehub.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPrice(
    val category: String,
    val weight: String,
    val price: String
): Parcelable
