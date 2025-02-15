package obj.study


import ParsingData
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val db = remember {
                Room.databaseBuilder(applicationContext, DataBase::class.java, "WeatherDataBase").build()
            }
            val weatherDao = db.WeatherDao()
            val navController = rememberNavController()
            var screen by remember { mutableStateOf<String?>(null) }
            var data by remember { mutableStateOf<List<Cities>>(emptyList()) }

            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch{
                    data = weatherDao.getAll()
                    if (data.isNotEmpty()) {
                        screen = "Main"
                    }
                    else{
                        screen = "StartScreen"
                    }
                }
            }

            if (screen != null) {
                NavHost(navController = navController, startDestination = screen ?: "StartScreen") {
                    composable("StartScreen") { StartScreen(navController, weatherDao) }
                    composable("Main") { Main(mainViewModel, weatherDao) }
                }
            }
        }
    }
}

