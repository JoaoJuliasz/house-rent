package com.imovelferias.model.user

import com.imovelferias.model.Dto.UserInfo
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
) {

    fun toUserInfo(): UserInfo {
        // Check if _id is null, otherwise throw an exception
        val userId = this._id!!
        return UserInfo(
            _id = userId,
            id = this.id,
            name = this.name,
            lastName = this.lastName,
            phoneNumber = this.phoneNumber,
            email = this.email,
            paymentPlans = this.paymentPlans,
            createDate = this.createDate,
            updateDate = this.updateDate
        )
    }
}

