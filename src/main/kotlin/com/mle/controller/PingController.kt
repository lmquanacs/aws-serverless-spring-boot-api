package com.mle.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@RestController
@EnableWebMvc
class PingController {
    @GetMapping("/ping")
    fun ping(): String = "pong"
}