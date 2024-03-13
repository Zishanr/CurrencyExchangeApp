package com.zishan.paypaycurrencyconversion.view.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zishan.paypaycurrencyconversion.di.qualifier.ApplicationContext
import com.zishan.paypaycurrencyconversion.domain.usecase.CurrencyUseCase
import com.zishan.paypaycurrencyconversion.utils.launchAndCatchError
import com.zishan.paypaycurrencyconversion.view.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.view.uimodels.ExchangeRateUIModel
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Fail
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Loading
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState.Success
import javax.inject.Inject


// TODO convert to AndroidViewModel
class PayPayViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
    @ApplicationContext val context: Context
) :
    ViewModel() {

    private val _currencyListLiveData: MutableLiveData<CurrencyExchangeUIState<List<CurrencyTypeUIModel>>> =
        MutableLiveData()
    val currencyListLiveData: LiveData<CurrencyExchangeUIState<List<CurrencyTypeUIModel>>> =
        _currencyListLiveData

    private val _currencyExchangeLiveData: MutableLiveData<CurrencyExchangeUIState<List<ExchangeRateUIModel>>> =
        MutableLiveData()
    val currencyExchangeLiveData: LiveData<CurrencyExchangeUIState<List<ExchangeRateUIModel>>> =
        _currencyExchangeLiveData
    private val _isInternetOn: MutableLiveData<Boolean> = MutableLiveData()
    val internetStatus: LiveData<Boolean> = _isInternetOn

    var selectedSpinnerIndex = 0

    init {
        checkInternetStatus()
    }

    private fun checkInternetStatus() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        if (capabilities == null) {
            _isInternetOn.value = false
            return
        }

        val isConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

        _isInternetOn.value = isConnected
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

}


