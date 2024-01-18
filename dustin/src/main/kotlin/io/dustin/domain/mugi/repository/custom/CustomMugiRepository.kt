package io.dustin.domain.mugi.repository.custom

import io.dustin.domain.mugi.model.entity.Mugi
import kotlinx.coroutines.flow.Flow
import org.springframework.data.relational.core.sql.SqlIdentifier

interface CustomMugiRepository {
    suspend fun updateMugi(mugi: Mugi, assignments: MutableMap<SqlIdentifier, Any>): Mugi
    fun findAllMugis(whereClause: String = "", orderClause: String = "", limitClause: String = ""): Flow<Mugi>


}