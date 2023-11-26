package io.dustin.domain.user.service

import io.dustin.DustinApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest(classes = [DustinApplication::class])
class ReadUserServiceTest @Autowired constructor(
    private val read: ReadUserService,
) {

    @Test
    @DisplayName("fetch user by id")
    fun userByIdTEST() {
        // given
        val id = 15L

        // when
        val selected = read.userById(id)

        // then
        selected.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it.name).isEqualTo("서현식")
            }
            .verifyComplete()
    }

    @Test
    @DisplayName("fetch user by id or throw")
    fun userByIdOrThrowTEST() {
        // given
        //val id = 1L
        val id = 15L

        // when
        val selected = read.userByIdOrThrow(id)

        // then
        selected.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it.name).isEqualTo("서현식")
            }
            .verifyComplete()
    }

    @Test
    @DisplayName("total user count test")
    fun totalCountTEST() {
        // when
        val count: Mono<Long> = read.totalCount()

        // then
        count.`as`(StepVerifier::create)
            .assertNext {
                assertThat(it).isEqualTo(4)
            }
            .verifyComplete()
    }


    @Test
    @DisplayName("users list by query test")
    fun usersByQueryTEST() {

        val list = emptyList<Criteria>()

        // given
        val match = query(Criteria.from(list))

        // when
        val users: Flux<String> = read.usersByQuery(
            // page 0, size 2
            match.limit(2)
                .offset(0)
        )
            .map {
                it.name
            }

        // then
        users.`as`(StepVerifier::create)
            .expectNext("한동근")
                .expectNext("슈퍼서현식")
            .verifyComplete()

    }

    @Test
    @DisplayName("total user count by query test")
    fun totalCountByQueryTEST() {
        // given
        val match = query(where("name").like("%식"))

        // when
        val count: Mono<Long> = read.totalCountByQuery(match)

        // then
        count.`as`(StepVerifier::create)
            .assertNext {
                // 현재 1개의 row가 있다.
                assertThat(it).isEqualTo(2)
            }
            .verifyComplete()
    }

}