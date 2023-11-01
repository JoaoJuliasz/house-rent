package com.imovelferias.repository

import com.imovelferias.model.announce.Announce
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface AnnounceRepository : ReactiveMongoRepository<Announce, ObjectId> {

    fun findFirstByOrderByIdDesc(): Mono<Announce>
}