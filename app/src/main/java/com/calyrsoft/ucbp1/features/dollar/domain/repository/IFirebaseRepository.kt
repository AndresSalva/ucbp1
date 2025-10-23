package com.calyrsoft.ucbp1.features.dollar.domain.repository

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

interface IFirebaseRepository {
    suspend fun updateDollar(dollarModel: DollarModel): Result<DollarModel>
}