package io.dustin.api.controller

import io.dustin.api.usercase.mugi.model.CreateMugi
import io.dustin.api.usercase.mugi.model.UpdateMugi
import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.model.code.MugiFormat
import io.dustin.domain.mugi.model.code.ReleasedType
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
class MugiControllerTest @Autowired constructor(
    private val webTestClient: WebTestClient,
) {

    @Test
    @DisplayName("create mugi test")
    fun createMugiTEST() {
        // given
        val formats = listOf(MugiFormat.GUM, MugiFormat.JIPANGI).joinToString(separator = ",") { it.name }
        val createdRecord = CreateMugi(
            userId = 1,
            name = "Highlander",
            label = "dustin",
            format = formats,
            releasedType = ReleasedType.JAK,
            releasedYear = 2020,
        )

        // when
        webTestClient.post()
            .uri("/api/v1/mugis")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(createdRecord), CreateMugi::class.java)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.name").isEqualTo(createdRecord.name)
    }

    @Test
    @DisplayName("mugi update test")
    fun updateMugiTEST() {
        // given
        val formats = listOf(MugiFormat.GUM, MugiFormat.JIPANGI, MugiFormat.HWAL).joinToString(separator = ",") { it.name }
        val id = 1
        val update = UpdateMugi(
            format = formats,
        )

        // when
        webTestClient.patch()
            .uri("/api/v1/mugis/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(update), UpdateMugi::class.java)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.format").isEqualTo(formats)
    }

    @Test
    @DisplayName("fetchMugi test")
    fun fetchMugiTEST() {
        // given
        val id = 1L

        // when
        webTestClient.get()
            .uri("/api/v1/mugis/$id")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            // then
            .jsonPath("$.name").isEqualTo("Bird At St. Nick's")

    }

    @Test
    @DisplayName("fetchMugiByUser test")
    fun fetchRecordByMusicianTEST() {

        val userId = 10

        // when
        webTestClient.get()
            .uri("/api/v1/mugis/user/$userId")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Mugi::class.java).hasSize(1)

    }

}