package com.polarboookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import kotlin.jvm.Throws

@JsonTest
class BookJsonTests {

    @Autowired
    private lateinit var json: JacksonTester<Book>

    @Test
    @Throws(Exception::class)
    fun `should test if the data is serialized`() {
        // given / when
        var book = Book("1234567890", "Title", "Author", 9.90)
        var jsonContent = json.write(book)

        // then
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn)
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title)
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price)
    }

    @Test
    @Throws(Exception::class)
    fun `should test if the data is deserialized`() {
        // given / when
        var content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90
            }
        """.trimIndent()

        // then
        assertThat(json.parse(content))
            .usingRecursiveComparison()
            .isEqualTo(Book("1234567890", "Title", "Author", 9.90))
    }
}