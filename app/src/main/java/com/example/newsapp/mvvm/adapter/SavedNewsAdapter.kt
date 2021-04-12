package com.example.newsapp.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemSavedNewsRowBinding
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import com.example.newsapp.mvvm.utility.NewsDiffUtil

class SavedNewsAdapter: RecyclerView.Adapter<SavedNewsAdapter.MyViewHolder>(){

    private var savedNews = emptyList<FavoriteEntity>()

    class MyViewHolder(private val binding: ItemSavedNewsRowBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(favoriteEntity: FavoriteEntity){
                binding.favoriteEntity = favoriteEntity
                binding.executePendingBindings()
            }

            companion object{
                fun from(parent: ViewGroup): MyViewHolder{
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemSavedNewsRowBinding.inflate(layoutInflater, parent,false)
                    return MyViewHolder(binding)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return savedNews.size
    }

    fun setData(newFavorite: List<FavoriteEntity>){
        val favoriteNewsDiffUtil =
                NewsDiffUtil(savedNews,newFavorite)

        val diffUtilResult = DiffUtil.calculateDiff(favoriteNewsDiffUtil)
        savedNews = newFavorite
        diffUtilResult.dispatchUpdatesTo(this)
    }
}