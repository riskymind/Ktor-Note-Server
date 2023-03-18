package com.kaylayshi.data.model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

// User Model
@Serializable
data class User(
    val name: String,
    val email: String,
    val password: String,
) : Principal
