package obj.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import obj.study.ui.theme.Pink80
import obj.study.ui.theme.Purple40


@Composable
fun StartScreen(
    navController: NavController,
    locationsSettings: LocationsSettings,
) {
    val availableItems: List<String> = listOf(
        "Железногорск", "Москва", "Томск", "Кемерово", "Новосибирск"
    )

    var searchText by remember { mutableStateOf("") }

    val selectedItems = remember { mutableStateListOf<String>() }

    var filteredItems: List<String> = emptyList()

    if (searchText.isBlank()) {
        filteredItems = availableItems
    } else {
        filteredItems = availableItems.filter {
            (it.contains(
                searchText, ignoreCase = true
            ) or (it in selectedItems))
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Purple40, Pink80))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )

        OutlinedTextField(value = searchText,
            onValueChange = { text -> searchText = text },
            label = { Text("Поиск", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Purple40, shape = RoundedCornerShape(10.dp))
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(top = 16.dp)
        ) {
            items(filteredItems) { item ->
                val isSelected = item in selectedItems
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(10.dp),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Purple40),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(modifier = Modifier.background(color = Color.Transparent),
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    if (item !in selectedItems) {
                                        selectedItems.add(item)
                                    }
                                } else {

                                    selectedItems.remove(item)
                                }
                            })
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                color = Color.White,
                                fontSize = 25.sp,
                            )
                        }
                    }
                }

            }
        }


        Button(onClick = {
            locationsSettings.saveLocations(selectedItems)
            navController.navigate("Main")
        }) {
            Text(text = "Выбрать", color = Color.White)
        }
    }
}