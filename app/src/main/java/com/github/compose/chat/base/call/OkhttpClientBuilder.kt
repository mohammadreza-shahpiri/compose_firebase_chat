package com.github.compose.chat.base.call

import com.github.compose.chat.BuildConfig
import com.github.compose.chat.data.source.UserConfig
import com.github.compose.chat.firebase.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

object OkhttpClientBuilder {
    fun buildHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                    followSslRedirects(true)
                    retryOnConnectionFailure(true)
                    readTimeout(1, TimeUnit.MINUTES)
                    writeTimeout(1, TimeUnit.MINUTES)
                    connectTimeout(1, TimeUnit.MINUTES)
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        UserConfig.accessToken.toString().bearerAccess()
                    }
                    refreshTokens {
                        UserConfig.run {
                            accessToken = getUpdatedToken().toString()
                        }
                        UserConfig.accessToken.toString().bearerAccess()
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            defaultRequest {
                url((BuildConfig.MessageUrl))
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            expectSuccess = true
        }
    }
}

suspend fun getUpdatedToken(): String? {
    return withContext(Dispatchers.IO) {
        TokenManager.getAccessToken()
    }
}

fun String.bearerAccess() = BearerTokens(this, "")