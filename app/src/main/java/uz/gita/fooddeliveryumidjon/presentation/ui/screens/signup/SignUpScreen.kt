package uz.gita.fooddeliveryumidjon.presentation.ui.screens.signup

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
import uz.gita.fooddeliveryumidjon.databinding.ScreenSignUpBinding
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.signup.viewmodel.SignUpScreenViewModel

class SignUpScreen : Fragment(R.layout.screen_sign_up) {
    private val binding by viewBinding(ScreenSignUpBinding::bind)
    private val viewModel by viewModels<SignUpScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openHomeScreenLD.observe(this, openHomeScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()

        binding.apply {
            etName.doAfterTextChanged { name ->
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.textChanged(name.toString(), email, password)
            }
            etEmail.doAfterTextChanged { email ->
                val name = etName.text.toString()
                val password = etPassword.text.toString()
                viewModel.textChanged(name, email.toString(), password)
            }
            etPassword.doAfterTextChanged { password ->
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                viewModel.textChanged(name, email, password.toString())
            }
        }

        binding.apply {
            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.signUp(name, email, password)
            }
        }
    }

    private fun attachObservers() {
        viewModel.btnStateEnabledLD.observe(viewLifecycleOwner, btnStateEnabledObserver)
        viewModel.showErrorLD.observe(viewLifecycleOwner, showErrorObserver)
        viewModel.showProgressBarLD.observe(viewLifecycleOwner, showProgressBarObserver)
    }

    private val btnStateEnabledObserver = Observer<Boolean> {
        binding.btnSignUp.isEnabled = it
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signUpScreen_to_screenHome)
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