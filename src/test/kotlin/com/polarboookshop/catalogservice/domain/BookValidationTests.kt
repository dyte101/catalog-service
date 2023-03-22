package com.polarboookshop.catalogservice.domain

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class BookValidationTests {

    companion object {
        private lateinit var validator: Validator

        @BeforeAll
        @JvmStatic
        fun setUp() {
            val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }

    @Test
    fun `should pass when all fields are correct`() {
        // given
        var book = Book("1234567890", "Title", "Author", 9.90)

        // when
        var violations: Set<ConstraintViolation<Book>> = validator.validate(book)

        // then
        assertThat(violations).isEmpty()
    }

    @Test
    fun `should fail when isbn is incorrect`() {
        // given
        var book = Book("a234567890", "Title", "Author", 9.90)

        // when
        var violations: Set<ConstraintViolation<Book>> = validator.validate(book)

        // then
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The ISBN format must be valid.")
    }
}