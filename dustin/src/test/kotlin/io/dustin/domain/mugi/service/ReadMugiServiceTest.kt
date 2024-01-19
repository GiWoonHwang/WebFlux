package io.dustin.domain.mugi.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

@SpringBootTest
class ReadMugiServiceTest @Autowired constructor(
    private val read: ReadMugiService,
) {

    @Test
    @DisplayName("mugi by id test")
    fun mugiByIdTEST() = runTest {
        // given
        val id = 1L

        // when
        val mugi = read.mugiByIdOrThrow(id)

        // then
        assertThat(mugi.name).isEqualTo("test")
    }

    @Test
    @DisplayName("mugi by user id test")
    fun mugiByUserIdTEST() = runTest {
        // given
        val userId = 1L

        // when
        val titles = read.mugiByUserId(userId, PageRequest.of(0, 10))
            .toList()
            .map { it.name }

        // then
        assertThat(titles.size).isEqualTo(4)
        assertThat(titles[0]).isEqualTo("test")
    }

    @Test
    @DisplayName("mugi Count by user Count test")
    fun mugiByUserCountTEST() = runTest {
        // given
        //val id = 2L
        val userId = 1L

        // when
        val count = read.mugiCountByUser(userId)

        // then
        assertThat(count).isEqualTo(4)
    }

    @Test
    @DisplayName("allMugis test")
    fun allMugisTEST() = runTest {
        // given
        val whereClause = "AND mugi.user_id = 1"
        val orderClause = "ORDER BY mugi.released_year DESC"
        val limitClause = "LIMIT 10"

        // when
        val recordTitle = read.allMugis(whereClause, orderClause, limitClause)
            .toList()
            .map { it.name }
            .first()

        // then
        assertThat(recordTitle).isEqualTo("Highlander")
    }

    @Test
    @DisplayName("mugis test")
    fun mugisTEST() = runTest {
        // given

        // when
        val mugiName = read.mugis()
            .toList()
            .map { it.name }
            .first()
        // then
        assertThat(mugiName).isEqualTo("Highlander")
    }

}