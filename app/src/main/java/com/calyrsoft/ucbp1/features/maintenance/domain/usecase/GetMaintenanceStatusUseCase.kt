package com.calyrsoft.ucbp1.features.maintenance.domain.usecase

import com.calyrsoft.ucbp1.features.maintenance.domain.model.MaintenanceStatus
import com.calyrsoft.ucbp1.features.maintenance.domain.repository.IMaintenanceRepository
import kotlinx.coroutines.flow.Flow

class GetMaintenanceStatusUseCase(private val repository: IMaintenanceRepository) {
    operator fun invoke(): Flow<MaintenanceStatus> = repository.getMaintenanceStatus()
}