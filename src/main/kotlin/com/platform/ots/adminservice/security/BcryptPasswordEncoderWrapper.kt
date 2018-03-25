package com.platform.ots.adminservice.security

import org.apache.commons.lang3.StringUtils.equals
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class BcryptPasswordEncoderWrapper(val passwordEncoder: PasswordEncoder) : PasswordEncoder {

    private val B_CRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}")

    override fun encode(rawPassword: CharSequence?): String {
        val newPassword: String = rawPassword.toString()
        return if (isEncoded(newPassword)) newPassword else passwordEncoder.encode(newPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return when {
            isEncoded(rawPassword.toString()) -> equals(rawPassword, encodedPassword)
            else -> passwordEncoder.matches(rawPassword, encodedPassword)
        }
    }


    private fun isEncoded(newPassword: String): Boolean {
        return B_CRYPT_PATTERN.matcher(newPassword).matches()
    }
}