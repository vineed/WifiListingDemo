package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.WifiItemBinding
import com.example.myapplication.model.WifiItem

class WifiItemAdapter(private val wifiItems: List<WifiItem>, context: Context) :
    RecyclerView.Adapter<WifiItemAdapter.WifiItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class WifiItemViewHolder(private val wifiItemBinding: WifiItemBinding) : RecyclerView.ViewHolder(wifiItemBinding.root) {

        fun setData(wifiItem: WifiItem) {
            wifiItemBinding.tvWifiName.text = wifiItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiItemViewHolder {
        return WifiItemViewHolder(WifiItemBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: WifiItemViewHolder, position: Int) {
        holder.setData(wifiItems[position])
    }

    override fun getItemCount() = wifiItems.size
}