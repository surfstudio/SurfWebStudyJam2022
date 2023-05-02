package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.ProjectCard
import java.util.*

interface ProjectCardRepository : JpaRepository<ProjectCard, UUID> {
}
