package com.malbyte.mytale.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.malbyte.mytale.R
import com.malbyte.mytale.data.factory.ViewModelFactory
import com.malbyte.mytale.databinding.ActivityHomeBinding
import com.malbyte.mytale.ui.add_story.AddStoryActivity
import com.malbyte.mytale.ui.detail.DetailActivity
import com.malbyte.mytale.ui.home.state.HomeState

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by viewBinding()
    private val viewModel: HomeVIewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllStory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val storyRV = binding.rvStory
        val loadingBar = binding.loadingBar

        binding.btnHomeAddStory.isVisible = false
        loadingBar.isVisible = true
        viewModel.StoryState.observe(this){
            when(it){
                is HomeState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    loadingBar.isVisible = false
                }
                is HomeState.Loading -> {
                    loadingBar.isVisible = true
                }
                is HomeState.Success -> {
                    val adapter = StoryAdapter(it.list) {id ->
                        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                    Toast.makeText(this, it.list.toString(), Toast.LENGTH_SHORT).show()
                    storyRV.adapter = adapter
                    loadingBar.isVisible = false

                    binding.btnHomeAddStory.isVisible = true
                    binding.btnHomeAddStory.setOnClickListener {
                        val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}