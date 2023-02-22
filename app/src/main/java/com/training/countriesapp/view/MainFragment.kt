package com.training.countriesapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.training.countriesapp.R
import com.training.countriesapp.constants.ConnectionCheck
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment(R.layout.fragment_main) {
    private lateinit var fragmentBinding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        fragmentBinding = binding

        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            findNavController(),
        )
        val snackBar = Snackbar
            .make(binding.mainFragment, Constants.NO_CONNECTION, Snackbar.LENGTH_LONG)

        binding.btnListCountries.setOnClickListener {
            if (ConnectionCheck(view.context).checkInternetConnection()) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToCountryListFragment())
            } else {
                snackBar.show()
            }
        }

        binding.btnListContinents.setOnClickListener {
            if (ConnectionCheck(view.context).checkInternetConnection()) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToContinentListFragment())
            } else {
                snackBar.show()
            }
        }
    }
}