package obj.study

import ParsingData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(location : String = "London") : ViewModel() {

    private val _temp = MutableStateFlow<ParsingData>(ParsingData())
    val temp: StateFlow<ParsingData> = _temp

    init {
        getApiData(location)
    }

    fun getApiData(location: String) {
        viewModelScope.launch {
            val result = MyApi.WeatherApi.getWeather(location)
            _temp.emit(result)
        }
    }

}