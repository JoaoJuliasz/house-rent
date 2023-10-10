package com.imovelferias.model.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("user")
data class User(
    @Id
    val _id: ObjectId? = null,
    val id: Int,
    val name: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val email: String,
    val paymentPlans: PaymentPlans? = null,
    val createDate: LocalDateTime,
    val updateDate: LocalDateTime
) {}

