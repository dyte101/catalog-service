package com.polarboookshop.catalogservice.domain

import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class BookService {
    lateinit var bookRepository: BookRepository

    constructor(bookRepository: BookRepository) {
        this.bookRepository = bookRepository
    }

    fun viewBookList(): Iterable<Book> {
        return bookRepository.findAll()
    }

    @Throws(BookNotFoundException::class)
    fun viewBookDetails(isbn: String): Book? {
        return bookRepository.findByIsbn(isbn)?.get()
    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String): Boolean {
        return bookRepository.deleteByIsbn(isbn)
    }

    fun editBookDetails(isbn: String, book: Book): Book {
        return if (bookRepository.findByIsbn(isbn)!!.isPresent) {
            var bookToUpdate = bookRepository.findByIsbn(isbn)
            bookToUpdate!!.get().id = book.id
            bookToUpdate.get().title = book.title
            bookToUpdate.get().author = book.author
            bookToUpdate.get().price = book.price
            bookToUpdate.get().createdDate = book.createdDate
            bookToUpdate.get().lastModifiedDate = book.lastModifiedDate
            bookToUpdate.get().version = book.version
            bookRepository.save(bookToUpdate.get())
        } else {
            addBookToCatalog(book)
            book
        }

    }
}