package com.imovelferias.`interface`

import com.imovelferias.model.user.User

interface JwtGeneratorInterface {

    fun generateToken(user: User): Map<String, String>

}