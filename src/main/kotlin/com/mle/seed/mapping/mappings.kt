package com.mle.seed.mapping

import com.mle.seed.controller.data.OrderRequest
import com.mle.seed.repository.Order
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

fun OrderRequest.toOrder() = Order(
    orderUid = UUID.randomUUID(),
    customerName = customerName,
    phoneNumber = phone,
    facebookProfile = facebookProfile,
    unit = unit,
    amount = amount,
    pickUpDate = pickUpDate,
    createdDateTime = Instant.now()
)

fun Order.toMapAttrValue() = mapOf(
    "order_uid" to orderUid.toAttrValue(),
    "customer_name" to customerName.toAttrValue(),
    "phone_number" to phoneNumber.toAttrValue(),
    "facebook_profile" to facebookProfile.toAttrValue(),
    "unit" to unit.name.toAttrValue(),
    "amount" to amount.toAttrValue(),
    "date_pick_up" to pickUpDate.toAttrValue(),
    "create_date_time" to createdDateTime.toString().toAttrValue()
)

fun UUID.toAttrValue(): AttributeValue = AttributeValue.builder().s(this.toString()).build()
fun String?.toAttrValue(): AttributeValue = AttributeValue.builder().s(this ?: "").build()
fun Double.toAttrValue(): AttributeValue = AttributeValue.builder().n(this.toString()).build()
fun LocalDate.toAttrValue(): AttributeValue = AttributeValue.builder().s(this.format(DateTimeFormatter.ISO_DATE)).build()
