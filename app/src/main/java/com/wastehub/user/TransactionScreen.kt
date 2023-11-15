package com.wastehub.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wastehub.user.common.dummy_data.model.pickup_order.PickupOrder
import com.wastehub.user.common.dummy_data.repository.pickup_order.PickupOrderRepository
import com.wastehub.user.databinding.FragmentTransactionScreenBinding
import com.wastehub.user.presentation.ui.transaction.adapter.PickupOrderAdapter

class TransactionScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTransactionScreenBinding.inflate(inflater, container, false)
        val recyclerView = binding.recyclerView // Ambil referensi ke RecyclerView dari binding

        // Inisialisasi adapter dan data dummy
        val adapter = PickupOrderAdapter(requireContext(), getPickupOrders()) // Ganti dataList dengan data yang sesuai

        // Atur LayoutManager
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        // Atur adapter ke RecyclerView
        recyclerView.adapter = adapter

        return binding.root
    }

    private fun getPickupOrders(): List<PickupOrder> {
        val pickupOrderRepository = PickupOrderRepository(requireContext())
        return pickupOrderRepository.getMockPickupOrders() ?: emptyList()
    }

}
