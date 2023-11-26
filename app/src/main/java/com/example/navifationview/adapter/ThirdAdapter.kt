package com.example.navifationview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navifationview.checkout_database.CheckOutItem
import com.example.navifationview.databinding.CheckoutRecyclerviewBinding


class ThirdAdapter(checkOutItem: ArrayList<CheckOutItem>,private val clickListener: ThirdAdapter.ItemClickListener) :
    RecyclerView.Adapter<ThirdAdapter.MyViewHolder>() {

    val checkOutItemUpdated: ArrayList<CheckOutItem> = arrayListOf()

    init {
        checkOutItemUpdated.addAll(checkOutItem)
    }


    inner class MyViewHolder(val binding: CheckoutRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CheckoutRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = checkOutItemUpdated[position]
        holder.binding.data = item
        holder.binding.delete.setOnClickListener {
            clickListener.onItemDeleted(item,position)
        }
    }

    override fun getItemCount(): Int {
        return checkOutItemUpdated.size
    }


    fun updateList(items: List<CheckOutItem>) {
        checkOutItemUpdated.clear()
        checkOutItemUpdated.addAll(items)
        Log.d("size_db", "addItem: size: ${checkOutItemUpdated.size}")
        notifyDataSetChanged()
    }


    interface ItemClickListener {
        fun onItemDeleted(item: CheckOutItem, position: Int)
    }


}


