
package com.nidhin.customerapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClient {

    fun setupOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .build()
    }
}