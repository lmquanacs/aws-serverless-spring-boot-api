package com.mle.filter

import com.amazonaws.serverless.proxy.RequestReader
import com.amazonaws.serverless.proxy.model.AwsProxyRequestContext
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class CognitoIdentityFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
        // nothing to do in init
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse?, filterChain: FilterChain) {
        val apiGwContext = servletRequest.getAttribute(RequestReader.API_GATEWAY_CONTEXT_PROPERTY)
        if (apiGwContext == null) {
            log.warn("API Gateway context is null")
            filterChain.doFilter(servletRequest, servletResponse)
            return
        }
        if (!AwsProxyRequestContext::class.java.isAssignableFrom(apiGwContext.javaClass)) {
            log.warn("API Gateway context object is not of valid type")
            filterChain.doFilter(servletRequest, servletResponse)
        }
        val ctx: AwsProxyRequestContext = apiGwContext as AwsProxyRequestContext
        if (ctx.identity == null) {
            log.warn("Identity context is null")
            filterChain.doFilter(servletRequest, servletResponse)
        }
        val cognitoIdentityId = ctx.identity.cognitoIdentityId
        if (cognitoIdentityId == null || "" == cognitoIdentityId.trim { it <= ' ' }) {
            log.warn("Cognito identity id in request is null")
        }
        servletRequest.setAttribute(COGNITO_IDENTITY_ATTRIBUTE, cognitoIdentityId)
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {
        // nothing to do in destroy
    }

    companion object {
        const val COGNITO_IDENTITY_ATTRIBUTE = "com.amazonaws.serverless.cognitoId"
        private val log = LoggerFactory.getLogger(CognitoIdentityFilter::class.java)
    }
}