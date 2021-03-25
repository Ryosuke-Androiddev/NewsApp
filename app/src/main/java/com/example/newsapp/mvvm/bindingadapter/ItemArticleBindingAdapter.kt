package com.example.newsapp.mvvm.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.newsapp.mvvm.models.Source

class ItemArticleBindingAdapter {


    companion object{

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView,imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
            }
        }


        @BindingAdapter("setSource")
        @JvmStatic
        fun setSource(textView: TextView,source:Source){
            textView.text = source.toString()
        }
    }
}