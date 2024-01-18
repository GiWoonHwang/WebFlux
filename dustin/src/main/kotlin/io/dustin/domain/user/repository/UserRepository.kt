package io.dustin.domain.user.repository

import io.dustin.common.repository.BaseRepository
import io.dustin.domain.user.model.entity.User
import io.dustin.domain.user.repository.custom.CustomUserRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable


interface UserRepository: BaseRepository<User, Long>, CustomUserRepository {
    override suspend fun findById(id: Long): User?
    fun findAllBy(pageable: Pageable): Flow<User>
}