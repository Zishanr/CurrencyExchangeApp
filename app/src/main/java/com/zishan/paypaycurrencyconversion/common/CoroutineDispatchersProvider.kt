package com.zishan.paypaycurrencyconversion.common

import kotlinx.coroutines.Dispatchers

object CoroutineDispatchersProvider: CoroutineDispatchers {

    override val main = Dispatchers.Main

    override val io = Dispatchers.IO

    override val default = Dispatchers.Default

    override val immediate = Dispatchers.Main.immediate

    override val computation = Dispatchers.Default
}