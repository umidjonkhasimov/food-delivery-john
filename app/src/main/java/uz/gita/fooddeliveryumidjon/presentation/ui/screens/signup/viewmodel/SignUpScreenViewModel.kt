package uz.gita.fooddeliveryumidjon.presentation.ui.screens.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddeliveryumidjon.domain.repository.impl.AuthRepositoryImpl

class SignUpScreenViewModel : ViewModel() {
    val showProgressBarLD = MutableLiveData<Boolean>(false)
    val btnStateEnabledLD = MutableLiveData<Boolean>(false)
    val openHomeScreenLD = MutableLiveData<Unit>()
    val showErrorLD = MutableLiveData<String>()
    private val repository = AuthRepositoryImpl.getInstance()

    fun textChanged(name: String, email: String, password: String) {
        btnStateEnabledLD.value = name.trim().isNotBlank() && email.endsWith("@gmail.com") && password.length >= 7
    }

    fun signUp(name: String, email: String, password: String) {
        showProgressBarLD.value = true
        repository.signUp(name, email, password).onEach {
            it.onSuccess {
                showProgressBarLD.value = false
                openHomeScreenLD.value = Unit
            }
            it.onFailure {
                showProgressBarLD.value = false
                showErrorLD.value = it.message
            }
        }.launchIn(viewModelScope)
    }

}