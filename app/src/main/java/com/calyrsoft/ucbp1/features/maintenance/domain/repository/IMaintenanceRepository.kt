package com.calyrsoft.ucbp1.features.maintenance.domain.repository

import com.calyrsoft.ucbp1.features.maintenance.domain.model.MaintenanceStatus
import kotlinx.coroutines.flow.Flow

interface IMaintenanceRepository {
    fun getMaintenanceStatus(): Flow<MaintenanceStatus>
}