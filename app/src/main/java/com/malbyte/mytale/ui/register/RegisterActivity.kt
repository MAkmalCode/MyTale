package com.malbyte.mytale.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.malbyte.mytale.R
import com.malbyte.mytale.data.factory.ViewModelFactory
import com.malbyte.mytale.databinding.ActivityRegisterBinding
import com.malbyte.mytale.ui.home.HomeActivity
import com.malbyte.mytale.ui.login.LoginActivity
import com.malbyte.mytale.ui.register.state.RegisterState

class RegisterActivity : AppCompatActivity() {
    private val binding : ActivityRegisterBinding by viewBinding()
    private val viewModel : RegisterViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = binding.edRegisterName
        val email = binding.edRegisterEmail
        val password = binding.edRegisterPassword
        binding.loadingBar.isVisible = false

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                name.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }

        viewModel.LoadingStatus.observe(this){ state ->
            when(state){
                is RegisterState.Error -> {
                    Toast.makeText(this@RegisterActivity, state.message, Toast.LENGTH_SHORT).show()
                    binding.loadingBar.isVisible = false
                }
                is RegisterState.Loading -> {
                    binding.loadingBar.isVisible = true
                }
                is RegisterState.Success -> {
                    binding.loadingBar.isVisible = false
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }
                null -> {
                }
            }
        }
    }
}