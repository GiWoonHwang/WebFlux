package io.dustin.api.router.user.model

import io.dustin.common.constraint.EnumCheck
import io.dustin.common.exception.BadParameterException
import io.dustin.domain.user.model.User
import io.dustin.domain.user.model.code.Job
import isParamBlankThrow
import org.springframework.data.relational.core.sql.SqlIdentifier

data class UpdateUser(
    val name: String? = null,
    @field:EnumCheck(enumClazz = Job::class, permitNull = true, message = "job 필드는 Junsa, Dojuk, Mabupsa, Gungsu, CLASSIC 만 가능합니다.")
    val job: String? = null,
) {
    fun createAssignments(user: User): Pair<User, MutableMap<SqlIdentifier, Any>> {
        val assignments = mutableMapOf<SqlIdentifier, Any>()
        name?.let {
            isParamBlankThrow(it)
            assignments[SqlIdentifier.unquoted("name")] = it
            user.name = it
        }
        job?.let {
            assignments[SqlIdentifier.unquoted("job")] = it
            user.job = Job.valueOf(it.uppercase())
        }
        if(assignments.isEmpty()) {
            throw BadParameterException("업데이트 정보가 누락되었습니다. [name, genre] 정보를 확인하세요.")
        }
        return user to assignments
    }
}

