package pl.pk.myUserApp.data

import java.io.Serializable

data class LoginResponse(
    var access_token: String,
    var token_type: String,
    var expires_in: Int,
    var scope: String,
    var email: String,
    var jti: String
) : Serializable