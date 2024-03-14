package org.example.bank.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.HandlerInterceptor

@Service
class CustomInterceptor: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURI.contains("/api/") && request.getHeader("X-ATM") != "value")
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "Missing auth Information")
        return super.preHandle(request, response, handler)
    }
}
