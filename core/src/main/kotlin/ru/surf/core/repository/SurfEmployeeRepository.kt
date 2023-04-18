package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.surf.core.entity.SurfEmployee
import java.util.UUID

@Repository
interface SurfEmployeeRepository : JpaRepository<SurfEmployee, UUID> {

    @Query("SELECT * FROM surf_employees se, teams t WHERE se.id = t.mentor_id", nativeQuery = true)
    fun findMentors(): List<SurfEmployee>

    // TODO: 10.03.2023 Сделать выборку hr когда будет ролевая модель
    /*@Query("SELECT * FROM surf_employees se, accounts a WHERE se.id=a.id AND a.role = R ", nativeQuery = true)
    fun findHR():List<SurfEmployee>*/

}