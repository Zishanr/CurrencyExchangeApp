package com.zishan.paypaycurrencyconversion.domain.usecase

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepo
import com.zishan.paypaycurrencyconversion.domain.datamapper.PayPayDataMapper
import com.zishan.paypaycurrencyconversion.domain.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.domain.uimodels.ExchangeRateUIModel
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

    private lateinit var payPayRepo: PayPayRepo
    private lateinit var payDataMapper: PayPayDataMapper
    private lateinit var currencyUseCase: CurrencyUseCase
    private lateinit var currencyList: List<CurrencyEntity>
    private lateinit var exchangeList: List<ExchangeRateEntity>
    private lateinit var currencyListUIModel: List<CurrencyTypeUIModel>
    private lateinit var exchangeRateUiModelList: List<ExchangeRateUIModel>
    private lateinit var currencyMap: Map<String, String>

    @Before
    fun setUp() {
        payPayRepo = mockk(relaxed = true)
        payDataMapper = mockk(relaxed = true)
        currencyUseCase = spyk(CurrencyUseCase(payPayRepo, payDataMapper))

        //Mocking data for my testing
        currencyList =
            listOf(
                CurrencyEntity(
                    currencyName = "INR",
                    currencyValue = "82.85"
                ), CurrencyEntity(currencyName = "JPY", currencyValue = "147.63")
            )
        exchangeList =
            listOf(
                ExchangeRateEntity(currencyName = "INR", currencyValue = 82.85),
                ExchangeRateEntity(currencyName = "JPY", currencyValue = 147.63)
            )

        currencyListUIModel =
            listOf(CurrencyTypeUIModel("INR : Indian Rupee"))

        exchangeRateUiModelList =
            listOf(
                ExchangeRateUIModel(currencyName = "INR", exchangeValue = 82.85),
                ExchangeRateUIModel(currencyName = "JPY", exchangeValue = 147.63)
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
            //Given
            coEvery { payPayRepo.getCurrencies() } returns currencyList
            coEvery { payDataMapper.mapToCurrencyUIList(any()) } returns currencyListUIModel

            //When
            val result = currencyUseCase.getCurrenciesList()

            //Then
            assertEquals(1, result.size)
            assertEquals("INR : Indian Rupee", result[0].currency)

        }
    }

    @Test
    fun `getExchangeRateData returns empty list if user has selected select currency`() {
        runTest {
            //When
            val result = currencyUseCase.getExchangeRateData(0, 82.23)

            //Then
            assertEquals(0, result.size)
        }
    }

    @Test
    fun `getExchangeRateData returns empty list if user has selected any currency`() {
        runTest {

            //Given
            currencyUseCase.saveCurrencyKeyList(currencyList)
            coEvery { payPayRepo.getExchangeRates() } returns exchangeList
            coEvery { payPayRepo.getSelectedCurrencyRate(any()) } returns 82.85
            coEvery {
                payDataMapper.mapToCurrencyUIModel(
                    exchangeList,
                    82.85,
                    82.85
                )
            } returns exchangeRateUiModelList

            //When
            val result = currencyUseCase.getExchangeRateData(1, 82.85)

            //Then
            assertEquals("82.85", result[0].exchangeValue.toString())
        }
    }
}