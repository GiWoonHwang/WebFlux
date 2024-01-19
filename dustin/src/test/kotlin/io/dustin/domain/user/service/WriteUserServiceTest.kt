package io.dustin.domain.user.service

import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.common.transaction.Transaction
import io.dustin.domain.user.model.code.Job
import io.dustin.domain.user.model.entity.User
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WriteUserServiceTest @Autowired constructor(
    private val read: ReadUserService,
    private val write: WriteUserService,
) {

    @Test
    @DisplayName("user create test")
    fun createUserTEST() = runTest {
        // given
        val createdUser = User(name = "dustin", job = Job.DOJUK)

        // when
        val user = Transaction.withRollback(createdUser) {
            write.create(it)
        }

        // then
        assertThat(user.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("user update using builder test")
    fun updateUserTEST() = runTest {
        // given
        val id = 15L

        val command = UpdateUser(name = "서현식", job = "DOJUK")

        val target = read.userByIdOrThrow(1)

        val (user, assignments) = command.createAssignments(target)

        // when
        val update = Transaction.withRollback(id) {
            write.update(user, assignments)
            read.userById(id)!!
        }

        // then
        assertThat(update.job).isEqualTo(Job.DOJUK)
    }

}