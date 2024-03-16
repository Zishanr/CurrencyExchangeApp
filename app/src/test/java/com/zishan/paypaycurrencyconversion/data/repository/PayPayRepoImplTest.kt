package com.zishan.paypaycurrencyconversion.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zishan.paypaycurrencyconversion.CoroutineTestDispatchersProvider
import com.zishan.paypaycurrencyconversion.data.datasource.local.LocalDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.utils.PayPayUtils
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PayPayRepoImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var dispatcher: CoroutineTestDispatchersProvider
    private lateinit var payUtils: PayPayUtils
    private lateinit var payPayRepoImpl: PayPayRepoImpl
    private lateinit var exception: Exception

    private lateinit var currencyList: List<CurrencyEntity>
    private lateinit var exchangeList: List<ExchangeRateEntity>
    private lateinit var currencyMap: Map<String, String>


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        remoteDataSource = mockk(relaxed = true)
        localDataSource = mockk(relaxed = true)
        dispatcher = CoroutineTestDispatchersProvider
        Dispatchers.setMain(dispatcher.testDispatcher)
        payUtils = mockk(relaxed = true)
        payPayRepoImpl = spyk(PayPayRepoImpl(remoteDataSource, localDataSource, dispatcher, payUtils))


        //Mocking data for my testing
        exception = Exception("Unknown Host Exception")
        currencyList =
            listOf(CurrencyEntity(id = 0, currencyName = "INR", currencyValue = "Indian Rupee"))
        exchangeList =
            listOf(ExchangeRateEntity(currencyName = "INR", currencyValue = 82.0))

        currencyMap = mapOf("INR" to "Indian Rupee")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCurrencies fetches from DB source when data is not needed to refresh`() {
        runTest {

            every { payUtils.isRefreshData(any()) } returns false
            coEvery { localDataSource.getCurrencies() } returns currencyList
            val result = payPayRepoImpl.getCurrencies()

            Assert.assertEquals(1, result.size)
            Assert.assertEquals("INR", result[0].currencyName)
            Assert.assertEquals("Indian Rupee", result[0].currencyValue)
        }
    }

    @Test
    fun `getCurrencies fetches from remote source when data needed to refresh`() {
        runTest {
            every { payUtils.isRefreshData(any()) } returns true
            coEvery { remoteDataSource.getCurrencies() } returns currencyMap
            coEvery { localDataSource.saveCurrencies(any()) } just Runs
            coEvery { localDataSource.saveTimeStamp(any()) } just Runs
            coEvery { localDataSource.getCurrencies() } returns currencyList
            val result = payPayRepoImpl.getCurrencies()

            Assert.assertEquals(1, result.size)
            Assert.assertEquals("INR", result[0].currencyName)
            Assert.assertEquals("Indian Rupee", result[0].currencyValue)
        }
    }

    @Test
    fun `getExchangeRates fetches from DB source when data is not needed to refresh`() {
        runTest {
            every { payUtils.isRefreshData(any()) } returns false
            coEvery { localDataSource.getExchangeRates() } returns exchangeList
            val result = payPayRepoImpl.getExchangeRates()
            Assert.assertEquals(1, result.size)
            Assert.assertEquals("INR", result[0].currencyName)
            Assert.assertEquals("82.0", result[0].currencyValue.toString())
        }
    }

    @Test
    fun `getExchangeRates fetches from remote source when data is needed to refresh`() {
        runTest {
            every { payUtils.isRefreshData(any()) } returns true
            coEvery { remoteDataSource.getExchangeRates() } returns CurrencyExchangeRatesResponse(
                mapOf("INR" to 82.0)
            )
            coEvery { localDataSource.getExchangeRates() } returns exchangeList
            coEvery { localDataSource.saveExchangeRates(any()) } just Runs
            coEvery { localDataSource.saveTimeStamp(any()) } just Runs
            val result = payPayRepoImpl.getExchangeRates()
            Assert.assertEquals(1, result.size)
            Assert.assertEquals("INR", result[0].currencyName)
            Assert.assertEquals("82.0", result[0].currencyValue.toString())
        }
    }

    @Test
    fun `getExchangeRates fetches throw exception when data is needed to refresh and data return from DB`() {
        runTest {
            every { payUtils.isRefreshData(any()) } returns true
            coEvery { remoteDataSource.getExchangeRates() } throws exception
            coEvery { localDataSource.getExchangeRates() } returns exchangeList
            val result = payPayRepoImpl.getExchangeRates()

            Assert.assertEquals(1, result.size)
            Assert.assertEquals("INR", result[0].currencyName)
            Assert.assertEquals("82.0", result[0].currencyValue.toString())
        }
    }

    @Test(expected = Exception::class)
    fun `getExchangeRates fetches throw exception when data is needed to refresh and no data in db`() {
        runTest {
            every { payUtils.isRefreshData(any()) } returns true
            coEvery { remoteDataSource.getExchangeRates() } throws exception
            coEvery { localDataSource.getExchangeRates() } returns listOf()
            payPayRepoImpl.getExchangeRates()

        }
    }

    @Test
    fun `getSelectedCurrencyRate from DB for selected currency`() {
        runTest {
            coEvery { localDataSource.getSelectedCurrencyRate(any()) } returns 82.0
            val result = payPayRepoImpl.getSelectedCurrencyRate("INR")
            Assert.assertEquals("82.0", result.toString())
        }
    }
}