package com.mle.seed.controller.data

import java.time.LocalDate
import java.util.UUID

data class OrderRequest(
    val customerName: String,
    val phone: String,
    val amount: Double,
    val unit: AmountUnit = AmountUnit.KG,
    val facebookProfile: String? = null,
    val pickUpDate: LocalDate
)

data class OrderResponse(
    val id: UUID
)

enum class AmountUnit {
    KG
}
