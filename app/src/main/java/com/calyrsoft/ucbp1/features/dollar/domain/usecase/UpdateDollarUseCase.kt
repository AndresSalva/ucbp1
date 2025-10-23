package com.calyrsoft.ucbp1.features.dollar.domain.usecase

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IFirebaseRepository

class UpdateDollarUseCase(
    val repository: IFirebaseRepository
) {
    suspend fun udpateDollar(dollar: DollarModel): Result<DollarModel> {
        return repository.updateDollar(dollar)
    }
}