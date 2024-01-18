package io.dustin.api.usercase.mugi

import io.dustin.api.usercase.mugi.model.CreateMugi
import io.dustin.api.usercase.mugi.model.UpdateMugi
import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.model.code.ReleasedType
import io.dustin.domain.mugi.service.ReadMugiService
import io.dustin.domain.mugi.service.WriteMugiService
import io.dustin.domain.user.service.ReadUserService
import org.springframework.stereotype.Service

@Service
class WriteMugiUseCase(
    private val readUser: ReadUserService,
    private val read: ReadMugiService,
    private val write: WriteMugiService,
) {

    suspend fun insert(command: CreateMugi): Mugi {
        val user = readUser.userByIdOrThrow(command.userId, "해당 무기의 유저 정보가 조회되지 않습니다.유저 아이디를 확인하세요.")
        val created = Mugi(
            userId = user.id!!,
            name = command.name,
            label = command.label,
            releasedType = ReleasedType.valueOf(command.releasedType),
            releasedYear = command.releasedYear,
            format = command.format,
        )
        return write.create(created)
    }

    suspend fun update(id: Long, command: UpdateMugi): Mugi {
        val target = read.mugiByIdOrThrow(id)
        val (mugi, assignments) = command.createAssignments(target)
        write.update(mugi, assignments)
        return read.mugiById(id)!!
    }

}