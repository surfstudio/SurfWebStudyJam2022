package ru.surf.remoting.hessian.accessor

import com.caucho.hessian.client.HessianProxyFactory
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert
import ru.surf.remoting.RemoteAccessor
import java.lang.reflect.InvocationTargetException

open class HessianClientInterceptor<T: Any>(
        serviceInterface: Class<T>,
        serviceUrl: String
) : RemoteAccessor<T>(serviceInterface, serviceUrl), MethodInterceptor, InitializingBean {

    private lateinit var hessianProxy: Any

    override fun afterPropertiesSet() {
        hessianProxy = HessianProxyFactory().create(serviceInterface, serviceUrl, getBeanClassLoader())
    }

    override operator fun invoke(invocation: MethodInvocation): Any? {
        Assert.isTrue(this::hessianProxy.isInitialized, "HessianClientInterceptor is not properly initialized")
        return doInvoke(invocation)
    }

    @Throws(Throwable::class)
    private fun doInvoke(invocation: MethodInvocation): Any? {
        val originalClassLoader = overrideThreadContextClassLoader()
        try {
            return invocation.method.invoke(hessianProxy, *invocation.arguments)
        }
        catch (ex: InvocationTargetException) {
            val targetEx = ex.targetException
            if (targetEx is InvocationTargetException) {
                throw targetEx.targetException
            }
            throw targetEx
        }
        finally {
            resetThreadContextClassLoader(originalClassLoader)
        }
    }

}