package obj.study

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MyApi {
    private val BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"  //NJXDEEW8R6FU4WACEHBKRASWB

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    val WeatherApi: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }
}