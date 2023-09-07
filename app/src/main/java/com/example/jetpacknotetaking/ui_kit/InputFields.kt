package com.example.jetpacknotetaking.ui_kit

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import com.example.jetpacknotetaking.ui.theme.Color25

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholderText: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    placeHolderTextColor: Color = Color.Gray,
    containerColor: Color = Color25,
    shape: RoundedCornerShape,
) {
    OutlinedTextField(
        value = value,
        textStyle = TextStyle(color = textColor, fontSize = fontSize),
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = containerColor,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        placeholder = {
            Text(
                placeholderText,
                style = TextStyle(color = placeHolderTextColor),
                fontSize = fontSize
            )
        },
        shape = shape,
        modifier = modifier,
    )
}
