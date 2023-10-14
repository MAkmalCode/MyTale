package com.malbyte.mytale.data.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.di.Injection
import com.malbyte.mytale.ui.add_story.AddStoryViewModel
import com.malbyte.mytale.ui.detail.DetailViewModel
import com.malbyte.mytale.ui.home.HomeVIewModel
import com.malbyte.mytale.ui.login.LoginViewModel
import com.malbyte.mytale.ui.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val api: APIInterface,
    private val context: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(api) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(api) as T
            modelClass.isAssignableFrom(HomeVIewModel::class.java) -> HomeVIewModel(api) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(api) as T
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> AddStoryViewModel(api, context) as T
            else -> throw Throwable("Unknown ViewModel Class" + modelClass.name)
        }


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Application): ViewModelFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelFactory(Injection.provideApiService(), context)
                .also { INSTANCE = it }
        }
    }
}