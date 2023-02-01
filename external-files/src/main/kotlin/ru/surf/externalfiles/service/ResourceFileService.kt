package ru.surf.externalfiles.service

import ru.surf.externalfiles.entity.Candidate

interface ResourceFileService {

    fun parseHrExcelFile(): List<Candidate>

}