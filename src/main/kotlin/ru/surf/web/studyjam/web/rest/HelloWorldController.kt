package ru.surf.web.studyjam.web.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hello-world")
class HelloWorldController {

//    http://localhost:8080/api/v1/hello-world/say
//    http://localhost:8080/swagger-ui/index.html#/hello-world-controller/getHelloWorld
    @GetMapping("/say")
    fun getHelloWorld(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, World!")
    }
}
