package com.zishan.paypaycurrencyconversion.view.viewmodel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.domain.usecase.CurrencyUseCase
import com.zishan.paypaycurrencyconversion.utils.launchAndCatchError
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Fail
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Loading
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Success
import javax.inject.Inject


class PayPayViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
) : ViewModel() {

    private val _currencyListLiveData: MutableLiveData<CurrencyExchangeUIState<List<CurrencyEntity>>> =
        MutableLiveData()
    val currencyListLiveData: LiveData<CurrencyExchangeUIState<List<CurrencyEntity>>> =
        _currencyListLiveData

    private val _currencyExchangeLiveData: MutableLiveData<CurrencyExchangeUIState<List<ExchangeRateEntity>>> =
        MutableLiveData()
    val currencyExchangeLiveData: LiveData<CurrencyExchangeUIState<List<ExchangeRateEntity>>> =
        _currencyExchangeLiveData

    var selectedSpinnerIndex = 0

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        _currencyListLiveData.value = Loading
        viewModelScope.launchAndCatchError(block = {
            val currencyData = currencyUseCase.getCurrenciesList()
            _currencyListLiveData.value = Success(currencyData)
        }, onError = {
            _currencyListLiveData.value = Fail(it)
        })
    }

    fun fetchExchangeRate(textValue: Double) {
        _currencyExchangeLiveData.value = Loading
        viewModelScope.launchAndCatchError(block = {
            val exchangeData = currencyUseCase.getExchangeRateData(selectedSpinnerIndex, textValue)
            exchangeData.let { _currencyExchangeLiveData.value = Success(it) }
        }, onError = {
            _currencyExchangeLiveData.value = Fail(it)
        })
    }

    fun fetchSelectedCurrencyRate(value: Editable?) {
        if (!value.isNullOrEmpty()) {
            fetchExchangeRate(value.toString().toDouble())
        } else {
            _currencyExchangeLiveData.value = Success(emptyList())
        }
    }
}


