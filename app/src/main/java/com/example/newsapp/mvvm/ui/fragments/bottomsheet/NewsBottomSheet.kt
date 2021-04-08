package com.example.newsapp.mvvm.ui.fragments.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsBottomSheetBinding
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_CATEGORY
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_CHIP_ID
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_COUNTRY
import com.example.newsapp.mvvm.viewmodel.QueryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.*

class NewsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: NewsBottomSheetBinding? = null
    private val binding get() = _binding!!

    //Tips Default value
    private var newsCategory = DEFAULT_NEWS_CATEGORY
    private var newsCategoryId = DEFAULT_NEWS_CHIP_ID
    private var newsCountry = DEFAULT_NEWS_COUNTRY
    private var newsCountryId = DEFAULT_NEWS_CHIP_ID

    private lateinit var queryViewModel: QueryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryViewModel = ViewModelProvider(requireActivity()).get(QueryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = NewsBottomSheetBinding.inflate(inflater,container,false)

        queryViewModel.readNewsTipsType.asLiveData().observe(viewLifecycleOwner,{value->
            newsCategory = value.selectedNewsCategory
            newsCountry = value.selectedNewsCountry
            updateChip(value.selectedNewsCategoryId,binding.categoryTypeChipGroup)
            updateChip(value.selectedNewsCountryId,binding.countryTypeChipGroup)
        })

        binding.categoryTypeChipGroup.setOnCheckedChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedNewsCategory = chip.text.toString().toLowerCase(Locale.ROOT)
            newsCategory = selectedNewsCategory
            newsCategoryId = selectedChipId
        }

        binding.countryTypeChipGroup.setOnCheckedChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedNewsCountry = chip.text.toString().toLowerCase(Locale.ROOT)
            newsCountry = selectedNewsCountry
            newsCountryId = selectedChipId
        }

        binding.applyButton.setOnClickListener{
            queryViewModel.saveNewsTipsType(
                    newsCategory,
                    newsCategoryId,
                    newsCountry,
                    newsCountryId
            )
            val action = NewsBottomSheetDirections.actionNewsBottomSheetToBreakingNewsFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception){
                Log.d("QueryViewModel",e.message.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}