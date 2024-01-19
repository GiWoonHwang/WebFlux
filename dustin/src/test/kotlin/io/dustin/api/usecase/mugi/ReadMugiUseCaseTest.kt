package io.dustin.api.usecase.mugi

import io.dustin.api.usercase.mugi.ReadMugiUseCase
import io.dustin.common.model.request.QueryPage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.LinkedMultiValueMap
@SpringBootTest
class ReadMugiUseCaseTest @Autowired constructor(
    private val readUseCase: ReadMugiUseCase,
) {

    @Test
    @DisplayName("mugi by id test")
    fun mugiByIdTEST() = runTest {
        // given
        val id = 1L

        // when
        val record = readUseCase.mugiById(id)

        // then
        assertThat(record.name).isEqualTo("test")
    }

    @Test
    @DisplayName("mugi by user id list test")
    fun mugiByUserIdTEST() = runTest {
        // given
        val userId = 1L
        val queryPage = QueryPage(size = 10, page = 1)

        // when
        val mugiName = readUseCase.mugiByUserId(queryPage, userId)
            .content
            .map { it.name }
            .first()

        // then
        assertThat(mugiName).isEqualTo("test")
    }
    @Test
    @DisplayName("allMugis test")
    fun allMugisTEST() = runTest {
        // given
        val queryPage = QueryPage(size = 10, page = 1, column = "releasedYear", sort = "desc")
        val matrixVariables = LinkedMultiValueMap<String, Any>()
        matrixVariables.put("userId", listOf("eq", 1))

        // when
        val recordTitle = readUseCase.allMugis(queryPage, matrixVariables)
            .toList()
            .map { it.name }
            .first()

        // then
        assertThat(recordTitle).isEqualTo("아케인셰이드 대거")
    }
}