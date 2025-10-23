package com.calyrsoft.ucbp1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.maintenance.domain.model.MaintenanceStatus
import com.calyrsoft.ucbp1.features.maintenance.domain.usecase.GetMaintenanceStatusUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class MainUiState {
    object Loading : MainUiState()
    data class Ready(val status: MaintenanceStatus) : MainUiState()
}

class MainViewModel(
    getMaintenanceStatusUseCase: GetMaintenanceStatusUseCase
) : ViewModel() {
    val uiState: StateFlow<MainUiState> =
        getMaintenanceStatusUseCase().map { status ->
            MainUiState.Ready(status)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading
        )
}