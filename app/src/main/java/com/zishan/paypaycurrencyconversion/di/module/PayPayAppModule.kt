package com.zishan.paypaycurrencyconversion.di.module

import com.zishan.paypaycurrencyconversion.data.datasource.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.RemoteDataSourceImpl
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.ExchangeCurrencyApi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
class PayPayAppModule {

    @Provides
    fun providesExchangeCurrencyApi(retrofit: Retrofit) : ExchangeCurrencyApi {
        return retrofit.create(ExchangeCurrencyApi::class.java)
    }

    @Provides
    fun provideRemoteDataSource(exchangeCurrencyApi: ExchangeCurrencyApi): RemoteDataSource {
        return RemoteDataSourceImpl(exchangeCurrencyApi)
    }

    @Provides
    fun provideDispatcher(): Dispatchers {
        return Dispatchers
    }
}