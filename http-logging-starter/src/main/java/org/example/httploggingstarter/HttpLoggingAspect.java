package org.example.httploggingstarter;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class HttpLoggingAspect {

    private final HttpLoggingProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingAspect.class);

    public HttpLoggingAspect(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object logHttpRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = (HttpServletRequest) attributes.getRequest();
        }

        StringBuilder requestLog = new StringBuilder();
        if (request != null) {
            requestLog.append("HTTP Request - ")
                    .append("Method: ").append(request.getMethod())
                    .append(", URI: ").append(request.getRequestURI());
            if (request.getQueryString() != null) {
                requestLog.append(", Query: ").append(request.getQueryString());
            }
        } else {
            requestLog.append("HTTP Request - [Не удалось получить объект HttpServletRequest]");
        }

        log(requestLog.toString());

        Object result = joinPoint.proceed();

        String responseLog = "HTTP Response - " + result;
        log(responseLog);

        return result;
    }

    private void log(String message) {
        switch (properties.getLevel().toLowerCase()) {
            case "debug":
                logger.debug(message);
                break;
            case "warn":
                logger.warn(message);
                break;
            case "error":
                logger.error(message);
                break;
            case "info":
            default:
                logger.trace(message);
                break;
        }
    }
}
