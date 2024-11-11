import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import java.time.Instant

class FirebaseJwtDecoder : JwtDecoder {

    override fun decode(token: String): Jwt {
        return try {
            // Verify the Firebase ID Token
            val decodedToken: FirebaseToken = FirebaseAuth.getInstance().verifyIdToken(token)

            // Create basic headers manually
            val headers = mapOf<String, Any>(
                "alg" to "RS256",  // Firebase uses RS256 by default
                "typ" to "JWT"
            )

            // Extract claims from the decoded token
            val claims = decodedToken.claims

            // Extract issuedAt and expiresAt from the claims map
            val issueTime = claims["iat"] as? Long
            val expirationTime = claims["exp"] as? Long

            // Check for null values and handle accordingly
            if (issueTime == null || expirationTime == null) {
                throw JwtException("Token does not contain valid 'iat' or 'exp' claims.")
            }

            // Convert issueTime and expirationTime from Unix timestamp (seconds) to Instant
            val issueInstant = Instant.ofEpochSecond(issueTime)
            val expirationInstant = Instant.ofEpochSecond(expirationTime)

            // Return the Jwt object
            Jwt(
                token,
                issueInstant,
                expirationInstant,
                headers,
                claims
            )
        } catch (e: Exception) {
            throw JwtException("Firebase token verification failed: ${e.message}", e)
        }
    }
}
