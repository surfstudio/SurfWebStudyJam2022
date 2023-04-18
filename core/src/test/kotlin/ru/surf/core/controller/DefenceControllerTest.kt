package ru.surf.core.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import ru.surf.core.service.impl.DefenceServiceImpl
import ru.surf.meeting.dto.ZoomCreateMeetingRequestDto
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = [DefenceController::class])
internal class DefenceControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var defenceServiceImpl: DefenceServiceImpl

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create defence successfully`() {
        val request = PostRequestDefenceDto(
            teamId = UUID.fromString("070e6988-b9dc-11ed-afa1-0242ac120002"),
            eventId = UUID.fromString("070e6988-b9dc-11ed-afa1-0242ac120006"),
            title = "Surf Study Jam",
            description = "Тестовая защита",
            date = LocalDateTime.now(),
            traineeIds = listOf(),
            juryIds = listOf(),
            zoomCreateMeetingRequestDto = ZoomCreateMeetingRequestDto(
                duration = 20,
                description = "Test Desc",
                startTime = "2023-03-20T12:02:00Z",
                topic = "Surf Study Jam Defence",
                recurrence = ZoomCreateMeetingRequestDto.Recurrence(type = 2)
            )
        )
        val response = PostResponseDefenceDto(
            id = UUID.randomUUID(),
            zoomMeetingId = ThreadLocalRandom.current().nextLong(),
            title = request.title,
            description = request.description,
            date = request.date,
            candidatesParticipants = listOf(Trainee(Candidate())),
            employeeParticipants = listOf(SurfEmployee("test name")),
            zoomLink = "12oj3o1j23ojoh1o2h4"
        )
        every { defenceServiceImpl.createDefence(request) } returns response
        mockMvc.perform(
            post("/defence")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title", `is`(response.title)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description", `is`(response.description)))
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.candidatesParticipants",
                    CoreMatchers.not(emptyList<Trainee>())
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.employeeParticipants",
                    CoreMatchers.not(emptyList<SurfEmployee>())
                )
            )
    }

    @Test
    fun `should cancel defence successfully`() {
        val testId = UUID.randomUUID()
        every { defenceServiceImpl.deleteDefence(testId) } just runs
        mockMvc.perform(
            delete("/defence/{id}",testId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}