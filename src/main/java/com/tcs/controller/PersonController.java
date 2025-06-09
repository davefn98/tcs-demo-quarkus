package com.tcs.controller;

import com.tcs.dto.PersonDTO;
import com.tcs.exception.ModelNotFoundException;
import com.tcs.services.PostService;

import io.quarkus.security.Authenticated;

import com.tcs.dto.GenericResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.jboss.logging.Logger;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class PersonController {

    private static final Logger logger = Logger.getLogger(PersonController.class);

    @Inject
    PostService postService;

    @GET
    @RolesAllowed("user")
    //@PermitAll
    public GenericResponse<PersonDTO> getAllPersons() {
        try {
            List<PersonDTO> obfuscatedList = postService.getPosts().stream()
                .map(this::obfuscateSensitiveData)
                .toList();
            logger.info("Consulta exitosa de todas las personas");
            return new GenericResponse<>("success", "Consulta exitosa", obfuscatedList);
        } catch (Exception ex) {
            logger.error("Búsqueda fallida: " + ex.getMessage());
            throw ex;
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getPersonById(@PathParam("id") @Positive(message = "ID must be positive") Integer id) {
        try {
            PersonDTO person = postService.getPosts().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .map(this::obfuscateSensitiveData)
                .orElseThrow(() -> new ModelNotFoundException("Person not found with ID: " + id));
            logger.info("Búsqueda exitosa para el ID: " + id);
            return Response.ok(new GenericResponse<>("success", "Consulta exitosa", List.of(person))).build();
        } catch (ModelNotFoundException ex) {
            logger.error("Búsqueda fallida: No se encontró la persona con ID: " + id);
            throw ex;
        }
    }

    private PersonDTO obfuscateSensitiveData(PersonDTO dto) {
        return new PersonDTO(
            dto.id(),
            dto.name(),
            dto.username(),
            dto.email(),
            "99999999999",
            dto.web()
        );
    }
}