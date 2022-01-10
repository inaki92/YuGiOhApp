package com.inaki.yugiohapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.inaki.yugiohapp.rest.CardApi
import com.inaki.yugiohapp.rest.CardsRepository
import com.inaki.yugiohapp.rest.CardsRepositoryImpl
import com.inaki.yugiohapp.viewmodel.CardsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    // provides the cards repository implementation
    fun provideCardsRepo(cardsApi: CardApi): CardsRepository = CardsRepositoryImpl(cardsApi)

    // provide Gson object
    fun provideGson() = GsonBuilder().create()

    // provide logging interceptor
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // provide okhttp client
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    // providing the retrofit builder
    fun provideCardsApi(okHttpClient: OkHttpClient, gson: Gson): CardApi =
        Retrofit.Builder()
            .baseUrl(CardApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(CardApi::class.java)

    single { provideGson() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideCardsApi(get(), get()) }
    single { provideCardsRepo(get()) }
}

val viewModelModule = module {
    viewModel { CardsViewModel(get()) }
}