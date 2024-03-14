package com.zishan.paypaycurrencyconversion.utils


object PayPayConstant {
    const val EXCHANGE_APP_ID_KEY = "app_id"
    const val SPAN_SIZE = 3
    object NetworkConst {
        const val BASE_URL = "https://openexchangerates.org/api/"
        const val HTTP_SUCCESS = 200
        const val ERROR_MESSAGE = "Something Unexpected Happened "
        const val CONNECTION_TIMEOUT = 15L
    }

    object DBConstant {
        const val CURRENCY_TIMESTAMP_KEY = "currency"
        const val EXCHANGE_RATE_TIMESTAMP_KEY = "exchange_rate"
        const val REFRESH_THRESHOLD = 30L
        const val CURRENCIES_TABLE = "currencies"
        const val EXCHANGE_RATE_TABLE = "exchange_rate"
        const val REFRESH_FREQUENCY_TABLE = "refresh_frequency"
    }

}