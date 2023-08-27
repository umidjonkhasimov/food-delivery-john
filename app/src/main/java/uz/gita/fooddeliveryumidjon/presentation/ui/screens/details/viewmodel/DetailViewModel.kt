package uz.gita.fooddeliveryumidjon.presentation.ui.screens.details.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddeliveryumidjon.domain.repository.impl.HomeRepositoryImpl
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.model.ProductData

class DetailViewModel : ViewModel() {
    private val repository = HomeRepositoryImpl.getInstance()

    var quantity = MutableLiveData(1)
    var showProgressLiveData = MutableLiveData<Boolean>()
    var isProductInTheBasketLD = MutableLiveData<BasketProductData?>()

    fun incrementQuantity() {
        quantity.value = quantity.value!! + 1
    }

    fun decrementQuantity() {
        quantity.value = if (quantity.value!! > 1) quantity.value!! - 1 else 1
    }

    fun addProductToBasket(productData: ProductData) {
        showProgressLiveData.value = true
        repository.addProductToBasket(
            BasketProductData(
                id = productData.id,
                productId = productData.id,
                quantity = quantity.value!!,
                productTitle = productData.title,
                imageUrl = productData.imageUrl,
                price = productData.price.toInt()
            )
        ).onEach {
            it.onSuccess {
                showProgressLiveData.value = false
            }
            it.onFailure {
                showProgressLiveData.value = false
            }
        }.launchIn(viewModelScope)
    }

    fun isProductInTheBasket(productData: ProductData) {
        repository.isProductInTheBasket(productData.id).onEach {
            it.onSuccess { data ->
                isProductInTheBasketLD.value = data
            }

            it.onFailure {

            }
        }.launchIn(viewModelScope)
    }
}