package pl.pk.myUserApp.data

import java.io.Serializable

data class AboutMe(
    var email:String,
    var username:String,
    var using2FA: Boolean,
    var points: AboutMePoints,
    var cards: AboutMeCards
) : Serializable