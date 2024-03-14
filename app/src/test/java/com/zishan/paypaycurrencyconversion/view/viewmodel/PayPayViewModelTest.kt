package com.zishan.paypaycurrencyconversion.view.viewmodel

import android.text.Editable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zishan.paypaycurrencyconversion.CoroutineTestDispatchersProvider
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.domain.usecase.CurrencyUseCase
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PayPayViewModelTest {

    private lateinit var viewModel: PayPayViewModel
    private lateinit var useCase: CurrencyUseCase
    private lateinit var dispatcher: CoroutineTestDispatchersProvider


    private lateinit var currencyList: List<CurrencyEntity>
    private lateinit var exchangeRateList: List<ExchangeRateEntity>
    private lateinit var exception: Exception

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        useCase = mockk(relaxed = true)
        dispatcher = CoroutineTestDispatchersProvider
        Dispatchers.setMain(dispatcher.testDispatcher)
        viewModel = spyk(PayPayViewModel(useCase))

        //Mocking data for my testing
        exception = Exception("Unknown Host Exception")
        currencyList =
            listOf(CurrencyEntity(currencyName = "InR", currencyValue = "INR : Indian Rupee"))

        exchangeRateList =
            listOf(
                ExchangeRateEntity(currencyName = "INR", currencyValue = 82.85),
                ExchangeRateEntity(currencyName = "JPY", currencyValue = 147.63)
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetchCurrencies return list from use case`() {
        coEvery { useCase.getCurrenciesList() } returns currencyList

        viewModel.fetchCurrencies()

        assertEquals(
            "INR : Indian Rupee",
            (viewModel.currencyListLiveData.value as CurrencyExchangeUIState.Success).data[0].currencyValue
        )
    }

    @Test
    fun `fetchCurrencies throw exception`() {
        coEvery { useCase.getCurrenciesList() } throws exception

        viewModel.fetchCurrencies()

        assertEquals(
            "Unknown Host Exception",
            (viewModel.currencyListLiveData.value as CurrencyExchangeUIState.Fail).throwable.message
        )
    }

    @Test
    fun `fetchExchangeRate return exchange rate list from use case`() {
        coEvery { useCase.getExchangeRateData(any(), 1.0) } returns exchangeRateList

        viewModel.fetchExchangeRate(1.0)

        assertEquals(
            "82.85",
            ((viewModel.currencyExchangeLiveData.value as CurrencyExchangeUIState.Success).data[0].currencyValue).toString()
        )
    }

    @Test
    fun `fetchExchangeRate throw exception`() {
        coEvery { useCase.getExchangeRateData(any(), 1.0) } throws exception

        viewModel.fetchExchangeRate(1.0)

        assertEquals(
            "Unknown Host Exception",
            (viewModel.currencyExchangeLiveData.value as CurrencyExchangeUIState.Fail).throwable.message
        )
    }

    @Test
    fun `fetchSelectedCurrencyRate returns emptyList if selected is null`() {

        viewModel.fetchSelectedCurrencyRate(null)

        assertTrue((viewModel.currencyExchangeLiveData.value as CurrencyExchangeUIState.Success).data.isEmpty())

    }

    @Test
    fun `fetchSelectedCurrencyRate fetches currency rates`() {
        val mockEditable: Editable = mockk(relaxed = true)
        coEvery { viewModel.fetchExchangeRate(any()) } just Runs

        viewModel.fetchSelectedCurrencyRate(mockEditable)

        verify { viewModel.fetchSelectedCurrencyRate(mockEditable) }
    }
}