/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.item.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            onItemClick = navigateToItemUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
            viewModel = viewModel
        )
    }
}

@Composable
private fun HomeBody(
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: HomeViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val items by viewModel.filteredItems.collectAsState(initial = emptyList())
    val minPrice by viewModel._minPrice.collectAsState()
    val maxPrice by viewModel._maxPrice.collectAsState()
    val minStock by viewModel._minStock.collectAsState()
    val maxStock by viewModel._maxStock.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(contentPadding),
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.setSearchQuery(it) // Update ViewModel
            },
            label = { Text(stringResource(R.string.search_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        )

        Row {
            OutlinedTextField(
                value = minPrice?.toString() ?: "",
                onValueChange = { viewModel.setMinPrice(it.toDoubleOrNull()) },
                label = { Text(stringResource(R.string.min_price)) },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = maxPrice?.toString() ?: "",
                onValueChange = { viewModel.setMaxPrice(it.toDoubleOrNull()) },
                label = { Text(stringResource(R.string.max_price)) },
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            OutlinedTextField(
                value = minStock?.toString() ?: "",
                onValueChange = { viewModel.setMinStock(it.toIntOrNull()) },
                label = { Text(stringResource(R.string.min_stock)) },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = maxStock?.toString() ?: "",
                onValueChange = { viewModel.setMaxStock(it.toIntOrNull()) },
                label = { Text(stringResource(R.string.max_stock)) },
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = { viewModel.clearFilters() },  // Calls the function
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.item_entry_title)
            )
            Text("Clear Filters")
        }

        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                itemList = items,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}


@Composable
private fun InventoryList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun InventoryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = stringResource(R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        HomeBody(onItemClick = {}
        , viewModel = viewModel(factory = AppViewModelProvider.Factory))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    InventoryTheme {
        HomeBody( onItemClick = {}, viewModel = viewModel(factory = AppViewModelProvider.Factory))
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        InventoryItem(
            Item(1, "Game", 100.0, 20),
        )
    }
}
