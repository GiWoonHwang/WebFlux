package io.dustin.api.usercase.user

import io.dustin.common.builder.createQuery
import io.dustin.common.model.request.QueryPage
import io.dustin.domain.user.model.User
import io.dustin.domain.user.service.ReadUserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Mono

@Service
class ReadUserUseCase(
    private val read: ReadUserService,
) {
    suspend fun userById(id: Long): User {
        return read.userByIdOrThrow(id)
    }

    suspend fun usersByQuery(queryPage: QueryPage, matrixVariable: MultiValueMap<String, Any>): Page<User> {
        val match = createQuery(matrixVariable)
        return read.usersByQuery(queryPage.pagination(match))
            .toList()
            .countZipWith(read.totalCountByQuery(match))
            .map { ( users, count) -> PageImpl(users.toList(), queryPage.fromPageable(), count)}
    }

}