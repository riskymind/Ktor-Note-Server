package com.kaylayshi.data.requests

/**
 * Login params for [User]
 */

@kotlinx.serialization.Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)
