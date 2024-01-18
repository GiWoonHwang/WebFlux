package io.dustin.domain.user.repository.custom

import io.dustin.domain.user.model.entity.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.sql.SqlIdentifier

interface CustomUserRepository {
    suspend fun updateUser(user: User, assignments: MutableMap<SqlIdentifier, Any>): User
    fun usersByQuery(match: Query): Flow<User>
    suspend fun totalCountByQuery(match: Query): Long
    suspend fun userWithMugis(id: Long): User?
}