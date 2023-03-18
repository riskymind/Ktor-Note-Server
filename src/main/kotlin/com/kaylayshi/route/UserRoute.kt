package com.kaylayshi.route

import com.kaylayshi.authentication.JwtService
import com.kaylayshi.data.model.User
import com.kaylayshi.data.requests.LoginRequest
import com.kaylayshi.data.requests.RegisterRequest
import com.kaylayshi.data.response.SimpleResponse
import com.kaylayshi.repository.AuthRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(
    db: AuthRepo,
    jwtService: JwtService,
    hashFunction: (String) -> String,
) {

    // Create [User] Endpoint
    post("/v1/users/register") {
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
            return@post
        }

        try {
            val user = User(
                email = registerRequest.email,
                name = registerRequest.name,
                password = hashFunction(registerRequest.password)
            )
            db.addUser(user)
            call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken(user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(true, e.message ?: "An error occurred"))
        }
    }

    // Login [User] Endpoint
    post("/v1/users/login") {
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
            return@post
        }

        try {
            val user = db.findUserByEmail(loginRequest.email)
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Wrong Email id"))
            } else {
                if (user.password == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken(user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Incorrect password!!"))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "An Error occurred"))
        }
    }
}