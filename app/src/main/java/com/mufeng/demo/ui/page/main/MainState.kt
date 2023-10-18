package com.mufeng.demo.ui.page.main

sealed class MainUiAction{
    data class UpdateViewPagerPosition(val index: Int = 0): MainUiAction()
    data class UpdateBottomBarLayoutPosition(val index: Int = 0): MainUiAction()
}

data class MainUiState(
    val viewPagerPosition: Int = 0,
    val bottomBarLayoutPosition: Int = 0,
)