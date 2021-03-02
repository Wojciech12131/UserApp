package pl.pk.myUserApp.data

import java.io.Serializable

data class CouponDetails(
    var id: Long,
    var createdOn: String,
    var updatedOn: String,
    var version: Long,
    var code: String,
    var couponType: CouponType,
    var status: String,
    var description: String,
    var useLimit: Int,
    var useTime: Int,
    var userId: Long

) : Serializable
