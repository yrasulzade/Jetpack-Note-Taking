package com.example.jetpacknotetaking.ui_kit

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpacknotetaking.ui.theme.Purple40

@Composable
fun RoundedButton(
    modifier: Modifier,
    buttonColor: Color = Purple40,
    text: String,
    fontSize: TextUnit = 18.sp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(text = text, fontSize = fontSize)
    }
}
