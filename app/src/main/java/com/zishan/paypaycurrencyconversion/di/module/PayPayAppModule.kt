package com.zishan.paypaycurrencyconversion.di.module

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.db.CurrencyDataBase
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSourceImpl
import com.zishan.paypaycurrencyconversion.data.datasource.remote.ExchangeCurrencyApi
import com.zishan.paypaycurrencyconversion.di.scope.AppScope
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
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