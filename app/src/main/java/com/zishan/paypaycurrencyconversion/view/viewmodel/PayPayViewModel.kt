package com.zishan.paypaycurrencyconversion.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zishan.paypaycurrencyconversion.usecase.CurrencyUseCase
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PayPayViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) :
    ViewModel() {

    private val _currencyListLiveData: MutableLiveData<Result<List<String>>> = MutableLiveData()
    val currencyListLiveData: LiveData<Result<List<String>>> = _currencyListLiveData

    private val _currencyExchangeLiveData: MutableLiveData<List<ExchangeRateUIModel>> =
        MutableLiveData()
    val currencyExchangeLiveData: LiveData<List<ExchangeRateUIModel>> =
        _currencyExchangeLiveData

    var selectedSpinnerIndex = 0

    init {
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            val currencyData = currencyUseCase.getCurrenciesList()
            _currencyListLiveData.value = Result.success(currencyData)
        }
    }

    fun fetchExchangeRate(textValue: Double) {
        viewModelScope.launch {
            val exchangeData = currencyUseCase.getExchangeRateData(selectedSpinnerIndex, textValue)
            exchangeData?.let { _currencyExchangeLiveData.value = it }
        }
    }

}