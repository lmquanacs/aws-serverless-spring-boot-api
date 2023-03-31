package com.mle.seed.handler

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.internal.testutils.Timer
import com.amazonaws.serverless.proxy.internal.servlet.AwsLambdaServletContainerHandler.StartupHandler
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.mle.seed.Application
import com.mle.seed.filter.CognitoIdentityFilter
import mu.KotlinLogging
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.EnumSet
import javax.servlet.DispatcherType
import javax.servlet.FilterRegistration
import javax.servlet.ServletContext

private val logger = KotlinLogging.logger {}
class StreamLambdaHandler : RequestStreamHandler {
    init {
        // we enable the timer for debugging. This SHOULD NOT be enabled in production.
        Timer.enable()
    }

    @Throws(IOException::class)
    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        handler.proxyStream(inputStream, outputStream, context)
    }

    companion object {
        private var handler: SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>

        init {
            try {
                val profile = System.getenv("spring_profile").toString()
                logger.info { "Profile = $profile" }
                handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application::class.java, profile)

                // For applications that take longer than 10 seconds to start, use the async builder:
                // handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                //                    .defaultProxy()
                //                    .asyncInit()
                //                    .springBootApplication(Application.class)
                //                    .buildAndInitialize();

                // we use the onStartup method of the handler to register our custom filter
                handler.onStartup(StartupHandler { servletContext: ServletContext ->
                    val registration: FilterRegistration.Dynamic = servletContext.addFilter("CognitoIdentityFilter",
                        CognitoIdentityFilter::class.java)
                    registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*")
                })
            } catch (e: ContainerInitializationException) {
                // if we fail here. We re-throw the exception to force another cold start
                e.printStackTrace()
                throw RuntimeException("Could not initialize Spring Boot application", e)
            }
        }
    }
}