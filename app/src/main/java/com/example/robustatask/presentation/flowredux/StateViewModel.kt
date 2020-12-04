package com.example.robustatask.presentation.flowredux

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class StateViewModel<State : Any, Action : Any, Effect : Any>(
    private val stateMachine: StateMachine<State, Action, Effect>
) : ViewModel() {


    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    val effects = stateMachine.effect

    init {
        viewModelScope.launch {
            stateMachine.state.collect { newState ->
                _state.value = newState
            }
        }
    }

    open fun dispatch(action: Action) {
        viewModelScope.launch {
            Timber.tag("FlowRedux - action -> ").d(action.toString())
            stateMachine.dispatch(action)
        }
    }

    fun currentState() = state.value

    override fun onCleared() {
        stateMachine.clear(state.value)
        super.onCleared()
    }
}
