package com.example.robustatask.presentation.screens.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.robustatask.R
import com.example.robustatask.databinding.HomeActivityBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.searchTIL.doAfterTextChanged {
            search(binding.searchTIL.text.toString())
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragContainer) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.searchTIL.isVisible = destination.id != R.id.detailsFragment
        }
    }

    private fun search(searchKey: String?) {
        searchKey?.let {
            viewModel.search(it)
        }
    }
}