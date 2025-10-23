package com.calyrsoft.ucbp1.features.maintenance.data.datasource

import android.util.Log
import com.calyrsoft.ucbp1.features.maintenance.domain.model.MaintenanceStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class MaintenanceRemoteDataSource {
    fun getStatus(): Flow<MaintenanceStatus> = callbackFlow {
        val database = Firebase.database
        val maintenanceRef = database.getReference("config/maintenance")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("MANTENIMIENTO_DEBUG", "Firebase onDataChange llamado. Snapshot: ${snapshot.value}")

                // --- [INICIO DEL CAMBIO MÁS IMPORTANTE] ---
                // Dejamos de usar snapshot.getValue(MaintenanceStatus::class.java)

                // 1. Leemos cada valor del snapshot manualmente.
                val isActiveValue = snapshot.child("isActive").getValue(Boolean::class.java) ?: false
                val messageValue = snapshot.child("message").getValue(String::class.java) ?: "En mantenimiento"

                // 2. Creamos el objeto nosotros mismos con los valores leídos.
                val newStatus = MaintenanceStatus(isActiveValue, messageValue)

                Log.d("MANTENIMIENTO_DEBUG", "Objeto creado MANUALMENTE. isActive = ${newStatus.isActive}")

                // 3. Enviamos el objeto creado manualmente.
                trySend(newStatus)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MANTENIMIENTO_DEBUG", "Firebase onCancelled. Error: ${error.message}")
                close(error.toException())
            }
        }
        maintenanceRef.addValueEventListener(listener)

        awaitClose {
            maintenanceRef.removeEventListener(listener)
        }
    }.flowOn(Dispatchers.IO)
}