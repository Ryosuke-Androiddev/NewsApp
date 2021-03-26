package com.example.newsapp.mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.mvvm.adapter.ItemArticleAdapter
import com.example.newsapp.mvvm.viewmodel.QueryViewModel
import com.example.newsapp.mvvm.utility.NetworkResult
import com.example.newsapp.mvvm.utility.observeOnce
import com.example.newsapp.mvvm.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var queryViewModel: QueryViewModel
    private val newsAdapter by lazy { ItemArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        queryViewModel = ViewModelProvider(requireActivity()).get(QueryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)

        setupRecyclerView()
        readDatabase()

        return binding.root
    }


    private fun setupRecyclerView() {
        binding.recyclerview.adapter = newsAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }


    //You should be careful because this method will be done twice regardless your intention!!
    private fun readDatabase() {
        lifecycleScope.launch{
            newsViewModel.getAllArticles.observeOnce(viewLifecycleOwner,{ datebase ->
                if (datebase.isNotEmpty()){

                    Log.d("BreakingNewsFragment", "ReadDatabase is called!!")

                    newsAdapter.setData(datebase[0].newsResponse)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {

        Log.d("BreakingNewsFragment", "requestApiData is called!!")

        newsViewModel.getInformation(queryViewModel.applyQueries())
        newsViewModel.newsResponses.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { newsAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    // If you have no internet connection don't worry, you can see news because this method tell status which you used last time!!
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
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}