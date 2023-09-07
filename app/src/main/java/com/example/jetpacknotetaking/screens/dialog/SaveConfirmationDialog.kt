package com.example.jetpacknotetaking.screens.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jetpacknotetaking.R
import com.example.jetpacknotetaking.ui.theme.Color25
import com.example.jetpacknotetaking.ui.theme.Green
import com.example.jetpacknotetaking.ui_kit.RoundedButton

@Composable
fun SaveConfirmationDialog(
    onSave: () -> Unit,
    onDiscard: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(Color(0xFFCCCCCC))
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color25)
                    .padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_info_black),
                    contentDescription = stringResource(id = R.string.cd_info),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    alignment = Alignment.Center
                )
                Text(
                    text = stringResource(id = R.string.save_changes),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 12.dp)
                )

                ButtonRow(onSave = onSave, onDiscard = onDiscard)
            }
        }
    }
}

@Composable
fun ButtonRow(
    onSave: () -> Unit,
    onDiscard: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val buttonModifier = Modifier
            .width(120.dp)
            .height(40.dp)

        RoundedButton(
            onClick = onDiscard,
            modifier = buttonModifier,
            text = stringResource(id = R.string.discard),
            buttonColor = Color.Red
        )
        RoundedButton(
            onClick = onSave,
            modifier = buttonModifier,
            text = stringResource(id = R.string.save),
            buttonColor = Green
        )
    }
}
