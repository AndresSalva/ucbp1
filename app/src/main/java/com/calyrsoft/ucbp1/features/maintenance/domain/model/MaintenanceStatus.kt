package com.calyrsoft.ucbp1.features.maintenance.domain.model

import com.google.firebase.database.IgnoreExtraProperties

// Le quitamos @Keep porque ya no dependemos de la reflexión
@IgnoreExtraProperties
class MaintenanceStatus {
    var isActive: Boolean = false
    var message: String = "En mantenimiento"

    // Constructor vacío que Firebase puede seguir usando si es necesario
    constructor()

    // Constructor que USAREMOS NOSOTROS para crear el objeto manualmente
    constructor(isActive: Boolean, message: String) {
        this.isActive = isActive
        this.message = message
    }
}