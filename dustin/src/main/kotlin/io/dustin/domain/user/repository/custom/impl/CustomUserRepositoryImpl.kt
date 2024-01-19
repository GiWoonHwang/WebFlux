package io.dustin.domain.user.repository.custom.impl

import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.model.code.ReleasedType
import io.dustin.domain.user.model.entity.User
import io.dustin.domain.user.model.code.Job
import io.dustin.domain.user.repository.custom.CustomUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update
import org.springframework.data.relational.core.sql.SqlIdentifier
import java.time.LocalDateTime

class CustomUserRepositoryImpl(
    private val query: R2dbcEntityTemplate,
    ): CustomUserRepository {

        override suspend fun updateUser(user: User, assignments: MutableMap<SqlIdentifier, Any>): User {
            return query.update(User::class.java)
                .matching(query(where("id").`is`(user.id!!)))
                .apply(Update.from(assignments))
                .thenReturn(user)
                .awaitSingle()
        }

        override fun usersByQuery(match: Query): Flow<User> {
            return query.select(User::class.java)
                .matching(match)
                .flow()
        }

        override suspend fun totalCountByQuery(match: Query): Long {
            return query.select(User::class.java)
                .matching(match)
                .count()
                .awaitSingle()
        }

        override suspend fun userWithMugis(id: Long): User? {
            var sql = """
            SELECT user.id,
                   user.name,
                   user.job,
                   user.created_at,         
                   user.updated_at,         
                   mugi.id AS mugiId,
                   mugi.name as mugiName,
                   mugi.label,
                   mugi.released_type,
                   mugi.released_year,
                   mugi.format,
                   mugi.created_at AS rCreatedAt,
                   mugi.updated_at AS rUpdatedAt
            FROM user
            LEFT OUTER JOIN mugi ON user.id = mugi.user_id
            WHERE user.id = :id
        """.trimIndent()

            return query.databaseClient
                .sql(sql)
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged { it["id"] }
                .map { rows ->
                    val user = User(
                        id = rows[0]["id"]!! as Long,
                        name = rows[0]["name"]!! as String,
                        job = Job.valueOf(rows[0]["job"]!! as String),
                        createdAt = rows[0]["created_at"]?.let { it as LocalDateTime },
                        updatedAt = rows[0]["updated_at"]?.let { it as LocalDateTime },
                    )
                    val mugis = rows.map {
                        Mugi(
                            id = it["mugiId"]!! as Long,
                            userId = rows[0]["id"]!! as Long,
                            name = it["mugiName"]!! as String,
                            label = it["label"]!! as String,
                            releasedType = ReleasedType.valueOf(it["released_type"]!! as String),
                            releasedYear = it["released_year"]!! as Int,
                            format = it["format"]!! as String,
                            createdAt = it["rCreatedAt"]?.let { row -> row as LocalDateTime },
                            updatedAt = it["rUpdatedAt"]?.let { row -> row as LocalDateTime },
                        )
                    }
                    user.mugis = mugis
                    user
                }
                .awaitFirst()
        }

    }