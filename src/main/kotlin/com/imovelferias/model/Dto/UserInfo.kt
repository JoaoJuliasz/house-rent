package com.imovelferias.model.Dto

import com.imovelferias.model.user.PaymentPlans
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class UserInfo(
    val _id: ObjectId,
    val id: Int,
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val paymentPlans: PaymentPlans? = null,
    val createDate: LocalDateTime,
    val updateDate: LocalDateTime
) {
}