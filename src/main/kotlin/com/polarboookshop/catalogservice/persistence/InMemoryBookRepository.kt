package com.polarboookshop.catalogservice.persistence

import com.polarboookshop.catalogservice.domain.Book
import com.polarboookshop.catalogservice.domain.BookRepository
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBookRepository : BookRepository{

    private val books: MutableMap<String, Book> = ConcurrentHashMap()

    override fun findAll(): Iterable<Book> {
        return books.values
    }

    override fun findByIsbn(isbn: String): Optional<Book> = Optional.ofNullable(books[isbn])

    override fun existsByIsbn(isbn: String): Boolean {
        return books[isbn] != null
    }

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}