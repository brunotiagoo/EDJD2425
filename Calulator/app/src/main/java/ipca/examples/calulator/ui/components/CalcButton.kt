package ipca.examples.calulator.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import ipca.examples.calulator.ui.theme.CalulatorTheme
import ipca.examples.calulator.ui.theme.Teal700
import ipca.examples.calulator.ui.theme.Cyan300

@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    label: String,
    isOperation: Boolean = false,
    onClick: (String) -> Unit
) {
    Button(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .then(
                if (isOperation) Modifier else Modifier.clip(RoundedCornerShape(50.dp)) // Corrigido com a importação de clip
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isOperation) Teal700 else Cyan300
        ),
        onClick = { onClick(label) }
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CalcButtonPreview() {
    CalulatorTheme {
        CalcButton(label = "7", onClick = {})
    }
}
