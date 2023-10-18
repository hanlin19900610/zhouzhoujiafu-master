package com.mufeng.demo.ui.page.main

import com.mufeng.libs.base.BaseViewModel
import com.mufeng.libs.core.mvi.setState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : BaseViewModel() {

    private val _viewStates = MutableStateFlow(MainUiState())
    val viewStates = _viewStates.asStateFlow()

    fun dispatch(viewAction: MainUiAction) {
        when (viewAction) {
            is MainUiAction.UpdateBottomBarLayoutPosition -> {
                updateViewPagerPosition(if (viewAction.index > 1) viewAction.index - 1 else viewAction.index)
            }
            is MainUiAction.UpdateViewPagerPosition -> {
                updateBottomBarLayoutPosition(if (viewAction.index > 1) viewAction.index + 1 else viewAction.index)
            }
        }
    }

    private fun updateViewPagerPosition(position: Int) {
        _viewStates.setState { copy(viewPagerPosition = position) }
    }
    private fun updateBottomBarLayoutPosition(position: Int) {
        _viewStates.setState { copy(bottomBarLayoutPosition = position) }
    }


}