package com.zishan.paypaycurrencyconversion.domain.usecase

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepoImpl
import com.zishan.paypaycurrencyconversion.domain.datamapper.PayPayDataMapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyUseCaseTest {

    private lateinit var payPayRepoImpl: PayPayRepoImpl
    private lateinit var payDataMapper: PayPayDataMapper
    private lateinit var currencyUseCase: CurrencyUseCase
    private lateinit var currencyList: List<CurrencyEntity>
    private lateinit var exchangeList: List<ExchangeRateEntity>
    private lateinit var currencyMap: Map<String, String>

    @Before
    fun setUp() {
        payPayRepoImpl = mockk(relaxed = true)
        payDataMapper = mockk(relaxed = true)
        currencyUseCase = spyk(CurrencyUseCase(payPayRepoImpl, payDataMapper))

        //Mocking data for my testing
        currencyList =
            listOf(
                CurrencyEntity(
                    currencyName = "INR",
                    currencyValue = "Indian Rupee"
                ), CurrencyEntity(currencyName = "JPY", currencyValue = "Japanese Yen")
            )
        exchangeList =
            listOf(
                ExchangeRateEntity(currencyName = "INR", currencyValue = 82.85),
                ExchangeRateEntity(currencyName = "JPY", currencyValue = 147.63)
            )

        currencyMap = mapOf("INR" to "Indian Rupee")

    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCurrenciesList fetching currency list from repo`() {
        runTest {
            coEvery { payPayRepoImpl.getCurrencies() } returns currencyList
            coEvery { payDataMapper.mapToCurrencyList(any()) } returns currencyList

            val result = currencyUseCase.getCurrenciesList()

            assertEquals(2, result.size)
            assertEquals("Indian Rupee", result[0].currencyValue)

        }
    }

    @Test
    fun `getExchangeRateData returns empty list if user has selected select currency`() {
        runTest {
            val result = currencyUseCase.getExchangeRateData(0, 82.23)

            assertEquals(0, result.size)
        }
    }

    @Test
    fun `getExchangeRateData returns empty list if user has selected any currency`() {
        runTest {
            currencyUseCase.saveCurrencyKeyList(currencyList)
            coEvery { payPayRepoImpl.getExchangeRates() } returns exchangeList
            coEvery { payPayRepoImpl.getSelectedCurrencyRate(any()) } returns 82.85
            coEvery {
                payDataMapper.mapToCurrencyModel(
                    exchangeList,
                    82.85,
                    82.85
                )
            } returns exchangeList

            val result = currencyUseCase.getExchangeRateData(1, 82.85)

            assertEquals("82.85", result[0].currencyValue.toString())
        }
    }
}