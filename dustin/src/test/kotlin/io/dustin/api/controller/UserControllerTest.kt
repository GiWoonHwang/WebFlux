package io.dustin.api.controller

import io.dustin.api.usercase.user.model.CreateUser
import io.dustin.api.usercase.user.model.UpdateUser
import io.dustin.domain.user.model.code.Job
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerTest @Autowired constructor(
    private val webTestClient: WebTestClient,
) {

    // 이거 안됨 ,,,
    @Test
    @DisplayName("user create test")
    fun createUserTEST() {
        // given
        val createUser = CreateUser(name = "dustin", job = "JUNSA")

        // when
        webTestClient.post()
            .uri("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(createUser), CreateUser::class.java)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.name").isEqualTo("dustin")
    }

    @Test
    @DisplayName("user update test")
    fun updateUserTEST() {
        // given
        val update = UpdateUser(job = "DOJUK")

        // when
        webTestClient.patch()
            .uri("/api/v1/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(update), UpdateUser::class.java)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.job").isEqualTo("DOJUK")
    }

    @Test
    @DisplayName("fetchUser test")
    fun fetchUserTEST() {
        // given
        val id = 1L

        // when
        webTestClient.get()
            .uri("/api/v1/users/$id")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.name").isEqualTo("한동근")

    }

    @Test
    @DisplayName("fetchUsers adjust Matrix Variable test")
    fun fetchUsersTEST() {

        // given
        val page = 1
        val size = 10

        // when
//        webTestClient.get()
//            .uri("/api/v1/users/query/search;name=like,lest?page=$page&size=$size")
//            .accept(MediaType.APPLICATION_JSON)
//            .exchange()
//            .expectStatus().isOk
//            .expectHeader().contentType(MediaType.APPLICATION_JSON)
//            .expectBody()
//            // then
//            .jsonPath("$.content[0].name").isEqualTo("한동근")

        // when
        val responseSpec = webTestClient.get()
            .uri("/api/v1/users/query/search;name=like,근?page=$page&size=$size")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()

        // then
        val responseBody = responseSpec.returnResult().responseBody
        println("Response Body: ${String(responseBody!!)}")

        responseSpec.jsonPath("$.content[0].name").isEqualTo("한동근")

    }

}