package com.example.newsapp.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemArticlePreviewBinding
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.utility.NewsDiffUtil

class ItemArticleAdapter: RecyclerView.Adapter<ItemArticleAdapter.MyViewHolder>() {

    private var news = emptyList<Article>()

    class MyViewHolder(private val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.article = article
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticlePreviewBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

    fun setData(newData: NewsResponse){
        val newsDiffUtil =
            NewsDiffUtil(news,newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        news = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }

}