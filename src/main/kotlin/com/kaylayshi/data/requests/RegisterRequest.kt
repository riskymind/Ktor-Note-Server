package com.kaylayshi.data.requests
import kotlinx.serialization.Serializable
/**
 *  Register params for [User]
 */
@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)