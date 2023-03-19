package com.kaylayshi

import io.ktor.server.application.*
import com.kaylayshi.plugins.*
import com.kaylayshi.repository.DatabaseFactory

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
