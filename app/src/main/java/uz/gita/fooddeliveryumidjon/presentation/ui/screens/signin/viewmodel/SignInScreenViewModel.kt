package uz.gita.fooddeliveryumidjon.presentation.ui.screens.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddeliveryumidjon.domain.repository.impl.AuthRepositoryImpl

class SignInScreenViewModel : ViewModel() {
    val showProgressBarLD = MutableLiveData<Boolean>(false)
    val btnStateEnabledLD = MutableLiveData<Boolean>(false)
    val openHomeScreenLD = MutableLiveData<Unit>()
    val openSignUpScreenLD = MutableLiveData<Unit>()
    val showErrorLD = MutableLiveData<String>()
    private val repository = AuthRepositoryImpl.getInstance()

    fun signIn(email: String, password: String) {
        showProgressBarLD.value = true
        repository.signIn(email, password).onEach {
            it.onSuccess {
                openHomeScreenLD.value = Unit
                showProgressBarLD.value = false
            }
            it.onFailure { error ->
                showProgressBarLD.value = false
                showErrorLD.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    fun textChanged(email: String, password: String) {
        btnStateEnabledLD.value = email.trim().endsWith("@gmail.com", true) && password.length >= 7
    }

    fun openSignUpScreen() {
        openSignUpScreenLD.value = Unit
    }
}
