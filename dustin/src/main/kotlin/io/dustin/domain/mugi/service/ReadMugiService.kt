package io.dustin.domain.mugi.service

import io.dustin.common.extensions.findByIdOrThrow
import io.dustin.domain.mugi.repository.MugiRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReadMugiService(
    private val mugiRepository: MugiRepository,
) {
    suspend fun mugiById(id: Long) = mugiRepository.findById(id)
    suspend fun mugiByIdOrThrow(id: Long, message: String? = null) = mugiRepository.findByIdOrThrow(id, message)
    suspend fun mugiCountByUser(userId: Long) = mugiRepository.countByUserId(userId)

    fun mugiByUserId(userId: Long, pageable: Pageable) = mugiRepository.findByUserId(userId, pageable)
    fun allMugis(whereClause: String = "",
                   orderClause: String = "",
                   limitClause: String = "") = mugiRepository.findAllMugis(whereClause, orderClause, limitClause)
    fun mugis() = mugiRepository.findMugis()


}