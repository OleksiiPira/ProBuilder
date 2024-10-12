package com.example.probuilder.presentation.screen.invoices.add_to_invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants.HORIZONTAL_PADDING
import com.example.probuilder.domain.model.ButtonCfg
import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.domain.model.SearchItem
import com.example.probuilder.presentation.screen.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToInvoiceScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: InvoiceViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val item by viewModel.invoiceItemState.collectAsState()
    var contentHeight by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()

    val invoices by viewModel.invoices.collectAsState(initial = emptyList())
    val searchableItems =
        listOf(invoices, invoices, invoices, invoices, invoices, invoices).flatMap { it.toList() }
            .filter { it.name.contains(item.invoice.name, true) }
            .map { SearchItem(it.name, it) }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Main manu"
                        )
                    }
                },
                title = { Text(text = "Категорії") },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Search"
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(HORIZONTAL_PADDING)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .onGloballyPositioned {
                        if (contentHeight == 0) contentHeight = it.size.height
                    },
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Text(
                        text = "Внести послугу в фактуру",
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                        text = "Назва роботи",
                        style = Typography.titleMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = item.name,
                        onValueChange = {
                            viewModel.onEvent(AddItemInvoiceEvent.SetName(it))
                        },
                        shape = ButtonCfg.RoundedShape,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        },
                    )
                }
                item {
                    var isOverlayVisible by rememberSaveable { mutableStateOf(false) }
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                            text = "Ціна за одиницю",
                            style = Typography.titleMedium

                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(3f),
                                shape = ButtonCfg.RoundedShape,
                                value = item.pricePerUnit.toDoubleOrNull().toString(),
                                onValueChange = {
                                },
                                suffix = { Text("грн") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )
                            TextButton(
                                modifier = Modifier.weight(1f),
                                onClick = { isOverlayVisible = !isOverlayVisible }
                            ) {
                                Text(
                                    text = item.unit,
                                    style = Typography.titleMedium
                                )
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(14.dp)
//                ) {
//                    Column(Modifier.weight(2f)) {
//                        Text(
//                            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
//                            text = "Ціна за одиницю",
//                            style = Typography.titleMedium
//
//                        )
//                        OutlinedTextField(
//                            shape = ButtonCfg.RoundedShape,
//                            value = item.pricePerUnit.toDoubleOrNull().toString(),
//                            onValueChange = {
//                                viewModel.onEvent(
//                                    AddItemInvoiceEvent.SetPricePerUnit(
//                                        it.toDoubleOrNull() ?: 0.0
//                                    )
//                                )
//                            },
//                            suffix = { Text("грн") },
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
//                        )
//                    }
//
//                    Column(modifier.weight(1f)) {
//                        Button(
//                            modifier = Modifier.fillMaxHeight(),
//                            onClick = { isOverlayVisible = !isOverlayVisible }
//                        ) {
//                            Text(text = item.unit)
//                            Icon(
//                                imageVector = Icons.Outlined.ArrowDropDown,
//                                contentDescription = null
//                            )
//                        }
//
////                        Text(
////                            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
////                            text = "Вимір",
////                            style = Typography.titleMedium
////                        )
////                        OutlinedTextField(
////                            modifier = Modifier
////                                .onFocusChanged { focusState ->
////                                    isOverlayVisible = focusState.hasFocus
////                                },
////                            shape = ButtonCfg.RoundedShape,
////                            value = item.unit,
////                            onValueChange = { viewModel.onEvent(AddItemInvoiceEvent.SetUnit(it)) },
////                            trailingIcon = {
////                                Icon(
////                                    imageVector = Icons.Outlined.ArrowDropDown,
////                                    contentDescription = null
////                                )
////                            }
////                        )
//                    }
//                }
                    if (isOverlayVisible) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Black.copy(alpha = 0.5f))
//                            .padding(16.dp)
//                    ) {
//                        Text("Overlay Content", color = Color.White)
//
//                        Button(
//                            onClick = { isOverlayVisible = false }
//                        ) {
//                            Text("Hide Overlay")
//                        }
//                    }
//
//                    LaunchedEffect(Unit) {
//                        coroutineScope.launch {
//                            scrollState.scrollToItem(1)
//                        }
//                    }
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                        text = "Кількість",
                        style = Typography.titleMedium
                    )
                    OutlinedTextField(
                        shape = ButtonCfg.RoundedShape,
                        value = if (item.quantity.toIntOrNull() != null && item.quantity.toDouble() > 0) item.quantity else "",
                        onValueChange = {
                            viewModel.onEvent(
                                AddItemInvoiceEvent.SetQuantity(
                                    it.toIntOrNull() ?: 0
                                )
                            )
                        },
                        suffix = { Text(item.unit) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                item {
                    Text(
                        modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                        text = "Рахунок-фактура",
                        style = Typography.titleMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { expanded = it.hasFocus },
                        shape = ButtonCfg.RoundedShape,
                        value = item.invoice.name,
                        onValueChange = {
                            viewModel.onEvent(
                                AddItemInvoiceEvent.SetInvoice(
                                    Invoice(
                                        name = it
                                    )
                                )
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { expanded = !expanded }) {
                                Icon(
                                    if (!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                }
                if (expanded) {
                    items(searchableItems) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { viewModel.onEvent(AddItemInvoiceEvent.SetInvoice(it.item as Invoice)) }
                        ) {
                            Text(it.name)
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        )

                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                scrollState.scrollToItem(3)
                            }
                        }
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { viewModel.onEvent(AddItemInvoiceEvent.SaveItem) }
                        ) {
                            Text("Внести в фактуру")
                        }
                    }
                    item { Spacer(modifier = Modifier.imePadding()) }
                }

            }
        }
    }
}

@Composable
fun Searching(
    item: InvoiceItemState,
    viewModel: InvoiceViewModel,
    searchableItems: List<SearchItem>,
    coroutineScope: CoroutineScope,
    scrollState: LazyListState
) {
    var expanded by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { expanded = it.hasFocus },
        value = item.invoice.name,
        onValueChange = { viewModel.onEvent(AddItemInvoiceEvent.SetInvoice(Invoice(name = it))) },
        label = { Text("Рахунок-фактура") },
        trailingIcon = {
            IconButton(
                onClick = { expanded = !expanded }) {
                Icon(
                    if (!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp,
                    contentDescription = "Localized description"
                )
            }
        }
    )
    if (expanded) {
        var columnHeight by remember { mutableStateOf(0) }
        Box(Modifier.onGloballyPositioned { columnHeight = it.size.height }) {
            Column() {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { TODO() }
                ) {
                    Text("Створити фактуру")
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
                (0..10).forEach { _ ->
                    searchableItems
                        .filter { it.name.contains(item.invoice.name, true) }
                        .map { SearchItem(it.name, it) }
                        .forEach {
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { viewModel.onEvent(AddItemInvoiceEvent.SetInvoice(it.item as Invoice)) }
                            ) {
                                Text(it.name)
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                }
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scrollState.scrollToItem(3)
            }
        }
    }
}


@Preview
@Composable
fun AddToPrev() {

}