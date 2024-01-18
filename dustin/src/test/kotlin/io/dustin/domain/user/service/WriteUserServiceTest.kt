package io.dustin.domain.user.service

import io.dustin.DustinApplication
import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.domain.user.model.entity.User
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
        val createdUser = User(name = "dustin2", job = Job.Dojuk)

        // when
        val mono = write.create(createdUser)

        // then
//        mono.`as`(Transaction::withRollback)
//            .`as`(StepVerifier::create)
//            .assertNext {
//                assertThat(it.id).isGreaterThan(0L)
//            }
//            .verifyComplete()
    }

    /**
     *
     */
    @Test
    @DisplayName("user update using builder test")
    fun updateTemplateUserTEST() {
        // given
        val id = 3L

        val command = UpdateUser(name = "dustin hwang ", job = Job.Junsa)
        //val command = UpdateUser(null, null)

        val target = read.userByIdOrThrow(3)

        // when
        val updated = target.flatMap {
            val (user, assignments) = command.createAssignments(it)
            write.update(user, assignments)
        }.then(read.userById(3))

        // then
        updated.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it.job).isEqualTo(Job.Junsa)
            }
            .verifyComplete()
    }
}