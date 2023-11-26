package com.example.navifationview.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.navifationview.databinding.PaginationRecyclerviewItemBinding
import com.example.navifationview.retrofit_data_model.RetrofitDataModel

class PagingAdapter :
    PagingDataAdapter<RetrofitDataModel.Product, PagingAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: PaginationRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    var phoneInfo: MutableList<RetrofitDataModel.Product> = mutableListOf()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RetrofitDataModel.Product>() {
            override fun areItemsTheSame(
                oldItem: RetrofitDataModel.Product,
                newItem: RetrofitDataModel.Product
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RetrofitDataModel.Product,
                newItem: RetrofitDataModel.Product
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = phoneInfo[position]
        val item= getItem(position)
        holder.binding.data = item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PaginationRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)

    }


}



