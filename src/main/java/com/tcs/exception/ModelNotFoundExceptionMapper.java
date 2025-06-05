package com.tcs.exception;

import com.tcs.dto.GenericResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ModelNotFoundExceptionMapper implements ExceptionMapper<ModelNotFoundException> {
    @Override
    public Response toResponse(ModelNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new GenericResponse<>("error", ex.getMessage(), List.of()))
                .build();
    }
}