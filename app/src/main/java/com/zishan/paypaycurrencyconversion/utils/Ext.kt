package com.zishan.paypaycurrencyconversion.utils

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
