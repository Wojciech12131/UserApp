package pl.pk.myUserApp.data

import java.io.Serializable

data class CouponType(
    var id: Long,
    var createdOn: String,
    var updatedOn: String,
    var version: Long,
    var code: String,
    var activeFrom: String,
    var activeTo: String,
    var useLimit: Int,
    var description: String,
    var prefix: String,
    var suffix: String,
    var firstNumber: Long,
    var lastNumber: Long
) : Serializable