package com.calyrsoft.ucbp1.features.maintenance.data.repository

import com.calyrsoft.ucbp1.features.maintenance.data.datasource.MaintenanceRemoteDataSource
import com.calyrsoft.ucbp1.features.maintenance.domain.model.MaintenanceStatus
import com.calyrsoft.ucbp1.features.maintenance.domain.repository.IMaintenanceRepository
import kotlinx.coroutines.flow.Flow

class MaintenanceRepository(
    private val remoteDataSource: MaintenanceRemoteDataSource
) : IMaintenanceRepository {
    override fun getMaintenanceStatus(): Flow<MaintenanceStatus> = remoteDataSource.getStatus()
}