package com.tcs.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import com.tcs.dto.PersonDTO;

@RegisterRestClient
@Path("/users")
public interface ExternalApiClient {
    @GET
    PersonDTO[] fetchPersons();
}