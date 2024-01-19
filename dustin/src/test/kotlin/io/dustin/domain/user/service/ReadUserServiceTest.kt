package io.dustin.domain.user.service

import io.dustin.common.utils.notFound
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.isEqual

@SpringBootTest
class ReadUserServiceTest @Autowired constructor(
    private val read: ReadUserService,
) {

    @Test
    @DisplayName("fetch user by id")
    fun userByIdTEST() = runTest{
        // given
        val id = 1L

        // when
        val selected = read.userById(id)

        // then
        assertThat(selected!!.name).isEqualTo("한동근")
    }

    @Test
    @DisplayName("fetch user by id or throw")
    fun userByIdOrThrowTEST() = runTest{
        // given
        val id = 1L
//        val id = 1111L

        // when
        val selected = read.userByIdOrThrow(id)

        // then
        assertThat(selected!!.name).isEqualTo("한동근")

    }

    @Test
    @DisplayName("fetch users pagination")
    fun usersTEST() = runTest{
        // given
        val pageable = PageRequest.of(0, 3)

        // when
        val musicians = read.users(pageable)
            .toList()
            .map { it.name }
        // then
        assertThat(musicians.size).isEqualTo(3)
        assertThat(musicians[0]).isEqualTo("한동근")
    }

    @Test
    @DisplayName("total user count test")
    fun totalCountTEST() = runTest{
        // when
        val count = read.totalCount()

        // then
        assertThat(count).isEqualTo(4)
    }

    @Test
    @DisplayName("users list by query test")
    fun usersByQueryTEST() = runTest{

        val list = emptyList<Criteria>()

        // given
        val match = query(Criteria.from(list)).limit(2).offset(0)

        // when
        val musicians: List<String> = read.usersByQuery(match)
            .toList()
            .map { it.name }

        // then
        assertThat(musicians.size).isEqualTo(2)

    }

    @Test
    @DisplayName("total user count by query test")
    fun totalCountByQueryTEST() = runTest{
        // given
        val match = query(where("job").isEqual("DOJUK"))

        // when
        val count = read.totalCountByQuery(match)

        // then
        assertThat(count).isEqualTo(3)
    }

    @Test
    @DisplayName("user with mugis test")
    fun userWithMugisTEST() = runTest{
        // given
        val id = 1L

        // when
        val user = read.userWithMugis(id) ?: notFound()

        // then
        assertThat(user.name).isEqualTo("한동근")
        assertThat(user.mugis!!.size).isEqualTo(4)

    }

}