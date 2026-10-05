package com.trackfox.app.service

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val userSessionManager: UserSessionManager
) : Authenticator {


    private fun count(response: Response) : Int {
        var cnt = 0
        var prior = response.priorResponse()
        while (prior != null) {
            cnt++
            prior = prior.priorResponse()
        }
        return cnt
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (count(response) > 1) {
            return null
        }

        val accessToken = userSessionManager.refreshTokens()
        if (accessToken == "") return null

        return response.request().newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}