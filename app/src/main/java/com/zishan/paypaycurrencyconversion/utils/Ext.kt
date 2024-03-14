package com.zishan.paypaycurrencyconversion.utils

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun CoroutineScope.launchAndCatchError(
    block: suspend () -> Unit,
    onError: suspend (throwable: Throwable) -> Unit
) {
    launch {
        try {
            block.invoke()
        } catch (throwable: Throwable) {
            try {
                onError.invoke(throwable)
            } catch (_: Throwable) {

            }
        }
    }
}

// Currency Formatter
fun Double.formatAsCurrency(): String = "%.2f".format(this)

fun Context?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()

}

