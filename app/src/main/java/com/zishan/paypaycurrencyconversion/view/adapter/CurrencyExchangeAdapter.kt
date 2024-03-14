package com.zishan.paypaycurrencyconversion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zishan.paypaycurrencyconversion.R
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity

class CurrencyExchangeAdapter(diffCallback: DiffUtil.ItemCallback<ExchangeRateEntity> = ComponentsDiffCallBacks()) :
    ListAdapter<ExchangeRateEntity, CurrencyExchangeVH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyExchangeVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_exchnage_item, parent, false)
        return CurrencyExchangeVH(view)
    }

    override fun onBindViewHolder(holder: CurrencyExchangeVH, position: Int) {
        holder.bindView(getItem(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    class ComponentsDiffCallBacks : DiffUtil.ItemCallback<ExchangeRateEntity>() {
        override fun areItemsTheSame(
            oldItem: ExchangeRateEntity,
            newItem: ExchangeRateEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ExchangeRateEntity,
            newItem: ExchangeRateEntity
        ): Boolean {
            return newItem == oldItem
        }
    }
}