package com.polarboookshop.catalogservice.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @Autowired
    lateinit var environment: Environment

    @GetMapping("/")
    fun getGreeting(): String? {
        return environment.getProperty("polar.greeting")
    }
}