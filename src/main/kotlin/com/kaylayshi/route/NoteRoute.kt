package com.kaylayshi.route

import com.kaylayshi.authentication.JwtService
import com.kaylayshi.data.model.Note
import com.kaylayshi.data.model.User
import com.kaylayshi.data.response.SimpleResponse
import com.kaylayshi.repository.NoteRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.noteRoute(
    db: NoteRepo
) {

    authenticate("jwt") {

        // Create note Endpoint
        post("v1/notes/create") {
            val note = try {
                call.receive<Note>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
                return@post
            }

            try {
                val email = call.principal<User>()!!.email
                db.createNote(note, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Noted created successfully!!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Unknown error occurred"))
            }
        }

        // Get all notes endpoint
        get("v1/notes") {
            try {
                val email = call.principal<User>()!!.email
                val notes = db.getAllNotes(email)
                call.respond(HttpStatusCode.OK, notes)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "An error occurred."))
            }
        }

        // Update note
        post("v1/notes/update") {
            val note = try {
                call.receive<Note>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
                return@post
            }

            try {
                val email = call.principal<User>()!!.email
                db.updateNote(note, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note updated successfully!!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "An error occurred."))
            }
        }

        // Delete note
        delete("v1/notes/delete") {
            val id = try {
                call.request.queryParameters["id"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing fields"))
                return@delete
            }

            try {
                val email = call.principal<User>()!!.email
                db.deleteNote(id, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note deleted successfully!!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "An error occurred."))
            }
        }
    }

}