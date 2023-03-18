package com.kaylayshi.repository

import com.kaylayshi.data.model.User
import com.kaylayshi.data.table.UserTable
import com.kaylayshi.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AuthRepo {

    // Add user to database
    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { userTable ->
                userTable[email] = user.email
                userTable[name] = user.name
                userTable[password] = user.password
            }
        }
    }

    // Find user by their email in the database
    suspend fun findUserByEmail(email: String): User? {
        return dbQuery {
            UserTable.select { UserTable.email.eq(email) }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }


    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }

        return User(
            email = row[UserTable.email],
            name = row[UserTable.name],
            password = row[UserTable.password]
        )
    }
}