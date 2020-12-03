package com.example.robustatask.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.robustatask.databinding.ViewProductBinding
import com.example.robustatask.domain.Product
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


@ExperimentalCoroutinesApi
@FlowPreview
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ProductsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewProductBinding =
        ViewProductBinding.inflate(LayoutInflater.from(context), this)

    private var viewScope: CoroutineScope? = null

    var itemClickedListener: (() -> Unit)? = null
        @CallbackProp set

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        viewScope = scope

        clicks()
            .debounce(250)
            .onEach { itemClickedListener?.invoke() }
            .launchIn(scope)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewScope?.cancel()
    }


    init {
    }

    @ModelProp
    fun setProductData(product: Product) {
        binding.testText.text = product.productName.name
    }

}