package ru.surf.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.core.dto.candidate.CredentialsDto
import ru.surf.core.entity.Account
import ru.surf.core.entity.Candidate
import ru.surf.core.service.TraineeService

@RestController
@RequestMapping(value = ["/trainee"])
class TraineeController(
        @Autowired
        private val traineeService: TraineeService
) {

        @PostMapping(value = ["/activate"])
        @PreAuthorize("isAuthenticated")
        fun activate(@AuthenticationPrincipal candidate: Candidate,
                     @RequestBody credentialsDto: CredentialsDto): Account =
                traineeService.activateCandidate(candidate, credentialsDto)

}
