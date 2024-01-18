package io.dustin.domain.mugi.service

import io.dustin.common.exception.BadParameterException
import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.model.code.MugiFormat
import io.dustin.domain.mugi.model.code.ReleasedType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import reactor.test.StepVerifier

@SpringBootTest
@TestExecutionListeners(
    listeners = [TransactionalTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class WriteMugiServiceTest @Autowired constructor(
    private val read: ReadMugiService,
    private val write: WriteMugiService,
) {

    @Test
    @DisplayName("mugi create test")
    fun createMugiTEST() {
        // given
        val formats = listOf(MugiFormat.ADAE, MugiFormat.GUM).joinToString(separator = ",") { it.name }
        val createdMugi = Mugi(
            userId = 1,
            name = "Nows The Time",
            label = "Verve",
            format = formats,
            releasedType = ReleasedType.JAK,
            releasedYear = 1957,
        )

        // when
        val mono = write.create(createdMugi)

        // then
        mono.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it.id).isGreaterThan(0L)
            }
            .verifyComplete()
    }

    @Test
    @DisplayName("mugi update using builder test")
    fun updateMugiTEST() {
        // given
        val id = 1L
        val name = "Now's The Time"

        val target = read.mugiByIdOrThrow(1)
        //println("target   "+target)

        val assignments = mutableMapOf<SqlIdentifier, Any>()
        name?.let {
            assignments[SqlIdentifier.unquoted("name")] = it
        }
        if(assignments.isEmpty()) {
            throw BadParameterException("업데이트 정보가 누락되었습니다. [name, job] 정보를 확인하세요.")
        }

        // when
        val updated = target.flatMap {
            write.update(it, assignments)
        }.then(read.mugiById(1))

        //println("updated   "+updated)

        // then
        updated.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it.name).isEqualTo(name)
            }
            .verifyComplete()
    }

}