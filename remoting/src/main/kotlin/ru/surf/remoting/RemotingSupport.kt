package ru.surf.remoting

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanClassLoaderAware
import org.springframework.util.ClassUtils

abstract class RemotingSupport: BeanClassLoaderAware {
    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var beanClassLoader: ClassLoader

    override fun setBeanClassLoader(classLoader: ClassLoader) {
        this.beanClassLoader = classLoader
    }

    protected fun getBeanClassLoader(): ClassLoader {
        return beanClassLoader
    }

    protected fun overrideThreadContextClassLoader(): ClassLoader? {
        return ClassUtils.overrideThreadContextClassLoader(getBeanClassLoader())
    }

    protected fun resetThreadContextClassLoader(original: ClassLoader?) {
        original?.let {
            Thread.currentThread().contextClassLoader = original
        }
    }
}