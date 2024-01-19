package io.dustin.api.usecase.user

import io.dustin.api.usercase.user.ReadUserUseCase
import io.dustin.common.model.request.QueryPage
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.LinkedMultiValueMap


@SpringBootTest
class ReadUserUseCaseTest @Autowired constructor(
    private val readUseCase: ReadUserUseCase,
) {

    @Test
    @DisplayName("userById test")
    fun userByIdTEST() = runTest{
        // given
        val id = 1L

        // when
        val user = readUseCase.userById(id)

        // then
        assertThat(user.name).isEqualTo("한동근")
    }

    @Test
    @DisplayName("users By Query test")
    fun usersByQueryTEST() = runTest{
        // given
        val query = QueryPage(1, 10, column = "id", sort = "DESC")
        val matrixVariable = LinkedMultiValueMap<String, Any>()
        matrixVariable.put("job", listOf("eq", "DOJUK"))

        // when
        val userName = readUseCase.usersByQuery(query, matrixVariable)
            .content
            .map { it.name }
        // then
        assertThat(userName.size).isEqualTo(3)
        assertThat(userName[0]).isEqualTo("서현식")

    }

}