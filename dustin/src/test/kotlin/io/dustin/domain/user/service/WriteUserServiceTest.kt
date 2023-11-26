package io.dustin.domain.user.service

import io.dustin.DustinApplication
import io.dustin.api.router.user.model.UpdateUser
import io.dustin.common.transaction.Transaction
import io.dustin.domain.user.model.User
import io.dustin.domain.user.model.code.Job
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import reactor.test.StepVerifier

//@SpringBootTest
@SpringBootTest(classes = [DustinApplication::class])
@TestExecutionListeners(
    listeners = [TransactionalTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class WriteUserServiceTest @Autowired constructor(
        private val read: ReadUserService,
        private val write: WriteUserService,
) {

    @Test
    @DisplayName("user create test")
    fun createUserTEST() {
        // given
        val createdUser = User(name = "서현식", job = Job.DOJUK)

        // when
        val mono = write.create(createdUser)

        // then
        mono.`as`(StepVerifier::create)
                .assertNext {
                    assertThat(it.id).isGreaterThan(0L)
                }
                .verifyComplete()
    }


    @Test
    @DisplayName("user update using builder test")
    fun updateUserTEST() {
        // given
        val id = 1L

        val command = UpdateUser(name = "서현식", job = Job.DOJUK.name)

        val target = read.userByIdOrThrow(id)

        // when
        val updated = target.flatMap {
            val (user, assignments) = command.createAssignments(it)
            write.update(user, assignments)
        }.then(read.userById(15))

        // then
        updated.`as`(StepVerifier::create)
                .assertNext {
                    assertThat(it.job).isEqualTo(Job.DOJUK)
                }
                .verifyComplete()
    }

}