package com.zishan.paypaycurrencyconversion.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zishan.paypaycurrencyconversion.CoroutineTestDispatchersProvider
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.domain.usecase.CurrencyUseCase
import com.zishan.paypaycurrencyconversion.domain.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.domain.uimodels.ExchangeRateUIModel
import com.zishan.paypaycurrencyconversion.view.uistate.CurrencyExchangeUIState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PayPayViewModelTest {

    private lateinit var viewModel: PayPayViewModel
    private lateinit var useCase: CurrencyUseCase
    private lateinit var dispatcher: CoroutineTestDispatchersProvider


    private lateinit var currencyListUIModel: List<CurrencyTypeUIModel>
    private lateinit var exchangeRateUiModelList: List<ExchangeRateUIModel>
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
        currencyListUIModel =
            listOf(CurrencyTypeUIModel("INR : Indian Rupee"))

        exchangeRateUiModelList =
            listOf(
                ExchangeRateUIModel(currencyName = "INR", exchangeValue = 82.85),
                ExchangeRateUIModel(currencyName = "JPY", exchangeValue = 147.63)
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetchCurrencies return list from use case`() {
        //Given
        coEvery { useCase.getCurrenciesList() } returns currencyListUIModel

        //When
        viewModel.fetchCurrencies()

        //Then
        assertEquals(
            "INR : Indian Rupee",
            (viewModel.currencyListLiveData.value as CurrencyExchangeUIState.Success).data[0].currency
        )
    }

    @Test
    fun `fetchCurrencies throw exception`() {
        //Given
        coEvery { useCase.getCurrenciesList() } throws exception

        //When
        viewModel.fetchCurrencies()

        //Then
        assertEquals(
            "Unknown Host Exception",
            (viewModel.currencyListLiveData.value as CurrencyExchangeUIState.Fail).throwable.message
        )
    }

    @Test
    fun `fetchExchangeRate return exchange rate list from use case`() {
        //Given
        coEvery { useCase.getExchangeRateData(any(), 1.0) } returns exchangeRateUiModelList

        //When
        viewModel.fetchExchangeRate(1.0)

        //Then
        assertEquals(
            "82.85",
            ((viewModel.currencyExchangeLiveData.value as CurrencyExchangeUIState.Success).data[0].exchangeValue).toString()
        )
    }

    @Test
    fun `fetchExchangeRate throw exception`() {
        //Given
        coEvery { useCase.getExchangeRateData(any(), 1.0) } throws exception

        //When
        viewModel.fetchExchangeRate(1.0)

        //Then
        assertEquals(
            "Unknown Host Exception",
            (viewModel.currencyExchangeLiveData.value as CurrencyExchangeUIState.Fail).throwable.message
        )
    }
}