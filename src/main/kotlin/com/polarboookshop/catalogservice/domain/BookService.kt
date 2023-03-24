package com.polarboookshop.catalogservice.domain

import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class BookService(var bookRepository: BookRepository) {

    fun viewBookList(): Iterable<Book> {
        return bookRepository.findAll()
    }

    @Throws(BookNotFoundException::class)
    fun viewBookDetails(isbn: String): Book {
        if (bookRepository.existsByIsbn(isbn)) {
            return bookRepository.findByIsbn(isbn)!!.get()
        } else {
            throw BookNotFoundException(isbn)
        }

    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String): Boolean {
        if (bookRepository.existsByIsbn(isbn)) {
            return bookRepository.deleteByIsbn(isbn)
        } else {
            throw BookNotFoundException(isbn)
        }

    }

    fun editBookDetails(isbn: String, book: Book): Book {
        return if (bookRepository.findByIsbn(isbn)!!.isPresent) {
            var bookToUpdate = bookRepository.findByIsbn(isbn)
            bookToUpdate!!.get().id = bookToUpdate.get().id
            bookToUpdate.get().isbn = bookToUpdate.get().isbn
            bookToUpdate.get().title = book.title
            bookToUpdate.get().author = book.author
            bookToUpdate.get().price = book.price
            bookToUpdate.get().createdDate = bookToUpdate.get().createdDate
            bookToUpdate.get().lastModifiedDate = bookToUpdate.get().lastModifiedDate
            bookToUpdate.get().version = bookToUpdate.get().version
            bookRepository.save(bookToUpdate.get())
        } else {
            addBookToCatalog(book)
            book
        }

    }
}