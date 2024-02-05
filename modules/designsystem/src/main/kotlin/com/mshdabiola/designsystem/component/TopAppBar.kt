/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoodleTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    isDark: Boolean = false,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
    ),
    onDarkToggle: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.headlineSmall,
            )
        },

        actions = {
            IconButton(
                modifier = Modifier.testTag("main:topbar:darkmode"),
                onClick = onDarkToggle,
            ) {
                if (isDark) {
                    Icon(
                        imageVector = Icons.Default.LightMode,
                        contentDescription = "light mode",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = "dark mode",
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier.testTag("main:topbar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun TopAppBarPreview() {
    DoodleTopAppBar(
        titleRes = android.R.string.untitled,
        isDark = false,
    )
}
