package com.wastehub.user.presentation.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wastehub.user.DataPrice
import com.wastehub.user.ListPickupAdapter
import com.wastehub.user.R
import com.wastehub.user.common.dummy_data.model.pickup_order.PickupOrder
import com.wastehub.user.common.dummy_data.repository.pickup_order.PickupOrderRepository
import com.wastehub.user.common.utils.SessionManager
import com.wastehub.user.data.AlamatRequestBody
import com.wastehub.user.databinding.FragmentHomeScreenBinding
import com.wastehub.user.presentation.ui.alamat.EditAlamatActivity
import com.wastehub.user.presentation.ui.transaction.adapter.PickupOrderAdapter
import com.wastehub.user.presentation.viewmodel.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class HomeScreen : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyPickupOrder: List<PickupOrder>
    private lateinit var adapter: PickupOrderAdapter

    private val session: SessionViewModel by viewModels()

    @Inject
    lateinit var preferences: SessionManager


    private lateinit var rvCategoryPickup: RecyclerView
    private lateinit var dialog: BottomSheetDialog
    private val list = ArrayList<DataPrice>()
    private val editAlamatActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val receivedData = data?.getParcelableExtra<AlamatRequestBody>("data_alamat")
                if (receivedData != null) {
                    alamatDone(receivedData)
                }
            }
        }
    private lateinit var dialogView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchWasteRecord()

        session.token.observe(viewLifecycleOwner) { token ->
            Log.d("TOKENNN", token.toString())
        }

        val btnRequestPickup = binding.btnRequestPickup
        btnRequestPickup.setOnClickListener {
            bottomSheet()
        }
        list.addAll(getListData())
    }

    private fun fetchWasteRecord() {
        val pickupOrderRepository = PickupOrderRepository(requireContext())
        historyPickupOrder = pickupOrderRepository.getMockPickupOrders() ?: emptyList()
        val adapter = PickupOrderAdapter(requireContext(), historyPickupOrder) // Ganti dataList dengan data yang sesuai

        binding.apply {
            recyclerWasteRecord.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerWasteRecord.adapter = adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bottomSheet() {
        dialogView = layoutInflater.inflate(R.layout.bottom_sheet_lokasi_waktu, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()

        val radioButtonTanggal = dialogView.findViewById<RadioButton>(R.id.radio_tanggal)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val besok = calendar.time
        val besokSubmit = SimpleDateFormat("yyyy-mm-ddThh:mm:ssZ", Locale("id", "ID")).format(besok)
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        radioButtonTanggal.text = dateFormat.format(besok)

        val btnEditAlamat = dialogView.findViewById<Button>(R.id.button_edit_alamat)
        btnEditAlamat.setOnClickListener {
            val intent = Intent(requireContext(), EditAlamatActivity::class.java)
            editAlamatActivityResultLauncher.launch(intent)
        }

        val layoutRadioButtonTanggal =
            dialogView.findViewById<LinearLayout>(R.id.layout_radio_tanggal)
        radioButtonTanggal.setOnClickListener {
            if (radioButtonTanggal.isSelected) {
                layoutRadioButtonTanggal.setBackgroundResource(R.drawable.rounded_green_square_bg)
            } else {
                layoutRadioButtonTanggal.setBackgroundResource(R.drawable.rounded_square_bg_with_stroke)
            }
        }

        val switchToSecondBottomSheetButton =
            dialogView.findViewById<Button>(R.id.button_detail_pesanan)
        switchToSecondBottomSheetButton.setOnClickListener {
            if (radioButtonTanggal.isChecked) {
                // Hapus konten bottom sheet pertama dan gantikan dengan konten bottom sheet kedua
                val rootLayout =
                    dialogView.findViewById<ConstraintLayout>(R.id.layout_bottom_sheet_lokasi_waktu)
                // Hapus semua view yang ada di rootLayout
                rootLayout.removeAllViews()
                // Gantikan dengan konten dari bottom sheet kedua
                val secondBottomSheetView =
                    layoutInflater.inflate(R.layout.detail_pickup_bottomsheet, null)
                rvCategoryPickup = secondBottomSheetView.findViewById(R.id.rv_category_pickup)
                rvCategoryPickup.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                val listHeroAdapter = ListPickupAdapter(list)
                rvCategoryPickup.adapter = listHeroAdapter
                rootLayout.addView(secondBottomSheetView)
            } else {
                Toast.makeText(
                    context,
                    "Silahkan pilih tanggal terlebih dahulu",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getListData(): ArrayList<DataPrice> {
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataMaxWeight = resources.getStringArray(R.array.data_max_weight)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val listHero = ArrayList<DataPrice>()
        for (i in dataCategory.indices) {
            val priceString = dataPrice[i]
            val formattedPrice = formatPrice(priceString)
            val hero = DataPrice(dataCategory[i], "Maks. ${dataMaxWeight[i]}Kg", formattedPrice)
            listHero.add(hero)
        }
        return listHero
    }

    private fun formatPrice(priceString: String): String {
        val price = priceString.toDoubleOrNull()
        if (price != null) {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            return numberFormat.format(price).replace("Rp", "Rp ").replace(",", ".")
        }
        // Handle invalid price strings or non-numeric values
        return "Invalid Price"
    }

    private fun alamatDone(resultData: AlamatRequestBody) {
        val textViewAlamatLengkap = dialogView.findViewById<TextView>(R.id.text_view_alamat_lengkap)
        textViewAlamatLengkap?.text = resultData.alamatLengkap
    }
}