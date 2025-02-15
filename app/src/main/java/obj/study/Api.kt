package obj.study

import ParsingData
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("{location}/next7days?unitGroup=metric&key=RL8PKJNAMHCHAPQVLFTMJCZMT&contentType=json")
    suspend fun getWeather(@Path("location") location: String): ParsingData
}