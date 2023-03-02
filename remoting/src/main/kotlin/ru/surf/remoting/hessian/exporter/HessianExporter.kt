package ru.surf.remoting.hessian.exporter

import com.caucho.hessian.io.*
import com.caucho.hessian.server.HessianSkeleton
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert
import ru.surf.remoting.RemoteExporter
import java.io.*


open class HessianExporter<T : Any>(
        serviceInterface: Class<in T>,
        service: T,
) : RemoteExporter<T>(serviceInterface, service), InitializingBean {

    companion object {
        const val CONTENT_TYPE_HESSIAN = "application/x-hessian"
    }

    private lateinit var skeleton: HessianSkeleton

    private val serializerFactory: SerializerFactory = SerializerFactory()

    override fun afterPropertiesSet() {
        skeleton = HessianSkeleton(getProxyForService(), serviceInterface)
    }

    /**
     * Perform an invocation on the exported object.
     * @param inputStream the request stream
     * @param outputStream the response stream
     * @throws Throwable if invocation failed
     */
    @Throws(Throwable::class)
    operator fun invoke(inputStream: InputStream, outputStream: OutputStream) {
        Assert.isTrue(this::skeleton.isInitialized, "Hessian exporter has not been initialized")
        doInvoke(skeleton, inputStream, outputStream)
    }

    /**
     * Actually invoke the skeleton with the given streams.
     * @param skeleton the skeleton to invoke
     * @param inputStream the request stream
     * @param outputStream the response stream
     * @throws Throwable if invocation failed
     */
    @Throws(Throwable::class)
    protected fun doInvoke(skeleton: HessianSkeleton, inputStream: InputStream, outputStream: OutputStream) {
        val originalClassLoader = overrideThreadContextClassLoader()
        try {
            hessianInvoke(skeleton, inputStream, outputStream)
        }
        finally {
            resetThreadContextClassLoader(originalClassLoader)
        }
    }

    private fun hessianInvoke(skeleton: HessianSkeleton, inputStream: InputStream, outputStream: OutputStream) {
        var isToUse = inputStream
        var osToUse = outputStream
        if (logger.isDebugEnabled) {
            val debugWriter = PrintWriter(object : Writer() {
                override fun close() = Unit
                override fun flush() = Unit
                override fun write(cbuf: CharArray, off: Int, len: Int) {
                    logger.debug(cbuf.slice(off..off + len).joinToString())
                }
            })
            val dis = HessianDebugInputStream(inputStream, debugWriter)
            dis.startTop2()
            val dos = HessianDebugOutputStream(outputStream, debugWriter)
            dos.startTop2()
            isToUse = dis
            osToUse = dos
        }
        if (!isToUse.markSupported()) {
            isToUse = BufferedInputStream(isToUse)
            isToUse.mark(1)
        }
        val code = isToUse.read()
        val major: Int
        val minor: Int
        val hessianInput: AbstractHessianInput
        val hessianOutput: AbstractHessianOutput
        when (code) {
            'H'.code -> {
                // Hessian 2.0 stream
                major = isToUse.read()
                minor = isToUse.read()
                if (major != 0x02) {
                    throw IOException("Version $major.$minor is not understood")
                }
                hessianInput = Hessian2Input(isToUse)
                hessianOutput = Hessian2Output(osToUse)
                hessianInput.readCall()
            }

            'C'.code -> {
                // Hessian 2.0 call... for some reason not handled in HessianServlet!
                isToUse.reset()
                hessianInput = Hessian2Input(isToUse)
                hessianOutput = Hessian2Output(osToUse)
                hessianInput.readCall()
            }

            'c'.code -> {
                // Hessian 1.0 call
                major = isToUse.read()
                @Suppress("UNUSED_VALUE")
                minor = isToUse.read()
                hessianInput = HessianInput(isToUse)
                hessianOutput = if (major >= 2) {
                    Hessian2Output(osToUse)
                } else {
                    HessianOutput(osToUse)
                }
            }

            else -> {
                throw IOException("Expected 'H'/'C' (Hessian 2.0) or 'c' (Hessian 1.0) in hessian input at $code")
            }
        }
        hessianInput.setSerializerFactory(serializerFactory)
        hessianOutput.serializerFactory = serializerFactory
        try {
            skeleton.invoke(hessianInput, hessianOutput)
        } finally {
            try {
                hessianInput.close()
                isToUse.close()
            } catch (ex: IOException) {
                // ignore
            }
            try {
                hessianOutput.close()
                osToUse.close()
            } catch (ex: IOException) {
                // ignore
            }
        }
    }
}