package ru.surf.meeting.service

import org.springframework.http.ResponseEntity
import ru.surf.meeting.dto.ZoomCreateMeetingRequestDto
import ru.surf.meeting.dto.zoom.ZoomAdminUserResponse
import ru.surf.meeting.dto.zoom.ZoomCreateMeetingResponseDto
import ru.surf.meeting.dto.zoom.ZoomAccessTokenResponseDto

interface ZoomIntegrationService {

    fun getZoomAdminUserInformation(userId: String): ZoomAdminUserResponse

    fun refreshAccessToken(): ZoomAccessTokenResponseDto

    fun createZoomMeeting(zoomCreateMeetingRequestDto: ZoomCreateMeetingRequestDto): ZoomCreateMeetingResponseDto

    fun deleteZoomMeeting(zoomMeetingId: Long): ResponseEntity<Void>

}