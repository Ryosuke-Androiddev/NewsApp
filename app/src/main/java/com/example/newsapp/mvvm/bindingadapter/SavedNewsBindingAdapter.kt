package com.example.newsapp.mvvm.bindingadapter

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.mvvm.adapter.SavedNewsAdapter
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.ui.fragments.BreakingNewsFragmentDirections

class SavedNewsBindingAdapter {

    companion object{

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(view: View, favoritesEntity: List<FavoriteEntity>?, mAdapter: SavedNewsAdapter?) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isInvisible = dataCheck
                    if(!dataCheck){
                        favoritesEntity?.let { mAdapter?.setData(it) }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }

    }
}