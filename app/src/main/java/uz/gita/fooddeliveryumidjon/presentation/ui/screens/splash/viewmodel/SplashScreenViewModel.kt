package uz.gita.fooddeliveryumidjon.presentation.ui.screens.splash.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.fooddeliveryumidjon.data.local.MySharedPreferences

class SplashScreenViewModel : ViewModel() {
    val openHomeScreenLiveData = MutableLiveData<Unit>()
    val openSignInScreenLiveData = MutableLiveData<Unit>()
    private val myPrefs = MySharedPreferences.getInstance()
    private var handler = Handler(Looper.getMainLooper())

    init {
        if (myPrefs.isSignedIn) {
            handler.postDelayed(
                {
                    openHomeScreenLiveData.value = Unit
                }, 1000
            )
        } else {
            handler.postDelayed(
                {
                    openSignInScreenLiveData.value = Unit
                }, 1000
            )
        }
    }
}