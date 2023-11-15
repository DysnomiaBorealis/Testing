package com.wastehub.user.presentation.ui.transaction.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wastehub.user.R
import com.wastehub.user.common.dummy_data.model.pickup_order.PickupOrder
import com.wastehub.user.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat

class PickupOrderAdapter(private val context: Context, private val records: List<PickupOrder>) : RecyclerView.Adapter<PickupOrderAdapter.PickupOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupOrderViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickupOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = records.size

    override fun onBindViewHolder(holder: PickupOrderViewHolder, position: Int) {
        holder.bind(records[position])
    }

    inner class PickupOrderViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: PickupOrder) {

            // Parse the original date string
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = originalFormat.parse(record.updatedAt)

            // Define the desired format for the output
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")

            // Format the date into the desired format
            val formattedDate = outputFormat.format(date)

            // Set the formatted date to the TextView
            binding.date.text = formattedDate

            setStatePickupStatus(record.status)
//            binding.wasteVolume.text = record.wasteVolume.name
            binding.personName.text = record.picker.name
            binding.amount.text = String.format("$%,.2f", record.wasteVolume.pickupFee)
        }

        private fun setStatePickupStatus(status: String) {
            when (status) {
                "requested" -> {
                    binding.status.text = "Pickup is requested"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.yellow_500))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_requested, 0, 0, 0)
                }
                "confirmed" -> {
                    binding.status.text = "Pickup has been confirmed"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.yellow_500))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_confirmed, 0, 0, 0)
                }
                "in progress" -> {
                    binding.status.text = "Pickup is in progress"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.blue_300))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_in_progress, 0, 0, 0)
                }
                "completed" -> {
                    binding.status.text = "Pickup is completed"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.green_500))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_completed, 0, 0, 0)
                }
                "cancelled" -> {
                    binding.status.text = "Pickup is cancelled"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.red_300))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_cancelled, 0, 0, 0)
                }
                "failed" -> {
                    binding.status.text = "Pickup has failed"
                    binding.status.setTextColor(ContextCompat.getColor(context, R.color.red_300))
                    binding.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_cancelled, 0, 0, 0)
                }
            }
        }
    }
}