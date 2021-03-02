package pl.pk.myUserApp.data

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class BasketItem(
    var id: Long,
    var createdOn: String,
    var updatedOn: String,
    var version: Long,
    var code: String,
    var name: String,
    var description: String,
    var quantity: BigDecimal,
    var price: BigDecimal
) : Serializable
