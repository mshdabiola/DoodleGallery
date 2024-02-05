/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.theme.DoodleTheme

@Composable
fun CtaButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(imageVector = Icons.Default.Brush, contentDescription = "brush")
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Draw")
    }
}

@ThemePreviews
@Composable
fun ButtonPreview() {
    DoodleTheme {
        // DoodleBackground(modifier = Modifier.size(300.dp)) {
        CtaButton()
        // }
    }
}
