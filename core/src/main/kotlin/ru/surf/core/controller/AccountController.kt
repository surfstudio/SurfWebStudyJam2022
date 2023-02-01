package ru.surf.core.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.core.entity.Account

@RestController
@RequestMapping(value = ["/account"])
class AccountController {

    @PostMapping(value = ["/login"])
    @PreAuthorize("isAuthenticated")
    fun login(authentication: Authentication) =
            ResponseCookie.from("TOKEN", authentication.credentials as String).
            secure(false).
            httpOnly(true).
            build().let {
                HttpHeaders().apply {
                    set(HttpHeaders.SET_COOKIE, it.toString())
                }
            }.let {
                ResponseEntity(authentication.principal, it, HttpStatus.OK)
            }

    @GetMapping
    @PreAuthorize("isAuthenticated")
    fun get(@AuthenticationPrincipal account: Account) = account

}