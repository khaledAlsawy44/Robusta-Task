package com.example.robustatask.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.robustatask.databinding.ViewShowMoreBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


@ExperimentalCoroutinesApi
@FlowPreview
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ShowMoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewShowMoreBinding =
        ViewShowMoreBinding.inflate(LayoutInflater.from(context), this)

    private var viewScope: CoroutineScope? = null

    var showMoreListener: (() -> Unit)? = null
        @CallbackProp set

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        viewScope = scope

        clicks()
            .debounce(250)
            .onEach { showMoreListener?.invoke() }
            .launchIn(scope)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewScope?.cancel()
    }

    @ModelProp
    fun setShowMoreState(isLoading: Boolean) {
        binding.loadingMorePb.isVisible = isLoading
        binding.showMoreTv.text = if (isLoading) "Loading ...."
        else "Show More"

    }

}