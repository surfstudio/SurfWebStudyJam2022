package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.core.entity.EventTag
import java.util.*

@Repository
interface EventTagRepository : JpaRepository<EventTag, UUID> {

    fun findEventTagByDescription(description: String): EventTag?

}