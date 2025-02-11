package obj.study

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import obj.study.ui.theme.Pink80
import obj.study.ui.theme.Purple40



class StartScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartScreen()
        }
    }
}


@Preview
@Composable
fun StartScreen(
    availableItems: List<String> = listOf(
        "Железногорск",
        "Москва",
        "Томск",
        "Tula"
    ),
) {

    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }

    val selectedItems = remember { mutableStateListOf<String>() }

    var filteredItems: List<String> = emptyList()

    if (searchText.isBlank()) {
        filteredItems = availableItems
    } else {
        filteredItems = availableItems.filter { it.contains(searchText, ignoreCase = true) }
    }


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
                .height(40.dp)
        )

        // Поле ввода для поиска
        OutlinedTextField(
            value = searchText,
            onValueChange = { text -> searchText = text },
            label = { Text("Поиск", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Purple40, shape = RoundedCornerShape(10.dp))
        )

        if (selectedItems.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (item in selectedItems) {
                        Card(
                            elevation = CardDefaults.cardElevation(7.dp)
                        ) {
                            Text(
                                fontSize = 30.sp,
                                color = Color.White,
                                text = item,
                                modifier = Modifier
                                    .background(color = Purple40, shape = RoundedCornerShape(10.dp))
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Прокручиваемый список
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
                        Checkbox(
                            modifier = Modifier
                                .background(color = Color.Transparent),
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    // Добавить в выбранные
                                    if (item !in selectedItems) {
                                        selectedItems.add(item)
                                    }
                                } else {
                                    // Убрать из выбранных
                                    selectedItems.remove(item)
                                }
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
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
            val intent = Intent(context, Main1::class.java)
            intent.putStringArrayListExtra("selectedItemsKey", ArrayList(selectedItems))
            context.startActivity(intent)
        }) {
            Text(text = "Открыть SecondActivity")
        }

    }
}