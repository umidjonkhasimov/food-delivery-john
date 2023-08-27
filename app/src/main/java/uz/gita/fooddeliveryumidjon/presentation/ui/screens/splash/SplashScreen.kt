package uz.gita.fooddeliveryumidjon.presentation.ui.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.splash.viewmodel.SplashScreenViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openSignInScreenLiveData.observe(this, openSignInScreenObserver)
        viewModel.openHomeScreenLiveData.observe(this, openHomeScreenObserver)
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_screenHome)
    }

    private val openSignInScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_signInScreen)
    }
}