package com.forthcode.customerapp.login.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.forthcode.customerapp.databinding.ActivityLoginBinding
import com.forthcode.customerapp.home.presentation.HomeActivity
import com.forthcode.customerapp.signup.presentation.SignupActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LifecycleOwner {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loginResponse.observe(this, this::otpSentSuccess)
        viewModel.sendOtpFailedResponse.observe(this, this::otpSentFailure)
        viewModel.verifyOtpFailedResponse.observe(this, this::verifyOtpFailure)
        viewModel.newUserFound.observe(this, this::newUserFound)
        viewModel.userLoggedIn.observe(this, this::loggedIn)
        viewModel.changeMobile.observe(this, this::changeNumber)
        binding.sendOtp.setOnClickListener {
            if (viewModel.validate(binding.editTextPhone.text.toString())) {
                binding.label.text = "Sending otp..."
                binding.sendOtp.isVisible = false
                viewModel.sendOtp(binding.editTextPhone.text.toString())
            } else {
                binding.editTextPhone.error = "Invalid mobile number"
                showToast("Invalid mobile number")
            }
        }
        binding.validateOtp.setOnClickListener {
            if (binding.editTextOtp.text.toString().length > 3) {
                binding.label.text = "Verifying otp..."
                binding.validateOtp.isVisible = false
                viewModel.verifyOtp(binding.editTextOtp.text.toString())
            } else {
                binding.editTextOtp.error = "Invalid OTP"
                showToast("Invalid OTP")
            }
        }
        binding.buttonChangeNumber.setOnClickListener {
            viewModel.changeMobile()
        }
        viewModel.checkUser()

    }

    private fun changeNumber(res: Boolean) {
        binding.sendOtp.visibility = VISIBLE
        binding.editTextPhone.visibility = VISIBLE
        binding.validateOtp.visibility = GONE
        binding.editTextOtp.visibility = GONE
        binding.label.setText("We will send an otp to this number")
        binding.editTextPhone.setText("")
        binding.editTextOtp.setText("")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private fun otpSentSuccess(message: String) {
        binding.label.text = message
        binding.editTextPhone.visibility = GONE
        binding.editTextOtp.visibility = VISIBLE
        binding.sendOtp.visibility = GONE
        binding.validateOtp.visibility = VISIBLE
    }

    private fun otpSentFailure(error: String) {
        binding.label.text = error
        binding.sendOtp.visibility = VISIBLE
        showToast(error)
    }

    private fun verifyOtpFailure(error: String) {
        binding.label.text = error
        showToast(error)
        binding.validateOtp.visibility = VISIBLE
    }

    private fun newUserFound(mobile: String) {
        showToast("We need more details to continue. Please sign up")
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loggedIn(message: String) {
        if (message.isNotEmpty())
            showToast(message)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        viewModel.cancelJobs()
        super.onDestroy()
    }
}