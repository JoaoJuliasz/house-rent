package com.imovelferias.repository

import com.imovelferias.model.user.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveMongoRepository<User, ObjectId> {

    fun findFirstByOrderByIdDesc(): Mono<User>

    @Query("{ 'email': ?0 }")
    fun findUserByEmail(email: String): Mono<User>
}