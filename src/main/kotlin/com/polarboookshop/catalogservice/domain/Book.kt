package com.polarboookshop.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedDate

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.Instant
import javax.annotation.processing.Generated

data class Book(

    @Id
    var id: Long,

    @field:NotBlank(message = "The book ISBN must be defined.")
    @field:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "The ISBN format must be valid."
    )
    var isbn: String,

    @field:NotBlank(message = "The book title must be defined.")
    var title: String,

    @field:NotBlank(message = "The book author must be defined.")
    var author: String,

    @field:Positive(message = "The book price must be greater than zero.")
    var price: Double,

    @CreatedDate
    var createdDate: Instant?,

    @LastModifiedDate
    var lastModifiedDate: Instant?,

    @Version
    var version: Int
) {
  constructor(): this(0, "", "", "", 0.0, null, null, 0){}
    constructor(isbn: String, title: String, author: String, price: Double) : this() {
        this.isbn = isbn
        this.title = title
        this.author = author
        this.price = price
    }
}
