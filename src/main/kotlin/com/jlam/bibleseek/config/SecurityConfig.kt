package com.jlam.bibleseek.config

import FirebaseJwtDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return FirebaseJwtDecoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Create a custom JWT converter (if you need role extraction)
        val jwtAuthenticationConverter = JwtAuthenticationConverter()

        http
            .csrf { it.disable() } // Disable CSRF for stateless authentication
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/api/auth/**").permitAll() // Allow public access to auth endpoints
                    .anyRequest().authenticated() // All other requests need authentication
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2
                    .jwt { jwt ->
                        jwt.decoder(jwtDecoder()) // Use the custom JwtDecoder bean
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) // Apply custom JWT handling
                    }
            }

        return http.build()
    }
}