package com.zishan.paypaycurrencyconversion.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.databinding.CurrencyExchnageItemBinding
import com.zishan.paypaycurrencyconversion.utils.formatAsCurrency

class CurrencyExchangeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = CurrencyExchnageItemBinding.bind(itemView)
    fun bindView(item: ExchangeRateEntity) {
        binding.currencyName.text = item.currencyName
        binding.currencyValue.text = item.currencyValue.formatAsCurrency()
    }
}