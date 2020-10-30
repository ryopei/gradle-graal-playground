package net.sunaba.graal

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, 8080) {
        routing {
            get("/") {
                call.respondText("My Example Blog", ContentType.Text.Html)
            }
            get("/client") {
                val res: String = HttpClient().get("https://ktor.io")
                call.respondText(res, ContentType.Text.Html)
            }
            get("/info") {
                call.respondText(
                    "Available Processors:${Runtime.getRuntime().availableProcessors()}", ContentType.Text.Html
                )
            }
        }
    }.start(wait = true)
}