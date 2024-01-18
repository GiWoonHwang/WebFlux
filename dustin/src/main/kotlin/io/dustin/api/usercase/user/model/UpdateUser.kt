package io.dustin.api.usercase.user.model

import io.dustin.common.exception.BadParameterException
import io.dustin.domain.user.model.User
import io.dustin.domain.user.model.code.Job
import org.springframework.data.relational.core.sql.SqlIdentifier

data class UpdateUser(
    val name: String?,
    @field:EnumCheck(enumClazz = Job::class, permitNull = true, message = "job 필드는 DOJUK, JUNSA, GUNGSU, MABUPSA 만 가능합니다.")
    val job: String?,
) {

    fun createAssignments(user: User): Pair<User, MutableMap<SqlIdentifier, Any>> {
        val assignments = mutableMapOf<SqlIdentifier, Any>()
        name?.let {
            assignments[SqlIdentifier.unquoted("name")] = it
            user.name = it
        }
        job?.let {
            assignments[SqlIdentifier.unquoted("job")] = it
            user.job = it
        }
        if(assignments.isEmpty()) {
            throw BadParameterException("업데이트 정보가 누락되었습니다. [name, job] 정보를 확인하세요.")
        }
        return user to assignments
    }
}