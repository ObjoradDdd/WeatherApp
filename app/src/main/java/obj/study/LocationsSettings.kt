package obj.study

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationsSettings(context: Context) {

    private val SETTINGS_NAME = "weather_app_preferences"
    private val LOCATIONS_LIST = "locations_key"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveLocations(locations: MutableList<String>) {
        val jsonString = gson.toJson(locations)
        sharedPreferences.edit().putString(LOCATIONS_LIST, jsonString).apply()
    }

    fun getLocationList(): List<String> {
        val jsonString = sharedPreferences.getString(LOCATIONS_LIST, null)
        return if (!jsonString.isNullOrEmpty()) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }
}

