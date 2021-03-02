package pl.pk.myUserApp.data

import java.io.Serializable
import java.math.BigDecimal

data class Transactions(
    var id: Long,
    var createdOn: String,
    var updatedOn: String,
    var version: Long,
    var issuedPoints: Int,
    var grantedPoints: Int,
    var totalPrice: BigDecimal,
    var discount: BigDecimal,
    var finalAmount: BigDecimal,
    var userId: String,
    var basket: List<BasketItem>,
    var trnCoupons: List<Coupons>,
    var trnType: String,
) : Serializable
