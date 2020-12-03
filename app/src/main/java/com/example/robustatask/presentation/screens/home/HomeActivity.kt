package com.example.robustatask.presentation.screens.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
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

        binding.searchTIET.doAfterTextChanged {
            search(binding.searchTIET.text.toString())
        }
    }

    private fun search(searchKey: String?) {
        searchKey?.let {
            viewModel.dispatch(ProductsActions.Search(it))
        }
    }
}