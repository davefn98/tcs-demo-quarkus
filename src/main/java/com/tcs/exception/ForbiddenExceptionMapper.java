package com.tcs.exception;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.security.ForbiddenException;
import org.jboss.logging.Logger;

@Provider
@Priority(Priorities.AUTHORIZATION + 1)
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    private static final Logger LOGGER = Logger.getLogger(ForbiddenExceptionMapper.class);

    @Inject
    JsonWebToken jwt;

    @Override
    public Response toResponse(ForbiddenException exception) {
        String username = jwt.getName();
        String mensaje = "🚫 Acceso denegado: usuario '" + username + "' no tiene permisos para este recurso.";

        LOGGER.warn(mensaje);

        return Response.status(Response.Status.FORBIDDEN)
                .entity(mensaje)
                .type("text/plain")
                .build();
    }
}