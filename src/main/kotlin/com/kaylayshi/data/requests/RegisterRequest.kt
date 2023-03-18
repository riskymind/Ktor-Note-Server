package com.kaylayshi.data.requests
import kotlinx.serialization.Serializable
/**
 *  model for registration
 */
@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)