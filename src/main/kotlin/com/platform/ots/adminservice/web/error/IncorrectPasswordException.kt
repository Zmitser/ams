package com.platform.ots.adminservice.web.error

import javax.security.sasl.AuthenticationException

class IncorrectPasswordException(message: String) : AuthenticationException(message)
