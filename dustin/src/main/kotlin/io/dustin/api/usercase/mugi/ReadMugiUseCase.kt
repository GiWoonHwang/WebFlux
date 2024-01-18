package io.dustin.api.usercase.mugi

import io.dustin.common.builder.createNativeSortLimitClause
import io.dustin.common.builder.createNativeWhereClause
import io.dustin.common.model.request.QueryPage
import io.dustin.domain.mugi.model.Mugi
import io.dustin.domain.mugi.service.ReadMugiService
import io.dustin.domain.user.service.ReadUserService
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReadMugiUseCase(
    private val read: ReadMugiService,
    private val readUser: ReadUserService,
) {

    suspend fun mugiById(id: Long): Mugi {
        return read.mugiByIdOrThrow(id)
    }

    suspend fun mugiByUserId(queryPage: QueryPage, userId: Long): Page<Mugi> {
        val user = readUser.userByIdOrThrow(userId)
        return read.mugiByUserId(user.id!!, queryPage.fromPageable())
            .toList()
            .countZipWith(read.mugiCountByUSer(userId))
            .map { (records, count) -> PageImpl(records.toList(), queryPage.fromPageable(), count) }
    }

    fun allMugis(queryPage: QueryPage, matrixVariable: MultiValueMap<String, Any>): Flow<Mugi> {
        val prefix = "mugi"
        val clazz = Mugi::class
        val whereClause = createNativeWhereClause(prefix, clazz, matrixVariable)
        val (orderSql, limitSql) = createNativeSortLimitClause(prefix, clazz, queryPage)
        return read.allMugis(whereClause = whereClause, orderClause = orderSql, limitClause = limitSql)
    }

}