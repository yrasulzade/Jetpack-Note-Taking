package com.example.jetpacknotetaking.ui_kit

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun RoundedIconButton(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onIconClicked: () -> Unit,
) {

    val alpha = if (isEnabled) 1f else 0.3f

    IconButton(
        onClick = { onIconClicked.invoke() },
        modifier = modifier.alpha(alpha),
        enabled = isEnabled
    ) {
        Icon(
            tint = Color.White,
            painter = painterResource(id = drawable),
            contentDescription = null
        )
    }
}
