package pl.pk.myUserApp.data;

import java.io.Serializable

data class RegistrationBody(
        var email:String,
        var username:String,
        var password: String
) : Serializable

