package com.curtesmalteser

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing {
        get("/hello") {
            if (call.request.queryParameters.contains("name")) {
                call.request.queryParameters["name"]?.let {
                    call.respondText("Hello $it!")
                }
            } else {
                call.respondText("Hello World!")
            }
        }

    }
}