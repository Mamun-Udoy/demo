package com.example.navifationview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.databinding.RecyclerViewItemBinding

class MyAdapter(
    private var datalist: ArrayList<ExpenseInfo>,
    private val clickListener: ItemClickListener
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    private val dataListUpdated: ArrayList<ExpenseInfo> = arrayListOf()

    init {
        dataListUpdated.addAll(datalist)
    }


    inner class MyViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListUpdated.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = dataListUpdated[position]
        holder.binding.data=item


//
//        holder.binding.textView3.text = item.expenseType
//        holder.binding.textView7.text = "${item.expenseAmount}"
//        holder.binding.textView8.text = item.timeDate
//        holder.binding.textView9.text = item.expenseDescription

        val menuIV = holder.binding.more
        menuIV.setOnClickListener {

            clickListener.moreItemClicked(menuIV, item, position)

        }
        var isContentVisible = false

        holder.itemView.setOnClickListener {
            if (isContentVisible) {
                // Content is currently visible, hide it
                holder.binding.textView5.visibility = View.GONE
                holder.binding.textView6.visibility = View.GONE
                holder.binding.textView8.visibility = View.GONE
                holder.binding.textView9.visibility = View.GONE
            } else {

                holder.binding.textView5.visibility = View.VISIBLE
                holder.binding.textView6.visibility = View.VISIBLE
                holder.binding.textView8.visibility = View.VISIBLE
                holder.binding.textView9.visibility = View.VISIBLE
            }

            // Toggle the visibility state

            isContentVisible = !isContentVisible
        }

    }


    interface ItemClickListener {
        fun moreItemClicked(imageView: ImageView, item: ExpenseInfo, position: Int)
    }

    fun updateList(items: List<ExpenseInfo>) {
        dataListUpdated.clear()
        dataListUpdated.addAll(items)
        Log.d("size_db", "addItem: size: ${datalist.size}")
        notifyDataSetChanged()
    }
}