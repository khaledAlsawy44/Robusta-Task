package com.example.robustatask.presentation.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.robustatask.databinding.ViewProductBinding
import com.example.robustatask.presentation.screens.home.ProductUi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import java.util.*


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

    @ModelProp
    fun setProductData(product: ProductUi) {
        val mySpannedText =
            SpannableString(product.productName.name.toLowerCase(Locale.getDefault()))
        mySpannedText.spanWith(product.searchKey.toLowerCase(Locale.getDefault())) {
            what = BackgroundColorSpan(Color.RED)
            flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        }
        binding.productNameTv.text = mySpannedText
        binding.description.text = product.productDescription.description

    }
}

fun SpannableString.spanWith(target: String, apply: SpannableBuilder.() -> Unit) {
    val builder = SpannableBuilder()
    apply(builder)

    val start = this.indexOf(target)
    val end = start + target.length


    val blueColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.BLACK))
    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null)
    setSpan(highlightSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

}

class SpannableBuilder {
    lateinit var what: Any
    var flags: Int = 0
}