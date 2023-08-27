package uz.gita.fooddeliveryumidjon.presentation.ui.screens.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ScreenSignInBinding
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.signin.viewmodel.SignInScreenViewModel

class SignInScreen : Fragment(R.layout.screen_sign_in) {
    private val binding by viewBinding(ScreenSignInBinding::bind)
    private val viewModel: SignInScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openSignUpScreenLD.observe(this, openSignUpScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()

        binding.apply {
            etEmail.doAfterTextChanged { email ->
                val password = etPassword.text.toString()
                viewModel.textChanged(email.toString(), password)
            }
            etPassword.doAfterTextChanged { password ->
                val email = etEmail.text.toString()
                viewModel.textChanged(email, password.toString())
            }
        }

        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.signIn(email, password)
            }
        }

        binding.apply {
            btnCreateAcc.setOnClickListener {
                viewModel.openSignUpScreen()
            }
        }
    }

    private fun attachObservers() {
        viewModel.showProgressBarLD.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModel.btnStateEnabledLD.observe(viewLifecycleOwner, btnStateEnabledObserver)
        viewModel.showErrorLD.observe(viewLifecycleOwner, showErrorObserver)
        viewModel.openHomeScreenLD.observe(viewLifecycleOwner, openHomeScreenObserver)
    }

    private val btnStateEnabledObserver = Observer<Boolean> {
        binding.btnLogin.isEnabled = it
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signInScreen_to_screenHome)
    }

    private val openSignUpScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signInScreen_to_signUpScreen)
    }

    private val showErrorObserver = Observer<String> { errorMessage ->
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private val showProgressBarObserver = Observer<Boolean> {
        if (it) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.INVISIBLE
        }
    }
}