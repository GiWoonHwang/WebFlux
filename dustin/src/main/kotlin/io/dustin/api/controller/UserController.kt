package io.dustin.api.controller

import io.dustin.api.usercase.user.model.CreateUser
import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.api.usercase.user.ReadUserUseCase
import io.dustin.api.usercase.user.WriteUserUseCase
import io.dustin.common.model.request.QueryPage
import io.dustin.domain.user.model.User
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val readUserUseCase: ReadUserUseCase,
    private val writeUserUseCase: WriteUserUseCase,
) {

    @GetMapping("/query/{queryCondition}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun fetchUsers(
        @Valid queryPage: QueryPage,
        @MatrixVariable(pathVar = "queryCondition", required = false) matrixVariable: MultiValueMap<String, Any>
    ): Page<User> {
        return readUserUseCase.usersByQuery(queryPage, matrixVariable)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun fetchUsers(@PathVariable("id") id: Long): User {
        return readUserUseCase.userById(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createUser(@RequestBody @Valid command: CreateUser): User {
        return writeUserUseCase.insert(command)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateUser(@PathVariable("id") id: Long, @RequestBody @Valid command: UpdateUser): User {
        return writeUserUseCase.update(id, command)
    }

}