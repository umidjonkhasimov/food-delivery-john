package uz.gita.fooddeliveryumidjon.presentation.ui.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ScreenMainBinding
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.basket.BasketScreen
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.home.HomeScreen
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.main.viewmodel.MainScreenViewModel
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.orders.OrdersScreen
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.profile.ProfileScreen
import uz.gita.fooddeliveryumidjon.utils.replaceFragment

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel by viewModels<MainScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openSignInScreenLD.observe(viewLifecycleOwner, openSignInScreenObserver)
        replaceFragment(HomeScreen(), true)

        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        replaceFragment(HomeScreen(), true)
                    }

                    R.id.cart -> {
                        replaceFragment(BasketScreen(), true)
                    }

                    R.id.my_orders -> {
                        replaceFragment(OrdersScreen(), true)
                    }

                    R.id.profile -> {
                        replaceFragment(ProfileScreen(), true)
                    }
                }

                return@setOnItemSelectedListener true
            }
        }
    }

    private val openSignInScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_screenHome_to_signInScreen)
    }
}