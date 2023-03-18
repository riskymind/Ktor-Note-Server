package com.kaylayshi.plugins

import com.kaylayshi.authentication.hash
import com.kaylayshi.repository.AuthRepo
import com.kaylayshi.route.userRoute
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    val db = AuthRepo()
    val hash = { string: String -> hash(string) }

    routing {
        userRoute(db, hash)
    }
}
