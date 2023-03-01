package ru.surf.testing.exception.base

abstract class TestingException(

        override var message: String

) : Exception(message) {

    open fun getParams(): Map<String, *> = mapOf<String, Any>()

}