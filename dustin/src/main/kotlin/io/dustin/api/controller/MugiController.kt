package io.dustin.api.controller

import io.dustin.api.usercase.mugi.ReadMugiUseCase
import io.dustin.api.usercase.mugi.WriteMugiUseCase
import io.dustin.api.usercase.mugi.model.CreateMugi
import io.dustin.api.usercase.mugi.model.UpdateMugi
import io.dustin.common.model.request.QueryPage
import io.dustin.domain.mugi.model.Mugi
import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/mugis")
class MugiController(
    private val readMugiUseCase: ReadMugiUseCase,
    private val writeMugiUseCase: WriteMugiUseCase,
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun fetchMugi(@PathVariable("id") id: Long): Mugi {
        return readMugiUseCase.mugiById(id)
    }

    @GetMapping("/query/{queryCondition}")
    @ResponseStatus(HttpStatus.OK)
    fun fetchAllMugis(
        @Valid queryPage: QueryPage,
        @MatrixVariable(pathVar = "queryCondition", required = false) matrixVariable: MultiValueMap<String, Any>
    ): Flow<Mugi> {
        return readMugiUseCase.allMugis(queryPage, matrixVariable)
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun fetchMugiByUser(@Valid queryPage: QueryPage, @PathVariable("userId") userId: Long): Page<Mugi> {
        return readMugiUseCase.mugiByUserId(queryPage, userId)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createMugi(@RequestBody @Valid command: CreateMugi): Mugi {
        return writeMugiUseCase.insert(command)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateMugi(@PathVariable("id") id: Long, @RequestBody @Valid command: UpdateMugi): Mugi {
        return writeMugiUseCase.update(id, command)
    }

}