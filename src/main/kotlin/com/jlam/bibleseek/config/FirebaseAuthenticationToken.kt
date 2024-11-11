package com.jlam.bibleseek.config

import com.google.firebase.auth.FirebaseToken
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class FirebaseAuthenticationToken(
    private val principal: String,
    private val firebaseToken: FirebaseToken?,
    authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any? = null

    override fun getPrincipal(): Any = principal

    fun getFirebaseToken(): FirebaseToken? = firebaseToken
}