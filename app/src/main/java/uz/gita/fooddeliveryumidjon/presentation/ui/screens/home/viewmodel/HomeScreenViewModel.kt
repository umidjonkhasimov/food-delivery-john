package uz.gita.fooddeliveryumidjon.presentation.ui.screens.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.fooddeliveryumidjon.domain.repository.impl.HomeRepositoryImpl
import uz.gita.fooddeliveryumidjon.model.CategoryData
import uz.gita.fooddeliveryumidjon.model.ProductData

class HomeScreenViewModel : ViewModel() {
    private val repository = HomeRepositoryImpl.getInstance()
    val categoriesLD = MutableLiveData<List<CategoryData>>()
    val errorHandlerLD = MutableLiveData<String>()
    val showProgressBarLD = MutableLiveData<Boolean>()
    val searchProductsLD = MutableLiveData<List<ProductData>?>()
    private var searchJob: Job? = null

    fun getCategories() {
        showProgressBarLD.value = true
        repository.getAllData().onEach {
            it.onSuccess { categoryList ->
                showProgressBarLD.value = false
                categoriesLD.value = categoryList
            }
            it.onFailure { exception ->
                showProgressBarLD.value = false
                errorHandlerLD.value = exception.message
            }
        }.launchIn(viewModelScope)
    }

    fun getCategoriesById(categoryId: String) {
        showProgressBarLD.value = true
        repository.getAllProductsByCategory(categoryId).onEach {
            it.onSuccess { list ->
                showProgressBarLD.value = false
                categoriesLD.value = list
            }
            it.onFailure {
                showProgressBarLD.value = false

            }
        }.launchIn(viewModelScope)
    }

    fun searchForProduct(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            showProgressBarLD.value = true

            repository.searchForProducts(query).onEach {
                it.onSuccess { list ->
                    showProgressBarLD.value = false
                    searchProductsLD.value = list
                }

                it.onFailure { error ->
                    showProgressBarLD.value = true
                    errorHandlerLD.value = error.message
                }
            }.launchIn(this)
        }
    }
}