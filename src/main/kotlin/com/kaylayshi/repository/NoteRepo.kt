package com.kaylayshi.repository

import com.kaylayshi.data.model.Note
import com.kaylayshi.data.table.NoteTable
import com.kaylayshi.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NoteRepo {

    // Create note
    suspend fun createNote(note: Note, email: String) =
        dbQuery {
            NoteTable.insert { noteTable ->
                noteTable[noteId] = note.id
                noteTable[noteTitle] = note.title
                noteTable[noteContent] = note.content
                noteTable[noteDate] = note.date
                noteTable[userEmail] = email
            }
        }

    // Get all notes
    suspend fun getAllNotes(email: String): List<Note> {
        return dbQuery {
            NoteTable.select { NoteTable.userEmail.eq(email) }
                .mapNotNull { rowToNote(it) }
        }
    }

    // Update Note
    suspend fun updateNote(note: Note, email: String) = dbQuery {
        NoteTable.update(
            where = { NoteTable.userEmail.eq(email) and NoteTable.noteId.eq(note.id) }
        ) { noteTable ->
            noteTable[noteTitle] = note.title
            noteTable[noteContent] = note.content
            noteTable[noteDate] = note.date
        }
    }

    // Delete Note
    suspend fun deleteNote(id: String, email: String) = dbQuery {
        NoteTable.deleteWhere { noteId.eq(id) and userEmail.eq(email) }
    }


    private fun rowToNote(row: ResultRow?): Note? {
        if (row == null) {
            return null
        }

        return Note(
            id = row[NoteTable.noteId],
            title = row[NoteTable.noteTitle],
            content = row[NoteTable.noteContent],
            date = row[NoteTable.noteDate]
        )
    }
}