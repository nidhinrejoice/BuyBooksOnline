//package com.forthcode.customerapp.api
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitBuilder {
//
////    const val BASE_URL: String = "https://jsonplaceholder.typicode.com"
////    const val BASE_URL: String = "https://go.ngopos.com/app/"
//    const val BASE_URL: String = "http://192.168.29.8:10000/app/"
//
//    val retrofitBuilder: Retrofit.Builder by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//    }
//
//    val apiService: ApiService by lazy {
//
//        retrofitBuilder
//            .build()
//            .create(ApiService::class.java)
//    }
//
//}
//
//
