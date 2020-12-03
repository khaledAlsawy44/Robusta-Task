package com.example.robustatask.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.robustatask.R
import com.example.robustatask.databinding.HomeFragmentBinding
import com.example.robustatask.domain.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel: HomeViewModel by sharedViewModel()
    private var uiStateJob: Job? = null

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!


    private val productsController =
        ProductsController(::onProductClicked)

    private fun onProductClicked(product: Product) {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsRv.setController(productsController)

//        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
//            renderState(state)
//        }
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: ProductsState) {
        when (state) {
            ProductsState.InitialState -> binding.showInitialState()
            ProductsState.Loading -> binding.showLoadingState()
            is ProductsState.Error -> binding.showErrorState()
            is ProductsState.Success -> binding.showSuccessState(
                state = state,
                controller = productsController
            )
        }
    }

    override fun onStop() {
        // Stop collecting when the View goes to the background
        uiStateJob?.cancel()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}