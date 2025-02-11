package obj.study

import ParsingData
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("{location}/today?unitGroup=metric&key=NJXDEEW8R6FU4WACEHBKRASWB&contentType=json")
    suspend fun getWeather(@Path("location") location: String): ParsingData
}