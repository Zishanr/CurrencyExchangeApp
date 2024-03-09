package com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models

import com.google.gson.annotations.SerializedName

data class CurrencyExchangeRatesResponse(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("rates")
    val rates: Map<String, Double>
)