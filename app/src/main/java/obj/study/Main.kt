package obj.study

import Hour
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import obj.study.ui.theme.Pink80
import obj.study.ui.theme.Purple20
import obj.study.ui.theme.Purple40
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Main(viewModel: MainViewModel, weatherDao: WeatherDao) {
    val state = viewModel.temp.collectAsState()

    CoroutineScope(Dispatchers.IO).launch {
        viewModel.getApiDatas(weatherDao.getAll())
    }

    if (state.value.isNotEmpty()) {
        val pagerState = rememberPagerState { state.value.size }
        HorizontalPager(pagerState) { index ->
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
                        .height(24.dp)
                )

                Text(
                    text = state.value[index].address,
                    fontSize = 25.sp,
                    color = Color.White,
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )

                Text(
                    text = "${state.value[index].days.get(0).temp.toInt()}°",
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
                            text = "${state.value[index].days.get(0).tempmin}°",
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
                            text = "${state.value[index].days.get(0).tempmax}°",
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


                    val hours: List<Hour> = state.value[index].days.get(0).hours


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

                Card(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = Purple20)
                            .fillMaxSize(),

                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        itemsIndexed(state.value[index].days) { fs, day ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                var topPadding = 0
                                if (fs == 0){
                                    topPadding = 10
                                }
                                Row(verticalAlignment = Alignment.Bottom) {
                                    if (fs == 0) {
                                        Text(
                                            text = "сегодня",
                                            fontSize = 25.sp,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(start = 15.dp, top = topPadding.dp)
                                        )
                                    } else {
                                        Text(
                                            text = LocalDate.now()
                                                .plusDays(fs.toLong()).dayOfWeek.getDisplayName(
                                                    TextStyle.FULL,
                                                    Locale("ru"),
                                                ),
                                            modifier = Modifier
                                                .padding(start = 15.dp),
                                            fontSize = 25.sp,
                                            color = Color.White

                                        )
                                    }

                                    Spacer(modifier = Modifier.width(15.dp))

                                    val dayNum = day.datetime.split("-")[2]
                                    val monthNum = day.datetime.split("-")[1]
                                    Text(
                                        text = "$dayNum.$monthNum",
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(top = topPadding.dp, bottom = 1.dp)
                                    )
                                }
                                Text(
                                    text = "${day.temp}°",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    modifier = Modifier
                                        .padding(end = 15.dp, top = topPadding.dp)

                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .padding(start = 30.dp, end = 30.dp, top = 10.dp)
                            )
                        }
                    }
                }


            }

        }
    }
}
