package com.example.newsapp.mvvm.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.ui.NewsActivity
import com.example.newsapp.mvvm.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticleBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Article? = args?.getParcelable("articleBundle")

        binding.mainImageView.load(myBundle?.urlToImage)
        binding.summaryTextView.text = myBundle?.description
        binding.titleTextView.text = myBundle?.title

        myBundle?.description.let {
            val desc = Jsoup.parse(it).text()
            binding.summaryTextView.text = desc
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}