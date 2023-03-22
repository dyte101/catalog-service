package com.polarboookshop.catalogservice.web

import com.polarboookshop.catalogservice.domain.BookNotFoundException
import com.polarboookshop.catalogservice.domain.BookService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
internal class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Test
    fun `should return 404 when book doesn't exist`() {
        // given / when
        var isbn = "73737373734"
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException::class.java)

        // then
        mockMvc.perform(get("/books/$isbn"))
            .andExpect(status().isNotFound)
    }
}