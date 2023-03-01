package ru.surf.remoting

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ClassUtils

open class RemoteInvocationTraceInterceptor(private val exporterName: String) : MethodInterceptor {
    @Throws(Throwable::class)
    override fun invoke(invocation: MethodInvocation): Any? {
        val method = invocation.method
        if (remoteInvocationLogger.isDebugEnabled) {
            remoteInvocationLogger.debug("Incoming $exporterName remote call: " +
                    ClassUtils.getQualifiedMethodName(method))
        }
        return try {
            invocation.proceed().also {
                if (remoteInvocationLogger.isDebugEnabled) {
                    remoteInvocationLogger.debug("Finished processing of $exporterName remote call: " +
                            ClassUtils.getQualifiedMethodName(method))
                }
            }
        } catch (throwable: Throwable) {
            if (throwable !is RuntimeException && throwable !is Error) {
                if (remoteInvocationLogger.isInfoEnabled) {
                    remoteInvocationLogger.info("Processing of $exporterName remote call resulted in exception: " +
                            ClassUtils.getQualifiedMethodName(method), throwable)
                }
            } else if (remoteInvocationLogger.isWarnEnabled) {
                remoteInvocationLogger.warn("Processing of $exporterName remote call resulted in fatal exception: " +
                        ClassUtils.getQualifiedMethodName(method), throwable)
            }
            throw throwable
        }
    }

    companion object {
        val remoteInvocationLogger: Logger = LoggerFactory.getLogger(RemoteInvocationTraceInterceptor::class.java)
    }
}