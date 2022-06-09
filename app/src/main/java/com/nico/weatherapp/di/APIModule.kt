package com.nico.weatherapp.di

import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.service.GeoService.GeoService
import com.nico.weatherapp.data.service.WeatherService.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    private val CONNECTION_TIMEOUT = 20L
    private val READ_TIMEOUT = 2 * CONNECTION_TIMEOUT

    private fun getMainOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        .build()

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.weather_api_url)
            .addConverterFactory(gsonConverterFactory)
            .client(getMainOkHttpClient())
            .build()
    }

    @Provides
    fun WeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    fun GeoCodeService(retrofit: Retrofit): GeoService = retrofit.create(GeoService::class.java)

}