package org.example.moviesearchservice.component;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* org.example.moviesearchservice.controller.*.create*(..))")
    public void create() {

    }

    @Pointcut("execution(* org.example.moviesearchservice.controller.*.update*(..))")
    public void update() {

    }

    @Pointcut("execution(* org.example.moviesearchservice.controller.*.delete*(..))")
    public void delete() {

    }

    @AfterReturning(pointcut = "create()", returning = "result")
    public void logCreate(Object result) {
        logger.info("Created: {}", result);
    }

    @AfterReturning(pointcut = "update()", returning = "result")
    public void logUpdate(Object result) {
        logger.info("Updated: {}", result);
    }

    @AfterReturning(pointcut = "delete()", returning = "result")
    public void logDelete(Object result) {
        logger.info("Deleted: {}", result);
    }
}
