package ru.surf.report.service

import ru.surf.report.model.Report

interface ReportWrapper {
    fun wrap(report: Report): ByteArray
}