package com.example.newsapp.mvvm.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.mvvm.adapter.ItemArticleAdapter
import com.example.newsapp.mvvm.utility.NetworkResult
import com.example.newsapp.mvvm.viewmodel.NewsViewModel
import com.example.newsapp.mvvm.viewmodel.QueryViewModel
import com.example.newsapp.mvvm.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(), SearchView.OnQueryTextListener{

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var queryViewModel: QueryViewModel
    private val newsAdapter by lazy { ItemArticleAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchNewsBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun searchForNews(searchQuery: String){
        showShimmerEffect()
        searchViewModel.searchForNews(queryViewModel.applySearchQuery(searchQuery))
        searchViewModel.searchedNewsResponse.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    val searchNews = response.data
                    searchNews?.let { newsAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchForNews(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch{
            newsViewModel.getAllArticles.observe(viewLifecycleOwner,{database ->
                if (database.isNotEmpty()){
                    newsAdapter.setData(database[0].newsResponse)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerviewSearch.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerviewSearch.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}