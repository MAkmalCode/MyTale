package com.malbyte.mytale.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.malbyte.mytale.data.source.remote.models.Story
import com.malbyte.mytale.databinding.ItemStoryBinding

class StoryAdapter(private val listStory: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    class StoryViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story){
            with(binding){
                binding.image.load(story.photoUrl)
                binding.title.text = story.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }
}