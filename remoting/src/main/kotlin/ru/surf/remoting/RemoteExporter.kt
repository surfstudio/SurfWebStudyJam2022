package ru.surf.remoting

import org.springframework.aop.framework.ProxyFactory
import org.springframework.util.ClassUtils

abstract class RemoteExporter<T: Any>(
        var serviceInterface: Class<in T>,
        var service: T,
) : RemotingSupport() {

    private val exporterName: String
        get() = ClassUtils.getShortName(this.javaClass)

    protected open fun getProxyForService(): Any {
        val proxyFactory = ProxyFactory()
        proxyFactory.addInterface(serviceInterface)
        proxyFactory.addAdvice(RemoteInvocationTraceInterceptor(exporterName))
        proxyFactory.setTarget(service)
        return proxyFactory.getProxy(getBeanClassLoader())
    }

}