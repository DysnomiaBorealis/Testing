package com.wastehub.user.presentation.viewmodel.alamat

import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModel
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.domain.usecase.alamat.ValidateAlamatInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditAlamatViewModel @Inject constructor(
    private val alamat: ValidateAlamatInputUseCase
) : ViewModel() {

    fun validateAlamatLengkap(alamatLengkap: String): ValidationResult {
        return alamat.validateAlamatLengkap(alamatLengkap)
    }

    fun validateLatLng(lat: String, lng: String): ValidationResult {
        return alamat.validateLatLng(lat, lng)
    }

    fun validateProvinsi(provinsi: AutoCompleteTextView): ValidationResult {
        return alamat.validateProvinsi(provinsi)
    }

    fun validateKabupaten(kabupaten: AutoCompleteTextView): ValidationResult {
        return alamat.validateKabupaten(kabupaten)
    }

    fun validateKecamatan(kecamatan: AutoCompleteTextView): ValidationResult {
        return alamat.validateKecamatan(kecamatan)
    }

    fun validateKelurahan(kelurahan: AutoCompleteTextView): ValidationResult {
        return alamat.validateKelurahan(kelurahan)
    }

    fun validateRw(rw: AutoCompleteTextView): ValidationResult {
        return alamat.validateRw(rw)
    }
}