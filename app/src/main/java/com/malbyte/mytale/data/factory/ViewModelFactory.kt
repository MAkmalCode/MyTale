package com.malbyte.mytale.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.di.Injection
import com.malbyte.mytale.ui.detail.DetailViewModel
import com.malbyte.mytale.ui.home.HomeVIewModel
import com.malbyte.mytale.ui.login.LoginViewModel
import com.malbyte.mytale.ui.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val api: APIInterface
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(api) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(api) as T
            modelClass.isAssignableFrom(HomeVIewModel::class.java) -> HomeVIewModel(api) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(api) as T
            else -> throw Throwable("Unknown ViewModel Class" + modelClass.name)
        }


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelFactory(Injection.provideApiService())
                .also { INSTANCE = it }
        }
    }
}