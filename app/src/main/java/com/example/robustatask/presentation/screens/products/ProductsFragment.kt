package com.example.robustatask.presentation.screens.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.robustatask.R
import com.example.robustatask.databinding.ProductsFragmentBinding
import com.example.robustatask.presentation.flowredux.StateFragment
import com.example.robustatask.presentation.screens.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ProductsFragment :
    StateFragment<ProductsState, ProductsActions, ProductsEffects>(R.layout.products_fragment) {
    private val homeViewModel: HomeViewModel by sharedViewModel()
    override val viewModel: ProductsViewModel by viewModel()

    private var _binding: ProductsFragmentBinding? = null
    private val binding get() = _binding!!


    private val productsController =
        ProductsController(::onProductClicked, ::onShowMore, ::onScrollToToClicked)

    private fun onScrollToToClicked() {
        binding.productsRv.smoothScrollToPosition(0)
    }

    private fun onShowMore() {
        viewModel.dispatch(ProductsActions.ShowMore)
    }

    private fun onProductClicked(product: ProductUi) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToDetailsFragment(
                productName = product.productName.name,
                productId = product.productId.id
            )
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsRv.setController(productsController)
        homeViewModel.searchLiveData.observe(viewLifecycleOwner) { searchKey ->
            searchKey?.let {
                viewModel.dispatch(ProductsActions.Search(it))
            }
        }

        binding.errorTryAgain
            .clicks()
            .debounce(150)
            .onEach {
                viewModel.dispatch(ProductsActions.TryAgain)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun renderState(state: ProductsState) {
        when (state) {
            ProductsState.InitialState -> binding.showInitialState()
            ProductsState.Loading -> binding.showLoadingState()
            is ProductsState.Error -> binding.showErrorState()
            is ProductsState.Success -> binding.showSuccessState(
                state = state,
                controller = productsController
            )
            is ProductsState.Empty -> binding.showEmptyState(state.searchKey)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEffect(effect: ProductsEffects) {
        when (effect) {
            is ProductsEffects.ShowSnakeBar -> {
                Snackbar.make(
                    requireView(),
                    effect.message ?: getString(R.string.default_error_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}