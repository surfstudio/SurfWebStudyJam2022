package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Team
import ru.surf.report.model.TeamWithMentor
import java.util.UUID

interface TeamRepository : JpaRepository<Team, UUID> {
    @Query(
        "select new ru.surf.report.model.TeamWithMentor(t.id, t.mentor.name) " +
                "from Team t " +
                "where t.event.id = :eventId"
    )
    fun getTeamsMentor(@Param("eventId") eventId: UUID): List<TeamWithMentor>

    @Query(
        value = "select tf.score " +
                "from teams t join teams_feedbacks tf on t.id = tf.team_id " +
                "where t.id = :teamId",
        nativeQuery = true
    )
    fun getTeamScoreById(@Param("teamId") teamId: UUID): List<Int>


}