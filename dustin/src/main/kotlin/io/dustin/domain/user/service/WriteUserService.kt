package io.dustin.domain.user.service

import io.dustin.domain.user.model.entity.User
import io.dustin.domain.user.repository.UserRepository
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Service

@Service
class WriteUserService(
    private val userRepository: UserRepository
) {

    suspend fun create(user: User): User {
        return userRepository.save(user)
    }
    suspend fun update(user: User, assignments: MutableMap<SqlIdentifier, Any>): User {
        return userRepository.updateUser(user, assignments)
    }
}