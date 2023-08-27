package uz.gita.fooddeliveryumidjon.presentation.ui.screens.basket.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddeliveryumidjon.domain.repository.HomeRepository
import uz.gita.fooddeliveryumidjon.domain.repository.impl.HomeRepositoryImpl
import uz.gita.fooddeliveryumidjon.model.BasketProductData

class BasketViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepositoryImpl.getInstance()
    val allBasketProductsLiveData = MutableLiveData<List<BasketProductData>>()
    val errorHandlerLiveData = MutableLiveData<String>()
    val showProgressLiveData = MutableLiveData<Boolean>()

    init {
        getAllBasketProducts()
    }

    fun incrementQuantityOfBasketProduct(basketProductData: BasketProductData) {
        showProgressLiveData.value = true
        repository.incrementQuantityOfBasketProduct(basketProductData).onEach {
            it.onSuccess {
                getAllBasketProducts()
            }

            it.onFailure {
                getAllBasketProducts()
            }
        }.launchIn(viewModelScope)
    }

    fun decrementQuantityOfBasketProduct(basketProductData: BasketProductData) {
        showProgressLiveData.value = true
        repository.decrementQuantityOfBasketProduct(basketProductData).onEach {
            it.onSuccess {
                showProgressLiveData.value = false
                getAllBasketProducts()
            }

            it.onFailure {
                showProgressLiveData.value = false
                getAllBasketProducts()
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllBasketProducts() {
        showProgressLiveData.value = true
        repository.getAllBasketProducts().onEach {
            it.onSuccess { list ->
                showProgressLiveData.value = false
                allBasketProductsLiveData.value = list
            }

            it.onFailure {
                showProgressLiveData.value = false
                errorHandlerLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }

    fun removeProductFromBasket(basketProductData: BasketProductData) {
        showProgressLiveData.value = true
        repository.removeProductFromBasket(basketProductData).onEach {
            it.onSuccess {
                showProgressLiveData.value = false
                getAllBasketProducts()
            }

            it.onFailure {
                showProgressLiveData.value = false
                getAllBasketProducts()
            }
        }.launchIn(viewModelScope)
    }

    fun placeOrders() {
        repository.placeOrders().onEach {
            it.onSuccess {
                getAllBasketProducts()
            }

            it.onFailure {
                errorHandlerLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }
}