package com.polarboookshop.catalogservice

import com.polarboookshop.catalogservice.domain.Book
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Autowired
	private lateinit var webTestClient: WebTestClient

	@Test
	fun `when post request then book created`() {
		val expectedBook = Book("1231231231", "Title", "Author", 9.90, "Publisher")
		webTestClient.post()
			.uri("/books")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(expectedBook), Book::class.java)
			.exchange()
			.expectStatus().isCreated
			.expectBody(Book::class.java).consumeWith { response ->
				val actualBook = response.responseBody
				if (actualBook != null) {
					assertEquals(expectedBook.isbn, actualBook.isbn)
				}
			}
	}
}
