package io.dustin.domain.mugi.service

import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.repository.MugiRepository
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Service

@Service
class WriteMugiService(
    private val mugiRepository: MugiRepository,
) {
    suspend fun create(mugi: Mugi) = mugiRepository.save(mugi)
    suspend fun update(mugi: Mugi, assignments: MutableMap<SqlIdentifier, Any>) = mugiRepository.updateMugi(mugi, assignments)
}