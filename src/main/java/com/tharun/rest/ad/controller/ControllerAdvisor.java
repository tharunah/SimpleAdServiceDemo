package com.tharun.rest.ad.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Global exception handler for REST endpoints.
 * Provides centralized error handling and logging.
 *
 * @since 1.0
 */
@ControllerAdvice
public class ControllerAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoSuchElementException(
            final NoSuchElementException exception,
            final HttpServletRequest req,
            final HttpServletResponse res) {
        LOG.warn("Resource not found for {} {}: {}", req.getMethod(), req.getRequestURI(), exception.getMessage());
        return new ResponseEntity<>(
            Map.of("error", "Not found", "message", exception.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleExceptions(
            final Exception exception,
            final HttpServletRequest req,
            final HttpServletResponse res) {
        LOG.error("Unexpected service error processing {} {}:", req.getMethod(), req.getRequestURI(), exception);
        return new ResponseEntity<>(
            Map.of("error", "Internal Server Error", "message", exception.toString()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
