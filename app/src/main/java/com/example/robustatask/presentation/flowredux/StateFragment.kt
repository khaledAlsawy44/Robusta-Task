package com.example.robustatask.presentation.flowredux

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class StateFragment<State : Any, Action : Any, Effect : Any>(
    @LayoutRes val contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected abstract val viewModel: StateViewModel<State, Action, Effect>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        viewModel.effects
            .onEach { effect -> onEffect(effect) }
            .flowOn(Dispatchers.Main)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    protected fun currentState() = viewModel.currentState()
    abstract fun renderState(state: State)
    abstract fun onEffect(effect: Effect)
}
