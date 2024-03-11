package com.zishan.paypaycurrencyconversion.view.uistate

sealed class CurrencyExchangeUIState<out T : Any> {
    data class Success<out T : Any>(val data: T) : CurrencyExchangeUIState<T>()
    data class Fail(val throwable: Throwable) : CurrencyExchangeUIState<Nothing>()
    data object Loading : CurrencyExchangeUIState<Nothing>()
}