package com.kaylayshi.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.references

object NoteTable : Table() {
    val noteId = varchar("id", 512)
    val noteTitle = text("title")
    val noteContent = text("content")
    val noteDate = long("date")
    val userEmail = varchar("email", 512).references(UserTable.email)

    override val primaryKey: PrimaryKey get() = PrimaryKey(noteId)
}