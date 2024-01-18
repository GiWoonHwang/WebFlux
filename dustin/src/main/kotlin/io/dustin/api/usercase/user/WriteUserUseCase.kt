package io.dustin.api.usercase.user

import io.dustin.api.usercase.user.model.CreateUser
import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.domain.user.model.code.Job
import io.dustin.domain.user.model.entity.User
import io.dustin.domain.user.service.ReadUserService
import io.dustin.domain.user.service.WriteUserService
import org.springframework.stereotype.Service

@Service
class WriteUserUseCase(
    private val read: ReadUserService,
    private val write: WriteUserService,
) {

    suspend fun insert(command: CreateUser): User {
        val created = User(name = command.name, job = Job.valueOf(command.job))
        return write.create(created)
    }

    suspend fun update(id: Long, command: UpdateUser): User {
        val selected = read.userByIdOrThrow(id)
        val (musician, assignments) = command.createAssignments(selected)
        write.update(musician, assignments)
        return read.userById(id)!!
    }

}