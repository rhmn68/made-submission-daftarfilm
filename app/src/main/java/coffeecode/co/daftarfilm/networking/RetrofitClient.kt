package coffeecode.co.daftarfilm.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val REQUEST_TIMEOUT = 30

    fun getClient(baseUrl:  String) : Retrofit {
        val gsonBuilder = GsonBuilder().setLenient().create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }
}