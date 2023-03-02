package ru.surf.remoting

abstract class RemoteAccessor<T: Any>(
        var serviceInterface: Class<T>,
        var serviceUrl: String
) : RemotingSupport() {

}