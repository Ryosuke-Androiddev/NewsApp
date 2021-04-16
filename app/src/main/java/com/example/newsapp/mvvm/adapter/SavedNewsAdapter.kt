package com.example.newsapp.mvvm.adapter

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemSavedNewsRowBinding
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import com.example.newsapp.mvvm.ui.fragments.SavedNewsFragmentDirections
import com.example.newsapp.mvvm.utility.NewsDiffUtil

class SavedNewsAdapter(
        private val requireActivity: FragmentActivity
): RecyclerView.Adapter<SavedNewsAdapter.MyViewHolder>(), ActionMode.Callback{

    private var savedNews = emptyList<FavoriteEntity>()

    class MyViewHolder(val binding: ItemSavedNewsRowBinding):
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
        val currentRecipe = savedNews[position]
        holder.bind(currentRecipe)

        /**
         * Single Click Listener
         * */
        holder.binding.savedlayout.setOnClickListener {
                val action =
                        SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailActivity(
                                currentRecipe.article
                        )
                holder.itemView.findNavController().navigate(action)
        }

        /**
         * Long Click Listener
         * */
        holder.binding.savedlayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }

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

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {

    }
}