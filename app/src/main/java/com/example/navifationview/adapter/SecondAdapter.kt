package com.example.navifationview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navifationview.R
import com.example.navifationview.databinding.BottomSheetBinding

import com.example.navifationview.databinding.RecylerViewNewItemBinding
import com.example.navifationview.retrofit_data_model.RetrofitDataModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class SecondAdapter(private val callback: ItemClickCallback): RecyclerView.Adapter<SecondAdapter.MyViewHolder>() {

    var phoneInfo: MutableList<RetrofitDataModel.Product> = mutableListOf()

    inner class MyViewHolder(val binding: RecylerViewNewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateList(items: List<RetrofitDataModel.Product>) {
        phoneInfo.clear()
        phoneInfo.addAll(items)
        Log.d("size_db", "addItem: size: ${phoneInfo.size}")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondAdapter.MyViewHolder {
        val binding=RecylerViewNewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)

    }
    override fun getItemCount(): Int {
        return phoneInfo.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = phoneInfo[position]
        holder.binding.retrofit = item

        Glide.with(holder.binding.profileImage.context)
            .load("${item.thumbnail}")
            .into(holder.binding.profileImage)

//        Glide.get(holder.binding.profileImage.context).
//        holder.binding.profileImage

        holder.itemView.setOnClickListener {
            callback.onItemClicked(item)
        }

    }

    interface ItemClickCallback {
        fun onItemClicked(item: RetrofitDataModel.Product)
    }


}