package io.dustin.api.usercase.mugi.model

import io.dustin.common.constraint.EnumCheck
import io.dustin.domain.mugi.model.code.ReleasedType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateMugi(
    @field:Min(1, message = "유저 아이디가 누락되었습니다.")
    var userId: Long,

    @field:NotNull
    @field:Size(min = 1, message = "무기의 이름이 누락되었습니다. 최소 한 글자 이상이어야 합니다.")
    val name: String,

    @field:NotNull(message = "무기의 각인 정보가 누락되었습니다.")
    var label: String,

    @field:NotNull(message = "무기 종류 정보가 누락되었습니다.")
    @field:EnumCheck(enumClazz = ReleasedType::class, message = "releasedType 필드는 SINGLE, FULL, EP, OST, COMPILATION, LIVE, MIXTAPE 만 가능합니다.")
    var releasedType: String,

    @field:Min(0, message = "무기 제작일 누락되었습니다.")
    var releasedYear: Int,

    @field:NotNull(message = "무기 포맷 형식이 누락되었습니다.")
    var format: String,
)