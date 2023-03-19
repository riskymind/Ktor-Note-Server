package com.kaylayshi.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kaylayshi.authentication.JwtService
import com.kaylayshi.repository.AuthRepo
import io.ktor.server.application.*

fun Application.configureSecurity() {

    val authRepo = AuthRepo()
    val jwtService = JwtService()

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "Note server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = authRepo.findUserByEmail(email)
                user
            }
        }
    }
}
