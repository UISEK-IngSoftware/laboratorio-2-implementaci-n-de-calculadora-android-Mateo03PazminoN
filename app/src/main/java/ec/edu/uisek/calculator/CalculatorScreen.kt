package ec.edu.uisek.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.calculator.ui.theme.Purple40
import ec.edu.uisek.calculator.ui.theme.UiSekBlue

val OrangeColor = Color(0xFFFF9800) // Un naranja bonito

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = state.display,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = 56.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        CalculatorGrid(onEvent = viewModel::onEvent)
    }
}

@Composable
fun CalculatorGrid(onEvent: (CalculatorEvent) -> Unit) {
    val buttons = listOf(
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "−",
        "0", ".", "=", "+"
    )

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(buttons.size) { index ->
            val label = buttons[index]

            // Definimos el color de fondo según si es número o no
            val isNumber = label in "0".."9"

            CalculatorButton(
                label = label,
                backgroundColor = if (isNumber) OrangeColor else Purple40,
                contentColor = Color.White
            ) {
                when (label) {
                    in "0".."9" -> onEvent(CalculatorEvent.Number(label))
                    "." -> onEvent(CalculatorEvent.Decimal)
                    "=" -> onEvent(CalculatorEvent.Calculate)
                    else -> onEvent(CalculatorEvent.Operator(label))
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            CalculatorButton(
                label = "AC",
                backgroundColor = Color.Red,
                contentColor = Color.White
            ) { onEvent(CalculatorEvent.AllClear) }
        }
        item {} // Espacio vacío
        item {
            CalculatorButton(
                label = "C",
                backgroundColor = Color.Red,
                contentColor = Color.White
            ) { onEvent(CalculatorEvent.Clear) }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    backgroundColor: Color = UiSekBlue,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(if (label == "AC") 2f else 1f)
            .fillMaxSize()
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = contentColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}
