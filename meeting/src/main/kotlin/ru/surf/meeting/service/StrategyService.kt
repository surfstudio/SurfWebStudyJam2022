package ru.surf.meeting.service

interface StrategyService {

    fun chooseStrategy(event: Any): Any

}