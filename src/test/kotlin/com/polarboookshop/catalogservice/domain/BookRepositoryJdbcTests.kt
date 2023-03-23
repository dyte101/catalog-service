package com.polarboookshop.catalogservice.domain

import com.polarboookshop.catalogservice.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.Optional
import java.util.stream.StreamSupport

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var jdbcAggregateTemplate: JdbcAggregateTemplate

    @Test
    fun findAllBooks() {
        var book1 = Book("1234561235", "Title", "Author", 12.90)
        var book2 = Book("1234561236", "Another Title", "Author", 12.90)
        jdbcAggregateTemplate.insert(book1)
        jdbcAggregateTemplate.insert(book2)

        var actualBooks = bookRepository.findAll()

        assertThat(actualBooks.filter { book: Book ->
            book.isbn == book1.isbn || book.isbn == book2.isbn
        }.toList()).hasSize(2)
    }

    @Test
    fun findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561237"
        var book = Book(bookIsbn, "Title", "Author", 12.09)
        jdbcAggregateTemplate.insert(book)
        var actualBook: Optional<Book>? = bookRepository.findByIsbn(bookIsbn)

        assertThat(actualBook).isPresent
        assertThat(actualBook!!.get().isbn).isEqualTo(book.isbn)
    }

    @Test
    fun findBookByIsbnWhenNotExisting() {
        var actualBook: Optional<Book>? = bookRepository.findByIsbn("1234561238")
        assertThat(actualBook).isEmpty
    }

    @Test
    fun existsByIsbnWhenExisting() {
        var bookIsbn = "1234561239"
        var bookToCreate = Book(bookIsbn, "Title", "Author", 12.90)
        jdbcAggregateTemplate.insert(bookToCreate)

        var existing: Boolean = bookRepository.existsByIsbn(bookIsbn)

        assertThat(existing).isFalse
    }

    @Test
    fun existsByIsbnWhenNotExisting() {
        var existing: Boolean = bookRepository.existsByIsbn("1234561240")
        assertThat(existing).isFalse
    }

    @Test
    fun deleteByIsbn() {
        var bookIsbn = "1234561241"
        var bookToCreate = Book(bookIsbn, "Title", "Author", 12.90)
        var persistedBook = jdbcAggregateTemplate.insert(bookToCreate)

        assertThat(bookRepository.deleteByIsbn(bookIsbn)).isTrue
    }
}