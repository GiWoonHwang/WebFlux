package io.dustin.api.usercase.user.model

import io.dustin.common.constraint.EnumCheck
import io.dustin.domain.user.model.code.Job
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUser(
    @field:NotNull
    @field:NotBlank(message = "뮤지션의 이름에 빈 공백은 허용하지 않습니다.")
    val name: String,

    @field:NotNull(message = "직업 정보가 누락 되었습니다.")
    @field:EnumCheck(enumClazz = Job::class, permitNull = true, message = "job 필드는 DOJUK, JUNSA, GUNGSU, MABUPSA 만 가능합니다.")
    val job: String,
)