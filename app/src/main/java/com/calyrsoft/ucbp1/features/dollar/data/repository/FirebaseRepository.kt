package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IFirebaseRepository

class FirebaseRepository(
    val firebaseRemoteDatasource: DollarRemoteDataSource
): IFirebaseRepository {
    override suspend fun updateDollar(dollarModel: DollarModel): Result<DollarModel> {
        firebaseRemoteDatasource.setDollar(dollarModel.purchasePrice!!)
        return Result.success(DollarModel(
            purchasePrice = dollarModel.purchasePrice,
            salesPrice = dollarModel.salesPrice
        ))
    }
}