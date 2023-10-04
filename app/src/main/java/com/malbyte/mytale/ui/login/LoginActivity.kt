package com.malbyte.mytale.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chibatching.kotpref.Kotpref
import com.malbyte.mytale.R
import com.malbyte.mytale.data.appPref.GlobalPref
import com.malbyte.mytale.data.factory.ViewModelFactory
import com.malbyte.mytale.databinding.ActivityLoginBinding
import com.malbyte.mytale.ui.home.HomeActivity
import com.malbyte.mytale.ui.login.state.LoginState
import com.malbyte.mytale.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by viewBinding()
    private val viewModel : LoginViewModel by viewModels{
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Kotpref.init(this)

        val email = binding.edLoginEmail
        val password = binding.edLoginPassword
        val loadingBar = binding.loadingBar

        loadingBar.isVisible = false

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            viewModel.login(
                email.text.toString(),
                password.text.toString()
            )
        }

        viewModel.LoginStatus.observe(this){
            when(it){
                is LoginState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    loadingBar.isVisible = false
                }
                is LoginState.Loading -> {
                    loadingBar.isVisible = true
                }
                is LoginState.Success -> {
                    loadingBar.isVisible = false
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
            }
        }
    }
}