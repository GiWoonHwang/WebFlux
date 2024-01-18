package io.dustin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class DustinApplication
fun main(args: Array<String>) {
	runApplication<DustinApplication>(*args)
}
