package com.forthcode.customerapp.signup.presentation

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.forthcode.customerapp.R
import com.forthcode.customerapp.databinding.ActivitySignupBinding
import com.forthcode.customerapp.home.presentation.HomeActivity
import com.forthcode.customerapp.login.data.ApiResponse
import com.forthcode.customerapp.login.presentation.LoginActivity
import com.forthcode.customerapp.login.presentation.LoginViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class SignupActivity : AppCompatActivity(), LifecycleOwner {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SignupViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userMobile.observe(this, this::setMobile)
        viewModel.changeMobile.observe(this, this::changeMobile)
        viewModel.toast.observe(this, this::showToast)
        viewModel.pDialog.observe(this, this::showProgress)
        viewModel.onSignup.observe(this, this::observeSignUp)
        viewModel.getMobile()
        binding.buttonChangeNumber.setOnClickListener { viewModel.changeMobile() }
        binding.buttonProceed.setOnClickListener {
            viewModel.validate(
                binding.editTextFirstName.text.toString(), binding.editTextLastName.text.toString(),
                binding.editEmail.text.toString(), binding.editMobile.text.toString()
            )
        }

    }

    private fun observeSignUp(response: ApiResponse) {
        if (response.success) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun showProgress(msg: String) {
        if (msg.isNotEmpty()) {
            binding.constraintLayout.isVisible = false
            binding.buttonChangeNumber.isVisible = false
            binding.buttonProceed.isVisible = false
            binding.animationView.isVisible = true
            binding.animationView.setAnimation(R.raw.loading)
            binding.animationView.playAnimation()
            binding.loadingText.text = msg
            binding.loadingText.isVisible = true
        } else {
            binding.animationView.isVisible = false
            binding.loadingText.isVisible = false
            binding.constraintLayout.isVisible = true
            binding.buttonChangeNumber.isVisible = true
            binding.buttonProceed.isVisible = true
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private fun setMobile(mobile: String) {
        binding.editMobile.setText(mobile)
        binding.editMobile.isEnabled = false
    }

    private fun changeMobile(success: Boolean) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}