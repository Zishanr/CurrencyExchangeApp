package com.zishan.paypaycurrencyconversion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zishan.paypaycurrencyconversion.R
import com.zishan.paypaycurrencyconversion.domain.uimodels.ExchangeRateUIModel

class CurrencyExchangeAdapter(diffCallback: DiffUtil.ItemCallback<ExchangeRateUIModel> = ComponentsDiffCallBacks()) :
    ListAdapter<ExchangeRateUIModel, CurrencyExchangeVH>(diffCallback) {

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

    class ComponentsDiffCallBacks : DiffUtil.ItemCallback<ExchangeRateUIModel>() {
        override fun areItemsTheSame(
            oldItem: ExchangeRateUIModel,
            newItem: ExchangeRateUIModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ExchangeRateUIModel,
            newItem: ExchangeRateUIModel
        ): Boolean {
            return newItem == oldItem
        }
    }
}