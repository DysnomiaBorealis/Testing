package com.wastehub.user.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlamatRequestBody(
    var alamatLengkap: String?,
    var lat: Double?,
    var lng: Double?,
    var provinsi: String?,
    var kabupaten: String?,
    var kecamatan: String?,
    var kelurahan: String?,
    var rw: String?
): Parcelable {
}