package uz.gita.fooddeliveryumidjon.presentation.ui.screens.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.fooddeliveryumidjon.data.local.MySharedPreferences
import uz.gita.fooddeliveryumidjon.domain.repository.impl.AuthRepositoryImpl

class MainScreenViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl.getInstance()
    private val myPrefs = MySharedPreferences.getInstance()
    val openSignInScreenLD = MutableLiveData<Unit>()
}