package com.kaylayshi.data.table

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val email = varchar("email", 512)
    val name = varchar("name", 512)
    val password = varchar("password", 512)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(email)
}