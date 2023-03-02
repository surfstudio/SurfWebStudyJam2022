package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.EventInfo
import java.util.*

@Repository
interface EventInfoRepository : JpaRepository<EventInfo, UUID>