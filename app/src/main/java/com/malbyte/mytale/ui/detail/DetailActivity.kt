package com.malbyte.mytale.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.malbyte.mytale.R
import com.malbyte.mytale.data.factory.ViewModelFactory
import com.malbyte.mytale.databinding.ActivityDetailBinding
import com.malbyte.mytale.ui.detail.state.DetailState

class DetailActivity : AppCompatActivity() {
    private val binding : ActivityDetailBinding by viewBinding()
    private val viewModel : DetailViewModel by viewModels{
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val loadingBar = binding.loadingBar
        val image = binding.ivDetailImage
        val name = binding.tvDetailName
        val desk = binding.tvDetailDeskripsi

        val id= intent.getStringExtra("id") ?: "osjsfjneifgefjn"
        viewModel.getDetail(id)

        loadingBar.isVisible = false
        viewModel.detailState.observe(this){
            when(it){
                is DetailState.Error -> {
                    Toast.makeText(this@DetailActivity, it.message, Toast.LENGTH_SHORT).show()
                    loadingBar.isVisible = false
                }
                is DetailState.Loading -> {
                    loadingBar.isVisible = true
                }
                is DetailState.Success -> {
                    image.load(it.story.photoUrl)
                    name.text = it.story.name
                    desk.text = it.story.description
                    loadingBar.isVisible = false
                }
            }
        }
    }
}