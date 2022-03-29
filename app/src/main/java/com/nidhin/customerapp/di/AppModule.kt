package com.nidhin.customerapp.di

import android.content.Context
import com.airbnb.lottie.utils.Utils
import com.nidhin.customerapp.api.HttpClient
import com.nidhin.customerapp.api.LoggingInterceptor
import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.data.remote.ApiService
import com.nidhin.customerapp.data.repository.HomeRepoImpl
import com.nidhin.customerapp.data.repository.ProductRepoImpl
import com.nidhin.customerapp.data.repository.UserLoginRepoImpl
import com.nidhin.customerapp.data.repository.UserRepoImpl
import com.nidhin.customerapp.domain.repository.HomeRepository
import com.nidhin.customerapp.domain.repository.ProductRepository
import com.nidhin.customerapp.domain.repository.UserLoginRepository
import com.nidhin.customerapp.domain.repository.UserRepository
import com.nidhin.customerapp.persistance.AppDatabase
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import com.google.gson.Gson
import com.nidhin.customerapp.utils.hasNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class AppModule {

    @Provides
    open fun provideLoggingInterceptor(): HttpLoggingInterceptor = LoggingInterceptor.create()

    @Provides
    open fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return HttpClient.setupOkhttpClient(httpLoggingInterceptor)
    }

//    @Provides
//    open fun provideOkHttpClient(
//        @ApplicationContext context: Context
//    ): OkHttpClient {
//        val cacheSize = (5 * 1024 * 1024).toLong()
//        val myCache = Cache(context.cacheDir, cacheSize)
//        return OkHttpClient.Builder()
//            .cache(myCache)
//            .addInterceptor { chain ->
//                var request = chain.request()
//                request =
//                    request.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60)
//                        .build()
//                chain.proceed(request)
//            }
//            .build()
//    }

    @Provides
    open fun provideGson(): Gson {
        return Gson().newBuilder().create()
    }

    @Singleton
    @Provides
    open fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    open fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("baseUrl")
    open fun provideBaseUrl(): String = Constants.API_END_POINT


    @Provides
    @Singleton
    fun provideUserLoginRepository(
        apiService: ApiService,
        sharedPrefsHelper: SharedPrefsHelper, gson: Gson
    ): UserLoginRepository {
        return UserLoginRepoImpl(apiService, sharedPrefsHelper, gson)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        apiService: ApiService, appdatabase: AppDatabase,
        sharedPrefsHelper: SharedPrefsHelper, gson: Gson
    ): HomeRepository {
        return HomeRepoImpl(apiService, appdatabase, sharedPrefsHelper, gson)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ApiService, appdatabase: AppDatabase,
        sharedPrefsHelper: SharedPrefsHelper, gson: Gson
    ): ProductRepository {
        return ProductRepoImpl(apiService, appdatabase, sharedPrefsHelper, gson)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService, appdatabase: AppDatabase,
        sharedPrefsHelper: SharedPrefsHelper, gson: Gson
    ): UserRepository {
        return UserRepoImpl(apiService, appdatabase, sharedPrefsHelper, gson)
    }
}