package com.tcs.services;

import com.tcs.client.ExternalApiClient;
import com.tcs.client.ExternalApiException;
import com.tcs.dto.PersonDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.Arrays;
import java.util.List;

import org.jboss.logging.Logger;

@ApplicationScoped
public class PostService {
    private static final Logger logger = Logger.getLogger(PostService.class);

    @Inject
    @RestClient
    ExternalApiClient externalApiClient;

    public List<PersonDTO> getPosts() {
        try {
            return Arrays.asList(externalApiClient.fetchPersons());
        } catch (WebApplicationException ex) {
            logger.error("Error HTTP al consumir la API externa (GET /users): " + ex.getMessage(), ex);
            throw new ExternalApiException("Error HTTP al consumir la API externa", ex);
        } catch (ProcessingException ex) {
            logger.error("Error de red al consumir la API externa (GET /users): " + ex.getMessage(), ex);
            throw new ExternalApiException("Error de red al consumir la API externa", ex);
        } catch (Exception ex) {
            logger.error("Error inesperado al consumir la API externa (GET /users): " + ex.getMessage(), ex);
            throw new ExternalApiException("Error inesperado al consumir la API externa", ex);
        }
    }
}