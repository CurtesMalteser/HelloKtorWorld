package com.curtesmalteser

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson ()
    }

    routing {
        get("/hello") {
            if (call.request.queryParameters.contains("name")) {
                call.request.queryParameters["name"]?.let {
                    call.respond(mapOf("hello" to "Hello $it!"))
                }
            } else {
                call.respond(mapOf("hello" to "Hello World!"))
            }
        }

        webSocket("/chat") { // this: DefaultWebSocketSession
            while (true) {
                val frame = incoming.receive() // suspend
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        outgoing.send(Frame.Text(text)) // suspend
                    }
                }
            }
        }
    }

}