package com.zishan.paypaycurrencyconversion.di.module


import com.zishan.paypaycurrencyconversion.BuildConfig
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.NetworkConst.CONNECTION_TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {

    @ApplicationScope
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PayPayConstant.NetworkConst.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @ApplicationScope
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @ApplicationScope
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedUrl = originalRequest.url.newBuilder()
                .addQueryParameter(
                    PayPayConstant.EXCHANGE_APP_ID_KEY,
                    BuildConfig.EXCHANGE_APP_ID
                )
                .build()
            val modifiedRequest = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(modifiedRequest)
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}