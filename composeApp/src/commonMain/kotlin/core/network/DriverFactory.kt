package core.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory() {
    fun createHttpClient() = HttpClient {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "your_token")
            append(HttpHeaders.UserAgent, "ktor client")
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // Here you can log with any preferred logging framework.
                    println(message)
                }
            }
            level = LogLevel.ALL
            filter { request ->
                request.url.host.contains("jsonplaceholder")
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

    }
}