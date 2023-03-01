package ru.surf.remoting.hessian.accessor

import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.factory.FactoryBean

class HessianProxyFactoryBean<T: Any>(
        serviceInterface: Class<T>,
        serviceUrl: String
) : HessianClientInterceptor<T>(serviceInterface, serviceUrl), FactoryBean<Any> {

    private lateinit var serviceProxy: Any

    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
        serviceProxy = ProxyFactory(this.serviceInterface, this).getProxy(getBeanClassLoader())
    }

    override fun getObject(): Any = serviceProxy

    override fun getObjectType(): Class<T> = this.serviceInterface

    override fun isSingleton(): Boolean = true

}