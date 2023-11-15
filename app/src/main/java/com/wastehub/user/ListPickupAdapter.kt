package com.wastehub.user

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ListPickupAdapter(private val listPrice: ArrayList<DataPrice>) : RecyclerView.Adapter<ListPickupAdapter.ListViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tv_category)
        val tvMaxWeight: TextView = itemView.findViewById(R.id.tv_max_weight)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val cardCategory: MaterialCardView = itemView.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_price, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (category, weight, price) = listPrice[position]
        holder.tvCategory.text = category
        holder.tvMaxWeight.text = weight
        holder.tvPrice.text = price
        if (position == selectedPosition) {
            // Set card stroke color when clicked
            holder.cardCategory.strokeColor = Color.parseColor("#5C61DC")
        } else {
            // Reset card stroke color
            holder.cardCategory.strokeColor = 0x000000
        }

        holder.cardCategory.setOnClickListener {
            // Update selected position when clicked
            if (selectedPosition != position) {
                val previousSelected = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int = listPrice.size
}