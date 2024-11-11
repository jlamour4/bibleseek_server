package com.jlam.bibleseek.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class FirebaseTokenFilter : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")?.substring(7)

        if (token != null) {
            try {
                val decodedToken: FirebaseToken = FirebaseAuth.getInstance().verifyIdToken(token)
                val userId = decodedToken.uid
//                val userEmail = decodedToken.email
                val roles = (decodedToken.claims["roles"] as? List<*>)?.filterIsInstance<String>() ?: listOf("USER")  // Example of fetching roles from claims

                // Convert roles into Spring authorities
                val authorities = roles.map { SimpleGrantedAuthority(it) }

                // Create an authentication token with userId, decodedToken, and authorities
                val authToken = FirebaseAuthenticationToken(userId, decodedToken, authorities)

                // Add additional request details (IP address, session ID)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                // Set the authenticated user in the security context
                SecurityContextHolder.getContext().authentication = authToken

            } catch (e: Exception) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }
        }
        filterChain.doFilter(request, response)
    }
}