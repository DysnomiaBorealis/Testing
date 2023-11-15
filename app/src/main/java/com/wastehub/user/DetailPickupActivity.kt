package com.wastehub.user

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.NumberFormat
import java.util.Locale

class DetailPickupActivity : AppCompatActivity() {
    private lateinit var rvCategoryPickup: RecyclerView
    private lateinit var dialog: BottomSheetDialog
    private val list = ArrayList<DataPrice>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pickup)

        val btnBottomSheet = findViewById<Button>(R.id.btn_bottom_sheet)
        btnBottomSheet.setOnClickListener {
            bottomSheet()
        }

        list.addAll(getListData())
    }

    private fun bottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_lokasi_waktu, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
//        rvCategoryPickup = dialogView.findViewById(R.id.rv_category_pickup)
//        rvCategoryPickup.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val listHeroAdapter = ListPickupAdapter(list)
//        rvCategoryPickup.adapter = listHeroAdapter
        dialog.show()
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
}