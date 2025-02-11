package obj.study

import Hour
import ParsingData
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import obj.study.ui.theme.Pink80
import obj.study.ui.theme.Purple20
import obj.study.ui.theme.Purple40


class Main1 : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main(mainViewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Main(viewModel: MainViewModel) {
    val context = LocalContext.current
    val activity = context as? Main1

    val locations = activity?.intent?.getStringArrayListExtra("selectedItemsKey") ?: arrayListOf()

    var loc by remember { mutableStateOf(0) }

    viewModel.getApiData(locations[loc])

    val state = viewModel.temp.collectAsState()

    val pages = remember {  }

    val pagerState = rememberPagerState {locations.size}
    if (state.value != ParsingData()) {
        HorizontalPager(pagerState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = listOf(Purple40, Pink80))),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )

                Text(
                    text = state.value.address,
                    fontSize = 30.sp,
                    color = Color.White,
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )

                Text(
                    text = "${state.value.days.get(0).temp}°",
                    fontSize = 150.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 37.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "${state.value.days.get(0).tempmin}°",
                            fontSize = 40.sp,
                            color = Color.White
                        )
                        Text(text = "min", fontSize = 20.sp, color = Color.White)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "${state.value.days.get(0).tempmax}°",
                            fontSize = 40.sp,
                            color = Color.White
                        )
                        Text(text = "max", fontSize = 20.sp, color = Color.White)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    val hours: List<Hour> = state.value.days.get(0).hours


                    itemsIndexed(hours) { time, hour ->
                        Card(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(100.dp),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Purple20),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "$time:00", fontSize = 20.sp, color = Color.White)
                                    Spacer(Modifier.size(10.dp))
                                    Text(
                                        text = "${hour.temp}°",
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}