package com.example.probuilder.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
    val ArrowBack: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.arrow_back),
            contentDescription = stringResource(text.icon_back)
        )
    val Search: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.search),
            contentDescription = stringResource(text.icon_search),
            tint = Color(0xFFEEEEF2)
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
    val Phone: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.phone),
            contentDescription = "Номер телефону"
        )
    val Email: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.email),
            contentDescription = "Електронна пошта іконка"
        )
    val ArrowRightLarge: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.arrow_right),
            contentDescription = stringResource(text.icon_arrow_right)
        )
    val ProfileSmall: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.profile_small),
            contentDescription = "Замовник"
        )
    val LocationSmall: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.location_small),
            contentDescription = "Локація"
        )

    //  Buttons
    val Check: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.check),
            contentDescription = stringResource(text.icon_check)
        )
    val ListDot: Unit
        @Composable get() = Icon(
            painter = painterResource(id = icons.list_dot),
            contentDescription = "List dot"
        )

}

// Bottom navigation
@Composable
fun HomeIcon(color: Color) {
    Icon(
        painter = painterResource(id = icons.home),
        contentDescription = stringResource(text.icon_home),
        tint = color
    )
}

@Composable
fun PricesIcon(color: Color) {
    Icon(
        painter = painterResource(id = icons.prices),
        contentDescription = stringResource(text.icon_prices),
        tint = color
    )
}

@Composable
fun ProjectsIcon(color: Color) {
    Icon(
        painter = painterResource(id = icons.projects),
        contentDescription = stringResource(text.icon_projects),
        tint = color
    )
}

@Composable
fun ProfileIcon(color: Color) {
    Icon(
        painter = painterResource(id = icons.profile),
        contentDescription = stringResource(text.icon_search),
        tint = color
    )
}
