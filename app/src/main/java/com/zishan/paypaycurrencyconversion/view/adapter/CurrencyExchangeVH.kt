package com.zishan.paypaycurrencyconversion.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zishan.paypaycurrencyconversion.databinding.CurrencyExchnageItemBinding
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel

class CurrencyExchangeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = CurrencyExchnageItemBinding.bind(itemView)
    fun bindView(item: ExchangeRateUIModel) {
        binding.currencyName.text = item.currencyName
        binding.currencyValue.text = String.format("%.3f", item.exchangeValue)
    }
}