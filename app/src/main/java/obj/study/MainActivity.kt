package obj.study


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    private lateinit var locationsSettings: LocationsSettings
    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            locationsSettings = LocationsSettings(context = this)
            var screen: String = "StartScreen"

            val navController = rememberNavController()



            if (locationsSettings.getLocationList().isNotEmpty()) {
                screen = "Main"
            }

            NavHost(navController = navController, startDestination = screen) {
                composable("StartScreen") { StartScreen(navController, locationsSettings) }
                composable("Main") { Main(mainViewModel, locationsSettings) }

            }
        }
    }
}

