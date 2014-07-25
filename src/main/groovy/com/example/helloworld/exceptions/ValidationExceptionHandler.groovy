package com.example.helloworld.exceptions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.validation.ValidationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {

    private final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class)

    @Inject
    ValidationExceptionHandler() {
    }

    @Override
    Response toResponse(ValidationException e) {
        LOGGER.debug(e.getMessage())
        return Response.serverError().status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type("text/plain").build()
    }
}
