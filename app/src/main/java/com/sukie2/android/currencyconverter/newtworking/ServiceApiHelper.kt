package com.sukie2.android.currencyconverter.newtworking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://revolut.duckdns.org/"

class ServiceApiHelper(){

    fun createServiceRetrofit(): ServiceApi {
        val instance: ServiceApi by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()

            retrofit.create(ServiceApi::class.java)
        }

        return instance
    }

    private fun getOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .method(original.method, original.body)

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
    }
}