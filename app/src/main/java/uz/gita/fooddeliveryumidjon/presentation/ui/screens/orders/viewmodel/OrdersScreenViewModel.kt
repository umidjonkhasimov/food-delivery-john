package uz.gita.fooddeliveryumidjon.presentation.ui.screens.orders.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddeliveryumidjon.domain.repository.impl.HomeRepositoryImpl
import uz.gita.fooddeliveryumidjon.model.BasketProductData

class OrdersScreenViewModel : ViewModel() {
    private val repository = HomeRepositoryImpl.getInstance()
    val allOrders = MutableLiveData<List<BasketProductData>>()
    val errorHandler = MutableLiveData<String>()
    val showProgressLD = MutableLiveData<Boolean>()

    init {
        showProgressLD.value = true
        repository.getAllOrders().onEach {
            it.onSuccess { list ->
                showProgressLD.value = false
                allOrders.value = list
            }

            it.onFailure {
                showProgressLD.value = false
                errorHandler.value = it.message
            }
        }.launchIn(viewModelScope)
    }
}