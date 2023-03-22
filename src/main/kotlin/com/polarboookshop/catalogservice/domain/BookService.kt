package com.polarboookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService {
    lateinit var bookRepository: BookRepository

    constructor(bookRepository: BookRepository) {
        this.bookRepository = bookRepository
    }

    fun viewBookList(): Iterable<Book> {
        return bookRepository.findAll()
    }

    fun viewBookDetails(isbn: String): Book {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow{ BookNotFoundException(isbn) }
    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String): Unit {
        return bookRepository.deleteByIsbn(isbn)
    }

    fun editBookDetails(isbn: String, book: Book): Book {
        return if (bookRepository.findByIsbn(isbn).isPresent) {
            var bookToUpdate = bookRepository.findByIsbn(isbn)
            bookToUpdate.get().title = book.title
            bookToUpdate.get().author = book.author
            bookToUpdate.get().price = book.price
            bookRepository.save(bookToUpdate.get())
        } else {
            addBookToCatalog(book)
            book
        }

    }
}