package com.example.newsapp.mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDetailBinding
import com.example.newsapp.mvvm.adapter.ViewPagerAdapter
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import com.example.newsapp.mvvm.ui.fragments.ArticleFragment
import com.example.newsapp.mvvm.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.ArrayList

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val args by navArgs<DetailActivityArgs>()

    private lateinit var newsViewModel: NewsViewModel

    private var newsSaved = false
    private var savedNewsId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(ArticleFragment())

        val titles = ArrayList<String>()
        titles.add("Article")

        val resultBundle = Bundle()
        resultBundle.putParcelable("articleBundle",args.article)

        val pagerAdapter = ViewPagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2?.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2){ tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !newsSaved){
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && newsSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity =
                FavoriteEntity(
                        0,
                        args.article
                )
        newsViewModel.insertFavoriteNews(favoriteEntity)
        changeMenuItemColor(item,R.color.yellow)
        showSnackBar("News Saved!!")
        newsSaved = true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
                binding.detailsLayout,
                message,
                Snackbar.LENGTH_LONG
        ).setAction("Okay"){}
                .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        val menuItem = menu!!.findItem(R.id.save_to_favorites_menu)
        checkSavedNews(menuItem)
        return true
    }

    private fun checkSavedNews(menuItem: MenuItem?) {
        newsViewModel.readFavoriteNews.observe(this, Observer { favoriteEntities->
            try {
                for (savedNews in favoriteEntities)
                    if (savedNews.article.title == args.article.title){
                        changeMenuItemColor(menuItem!!,R.color.yellow)
                        savedNewsId = savedNews.id
                        newsSaved = true
                    } else{
                        changeMenuItemColor(menuItem!!,R.color.white)
                    }
            }catch (e:Exception){
                Log.d("DetailsActivity",e.message.toString())
            }
        })
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
                FavoriteEntity(
                        savedNewsId,
                        args.article
                )
        newsViewModel.deleteFavoriteNews(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        newsSaved = false
    }
}