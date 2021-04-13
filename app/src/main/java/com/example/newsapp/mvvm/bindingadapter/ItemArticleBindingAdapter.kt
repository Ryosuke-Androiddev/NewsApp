package com.example.newsapp.mvvm.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.newsapp.R
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.models.Source
import com.example.newsapp.mvvm.ui.fragments.BreakingNewsFragmentDirections
import org.jsoup.Jsoup

class ItemArticleBindingAdapter {


    companion object{


        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(newsRowLayout: ConstraintLayout, article: Article){
            newsRowLayout.setOnClickListener {
                try {
                    val action =
                        BreakingNewsFragmentDirections.actionBreakingNewsFragmentToDetailActivity(article)
                    newsRowLayout.findNavController().navigate(action)
                } catch (e: Exception){
                    Log.d("onRecipeClickListener",e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView,imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }


        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?){
            if (description != null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }
}