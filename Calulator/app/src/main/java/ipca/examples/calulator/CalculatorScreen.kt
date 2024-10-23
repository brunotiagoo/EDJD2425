package ipca.examples.calulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import ipca.examples.calulator.ui.components.CalcButton
import ipca.examples.calulator.ui.theme.CalulatorTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var displayText by remember { mutableStateOf("0") }
    var operand by remember { mutableStateOf(0.0) }
    var operator by remember { mutableStateOf("") }
    var userIsInTheMiddleOfIntroduction by remember {
        mutableStateOf(true)
    }

    fun getDisplay(): Double = displayText.toDouble()

    fun setDisplay(value: Double) {
        displayText = if (value % 1 == 0.0) value.toInt().toString() else value.toString()
    }

    val clearDisplay: () -> Unit = {
        displayText = "0"
        operand = 0.0
        operator = ""
        userIsInTheMiddleOfIntroduction = true
    }

    val onNumPressed: (String) -> Unit = { num ->
        if (userIsInTheMiddleOfIntroduction) {
            if (displayText == "0" && num != ".") {
                displayText = num
            } else {
                if (!displayText.contains('.') || num != ".") {
                    displayText += num
                }
            }
        } else {
            displayText = num
            userIsInTheMiddleOfIntroduction = true
        }
    }

    val onOperatorPressed: (String) -> Unit = { op ->
        when (op) {
            "C" -> clearDisplay()
            "=" -> {
                if (operator.isNotEmpty()) {
                    when (operator) {
                        "+" -> operand += getDisplay()
                        "-" -> operand -= getDisplay()
                        "*" -> operand *= getDisplay()
                        "/" -> operand /= getDisplay()
                    }
                    setDisplay(operand)
                    operator = ""
                }
            }
            else -> {
                if (operator.isNotEmpty() && !userIsInTheMiddleOfIntroduction) {
                    when (operator) {
                        "+" -> operand += getDisplay()
                        "-" -> operand -= getDisplay()
                        "*" -> operand *= getDisplay()
                        "/" -> operand = if (getDisplay() != 0.0) operand / getDisplay() else Double.NaN  // Handle division by zero
                    }
                    setDisplay(operand)
                }
                operand = getDisplay()
                operator = op
                userIsInTheMiddleOfIntroduction = false
            }
        }
    }

    CalulatorTheme {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right,
                text = displayText,
                style = MaterialTheme.typography.displayLarge
            )
            // Define the layout for buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                CalcButton(modifier = Modifier.weight(1f), label = "C", onClick = { _ -> onOperatorPressed("C") })
                CalcButton(modifier = Modifier.weight(1f), label = "/", isOperation = true, onClick = { onOperatorPressed("/") })
                CalcButton(modifier = Modifier.weight(1f), label = "*", isOperation = true, onClick = { onOperatorPressed("*") })
                CalcButton(modifier = Modifier.weight(1f), label = "-", isOperation = true, onClick = { onOperatorPressed("-") })
            }
            // Additional rows of buttons for numbers and operations
            Row(modifier = Modifier.fillMaxWidth()) {
                CalcButton(modifier = Modifier.weight(1f), label = "7", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "8", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "9", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "+", isOperation = true, onClick = { onOperatorPressed("+") })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalcButton(modifier = Modifier.weight(1f), label = "4", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "5", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "6", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "=", isOperation = true, onClick = { onOperatorPressed("=") })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalcButton(modifier = Modifier.weight(1f), label = "1", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "2", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "3", onClick = onNumPressed)
                CalcButton(modifier = Modifier.weight(1f), label = "0", onClick = onNumPressed)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalulatorTheme {
        CalculatorScreen()
    }
}
