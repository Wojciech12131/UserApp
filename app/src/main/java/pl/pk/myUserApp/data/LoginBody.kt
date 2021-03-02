package pl.pk.myUserApp.data

import java.io.Serializable

data class LoginBody(
    var username: String,
    var password: String,
    var code: String?
) : Serializable