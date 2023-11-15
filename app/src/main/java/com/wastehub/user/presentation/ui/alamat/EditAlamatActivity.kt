package com.wastehub.user.presentation.ui.alamat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.wastehub.user.R
import com.wastehub.user.common.utils.KeyboardUtil
import com.wastehub.user.data.AlamatRequestBody
import com.wastehub.user.databinding.ActivityEditAlamatBinding
import com.wastehub.user.presentation.viewmodel.alamat.EditAlamatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class EditAlamatActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private lateinit var binding: ActivityEditAlamatBinding
    private val alamat: EditAlamatViewModel by viewModels()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            tvDapatkanKoordinat.setOnClickListener { getLocation() }
            btnBackAlamat.setOnClickListener { finish() }
            btnSimpanAlamat.setOnClickListener { submit() }
            textInputAlamatLengkap.onFocusChangeListener = this@EditAlamatActivity
            autoFillProvinsi.onFocusChangeListener = this@EditAlamatActivity
            autoFillKabupaten.onFocusChangeListener = this@EditAlamatActivity
            autoFillKecamatan.onFocusChangeListener = this@EditAlamatActivity
            autoFillKelurahan.onFocusChangeListener = this@EditAlamatActivity
            autoFillRw.onFocusChangeListener = this@EditAlamatActivity
            textInputAlamatLengkap.setOnClickListener {
                textInputAlamatLengkap.isFocusable = true
                textInputAlamatLengkap.isFocusableInTouchMode = true
                textLayoutAlamatLengkap.requestFocus()
                KeyboardUtil.showKeyboard(this@EditAlamatActivity, textInputAlamatLengkap)
            }
        }
    }

    private fun submit() {
        if (validateFieldSuccess()) {
            val alamatLengkapInput = binding.textInputAlamatLengkap.text.toString()
            val latInput = binding.tvLat.text.toString().toDouble()
            val lngInput = binding.tvLng.text.toString().toDouble()
            val provinsiInput = binding.autoFillProvinsi.text.toString()
            val kabupatenInput = binding.autoFillKabupaten.text.toString()
            val kecamatanInput = binding.autoFillKecamatan.text.toString()
            val kelurahanInput = binding.autoFillKelurahan.text.toString()
            val rwInput = binding.autoFillRw.text.toString()
            val data = AlamatRequestBody(
                alamatLengkapInput,
                latInput,
                lngInput,
                provinsiInput,
                kabupatenInput,
                kecamatanInput,
                kelurahanInput,
                rwInput
            )
            val intent = Intent()
            intent.putExtra("data_alamat", data)
            setResult(RESULT_OK,intent)
            Toast.makeText(
                this@EditAlamatActivity,
                "Data berhasil disimpan!",
                Toast.LENGTH_LONG
            ).show()
            finish()
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                "Tolong masukkan data dengan benar",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun validateFieldSuccess(): Boolean {
        var isValid = true

        if (!validateAlamatLengkap()) isValid = false
        if (!validateLatLng()) isValid = false
        if (!validateProvinsi()) isValid = false
        if (!validateKabupaten()) isValid = false
        if (!validateKecamatan()) isValid = false
        if (!validateKelurahan()) isValid = false
        if (!validateRw()) isValid = false

        return isValid
    }

    private fun validateAlamatLengkap(): Boolean {
        val alamatLengkap = binding.textInputAlamatLengkap.text.toString()
        val validationResult = alamat.validateAlamatLengkap(alamatLengkap)
        return if (validationResult.isSuccessful) {
            true
        } else {
            binding.textLayoutAlamatLengkap.apply {
                isErrorEnabled = true
                error = validationResult.errorMessage
            }
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateLatLng(): Boolean {
        val lat = binding.tvLat.text.toString()
        val lng = binding.tvLng.text.toString()
        val validationResult = alamat.validateLatLng(lat, lng)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateProvinsi(): Boolean {
        val provinsi = binding.autoFillProvinsi
        val validationResult = alamat.validateProvinsi(provinsi)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateKabupaten(): Boolean {
        val kabupaten = binding.autoFillKabupaten
        val validationResult = alamat.validateKabupaten(kabupaten)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateKecamatan(): Boolean {
        val kecamatan = binding.autoFillKecamatan
        val validationResult = alamat.validateKecamatan(kecamatan)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateKelurahan(): Boolean {
        val kelurahan = binding.autoFillKelurahan
        val validationResult = alamat.validateKelurahan(kelurahan)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun validateRw(): Boolean {
        val rw = binding.autoFillRw
        val validationResult = alamat.validateRw(rw)
        return if (validationResult.isSuccessful) {
            true
        } else {
            Toast.makeText(
                this@EditAlamatActivity,
                validationResult.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                        binding.apply {
                            tvLat.text = "${list[0].latitude}"
                            tvLng.text = "${list[0].longitude}"
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.text_input_alamat_lengkap -> {
                    if (hasFocus) {
                        if (binding.textInputAlamatLengkap.text?.isNotBlank() == true) {
                            binding.textLayoutAlamatLengkap.isEndIconVisible = true
                        }
                        if (binding.textLayoutAlamatLengkap.isErrorEnabled) {
                            binding.textLayoutAlamatLengkap.isErrorEnabled = false
                        }
                    } else {
                        validateAlamatLengkap()
                        binding.textLayoutAlamatLengkap.isEndIconVisible = false
                        val inputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        }
    }
}