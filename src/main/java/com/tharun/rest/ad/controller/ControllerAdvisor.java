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

/**
 * Created by tharu on 4/27/2017.
 */
@ControllerAdvice
public class ControllerAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> handleExceptions(final Exception exception, final HttpServletRequest req, final HttpServletResponse res) {
        LOG.error("unexpected service error processing {} {}:", req.getMethod(), req.getRequestURI(), exception);

        return new ResponseEntity<>(exception.toString(), HttpStatus.EXPECTATION_FAILED);
    }
}
