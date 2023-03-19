package com.kaylayshi.plugins

import com.kaylayshi.authentication.JwtService
import com.kaylayshi.authentication.hash
import com.kaylayshi.repository.AuthRepo
import com.kaylayshi.repository.NoteRepo
import com.kaylayshi.route.noteRoute
import com.kaylayshi.route.userRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    val authRepo = AuthRepo()
    val noteRepo = NoteRepo()
    val hash = { string: String -> hash(string) }
    val jwtService = JwtService()

    routing {
        userRoute(authRepo, jwtService, hash)
        noteRoute(noteRepo)
    }
}
