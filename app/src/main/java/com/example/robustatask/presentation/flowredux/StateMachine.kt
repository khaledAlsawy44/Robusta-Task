package com.example.robustatask.presentation.flowredux

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class StateMachine<State : Any, Action : Any, Effect : Any>(
    initialState: State
) : FlowReduxStateMachine<State, Action>(
    initialState
) {


    private val _effect = Channel<Effect>(Channel.CONFLATED)
    val effect = _effect.receiveAsFlow()

    suspend fun dispatchEffect(effect: Effect) {
        _effect.send(effect)
    }

    open fun clear(state: State?) {
    }
}
