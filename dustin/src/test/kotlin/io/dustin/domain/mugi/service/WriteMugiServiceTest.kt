package io.dustin.domain.mugi.service

import io.dustin.common.exception.BadParameterException
import io.dustin.domain.mugi.model.code.MugiFormat
import io.dustin.domain.mugi.model.code.ReleasedType
import io.dustin.domain.mugi.model.entity.Mugi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TransactionalTestExecutionListener



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
    fun createMugiTEST() = runTest {
        // given
        val formats = listOf(MugiFormat.GUM, MugiFormat.ADAE).joinToString(separator = ",") { it.name }
        val createdMugi = Mugi(
            userId = 1,
            name = "아케인셰이드 대거",
            label = "황기훈님의",
            format = formats,
            releasedType = ReleasedType.GUMAE,
            releasedYear = 2022,
        )

        // when
        val created = write.create(createdMugi)

        // then
        assertThat(created.id).isGreaterThan(0L)
    }

    @Test
    @DisplayName("mugi update using builder test")
    fun updateMugiTEST() = runTest {
        // given
        val target = read.mugiByIdOrThrow(1)

        val id = target.id!!
        val name = "test"

        val assignments = mutableMapOf<SqlIdentifier, Any>()
        name?.let {
            assignments[SqlIdentifier.unquoted("name")] = it
        }
        if(assignments.isEmpty()) {
            throw BadParameterException("업데이트 정보가 누락되었습니다. [name, genre] 정보를 확인하세요.")
        }

        // when
        write.update(target, assignments)
        val updated = read.mugiById(target.id!!)!!

        // then
        assertThat(updated.name).isEqualTo(name)
    }

}