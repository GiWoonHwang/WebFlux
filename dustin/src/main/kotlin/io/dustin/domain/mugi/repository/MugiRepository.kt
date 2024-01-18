package io.dustin.domain.mugi.repository

import io.dustin.common.repository.BaseRepository
import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.repository.custom.CustomMugiRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Mono

interface MugiRepository: BaseRepository<Mugi, Long>, CustomMugiRepository {
    override suspend fun findById(id: Long): Mugi?
    fun findByUserId(id: Long, pageable: Pageable): Flow<Mugi>
    @Query("SELECT COUNT(id) FROM mugi WHERE user_id = :userId")
    suspend fun countByUserId(@Param("userId") userId: Long): Long

    @Query("""
            SELECT user.name AS userName,
                   user.job,
                   user.created_at AS mCreatedAt,
                   user.updated_at AS mUpdatedAt,
                   mugi.*
              FROM mugi
              INNER JOIN user
              ON mugi.user_id = user_id
        """)
    fun findMugis(): Flow<Mugi>

}