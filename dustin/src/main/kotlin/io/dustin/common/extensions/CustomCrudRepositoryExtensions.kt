package io.dustin.common.extensions

import io.dustin.common.utils.notFound
import org.springframework.data.repository.kotlin.CoroutineCrudRepository



suspend fun <T, ID> CoroutineCrudRepository<T, ID>.findByIdOrThrow(id: ID, message: String? = null): T {
    return this.findById(id) ?: notFound(message)
}