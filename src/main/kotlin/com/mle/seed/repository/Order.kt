package com.mle.seed.repository

import com.mle.seed.controller.data.AmountUnit
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class Order(
    val orderUid: UUID,
    val customerName: String,
    val phoneNumber: String,
    val facebookProfile: String?,
    val unit: AmountUnit,
    val amount: Double,
    val pickUpDate: LocalDate,
    val createdDateTime: Instant
)
