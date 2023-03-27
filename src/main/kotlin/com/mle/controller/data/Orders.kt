package com.mle.controller.data

import java.time.LocalDate
import java.util.UUID

data class OrderRequest(
    val name: String,
    val phone: String,
    val amount: Double,
    val unit: Unit = Unit.KG,
    val facebookUrl: String? = null,
    val pickUpDate: LocalDate
)

data class OrderResponse(
    val id: UUID
)

enum class Unit {
    KG
}
