package com.wastehub.user.domain.usecase.alamat

import android.widget.AutoCompleteTextView
import com.wastehub.user.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidateAlamatInputUseCase @Inject constructor() {
    fun validateAlamatLengkap(alamatLengkap: String): ValidationResult {
        return if (alamatLengkap.isBlank()) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Alamat lengkap tidak boleh kosong"
            )
        } else ValidationResult(isSuccessful = true)
    }

    fun validateLatLng(latitude: String, longitude: String): ValidationResult {
        return if (latitude == "Latitude" || longitude == "Longitude") {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Tekan tombol dapatkan titik koordinat"
            )
        } else ValidationResult(isSuccessful = true)
    }

    fun validateProvinsi(provinsi: AutoCompleteTextView): ValidationResult {
        return if (provinsi.text.toString().isNotEmpty()) {
            ValidationResult(isSuccessful = true)
        } else {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Silahkan pilih provinsi terlebih dahulu"
            )
        }
    }

    fun validateKabupaten(kabupaten: AutoCompleteTextView): ValidationResult {
        return if (kabupaten.text.toString().isNotEmpty()) {
            ValidationResult(isSuccessful = true)
        } else {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Silahkan pilih kabupaten terlebih dahulu"
            )
        }
    }

    fun validateKecamatan(kecamatan: AutoCompleteTextView): ValidationResult {
        return if (kecamatan.text.toString().isNotEmpty()) {
            ValidationResult(isSuccessful = true)
        } else {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Silahkan pilih kecamatan terlebih dahulu"
            )
        }
    }

    fun validateKelurahan(kelurahan: AutoCompleteTextView): ValidationResult {
        return if (kelurahan.text.toString().isNotEmpty()) {
            ValidationResult(isSuccessful = true)
        } else {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Silahkan pilih kelurahan terlebih dahulu"
            )
        }
    }

    fun validateRw(rw: AutoCompleteTextView): ValidationResult {
        return if (rw.text.toString().isNotEmpty()) {
            ValidationResult(isSuccessful = true)
        } else {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Silahkan pilih RW terlebih dahulu"
            )
        }
    }
}