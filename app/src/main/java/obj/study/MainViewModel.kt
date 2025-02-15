package obj.study

import ParsingData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private val _temp = MutableStateFlow<MutableList<ParsingData>>(mutableListOf())
    val temp : StateFlow<MutableList<ParsingData>> = _temp.asStateFlow()

    fun getApiDatas(locations : List<String>){
        viewModelScope.launch(Dispatchers.IO) {
            val result : MutableList<ParsingData> = mutableListOf()
            for (location in locations){
                result.add(MyApi.WeatherApi.getWeather(location))
            }
            _temp.update { result }
        }
    }
}