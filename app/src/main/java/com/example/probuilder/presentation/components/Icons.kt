package com.example.probuilder.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.probuilder.R.string as text
import com.example.probuilder.R.drawable as icons

object Icons {
    // Navigation
    val Menu: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.menu),
            contentDescription = stringResource(text.icon_menu)
        )
    val Search: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.search),
            contentDescription = stringResource(text.icon_search)
        )
    // More actions
    val MoreVert: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.more_vert),
            contentDescription = stringResource(text.icon_more_vert)
        )
    val Edit: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.more_vert),
            contentDescription = stringResource(text.icon_edit)
        )
    val SelectAll: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.select_all),
            contentDescription = stringResource(text.icon_select_all)
        )

    val Add: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.add),
            contentDescription = stringResource(text.icon_select_all)
        )
    val Delete: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.delete),
            contentDescription = stringResource(text.icon_delete)
        )

    // Input field
    val Clear: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.close),
            contentDescription = stringResource(text.icon_clear)
        )

    // Other
    val ArrowRight: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.arrow_right),
            contentDescription = stringResource(text.icon_arrow_right)
        )


}