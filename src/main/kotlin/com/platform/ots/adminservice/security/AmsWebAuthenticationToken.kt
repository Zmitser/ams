package com.platform.ots.adminservice.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class AmsWebAuthenticationToken(val token: String?, principal: Any?, credentials: Any?, authorities: List<GrantedAuthority>?) : UsernamePasswordAuthenticationToken(principal, credentials, authorities) {
    constructor(principal: Any?, credentials: Any?, authorities: List<GrantedAuthority>) : this(null, principal, credentials, authorities)
    constructor(principal: Any?, credentials: Any?) : this(null, principal, credentials, null)
}