package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve all items in the Room database and handle filtering.
 */
class HomeViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    private val _searchQuery = MutableStateFlow("")
    val _minPrice = MutableStateFlow<Double?>(null)
    val _maxPrice = MutableStateFlow<Double?>(null)
    val _minStock = MutableStateFlow<Int?>(null)
    val _maxStock = MutableStateFlow<Int?>(null)

    private val _filteredItems = MutableStateFlow<List<Item>>(emptyList())
    val filteredItems: StateFlow<List<Item>> = _filteredItems

    init {
        viewModelScope.launch {
            homeUiState.collect { updateFilteredItems(it.itemList) }
        }
    }

    private fun updateFilteredItems(itemList: List<Item>) {
        val query = _searchQuery.value
        val minP = _minPrice.value
        val maxP = _maxPrice.value
        val minS = _minStock.value
        val maxS = _maxStock.value

        _filteredItems.value = itemList.filter { item ->
            val matchesQuery = item.name.contains(query, ignoreCase = true)
            val matchesPrice = (minP == null || item.price >= minP) && (maxP == null || item.price <= maxP)
            val matchesStock = (minS == null || item.quantity >= minS) && (maxS == null || item.quantity <= maxS)
            matchesQuery && matchesPrice && matchesStock
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateFilteredItems(homeUiState.value.itemList)
    }

    fun setMinPrice(price: Double?) {
        _minPrice.value = price
        updateFilteredItems(homeUiState.value.itemList)
    }

    fun setMaxPrice(price: Double?) {
        _maxPrice.value = price
        updateFilteredItems(homeUiState.value.itemList)
    }

    fun setMinStock(stock: Int?) {
        _minStock.value = stock
        updateFilteredItems(homeUiState.value.itemList)
    }

    fun setMaxStock(stock: Int?) {
        _maxStock.value = stock
        updateFilteredItems(homeUiState.value.itemList)
    }

    fun clearFilters(){
        _minPrice.value = null
        _maxPrice.value = null
        _minStock.value = null
        _maxStock.value = null
        updateFilteredItems(homeUiState.value.itemList)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Item> = listOf())