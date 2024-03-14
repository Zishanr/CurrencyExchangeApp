package com.zishan.paypaycurrencyconversion.utils

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PayPayUtils @Inject constructor() {
    fun isRefreshData(timeStamp: Long?): Boolean {
        // Logic to refresh data from remote source once every 30 minutes
        val timeDiff = System.currentTimeMillis() - (timeStamp ?: 0L)
        return timeDiff > TimeUnit.MINUTES.toMillis(PayPayConstant.DBConstant.REFRESH_THRESHOLD)
    }
}