package io.dustin.api.usecase.user

import io.dustin.api.usercase.user.WriteUserUseCase
import io.dustin.api.usercase.user.model.CreateUser
import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.domain.user.model.code.Job
import io.dustin.domain.user.service.ReadUserService
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TransactionalTestExecutionListener


@SpringBootTest
@TestExecutionListeners(
    listeners = [TransactionalTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class WriteUserUseCaseTest @Autowired constructor(
    private val writeUseCase: WriteUserUseCase,
    private val readUserService: ReadUserService,
) {

    @Test
    @DisplayName("user insert useCase test")
    fun insertUseCaseTEST() = runTest {
        // given
        val command = CreateUser(name = "설인", job = Job.ETC.name)

        // when
        val user = writeUseCase.insert(command)

        // then
        assertThat(user.job).isEqualTo(Job.ETC)
    }

    @Test
    @DisplayName("user update useCase test")
    fun updateUseCaseTEST() = runTest {
        // given
        val id = 1L
        val command = UpdateUser(job = Job.DOJUK.name)

        // when
        writeUseCase.update(id, command)

        // then
        val update = readUserService.userById(id)!!
        assertThat(update.job).isEqualTo(Job.DOJUK)
    }

}