package pl.pk.myUserApp.data

import java.io.Serializable

data class Coupons(
    var id: Long,
    var createdOn: String,
    var updatedOn: String,
    var version: Long,
    var couponCode: String,
    var couponId: String,
    var useTime: Int,
    var action: String,
    var details: String
) : Serializable
