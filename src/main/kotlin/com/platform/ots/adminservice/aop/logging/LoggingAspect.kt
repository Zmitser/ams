package com.platform.ots.adminservice.aop.logging

import com.platform.ots.adminservice.constant.Constants
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Aspect
@Configuration
class LoggingAspect(val environment: Environment) {

    private val log = KotlinLogging.logger {}

    @Pointcut("within(com.platform.ots.adminservice.web.handler..*) || " +
            "within(com.platform.ots.adminservice.web.router..*) || " +
            "within(com.platform.ots.adminservice.repository..*) " +
            "within(com.platform.ots.adminservice.web.service..*)")
    fun applicationPackagePointCut() {
    }


    /**
     * Advice that logs method after exception
     *
     * @param joinPoint joint point for advice
     * @param throwable exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointCut()", throwing = "throwable")
    fun logAfterThrowing(joinPoint: JoinPoint, throwable: Throwable) {
        if (environment.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)) {
            log.error(throwable) {
                """
                    Exception in ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}()
                    with cause = '${throwable.cause}' and exception = '${throwable.message}'
                """
            }
        } else {
            log.error {
                """
                    Exception in ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}()
                    with cause = '${throwable.cause}'
                """
            }
        }

    }

    @Around("applicationPackagePointCut()")
    @Throws(Throwable::class)
    fun logAround(proceedingJoinPoint: ProceedingJoinPoint): Any {
        if (log.isDebugEnabled) {
            log.debug {
                """
                    Enter: ${proceedingJoinPoint.signature.declaringTypeName}.${proceedingJoinPoint.signature.name}()
                    with argument[s] = ${proceedingJoinPoint.args}
               """
            }
        }
        try {
            val proceed: Any = proceedingJoinPoint.proceed()
            if (log.isDebugEnabled) {
                log.debug {
                    """
                        Exit: ${proceedingJoinPoint.signature.declaringTypeName}.${proceedingJoinPoint.signature.name}()
                        with result = $proceed
                    """
                }
            }
            return proceed
        } catch (e: IllegalArgumentException) {
            log.error(e) {
                """
                    Illegal argument: ${proceedingJoinPoint.args}
                    in ${proceedingJoinPoint.signature.declaringTypeName}.${proceedingJoinPoint.signature.name}()
                """
            }
            throw  e
        }
    }
}
